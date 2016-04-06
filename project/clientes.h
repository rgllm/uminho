#ifndef AVL
#include "avl.h"
#define AVL
#endif

typedef nodo* cliente;
typedef cliente catClientes[26];
typedef nodo* listaClientes;

cliente criaCliente(char * cod);
cliente insereCliente(char * cod,cliente raiz);
void initC(cliente* c);
int existeCliente(catClientes clientes,int indice,char * cod);
int totalClientes(catClientes clientes);
int totalClientesLetra(catClientes clientes, char letra); 
void removeCatClientes(catClientes clientes); 
