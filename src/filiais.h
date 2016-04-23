#include "avl_filial.h"
#ifndef AVL
#include "avl.h"
#define AVL
#endif

#ifndef DUP
#include "others.h"
#define strdup(x) my_strdup(x)
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
int existeClienteF(nodo *clientesFiliais);
int procuraClientes(nodo *clientesFiliais, int count);
void carregaVenda(char * cliente, char * produto, int qtd, char tipo, int mes, double preco,int filial);
void carregaQtd(int filial, char * cliente, int tabela[12][3]);
int addProduto(char * prod, int qtd, char * * * produtos, int * * quant, int t);
int carregaCompra(int filial, char * cliente, int mes, char * * * produtos, int * * quant, int t);
int determinaClientes(nodoFilial nodo, char * prod, char * * * clientes, char * * tipo, int t);
nodo * comparaAnterior(nodoFilial anterior,nodoFilial atual,nodo * ret);
nodo * comparaAnterior2(nodo * anterior,nodoFilial atual,nodo * ret);
nodo * compraramTodasFiliais();
void swapString2(char * * x, char * * y);
void swapDouble(double *x,double *y);
void carregaMaxValor(char * cliente, char * * produtos, double * valor);
int totalClientesFilial(char * produto, int filial);