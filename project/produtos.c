#include "produtos.h"
#ifndef PRODUTOS

typedef struct prod{
    char * cod;
}prod;


typedef struct listaProdutos{
    nodoProduto produtos;
    /* TODO - size of lista */
}listaProdutos;

typedef struct catProdutos{
    int size;
    nodoProduto produtos;
}catProdutos[26];
#define PRODUTOS
#endif

nodoProduto getAVLProd(CatProdutos p,int lP){
    return p[lP].produtos;
}


/*Funções sobre o tipo catProdutos */


CatProdutos initCatProdutos(){
    int i;
    CatProdutos produtos=malloc(26*sizeof(struct catProdutos));
    for(i=0;i<26;i++){
        produtos[i].size=0;
        produtos[i].produtos=NULL;
    }
    return produtos;

}

CatProdutos insereProduto(CatProdutos catP, Produto prod){
    int lInicial=prod->cod[0]-65;
    if(catP[lInicial].produtos==NULL){
        catP[lInicial].produtos=criaNodo(prod->cod,NULL);
    }
    else {
        catP[lInicial].produtos=insert(prod->cod,catP[lInicial].produtos);
    }
    catP[lInicial].size++;
    return catP;
}

int existeProduto(CatProdutos catP,Produto prod){
    int lInicial=prod->cod[0]-65;
    if(search(catP[lInicial].produtos,prod->cod)==NULL) return 0;
    else return 1;
}

int totalProdutos(CatProdutos catP){
    int i,total=0;
    for(i=0;i<26;i++){
       total+=catP[i].size;
    }
    return total;
}

int totalProdutosLetra(CatProdutos catP, char letra){
    return catP[letra-65].size;
}

/* Funções sobre o tipo Produto */

Produto criaProduto(char * codigo){
    Produto prod=malloc(sizeof(Produto));
    prod->cod=codigo;
    return prod;
}


char * getCodProd(Produto p){
    return p->cod;
}


