#include "avl_faturacao.h"

/**
 * Dado uma árvore do tipo FaturacaoProduto e um código procura esse código na árvore.
 * Retorna o apontador para o nodo caso exista e NULL caso não exista.
 * @param raiz
 * @param codigo
 * @return
 */
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

/**
 * Dado uma árvore calcula a altura da árvore.
 * Retorna um inteiro com a altura.
 * @param raiz
 * @return
 */
int alturaV(nodoFaturacaoProduto raiz){
    if(raiz) return raiz->altura;
    return 0;
}

/**
 * Dado uma árvore e um código procura esse código na árvore.
 * Retorna o apontador para o nodo caso exista e NULL caso não exista.
 * @param raiz
 * @param codigo
 * @return
 */
void ajustaAlturaV(nodoFaturacaoProduto raiz){
    raiz->altura = 1 + max(alturaV(raiz->esq), alturaV(raiz->dir));
}

/**
 * Dado uma árvore e um código procura esse código na árvore.
 * Retorna o apontador para o nodo caso exista e NULL caso não exista.
 * @param raiz
 * @param codigo
 * @return
 */
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

/**
 * Dado uma árvore e um código procura esse código na árvore.
 * Retorna o apontador para o nodo caso exista e NULL caso não exista.
 * @param raiz
 * @param codigo
 * @return
 */
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


/**
 * Dado uma árvore e um código procura esse código na árvore.
 * Retorna o apontador para o nodo caso exista e NULL caso não exista.
 * @param raiz
 * @param codigo
 * @return
 */
void infoProdutoCopy(infoP p1,infoP p2){
    p1->qtdNormal=p2->qtdNormal;
    p1->qtdPromocao=p2->qtdPromocao;
    p1->totalNormal=p2->totalNormal;
    p1->totalPromocao=p2->totalPromocao;
    p1->produto=strdup(p2->produto);
}

/**
 * Dado uma árvore e um código procura esse código na árvore.
 * Retorna o apontador para o nodo caso exista e NULL caso não exista.
 * @param raiz
 * @param codigo
 * @return
 */
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

/**
 * Dado uma árvore e um código procura esse código na árvore.
 * Retorna o apontador para o nodo caso exista e NULL caso não exista.
 * @param raiz
 * @param codigo
 * @return
 */
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

/**
 * Dado uma árvore e um código procura esse código na árvore.
 * Retorna o apontador para o nodo caso exista e NULL caso não exista.
 * @param raiz
 * @param codigo
 * @return
 */
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

/**
 * Adiciona um produto a um array caso este não tenha sido comprado.
 * @param raiz
 * @param arr
 * @param i
 * @return
 */
int AddToArrayNComprados(nodoFaturacaoProduto raiz, char * arr[], int i){
    if(raiz == NULL)
        return i;

    if(raiz->esq != NULL)
        i = AddToArrayNComprados(raiz->esq, arr, i);
    if(raiz->produto->qtdNormal==0 && raiz->produto->qtdPromocao==0){
        arr[i] = strdup(raiz->produto->produto);
        i++;
    }
    
    if(raiz->dir != NULL)
        i = AddToArrayNComprados(raiz->dir, arr, i);

    return i;
}

/**
 * Converte uma AVL num array.
 * @param raiz
 * @param nelem
 * @return
 */
char * * AVLtoArrayNComprados(nodoFaturacaoProduto raiz,int nelem){
    char * * array=malloc(nelem*sizeof(char *));
    AddToArrayNComprados(raiz,array,0);
    return array;
}

/**
 * Imprime no ecrã página a página os elementos não comprados.
 * @param raiz
 * @param nelem
 * @return
 */
void printNaoCompradosAVL(nodoFaturacaoProduto raiz,int nelem){
    int i,index=0, h = 0, nrpag = nelem/30,sys;
    char key = '+';
    char * * lista=AVLtoArrayNComprados(raiz,nelem);

    if((nelem%30)) nrpag++;

    while(key != 'q'){
        if(key == '+'){
            sys=system("clear");
            for(i = 0; i < 30 && index<nelem; i++){
                printf("|  %s  |\n", lista[index]);
                index++;

            }
            h++;
            printf("Página: %d de %d | Lidos: %d de %d |\n",h, nrpag, index, nelem);
            printf("\'+\' para próximo, \'-\' para anterior, \'q\' para sair\n");
        }
        else if(key == '-'){
            if(index > 30){
                sys=system("clear");
                if(h==nrpag && nelem%30!=0) index-=30+nelem%30;
                else index-=60;

                for(i = 0; i < 30 && index<nelem; i++){
                    printf("|  %s  |\n", lista[index]);
                    index++;
                }
                h--;
            }
            printf("Página: %d de %d | Lidos: %d de %d |\n",h, nrpag, index, nelem);
            printf("\'+\' para próximo, \'-\' para anterior, \'q\' para sair\n");
        }
        key = getchar();
        if(key=='+' && h==nrpag){sleep(1);return;}
    }

}
