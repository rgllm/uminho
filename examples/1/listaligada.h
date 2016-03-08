#ifndef LLIGADA_H
#define LLIGADA_H

#include <stdio.h>
#include <string.h>
#include <stdlib.h>

/**
 * Vai servir para passar as árvores de produtos
 * e clientes para listas duplamente ligadas de
 * modo a que seja mais facil imprimir
 */

typedef struct listaligada {
    char codigo[7];
    struct listaligada *ant,*prox;
} *ListaLigada;

ListaLigada initLista(ListaLigada l, char *c);

ListaLigada insereElemento(ListaLigada l, char *c);

int comprimentoListaLigada(ListaLigada l);

ListaLigada insereElemNaoRepetido(ListaLigada l, char *c);

int existeElemento(ListaLigada l, char *c);

ListaLigada removeElemento(ListaLigada l, char *c);

ListaLigada interseccaoListas(ListaLigada a, ListaLigada b);

ListaLigada concatenaListas(ListaLigada a, ListaLigada b);

ListaLigada juntaListas(ListaLigada res, ListaLigada a);


#endif
