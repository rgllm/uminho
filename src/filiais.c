#include "filiais.h"

nodoFilial filial1,filial2,filial3;

void initfiliais(){
   filial1=filial2=filial3=NULL;
}

int constaFilial(char * cliente, nodoFilial raiz){
    int i;
    if (raiz==NULL) return 0;
    for(i=0; i<raiz->produto->nVendas;i++)
        if(strcmp(raiz->produto->vendas[i].cliente, cliente)==0)
            return 1;
    return (constaFilial (cliente, raiz->esq) || constaFilial (cliente, raiz->dir));
}


int percorreClientes(nodo * raiz){
    if(raiz==NULL) return 0;
    char * cliente=raiz->codigo;
    if (constaFilial(cliente, filial1) && constaFilial(cliente, filial2) && constaFilial(cliente, filial3)){
        return 1+ percorreClientes(raiz->esq)+percorreClientes(raiz->dir);
    }
    return percorreClientes(raiz->esq)+percorreClientes(raiz->dir);
}



void carregaVenda(char * produto, char * cliente, int qtd, char tipo, int mes, double preco,int filial){
    infoF aux;
    nodoFilial fil;
    if(filial==1) fil=filial1;
    if(filial==2) fil=filial2;
    if(filial==3) fil=filial3;
    if(fil==NULL){
        aux=malloc(sizeof(infoF));
        aux->produto=strdup(produto);
        aux->nVendas=1;
        aux->vendas=malloc(sizeof(venda));
        aux->vendas[0].cliente=strdup(cliente);
        aux->vendas[0].qtd=qtd;
        aux->vendas[0].preco=preco;
        aux->vendas[0].tipo=tipo;
        aux->vendas[0].mes=mes;
        fil=criaNodoFilial(aux,NULL);
    }
    else{
        nodoFilial nodo=procuraPFilial(fil,produto);
        if(nodo==NULL){
            aux=malloc(sizeof(infoF));
            aux->produto=strdup(produto);
            aux->nVendas=1;
            aux->vendas=malloc(sizeof(venda));
            aux->vendas[0].cliente=strdup(cliente);
            aux->vendas[0].qtd=qtd;
            aux->vendas[0].preco=preco;
            aux->vendas[0].tipo=tipo;
            aux->vendas[0].mes=mes;
            fil=insertNodoFilial(aux,fil);
        }
        else{
            nodo->produto->vendas=realloc(nodo->produto->vendas,(nodo->produto->nVendas+1)*sizeof(struct venda));
            nodo->produto->vendas[nodo->produto->nVendas].cliente=strdup(cliente);
            nodo->produto->vendas[nodo->produto->nVendas].qtd=qtd;
            nodo->produto->vendas[nodo->produto->nVendas].preco=preco;
            nodo->produto->vendas[nodo->produto->nVendas].tipo=tipo;
            nodo->produto->vendas[nodo->produto->nVendas].mes=mes;
            nodo->produto->nVendas++;
        }
    }
    if(filial==1) filial1=fil;
    if(filial==2) filial2=fil;
    if(filial==3) filial3=fil;
}

    







