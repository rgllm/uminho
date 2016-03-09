#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>

typedef struct venda{
	char produto[8];
	double preco;
	int qtd;
	char np;
	char cliente[8];
	int mes;
	int filial;
}venda;


typedef struct nodoV
{
	venda info;
	struct nodoV *esq;
	struct nodoV *dir;
	struct nodoV *pai;
	int altura;
} nodoV;

nodoV *searchV(nodoV *raiz, venda info);
int alturaV(nodoV *raiz);
void ajustaAlturaV(nodoV *raiz);
nodoV *rodaDirV(nodoV *raiz);
nodoV *rodaEsqV(nodoV *raiz);
nodoV *criaNodoV(venda info, nodoV *pai);
nodoV *balanceV(nodoV *raiz);
nodoV *insertV(venda info,nodoV *raiz);
void print_tree_indentV(nodoV *nodoV, int indent);
void print_treeV(nodoV *nodoV);
void criaVenda(venda * info,char produto[], double preco, int qtd, char np, char cliente[], int mes, int filial);
int vendaCmp(venda v1,venda v2);