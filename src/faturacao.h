#ifndef AVL
#include "avl.h"
#define AVL
#endif
#include "avl_produtos.h"


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
void registaFaturacaoProduto(infoP produto,int filial, int mes);
void carregaProduto(infoP produto);
/*nodo getNaoComprados();*/
int contaNaoComprados(faturacaoProduto raiz);
void getQuery3(int mes,char * produto,double * totFat,int * totVendas);
int contaTotalVendas(int mesI, int mesF);
double contaTotalFaturado(int mesI, int mesF);
int getTotalVendasMes(int mes,char * produto);
int getTotalVendasMesN(int mes,char * produto);
double getTotalFaturadoMesN_filial(int mes,char * produto,int filial);
int getTotalVendasMesP(int mes,char * produto);
double getTotalFaturadoMesP_filial(int mes,char * produto, int filial);
double getTotalFaturadoMes(int mes,char * produto);
double getTotalFaturadoMesN(int mes,char * produto);
int getTotalVendasMesN_filial(int mes,char * produto, int filial);
int getTotalVendasMesP_filial(int mes,char * produto, int filial);
double getTotalFaturadoMesP(int mes,char * produto);
int contaNaoCompradosFilial(int filial);
faturacaoProduto getTotalFilial(int filial);


