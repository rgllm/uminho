#ifndef AVL
#include "avl.h"
#define AVL
#endif
#ifndef DUP
#include "others.h"
#define strdup(x) my_strdup(x)
#endif

/*
 * Definição das estruturas
 */

typedef struct cliente* Cliente;
typedef struct listaC* ListaClientes;
typedef struct catClientes* CatClientes;
typedef nodo * nodoCliente;


/*
 * Funções sobre o tipo CATCLIENTES
 */

CatClientes initCatClientes();
CatClientes insereCliente(CatClientes catC, Cliente c);
int existeCliente(CatClientes catC, Cliente c);
int totalClientes(CatClientes catC);
int totalClientesLetra(CatClientes catC, char letra);
void removeCatClientes(CatClientes catC);
nodoCliente getAVLCli(CatClientes p,int lC);

/*
 * Funções sobre o tipo CLIENTES
 */

Cliente criaCliente(char * codigo);
char * getCodCliente(Cliente c);
