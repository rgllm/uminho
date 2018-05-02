library(GA)

nr_clientes = 50; #length(cli_Demand)
nr_camioes = 5 #ncol(cli_Depots_Dist) # pior cenário há tantos quantos depositos. 
cli_Dist = as.matrix(customerDistances);
cli_Demand = as.matrix(customerDemand);
cli_Depots_Dist = as.matrix(depotsDistances);

#vehicle_cap
#vehicle_cost 
#cost_type
#customers

#Function to calculate tour length 
tourLength <- function(tour, cli_Dist, cli_Demand, cli_Depots_Dist) {
  
  # reshape do cromossoma numa matrix
  # Cada linha define o percurso de um camião
  percursos = matrix(data=tour,nrow=nr_camioes, ncol =nr_clientes)
  
  custo = 0
  
  # array para saber se a solução visita todos os clientes
  cli_visitados = matrix(0, ncol = nr_clientes, nrow = 1)
  
  for (i in c(1:nrow(percursos))){
    # Se não está tudo a zero, é pq há percurso no camião i 
    if(sum(percursos[i,]) != 0){
      percurso = percursos[i,]
      
      # Marcar quais os clientes visitados 
      for (p in c(1:ncol(percursos))){
        if(percurso[p]!= 0){
          cli_visitados[p] = 1; 
        }
      }
      
      # custo base de deslocar um camião 
      custo = custo + vehicle_cost
      
      # Inicio: custo do deposito i ao 1º cliente visitado
      custo = custo + cli_Depots_Dist[i,1]
        
      # Rota: Calcular o custo por km percorrido na rota de 1 camião
      percurso = c(percurso, percurso[1])
      rota = embed(percurso, 2)[,2:1]
      custo = custo + sum(cli_Dist[rota])
      
      # Regresso: custo de regressar do último cliente ao deposito i
      custo = custo + cli_Depots_Dist[i,length((cli_Depots_Dist))]
    }
  }
  
  if(sum(cli_visitados) != nr_clientes){
    return(1000000) 
  }else return(-custo)
}

#Firness function to be maximized
tspFitness <- function(tour, ...) 1/tourLength(tour, ...)
cli_Depots_Dist[3,]
GA <- ga(type = "permutation", fitness = tspFitness,
         cli_Dist=cli_Dist, cli_Demand=cli_Demand, cli_Depots_Dist=cli_Depots_Dist,
         nBits = nr_camioes*nr_clientes,
         min = 1, max = nr_camioes*nr_clientes, popSize = 50, maxiter = 150,
         run = 500, pmutation = 0.2)

summary(GA)

tour = GA@solution[1,]
tour <- c(tour, tour[1])
route <- embed(tour, 2)[,2:1]
cat("custo = ", sum(cli_Dist[route]))

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



