library(GA)

cli_Dist = as.matrix(customerDistances);
cli_Demand = as.matrix(customerDemand);
cli_Depots_Dist = as.matrix(depotsDistances);
nr_clientes = ncol(cli_Demand)
nr_camioes = nr_clientes # pior cenário há tantos quantos depositos. 
nr_depositos = ncol(depositosStock)

# Função para calcular o custo por kms percorrido num dado percurso
custo_viagem <- function(cli_Depots_Dist, cli_Dist, percurso){
  custo = 0
  
  # Inicio: custo do deposito i ao 1º cliente visitado
  custo = custo + cli_Depots_Dist[1, i]
  
  # Rota: Calcular o custo por km percorrido na rota de 1 camião
  percurso = c(percurso, percurso[1])
  rota = embed(percurso, 2)[,2:1]
  custo = custo + sum(cli_Dist[rota])
  
  # Regresso: custo de regressar do último cliente ao deposito i
  custo = custo + cli_Depots_Dist[nrow(cli_Depots_Dist), i]
  
  return(custo)
}

#Function to calculate tour length 
tourLength <- function(tour, cli_Dist, cli_Demand, cli_Depots_Dist) {
  
  # reshape do cromossoma numa matrix
  # Cada linha define o percurso de um camião
  percursos = matrix(data=tour,nrow=nr_camioes, ncol =nr_clientes)
  
  custo = 0
  
  # array para saber se a solução visita todos os clientes
  cli_visitados = matrix(0, ncol = nr_clientes, nrow = 1)
  
  # vetor com a procura de cada client. No final, a soma deve ser zero (toda a procura satisfeita)
  procura = cli_Demand
  
  for (i in c(1:nrow(percursos))){
    # Ficar apenas com os valores do cromossoma entre [1: nr_cliente ]
    percurso = intersect(percursos[i,], c(1:nr_clientes))
    
    carga = vehicle_cap # Carga inicial do camião no inicio do percurso
    stock = depositosStock[i] # 
    
    # Marcar quais os clientes visitados 
    for (p in c(1:length(percurso))){
      # O camião faz um percurso por clientes, sem carga para os satisfazer até ao final, 
      # mesmo reabastecendo no stock do deposito 
      if((carga==0)&& (stock==0) && (p!=length(percurso))){  return(0)  }
      # cliente já satisfeito por outros camiões. Não se justifica vir até ele se a sua procura está a 0. 
      if(procura[p] == 0 ){ return(0)}
      
      if(carga >= procura[p]){
        entregar = procura[p] # entregar tudo de uma vez, pq ainbda há carga no camião
      }else{
        entregar = carga # entregar só o que temos 
      }
      carga = carga - entregar; # reduzir capacidade do camião
      procura[p] = procura[p] - entregar # reduzir a procura do cliente 
      cli_visitados[p] = 1; 
    }
    
    # custo base de deslocar um camião 
    custo = custo + vehicle_cost
  
    # Calcular o custo de uma viagem 
    #c_viagem <- custo_viagem(cli_Depots_Dist, cli_Dist, percurso)
    #custo = custo + c_viagem
  }

  # Se não se visitaram todos os clientes OU a procura não foi satisfeita, a solução nao é valida
  if((sum(cli_visitados)!=nr_clientes) || (sum(procura[p])!=0)){  return(0) }
  else{ return(-custo) }
  
}

#Firness function to be maximized
tspFitness <- function(tour, ...) tourLength(tour, ...)

GA <- ga(type = "permutation", fitness = tspFitness,
         cli_Dist=cli_Dist, cli_Demand=cli_Demand, cli_Depots_Dist=cli_Depots_Dist,
         nBits = nr_*nr_clientes,
         min = 1, max = nr_camioes*nr_clientes, popSize = 50, maxiter = 500,
         run = 500, pmutation = 0.2)

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



