#ifndef AVL
#include "avl.h"
#define AVL
#endif

typedef nodo* produto;
typedef produto catProdutos[26];
typedef nodo* listaProdutos;

produto criaProduto(char * cod);
produto insereProduto(char * cod,produto raiz);
void initP(catProdutos c);
int existeProduto(catProdutos produtos,int indice,char * cod);
int totalProdutos(catProdutos produtos);
int totalProdutosLetra(catProdutos produtos, char letra); 
void removeCatProdutos(catProdutos produtos);
