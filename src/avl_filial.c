/* #include "avl_filial.h"

nodoFilial procura(nodoFilial raiz, char * codigo){
    int cmp;
    if (raiz == NULL) return NULL;
    cmp=strcmp(codigo,raiz->key->codigo);
    if (cmp<0)
        return procura(raiz->esq, codigo);
    else if (cmp > 0)
        return procura(raiz->dir, codigo);
    else
        return raiz;
}

int alturaV(nodoFilial raiz){
    if(raiz) return raiz->altura;
    return 0;
}

void ajustaAlturaV(nodoFilial raiz){
    raiz->altura = 1 + max(alturaV(raiz->esq), alturaV(raiz->dir));
}

nodoFilial rodaDirV(nodoFilial raiz){
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
    ajustaAlturaV(raiz);
    ajustaAlturaV(new);
    return new;
}

nodoFilial rodaEsqV(nodoFilial raiz){

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
    ajustaAlturaV(raiz);
    ajustaAlturaV(new);
    return new;
}

void infoProdutoCopy(estruturaFilial p1,estruturaFilial p2){

}

nodoFilial criaNodoFat(infoP produto, nodoFilial pai){
    struct nodoFilial *n = malloc(sizeof(struct nodoFilial));
    n->produto=malloc(sizeof(struct infoProduto));
    infoProdutoCopy(n->produto,produto);
    n->pai = pai;
    n->altura = 1;
    n->esq = NULL;
    n->dir = NULL;
    return n;
}

nodoFilial balanceV(nodoFilial raiz){
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

nodoFilial insertNodoFat(infoP produto,nodoFilial raiz){
    nodoFilial aux = raiz;
    while (strcmp(produto->produto,aux->produto->produto)){
        if (strcmp(produto->produto,aux->produto->produto)<0){
            if (aux->esq) aux = aux->esq;
            else{
                aux->esq = criaNodoFat(produto, aux);
                aux = aux->esq;
            }
        }
        else if (strcmp(produto->produto,aux->produto->produto)>0){
            if (aux->dir) aux = aux->dir;
            else{
                aux->dir = criaNodoFat(produto, aux);
                aux = aux->dir;
            }
        }
        else return raiz;
    }
    if(strcmp(produto->produto,aux->produto->produto)==0)
        return raiz;
    do {

        aux  = aux->pai;
        ajustaAlturaV(aux);
        aux = balanceV(aux);

    } while (aux->pai);
    return aux;
}

*/

