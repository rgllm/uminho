# Import das bibliotecas necessárias
library("neuralnet")
library("hydroGOF")
library("arules")

# Leitura do ficheiro de dados
dataset <- read.csv("C:\\Users\\perei\\Desktop\\exaustao-norm-rand.csv", header=TRUE, sep=";", dec=".")

# Discretização dos valores dos atributos  -- Para o relatório 
#dataset$Performance.KDTMean <- discretize(dataset$Performance.KDTMean,method="frequency", categories=10)
#dataset$Performance.MAMean <- discretize(dataset$Performance.MAMean,method="frequency", categories=10)
#dataset$Performance.MVMean <- discretize(dataset$Performance.MVMean,method="frequency", categories=10)
#dataset$Performance.TBCMean <- discretize(dataset$Performance.TBCMean,method="frequency", categories=10)
#dataset$Performance.DDCMean <- discretize(dataset$Performance.DDCMean,method="frequency", categories=10)
#dataset$Performance.ADMSLMean <- discretize(dataset$Performance.ADMSLMean,method="frequency", categories=10)        
#dataset$Performance.DMSMean <- discretize(dataset$Performance.DMSMean,method="frequency", categories=10)
#dataset$Performance.AEDMean <- discretize(dataset$Performance.AEDMean,method="frequency", categories=10)
#write.csv(dataset, "exaustao-discretizada.csv")

# Divisão dos dados para aprendizagem em vários conjuntos com dimensões distintas
dados1 <- dataset[1:600,] # 633 registos
dados2 <- dataset[1:422,] # 422 registos
dados3 <- dataset[1:211,] # 211 registos
dados4 <- dataset[1:140,] # 140 registos

# Divisão dos dados para aprendizagem em vários conjuntos com dimensões distintas
treino1 <- dataset[601:844,] # 211 registos
treino2 <- dataset[423:844,] # 422 registos
treino3 <- dataset[212:844,] # 634 registos
treino4 <- dataset[141:844,] # 704 registos


sub41 <-subset(treino1, select=c("Performance.Task","Performance.DDCMean","Performance.MAMean","Performance.MVMean","FatigueLevel"))
sub42 <-subset(treino2, select=c("Performance.Task","Performance.DDCMean","Performance.MAMean","Performance.MVMean","FatigueLevel"))

sub51 <-subset(treino1, select=c("Performance.Task","Performance.DDCMean","Performance.MAMean","Performance.MVMean","Performance.KDTMean","FatigueLevel"))
sub52 <-subset(treino2, select=c("Performance.Task","Performance.DDCMean","Performance.MAMean","Performance.MVMean","Performance.KDTMean","FatigueLevel"))

sub61 <-subset(treino1, select=c("Performance.Task","Performance.DDCMean","Performance.MAMean","Performance.MVMean","Performance.DMSMean","Performance.ADMSLMean","FatigueLevel"))
sub62 <-subset(treino2, select=c("Performance.Task","Performance.DDCMean","Performance.MAMean","Performance.MVMean","Performance.DMSMean","Performance.ADMSLMean","FatigueLevel"))

sub71 <-subset(treino1, select=c("Performance.Task","Performance.DDCMean","Performance.MAMean","Performance.MVMean","Performance.DMSMean","Performance.ADMSLMean","Performance.KDTMean","FatigueLevel"))
sub72 <-subset(treino2, select=c("Performance.Task","Performance.DDCMean","Performance.MAMean","Performance.MVMean","Performance.DMSMean","Performance.ADMSLMean","Performance.KDTMean","FatigueLevel"))

sub81 <-subset(treino1, select=c("Performance.Task","Performance.DDCMean","Performance.MAMean","Performance.MVMean","Performance.DMSMean","Performance.ADMSLMean","Performance.KDTMean","Performance.TBCMean","FatigueLevel"))
sub82 <-subset(treino2, select=c("Performance.Task","Performance.DDCMean","Performance.MAMean","Performance.MVMean","Performance.DMSMean","Performance.ADMSLMean","Performance.KDTMean","Performance.TBCMean","FatigueLevel"))

# Diferentes camadas intermédias
hidden1 <- c(60, 40, 20)
hidden2 <- c(120, 80, 40)
hidden3 <- c(6, 4, 2)
hidden4 <- c(10,5)
hidden5 <- c(20)

# Diferentes tolerâncias (thresholds)
threshold1 <- 0.01
threshold2 <- 0.1

# Diferentes fórmulas
formula1 <- FatigueLevel ~ Performance.KDTMean+Performance.MAMean+Performance.MVMean+Performance.TBCMean+Performance.DDCMean+Performance.DMSMean+Performance.AEDMean+Performance.ADMSLMean+Performance.Task
formula4NNR <- FatigueLevel ~ Performance.Task+Performance.DDCMean+Performance.MAMean+Performance.MVMean
formula5NNR <- FatigueLevel ~ Performance.Task+Performance.DDCMean+Performance.MAMean+Performance.MVMean+Performance.KDTMean
formula6NNR <- FatigueLevel ~ Performance.Task+Performance.DDCMean+Performance.MAMean+Performance.MVMean+Performance.DMSMean+Performance.ADMSLMean
formula7NNR <- FatigueLevel ~ Performance.Task+Performance.DDCMean+Performance.MAMean+Performance.MVMean+Performance.DMSMean+Performance.ADMSLMean+Performance.KDTMean
formula8NNR <- FatigueLevel ~ Performance.Task+Performance.DDCMean+Performance.MAMean+Performance.MVMean+Performance.DMSMean+Performance.ADMSLMean+Performance.KDTMean+Performance.TBCMean

formulaTask <- Performance.Task ~ Performance.KDTMean+Performance.MAMean+Performance.MVMean+Performance.TBCMean+Performance.DDCMean+Performance.DMSMean+Performance.AEDMean+Performance.ADMSLMean+FatigueLevel

formula2NR <- FatigueLevel ~ Performance.Task+Performance.MAMean+Performance.KDTMean+Performance.DMSMean
formula3NR <- FatigueLevel ~ Performance.Task+Performance.MAMean+Performance.KDTMean+Performance.DMSMean+Performance.DDCMean
formula4NR <- FatigueLevel ~ Performance.Task+Performance.MAMean+Performance.KDTMean+Performance.DMSMean+Performance.DDCMean+Performance.AEDMean
formula5NR <- FatigueLevel ~ Performance.Task+Performance.MAMean+Performance.KDTMean+Performance.DMSMean+Performance.DDCMean+Performance.AEDMean+Performance.TBCMean
formula6NR <- FatigueLevel ~ Performance.Task+Performance.MAMean+Performance.KDTMean+Performance.DMSMean+Performance.DDCMean+Performance.AEDMean+Performance.TBCMean+Performance.ADMSLMean

formula7NR <- FatigueLevel ~ Performance.Task+Performance.DDCMean+Performance.MAMean+Performance.MVMean
formula8NR <- FatigueLevel ~ Performance.Task+Performance.DDCMean+Performance.MAMean+Performance.MVMean+Performance.KDTMean
formula9NR <- FatigueLevel ~ Performance.Task+Performance.DDCMean+Performance.MAMean+Performance.MVMean+Performance.DMSMean+Performance.ADMSLMean
formula10NR <- FatigueLevel ~ Performace.Task+Performance.DDCMean+Performance.MAMean+Performance.MVMean+Performance.KDTMean+Performance.DMSMean+Performance.ADMSLMean
formula11NR <- FatigueLevel ~ Performace.Task+Performance.DDCMean+Performance.MAMean+Performance.MVMean+Perfomance.TBCMean+Performance.KDTMean+Performance.ADMSLMean+Performance.DMSMean

sub71NR <- subset(treino1, select=c("Performance.Task","Performance.MAMean","Performance.DDCMean","Performance.MVMean","FatigueLevel"))
sub72NR <- subset(treino2, select=c("Performance.Task","Performance.MAMean","Performance.DDCMean","Performance.MVMean","FatigueLevel"))
sub81NR <- subset(treino1, select=c("Performance.Task","Performance.MAMean","Performance.DDCMean","Performance.MVMean","Performance.KDTMean","FatigueLevel"))
sub82NR <- subset(treino2, select=c("Performance.Task","Performance.MAMean","Performance.DDCMean","Performance.MVMean","Performance.KDTMean","FatigueLevel"))
sub91NR <- subset(treino1, select=c("Performance.Task","Performance.MAMean","Performance.DDCMean","Performance.MVMean","Performance.DMSMean","Performance.ADMSLMean","FatigueLevel"))
sub92NR <- subset(treino2, select=c("Performance.Task","Performance.MAMean","Performance.DDCMean","Performance.MVMean","Performance.DMSMean","Performance.ADMSLMean","FatigueLevel"))

sub1NR <- subset(treino1, select=c("Performance.Task","Performance.MAMean","Performance.KDTMean","Performance.DMSMean","FatigueLevel"))
sub2NR <- subset(treino2, select=c("Performance.Task","Performance.MAMean","Performance.KDTMean","Performance.DMSMean","FatigueLevel"))
sub3NR <- subset(treino1, select=c("Performance.Task","Performance.MAMean","Performance.KDTMean","Performance.DMSMean","Performance.DDCMean","FatigueLevel"))
sub4NR <- subset(treino2, select=c("Performance.Task","Performance.MAMean","Performance.KDTMean","Performance.DMSMean","Performance.DDCMean","FatigueLevel"))
sub5NR <- subset(treino1, select=c("Performance.Task","Performance.MAMean","Performance.KDTMean","Performance.DMSMean","Performance.DDCMean","Performance.AEDMean","FatigueLevel"))
sub6NR <- subset(treino2, select=c("Performance.Task","Performance.MAMean","Performance.KDTMean","Performance.DMSMean","Performance.DDCMean","Performance.AEDMean","FatigueLevel"))
sub7NR <- subset(treino1, select=c("Performance.Task","Performance.MAMean","Performance.KDTMean","Performance.DMSMean","Performance.DDCMean","Performance.AEDMean","Performance.TBCMean","FatigueLevel"))
sub8NR <- subset(treino2, select=c("Performance.Task","Performance.MAMean","Performance.KDTMean","Performance.DMSMean","Performance.DDCMean","Performance.AEDMean","Performance.TBCMean","FatigueLevel"))
sub9NR <- subset(treino1, select=c("Performance.Task","Performance.MAMean","Performance.KDTMean","Performance.DMSMean","Performance.DDCMean","Performance.AEDMean","Performance.TBCMean","Performance.ADMSLMean","FatigueLevel"))
sub10NR <- subset(treino2, select=c("Performance.Task","Performance.MAMean","Performance.KDTMean","Performance.DMSMean","Performance.DDCMean","Performance.AEDMean","Performance.TBCMean","Performance.ADMSLMean","FatigueLevel"))


# Função que determina a existência ou ausência de cansaço/fatiga/exaustão
determinaExaustao <- function(dataset)
{
  # 0 - Não existe exaustão
  # 1 - Existe exaustão
  # Dos níveis 1 a 3, considera-se que não existe exaustão
  # Dos níveis 4 a 7, considera-se que existe exaustão
  
  auxSet <- dataset
  
  # Se (exaustão <= 0.3), então exaustão = 0
  # Se não exaustão = 1
  auxSet$FatigueLevel <- ifelse(auxSet$FatigueLevel <= 0.3, 0, 0.1)
  
  return (auxSet);
}

# Função que determina a exaustão em três níveis distintos
escala3niveis <- function(dataset)
{
  # 0 - Não se encontra exausto (níveis 1, 2 e 3)
  # 0.5 - Encontra-se em risco de exaustão (níveis 4 e 5)
  # 1 - Encontra-se em exaustão (níveis 6 e 7)
  
  auxSet <- dataset
  
  # Se (exaustão >= 0.6), então exaustão = 1
  # Se não { Se (exaustão <= 0.3), então exaustão = 0
  #          Se não exaustão = 0.5
  #        }
  auxSet$FatigueLevel <- ifelse(auxSet$FatigueLevel >= 0.6, 0.2, 
                                ifelse(auxSet$FatigueLevel <= 0.3, 0, 0.1))
  
  return (auxSet)
}

# Função que determina a exaustão em quatro níveis distintos
escala4niveis <- function(dataset)
{
  # 0 - Encontra-se num estado físico e mental excelente (nível 1)
  # 0.3 - Encontra-se num estado normal (níveis 2 e 3)
  # 0.6 - Encontra-se cansado, mas não em exaustão completa (níveis 4 e 5)
  # 1 - Encontra-se num estado de exaustão (níveis 6 e 7)
  
  # Guardam-se os dados numa variável, de modo a não perder os dados originais
  auxSet <- dataset
  
  # Se (exaustão >= 0.6), então exaustão = 1
  # Se não { Se (exaustão >= 0.4), então exaustão = 0.6
  #          Se não { Se (exaustão >= 0.2), então exaustão = 0.3
  #                   Se não exaustão = 0
  #                 }
  #        }
  #
  auxSet$FatigueLevel <- ifelse(auxSet$FatigueLevel >= 0.6, 0.3, 
                                ifelse(auxSet$FatigueLevel >= 0.4, 0.2,
                                       ifelse(auxSet$FatigueLevel >= 0.2, 0.1, 0)))
  
  return (auxSet)
}

# Função que treina a rede com as características fornecidas e testa os dados de treino
exaustao <- function(dados, treino, hidden, formula, t, algoritmo)
{
  # Treino da rede com os parâmetros passados como input
  net <- neuralnet(formula,dados, hidden=hidden, threshold=t, algorithm=algoritmo, lifesign="full", linear.output = FALSE)
  # Falta acrescentar o linear.output
  
  # Guardamos o input de treino numa variável distinta, à qual retiramos o output, de modo a ser possível 
  # testar os dados e, de seguida, compará-los com os originais
  input <- treino
  input$FatigueLevel <- NULL
  
  # Testamos os dados de input com a rede já treinada
  net$res <- compute(net, input)
  
  # Guardamos os resultados obtidos num dataframe
  res <- data.frame(atual = treino$FatigueLevel, prev = net$res$net.result)
  
  # Arredonda-se os resultados obtidos e guarda-se numa nova variável
  net$prev <- round(res$prev)
  # Acrescentar casas decimais??
  
  # Comparação entre output original e output depois do treino
  net$rmse <- rmse(c(treino$FatigueLevel), c(net$prev))
  
  return (net)
}


id7niveis <- function(dataset)
{
  auxSet <- dataset
  
  v <- auxSet$Performance.KDTMean+auxSet$Performance.MAMean+auxSet$Performance.MVMean+auxSet$Performance.TBCMean+auxSet$Performance.DDCMean+auxSet$Performance.DMSMean+auxSet$Performance.AEDMean+auxSet$Performance.ADMSLMean+auxSet$Performance.Task
  
  auxSet$FatigueLevel <- ifelse(v <= 2.34, 0.7,
                                ifelse(v <= 3.28, 0.6,
                                       ifelse(v <= 4.22, 0.5,
                                              ifelse(v <= 5.16, 0.4,
                                                     ifelse(v <= 6.1, 0.3,
                                                            ifelse(v <= 7.04, 0.2, 0.1))))))
  
  return (auxSet)
}


task <- function(dados, treino, hidden, formula, t, algoritmo)
{
  # Treino da rede com os parâmetros passados como input
  net <- neuralnet(formula,dados, hidden=hidden, threshold=t, algorithm=algoritmo, lifesign="full", linear.output = TRUE)
  # Falta acrescentar o linear.output
  
  # Guardamos o input de treino numa variável distinta, à qual retiramos o output, de modo a ser possível 
  # testar os dados e, de seguida, compará-los com os originais
  input <- treino
  input$Performance.Task<- NULL
  
  # Testamos os dados de input com a rede já treinada
  net$res <- compute(net, input)
  
  # Guardamos os resultados obtidos num dataframe
  res <- data.frame(atual = treino$Performance.Task, prev = net$res$net.result)
  
  # Arredonda-se os resultados obtidos e guarda-se numa nova variável
  net$prev <- round(res$prev)
  # Acrescentar casas decimais??
  
  # Comparação entre output original e output depois do treino
  net$rmse <- rmse(c(treino$Performance.Task), c(net$prev))
  
  return (net)
}

qualTarefa <-function(net, kdt, ma, mv, tbc, ddc, dms, aed, admsl, exaustion)
{
  teste1<-data.frame(Performance.KDTMean=kdt,Performance.MAMean=ma, Performance.MVMean=mv,Performance.TBCMean=tbc,
                   Performance.DDCMean =ddc, Performance.DMSMean=dms,Performance.AEDMean=aed, Performance.ADMSLMean=admsl,
                   FatigueLevel=exaustion)
  net.results<-compute(net, teste1)
  print(round(net.results$net.result,digits =1))
}

# Resultados vão ficar armazenados numa variável. De forma a verificar o valor de rmse, executar View(res1$rmse).
res1 <- task(dados2, treino2, hidden1, formulaTask, threshold1, "rprop+")
qualTarefa(res1, 0.1, 0.2, 0.5, 0.2, 0.1, 0.2, 0.3, 0.2, 0.3)

# Resultados vão ficar armazenados numa variável. De forma a verificar o valor de rmse, executar View(res1$rmse).
#r <- id7niveis(dataset)
#dr1 <- r[1:422,]
#tr1 <- r[423:844,]
#dr2 <- subset(dr1, select=c("FatigueLevel","Performance.DMSMean", "Performance.TBCMean", "Performance.DDCMean", "Performance.KDTMean"))
#tr2 <- subset(tr1, select=c("FatigueLevel","Performance.DMSMean", "Performance.TBCMean", "Performance.DDCMean", "Performance.KDTMean"))
#formulaA <- FatigueLevel ~ Performance.DMSMean+Performance.TBCMean+Performance.DDCMean+Performance.KDTMean
#res7 <- exaustao(dr2, tr2, hidden2, formulaA, threshold1, "rprop+")
#res1 <- exaustao(dados2, treino2, hidden5, formula1, threshold1, "rprop+")
#ex <- determinaExaustao(dataset) # Novo dataset com exaustão 0 ou 1
#d1 <- ex[1:422,] # Novos dados de aprendizagem
#t1 <- ex[423:844,] # Novos dados de treino
#res2 <- exaustao(d1, t1, hidden2, formula1, threshold1, "rprop+")
#escala3n <- escala3niveis(dataset)
#escala4n <- escala4niveis(dataset)
#d3 <- escala3n[1:422,]
#t3 <- escala3n[423:844,]
#res3 <- exaustao(d3, t3, hidden2, formula1, threshold1, "rprop+")
#escala4n <- escala4niveis(dataset)
#d4 <- escala4n[1:422,]
#t4 <- escala4n[423:844,]

