#include "produtos.h"
#ifndef PROD
typedef struct prod{
    char * cod;
}prod;

typedef nodo * NodoProd;

typedef struct listaP{
    NodoProd prod;
    /*int count*/
}listaP;

typedef struct Produtos{
    int num;
    NodoProd prods;
}Produtos[26];
#define PROD
#endif


Produto criaProduto(char * codigo){
    Produto prod=malloc(sizeof(Produto));
    prod->cod=codigo;
    return prod;
}

CatProdutos insereProduto(CatProdutos catP,Produto prod){
    int aux=prod->cod[0]-65;
    if(catP[aux].prods==NULL){
        catP[aux].prods=criaNodo(prod->cod,NULL);
    }
    else catP[aux].prods=insert(prod->cod,catP[aux].prods);
    catP[aux].num++;
    return catP;
}


CatProdutos initCatProds(){
    int i;
    CatProdutos produtos=malloc(26*sizeof(struct Produtos));
    for(i=0;i<26;i++){
        produtos[i].num=0;
        produtos[i].prods=NULL;
    }
    return produtos;

}

int existeProduto(CatProdutos produtos,Produto prod){
    int aux=prod->cod[0]-65;
    if(search(produtos[aux].prods,prod->cod)==NULL)
        return 0;
    return 1;
}

int totalProdutos(CatProdutos produtos){
    int i,count=0;
    for(i=0;i<26;i++){
        count+=produtos[i].num;
    }
    return count;
}

int totalProdutosLetra(CatProdutos produtos, char letra){
    return produtos[letra-65].num;
}


void removeCatProdutos(CatProdutos produtos){
    int i;
    for(i = 0; i < 26; i++){
        freeTree(produtos[i].prods);
    }
    free(produtos);
}
