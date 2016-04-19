#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include "others.h"

#ifndef NODO
typedef struct nodo{
	char *codigo;
	struct nodo *esq;
	struct nodo *dir;
	struct nodo *pai;
	int altura;
} nodo;
#define NODO
#endif

#define PRINT_COLS 6

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
int printInOrder(nodo * raiz, int count);
void freeTree(nodo * raiz);


























