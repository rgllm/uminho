--
-- Projecto CP 2015/16
--
-- O projecto consiste em desenvolver testes para o módulo Graph.hs
-- (para grafos orientados e não pesados).
-- Mais concretamente, o projecto consiste em 3 tarefas que são descritas abaixo.
-- O prazo para entrega é o dia 3 de Abril. Cada grupo deve enviar apenas
-- o módulo de testes (este módulo) por email para calculodeprogramas@gmail.com
-- O nome do ficheiro deve identificar os números dos 2 alunos do grupo (numero1_numero2.hs).
-- Certifiquem-se que o módulo de testes desenvolvido compila correctamente antes
-- de submeter. O módulo Graph.hs não deve ser alterado.
-- Os 2 alunos do grupo devem também indentificar-se nos comentários abaixo.
--
-- Aluno 1
-- Número: A76507
-- Nome: Samuel Gonçalves Ferreira
-- Curso: MIEINF
--
-- Aluno 2
-- Número: A74634
-- Nome: Rogério Gomes Lopes Moreira
-- Curso: MIEINF
--

{-# LANGUAGE TemplateHaskell #-}

module Main where

import Graph
import Test.HUnit hiding (path)
import Test.QuickCheck
import Data.Set as Set
import System.Random
import Test.QuickCheck.All

--
-- Funções auxiliares
--

desempacota :: Maybe (Graph.Path v)-> Graph.Path v
desempacota (Just x) =x
desempacota Nothing=[]

--
-- Teste unitário
--

g1 = Graph {nodes = fromList [1],
            edges = fromList [Edge 1 1]
           }

g2 = Graph {nodes = fromList [1,2,3,4,5],
            edges = fromList [Edge 5 1,Edge 1 3,Edge 3 2,Edge 2 4]
           }

--sub_g2 é um subgrafo de g2
sub_g2= Graph {nodes = fromList [1,2,3],
               edges = fromList [Edge 1 3,Edge 3 2]
              }

g3 = Graph {nodes = fromList [1,2,3,4,5],
            edges = fromList [Edge 3 5,Edge 5 4,Edge 3 4,Edge 3 1,Edge 4 1,Edge 3 2]
           }
-- sub_g3 não é um subgrafo de g3, tem a aresta 4->5 em vez de 5->4
sub_g3= Graph {nodes = fromList [1,3,4,5],
               edges = fromList [Edge 3 5,Edge 4 5,Edge 3 4]
              }

g4 = Graph {nodes = fromList [1,2,3],
            edges = fromList [Edge 1 2,Edge 1 7]
           }

g5 = Graph {nodes = fromList [1,2],
            edges = fromList [Edge 1 2,Edge 2 1]
           }

g6 = Graph {nodes = fromList [],
            edges = fromList [Edge 1 1]
           }

g7 = Graph {nodes = fromList [1],
            edges = fromList []
           }

g8 = Graph {nodes = fromList [1,2,3,4],
            edges = fromList [Edge 1 2, Edge 2 4,Edge 4 3,Edge 3 2]
           }

-- Grafo para testar isValid com arestas repetidas
g9 = Graph {nodes = fromList[1,2,3,4],
            edges = fromList[Edge 1 2, Edge 2 4, Edge 2 4, Edge 3 4]
           }

g10= Graph {nodes = fromList[1,2,3,4,5],
            edges = fromList[Edge 1 2, Edge 2 4, Edge 2 3]
           }

g11 = Graph{nodes = fromList[1,2,3,4,5,6,7,8,9],
            edges = fromList[Edge 1 2, Edge 2 3, Edge 2 6, Edge 3 1, Edge 4 3, Edge 4 8, Edge 5 3, Edge 5 4, Edge 7 1, Edge 7 4, Edge 7 8, Edge 9 8, Edge 9 5, Edge 9 6]}

sub_g11 = Graph{nodes = fromList[3,4,5],
            edges = fromList[Edge 4 3, Edge 5 4, Edge 5 3]}

g12 = Graph{nodes = fromList[1,2,3,4,5,6],
            edges = fromList[Edge 1 2, Edge 2 3, Edge 3 4, Edge 4 5, Edge 5 6]}

g13 = Graph{nodes = fromList[1,2,3,4,5],
            edges = fromList[Edge 1 2, Edge 2 3, Edge 4 5]}

g14 = Graph{nodes = fromList[1,2,3,4,5],
            edges = fromList[Edge 1 2, Edge 1 3, Edge 3 2, Edge 2 4, Edge 4 5, Edge 2 5, Edge 3 4]}

--
-- Tarefa 1
--
-- Defina testes unitários para todas as funções do módulo Graph,
-- tentando obter o máximo de cobertura de expressões, condições, etc.
--

test_swap1= swap (Edge 1 1) ~?= (Edge 1 1)
test_swap2= swap (Edge 2 3) ~?= (Edge 3 2)

test_empty= isEmpty(Graph.empty) ~?= True

test_isEmpty1=isEmpty(Graph.empty) ~?= True
test_isEmpty2=isEmpty(g2) ~?= False
test_isEmpty3=isEmpty(g6) ~?= True
test_isEmpty4=isEmpty(g7) ~?= False

test_isValid1=isValid(g1) ~?= True
test_isValid2=isValid(g2) ~?= True
test_isValid3=isValid(g3) ~?= True
test_isValid4=isValid(g4) ~?= False
test_isValid5=isValid(g5) ~?= True
test_isValid6=isValid(g6) ~?= False
test_isValid7=isValid(g7) ~?= True
test_isValid8=isValid(g8) ~?= True
test_isValid9=isValid(g9) ~?= True
test_isValid10=isValid(g11) ~?= True

test_isDAG1=isDAG(g1) ~?= False
test_isDAG2=isDAG(g2) ~?= True
test_isDAG3=isDAG(g3) ~?= True
test_isDAG4=isDAG(g7) ~?= True
test_isDAG5=isDAG(g8) ~?= False
test_isDAG6=isDAG(sub_g2) ~?= True
test_isDAG7=isDAG(sub_g3) ~?= True
test_isDag8=isDAG(g11) ~?= False

test_isForest1=isForest(g2) ~?= True
test_isForest2=isForest(g3) ~?= False
test_isForest3=isForest(sub_g3) ~?= False
test_isForest4=isForest(g10) ~?= False
test_isForest5=isForest(g12) ~?= True
test_isForest6=isForest(g13) ~?= True
test_isForest7=isForest(g14) ~?= False

test_isSubgraphOf1= isSubgraphOf sub_g2 g2 ~?= True
test_isSubgraphOf2= isSubgraphOf sub_g3 g3 ~?= False
test_isSubgraphOf3= isSubgraphOf sub_g11 g11 ~?= True
test_isSubgraphOf4= isSubgraphOf g11 g11 ~?= True

test_adj1 = adj g1 1 ~?= fromList [Edge 1 1]
test_adj2 = adj g2 5 ~?= fromList [Edge 5 1]
test_adj3 = adj g2 4 ~?= fromList []
test_adj4 = adj g11 1 ~?= fromList[Edge 1 2]
test_adj5 = adj g11 5 ~?= fromList[Edge 5 4, Edge 5 3]
test_adj6 = adj g11 7 ~?= fromList[Edge 7 1, Edge 7 4, Edge 7 8]
test_adj7 = adj g9 2 ~?= fromList[Edge 2 4]

test_transpose1 = transpose(g1) ~?= Graph {nodes = fromList [1],edges = fromList [Edge 1 1]}
test_transpose2 = transpose(g3) ~?= Graph {nodes = fromList [1,2,3,4,5], edges = fromList [Edge 5 3,Edge 4 5,Edge 4 3,Edge 1 3,Edge 1 4,Edge 2 3]}
test_transpose3 = transpose(g7) ~?= g7
test_transpose4 = transpose(g11) ~?= Graph{nodes = fromList[1,2,3,4,5,6,7,8,9], edges = fromList[Edge 2 1, Edge 3 2, Edge 6 2, Edge 1 3, Edge 3 4, Edge 8 4, Edge 3 5, Edge 4 5, Edge 1 7, Edge 4 7, Edge 8 7, Edge 8 9, Edge 5 9, Edge 6 9]}

test_union1 = Graph.union g8 g9 ~?= Graph {nodes = fromList[1,2,3,4], edges = fromList[Edge 1 2, Edge 2 4, Edge 4 3, Edge 2 4, Edge 3 2, Edge 3 4]}
test_union2 = Graph.union g5 g5 ~?= g5
test_union3 = Graph.union g1 g11 ~?= Graph{nodes = fromList[1,2,3,4,5,6,7,8,9], edges = fromList[Edge 1 1, Edge 1 2, Edge 2 3, Edge 2 6, Edge 3 1, Edge 4 3, Edge 4 8, Edge 5 3, Edge 5 4, Edge 7 1, Edge 7 4, Edge 7 8, Edge 9 8, Edge 9 5, Edge 9 6]}

test_reachable1 = Graph.reachable g1 1 ~?=fromList[1]
test_reachable2 = Graph.reachable g2 3 ~?=fromList[2,3,4]
test_reachable3 = Graph.reachable g3 3 ~?=fromList[1,2,3,4,5]
test_reachable4 = Graph.reachable g7 1 ~?=fromList[1]
test_reachable5 = Graph.reachable sub_g3 1 ~?=fromList[1]
test_reachable6 = Graph.reachable sub_g3 3 ~?=fromList[3,4,5]
test_reachable7 = Graph.reachable sub_g3 5 ~?=fromList[5]
test_reachable8 = Graph.reachable sub_g3 4 ~?=fromList[4,5]
test_reachable9 = Graph.reachable g11 5 ~?=fromList[1,2,3,4,5,6,8]
test_reachable10 = Graph.reachable g11 7 ~?=fromList[1,2,3,4,6,7,8]
test_reachable11 = Graph.reachable g11 8 ~?=fromList[8]

test_bft1= Graph.bft g2 (fromList[1]) ~?= Graph {nodes = fromList [1,2,3,4],edges = fromList [ Edge 3 1, Edge 2 3, Edge 4 2 ] }
test_bft2= Graph.bft g3 (fromList[1]) ~?= Graph {nodes = fromList [1],edges = fromList [] }
test_bft3= Graph.bft g3 (fromList[3]) ~?= Graph {nodes = fromList [1,2,3,4,5],edges = fromList [Edge 2 3,Edge 1 3,Edge 4 3,Edge 5 3] }

test_isPathOf1= isPathOf (desempacota (path g2 1 4)) g2 ~?= True
test_isPathOf2= isPathOf [ Edge 3 4 , Edge 4 5] sub_g3 ~?= True
test_isPathOf3= isPathOf (desempacota (path g3 5 1)) g3 ~?= True
test_isPathOf4= isPathOf (toList(edges sub_g2)) g2 ~?= True
test_isPathOf5= isPathOf (toList(edges sub_g3)) g3 ~?= False
test_isPathOf6= isPathOf [Edge 9 5, Edge 5 3, Edge 3 1, Edge 1 2, Edge 2 6] g11 ~?= True

test_path1= path g2 5 4 ~?= Just [Edge 5 1,Edge 1 3,Edge 3 2,Edge 2 4]
test_path2= path g1 1 1 ~?= Just []
test_path3= path g8 3 1 ~?= Nothing
test_path4= path g3 1 5 ~?= Nothing
test_path5= path g11 9 6 ~?= Just [Edge 9 6]
test_path6= path g11 8 3 ~?= Nothing


test_topo1= topo g2 ~?=[fromList[5],fromList[1],fromList[3],fromList[2],fromList[4]]
test_topo2= topo g3 ~?=[fromList[3],fromList[2,5],fromList[4],fromList[1]]
test_topo3= topo g10 ~?=[fromList[5,1],fromList[2],fromList[3,4]]
test_topo4= topo g7 ~?=[fromList[1]]


main = runTestTT $ TestList [test_swap1,test_swap2,
                             test_empty,
                             test_isEmpty1,test_isEmpty2,test_isEmpty3,test_isEmpty4,
                             test_isValid1,test_isValid2,test_isValid3,test_isValid4,test_isValid5,test_isValid6,test_isValid7,test_isValid8,test_isValid9, test_isValid10,
                             test_isDAG1,test_isDAG2,test_isDAG3,test_isDAG4,test_isDAG5,test_isDAG6,test_isDAG7, test_isDag8,
                             test_isSubgraphOf1,test_isSubgraphOf2, test_isSubgraphOf3, test_isSubgraphOf4,
                             test_adj1, test_adj2, test_adj3, test_adj4, test_adj5, test_adj6, test_adj7,
                             test_isForest1,test_isForest2,test_isForest3,test_isForest4,test_isForest5, test_isForest6, test_isForest7,
                             test_transpose1,test_transpose2,test_transpose3, test_transpose4,
                             test_union1,test_union2, test_union3,
                             test_reachable1,test_reachable2,test_reachable3,test_reachable4,test_reachable5,test_reachable6,test_reachable7,test_reachable8, test_reachable9, test_reachable10, test_reachable11,
                             test_bft1,test_bft2,test_bft3,
                             test_isPathOf1,test_isPathOf2,test_isPathOf3,test_isPathOf4,test_isPathOf5,test_isPathOf6,
                             test_path1,test_path2,test_path3,test_path4, test_path5, test_path6,
                             test_topo1,test_topo2,test_topo3 {-test_topo4 -}
                            ]

--
-- Teste aleatório
--

--
-- Tarefa 2
--
-- A instância de Arbitrary para grafos definida abaixo gera grafos
-- com muito poucas arestas, como se pode constatar testando a
-- propriedade prop_valid.
-- Defina uma instância de Arbitrary menos enviesada.
-- Este problema ainda é mais grave nos geradores dag e forest que
-- têm como objectivo gerar, respectivamente, grafos que satisfazem
-- os predicados isDag e isForest. Estes geradores serão necessários
-- para testar propriedades sobre estas classes de grafos.
-- Melhore a implementação destes geradores por forma a serem menos enviesados.
--

-- Instância de Arbitrary para arestas
instance Arbitrary v => Arbitrary (Edge v) where
    arbitrary = do s <- arbitrary
                   t <- arbitrary
                   return $ Edge {source = s, target = t}

instance (Ord v, Arbitrary v) => Arbitrary (Graph v) where
    arbitrary = aux `suchThat` isValid
        where aux = do ns <- arbitrary
                       numeroEdges <- choose (0, (length ns)^2)
                       es<- geraEdges ns numeroEdges
                       return $ Graph {nodes = fromList ns, edges = fromList es}
              geraEdges _ 0 =return []
              geraEdges l n= do source <- choose (0, (length l) - 1)
                                target <- choose (0, (length l) - 1)
                                tail<- geraEdges l (n-1)
                                return $ (Edge (l!!source) (l!!target) ): tail

prop_valid :: Graph Int -> Property
prop_valid g = collect (length (edges g)) $ isValid g

-- Gerador de DAGs
dag :: (Ord v, Arbitrary v) => Gen (DAG v)
dag = arbitrary `suchThat` isDAG

prop_dag :: Property
prop_dag = forAll (dag :: Gen (DAG Int)) $ \g -> collect (length (edges g)) $ isDAG g

-- Gerador de florestas
forest :: (Ord v, Arbitrary v) => Gen (Forest v)
forest = arbitrary `suchThat` isForest

prop_forest :: Property
prop_forest = forAll (forest :: Gen (Forest Int)) $ \g -> collect (length (edges g)) $ isForest g

--
-- Tarefa 3
--
-- Defina propriedades QuickCheck para testar todas as funções
-- do módulo Graph.
--

-- Exemplo de uma propriedade QuickCheck para testar a função adj
prop_adj :: Graph Int -> Property
prop_adj g = forAll (elements $ elems $ nodes g) $ \v -> adj g v `isSubsetOf` edges g

prop_swap1 :: Eq v => Edge v -> Bool
prop_swap1 e = swap(swap e) == e

prop_swap2 :: Eq v => Edge v -> Bool
prop_swap2 e = swap e == Edge (target e) (source e)


prop_isEmpty1 :: Graph v -> Bool
prop_isEmpty1 g = if(isEmpty g)
                    then length(nodes g)==0 && length(edges g)==0
                    else True

prop_isValid1 :: Ord v =>Graph v -> Bool
prop_isValid1 g = if(isValid g)
                    then (Set.map source (edges g) `Set.union` Set.map target (edges g)) `isSubsetOf` nodes g
                    else True


-- Um grafo válido tem no máximo n² arestas (n=numero de nodos), assumindo que um nodo pode ter uma aresta a apontar para si próprio
prop_isValid2 :: Ord v =>Graph v -> Bool
prop_isValid2 g = if(isValid g)
                    then length(edges g) <= ( length (nodes g))^2
                    else True

prop_isDAG1 ::Ord v => Graph v -> Bool
prop_isDAG1 g = if(isDAG g)
                  then (arestas <= vertices*(vertices-1) `div` 2 )
                  else True
    where arestas = length (edges g)
          vertices = length (nodes g)

prop_isSubgraphOf1 :: Ord v => Graph v -> Bool
prop_isSubgraphOf1 g = Graph.empty `isSubgraphOf` g

prop_isSubgraphOf2 :: Ord v => Graph v -> Graph v -> Bool
prop_isSubgraphOf2 g1 g2 = if(g1 `isSubgraphOf` g2)
                              then (nodes g1) `isSubsetOf` (nodes g2) && (edges g1) `isSubsetOf` (edges g2)
                              else True

prop_isForest1 :: Ord v => Graph v -> v -> Bool
prop_isForest1 g v = if(isForest g)
                     then length(adj g v) <=1
                     else True

-- A mesma propriedade mas com o gerador de forests
prop_isForest2 :: Property
prop_isForest2 = forAll (forest :: Gen(Forest Int)) $ \f -> forAll (elements $ elems $ nodes f) $ \v -> length (adj f v) <= 1

prop_adj1 :: Ord v => Graph v -> v -> Bool
prop_adj1 g v = length (adj g v ) <= length (nodes g)

prop_adj2 :: Ord v => Graph v -> v -> Bool
prop_adj2 g v = if(temAdjacentes && not temLacete )
                  then toList(Set.map (\x-> length (desempacota (path g v x)) ) adjacentes) == [1]
                  else all (\e -> (source e)/=v ) (edges g) || temLacete
    where temAdjacentes = toList(adj g v) /= []
          temLacete = length(Set.filter (\(Edge s t) ->s==v || t==v) (edges g) ) >0
          adjacentes = Set.map (\e -> target e) (adj g v)

prop_transpose1 :: Ord v => Graph v -> Bool
prop_transpose1 g = transpose(transpose g) == g

prop_transpose2 :: Ord v => Graph v -> Bool
prop_transpose2 g = Set.map (source) (edges g) ==  Set.map (target) (edges (transpose g))

prop_union1 :: Ord v => Graph v -> Graph v -> Bool
prop_union1 g g' = edgesUnion <= edgesGraph
      where edgesUnion = length(edges (Graph.union g g'))
            edgesGraph = length(edges g) + length(edges g')

prop_union2 :: Ord v => Graph v  -> Bool
prop_union2 g  = g `Graph.union` Graph.empty == g

prop_union3 :: Ord v => Graph v -> Graph v -> Bool
prop_union3 g g' = isSubgraphOf g (Graph.union g g') && isSubgraphOf g' (Graph.union g g')

prop_bft1 :: Ord v => Graph v -> v -> Bool
prop_bft1 g v= nodes ( bft g (fromList[v]) ) == reachable g v

prop_reachable1 :: Ord v=> Graph v -> v -> Bool
prop_reachable1 g v = if(fromList[v] `isSubsetOf` nodes g)
                        then lr<=le
                        else True
                where le = length(nodes g)
                      lr = length(reachable g v)

prop_isPathOf1 :: Ord v => Graph v -> v -> v -> Bool
prop_isPathOf1 g o d = if (isPathOf (desempacota(path g o d)) g) == True
                      then True
                      else False

return []
tarefa3 = $quickCheckAll