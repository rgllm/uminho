#include "clientes.h"
#include "aux.h"

/* Função para calcular a altura da arvore*/
int alturaC(NodoC N)
{
    if (N == NULL)
        return 0;
    return N->altura;
}
 
/* Função que aloca um novo NodoC com o valor recebido e inicializa a esq e dir a NULL. */
NodoC novoNodoC(char * valor)
{
    struct nodoc* nodo = (NodoC)malloc(sizeof(struct nodoc));
    strcpy(nodo->valor, valor);
    nodo->esq   = NULL;
    nodo->dir   = NULL;
    nodo->altura = 1;  /* novo NodoC adicionado como folha*/
    return(nodo);
}
 
/* Função que faz a rotação à direita*/
NodoC dirRotateC(NodoC y)
{
    NodoC x = y->esq;
    NodoC T2 = x->dir;
 
    /* Rotação */
    x->dir = y;
    y->esq = T2;
 
    /* Update altura */
    y->altura = max(alturaC(y->esq), alturaC(y->dir))+1;
    x->altura = max(alturaC(x->esq), alturaC(x->dir))+1;
 
    /* Return novo root */
    return x;
}
 
/* Função que faz a rotação à esquerda */
NodoC esqRotateC(NodoC x)
{
    NodoC y = x->dir;
    NodoC T2 = y->esq;
 
    /* Rotação */
    y->esq = x;
    x->dir = T2;
 
    /*  Update altura */
    x->altura = max(alturaC(x->esq), alturaC(x->dir))+1;
    y->altura = max(alturaC(y->esq), alturaC(y->dir))+1;
 
    /* Return novo root */
    return y;
}
 
/* Retorna o balanceamento do nodo */
int getBalanceC(NodoC N)
{
    if (N == NULL)
        return 0;
    return alturaC(N->esq) - alturaC(N->dir);
}
 
NodoC insertC(NodoC nodo, char * valor)
{
    int balance; 
    /* 1.  Rotação normal de uma arvore */
    if (nodo == NULL)
        return(novoNodoC(valor));
 
    if (strcmp(valor, nodo->valor) < 0)
        nodo->esq  = insertC(nodo->esq, valor);
    else if (strcmp(valor, nodo->valor) > 0)
        nodo->dir = insertC(nodo->dir, valor);
 
    /* 2. Update altura do nodo anterior ao nodo */
    nodo->altura = max(alturaC(nodo->esq), alturaC(nodo->dir)) + 1;
 
    /* 3. Calculo do balaceamento do nodo para verificar se fico desbalanceado */  
    balance = getBalanceC(nodo);
 
    /* Se ficou desbalanceado, temos 4 casos */
 
    /* esq esq Case */
    if (balance > 1 && (strcmp(valor, nodo->esq->valor) < 0))
        return dirRotateC(nodo);
 
    /* dir dir Case */
    if (balance < -1 && (strcmp(valor, nodo->dir->valor) > 0))
        return esqRotateC(nodo);
 
    /* esq dir Case */
    if (balance > 1 && (strcmp(valor, nodo->esq->valor) > 0))
    {
        nodo->esq =  esqRotateC(nodo->esq);
        return dirRotateC(nodo);
    }
 
    /* dir esq Case */
    if (balance < -1 && (strcmp(valor, nodo->dir->valor) < 0))
    {
        nodo->dir = dirRotateC(nodo->dir);
        return esqRotateC(nodo);
    }
 
    /* retorna o apontador de novo */
    return nodo;
}


int contaNodosC(NodoC avl)
{
	if(avl == NULL) return 0;
	return 1 + contaNodosC(avl->esq) + contaNodosC(avl->dir);
}

/**
 * Passa de uma árvore para uma lista em INORDER
 * »CLIENTES
 * @param l
 * @param c
 * @return 
 */
ListaLigada clientesParaLista(ListaLigada l, NodoC c) {
    if(c!=NULL) {
        l = clientesParaLista(l,c->dir);
        l = insereElemento(l,c->valor);
        l = clientesParaLista(l,c->esq);
    }
    return l;
}

int existeC(NodoC n, char * nom)
{
	int x = 0;
	if ( n == NULL) {}
	else if (strcmp(n->valor, nom) == 0) { x = 1;}
	else if (strcmp(nom, n->valor) < 0) { x = existeC(n->esq, nom);}
	else { x = existeC(n->dir, nom);}
	return x;
}

