{- |

Module : Tarefa2 - LI1
Description : Módulo Haskell que contém as funções da Tarefa 2 do Projeto de Laboratórios de Informática I.
Copyright: Rogério Moreira <a74634@alunos.uminho.pt>
           Samuel Ferreira <a76507@alunos.uminho.pt>

Módulo da Tarefa 2 que contém as funções relativas ao cálculo das coordenadas e orientação após a execução do primeiro comando no âmbito do Projeto de Laboratórios de Informática I.

-}

module Main where

import Data.Char
import Data.List
import Data.String 

type Tabuleiro = [String]
type Pos = (Int,Int,Char)

outStr :: [String] -> String
outStr [] = "\n"
outStr t = unlines t

main = do inp <- getContents
          putStr (outStr (tarefa (lines inp)))

-- | 'tarefa': Função que executa o primeiro comando num dado tabuleiro:
--
-- * Caso o comando seja válido - O valor final são as coordenadas e a orientação depois de executado o comando.
--
-- * Caso o comando não seja válido - O valor final é "ERRO"
tarefa :: [String] -> [String]
tarefa [] = []
tarefa input = [linha]
                where linha | primcomando == 'E' = convertToString (esquerda posicao)
                            | primcomando == 'D' = convertToString (direita posicao)
                            | primcomando == 'L' && isUpper (indice posicao tabuleiro) = convertToString posicao
                            | primcomando == 'A' && testacoord tabuleiro (proximaPos posicao)
                                                 && altura (proximaPos posicao) tabuleiro == altura posicao tabuleiro
                                                  = convertToString (proximaPos posicao)
                            | primcomando == 'S' && testacoord tabuleiro (proximaPos posicao)
                                                  = if altura (proximaPos posicao) tabuleiro < altura posicao tabuleiro 
                                                  || altura (proximaPos posicao) tabuleiro == (altura posicao tabuleiro) +1
                                                  then convertToString (proximaPos posicao)
                                                   else "ERRO"
                            | otherwise = "ERRO"
                      tabuleiro = selTab input -- linhas respetivas ao tabuleiro
                      posicao = selPos input -- coordenadas x,y e orientação (x,y,o)
                      primcomando = selCom input -- Letra respetiva ao primeiro comando


-- |'selTab': Função que seleciona as linhas respetivas ao tabuleiro.
selTab :: [String] -> [String]
selTab inp = takeWhile (notElem ' ') inp

-- |'selPos': Função que seleciona a linha respetiva à posição inicial.
selPos :: [String] -> Pos
selPos inp = (x,y,o)
             where x = digitToInt (head (linhaPos!!0))
                   y = digitToInt (head (linhaPos!! 1))
                   o = head (last linhaPos)
                   linhaPos = (words (inp!!length (selTab inp)))

-- |'selCom': Função que seleciona a linha respetiva aos comandos.
selCom :: [String] -> Char
selCom inp = head ( last inp )


-- |'convertToString': Função que converte a 'Posição' para uma String.
convertToString :: Pos -> String
convertToString (x,y,o) = show x ++ " " ++ show y ++ " " ++ [o]


-- |'proximaPos': Função que dadas as coordenadas e a orientação retornam as coordenadas e a orientação após o deslocamento.
proximaPos :: Pos  -> Pos
proximaPos (x,y,o) | o == 'N' = (x,(y+1),o)
                   | o == 'S' = (x,(y-1),o)
                   | o == 'O' = ((x-1),y,o)
                   | o == 'E' = ((x+1),y,o)

-- |'esquerda': Dada a orientação e o comando E a função dá a orientação depois de executado o comando.
esquerda :: Pos -> Pos
esquerda (x,y,o)| o =='N' = (x,y,'O')
                | o =='S' = (x,y,'E')  
                | o =='O' = (x,y,'S') 
                | o =='E' = (x,y,'N')

-- |'direita': Dada a orientação e o comando D a função dá a orientação depois de executado o comando.
direita :: Pos -> Pos
direita (x,y,o) | o =='N' = (x,y,'E')
                | o =='S' = (x,y,'O')  
                | o =='O' = (x,y,'N') 
                | o =='E' = (x,y,'S')


-- |'indice': Encontra a letra do tabuleiro correspondente às coordenadas x e y.
indice :: Pos -> Tabuleiro -> Char
indice (x,y,o) t = (t!!((length t)-y-1))!!x


-- |'altura': Converte a letra de um tabuleiro (Char) na respetiva altura (Int).
-- Exemplo: a=0 , A=0 , b=1 , B=1.
altura :: Pos -> Tabuleiro -> Int
altura (x,y,o) t = ord (toUpper c) - 65
                    where c = indice (x,y,o) t

-- |'testacoord': Dado um tabuleiro e uma posição testa se é possível, ou seja, as coordenadas depois do comando estão dentro do tabuleiro.
testacoord :: Tabuleiro -> Pos -> Bool
testacoord tab (x,y,o) = x>=0 && x< (compX tab) && y>=0 && y<compY tab
                      where compX tab = length (head tab)
                            compY tab = length tab
