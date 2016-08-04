
{- |

Module : Tarefa1 - LI1
Description : Módulo Haskell que contém as funções da Tarefa 1 do Projeto de Laboratórios de Informática I.
Copyright: Rogério Moreira <a74634@alunos.uminho.pt>
           Samuel Ferreira <a76507@alunos.uminho.pt>

Módulo da Tarefa 1 que contém as funções relativas à verificação dos tabuleiros no âmbito do Projeto de Laboratórios de Informática I.

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

-- | 'tarefa': função que recebe a validade do tabuleiro « e que retorna a linha do erro ou "Ok"
tarefa :: [String] -> [String]
tarefa [] = ["1"]
tarefa [a] = ["1"]
tarefa [a,b] = if isLetter(head a)then ["2"] else ["1"]
tarefa input = [linha]
                where linha | testaTab tabuleiro > 0 = show (testaTab tabuleiro)
                            | testaPos posicao (length input -1) > 0 = show (testaPos posicao (length input - 1) )
                            | (testax (input!!0) (words posicao!!0) + testay tabuleiro ( words posicao!!1) )> 0 = show  ((length input) - 1 )
                            | (validaComV (selCom input) )> 0 = show (length input)
                            | length input > length tabuleiro +2 = show (length input)
                            | otherwise = "OK"
                      tabuleiro = (selTab input)  -- linhas respetivas ao tabuleiro
                      posicao = (selPos input) -- linha respetiva à posição inicial




-- |==Funções de seleção e teste do tabuleiro

-- | 'selTab': função que filtra as linhas do tabuleiro
selTab :: [String] -> [String]
selTab [] = []
selTab inp = takeWhile (notElem ' ') inp

-- | 'testaTab': função que testa a validade do tabuleiro recorrendo à função verificaTab e testarLetras
testaTab :: [String] -> Int 
testaTab [] = 1
testaTab [a] = 2
testaTab (x:xs) = verificaTab (length x) 1 (x:xs)

-- | 'verificaTab': função que esta se todas as linhas do tabuleiro têm o mesmo comprimento.
verificaTab :: Int -> Int -> [String] -> Int
verificaTab tam n [] = 0
verificaTab tam n (x:xs) = if length x == tam && testarLetras x
                            then verificaTab tam (n+1) xs
                             else n

-- | 'testarLetras': função que valida se o tabuleiro tem apenas letras na sua constituição                            
testarLetras :: String -> Bool
testarLetras [] = True
testarLetras (x:xs) = ( ord x >= 65 && (ord x) <= 90 || ord x >= 97 && ord x <= 122 ) && testarLetras xs 


-- |==Funções seleção e teste das coordenadas da posição inicial

-- | 'selPos': função que filtra a linha da posição inicial
selPos :: [String] -> String
selPos [] = []
selPos inp =  inp !! (length (selTab inp))

-- | 'testaPos': função que valida se a posição inicial tem 3 comandos, e que chama as restantes funções de validação.
testaPos :: String -> Int -> Int
testaPos "" n = n
testaPos l n = if ( length (words l) == 3 && validaNum ((words l) !! 0) && validaNum ((words l) !! 1) && isAlpha (head ((words l) !! 2)) && validaCoord ((words l) !! 2) )
                then 0
                else n

-- | 'validaCoord': função que verifica se a terceira coordenada é uma das letras
validaCoord :: String -> Bool
validaCoord [] = True
validaCoord l | l == "O" || l == "E" || l == "S" || l == "N" = True 
              |otherwise = False

-- | 'validaNum': função que valida se as duas primeiras coordenadas são números
validaNum :: String -> Bool
validaNum [] = True
validaNum (h:t) = isDigit h && validaNum t

-- |==Funções que validam se a posição 

-- | 'testax': valida se o comprimento de cada linha é menor do que o valor da primeira coordenada, ou seja, se a coordenada X está dentro do tabuleiro.
testax :: String -> String -> Int
testax t x = if read x <= ((length t) -1) 
              then 0
              else 1

-- | 'testay': valida se o número total de linahs é menor do que o valor da segunda coordenada, ou seja, se a coordenada Y está dentro do tabuleiro.
testay :: [String] -> String -> Int
testay t y = if read y <= ((length t) -1) 
              then 0
              else 1

-- |==Funções de seleção e teste dos comandos

-- | 'selCom': seleciona a linha dos comandos 
selCom :: [String] -> String
selCom [] = []
selCom inp = inp !! (length (selTab inp) +1)

-- | 'validaComV': verifica se existem apenas os comandos válidos: *A=Avançar *S=Saltar *L=Luz *E=Esquerda *D=Direita
validaComV :: String -> Int
valigaComV [] = 1
validaComV x = validaCom x
        where validaCom [] = 0
              validaCom (h:t) |elem h "ASLED" = validaCom t
                              |otherwise = 1

