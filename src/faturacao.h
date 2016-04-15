#ifndef AVL
#include "avl.h"
#define AVL
#endif

#ifndef DUP
#include "outros.h"
#define DUP
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


faturacaoProduto naoComprados;


void initTabela();
faturacaoProduto initNComprados();
infoP criaInfoProduto(char * produto,int qtdNormal,int qtdPromocao,double totalNormal,double totalPromocao );
void registaFaturacaoProduto(infoP produto,int mes,int filial);





