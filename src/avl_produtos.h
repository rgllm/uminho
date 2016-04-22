#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#ifndef AVL
#include "avl.h"
#define AVL
#endif


#ifndef INFOPROD
typedef struct infoProduto{
    char * produto;
    int qtdNormal;
    int qtdPromocao;
    double totalNormal;
    double totalPromocao;
}* infoP;
#define INFOPROD
#endif

/* ARVORE DE VENDAS */

typedef struct nodoFaturacaoProduto{
    infoP produto;
    struct nodoFaturacaoProduto *esq;
    struct nodoFaturacaoProduto *dir;
    struct nodoFaturacaoProduto *pai;
    int altura;
} *nodoFaturacaoProduto;


nodoFaturacaoProduto searchProduto(nodoFaturacaoProduto raiz, char * produto);
int alturaV(nodoFaturacaoProduto raiz);
void ajustaAlturaV(nodoFaturacaoProduto raiz);
nodoFaturacaoProduto rodaDirV(nodoFaturacaoProduto raiz);
nodoFaturacaoProduto rodaEsqV(nodoFaturacaoProduto raiz);
void infoProdutoCopy(infoP p1,infoP p2);
nodoFaturacaoProduto criaNodoFat(infoP produto, nodoFaturacaoProduto pai);
nodoFaturacaoProduto balanceV(nodoFaturacaoProduto raiz);
nodoFaturacaoProduto insertNodoFat(infoP produto,nodoFaturacaoProduto raiz);
int printNaoComprados(nodoFaturacaoProduto raiz, int count);