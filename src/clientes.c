#include "clientes.h"
#ifndef CLIENTE

typedef struct cliente{
    char * cod;
}cliente;


typedef struct listaC{
    nodoCliente cliente;
    /* TODO - size of lista */
}listaC;

typedef struct catClientes{
    int size;
    nodoCliente clientes;
}catClientes[26];
#define CLIENTE
#endif

nodoCliente getAVLCli(CatClientes p,int lC){
    return p[lC].clientes;
}

/* Funções relativas ao tipo CatClientes */

CatClientes initCatClientes(){
    int i;
    CatClientes clientes=malloc(26*sizeof(struct catClientes));
    for(i=0;i<26;i++){
       clientes[i].size=0;
        clientes[i].clientes=NULL;
    }
    return clientes;

}

CatClientes insereCliente(CatClientes catC, Cliente c){
    int lInicial = c->cod[0]-65;
    if(catC[lInicial].clientes==NULL){
        catC[lInicial].clientes=criaNodo(c->cod,NULL);
    }
    else {
        catC[lInicial].clientes=insert(c->cod,catC[lInicial].clientes);
    }
    catC[lInicial].size++;
    return catC;
}

int existeCliente(CatClientes catC, Cliente c){
    int lInicial = c->cod[0]-65;
    if(search(catC[lInicial].clientes,c->cod)==NULL) return 0;
    else return 1;
}

int totalClientes(CatClientes catC){
    int i,total=0;
    for(i=0;i<26;i++){
       total+=catC[i].size;
    }
    return total;
}

int totalClientesLetra(CatClientes catC, char letra){
    return catC[letra-65].size;
}

void removeCatClientes(CatClientes catC){
    int i;
    for(i=0;i<26;i++) {freeTree(catC[i].clientes);}
    free(catC);
}

/* Funções relativas ao tipo Cliente */

Cliente criaCliente(char * codigo){
    Cliente c = malloc(sizeof(Cliente));
    c->cod=codigo;
    return c; 
}

char * getCodCliente(Cliente c){
    return c->cod;
}



