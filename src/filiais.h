#include "avl_filial.h"

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


void initfiliais();
void carregaVenda(char * produto, char * cliente, int qtd, char tipo, int mes, double preco,int filial);