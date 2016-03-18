#include <stdlib.h>
#include <stdio.h>
#include <string.h>

#ifndef INFO
#define INFO
typedef struct info{
    char produto[8];
    double preco;
    int qtd;
    char np;
    char cliente[8];
    int mes;
    int filial;
}info;
#endif

typedef struct nodo{
	char codigo[8];
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
void print_tree_indent(nodo *nodo, int indent);
void print_tree(nodo *root,int level);
void padding ( char ch, int n );


/* ARVORE DE VENDAS */

typedef struct nodoV
{
	info inf;
	struct nodoV *esq;
	struct nodoV *dir;
	struct nodoV *pai;
	int altura;
} nodoV;

nodoV *searchV(nodoV *raiz, info inf);
int alturaV(nodoV *raiz);
void ajustaAlturaV(nodoV *raiz);
nodoV *rodaDirV(nodoV *raiz);
nodoV *rodaEsqV(nodoV *raiz);
nodoV *criaNodoV(info inf, nodoV *pai);
nodoV *balanceV(nodoV *raiz);
nodoV *insertV(info inf,nodoV *raiz);
int vendaCmp(info v1,info v2);
