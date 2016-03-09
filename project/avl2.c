#include "avl2.h"

int vendaCmp(venda v1,venda v2){
    int cmp= strcmp(v1.produto,v2.produto);
    if(cmp!=0){ return cmp ; }
    cmp=strcmp(v1.cliente,v2.cliente);
    if(cmp!=0) return cmp;
    if(v1.mes > v2.mes) return 1;
    if(v1.mes < v2.mes) return -1;
    if(v1.filial > v2.filial) return 1;
    if(v1.filial < v2.filial) return -1;
    return 0;
}

nodoV *searchV(nodoV *raiz, venda info){
    if (raiz == NULL) return NULL;
    int cmp=vendaCmp(info,raiz->info);
    if (cmp<0)
        return searchV(raiz->esq, info);
    else if (cmp > 0)
        return searchV(raiz->dir, info);
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

    ajustaAlturaV(raiz);
    ajustaAlturaV(new);
    return new;
}

nodoV *rodaEsqV(nodoV *raiz)
{

    nodoV *new = raiz->dir;
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

    ajustaAlturaV(raiz);
    ajustaAlturaV(new);
    return new;
}

void criaVenda(venda * info,char produto[], double preco, int qtd, char np, char cliente[], int mes, int filial){
    strcpy(info->produto,produto);
    info->preco = preco;
    info->qtd = qtd;
    info->np = np;
    strcpy(info->cliente,cliente);
    info->mes = mes;
    info->filial = filial;
}

void vendaCopy(venda *v1,venda v2){
    strcpy(v1->produto,v2.produto);
    v1->preco = v2.preco;
    v1->qtd   = v2.qtd;
    v1->np    = v2.np;
    strcpy(v1->cliente,v2.cliente);
    v1->mes  = v2.mes;
    v1->filial= v2.filial;
}

nodoV *criaNodoV(venda info, nodoV *pai)
{
    nodoV *n = malloc(sizeof(nodoV));

    vendaCopy(&n->info,info);
    n->pai = pai;
    n->altura = 1;
    n->esq = NULL;
    n->dir = NULL;

    return n;
}

nodoV *balanceV(nodoV *raiz)
{
    if (alturaV(raiz->esq) - alturaV(raiz->dir) > 1)
    {
        if (alturaV(raiz->esq->esq) > alturaV(raiz->esq->dir))
        {
            raiz = rodaDirV(raiz);
        }
        else
        {
            rodaEsqV(raiz->esq);
            raiz = rodaDirV(raiz);
        }
    }
    else if (alturaV(raiz->dir) - alturaV(raiz->esq) > 1)
    {
        if (alturaV(raiz->dir->dir) > alturaV(raiz->dir->esq))
        {
            raiz = rodaEsqV(raiz);
        }
        else
        {
            rodaDirV(raiz->dir);
            raiz = rodaEsqV(raiz);
        }
    }
    return raiz;
}

nodoV *insertV(venda info,nodoV *raiz)
{
    nodoV *aux = raiz;
    while (vendaCmp(info,aux->info)!=0)
    {
        if (vendaCmp(info,aux->info)<0)
        {
            if (aux->esq) aux = aux->esq;
            else
            {
                aux->esq = criaNodoV(info, aux);
                aux = aux->esq;
            }
        }
        else if (vendaCmp(info,aux->info)>0)
        {
            if (aux->dir) aux = aux->dir;
            else
            {
                aux->dir = criaNodoV(info, aux);
                aux = aux->dir;
                break;
            }
        }
        else return raiz;
    }

    do
    {

        aux  = aux->pai;
        ajustaAlturaV(aux);
        aux = balanceV(aux);
    } while (aux->pai);

    return aux;
}



void print_tree_indentV(nodoV *nodoV, int indent)
{
    int i;
    for (i = 0; i < indent; i++) printf(" ");
    if (!nodoV) printf("*\n");
    else
    {
        printf("NODOV: %s; altura: %d\n", nodoV->info.produto, nodoV->altura);
        print_tree_indentV(nodoV->esq, indent + 4);
        print_tree_indentV(nodoV->dir, indent + 4);
    }
}

void print_treeV(nodoV *nodoV)
{
    print_tree_indentV(nodoV, 0);
}

/*
int main(int argc, char *argv[])
{;
    venda aux;
    aux->
    nodoV *raiz = criaNodoV("ddddd", NULL);
    raiz = insertV("fffffff",raiz);
    raiz = insertV("aaaaaa",raiz);
    raiz = insertV("zzzz",raiz);
    raiz = insertV("bbab",raiz);
    print_tree(raiz);

    return 0;
}

*/