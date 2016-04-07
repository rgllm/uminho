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

int existeCliente(catClientes clientes,int indice,char * cod){
    if(search(clientes[indice],cod)==NULL)
        return 0;
    return 1;
}

int totalClientes(catClientes clientes){
    int i,count=0;
    for(i=0;i<26;i++){
        count+=conta(clientes[i]);
    }
    return count;
}

int totalClientesLetra(catClientes clientes, char letra){
    return conta(clientes[letra-65]);
}

void removeCatClientes(catClientes clientes){
    int i;
    for(i = 0; i < 26; i++){
        clientes[i]=NULL;      
    }
}
