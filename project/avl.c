#include "avl.h"

nodo *search(nodo *raiz, char codigo[]){
    int cmp;
    if (raiz == NULL) return NULL;
    cmp=strcmp(codigo,raiz->codigo);
    if (cmp<0)
        return search(raiz->esq, codigo);
    else if (cmp > 0)
        return search(raiz->dir, codigo);
    else
        return raiz;
}

int max ( int a, int b ){
    return a > b ? a : b;
}

int altura(nodo *raiz){
    if(raiz) return raiz->altura;
    return 0;
}

void ajustaAltura(nodo *raiz){
    raiz->altura = 1 + max(altura(raiz->esq), altura(raiz->dir));
}

nodo *rodaDir(nodo *raiz){
    nodo *new = raiz->esq;
    if (raiz->pai)
    {
        if (raiz->pai->esq == raiz) raiz->pai->esq = new;
        else raiz->pai->dir = new;
    }
    new->pai = raiz->pai;
    raiz->pai = new;
    raiz->esq = new->dir;
    if (raiz->esq) raiz->esq->pai = raiz;
    new->dir = raiz;

    ajustaAltura(raiz);
    ajustaAltura(new);
    return new;
}

nodo *rodaEsq(nodo *raiz){

    nodo *new = raiz->dir;
    if (raiz->pai)
    {
        if (raiz->pai->dir == raiz) raiz->pai->dir = new;
        else raiz->pai->esq = new;
    }
    new->pai = raiz->pai;
    raiz->pai = new;
    raiz->dir = new->esq;
    if (raiz->dir) raiz->dir->pai = raiz;
    new->esq = raiz;

    ajustaAltura(raiz);
    ajustaAltura(new);
    return new;
}

nodo *criaNodo(char codigo[], nodo *pai){
    nodo *n = malloc(sizeof(nodo));
    strcpy(n->codigo,codigo);
    n->pai = pai;
    n->altura = 1;
    n->esq = NULL;
    n->dir = NULL;

    return n;
}

nodo *balance(nodo *raiz){
    if (altura(raiz->esq) - altura(raiz->dir) > 1){
        if (altura(raiz->esq->esq) > altura(raiz->esq->dir)){
            raiz = rodaDir(raiz);
        }
        else{
            rodaEsq(raiz->esq);
            raiz = rodaDir(raiz);
        }
    }
    else if (altura(raiz->dir) - altura(raiz->esq) > 1){
        if (altura(raiz->dir->dir) > altura(raiz->dir->esq)){
            raiz = rodaEsq(raiz);
        }
        else{
            rodaDir(raiz->dir);
            raiz = rodaEsq(raiz);
        }
    }
    return raiz;
}

nodo *insert(char codigo[],nodo *raiz){
    nodo *aux = raiz;
    while (strcmp(codigo,aux->codigo)){
        if (strcmp(codigo,aux->codigo)<0){
            if (aux->esq) aux = aux->esq;
            else{
                aux->esq = criaNodo(codigo, aux);
                aux = aux->esq;
            }
        }
        else if (strcmp(codigo,aux->codigo)>0){
            if (aux->dir) aux = aux->dir;
            else{
                aux->dir = criaNodo(codigo, aux);
                aux = aux->dir;
            }
        }
        else return raiz;
    }

    do{
        aux  = aux->pai;
        ajustaAltura(aux);
        aux = balance(aux);
    } while (aux->pai);

    return aux;
}

int conta(nodo * raiz){
    if(raiz==NULL)
        return 0;
    return 1+conta(raiz->esq)+conta(raiz->dir);
}


    /*###################################################################*/
/*##################################################################################*/
/*                              ARVORES DE VENDAS                                     */
/*##################################################################################*/
    /*###################################################################*/

int vendaCmp(info v1,info v2){
    int cmp= strcmp(v1.produto,v2.produto);
    if(cmp!=0){ return cmp ; }
    cmp=strcmp(v1.cliente,v2.cliente);
    if(cmp!=0) return cmp;
    if(v1.mes > v2.mes) return 1;
    if(v1.mes < v2.mes) return -1;
    if(v1.filial > v2.filial) return 1;
    if(v1.filial < v2.filial) return -1;
    if(v1.np > v2.np) return 1;
    if(v1.np < v2.np) return -1;
    if(v1.preco > v2.preco) return 1;
    if(v1.preco < v2.preco) return -1;
    if(v1.qtd > v2.qtd) return 1;
    if(v1.qtd < v2.qtd) return -1;
    return 0;
}

nodoV *searchV(nodoV *raiz, info inf){
    int cmp;
    if (raiz == NULL) return NULL;
    cmp=vendaCmp(inf,raiz->inf);
    if (cmp<0)
        return searchV(raiz->esq, inf);
    else if (cmp > 0)
        return searchV(raiz->dir, inf);
    else
        return raiz;
}

int alturaV(nodoV *raiz){
    if(raiz) return raiz->altura;
    return 0;
}

void ajustaAlturaV(nodoV *raiz){
    raiz->altura = 1 + max(alturaV(raiz->esq), alturaV(raiz->dir));
}

nodoV *rodaDirV(nodoV *raiz){
    nodoV *new = raiz->esq;
    if (raiz->pai){
        if (raiz->pai->esq == raiz) raiz->pai->esq = new;
        else raiz->pai->dir = new;
    }
    new->pai = raiz->pai;
    raiz->pai = new;
    raiz->esq = new->dir;
    if (raiz->esq) raiz->esq->pai = raiz;
    new->dir = raiz;
    ajustaAlturaV(raiz);
    ajustaAlturaV(new);
    return new;
}

nodoV *rodaEsqV(nodoV *raiz){

    nodoV *new = raiz->dir;
    if (raiz->pai){
        if (raiz->pai->dir == raiz) raiz->pai->dir = new;
        else raiz->pai->esq = new;
    }
    new->pai = raiz->pai;
    raiz->pai = new;
    raiz->dir = new->esq;
    if (raiz->dir) raiz->dir->pai = raiz;
    new->esq = raiz;
    ajustaAlturaV(raiz);
    ajustaAlturaV(new);
    return new;
}

void vendaCopy(info *v1,info v2){
    strcpy(v1->produto,v2.produto);
    strcpy(v1->cliente,v2.cliente);
    v1->preco = v2.preco;
    v1->qtd   = v2.qtd;
    v1->np    = v2.np;
    v1->mes  = v2.mes;
    v1->filial= v2.filial;
}

nodoV *criaNodoV(info inf, nodoV *pai){
    nodoV *n = malloc(sizeof(nodoV));
    vendaCopy(&n->inf,inf);
    n->pai = pai;
    n->altura = 1;
    n->esq = NULL;
    n->dir = NULL;
    return n;
}

nodoV *balanceV(nodoV *raiz){
    if (alturaV(raiz->esq) - alturaV(raiz->dir) > 1)
    {
        if (alturaV(raiz->esq->esq) > alturaV(raiz->esq->dir)){
            raiz = rodaDirV(raiz);
        }
        else{
            rodaEsqV(raiz->esq);
            raiz = rodaDirV(raiz);
        }
    }
    else if (alturaV(raiz->dir) - alturaV(raiz->esq) > 1){
        if (alturaV(raiz->dir->dir) > alturaV(raiz->dir->esq)){
            raiz = rodaEsqV(raiz);
        }
        else{
            rodaDirV(raiz->dir);
            raiz = rodaEsqV(raiz);
        }
    }
    return raiz;
}

nodoV *insertV(info inf,nodoV *raiz){
    nodoV *aux = raiz;
    while (vendaCmp(inf,aux->inf)!=0){
        if (vendaCmp(inf,aux->inf)<0){
            if (aux->esq) aux = aux->esq;
            else{
                aux->esq = criaNodoV(inf, aux);
                aux = aux->esq;
            }
        }
        else if (vendaCmp(inf,aux->inf)>0){
            if (aux->dir) aux = aux->dir;
            else{
                aux->dir = criaNodoV(inf, aux);
                aux = aux->dir;
                break;
            }
        }
        else return raiz;
    }
    do {
        aux  = aux->pai;
        ajustaAlturaV(aux);
        aux = balanceV(aux);
    } while (aux->pai);
    return aux;
}
