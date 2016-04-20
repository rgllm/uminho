#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#ifndef AVL
#include "avl.h"
#define AVL
#endif
#ifndef INFOF
typedef struct venda{
    char * cliente;
    int qtd;
    double preco;
    char tipo;
    int mes;
}venda;

typedef struct infoF{
    char * produto;
    venda * vendas;
    int nVendas;
}* infoF;


#define INFOF
#endif


typedef struct nodoFilial{
    infoF produto;
    struct nodoFilial *esq;
    struct nodoFilial *dir;
    struct nodoFilial *pai;
    int altura;
} *nodoFilial;

nodoFilial procuraPFilial(nodoFilial raiz, char * codigo);
int alturaVFilial(nodoFilial raiz);
void ajustaAlturaVFilial(nodoFilial raiz);
nodoFilial rodaDirVFilial(nodoFilial raiz);
nodoFilial rodaEsqVFilial(nodoFilial raiz);
void infoFcopy(infoF p1,infoF p2);
nodoFilial criaNodoFilial(infoF produto, nodoFilial pai);
nodoFilial balanceVFilial(nodoFilial raiz);
nodoFilial insertNodoFilial(infoF produto,nodoFilial raiz);
