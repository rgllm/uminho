#include "filiais.h"

nodoFilial filial1,filial2,filial3;

void initfiliais(){
   filial1=filial2=filial3=NULL;
}


void carregaVenda(char * produto, char * cliente, int qtd, char tipo, int mes, double preco,int filial){
    infoF aux;
    if(filial==1){

        if(filial1==NULL){
            aux=malloc(sizeof(infoF));
            aux->produto=strdup(produto);
            aux->nVendas=1;
            aux->vendas=malloc(sizeof(venda));
                        aux->vendas[0].cliente=strdup(cliente);
                        aux->vendas[0].qtd=qtd;
                        aux->vendas[0].preco=preco;
                        aux->vendas[0].tipo=tipo;
                        aux->vendas[0].mes=mes;
            filial1=criaNodoFilial(aux,NULL);
        }
        else{
            nodoFilial nodo=procuraPFilial(filial1,produto);
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
                filial1=insertNodoFilial(aux,filial1);
            }
            else{
                nodo->produto->vendas=realloc(nodo->produto->vendas,(nodo->produto->nVendas+1)*sizeof(struct venda));
                nodo->produto->vendas[nodo->produto->nVendas].cliente=strdup(cliente);
                nodo->produto->vendas[nodo->produto->nVendas].qtd=qtd;
                nodo->produto->vendas[nodo->produto->nVendas].preco=preco;
                nodo->produto->vendas[nodo->produto->nVendas].tipo=tipo;
                nodo->produto->vendas[nodo->produto->nVendas].mes=mes;
            }
        }
    }

    if(filial==2){
        if(filial2==NULL){
            aux=malloc(sizeof(infoF));
            aux->produto=strdup(produto);
            aux->nVendas=1;
            aux->vendas=malloc(sizeof(venda));
             aux->vendas[0].cliente=strdup(cliente);
             aux->vendas[0].qtd=qtd;
             aux->vendas[0].preco=preco;
            aux->vendas[0].tipo=tipo;
            aux->vendas[0].mes=mes;
            filial2=criaNodoFilial(aux,NULL);
        }
        else{
            nodoFilial nodo=procuraPFilial(filial2,produto);
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
                filial2=insertNodoFilial(aux,filial2);
            }
            else{
                nodo->produto->vendas=realloc(nodo->produto->vendas,(nodo->produto->nVendas+1)*sizeof(struct venda));
                nodo->produto->vendas[nodo->produto->nVendas].cliente=strdup(cliente);
                nodo->produto->vendas[nodo->produto->nVendas].qtd=qtd;
                nodo->produto->vendas[nodo->produto->nVendas].preco=preco;
                nodo->produto->vendas[nodo->produto->nVendas].tipo=tipo;
                nodo->produto->vendas[nodo->produto->nVendas].mes=mes;
            }
        }
    }
    if(filial==3){
        if(filial3==NULL){
            aux=malloc(sizeof(infoF));
            aux->produto=strdup(produto);
            aux->nVendas=1;
            aux->vendas=malloc(sizeof(venda));
                        aux->vendas[0].cliente=strdup(cliente);
                        aux->vendas[0].qtd=qtd;
                        aux->vendas[0].preco=preco;
                        aux->vendas[0].tipo=tipo;
                        aux->vendas[0].mes=mes;
            filial3=criaNodoFilial(aux,NULL);
        }
        else{
            nodoFilial nodo=procuraPFilial(filial3,produto);
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
                filial3=insertNodoFilial(aux,filial3);
            }
            else{
                nodo->produto->vendas=realloc(nodo->produto->vendas,(nodo->produto->nVendas+1)*sizeof(struct venda));
                nodo->produto->vendas[nodo->produto->nVendas].cliente=strdup(cliente);
                nodo->produto->vendas[nodo->produto->nVendas].qtd=qtd;
                nodo->produto->vendas[nodo->produto->nVendas].preco=preco;
                nodo->produto->vendas[nodo->produto->nVendas].tipo=tipo;
                nodo->produto->vendas[nodo->produto->nVendas].mes=mes;
            }
        }
    }
}








