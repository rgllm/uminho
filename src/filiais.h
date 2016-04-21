#include "avl_filial.h"
#ifndef AVL
#include "avl.h"
#define AVL
#endif


#ifndef INFOF
typedef struct venda{
    char * produto;
    int qtd;
    double preco;
    char tipo;
    int mes;
}venda;

typedef struct infoF{
    char * cliente;
    venda * vendas;
    int nVendas;
}* infoF;


#define INFOF
#endif


void initfiliais();
void carregaVenda(char * cliente, char * produto, int qtd, char tipo, int mes, double preco,int filial);
int percorreClientes();