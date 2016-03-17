#ifndef MAXBUFF
#include "avl.h"
#define MAXBUFF 128
#endif

typedef nodo* produto;
typedef produto catProdutos[26];

produto criaProduto(char * cod);
produto insereProduto(char * cod,produto raiz);
void initP(catProdutos c);



