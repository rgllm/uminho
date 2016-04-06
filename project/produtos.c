#include "produtos.h"

produto criaProduto(char* cod){
    return criaNodo(cod,NULL);
}

produto insereProduto(char * cod,produto raiz){
    return insert(cod,raiz);
}

void initP(catProdutos c){
    int i;
    for(i=0;i<26;i++)
        c[i]=NULL;
}

int existeProduto(catProdutos produtos,int indice,char * cod){
    if(search(produtos[indice],cod)==NULL)
        return 0;
    return 1;
}

int totalProdutos(catProdutos produtos){
    int i,count=0;
    for(i=0;i<26;i++){
        count+=conta(produtos[i]);
    }
    return count;
}

int totalProdutosLetra(catProdutos produtos, char letra){
    return conta(produtos[letra-65]);
}

void removeCatProdutos(catProdutos produtos){
    int i;
    for(i = 0; i < 26; i++){
        produtos[i]=NULL;      
    }
}
