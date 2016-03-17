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

