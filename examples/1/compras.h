#ifndef COMPRAS_H
#define COMPRAS_H

#include<stdio.h>
#include<string.h>
#include<stdlib.h>
#include"listaligada.h"
#include"clientes.h"

typedef struct listaCompras {
    char produto[7];
    char modo;
    double preco;
    int quantidade;
    struct listaCompras *prox;
} *ListaCompras;

typedef struct compras {
    char cliente[6];
    ListaCompras lista;
    struct compras *esq,*dir;
} *Compras;

ListaCompras initListaCompras(ListaCompras l, char *p, char m, double pr, int q);

Compras initCompras(Compras c, char *cl, char *p, char m, double pr, int q);

ListaCompras insereElemListaCompras(ListaCompras l, char *p, char m, double pr, int q);

Compras insereCompra(Compras c, char *cl, char *p, char m, double pr, int q);

int comprimentoListaCompras(ListaCompras l);

int clienteComprouProduto(ListaCompras l, char *p);

int clienteComprouProdutoModo(ListaCompras l,char *p, char m);

ListaLigada listaClientesCompraramProduto(Compras c, ListaLigada l, char m, char *p);

ListaCompras juntaComprasPorProduto(ListaCompras res, ListaCompras l);

ListaCompras juntaComprasPorProduto2(ListaCompras res, ListaCompras l);

ListaCompras actualizaListaCompras(ListaCompras l, int q, char *p);

ListaCompras insereComprasOrdenadas(ListaCompras l, ListaCompras res);

ListaLigada listaLigadaDeCompras(ListaCompras l, ListaLigada r);

ListaCompras devolveListaComprasCliente(Compras c, ListaCompras l, char *cl);

ListaLigada comprasDosClientesParaLista(ListaLigada l, Compras c);

int contaComprasMes(Compras c);

int contaClientes(Compras c);

ListaLigada tresProdutosMaisComprados(ListaCompras l, ListaLigada p, int *m1, int *m2, int *m3);

int existeProduto(Compras c, char *p);

int existeCliente(Compras c, char *p);

NodoC clientesQueCompraram(NodoC cl, Compras c);

NodoC insereClientesQueCompraramNoMes(NodoC cl, Compras c);

/* Gestão da AVL */

int alturaCP(Compras nodo);

int fatorCP(Compras nodo);

Compras rotacao_dir_dirCP(Compras pai);

Compras rotacao_esq_esqCP(Compras pai);

Compras rotacao_dir_esqCP(Compras pai);

Compras rotacao_esq_dirCP(Compras pai);

Compras balancearCP(Compras nodo);


#endif
