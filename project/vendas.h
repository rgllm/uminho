#ifndef MAXBUFF
#include "avl.h"
#define MAXBUFF 128
#endif

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




typedef nodoV * venda;
typedef venda catVendas[26];

void registaVenda(info * inf,char produto[], double preco, int qtd, char np, char cliente[], int mes, int filial);
venda criaVenda(info inf);
venda insereVenda(info cod,venda raiz);
void initV(catVendas c);


