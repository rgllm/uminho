#ifndef AVL
#include "avl.h"
#define AVL
#endif

typedef struct prod* Produto;
typedef struct listaP* ListaProdutos;
typedef struct catProdutos* CatProdutos;
typedef nodo * nodoProduto;


/*Funções sobre o tipo CatProdutos */

CatProdutos initCatProdutos();
CatProdutos insereProduto(CatProdutos catP,Produto prod);
int existeProduto(CatProdutos catP,Produto prod);
int totalProdutos(CatProdutos catP);
int totalProdutosLetra(CatProdutos catP, char letra); 
void removeCatProdutos(CatProdutos catP);
nodoProduto getAVLProd(CatProdutos p,int lP);

/* Funções sobre o tipo Produto */

Produto criaProduto(char * codigo);
char * getCodProd(Produto p);

