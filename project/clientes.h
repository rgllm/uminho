#ifndef AVL
#include "avl.h"
#define AVL
#endif

#ifndef CLIENTE

typedef struct cliente{
    char * cod;
}cliente;

typedef nodo * nodoCliente;

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

/*
 * Definição das estruturas
 */

typedef struct cliente* Cliente;
typedef struct listaC* ListaClientes;
typedef struct catClientes* CatClientes;

/*
 * Funções sobre o tipo CATCLIENTES
 */

CatClientes initCatClientes();
CatClientes insereCliente(CatClientes catC, Cliente c);
int existeCliente(CatClientes catC, Cliente c);
int totalClientes(CatClientes catC);
int totalClientesLetra(CatClientes catC, char letra);
void removeCatClientes(CatClientes catC);

/*
 * Funções sobre o tipo CLIENTES
 */

Cliente criaCliente(char * codigo);
char * getCodCliente(Cliente c);
