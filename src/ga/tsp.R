library(GA)
library(permute)

cli_Dist = as.matrix(customerDistances);
cli_Demand = as.matrix(customerDemand, nrow=1);
cli_Depots_Dist = as.matrix(depotsDistances);
nr_clientes = ncol(cli_Demand)
nr_depositos = ncol(depositosStock)
nr_camioes = nr_depositos # pior cenário há tantos quantos depositos. 

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
tourLength <- function(tour, customerDistances, customerDemand, depositosDistances) {
  tour = c(1:50, 1:50,1:50, 1:50, 1:50)
  tour
  
  # reshape do cromossoma numa matrix
  # Cada linha define o percurso de um camião
  percursos = matrix(data=tour,nrow=nr_camioes, ncol =nr_clientes, byrow = TRUE)
  
  custo = 0
  
  # array para saber se a solução visita todos os clientes
  cli_visitados = matrix(0, ncol = nr_clientes, nrow = 1)
  
  # vetor com a procura de cada client. No final, a soma deve ser zero (toda a procura satisfeita)
  procura = cli_Demand
  
  for (i in c(1:nrow(percursos))){
    # Ficar apenas com os valores do cromossoma entre [1: nr_cliente ]
    percurso = intersect(percursos[i,], c(1:nr_clientes))
    
    carga_atual = vehicle_cap # Carga inicial do camião no inicio do percurso
    stock_atual = depositosStock[i] # 
    
    # Marcar quais os clientes visitados 
    for (p in c(1:length(percurso))){
      # -> o camião passa por clientes, mesmo já tendo esgotado o stock do deposito
      if((carga_atual==0)&& (stock_atual==0) && (p!=length(percurso))){  
        return(0)  
        #cat("Falha1")
      }
      
      # carga do camião = 0 --> recarregar com stock do deposito
      if((carga_atual==0) && (stock_atual>=0)){
        if(stock_atual >= vehicle_cap){
          recarga = vehicle_cap # se houver stock suficiente, atesta o stock do camiao
        }else{
          recarga = stock_atual # senão, careega parcialmente o camião, com o que sobra de stock 
        }
        carga_atual = carga_atual + recarga; 
        stock_atual = stock_atual - recarga;
      }
      
      # cliente já satisfeito por outros camiões. Não se justifica vir até ele se a sua procura está a 0. 
      if(procura[p] == 0 ){ 
        break;
        #return(0)  
        #cat("Falha2")
      }
      
      # senão, vamos satisfazer o máximo de procura do cliente 
      if(carga_atual >= procura[p]){
        entregar = procura[p] # entregar tudo de uma vez, pq ainda há carga no camião
      }else{
        entregar = carga_atual # entregar só o que temos 
      }
      carga_atual = carga_atual - entregar; # reduzir capacidade disponivel do camião
      procura[p] = procura[p] - entregar # reduzir a procura do cliente 
      cli_visitados[p] = 1; 
    }
    
    # opening costs for the depots
    custo = custo + depot_cost
    
    # opening cost of a route (cost of a vehicle)
    custo = custo + vehicle_cost
  
    # Calcular o custo de uma viagem 
    c_viagem <- custo_viagem(depositosDistances, customerDistances, percurso, i)
    custo = custo + c_viagem
  }

  # Se não se visitaram todos os clientes OU a procura não foi satisfeita, a solução nao é valida
  if((sum(cli_visitados)!=nr_clientes) || (sum(procura[p])!=0)){  
    return(0)  
    #cat("Falha3")
  }else{ 
    return(-custo)
    #custo
  }
  
}

#Firness function to be maximized
tspFitness <- function(tour, ...) {
  1 / (0.00001 + tourLength(tour, ...))
}

GA <- ga(type = "permutation", fitness = tspFitness,
         customerDistances=customerDistances, customerDemand=customerDemand, depositosDistances=depositosDistances,
         nBits = nr_camioes*nr_clientes,
         min = 1, max = nr_camioes*nr_clientes, popSize = 100, maxiter = 1000,
         run = 1000, pmutation = 0.2)

summary(GA)

custo = 0
tour = GA@solution[1,]
tour = matrix(data=tour,nrow=nr_camioes, ncol =nr_clientes)
for(i in c(1:nrow(tour))){
  percurso = intersect(tour[i,], c(1:nr_clientes))
  percurso = c(percurso, percurso[1])
  percurso <- embed(percurso, 2)[,2:1]
  custo = custo + sum(custo_viagem(cli_Depots_Dist, cli_Dist, percurso))
  }
cat("custo = ", custo )

#Visualization 

#mds <- cmdscale(eurodist)
#x <- mds[, 1]
#y <- -mds[, 2]
#plot(x, y, type = "n", asp = 1, xlab = "", ylab = "")
#abline(h = pretty(range(x), 10), v = pretty(range(y), 10),
#       col = "light gray")
#tour <- GA@solution[1, ]
#tour <- c(tour, tour[1])
#n <- length(tour)
#arrows(x[tour[-n]], y[tour[-n]], x[tour[-1]], y[tour[-1]],
#       length = 0.15, angle = 25, col = "steelblue", lwd = 2)
#text(x, y, labels(eurodist), cex=0.8)



