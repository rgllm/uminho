library(GA)

cli_Dist = as.matrix(customerDistances);
cli_Demand = as.matrix(customerDemand, nrow=1);
cli_Depots_Dist = as.matrix(depositosDistances);
nr_clientes = ncol(cli_Demand)
nr_depositos = ncol(depositosStock)
nr_camioes = round(sum(cli_Demand) / vehicle_cap, 0) 

# Função para calcular o custo por kms percorrido num dado percurso
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
}

#Function to calculate tour length 
CustoTotal <- function(genoma, customerDistances, customerDemand, depositosDistances) {
  # Cada cromossoma representa um deposito, que pode ou nao ser aberto
  cromossomas = matrix(data=genoma, nrow=nr_depositos, ncol=nr_camioes*nr_clientes, byrow=TRUE);
  
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
      deposito = matrix(data=cromossomas[it_dep,], nrow=nr_camioes, ncol=nr_clientes)  
    
      # Segundo ciclo divide o cromossoma em genes
      # - Computar cada um dos camiões (gene) do deposito i
      for(it_cam in c(1:nrow(deposito))){
        # Obter os alelos de um gene = clientes por onde passa o camião 
        camiao = matrix(data=deposito[it_cam,], nrow=1, ncol=nr_clientes);
        
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
}

#Firness function to be minimized 
tspFitness <- function(genoma, customerDistances, customerDemand, depositosDistances) {
  1 / CustoTotal(genoma, customerDistances, customerDemand, depositosDistances)
}


sizeGenoma = nr_depositos * nr_camioes * nr_clientes
GA <- ga(type = "permutation", fitness = tspFitness,
         customerDistances=customerDistances, customerDemand=customerDemand, depositosDistances=depositosDistances,
         min = 1, max = sizeGenoma, popSize = 100, 
         maxiter = 500, run = 500, 
         pmutation = 0.3, pcrossover =0.2, elitism = 0.2)

summary(GA)

custo = 0
tours = GA@solution
for(g in c(1:nrow(tours))){
  custo <- CustoTotal(genoma = tours[g,], customerDistances, customerDemand, depositosDistances)
  cat("custo solução ", g, " = ", (custo), "\n" )
}

#Visualization 
visualizacao <- function(){ 
  
  # Todos os pontos 
  mds <- cmdscale(auxiliarPlot)
  x <- mds[, 1];    
  y <- mds[, 2];
  plot(x,y, type="n", asp=1, xlab="", ylab="", main="Tour after GA converted")
  abline(h = pretty(range(x), 10), v = pretty(range(y), 10), col="lightgrey")
  
  # imprimir as rotas 
  cores_dep = c("chartreuse", "chocolate1", "cyan", "darkorange", "darkolivegreen1", "darkviolet", "gold", "yellow", "gold1", "gray33")
  
  tours <- GA@solution[1, ]
  cromossomas = matrix(data=tours, nrow=nr_depositos, ncol=nr_camioes*nr_clientes, byrow=TRUE);
  for(it_dep in c(1:nrow(cromossomas))){
      # Dividir as infos do deposito por cada camião 
      deposito = matrix(data=cromossomas[it_dep,], nrow=nr_camioes, ncol=nr_clientes)  
      c = 1
      cat("Deposito ", it_dep, ": \n")
      for(it_cam in c(1:nrow(deposito))){
        # Obter os alelos de um gene = clientes por onde passa o camião 
        camiao = matrix(data=deposito[it_cam,], nrow=1, ncol=nr_clientes);
        
        # Ficar com o percurso efetivamente possivel de desempenhar por um camião 
        percurso = intersect(camiao, c(1:nr_clientes))
        if(length(percurso)!=0){
          cat("\tCamião ", c, " realiza o percurso pelos clientes: ", percurso, "\n")
          c <- c+1
          
          # truque para aparecer o deposito no percurso
          tour <- c(nr_clientes + it_dep, percurso)
          tour <- c(tour, tour[1]) # adicionar o regresso ao ponto partida
          n <- length(tour)
          arrows(x[tour[-n]], y[tour[-n]],x[tour[-1]], y[tour[-1]], length = 0.15, angle=45, col=cores_dep[it_dep], lty=it_cam%%6, lwd=2)
          
          text(x, y, labels(auxiliarPlot[1,]), pos=3, cex=0.8)
        }
      }
  }
  
  # Marcar clientes no mapa
  mds <- cmdscale(auxiliarPlot)
  x <- mds[1:nr_clientes, 1];    
  y <- mds[1:nr_clientes, 2];      
  n <- length(x)
  points(x,y,pch=15, cex=1.5, col="blue")
  
  x <- mds[nr_clientes:ncol(auxiliarPlot), 1];    y <- mds[nr_clientes:ncol(auxiliarPlot), 2];      n <- length(x)
  points(x,y,pch=17, cex=1.5, col="red")
}

x <- visualizacao()
