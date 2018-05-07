#install.packages("rjson")
library(GA)
library(jsonlite)
library(parallel)
library(doParallel)

#----------------------------------------------------------------------------------------------------------------
# Load to usefull data from previous created json file to matrix and variables at R 

runForAll <- function(fileName) { 
  
  json <- fromJSON(fileName)
  
  nr_clientes = json$meta_data$nb_customers
  nr_depositos = json$meta_data$nb_depots
  vehicle_cap = json$meta_data$vehicle_cap
  vehicle_cost = json$meta_data$vehicle_cost
  cost_type = json$meta_data$cost_type
  clientes = json$customers
  depositos = json$depots
  
  customerDistances = matrix(nrow=nr_clientes, ncol = nr_clientes)
  customerDemand = matrix(ncol=nr_clientes, nrow=1)
  depositosDistances = matrix(nrow = nr_clientes, ncol = nr_depositos)
  auxiliarDepositos = matrix(nrow = nr_depositos, ncol = nr_depositos)
  auxiliarPlot = matrix(nrow = nr_clientes + nr_depositos, ncol = nr_clientes + nr_depositos)
  depositosStock = matrix(ncol=nr_depositos, nrow=1)
  depot_cost = matrix(ncol=nr_depositos, nrow=1)
  
  #calculate clientes distances from c0 -> c49
  
  i=1
  cli_pos = matrix(nrow=nr_clientes, ncol=2, byrow = TRUE)
  for(c1 in clientes){
    cli_pos[i, 1] = c1$x
    cli_pos[i, 2] = c1$y
    i = i+1
  }
  
  i = 1
  depot_pos = matrix(nrow=nr_depositos, ncol=2, byrow = TRUE)
  for(depot in depositos){
    depot
    depositosStock[i] = depot$capacity
    depot_cost[i] = depot$opening_cost
    depot_pos[i,1] = depot$x
    depot_pos[i,2] = depot$y
    i = i+1
  }
  
  i=1
  j=1
  
  for (c1 in clientes){
    x1 = c1$x;
    y1 = c1$y;
    customerDemand[i] = c1$demand;
    for (c2 in clientes){
      x2 = c2$x;
      y2 = c2$y;
      d = sqrt((x1 - x2)^2 + (y1-y2)^2);
      customerDistances[i,j] = d;
      customerDistances[j,i] = d;
      auxiliarPlot[i,j] = d;
      auxiliarPlot[j,i] = d;
      j = j+1;
    }
    j=1
    i = i+1;
  }
  
  i=1
  j=1
  for (c in clientes){
    x1 = c$x;
    y1 = c$y;
    for (d in depositos){
      x2 = d$x;
      y2 = d$y;
      distance = sqrt((x1 - x2)^2 + (y1-y2)^2);
      depositosDistances[i,j] = distance;
      auxiliarPlot[i, nr_clientes + j] = distance; 
      auxiliarPlot[nr_clientes + j,i] = distance; 
      j = j+1;
    }
    j=1
    i = i+1;
  }
  
  i=1
  j=1
  for (d1 in depositos){
    x1 = d1$x;
    y1 = d1$y;
    for (d2 in depositos){
      x2 = d2$x;
      y2 = d2$y;
      distance = sqrt((x1 - x2)^2 + (y1-y2)^2);
      auxiliarDepositos[i,j] = distance;
      auxiliarDepositos[j,i] = distance;
      auxiliarPlot[nr_clientes + i, nr_clientes + j] = distance;
      auxiliarPlot[nr_clientes + j, nr_clientes + i] = distance;
      j = j+1;
    }
    j=1
    i = i+1;
  }
  
  #auxiliarPlot[nr_clientes:nrow(auxiliarPlot), nr_clientes:nrow(auxiliarPlot)]
  
  remove(i,j, x1, x2, y1, y2, distance, c, c1, c2, d, d1, d2, depot)
  remove(clientes, depositos, json)
  
  return (list(auxiliarDepositos, auxiliarPlot, 
         cli_pos, customerDemand, customerDistances, 
         depositosDistances, depositosStock,depot_cost, depot_pos, 
         cost_type, nr_clientes, nr_depositos, 
         vehicle_cap, vehicle_cost))
} # end of read_data_from_Json
#----------------------------------------------------------------------------------------------------------------

#----------------------------------------------------------------------------------------------------------------
# função para calcular o custo de um genoma (conjunto de cromossomas referentes a N depósitos)
detalhesSolucao <- function(genoma, customerDistances, customerDemand, depositosDistances) {
  # Cada cromossoma representa um deposito, que pode ou nao ser aberto
  cromossomas = matrix(data=genoma, nrow=nr_depositos, ncol=nr_camioes*nr_alelos, byrow=TRUE);
  
  # custo inicial da solução é zero
  custo = 0;
  used_depositos = 0
  used_camioes = matrix(ncol = nr_depositos, nrow = 1)
  for(it in c(1:length(used_camioes))){ used_camioes[it] = 0 }
  
  # vetor com a procura de cada client. No final, a soma deve ser zero (toda a procura satisfeita)
  procura = cli_Demand
  
  # Primeiro ciclo divide o genoma em cromossomas
  # - Computar cada um dos depositos (cromossomas)
  for(it_dep in c(1:nrow(cromossomas))){
    # Teste inicial para saber se o deposito é ou não aberto 
    # - Olha para os dados do deposito sem considerar as divisões por camião
    # - Fica apenas com os valores do cromossoma entre [1:nr_clientes]
    # - Se length(deposito == 0) nenhum camião do deposito é utilizado -> o deposito nao é aberto
    deposito = intersect(cromossomas[it_dep,], c(1:nr_clientes));
    
    if(length(deposito)!=0){ # O deposito é aberto -> terá influencia no custo 
      # opening costs for the depots
      custo = custo + depot_cost[it_dep]
      used_depositos = used_depositos + 1; 
      
      # stock inicial do deposito 
      stock_atual = depositosStock[it_dep] # 
      
      # Dividir as infos do deposito por cada camião 
      deposito = matrix(data=cromossomas[it_dep,], nrow=nr_camioes, ncol=nr_alelos)  
      
      # Segundo ciclo divide o cromossoma em genes
      # - Computar cada um dos camiões (gene) do deposito i
      for(it_cam in c(1:nrow(deposito))){
        # Obter os alelos de um gene = clientes por onde passa o camião 
        camiao = matrix(data=deposito[it_cam,], nrow=1, ncol=nr_alelos);
        
        # Ficar com o percurso efetivamente possivel de desempenhar por um camião 
        percurso = intersect(camiao, c(1:nr_clientes))
        
        # Se length(percurso == 0) o camião não é utilizado -> não trás custos 
        if(length(percurso!=0)){
          # opening cost of a route (cost of a vehicle)
          custo = custo + vehicle_cost
          used_camioes[it_dep] = used_camioes[it_dep] + 1; 
          
          # Se 1 camião é usado, o stock do deposito baixa 
          stock_atual = stock_atual - vehicle_cap
          
          # Carga inicial do camião no inicio do percurso
          carga_atual = vehicle_cap 
          
          for(cli in c(1:length(percurso))){
            cliente = percurso[cli]
            
            # senão, vamos satisfazer o máximo de procura do cliente 
            if(carga_atual >= procura[cliente]){
              # só se consideram entregas completas da procura do cliente
              entregar = procura[cliente] 
              carga_atual = carga_atual - entregar; # reduzir capacidade disponivel do camião
              procura[cliente] = 0 # reduzir a procura do cliente 
            }else{
              # passamos por um cliente sem stock no camião para o atender
              # aumentar absurdamente o custo da solução, para penalizar
              custo = custo + 10000000000
            }
          }
          
          # Calcular o custo de uma viagem 
          c_viagem <- custo_viagem(depositosDistances, customerDistances, percurso)
          custo = custo + c_viagem
        }
      }
    }
  }
  if(sum(procura)!=0){  
    custo = custo + 10000000000 # o genoma nao satisfaz a procura de todo os cliente
  }
  return(list(custo, used_depositos, used_camioes))
} # Fim da função que calcula o custo de um genoma ("cromossoma" com vários cromossomas referentes a N depositos)
#----------------------------------------------------------------------------------------------------------------
#----------------------------------------------------------------------------------------------------------------
# Função para gerar grafo com todas as rotas, clientes e depositos 
visualizacaoRotas <- function(res){ 
  
  # Todos os pontos (distancias clientes +  depositos)
  mds <- cmdscale(auxiliarPlot)
  x <- mds[, 1];    
  y <- mds[, 2];
  plot(x,y, type="n", asp=1, xlab="", ylab="", main="Tour after GA converted")
  abline(h = pretty(range(x), 10), v = pretty(range(y), 10), col="lightgrey")
  
  # Array de cores -> cada deposito terá uma cor 
  cores_dep = c("chartreuse", "chocolate1", "cyan", "darkorange", "darkolivegreen1", "darkviolet", "gold", "yellow", "gold1", "gray33")
  
  tours <- res@solution[1, ]
  cromossomas = matrix(data=tours, nrow=nr_depositos, ncol=nr_camioes*nr_alelos, byrow=TRUE);
  for(it_dep in c(1:nrow(cromossomas))){
    # teste inicial para saber se o deposito é aberto 
    deposito = intersect(cromossomas[it_dep,], c(1:nr_clientes));
    if(length(deposito)==0){
        cat("Deposito ", it_dep, " não é aberto.\n")
    }else{ 
    
        # Dividir as infos do deposito por cada camião 
        deposito = matrix(data=cromossomas[it_dep,], nrow=nr_camioes, ncol=nr_alelos)  
        
        c = 1 # contador para atribuir um id aos camioes utilizados 
        
        cat("Deposito ", it_dep, ": \n") # imprimir o numero do deposito em análise 
        
        for(it_cam in c(1:nrow(deposito))){
          # Obter os alelos de um gene = clientes por onde passa o camião 
          camiao = matrix(data=deposito[it_cam,], nrow=1, ncol=nr_alelos);
          
          # Ficar com o percurso efetivamente possivel de desempenhar por um camião 
          percurso = intersect(camiao, c(1:nr_clientes))
          
          if(length(percurso)!=0){
            cat("\tCamião ", c, " realiza o percurso pelos clientes: ", percurso, "\n")
            c <- c+1 # se um camião é usado, incremeta contador de camiões usados pelo deposito i 
            
            tour <- c(nr_clientes + it_dep, percurso) # truque para adicionar o deposito no percurso
            tour <- c(tour, tour[1]) # adicionar o regresso ao ponto partida (deposito)
            n <- length(tour)
            
            i_cor = it_dep %% length(cores_dep) # escolher uma cor que varie por deposito 
            # cada percurso de um camião tem uma linha diferente e recebe a cor do deposito i 
            arrows(x[tour[-n]], y[tour[-n]],x[tour[-1]], y[tour[-1]], length = 0.15, angle=45, col=cores_dep[i_cor], lty=it_cam%%6, lwd=2)
            
          }
        }
    }
  }
  
  # Marcar clientes no mapa
  mds <- cmdscale(auxiliarPlot)
  x <- mds[1:nr_clientes, 1];    
  y <- mds[1:nr_clientes, 2];      
  n <- length(x)
  points(x,y,pch=15, cex=1.5, col="blue")
  
  # marcar depositos no mapa 
  x <- mds[nr_clientes:ncol(auxiliarPlot), 1];    
  y <- mds[nr_clientes:ncol(auxiliarPlot), 2];      
  n <- length(x)
  points(x,y,pch=17, cex=1.5, col="red")
  
  #Marcar legenda no mapa 
  mds <- cmdscale(auxiliarPlot)
  x <- mds[, 1];    
  y <- mds[, 2];
  text(x, y, labels(auxiliarPlot[1,]), pos=3, cex=0.8) 
} # Fim da função de visualização dos percursos de uma solução 
#----------------------------------------------------------------------------------------------------------------

#----------------------------------------------------------------------------------------------------------------
# Loas das variaveis necessárias
# As matriz principais são passadas como argumento ao metodo GA e fitness
# outras variaveis assumen-se carregadas em memória --> Daí este scrip com nomes especificos
load_variaveis <- function(){
  cli_Dist = as.matrix(customerDistances);
  cli_Demand = as.matrix(customerDemand, nrow=1);
  cli_Depots_Dist = as.matrix(depositosDistances);
  nr_clientes = ncol(cli_Demand)
  nr_depositos = ncol(depositosStock)
  nr_camioes = ceiling(sum(cli_Demand) / vehicle_cap) 
  mean_demand = mean(cli_Demand)
  nr_alelos = ceiling(vehicle_cap / mean_demand ) + 1
}
#----------------------------------------------------------------------------------------------------------------

#----------------------------------------------------------------------------------------------------------------
# Função para calcular o custo de 1 percurso, com inicio e regresso ao deposito. 
# Assume o custo por km = 1U.M. 
custo_viagem <- function(depositosDistances, customerDistances, percurso, deposito){
  custo = 0
  
  # Inicio: custo do deposito i ao 1º cliente visitado
  custo = custo + depositosDistances[percurso[1], deposito]
  
  # Rota: Calcular o custo por km percorrido na rota de 1 camião
  percurso = c(percurso, percurso[1])
  rota = embed(percurso, 2)[,2:1]
  custo = custo + sum(customerDistances[rota])
  
  # Regresso: custo de regressar do último cliente ao deposito i
  custo = custo + depositosDistances[percurso[length(percurso)], deposito]
  
  return(custo)
} # Fim da função que calcula custo do percurso 
#----------------------------------------------------------------------------------------------------------------


#----------------------------------------------------------------------------------------------------------------
# função para calcular o custo de um genoma (conjunto de cromossomas referentes a N depósitos)
CustoTotal <- function(genoma, customerDistances, customerDemand, depositosDistances) {
  # Cada cromossoma representa um deposito, que pode ou nao ser aberto
  cromossomas = matrix(data=genoma, nrow=nr_depositos, ncol=nr_camioes*nr_alelos, byrow=TRUE);
  
  # custo inicial da solução é zero
  custo = 0;
  
  # vetor com a procura de cada client. No final, a soma deve ser zero (toda a procura satisfeita)
  procura = cli_Demand
  
  # Primeiro ciclo divide o genoma em cromossomas
  # - Computar cada um dos depositos (cromossomas)
  for(it_dep in c(1:nrow(cromossomas))){
    # Teste inicial para saber se o deposito é ou não aberto 
    # - Olha para os dados do deposito sem considerar as divisões por camião
    # - Fica apenas com os valores do cromossoma entre [1:nr_clientes]
    # - Se length(deposito == 0) nenhum camião do deposito é utilizado -> o deposito nao é aberto
    deposito = intersect(cromossomas[it_dep,], c(1:nr_clientes));
    
    if(length(deposito)!=0){ # O deposito é aberto -> terá influencia no custo 
      # opening costs for the depots
      custo = custo + depot_cost[it_dep]
      
      # stock inicial do deposito 
      stock_atual = depositosStock[it_dep] # 
      
      # Dividir as infos do deposito por cada camião 
      deposito = matrix(data=cromossomas[it_dep,], nrow=nr_camioes, ncol=nr_alelos)  
      
      # Segundo ciclo divide o cromossoma em genes
      # - Computar cada um dos camiões (gene) do deposito i
      for(it_cam in c(1:nrow(deposito))){
        # Obter os alelos de um gene = clientes por onde passa o camião 
        camiao = matrix(data=deposito[it_cam,], nrow=1, ncol=nr_alelos);
        
        # Ficar com o percurso efetivamente possivel de desempenhar por um camião 
        percurso = intersect(camiao, c(1:nr_clientes))
        
        # Se length(percurso == 0) o camião não é utilizado -> não trás custos 
        if(length(percurso!=0)){
          # opening cost of a route (cost of a vehicle)
          custo = custo + vehicle_cost
          
          # Se 1 camião é usado, o stock do deposito baixa 
          stock_atual = stock_atual - vehicle_cap
          
          # Carga inicial do camião no inicio do percurso
          carga_atual = vehicle_cap 
          
          for(cli in c(1:length(percurso))){
            cliente = percurso[cli]
            
            # senão, vamos satisfazer o máximo de procura do cliente 
            if(carga_atual >= procura[cliente]){
              # só se consideram entregas completas da procura do cliente
              entregar = procura[cliente] 
              carga_atual = carga_atual - entregar; # reduzir capacidade disponivel do camião
              procura[cliente] = 0 # reduzir a procura do cliente 
            }else{
              # passamos por um cliente sem stock no camião para o atender
              # aumentar absurdamente o custo da solução, para penalizar
              custo = custo + 10000000000
            }
          }
          
          # Calcular o custo de uma viagem 
          c_viagem <- custo_viagem(depositosDistances, customerDistances, percurso)
          custo = custo + c_viagem
        }
      }
    }
  }
  if(sum(procura)!=0){  
    return(custo = custo + 10000000000) # o genoma nao satisfaz a procura de todo os cliente
  }else{ 
    return(custo)
    #custo
  }
} # Fim da função que calcula o custo de um genoma ("cromossoma" com vários cromossomas referentes a N depositos)
#----------------------------------------------------------------------------------------------------------------

#----------------------------------------------------------------------------------------------------------------
# Fitness function to be minimized 
lrpFitness <- function(genoma, customerDistances, customerDemand, depositosDistances) {
  1 / CustoTotal(genoma, customerDistances, customerDemand, depositosDistances)
}
#----------------------------------------------------------------------------------------------------------------

#----------------------------------------------------------------------------------------------------------------
# Função para realizar a computação do algoritmo genético. 
run_GA <- function(){
  
  # nr_camioes previamente optimizado para o número minimo suficiente 
  sizeGenoma = nr_depositos * nr_camioes * nr_alelos
  
  GA <- ga(type = "permutation", fitness = lrpFitness,
           customerDistances=customerDistances, customerDemand=customerDemand, depositosDistances=depositosDistances,
           min = 1, max = sizeGenoma, popSize = 75, 
           maxiter = 200, run = 500, 
           pmutation = 0.3, pcrossover =0.85, elitism = 0.2, monitor = FALSE)
  return(GA)
}
#----------------------------------------------------------------------------------------------------------------

#summary(GA)

custo_solucao <- function(GA){
    custo = 0
    best_tour = GA@solution[1,]
    custo <- CustoTotal(genoma = best_tour, customerDistances, customerDemand, depositosDistances)
    #cat("custo solução = ", (custo), "\n" )
    return(custo[1])
}


#foldersName = c('~/GitHub/cn3/data/json/barreto_json/') 
#foldersName = c('~/GitHub/cn3/data/json/prodhon_json/')  
foldersName = c('~/GitHub/cn3/data/json/tuzun_json/')
    
#for(folderName in foldersName){
for(f in c(1:length(foldersName))){
  files <- list.files(foldersName[f], full.names=TRUE)
  files <- files[ grepl("\\.[json]", files) ]
      
  split_names = strsplit(foldersName[f], "/")
  folder_name = split_names[[1]][length(split_names[[1]])]
  cat("Folder name:\t", folder_name, "\n")
  
  resultados <- matrix(nrow=length(files)+1, ncol=10)
  resultados[1,] = c("File Name", "Nr Clientes", "Nr Depots", "Nr Camioes", "Nr Alelos", "Size genoma", "Tempo (min)", "Custo (U.M.)", "Nr Used Depots", "Nr Used camioes per depot")

  for (it_files in 1:length(files)){
    
    json <- fromJSON(files[it_files])
    #fileaux = '~/GitHub/cn3/data/json/prodhon_json/coord200-10-1.json'
    #json <- fromJSON(fileaux)
    
    nr_clientes = json$meta_data$nb_customers
    nr_depositos = json$meta_data$nb_depots
    vehicle_cap = json$meta_data$vehicle_cap
    vehicle_cost = json$meta_data$vehicle_cost
    cost_type = json$meta_data$cost_type
    clientes = json$customers
    depositos = json$depots
    
    customerDistances = matrix(nrow=nr_clientes, ncol = nr_clientes)
    customerDemand = matrix(ncol=nr_clientes, nrow=1)
    depositosDistances = matrix(nrow = nr_clientes, ncol = nr_depositos)
    auxiliarDepositos = matrix(nrow = nr_depositos, ncol = nr_depositos)
    auxiliarPlot = matrix(nrow = nr_clientes + nr_depositos, ncol = nr_clientes + nr_depositos)
    depositosStock = matrix(ncol=nr_depositos, nrow=1)
    depot_cost = matrix(ncol=nr_depositos, nrow=1)
    
    #calculate clientes distances from c0 -> c49
    
    i=1
    cli_pos = matrix(nrow=nr_clientes, ncol=2, byrow = TRUE)
    for(c1 in clientes){
      cli_pos[i, 1] = c1$x
      cli_pos[i, 2] = c1$y
      i = i+1
    }
    
    i = 1
    depot_pos = matrix(nrow=nr_depositos, ncol=2, byrow = TRUE)
    for(depot in depositos){
      depot
      depositosStock[i] = depot$capacity
      depot_cost[i] = depot$opening_cost
      depot_pos[i,1] = depot$x
      depot_pos[i,2] = depot$y
      i = i+1
    }
    
    i=1
    j=1
    
    for (c1 in clientes){
      x1 = c1$x;
      y1 = c1$y;
      customerDemand[i] = c1$demand;
      for (c2 in clientes){
        x2 = c2$x;
        y2 = c2$y;
        d = sqrt((x1 - x2)^2 + (y1-y2)^2);
        customerDistances[i,j] = d;
        customerDistances[j,i] = d;
        auxiliarPlot[i,j] = d;
        auxiliarPlot[j,i] = d;
        j = j+1;
      }
      j=1
      i = i+1;
    }
    
    i=1
    j=1
    for (c in clientes){
      x1 = c$x;
      y1 = c$y;
      for (d in depositos){
        x2 = d$x;
        y2 = d$y;
        distance = sqrt((x1 - x2)^2 + (y1-y2)^2);
        depositosDistances[i,j] = distance;
        auxiliarPlot[i, nr_clientes + j] = distance; 
        auxiliarPlot[nr_clientes + j,i] = distance; 
        j = j+1;
      }
      j=1
      i = i+1;
    }
    
    i=1
    j=1
    for (d1 in depositos){
      x1 = d1$x;
      y1 = d1$y;
      for (d2 in depositos){
        x2 = d2$x;
        y2 = d2$y;
        distance = sqrt((x1 - x2)^2 + (y1-y2)^2);
        auxiliarDepositos[i,j] = distance;
        auxiliarDepositos[j,i] = distance;
        auxiliarPlot[nr_clientes + i, nr_clientes + j] = distance;
        auxiliarPlot[nr_clientes + j, nr_clientes + i] = distance;
        j = j+1;
      }
      j=1
      i = i+1;
    }
    
    #auxiliarPlot[nr_clientes:nrow(auxiliarPlot), nr_clientes:nrow(auxiliarPlot)]
    
    remove(i,j, x1, x2, y1, y2, distance, c, c1, c2, d, d1, d2, depot)
    remove(clientes, depositos, json)
    # end of read_data_from_Json
  
  
    #load_variaveis()
    cli_Dist = as.matrix(customerDistances);
    cli_Demand = as.matrix(customerDemand, nrow=1);
    cli_Depots_Dist = as.matrix(depositosDistances);
    nr_clientes = ncol(cli_Demand)
    nr_depositos = ncol(depositosStock)
    nr_camioes = ceiling(sum(cli_Demand) / vehicle_cap) 
    mean_demand = ceiling(mean(cli_Demand))
    nr_alelos = floor(vehicle_cap / mean_demand ) 
    sizeGenoma = nr_depositos * nr_camioes * nr_alelos
    
    start <- Sys.time()
    res <- run_GA()
    taken <- round((Sys.time() - start), 2) # end time after runing GA 
    
    best_tour = res@solution[1,]
    detailhes = detalhesSolucao(best_tour, customerDistances, customerDemand,depositosDistances)
    custo = detailhes[[1]][1]
    used_depositos = detailhes[[2]]
    used_camioes = paste(detailhes[[3]], collapse = " ")
    split_names = strsplit(files[it_files], "/")
    file_name = split_names[[1]][length(split_names[[1]])]
    
    resultados[it_files+1,] = c(file_name, nr_clientes, nr_depositos, nr_camioes, nr_alelos, sizeGenoma, round(taken,2), round(custo,2), used_depositos, used_camioes)
    cat("\t", file_name, ": nr clientes: ", nr_clientes, ", nr depositos: ", nr_depositos, ", nr camioes: ", nr_camioes, "nr alelos: ", nr_alelos, "\n", sep="")
    cat("\t Tamanho cromossoma", sizeGenoma, "\tTempo:", taken, "min\t Custo:", round(custo,2), "U.M\t Depósitos", used_depositos, "\t Veiculos:", used_camioes, "\n")
  }
  
  write.csv(resultados, file ="./tunzun_testar.csv", row.names=FALSE)
}

