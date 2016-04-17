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
/*TESTADO*/
infoP criaInfoProduto(char * produto,int qtdNormal,int qtdPromocao,double totalNormal,double totalPromocao ){
    struct infoProduto *ret=(struct infoProduto*)malloc(sizeof(struct infoProduto));
    ret->produto=strdup(produto);
    ret->qtdNormal=qtdNormal;
    ret->qtdPromocao=qtdPromocao;
    ret->totalNormal=totalNormal;
    ret->totalPromocao=totalPromocao;
    return ret;
}

void registaFaturacaoProduto(infoP produto,int filial,int mes){

    nodoFaturacaoProduto aux;
    tabela[filial-1][mes-1]=insertNodoFat(produto,tabela[filial-1][mes-1]);
    aux=searchProduto(tabela[filial-1][mes-1],produto->produto);
    aux->produto->qtdNormal+=produto->qtdNormal;
    aux->produto->qtdPromocao+=produto->qtdPromocao;
    aux->produto->totalNormal+=produto->totalNormal;
    aux->produto->totalPromocao+=produto->totalPromocao;

    tabela[3][mes-1]=insertNodoFat(produto,tabela[3][mes-1]);

    aux=searchProduto(tabela[3][mes-1],produto->produto);
    aux->produto->qtdNormal+=produto->qtdNormal;
    aux->produto->qtdPromocao+=produto->qtdPromocao;
    aux->produto->totalNormal+=produto->totalNormal;
    aux->produto->totalPromocao+=produto->totalPromocao;

    tabela[filial-1][12]=insertNodoFat(produto,tabela[filial-1][12]);

    aux=searchProduto(tabela[filial-1][12],produto->produto);
    aux->produto->qtdNormal+=produto->qtdNormal;
    aux->produto->qtdPromocao+=produto->qtdPromocao;
    aux->produto->totalNormal+=produto->totalNormal;
    aux->produto->totalPromocao+=produto->totalPromocao;

    tabela[3][12]=insertNodoFat(produto,tabela[3][12]);

    aux=searchProduto(tabela[3][12],produto->produto);
    aux->produto->qtdNormal+=produto->qtdNormal;
    aux->produto->qtdPromocao+=produto->qtdPromocao;
    aux->produto->totalNormal+=produto->totalNormal;
    aux->produto->totalPromocao+=produto->totalPromocao;

}

/*

nodo getNaoComprados(){
    nodo NaoComprados;
    return NaoComprados(NaoComprados, tabela[3][12]);
}

nodo NaoComprados(nodo NaoComprados, faturacaoProduto totais){
if(raiz==NULL) return NULL;
nodo pai = NaoComprados;
infoP produto = totais->produto;
if(produto->qtdPromocao==0 && produto->qtdNormal==0){
    if(NaoComprados==NULL){
        NaoComprados=criaNodo(produto->produto,NULL);
    }
    else{
        NaoComprados=insert(produto->produto, NaoComprados);
    }
}
NaoComprados=NaoComprados(NaoComprados, totais->esq);
NaoComprados=NaoComprados(NaoComprados, totais->dir);
return pai;
}

*/

/*
int getNaoComprados(){
    return contaNaoComprados(tabela[3][12]);
}

int contaNaoComprados(faturacaoProduto raiz){
    if(raiz==NULL)
        return 0;
    infoP aux=raiz->produto;
    if(aux->qtdPromocao==0 && aux->qtdNormal==0)
        return (1 + contaNaoComprados(raiz->esq) + contaNaoComprados(raiz->dir));
    return contaNaoComprados(raiz->esq) + contaNaoComprados(raiz->dir);
}
*/
void carregaProduto(infoP produto){
    int i,j;
     for(i=0;i<4;i++)
            for(j=0;j<13;j++){
                if(tabela[i][j]){

                    tabela[i][j]=insertNodoFat(produto,tabela[i][j]);
                }
                else {
                    tabela[i][j]=criaNodoFat(produto,NULL);

                }
            }
}

double getTotalFaturado(int mes,char * produto){
    faturacaoProduto nodo=searchProduto(tabela[3][mes-1],produto);
    if(nodo==NULL) return -1;
    infoP aux= nodo->produto;
    return aux->totalNormal + aux->totalPromocao;

}

int getTotalVendas(int mes,char * produto){
    faturacaoProduto nodo=searchProduto(tabela[3][mes-1],produto);
    if(nodo==NULL) return -1;
    infoP aux= nodo->produto;
    return aux->qtdNormal + aux->qtdPromocao;

}


void getQuery3(int mes,char * produto,double * totFat,int * totVendas){
    faturacaoProduto nodo=searchProduto(tabela[3][mes-1],produto);
    if(nodo==NULL) totFat=-1;
    else{
        infoP aux= nodo->produto;
        *totFat=aux->totalNormal+aux->totalPromocao;
        *totVendas=aux->qtdNormal+aux->qtdPromocao;
    }
}

double contaTotFat(faturacaoProduto raiz){
    if(raiz==NULL) return 0;
    else {
        infoP produto = raiz->produto;
        double faturacao;
        faturacao=produto->totalNormal+produto->totalPromocao;
        return faturacao + contaTotFat(raiz->esq) + contaTotFat(raiz->dir);
    }
}

int contaTotVendas(faturacaoProduto raiz){
    if(raiz==NULL) return 0;
    else {
        infoP produto = raiz->produto;
        int vendas;
        vendas=(produto->qtdNormal+produto->qtdPromocao);
        return vendas + contaTotVendas(raiz->esq) + contaTotVendas(raiz->dir);
    }
}

void getQuery6(int mesI, int mesF, double * totFat, int * totVendas){
    int i;
    *totFat=0;
    *totVendas=0;
    for(i=mesI-1;i<mesF;i++){

        *totFat+=contaTotFat(tabela[3][i]);
        *totVendas+=contaTotVendas(tabela[3][i]);
    }

}



