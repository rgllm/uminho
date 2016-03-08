#include "avl.h"

nodo *search(nodo *raiz, char codigo[]){
    if (raiz == NULL) return NULL;
    int cmp=strcmp(codigo,raiz->codigo);
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

/* We can assume nodo->esq is non-null due to how this is called */
nodo *rodaDir(nodo *raiz){
    /* Fix pointers */
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

    /* Fix alturas; raiz and new may be wrong. Do bottom-up */
    ajustaAltura(raiz);
    ajustaAltura(new);
    return new;
}

/* We can assume nodo->dir is non-null due to how this is called */
nodo *rodaEsq(nodo *raiz)
{
    /* Fix pointers */
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

    /* Fix alturas; raiz and new may be wrong */
    ajustaAltura(raiz);
    ajustaAltura(new);
    return new;
}

nodo *criaNodo(char codigo[], nodo *pai)
{
    nodo *n = malloc(sizeof(nodo));
    strcpy(n->codigo,codigo);
    n->pai = pai;
    n->altura = 1;
    n->esq = NULL;
    n->dir = NULL;

    return n;
}

nodo *balance(nodo *raiz)
{
    if (altura(raiz->esq) - altura(raiz->dir) > 1)
    {
        if (altura(raiz->esq->esq) > altura(raiz->esq->dir))
        {
            raiz = rodaDir(raiz);
        }
        else
        {
            rodaEsq(raiz->esq);
            raiz = rodaDir(raiz);
        }
    }
    else if (altura(raiz->dir) - altura(raiz->esq) > 1)
    {
        if (altura(raiz->dir->dir) > altura(raiz->dir->esq))
        {
            raiz = rodaEsq(raiz);
        }
        else
        {
            rodaDir(raiz->dir);
            raiz = rodaEsq(raiz);
        }
    }
    return raiz;
}






void print_tree_indent(nodo *nodo, int indent)
{
    int i;
    for (i = 0; i < indent; i++) printf(" ");
    if (!nodo) printf("Empty child\n");
    else
    {
        printf("nodo: %s; altura: %d\n", nodo->codigo, nodo->altura);
        print_tree_indent(nodo->esq, indent + 4);
        print_tree_indent(nodo->dir, indent + 4);
    }
}

void print_tree(nodo *nodo)
{
    print_tree_indent(nodo, 0);
}


nodo *insert(char codigo[],nodo *raiz)
{
    nodo *aux = raiz;
    int cmp;
    while (!cmp)
    {
        if (strcmp(codigo,aux->codigo)<0)
        {
            if (aux->esq) aux = aux->esq;
            else
            {
                aux->esq = criaNodo(codigo, aux);
                aux = aux->esq;
            }
        }
        else if (strcmp(codigo,aux->codigo)>0)
        {
            if (aux->dir) aux = aux->dir;
            else
            {
                aux->dir = criaNodo(codigo, aux);
                aux = aux->dir;
            }
        }
        else return raiz; /* codigoue was in the tree, dumbass */
    }
    
    do
    {
        
        aux  = aux->pai;
        //printf("akldmasdaszd  aux->altura: %d \n",aux->altura);
        ajustaAltura(aux);
        print_tree(aux);
        aux = balance(aux);
    } while (aux->pai);
    
    return aux;
}

/* Tests to make sure above code actually works */



int main(int argc, char *argv[])
{;
    nodo *raiz = criaNodo("ddddd", NULL);
    raiz = insert("fffffff",raiz);;
    raiz = insert("aaaaaa",raiz);
    raiz = insert("aaaaab",raiz);
    raiz = insert("bbab",raiz);

    print_tree(raiz);
    
    return 0;
}