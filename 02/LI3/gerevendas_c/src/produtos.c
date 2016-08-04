#include "produtos.h"

typedef struct prod{
    char * cod;
}prod;


typedef struct listaProdutos{
    nodoProduto produtos;
}listaProdutos;

typedef struct catProdutos{
    int size;
    nodoProduto produtos;
}catProdutos[26];

nodoProduto getAVLProd(CatProdutos p,int lP){
    return p[lP].produtos;
}


/*Funções sobre o tipo catProdutos */


/**
 * Função que cria e inicia o cátologo de produtos.
 * @return
 */
CatProdutos initCatProdutos(){
    int i;
    CatProdutos produtos=malloc(26*sizeof(struct catProdutos));
    for(i=0;i<26;i++){
        produtos[i].size=0;
        produtos[i].produtos=NULL;
    }
    return produtos;

}

/**
 * Função que insere um produto num cátologo de produtos.
 * @param catP
 * @param prod
 * @return
 */
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

/**
 * Função que verifica se um dado produto existe num cátologo.
 * Retorna 0 caso não exista e 1 caso exista.
 * @param catP
 * @param prod
 * @return
 */
int existeProduto(CatProdutos catP,Produto prod){
    int lInicial=prod->cod[0]-65;
    if(search(catP[lInicial].produtos,prod->cod)==NULL) return 0;
    else return 1;
}

/**
 * Função que conta o número total de produtos num cátologo.
 * @param catP
 * @return
 */
int totalProdutos(CatProdutos catP){
    int i,total=0;
    for(i=0;i<26;i++){
       total+=catP[i].size;
    }
    return total;
}

/**
 * Função que conta o número total de produtos iniciados por uma letra num cátologo.
 * @param catP
 * @param letra
 * @return
 */
int totalProdutosLetra(CatProdutos catP, char letra){
    return catP[letra-65].size;
}

/* Funções sobre o tipo Produto */

/**
 * Função que cria um novo produto.
 * @param codigo
 * @return
 */
Produto criaProduto(char * codigo){
    Produto prod=malloc(sizeof(Produto));
    prod->cod=codigo;
    return prod;
}

/**
 * Função que dado um produto retorna o seu código de cliente.
 * @param p
 * @return
 */
char * getCodProd(Produto p){
    return p->cod;
}


