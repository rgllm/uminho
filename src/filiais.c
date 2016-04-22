#include "filiais.h"

nodoFilial filial1,filial2,filial3;

void initfiliais(){
   filial1=filial2=filial3=NULL;
}


int existeClienteF(nodo *clientesFiliais){
    int count=0;
    count=procuraClientes(clientesFiliais,count);
    return count;
}

int procuraClientes(nodo *clientesFiliais, int count){

if( (procuraPFilial(filial2,filial1->cliente)!=NULL) && (procuraPFilial(filial3,filial1->cliente)!=NULL)){
        insert(filial1->cliente, clientesFiliais);
        count++;
}
count+=procuraClientes(clientesFiliais->esq, count);
count+=procuraClientes(clientesFiliais->dir, count);
return count;

}
/*
int totalClientesNCompraram(CatClientes catClientes){
int count;
if(catClientes==NULL) return 0;
if(search(catClientes->clientes, filial1)==NULL && search(catClientes->clientes, filial2)==NULL && search(catClientes->clientes, filial3==NULL)) count++;
count+=totalClientesNCompraram(catClientes->esq);
count+=totalClientesNCompraram(catClientes->dir);
return count;
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


int addProduto(char * prod, int qtd, char * * * produtos, int * * quant, int t){
    int i;
    for(i=0; i<t; i++)
        if(strcmp((*produtos)[i], prod)==0)
            (*quant)[i]+=qtd;

    if(i==t){
        *produtos=realloc(*produtos,(t+1)*sizeof(char*));
        *quant=realloc(*quant,(t+1)*sizeof(int));
        (*quant)[t]=qtd;
        (*produtos)[t]=strdup(prod);
        t++;
    }
    return t;
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





nodoFilial getFilial(int filial){
    if(filial ==1) return filial1;
    if(filial ==2) return filial2;
    return filial3;
}

int determinaClientes(nodoFilial nodo, char * prod, char * * * clientes, char * * tipo, int t){
    int i;
    if(nodo==NULL) return t;
    for(i=0; i<nodo->cliente->nVendas; i++)
        if(strcmp(nodo->cliente->vendas[i].produto,prod)==0){
            *clientes=realloc(*clientes,(t+1)*sizeof(char*));
            *tipo=realloc(*tipo,(t+1)*sizeof(char));
            (*clientes)[t]=(char*)strdup(nodo->cliente->cliente);
            (*tipo)[t]=nodo->cliente->vendas[i].tipo;
            t++;
            break;
        }
    t=determinaClientes(nodo->esq, prod, clientes, tipo, t);
    t=determinaClientes(nodo->dir, prod, clientes, tipo, t);
    return t;
}


nodo * comparaAnterior(nodoFilial anterior,nodoFilial atual,nodo * ret){
    if(atual==NULL) return ret;
    if(procuraPFilial(anterior,atual->cliente->cliente)!=NULL)
        if(ret==NULL)
            ret=criaNodo(atual->cliente->cliente,NULL);
        else
            ret=insert(atual->cliente->cliente,ret);
    ret=comparaAnterior(anterior,atual->esq,ret);
    ret=comparaAnterior(anterior,atual->dir,ret);
    return ret;
}

nodo * comparaAnterior2(nodo * anterior,nodoFilial atual,nodo * ret){
    if(atual==NULL) return ret;
    if(search(anterior,atual->cliente->cliente)!=NULL)
        if(ret==NULL)
            ret=criaNodo(atual->cliente->cliente,NULL);
        else
            ret=insert(atual->cliente->cliente,ret);
    ret=comparaAnterior2(anterior,atual->esq,ret);
    ret=comparaAnterior2(anterior,atual->dir,ret);
    return ret;
}

nodo * compraramTodasFiliais(){
    nodo * fil2=NULL;
    nodo * fil3=NULL;
    fil2=comparaAnterior(filial1,filial2,fil2);
    fil3=comparaAnterior2(fil2,filial3,fil3);
    return fil3;

}

