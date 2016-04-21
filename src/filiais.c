#include "filiais.h"

nodoFilial filial1,filial2,filial3;

void initfiliais(){
   filial1=filial2=filial3=NULL;
}

/*int constaFilial(char * cliente, nodoFilial raiz){
    int i;
    if (raiz==NULL) return 0;
    for(i=0; i<raiz->produto->nVendas;i++)
        if(strcmp(raiz->produto->vendas[i].cliente, cliente)==0)
            return 1;
    return (constaFilial (cliente, raiz->esq) || constaFilial (cliente, raiz->dir));
} */

/*
void compraramFilial1(nodoFilial raiz,nodo * ret){
    int i;
    if(raiz==NULL) return;
    for(i=0;i<raiz->cliente->nVendas;i++){
        if(ret==NULL)
            ret=criaNodo(raiz->cliente->vendas[i].produto,NULL);
        else if(search(ret,raiz->cliente->vendas[i].produto)==NULL)
            ret=insert(raiz->cliente->vendas[i].produto,ret);
    }
    compraramFilial1(raiz->esq,ret);
    compraramFilial1(raiz->dir,ret);

}

nodo * compraramFilial(nodoFilial raiz, nodo * fil){
    int i;
    nodo * ret;
    if(raiz==NULL) return NULL;
    for(i=0;i<raiz->cliente->nVendas;i++){
        if(ret==NULL && search(fil,raiz->cliente->vendas[i].produto))
            ret=criaNodo(raiz->cliente->vendas[i].produto,NULL);
        else if(search(ret,raiz->cliente->vendas[i].produto)==NULL && search(fil,raiz->cliente->vendas[i].produto))
            ret=insert(raiz->cliente->vendas[i].produto,ret);
    }
    ret=compraramFilial(raiz->esq,fil);
    ret=compraramFilial(raiz->dir,fil);

   return ret;
}

*/
/*
int percorreClientes(){
    nodo * fil1,*fil2,*fil3;
    compraramFilial1(filial1,fil1);
    printf("asdf\n");
    printf("%s\n",fil1->codigo);
    fil2=compraramFilial(filial2,fil1);
    printf("%s\n",fil2->codigo);
    fil3=compraramFilial(filial3,fil2);
    printf("asdf\n");
    return altura(fil3);
}

*/

void carregaVenda(char * cliente, char * produto, int qtd, char tipo, int mes, double preco,int filial){
    infoF aux;
    nodoFilial fil;
    if(filial==1) fil=filial1;
    if(filial==2) fil=filial2;
    if(filial==3) fil=filial3;
    if(fil==NULL){
        aux=malloc(sizeof(infoF));
        aux->cliente=strdup(cliente);
        aux->nVendas=1;
        aux->vendas=malloc(sizeof(venda));
        aux->vendas[0].produto=strdup(produto);
        aux->vendas[0].qtd=qtd;
        aux->vendas[0].preco=preco;
        aux->vendas[0].tipo=tipo;
        aux->vendas[0].mes=mes;
        fil=criaNodoFilial(aux,NULL);
    }
    else{
        nodoFilial nodo=procuraPFilial(fil,cliente);
        if(nodo==NULL){
            aux=malloc(sizeof(infoF));
            aux->cliente=strdup(cliente);
            aux->nVendas=1;
            aux->vendas=malloc(sizeof(venda));
            aux->vendas[0].produto=strdup(produto);
            aux->vendas[0].qtd=qtd;
            aux->vendas[0].preco=preco;
            aux->vendas[0].tipo=tipo;
            aux->vendas[0].mes=mes;
            fil=insertNodoFilial(aux,fil);
        }
        else{
            nodo->cliente->vendas=realloc(nodo->cliente->vendas,(nodo->cliente->nVendas+1)*sizeof(struct venda));
            nodo->cliente->vendas[nodo->cliente->nVendas].produto=strdup(produto);
            nodo->cliente->vendas[nodo->cliente->nVendas].qtd=qtd;
            nodo->cliente->vendas[nodo->cliente->nVendas].preco=preco;
            nodo->cliente->vendas[nodo->cliente->nVendas].tipo=tipo;
            nodo->cliente->vendas[nodo->cliente->nVendas].mes=mes;
            nodo->cliente->nVendas++;
        }
    }
    if(filial==1) filial1=fil;
    if(filial==2) filial2=fil;
    if(filial==3) filial3=fil;
}

void carregaQtd(int filial, char * cliente, int tabela[12][3]){
    int i;
    nodoFilial nodo;
    if (filial ==1) nodo=filial1;
    if (filial ==2) nodo=filial2;
    if (filial ==3) nodo=filial3;
    if ((nodo=procuraPFilial(nodo, cliente))!=NULL){
        for (i=0; i<nodo->cliente->nVendas; i++)
            tabela[nodo->cliente->vendas[i].mes-1][filial-1]+=nodo->cliente->vendas[i].qtd;
    }
}


int carregaCompra(int filial, char * cliente, int mes, char * * * produtos, int * * quant, int t){
    int i;
    nodoFilial nodo;
    if(filial ==1) nodo=filial1;
    if(filial ==2) nodo=filial2;
    if(filial ==3) nodo=filial3;

    if(nodo==NULL) return t;
    if((nodo=procuraPFilial(nodo, cliente))!=NULL)
        for(i=0; i<nodo->cliente->nVendas; i++)
            if(nodo->cliente->vendas[i].mes==mes){
                t=addProduto(nodo->cliente->vendas[i].produto, nodo->cliente->vendas[i].qtd, produtos, quant, t);
            }
    return t;
}



int addProduto(char * prod, int qtd, char * * * produtos, int * * quant, int t){
    int i;
    for(i=0; i<t; i++)
        if(strcmp((*produtos)[i], prod)==0)
            (*quant)[i]+=qtd;

    if(i==t){
        *produtos=realloc(*produtos,(t+1)*sizeof(char*));
        (*quant)=realloc(*quant,(t+1)*sizeof(int));
        (*quant)[t]=qtd;
        (*produtos)[t]=strdup(prod);
        t++;
    }
    return t;
}

