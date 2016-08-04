
{- |

Module : Tarefa3 - LI1
Description : Módulo Haskell que contém as funções da Tarefa 3 do Projeto de Laboratórios de Informática I.
Copyright: Rogério Moreira <a74634@alunos.uminho.pt>
           Samuel Ferreira <a76507@alunos.uminho.pt>

Módulo da Tarefa 3 que contém as funções que executam todos os comandos fornecidos  no tabuleiro e dão como output consoante o enunciado no âmbito do Projeto de Laboratórios de Informática I.

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

tarefa :: [String] -> [String]
tarefa [] = []
tarefa input = executa (selTab input) (input!!(length input -2)) (last input) (stlampadas (selTab input) 0) 0

-- |'stlampadas': Cria uma lista com as coordenadas de todas as lâmpadas do tabuleiro.
stlampadas :: [String] -> Int -> [String]
stlampadas [] _ = []
stlampadas (h : t) n = (aux h (length (h:t) -1) 0) ++ (stlampadas t (n-1))
                    where aux [] _ _ = []
                          aux (x:xs) l c | isUpper x = (show c ++ " " ++ show l) : aux xs l (c+1) 
                                         | otherwise = aux xs l (c+1)

-- |'executa': Função recursiva que corre cada comando pela função 'tarefaB' e que devolve o resultado final depois de exexutados todos
--os testes necessários à validação do input.
executa :: [String] -> String -> String -> [String] ->Int -> [String]
executa _ _ [] l n | l == [] = ["FIM " ++ show n]
                   | otherwise = ["INCOMPLETO"]
executa t p c l n | l == [] = ["FIM " ++ show n]
                  | tarefaB set == ["ERRO"] = executa t p (tail c) l n 
                  | tarefaB set == [p] && (head c) == 'L' = [take (length p -2) p] ++ executa t p (tail c) (upLight (take (length p -2) p) l) (n+1)
                  | otherwise = executa t (head (tarefaB set)) (tail c) l (n+1)
                    where set = (t++[p]++[c])


-- | 'upLight': Testa se uma determinada luz está acesa ou não:
-- 
-- * Se está apagada -> Já pertence à lista
--
-- * Se está acesa -> Ainda não pertende à lista
--
-- Caso esteja apagada a função remove as coordenadas dessa luz da lista.
-- Caso esteja acesa a função adiciona as coordenadas dessa luz à lista.
-- Se a lista está vazia -> Todas as luzes estão acessas
upLight :: String -> [String] -> [String]
upLight _ [] = []
upLight pos l | elem pos l = remove pos l
                 | otherwise = pos : l



-- | 'remove': Função auxiliar do 'upLight' que encontra o elemento pretendido e remove-o da lista.
remove :: String -> [String] -> [String]
remove _ [] = []
remove e (x:xs) |e==x = xs
                |otherwise = x : remove e xs



-- |==Funções relativas à Tarefa 2 já documentadas no respetivo ficheiro.

tarefaB :: [String] -> [String]
tarefaB [] = []
tarefaB input = [linha]
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


selTab :: [String] -> [String]
selTab inp = takeWhile (notElem ' ') inp


selPos :: [String] -> Pos
selPos inp = (x,y,o)
             where x = digitToInt (head (linhaPos!!0))
                   y = digitToInt (head (linhaPos!! 1))
                   o = head (last linhaPos)
                   linhaPos = (words (inp!!length (selTab inp)))


selCom :: [String] -> Char
selCom inp = head ( last inp )



convertToString :: Pos -> String
convertToString (x,y,o) = show x ++ " " ++ show y ++ " " ++ [o]


proximaPos :: Pos  -> Pos
proximaPos (x,y,o) | o == 'N' = (x,(y+1),o)
                   | o == 'S' = (x,(y-1),o)
                   | o == 'O' = ((x-1),y,o)
                   | otherwise = ((x+1),y,o)


esquerda :: Pos -> Pos
esquerda (x,y,o)| o =='N' = (x,y,'O')
                | o =='S' = (x,y,'E')  
                | o =='O' = (x,y,'S') 
                | otherwise = (x,y,'N')


direita :: Pos -> Pos
direita (x,y,o) | o =='N' = (x,y,'E')
                | o =='S' = (x,y,'O')  
                | o =='O' = (x,y,'N') 
                | otherwise = (x,y,'S')


-- Encontra a letra do tabuleiro executaspondente às coordenadas x e y
indice :: Pos -> Tabuleiro -> Char
indice (x,y,o) t = (t!!((length t)-y-1))!!x


-- Converte a letra de um tabuleiro (char) na respetiva altura (Int)
-- a=0 , A=0 , b=1 , B=1 ...

altura :: Pos -> Tabuleiro -> Int
altura (x,y,o) t = ord (toUpper c) - 65
                    where c = indice (x,y,o) t


testacoord :: Tabuleiro -> Pos -> Bool
testacoord tab (x,y,o) = x>=0 && x< (compX tab) && y>=0 && y<compY tab
                      where compX tab = length (head tab)
                            compY tab = length tab