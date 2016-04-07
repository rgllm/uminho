#ifndef AVL
#include "avl.h"
#define AVL
#endif

#ifndef PROD
typedef struct prod{
    char * cod;
}prod;

typedef nodo * NodoProd;

typedef struct listaP{
    NodoProd prod;
    /*int count*/
}listaP;

typedef struct Produtos{
    int num;
    NodoProd prods;
}Produtos[26];
#define PROD
#endif

typedef struct prod* Produto;
typedef struct listaP* ListaProdutos;
typedef struct Produtos* CatProdutos;


CatProdutos initCatProds();
CatProdutos insereProduto(CatProdutos catP,Produto prod);
int existeProduto(CatProdutos produtos,Produto prod);
int totalProdutos(CatProdutos produtos);
int totalProdutosLetra(CatProdutos produtos, char letra); 
void removeCatProdutos(CatProdutos produtos);

/*funções sobre o tipo Produto */
Produto criaProduto(char * codigo);
char * getCodProd(Produto p);
