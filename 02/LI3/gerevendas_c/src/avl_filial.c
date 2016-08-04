#include "avl_filial.h"

nodoFilial procuraPFilial(nodoFilial raiz, char * codigo){
    int cmp;
    if (raiz == NULL) return NULL;
    cmp=strcmp(codigo,raiz->cliente->cliente);
    if (cmp<0)
        return procuraPFilial(raiz->esq, codigo);
    else if (cmp > 0)
        return procuraPFilial(raiz->dir, codigo);
    else
        return raiz;
}

int alturaVFilial(nodoFilial raiz){
    if(raiz) return raiz->altura;
    return 0;
}

void ajustaAlturaVFilial(nodoFilial raiz){
    raiz->altura = 1 + max(alturaVFilial(raiz->esq), alturaVFilial(raiz->dir));
}

nodoFilial rodaDirVFilial(nodoFilial raiz){
    nodoFilial new = raiz->esq;
    if (raiz->pai){
        if (raiz->pai->esq == raiz) raiz->pai->esq = new;
        else raiz->pai->dir = new;
    }
    new->pai = raiz->pai;
    raiz->pai = new;
    raiz->esq = new->dir;
    if (raiz->esq) raiz->esq->pai = raiz;
    new->dir = raiz;
    ajustaAlturaVFilial(raiz);
    ajustaAlturaVFilial(new);
    return new;
}

nodoFilial rodaEsqVFilial(nodoFilial raiz){

    nodoFilial new = raiz->dir;
    if (raiz->pai){
        if (raiz->pai->dir == raiz) raiz->pai->dir = new;
        else raiz->pai->esq = new;
    }
    new->pai = raiz->pai;
    raiz->pai = new;
    raiz->dir = new->esq;
    if (raiz->dir) raiz->dir->pai = raiz;
    new->esq = raiz;
    ajustaAlturaVFilial(raiz);
    ajustaAlturaVFilial(new);
    return new;
}

void infoFcopy(infoF p1,infoF p2){
    int i;
    p1->cliente=strdup(p2->cliente);
    p1->nVendas=p2->nVendas;
    p1->vendas=malloc(p1->nVendas*sizeof(struct venda));
    for(i=0;i<p1->nVendas;i++){
        p1->vendas[i].produto=strdup(p2->vendas[i].produto);
        p1->vendas[i].qtd=p2->vendas[i].qtd;
        p1->vendas[i].preco=p2->vendas[i].preco;
        p1->vendas[i].tipo=p2->vendas[i].tipo;
        p1->vendas[i].mes=p2->vendas[i].mes;
    }
}

nodoFilial criaNodoFilial(infoF cliente, nodoFilial pai){
    struct nodoFilial *n = malloc(sizeof(struct nodoFilial));
    n->cliente=malloc(sizeof(struct infoF));
    infoFcopy(n->cliente,cliente);
    n->pai = pai;
    n->altura = 1;
    n->esq = NULL;
    n->dir = NULL;
    return n;
}

nodoFilial balanceVFilial(nodoFilial raiz){
    if (alturaVFilial(raiz->esq) - alturaVFilial(raiz->dir) > 1)
    {
        if (alturaVFilial(raiz->esq->esq) > alturaVFilial(raiz->esq->dir)){
            raiz = rodaDirVFilial(raiz);
        }
        else{
            rodaEsqVFilial(raiz->esq);
            raiz = rodaDirVFilial(raiz);
        }
    }
    else if (alturaVFilial(raiz->dir) - alturaVFilial(raiz->esq) > 1){
        if (alturaVFilial(raiz->dir->dir) > alturaVFilial(raiz->dir->esq)){
            raiz = rodaEsqVFilial(raiz);
        }
        else{
            rodaDirVFilial(raiz->dir);
            raiz = rodaEsqVFilial(raiz);
        }
    }
    return raiz;
}

nodoFilial insertNodoFilial(infoF cliente,nodoFilial raiz){
    nodoFilial aux = raiz;
    while (strcmp(cliente->cliente,aux->cliente->cliente)){
        if (strcmp(cliente->cliente,aux->cliente->cliente)<0){
            if (aux->esq) aux = aux->esq;
            else{
                aux->esq = criaNodoFilial(cliente, aux);
                aux = aux->esq;
            }
        }
        else if (strcmp(cliente->cliente,aux->cliente->cliente)>0){
            if (aux->dir) aux = aux->dir;
            else{
                aux->dir = criaNodoFilial(cliente, aux);
                aux = aux->dir;
            }
        }
        else return raiz;
    }
    if(strcmp(cliente->cliente,aux->cliente->cliente)==0)
        return raiz;
    do {

        aux  = aux->pai;
        ajustaAlturaVFilial(aux);
        aux = balanceVFilial(aux);

    } while (aux->pai);
    return aux;
}
