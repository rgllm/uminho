#include "faturacao.h"


faturacaoProduto tabela[4][13];


void initTabela(){
    int i,j;
    for(i=0;i<4;i++)
        for(j=0;j<13;j++)
            tabela[i][j]=NULL;
}

faturacaoProduto initNComprados(){
    faturacaoProduto ret=malloc(sizeof(faturacaoProduto));
    return ret;
}

infoP criaInfoProduto(char * produto,int qtdNormal,int qtdPromocao,double totalNormal,double totalPromocao ){
    struct infoProduto *ret=(struct infoProduto*)malloc(sizeof(struct infoProduto));
    ret->produto=myStrdup(produto);
    ret->qtdNormal=qtdNormal;
    ret->qtdPromocao=qtdPromocao;
    ret->totalNormal=totalNormal;
    ret->totalPromocao=totalPromocao;
    return ret;
}

void registaFaturacaoProduto(infoP produto,int mes,int filial){
    faturacaoProduto aux;
    if(tabela[mes-1][filial-1]!=NULL)
        tabela[mes-1][filial-1]=insertNodoFat(produto,tabela[mes-1][filial-1]);
    else tabela[mes-1][filial-1]=criaNodoFat(produto,NULL);

    if(tabela[3][filial-1] != NULL){
        aux=searchProduto(tabela[3][filial-1],produto->produto);
        aux->produto->qtdNormal+=produto->qtdNormal;
        aux->produto->qtdPromocao+=produto->qtdPromocao;
        aux->produto->totalNormal+=produto->totalNormal;
        aux->produto->totalPromocao+=produto->totalPromocao;
    }
    else tabela[3][filial-1]=tabela[mes-1][filial-1];

    if(tabela[mes-1][12]!=NULL){
        aux=searchProduto(tabela[mes-1][12],produto->produto);
        aux->produto->qtdNormal+=produto->qtdNormal;
        aux->produto->qtdPromocao+=produto->qtdPromocao;
        aux->produto->totalNormal+=produto->totalNormal;
        aux->produto->totalPromocao+=produto->totalPromocao;
    }
    else tabela[mes-1][12]=tabela[mes-1][filial-1];

    if(tabela[3][12]!=NULL){
        aux=searchProduto(tabela[3][12],produto->produto);
        aux->produto->qtdNormal+=produto->qtdNormal;
        aux->produto->qtdPromocao+=produto->qtdPromocao;
        aux->produto->totalNormal+=produto->totalNormal;
        aux->produto->totalPromocao+=produto->totalPromocao;
    }
    else tabela[3][12]=tabela[mes-1][filial-1];

    
}