#include"listaligada.h"

/**
 * Inicia a lista ligada
 * @param l
 * @param c
 * @return 
 */
ListaLigada initLista(ListaLigada l, char *c) {
    if(l==NULL){
        l = (ListaLigada) malloc(sizeof(struct listaligada));
        strcpy(l->codigo,c);
        l->ant = l->prox = NULL;
    }
    return l;
}

/**
 * Insere elemento na lista ligada
 * @param l
 * @param c
 * @return 
 */
ListaLigada insereElemento(ListaLigada l, char *c) {
    if(l==NULL) {
        l = initLista(l,c);
    } else {
        ListaLigada novo;
        novo = (ListaLigada) malloc(sizeof(struct listaligada));
        strcpy(novo->codigo,c);
        novo->prox = l;
        novo->ant = NULL;
        l->ant = novo;
        l = novo;
    }
    
    return l;
}

/**
 * Insere um elemento nao repetido na lista ligada
 * @param l
 * @param c
 * @return 
 */
ListaLigada insereElemNaoRepetido(ListaLigada l, char *c) {
    int encontrou = 0;
    ListaLigada aux = l;
    
    while(!encontrou && aux){
        if(strcmp(aux->codigo,c) == 0) encontrou = 1;
        aux = aux->prox;
    }

    if(!encontrou){
        l = insereElemento(l,c);
    }
    
    return l;
}

/**
 * Verifica se existe um determinado elemento da lista ligada
 * @param l
 * @param c
 * @return 
 */
int existeElemento(ListaLigada l, char *c){
    int res = 0;
    ListaLigada aux = l;
    
    while(!res && aux){
        if(strcmp(aux->codigo,c) == 0) res = 1;
        aux = aux->prox;
    }

    return res;
}

/* Cria uma nova lista inserindo aqueles que são diferentes que 
* são para remover */
ListaLigada removeElemento(ListaLigada l, char *c){
    int removeu = 0;
    ListaLigada aux = l, novo = NULL;
    
    /* Caso o elemento seja o 1o da lista */
    if(strcmp(aux->codigo, c) == 0){
        novo = aux;
        aux = aux->prox;
        free(novo);
        aux->ant = NULL;
        l = aux;
    }
    else {
        while(aux && !removeu){
            if(strcmp(aux->prox->codigo,c) == 0){
                novo = aux->prox;
                aux->prox = aux->prox->prox;
                if(aux->prox) aux->prox->ant = aux;
                free(novo);
                removeu = 1;
            }
            aux = aux->prox;
        }
    }

    return l;
}

/**
 * Faz a intersecção de 2 listas ligadas (comuns)
 * @param a
 * @param b
 * @return 
 */
ListaLigada interseccaoListas(ListaLigada a, ListaLigada b){
    ListaLigada result = NULL, aux = a;

    while(aux){
        if(existeElemento(b,aux->codigo)) {
            result = insereElemento(result,aux->codigo);
        }
        aux = aux->prox;
    }

    return result;
}

/**
 * Junta 2 listas
 * @param a
 * @param b
 * @return 
 */
ListaLigada juntaListas(ListaLigada res, ListaLigada a){
    ListaLigada aux = res;
    
    if(res){
        while(aux->prox){
            aux = aux->prox;
        }
        aux->prox = a;
    }
    else res = a;

    return res;
}

/**
 * Dadas 2 listas ligadas concatena-as
 * @param a
 * @param b
 * @return 
 */
ListaLigada concatenaListas(ListaLigada a, ListaLigada b) {
    ListaLigada res = NULL;
    
    if(a==NULL) {
        res = b;
    } else {
        res = a;
        while(a->prox != NULL) {
            a = a->prox;
        }
        a->prox = b;
    }
    return res;
}

/**
 * Devolve o comprimento da lista ligada
 * @param l
 * @return 
 */
int comprimentoListaLigada(ListaLigada l) {
    int c = 0;
    while(l != NULL){
        c++;
        l = l->prox;
    }
    return c;
}
