#include <stdlib.h>
#include <stdio.h>
#include <string.h>


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

typedef struct nodo{
	char *codigo;
	struct nodo *esq;
	struct nodo *dir;
	struct nodo *pai;
	int altura;
} nodo;

int max ( int a, int b );
nodo *search(nodo *raiz, char codigo[]);
int altura(nodo *raiz);
void ajustaAltura(nodo *raiz);
nodo *rodaDir(nodo *raiz);
nodo *rodaEsq(nodo *raiz);
nodo *criaNodo(char codigo[], nodo *pai);
nodo *balance(nodo *raiz);
nodo *insert(char codigo[],nodo *raiz);
int conta(nodo * raiz);
void printInOrder(nodo * raiz);
void freeTree(nodo * raiz);


/* ARVORE DE VENDAS */

typedef struct nodoFaturacaoProduto{
	infoP produto;
	struct nodoFaturacaoProduto *esq;
	struct nodoFaturacaoProduto *dir;
	struct nodoFaturacaoProduto *pai;
	int altura;
} *nodoFaturacaoProduto;

void printInOrderProd(nodoFaturacaoProduto raiz);
nodoFaturacaoProduto searchProduto(nodoFaturacaoProduto raiz, char * produto);
int alturaV(nodoFaturacaoProduto raiz);
void ajustaAlturaV(nodoFaturacaoProduto raiz);
nodoFaturacaoProduto rodaDirV(nodoFaturacaoProduto raiz);
nodoFaturacaoProduto rodaEsqV(nodoFaturacaoProduto raiz);
void infoProdutoCopy(infoP p1,infoP p2);
nodoFaturacaoProduto criaNodoFat(infoP produto, nodoFaturacaoProduto pai);
nodoFaturacaoProduto balanceV(nodoFaturacaoProduto raiz);
nodoFaturacaoProduto insertNodoFat(infoP produto,nodoFaturacaoProduto raiz);


























