#ifndef AVL
#include "avl.h"
#define AVL
#endif

#ifndef PRODUTOS

typedef struct prod{
    char * cod;
}prod;

typedef nodo * nodoProd;

typedef struct listaP{
    nodoProd prod;
    /* TODO - size of lista */
}listaP;

typedef struct catProdutos{
    int size;
    nodoProd produtos;
}catProdutos[26];
#define PRODUTOS
#endif


typedef struct prod* Produto;
typedef struct listaP* ListaProdutos;
typedef struct catProdutos* CatProdutos;


/*Funções sobre o tipo CatProdutos */

CatProdutos initCatProdutos();
CatProdutos insereProduto(CatProdutos catP,Produto prod);
int existeProduto(CatProdutos catP,Produto prod);
int totalProdutos(CatProdutos catP);
int totalProdutosLetra(CatProdutos catP, char letra); 
void removeCatProdutos(CatProdutos catP);

/* Funções sobre o tipo Produto */

Produto criaProduto(char * codigo);
char * getCodProd(Produto p);

