   /*###################################################################*/
/*##################################################################################*/
/*                              ARVORES DE VENDAS                                     */
/*##################################################################################*/
    /*###################################################################*/
#include "avl_vendas.h"

nodoFaturacaoProduto searchProduto(nodoFaturacaoProduto raiz, char * produto){
    int cmp;
    if (raiz == NULL) return NULL;
    cmp=strcmp(produto,raiz->produto->produto);
    if (cmp<0)
        return searchProduto(raiz->esq, produto);
    else if (cmp > 0)
        return searchProduto(raiz->dir, produto);
    else
        return raiz;
}

int alturaV(nodoFaturacaoProduto raiz){
    if(raiz) return raiz->altura;
    return 0;
}

void ajustaAlturaV(nodoFaturacaoProduto raiz){
    raiz->altura = 1 + max(alturaV(raiz->esq), alturaV(raiz->dir));
}

nodoFaturacaoProduto rodaDirV(nodoFaturacaoProduto raiz){
    nodoFaturacaoProduto new = raiz->esq;
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

nodoFaturacaoProduto rodaEsqV(nodoFaturacaoProduto raiz){

    nodoFaturacaoProduto new = raiz->dir;
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


/*TESTADA*/
void infoProdutoCopy(infoP p1,infoP p2){
    p1->qtdNormal=p2->qtdNormal;
    p1->qtdPromocao=p2->qtdPromocao;
    p1->totalNormal=p2->totalNormal;
    p1->totalPromocao=p2->totalPromocao;
    p1->produto=strdup(p2->produto);
}

/*TESTADA*/
nodoFaturacaoProduto criaNodoFat(infoP produto, nodoFaturacaoProduto pai){
    struct nodoFaturacaoProduto *n = malloc(sizeof(struct nodoFaturacaoProduto));
    n->produto=malloc(sizeof(struct infoProduto));
    infoProdutoCopy(n->produto,produto);
    n->pai = pai;
    n->altura = 1;
    n->esq = NULL;
    n->dir = NULL;
    return n;
}

nodoFaturacaoProduto balanceV(nodoFaturacaoProduto raiz){
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

nodoFaturacaoProduto insertNodoFat(infoP produto,nodoFaturacaoProduto raiz){
    nodoFaturacaoProduto aux = raiz;
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



int contaNaoComprados(nodoFaturacaoProduto raiz){
    if(raiz==NULL)
        return 0;
    infoP aux = raiz->produto;
    if (aux->qtdNormal == 0 && aux->qtdPromocao == 0)return 1+contaNaoComprados(raiz->esq)+contaNaoComprados(raiz->dir);
        else return contaNaoComprados(raiz->esq)+contaNaoComprados(raiz->dir);
}

