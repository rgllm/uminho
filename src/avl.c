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
    n->codigo=myStrdup(codigo);
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

void printInOrder(nodo * raiz){
    if(raiz!=NULL){
        printInOrder(raiz->esq);
        printf("%s\n",raiz->codigo);
        printInOrder(raiz->dir);
    }
}

void freeTree(nodo * raiz){
    if(raiz!=NULL){
        if(raiz->esq!=NULL){
            freeTree(raiz->esq);
        }
        if(raiz->dir!=NULL)
            freeTree(raiz->dir);
        free(raiz);
    }
}


    /*###################################################################*/
/*##################################################################################*/
/*                              ARVORES DE VENDAS                                     */
/*##################################################################################*/
    /*###################################################################*/


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

void infoProdutoCopy(infoP p1,infoP p2){
    p1->produto=myStrdup(p2->produto);
    p1->qtdNormal=p2->qtdNormal;
    p1->qtdPromocao=p2->qtdPromocao;
    p1->totalNormal=p2->totalNormal;
    p1->totalPromocao=p2->totalPromocao;
}

nodoFaturacaoProduto criaNodoFat(infoP produto, nodoFaturacaoProduto pai){
    nodoFaturacaoProduto n = malloc(sizeof(nodoFaturacaoProduto));
    infoProdutoCopy(&n->produto,produto);
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
    printf("aaaaaaaaaaaaaaaaaaaaaaaaa\n");
    while (strcmp(produto->produto,aux->produto->produto)!=0){
        printf("bbbbbbbbbbbbbbbb\n");
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
