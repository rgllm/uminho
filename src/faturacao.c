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
    faturacaoProduto aux;

    tabela[filial-1][mes-1]=insertNodoFat(produto,tabela[filial-1][mes-1]);
printf("--------------------------------\n");
    aux=searchProduto(produto->produto,tabela[filial-1][mes-1]);
    aux->produto->qtdNormal+=produto->qtdNormal;
    aux->produto->qtdPromocao+=produto->qtdPromocao;
    aux->produto->totalNormal+=produto->totalNormal;
    aux->produto->totalPromocao+=produto->totalPromocao;

    tabela[3][mes-1]=insertNodoFat(produto,tabela[3][mes-1]);

    aux=searchProduto((tabela[3][mes-1]),produto->produto);
    aux->produto->qtdNormal+=produto->qtdNormal;
    aux->produto->qtdPromocao+=produto->qtdPromocao;
    aux->produto->totalNormal+=produto->totalNormal;
    aux->produto->totalPromocao+=produto->totalPromocao;

    tabela[filial-1][12]=insertNodoFat(produto,tabela[filial-1][12]);

    aux=searchProduto((tabela[filial-1][12]),produto->produto);
    aux->produto->qtdNormal+=produto->qtdNormal;
    aux->produto->qtdPromocao+=produto->qtdPromocao;
    aux->produto->totalNormal+=produto->totalNormal;
    aux->produto->totalPromocao+=produto->totalPromocao;

    tabela[3][12]=insertNodoFat(produto,tabela[3][12]);

    aux=searchProduto((tabela[3][12]),produto->produto);
    aux->produto->qtdNormal+=produto->qtdNormal;
    aux->produto->qtdPromocao+=produto->qtdPromocao;
    aux->produto->totalNormal+=produto->totalNormal;
    aux->produto->totalPromocao+=produto->totalPromocao;
}
/*

    printf("    inserir produto em tabela[%d][%d] \n",filial-1,mes-1);

    if(tabela[filial-1][mes-1]){
        printf("        tabela[%d][%d] NAO NULO \n",filial-1,mes-1);
        tabela[filial-1][mes-1]=insertNodoFat(produto,tabela[filial-1][mes-1]);
    }
    else {
        printf("      tabela[%d][%d] NULO \n",filial-1,mes-1);
        tabela[filial-1][mes-1]=criaNodoFat(produto,NULL);
    }
    printf("    inserir produto em tabela[3][%d] \n",mes-1);
    if(tabela[3][mes-1]){
        printf("      tabela[3][%d] NAO NULO \n",mes-1);
        if(aux=searchProduto((tabela[3][mes-1]),produto->produto) != NULL){
            aux->produto->qtdNormal+=produto->qtdNormal;
            aux->produto->qtdPromocao+=produto->qtdPromocao;
            aux->produto->totalNormal+=produto->totalNormal;
            aux->produto->totalPromocao+=produto->totalPromocao;
        }
        else {
            insertNodoFat(produto,(tabela[3][mes-1]));
        }
    }
    else {printf("      tabela[3][%d] NULO \n",filial-1);
        tabela[3][mes-1]=criaNodoFat(produto,NULL);
    }

    printf("    inserir produto em tabela[%d][12] \n",filial-1);
    if(tabela[filial-1][12]){
        printf("      tabela[%d][12] NAO NULO \n",mes-1);
        if(aux=searchProduto((tabela[filial-1][12]),produto->produto)){
            aux->produto->qtdNormal+=produto->qtdNormal;
            aux->produto->qtdPromocao+=produto->qtdPromocao;
            aux->produto->totalNormal+=produto->totalNormal;
            aux->produto->totalPromocao+=produto->totalPromocao;
        }
        else {insertNodoFat(produto,tabela[filial-1][12]);}
    }
    else tabela[filial-1][12]=criaNodoFat(produto,NULL);

    if(tabela[3][12]!=NULL){
        if(aux=searchProduto((tabela[3][12]),produto->produto)){
            aux->produto->qtdNormal+=produto->qtdNormal;
            aux->produto->qtdPromocao+=produto->qtdPromocao;
            aux->produto->totalNormal+=produto->totalNormal;
            aux->produto->totalPromocao+=produto->totalPromocao;
        }
        else insertNodoFat(produto,(tabela[3][12]));
    }
    else tabela[3][12]=criaNodoFat(produto,NULL);

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

int aux(nodoFaturacaoProduto x){
    if(x==NULL) return 0;
    return 1 + aux(x->esq) + aux(x->dir);
}


/*printf("P2:\n%s\n%d\n%d\n%f\n%f\n",p2->produto,p2->qtdPromocao,p2->qtdNormal,p2->totalPromocao,p2->totalNormal );*/
