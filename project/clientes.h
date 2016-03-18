#ifndef AVL
#include "avl.h"
#define AVL
#endif

typedef nodo* cliente;
typedef cliente catClientes[26];

cliente criaCliente(char * cod);
cliente insereCliente(char * cod,cliente raiz);
void initC(cliente* c);



