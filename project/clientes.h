#ifndef MAXBUFF
#include "avl.h"
#define MAXBUFF 128
#endif


typedef nodo* cliente;
typedef cliente catClientes[26];

cliente criaCliente(char * cod);
cliente insereCliente(char * cod,cliente raiz);
void initC(cliente* c);



