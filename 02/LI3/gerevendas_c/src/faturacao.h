#ifndef AVL
#include "avl.h"
#define AVL
#endif
#include "avl_faturacao.h"

#ifndef DUP
#include "others.h"
#define strdup(x) my_strdup(x)
#endif

#ifndef INFOPROD
typedef struct infoProduto{
    char * produto;
    int qtdNormal;
    int qtdPromocao;
    double totalNormal;
    double totalPromocao;
}* infoP;
#define INFOPROD
#endif

typedef nodoFaturacaoProduto faturacaoProduto;


void initTabela();
faturacaoProduto initNComprados();
infoP criaInfoProduto(char * produto,int qtdNormal,int qtdPromocao,double totalNormal,double totalPromocao );
void registaFaturacaoProduto(infoP produto,int filial,int mes);
void carregaProduto(infoP produto);
double getTotalFaturadoMes(int mes,char * produto);
double getTotalFaturadoMesN(int mes,char * produto);
double getTotalFaturadoMesN_filial(int mes,char * produto,int filial);
double getTotalFaturadoMesP(int mes,char * produto);
double getTotalFaturadoMesP_filial(int mes,char * produto, int filial);
int getTotalVendasMes(int mes,char * produto);
int getTotalVendasMesN(int mes,char * produto);
int getTotalVendasMesN_filial(int mes,char * produto, int filial);
int getTotalVendasMesP(int mes,char * produto);
int getTotalVendasMesP_filial(int mes,char * produto, int filial);
double contaTotalFaturadoMes(faturacaoProduto raiz);
double contaTotalFaturado(int mesI, int mesF);
int contaTotalVendasMes(faturacaoProduto raiz);
int contaTotalVendas(int mesI, int mesF);
int contaNaoComprados(nodoFaturacaoProduto raiz);
int contaNaoCompradosFilial(int filial);
nodo * naoComprados(nodo * nComprados, faturacaoProduto totais);
nodo * getNaoCompradosFilial(int filial);
faturacaoProduto getTotalFilial(int filial);
void printNaoComprados(int filial);
void preencheProdutos(char * * produtos, int * qtd, int n, nodoFaturacaoProduto nodo);
int totalVendasPFilial(char * produto, int filial);
