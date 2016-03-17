#include "vendas.h"

void registaVenda(info * inf,char produto[], double preco, int qtd, char np, char cliente[], int mes, int filial){
    strcpy(inf->produto,produto);
    inf->preco = preco;
    inf->qtd = qtd;
    inf->np = np;
    strcpy(inf->cliente,cliente);
    inf->mes = mes;
    inf->filial = filial;
}

venda criaVenda(info inf){
    return criaNodoV(inf,NULL);
}

venda insereVenda(info cod,venda raiz){
    return insertV(cod,raiz);
}

void initV(venda * c){
    int i;
    for(i=0;i<26;i++)
        c[i]=NULL;
}

