#include "clientes.h"


cliente criaCliente(char* cod){
    return criaNodo(cod,NULL);
}

cliente insereCliente(char * cod,cliente raiz){
    return insert(cod,raiz);
}

void initC(catClientes c){
    int i;
    for(i=0;i<26;i++)
        c[i]=NULL;
}

