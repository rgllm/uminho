#ifndef CLIENTES_H
#define CLIENTES_H

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "listaligada.h"

typedef struct nodoc
{
	char valor[7];
	struct nodoc *esq;
	struct nodoc *dir;
	int altura;
} * NodoC;

int alturaC(NodoC N);

NodoC novonodoC(char * valor);

NodoC dirRotateC(NodoC y);

NodoC esqRotateC(NodoC x);

int getBalanceC(NodoC N);

NodoC insertC(NodoC nodo, char * valor);

int contaNodosC(NodoC avl);

ListaLigada clientesParaLista(ListaLigada l, NodoC c);

int existeC(NodoC n, char * nom);

#endif
