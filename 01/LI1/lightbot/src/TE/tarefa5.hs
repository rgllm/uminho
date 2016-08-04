{- |

Module : Tarefa5 - LI1
Description : Módulo Haskell que contém as funções da Tarefa 5 do Projeto de Laboratórios de Informática I.
Copyright: Rogério Moreira <a74634@alunos.uminho.pt>
           Samuel Ferreira <a76507@alunos.uminho.pt>

Módulo da Tarefa 5 que contém as funções que possibilitam que seja gerado código html (com recurso ao X3DOM) de modo a apresentar a animação respetiva aos dados inseridos conforme indicado no eunciado no âmbito do Projeto de Laboratórios de Informática I.

-}

module Main where

import Data.Char
import Data.List
import Data.String 

outStr :: [String] -> String
outStr [] = "\n"
outStr t = unlines t

main = do inp <- getContents
          putStr (outStr (tarefa (lines inp)))

type Tab = [String]
type Pos = (Int,Int,Char)
type Input = [String]
type Comandos = String
type Orient = Char

-- |'tarefa': Função que junta todo o HTML estático com as funções que geram todo o código que varia de tabuleiro para tabuleiro.
tarefa :: Input -> [String]
tarefa input = ["<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Strict//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd'>",
                "<html xmlns='http://www.w3.org/1999/xhtml'>",
                "<head>",
                "<meta http-equiv='X-UA-Compatible' content='chrome=1' />",
                "<meta http-equiv='Content-Type' content='text/html;charset=utf-8' />", 
                "<title>Tabuleiro Lightbot</title>",
                "<script type='text/javascript' src='http://www.x3dom.org/release/x3dom.js'></script>", 
                "<link rel='stylesheet' type='text/css' href='style.css'/>",
                "</head>",
                "<body>",
                "<center>",
                "<h1>Tabuleiro Lightbot</h1>",
                "</center>",
                "<center>",
                "<p class='case'>", 
                "<X3D xmlns='http://www.web3d.org/specifications/x3d-namespace' id='boxes' showStat='false' showLog='false'>", 
                "<scene>",
                "<Viewpoint position='" ++ pontoDeVista (selTab input) ++ "' orientation='0 0 1 2' centerOfRotation='"++ init (init(pontoDeVista (selTab input)))++"0" ++"' description='camera'></Viewpoint>",
                "<shape DEF='cubo'>",
                "<Appearance>",
                "<material diffuseColor='.3 .3 .3'/>", 
                "</Appearance>", 
                "<box size='.99 .99 .99'/>",
                "</shape>",
                "<shape DEF='cubol'>",
                "<appearance>", 
                "<material diffuseColor='0 0 1.0'/>", 
                "</appearance> <box size='.99 .99 .99'/>",
                "</shape>"] ++ robot ++
                ["</transform>"] ++ pontos (selTab input) ++ movimento input ++
                ["</scene> </X3D> </p> </body> </html>"]


-- |'selTab': Função que seleciona o tabuleiro a partir do input inicial.
selTab :: [String] -> [String]
selTab inp = takeWhile (notElem ' ') inp

-- |'selPos': Função que seleciona a posição inicial a partir do input inicial.
selPos :: [String] -> Pos
selPos inp = (x,y,o)
             where x = digitToInt (head (linhaPos!!0))
                   y = digitToInt (head (linhaPos!! 1))
                   o = head (last linhaPos)
                   linhaPos = (words (inp!!length (selTab inp)))

-- |'selCom': Função que seleciona os comandos a partir do input inicial.
selCom :: [String] -> String
selCom [] = []
selCom inp = inp !! (length (selTab inp) +1)


-- |'pontoDeVista': Função que recebe as linhas correspondentes ao tabuleiro no input fornecido e gera uma linha com as coordenadas respetivas ao ponto de vista 
pontoDeVista :: Tab -> String
pontoDeVista tab = show (fromIntegral(length tab)/2) ++ " " ++ show (((fromIntegral(length (head tab)) /2))) ++" " ++ show (maiorAltura +10)
                   where maiorAltura = maximum (map maximum (map (map nivel) (selTab tab)))


-- |'nivel': Dado um char, retorna a altura correspondente. A altura de 'a' é 0.
-- 
-- * 65 -> Corresponde ao código ASCII do caracter 'A'.
--
-- * 97 -> Corresponde ao código ASCII do caracter 'a'.
nivel :: Char -> Int
nivel h = if isUpper h 
      then ((ord h) - 65)
      else ((ord h) - 97)

-- |==Funções que geram o tabuleiro

-- |'pontos': Função que juntamente com a função 'processa', a função 'processa2' e a função 'proLinha' transforam a representação do tabuleiro.
-- 
-- * ["aabB","BcAa"] -> ["00a","01a","02b","03B","10B","11c","12A","13a"].

pontos :: Tab -> [String]
pontos x = proc (processa x) 


processa :: Tab -> Tab
processa t = processa2 0 t


processa2 :: Int -> Tab -> [String]
processa2 _ [] = []
processa2 n (h:t) = (procLinha n 0 h) ++ (processa2 (n+1) t)

procLinha :: Int -> Int-> String -> [String]
procLinha _ _ "" = []
procLinha x y (h:t) = ((show x) ++ (show y) ++ (h:[])) : (procLinha x (y+1) t)

-- |'proc': Função que gera o código HTML relativo ao tabuleiro.
proc :: [String] -> [String]
proc [] = []
proc (h:t) | (nivel (h!!2))>0 = (("<transform translation='"++ coordenadas h ++ "'><shape USE=" ++tipoc h++ "/></transform>"):(fill h ((nivel (h!!2))-1))) ++ proc t
           | otherwise = ("<transform translation='"++ coordenadas h ++ "'><shape USE=" ++tipoc h++ "/></transform>"):proc t

-- |'coordenadas': Função que calcula as coordenadas e as coloca no formato correto do HTML.
coordenadas :: String -> String
coordenadas h = (head h):' ':(head (tail h)):' ':show (nivel (head(tail (tail h))))

-- |'tipoc': Função que seleciona o tipo de cubo consoante esse ponto seja ou não um cubo com lâmpada.
tipoc :: String -> String
tipoc h = if isUpper (head (tail(tail h)))
      then "'cubol'"
      else "'cubo'"

-- |'fill': Função que preenche o restante tabuleiro, todas as posições em baixo, apenas quando o nível é superior a 0.
fill :: String -> Int -> [String]
fill h n | n == 0 = ("<transform translation='"++ coordenadas ((init h)++ ((invNivel n):[])) ++ "'><shape USE='cubo'/></transform>") : []
       | otherwise = ("<transform translation='"++ coordenadas ((init h)++ ((invNivel n):[])) ++ "'><shape USE='cubo'/></transform>") : fill h (n-1)

-- |'invNivel': Função que faz o contrário da função 'nivel'. A função a partir de uma altura calcula a letra do tabuleiro correspondente.
invNivel :: Int -> Char
invNivel n = chr (n+97)




-- |==Funções que geram todo o movimento do Robot

-- |'movimento': Função que gera as linhas respetivas à animação do robot 
movimento :: Input -> [String]
movimento x = ["<timeSensor DEF='time' cycleInterval='" ++ show (length comandos) ++ "' loop='true'> </timeSensor>",
               "<PositionInterpolator DEF='move' key='" ++ unwords key ++ "' keyValue='" ++ unwords keyValue ++"'> </PositionInterpolator>",
               "<Route fromNode='time' fromField ='fraction_changed' toNode='move' toField='set_fraction'> </Route>",
               "<Route fromNode='move' fromField ='value_changed' toNode='robot' toField='translation'> </Route>",
               "<orientationInterpolator DEF='roda' key='0 " ++ keyRot  ++ "' keyValue='" ++ keyValueRot  ++ "'></orientationInterpolator>",
               "<ROUTE fromNode='time' fromField='fraction_changed' toNode='roda' toField='set_fraction'></Route>",
               "<ROUTE fromNode='roda' fromField='value_changed' toNode='robot' toField='set_rotation'></Route>"]
                where key = ["0"] ++ (calculat 0 tam (1/tam))
                      keyValue = map (converte tab) ([pInicial] ++ (posicao tab pInicial comandos))
                      tab = selTab x
                      pInicial = selPos x
                      comandos = selCom x
                      keyRot = duracaoRot listaRot
                      keyValueRot = "0 0 1 " ++ (show radPosInicial) ++ " " ++ "0 0 1 " ++ (show radPosInicial) ++ " " ++ geraRadianos radPosInicial orients

                      duracaoRot [] = "1"
                      duracaoRot (x:xs) | (read x) + tamRot >= 1 = x ++ " 1" 
                                        | otherwise = x ++ " " ++ show (read x + tamRot) ++ " " ++ duracaoRot xs

                      tamRot = 1/(fromIntegral (length comandos) ) -- espaço de tempo entre as key frames

                      listaRot =  (map (\(x,y) -> y) listaTempos)

                      listaTempos = filter (\(x,y)-> x =='E' || x == 'D') (zip comandos key)

                      tam = fromIntegral (length comandos) -1

                      orients = map (\(x,y) -> (x)) listaTempos
                      radPosInicial = radianos (posToOrient pInicial)

-- |'posToOrient': Função que recebe um triplo do tipo Pos e retorna apenas o terceiro elemento (orientação) 
posToOrient :: Pos -> Orient
posToOrient (x,y,o) = o

-- |'radianos': Recebe um Orient e retorna o valor em radianos que corresponde à orientação pretendida na animação 
radianos :: Orient -> Float
radianos ori | ori == 'S' = -1.57
             | ori == 'E' = 0 
             | ori == 'N' = 1.57
             | ori == 'O' = 3.14 

-- |'geraRadianos': Recebe um valor em radianos (respetivo à orientação da posição inicial) e uma string de comandos e gera uma string com as keyframes da rotação do robot ao longo da animação consoante os comandos
geraRadianos :: Float -> Comandos -> String
geraRadianos _ [] = []
geraRadianos x (cmd:cmds) | cmd == 'E' = (unwords (replicate 2 esquerda)) ++ geraRadianos (x + 1.57) cmds
                          | otherwise = (unwords (replicate 2 direita)) ++ geraRadianos (x - 1.57) cmds       
                            where direita = (" 0 0 1 "++(show (x - 1.57)) ++ " ")
                                  esquerda = (" 0 0 1 "++(show (x + 1.57)) ++ " ")

-- |'converte': Função usada para gerar as key frames respetivas à translação do robot na animação
converte :: Tab -> Pos -> String
converte tab (x,y,o) = (show ((length tab) -1 - y)) ++ " " ++ (show x) ++ " " ++ show ((altura tab (x,y,o)) +1) ++ " "

-- |'calculat': Cria uma lista com os tempos respetivos às key frames da translação do robot na animação
calculat :: Float -> Float -> Float-> [String]
calculat _ 1 _ = ["1"]
calculat x n y = show (x + y): calculat (x + y) (n-1) y

-- |'posicao': Cria uma lista com as posições respetivas às key frames da translação do robot na animação
posicao :: Tab -> Pos -> Comandos -> [Pos]
posicao _ _ [] = []
posicao tab p (h:t)  | h== 'S' && verifica tab (move tab p) = move tab p : posicao tab (move tab p) t
                     | h== 'A' && verifica tab (move tab p)= move tab p : posicao tab (move tab p) t
                     | h== 'L' = p : posicao tab p t
                     | h== 'D' = direita p : posicao tab (direita p) t
                     | h== 'E' = esquerda p : posicao tab (esquerda p) t
                     | otherwise = p: posicao tab p t

-- |'verifica': Testa se uma posição está dentro do tabuleiro ou não
verifica :: Tab -> Pos -> Bool
verifica tab (x,y,o) = if (x <= (length (head tab))) && (x >=0) && (y <= (length tab)) && (y>=0)
                            then True
                            else False

-- |'move': Dada uma posição, retorna a posição resultante da execução do comando S (saltar) ou A (avançar)
move :: Tab -> Pos -> Pos
move tab (x,y,o)    | o == 'N' = (x,y+1,o)
                    | o == 'S' = (x,y-1,o)
                    | o == 'O' = (x-1,y,o)
                    | o == 'E' = (x+1,y,o)

-- |'esquerda': Recebe uma posição e retorna uma posição com as mesmas coordenadas, mas com a orientação à esquerda da anterior
esquerda :: Pos -> Pos
esquerda (x,y,o)| o =='N' = (x,y,'O')
                | o =='S' = (x,y,'E')  
                | o =='O' = (x,y,'S') 
                | o =='E' = (x,y,'N')

-- |'direita': Recebe uma posição e retorna uma posição com as mesmas coordenadas, mas com a orientação à direita da anterior
direita :: Pos -> Pos
direita (x,y,o) | o =='N' = (x,y,'E')
                | o =='S' = (x,y,'O')  
                | o =='O' = (x,y,'N') 
                | o =='E' = (x,y,'S')

-- |'altura': Calcula a altura do tabuleiro em determinada posição
altura :: Tab -> Pos -> Int
altura tab (x,y,o) = nivel (selectx (x,y,o) (selecty (x,y,o) (reverse tab)))

-- |'selectx': Recebe uma posição e uma string (linha do tabuleiro) e retorna o caractere correspondente à coordenada x da posição fornecida
selectx :: Pos -> String -> Char
selectx (x,y,o) l = l!!x

-- |'selecty': Recebe uma posição e as strings respetivas ao tabuleiro e retorna a linha correspondente à coordenada y da posição fornecida
selecty :: Pos -> Tab -> [Char]
selecty (x,y,o) tab = tab!!y


robot = ["<transform DEF = 'robot'>",
         "<transform scale='.5 .5 .5'>",
         "<transform translation='0 0 1.4'  scale='0.8 0.7 .7'>",
         "<shape>",
         "<appearance>",
         "<material diffuseColor='0.7 0.7 0.7'/>",
         "</appearance>",
         "<sphere></sphere>",
         "</shape>",
         "</transform>",
         "<transform translation='0 0 0' rotation='0 0 1 3.2' scale='1 1 1'>,",
         "<shape>",
         "<appearance>",
         "<material diffuseColor='0.7 0.7 0.7'/>",
         "</appearance>",
         "<sphere></sphere>",
         "</shape>",
         "</transform>",
         "<transform translation='0 0.2 2.5' rotation='0 0 0 0' scale='.1 .1 .1'>",
         "<shape>",
         "<appearance>",
         "<material diffuseColor='1 1 1'/>",
         "</appearance>",
         "<sphere></sphere>",
         "</shape>",
         "</transform>",
         "<transform translation='0 0.2 2.3' rotation='0 0 0 0' scale='0.03 0.03 0.3'>",
         "<shape>",
         "<appearance>",
         "<material diffuseColor='0.7 0.7 0.7'/>",
         "</appearance>",
         "<sphere></sphere>",
         "</shape>",
         "</transform>",
         "<transform translation='-.3 .7 1.4' scale='.15 .05 .15'>",
         "<shape>",
         "<appearance>",
         "<material diffuseColor='1 1 1'/>",
         "</appearance>",
         "<sphere></sphere>",
         "</shape>",
         "</transform>",
         "<transform translation='.3 .7 1.4' scale='.15 .05 .15'>",
         "<shape>",
         "<appearance>",
         "<material diffuseColor='1 1 1'/>",
         "</appearance>",
         "<sphere></sphere>",
         "</shape>",
         "</transform>",
         "<transform translation='0 0.71 1.1' scale='.2 0 .075'>",
         "<shape>",
         "<appearance>",
         "<material diffuseColor='1 1 1'/>",
         "</appearance>",
         "<sphere></sphere>",
         "</shape>",
         "</transform>",
         "</transform>"]



tb1=["Bbaa",
     "aabB",
     "Bbaa","3 2 O","ASAASALESEASALDSDASAL"]

tb2=["aaa",
     "bbb",
     "Ccc","0 0 O","AAEEAAL"]
