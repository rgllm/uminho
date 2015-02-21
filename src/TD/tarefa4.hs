
{- |

Module : Tarefa4 - LI1
Description : Módulo Haskell que contém as funções da Tarefa 4 do Projeto de Laboratórios de Informática I.
Copyright: Rogério Moreira <a74634@alunos.uminho.pt>
           Samuel Ferreira <a76507@alunos.uminho.pt>

Módulo da Tarefa 4 que contém as funções que calculam os comandos necessários para completar um tabuleiro.

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


type Pos = (Int,Int)
type Orient = Char
type Cmd = Char
type Tab = [String]
type Input = [String]


-- |'tarefa': Função que dado um tabuleiro e a posição inicial de um robot calcula os comandos necessários para ligar todas as luzes do tabuleiro.
tarefa :: [String] -> [String]
tarefa input = luzes (reverse (stlampadas (selTab input) 0)) (fst (selPos input)) (snd (selPos input)) (selTab input):[]

-- |'selTab': Função que seleciona o tabuleiro a partir da [String] inicial.
selTab :: [String] -> [String]
selTab inp = takeWhile (notElem ' ') inp

-- |'selTab': Função que seleciona a posição inicial a partir da [String] inicial.
selPos :: [String] -> (Pos,Orient)
selPos inp = ((x,y),o)
             where x = digitToInt (head (linhaPos!!0))
                   y = digitToInt (head (linhaPos!! 1))
                   o = head (last linhaPos)
                   linhaPos = (words (inp!!length (selTab inp)))

-- |'stlampadas': Cria uma lista com as coordenadas de todas as lâmpadas do tabuleiro.
stlampadas :: [String] -> Int -> [Pos]
stlampadas [] _ = []
stlampadas (h : t) n = aux h (length (h:t) -1) 0 ++ (stlampadas t (n-1))
                    where aux [] _ _ = []
                          aux (x:xs) l c | isUpper x = (c,l) : aux xs l (c+1) 
                                         | otherwise = aux xs l (c+1)

-- |'listaAlt': Função que calcula todas as alturas de cada posição do tabuleiro.
listaAlt :: Tab -> [[Int]]
listaAlt [] = []
listaAlt (h:t) = map nivel h : listaAlt t 

-- |'nivel': Dado um char, retorna a altura correspondente. A altura de 'a' é 0.
-- 
-- * 65 -> Corresponde ao código ASCII do caracter 'A'.
--
-- * 97 -> Corresponde ao código ASCII do caracter 'a'.
nivel :: Char -> Int
nivel h = if isUpper h 
            then ((ord h) - 65)
            else ((ord h) - 97)

-- | 'luzes': Função que a partir da lista das luzes vai sucessivamente acender todas as luzes de determinado tabuleiro.
-- 
-- * [Pos] -> Corresponde à lista das posições das luzes do tabuleiro.
--
-- * Pos -> Coresponde à posição inicial do robot.
luzes :: [Pos] -> Pos -> Orient -> Tab -> [Cmd]
luzes [] _ _ _ = []
luzes ((x,y):[]) (a,b) orient tab | snd (move (a,b) (x,y) orient tab []) == 'I' = []
                                  | otherwise= cmd ++ "L" 
                                  where cmd = fst (move (a,b) (x,y) orient tab []) 
                       
luzes l (a,b) orient tab | elem (a,b) l = "L"++ luzes (delete (a,b) l) (a,b) orient tab
                         | snd (move (a,b) (x,y) orient tab []) == 'I' = luzes (xys++[(x,y)]) (a,b) orient tab
                         | otherwise = cmd ++ "L" ++ luzes xys (x,y) o tab
                          where (x,y)= head l
                                xys = tail l
                                (cmd,o) = (move (a,b) (x,y) orient tab [])

-- |'move': Função que move o robot dentro do tabuleiro. Em primeiro lugar acerta a coordenada X e em seguida a coordenada Y.
-- 
-- * I -> No caso do robot não conseguir avançar retorna a I, que significa que é impossível.
move :: Pos -> Pos  -> Orient -> Tab  -> [Cmd] -> ([Cmd],Orient)  
move (x1,y1) (x2,y2) o tab cmd | x1==x2 && y1==y2 = (cmd,o)
                               | x1 > x2 && (indice2 (x1,y1) (listaAlt tab) - indice2 ((x1-1),y1) (listaAlt tab))>(-2) = move (x1-1,y1) (x2,y2) 'O' tab (adiciona 'O' (altura (x1-1,y1)) (altura (x1,y1)) cmd o)
                               | x1 < x2 && (indice2 (x1,y1) (listaAlt tab) - indice2 ((x1+1),y1) (listaAlt tab))>(-2) = move (x1+1,y1) (x2,y2) 'E' tab (adiciona 'E' (altura (x1+1,y1)) (altura (x1,y1)) cmd o)
                               | y1 < y2 && (indice2 (x1,y1) (listaAlt tab) - indice2 (x1,(y1+1)) (listaAlt tab))>(-2) = move (x1,y1+1) (x2,y2) 'N' tab (adiciona 'N' (altura (x1,y1+1)) (altura (x1,y1)) cmd o)
                               | y1 > y2 && (indice2 (x1,y1) (listaAlt tab) - indice2 (x1,(y1-1)) (listaAlt tab))>(-2) = move (x1,y1-1) (x2,y2) 'S' tab (adiciona 'S' (altura (x1,y1-1)) (altura (x1,y1)) cmd o)
                               | otherwise= (cmd,'I')
                             where 
                              altura (x,y) = nivel (indice (x,y) tab)
                              --testaObs x y = indice2 (x1,y1) (listaAlt tab) - indice2 ((x1-1,y1) (listaAlt tab)

-- |'testaAlt': Função que testa se duas alturas forem iguais o robot devolve o comando 'A' (Avançar), caso não sejam iguais devolve o comando 'S' (Saltar).
-- | Anteriormente já filtramos se a diferença entre as duas posições é maior do que 1 (caso impossível).
testaAlt :: Int -> Int -> String
testaAlt x y | x == y = "A"
             | otherwise = "S"

-- |'adiciona': Função 
adiciona :: Orient -> Int -> Int -> [Cmd] -> Orient -> [Cmd]
adiciona x alt alt2 cmd o| (roda o x) == "EEE" =  (cmd ++ "D" ++ (testaAlt alt alt2))
                         | otherwise = (cmd ++ (roda o x) ++ (testaAlt alt alt2))

-- |'roda': Função que a partir da orientação do robot e da orientação pretendida para o robot vira o robot para a esquerda o número de vezes necessária até atingir a orientação pretendida.
roda :: Orient -> Orient -> [Cmd]
roda o1 o2 | o1 == o2 = []
           | otherwise = 'E':(roda (esquerda o1) o2)

-- |'esquerda': Função que calcula a orientação do robot após rodar.
esquerda :: Orient -> Orient
esquerda o | o =='N' = 'O'
           | o =='S' = 'E'
           | o =='O' = 'S'
           | otherwise = 'N'

indice :: Pos -> Tab -> Char
indice (x,y) t = (t!!((length t)-y-1))!!x

indice2 :: Pos -> [[Int]] -> Int
indice2 (x,y) t = (t!!((length t)-y-1))!!x
