#include "clientes.h"
#ifndef CLIENTE

typedef struct cliente{
    char * cod;
}cliente;


typedef struct listaC{
    nodoCliente cliente;
}listaC;

typedef struct catClientes{
    int size;
    nodoCliente clientes;
}catClientes[26];
#define CLIENTE
#endif


/**
 * Dado um cátologo de clientes e uma letra inicial a função retorna a árvore dos clientes que começam por essa letra.
 * @param p
 * @param lC
 * @return
 */
nodoCliente getAVLCli(CatClientes p,int lC){
    return p[lC].clientes;

}

/* Funções relativas ao tipo CatClientes */


/**
 * Função que cria e inicia o cátologo de clientes.
 * @return
 */
CatClientes initCatClientes(){
    int i;
    CatClientes clientes=malloc(26*sizeof(struct catClientes));
    for(i=0;i<26;i++){
       clientes[i].size=0;
        clientes[i].clientes=NULL;
    }
    return clientes;

}

/**
 * Função que insere um cliente num cátologo de clientes.
 * @param catC
 * @param c
 * @return
 */
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

/**
 * Função que verifica se um dado cliente existe num cátologo.
 * Retorna 0 caso não exista e 1 caso exista.
 * @param catC
 * @param c
 * @return
 */
int existeCliente(CatClientes catC, Cliente c){
    int lInicial = c->cod[0]-65;
    if(search(catC[lInicial].clientes,c->cod)==NULL) return 0;
    else return 1;
}

/**
 * Função que conta o número total de clientes num cátologo.
 * @param catC
 * @return
 */
int totalClientes(CatClientes catC){
    int i,total=0;
    for(i=0;i<26;i++){
       total+=catC[i].size;
    }
    return total;
}

/**
 * Função que conta o número total de clientes iniciados por uma letra num cátologo.
 * @param catC
 * @param letra
 * @return
 */
int totalClientesLetra(CatClientes catC, char letra){
    return catC[letra-65].size;
}

/**
 * Função que elimina um cátologo de clientes.
 * @param catC
 */
void removeCatClientes(CatClientes catC){
    int i;
    for(i=0;i<26;i++) {freeTree(catC[i].clientes);}
    free(catC);
}

/* Funções relativas ao tipo Cliente */


/**
 * Função que cria um novo clinete.
 * @param codigo
 * @return
 */
Cliente criaCliente(char * codigo){
    Cliente c = malloc(sizeof(Cliente));
    c->cod=codigo;
    return c;
}

/**
 * Função que dado um cliente retorna o seu código de cliente.
 * @param c
 * @return
 */
char * getCodCliente(Cliente c){
    return c->cod;
}



