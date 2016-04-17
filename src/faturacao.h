#ifndef AVL
#include "avl.h"
#define AVL
#endif
#include "avl_vendas.h"


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


faturacaoProduto naoComprados;


void initTabela();
faturacaoProduto initNComprados();
infoP criaInfoProduto(char * produto,int qtdNormal,int qtdPromocao,double totalNormal,double totalPromocao );
void registaFaturacaoProduto(infoP produto,int filial, int mes);
void carregaProduto(infoP produto);
/*nodo getNaoComprados();*/
int contaNaoComprados(faturacaoProduto raiz);
void getQuery3(int mes,char * produto,double * totFat,int * totVendas);
int contaTotalVendas(int mesI, int mesF);
double contaTotalFaturado(int mesI, int mesF);
int getTotalVendasMes(int mes,char * produto);
double getTotalFaturadoMes(int mes,char * produto);




