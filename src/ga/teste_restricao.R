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
      #return(0)  
      cat("Falha1")
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
      #return(0)  
      cat("Falha2: ", procura[p], ", p=", p, "\n")
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
  #return(0)  
  cat("Falha3")
}else{ 
  #return(-custo)
  custo
}