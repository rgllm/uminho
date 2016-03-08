#ifndef PRODUTOS_H
#define PRODUTOS_H

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "listaligada.h"

typedef struct nodop
{
	char valor[8];
	struct nodop *esq;
	struct nodop *dir;
	int altura;
} * NodoP;


int alturaP(NodoP N);

NodoP novoNodoP(char * valor);

NodoP dirRotateP(NodoP y);
 
NodoP esqRotateP(NodoP x);

int getBalanceP(NodoP N);
 
NodoP insertP(NodoP nodo, char * valor);

int contaNodosP(NodoP avl);

void listarP(NodoP n, ListaLigada l);

ListaLigada produtosParaLista(ListaLigada l, NodoP c);

int existeP(NodoP n, char * nom);

#endif
