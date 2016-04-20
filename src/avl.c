#include "avl.h"

/**
 * Dado uma árvore e um código procura esse código na árvore.
 * Retorna o apontador para o nodo caso exista e NULL caso não exista.
 * @param raiz
 * @param codigo
 * @return
 */
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
    n->codigo = strdup(codigo);
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


int printInOrder(nodo *raiz, int count){
    if(raiz!=NULL){
        count=printInOrder(raiz->esq,count);
        count++;
        if(count%PRINT_COLS==0 && count!=0) printf("%s \n", raiz->codigo);
        else printf("%s \t\t", raiz->codigo);
        if(count%(2*10*PRINT_COLS)
            ==0 && count!=0){getch();}
        count=printInOrder(raiz->dir, count);
    }
    return count;
}



int AddToArray(nodo * raiz, char * arr[], int i){
    if(raiz == NULL)
        return i;
     
    if(raiz->esq != NULL)
        i = AddToArray(raiz->esq, arr, i);

    arr[i] = strdup(raiz->codigo);
    i++;
    if(raiz->dir != NULL)
        i = AddToArray(raiz->dir, arr, i);

    return i;
}


char * AVLtoArray(nodo *raiz){
    char * * array=malloc(conta(raiz)*sizeof(char *));
    AddToArray(raiz,array,0);
    return array;
}

void printPages(nodo * raiz){
    int i,index=0, h = 0, nelem = conta(raiz), nrpag = nelem/30;
    char key = 'n';
    char * * lista=AVLtoArray(raiz);

    if((nelem%30)) nrpag++;
    
    while(key != 'q'){
        if(key == 'n'){
            system("clear");
            for(i = 0; i < 30 && index<nelem; i++){
                printf("|  %s  |\n", lista[index]);
                index++;

            }
            h++;
            printf("Página: %d de %d | Lidos: %d de %d |\n",h, nrpag, index, nelem);
            printf("\'n\' para próximo, \'p\' para anterior, \'q\' para sair\n");
        }
        else if(key == 'p'){
            if(index > 30){ 
                system("clear");
                if(h==nrpag && nelem%30!=0) index-=30+nelem%30;
                else index-=60;

                for(i = 0; i < 30 && index<nelem; i++){
                    printf("|  %s  |\n", lista[index]);
                    index++;
                }
                h--;   
            }
            printf("Página: %d de %d | Lidos: %d de %d |\n",h, nrpag, index, nelem);
            printf("\'n\' para próximo, \'p\' para anterior, \'q\' para sair\n");
        }
        key = getchar();
        if(key=='n' && h==nrpag) return;
    }

}