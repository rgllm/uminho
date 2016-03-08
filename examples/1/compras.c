#include"compras.h"

/**
 * Inicia a estrutura lista de compras
 * @param l
 * @param p
 * @param pr
 * @param q
 * @return 
 */
ListaCompras initListaCompras(ListaCompras l, char *p, char m, double pr, int q) {
    if(l==NULL){
        l = (ListaCompras) malloc(sizeof(struct listaCompras));
        strcpy(l->produto,p);
        l->modo=m;
        l->preco = pr;
        l->quantidade = q;
        l->prox = NULL;
    }
    return l;
}

/**
 * Inicia estrutura compras
 * @param c
 * @param p
 * @param pr
 * @param q
 * @return 
 */
Compras initCompras(Compras c, char *cl, char *p, char m, double pr, int q) {
    if(c==NULL) {
        c = (Compras) malloc(sizeof(struct compras));
        strcpy(c->cliente,cl);
        c->lista = initListaCompras(c->lista,p,m,pr,q);
        c->esq=c->dir=NULL;
    }
    return c;
}

/**
 * Insere elemento na lista de compras
 * @param l
 * @param p
 * @param pr
 * @param q
 * @return 
 */
ListaCompras insereElemListaCompras(ListaCompras l, char *p, char m, double pr, int q) {
    if(l==NULL) {
        l = initListaCompras(l,p,m,pr,q);
    } else {
        ListaCompras novo;
        novo = (ListaCompras) malloc(sizeof(struct listaCompras));
        strcpy(novo->produto,p);
        novo->modo = m;
        novo->preco = pr;
        novo->quantidade = q;
        novo->prox = l;
        l = novo;
    }
    return l;
}

/**
 * Insere compra na estrutura compras
 * NOTA:    precisa de balancearCP?
 * @param c
 * @param c
 * @param p
 * @param pr
 * @param q
 * @return 
 */
Compras insereCompra(Compras c, char *cl, char *p, char m, double pr, int q) {
    if(c==NULL){
        c = initCompras(c,cl,p,m,pr,q);
    } else if(strcmp(c->cliente,cl)==0) {
        c->lista = insereElemListaCompras(c->lista,p,m,pr,q);
    } else if(strcmp(c->cliente,cl)>0) {
        c->esq = insereCompra(c->esq,cl,p,m,pr,q);
    } else if(strcmp(c->cliente,cl)<0) {
        c->dir = insereCompra(c->dir,cl,p,m,pr,q);    
    }
    
    return c;
}

/**
 * Calcula o comprimento da lista de compras
 * @param l
 */
int comprimentoListaCompras(ListaCompras l) {
    int res=0;
    ListaCompras aux = l;
    
    while(aux!=NULL) {
        res++;
        aux = aux->prox;
    }
    return res;
}


/**
 * Retorna 0 se nao comprou
 * Retorna 1 se comprou 
 * @param l
 * @param p
 * @return 
 */
int clienteComprouProduto(ListaCompras l, char *p) {
    int encontrou = 0;
    ListaCompras laux = l;
    
    if(l != NULL) {
        /* Tem de verificar se é modo N ou P*/
        while(!encontrou && laux) {
            if(strcmp(laux->produto,p)==0){
                encontrou = 1;
            }
            laux = laux->prox;
        }
    }
    /* Alternar res*/
    return encontrou;
}

/**
 * Retorna 0 se nao comprou
 * Retorna 1 se comprou com o modo indicado
 * @param l
 * @param p
 * @return 
 */
int clienteComprouProdutoModo(ListaCompras l,char *p, char m){
    int encontrou = 0;
    ListaCompras laux = l;
    
    if(l != NULL) {
        /* Tem de verificar se é modo N ou P*/
        while(!encontrou && laux) {
            if(strcmp(laux->produto,p)==0 && laux->modo == m){
                encontrou = 1;
            }
            laux = laux->prox;
        }
    }
    /* Alternar res*/
    return encontrou;
}

/**
 * Retorna lista dos clientes que compraram um produto
 * @param c
 * @param l
 * @param p
 * @return 
 */
ListaLigada listaClientesCompraramProduto(Compras c, ListaLigada l, char m, char *p) {
    if(c!=NULL){
        /* Se o cliente comprou o produto */
        if(clienteComprouProdutoModo(c->lista,p,m)) {
            /* Insere-o na lista ligada */
            l = insereElemNaoRepetido(l,c->cliente);
        }
        l = listaClientesCompraramProduto(c->esq,l,m,p);
        l = listaClientesCompraramProduto(c->dir,l,m,p);
    }
    return l;
}


/**
 * Dada uma lista de compras associa pelos produtos somando as 
 * quantidades respetivas
 * NOTA: retorna uma lista de compras sem repetidos
 * @param l
 * @return 
 */
ListaCompras juntaComprasPorProduto(ListaCompras res, ListaCompras l) {
    ListaCompras laux = l;
    
    if(l!=NULL) {
        res = insereElemListaCompras(res,laux->produto,laux->modo,laux->preco,laux->quantidade);
        laux = laux->prox;
        while(laux) {
            /* Se RES já tem o produto actual */
            if(clienteComprouProduto(res,laux->produto)) {
                /* actualiza */
                res = actualizaListaCompras(res,laux->quantidade,laux->produto);
            } else {
                /* Senão adiciona novo produto a RES */
                res = insereElemListaCompras(res,laux->produto,laux->modo,laux->preco,laux->quantidade);
            }
            laux = laux->prox;
        }
    }
    
    return res;
}

/**
 * Junta comrpas por produto (alternativa). Difere na anterior no return.
 * @param res
 * @param l
 * @return 
 */
ListaCompras juntaComprasPorProduto2(ListaCompras a, ListaCompras b) {
    ListaCompras aux=NULL, resultado=NULL;
    
    if(a==NULL) {
        resultado = b;
    } else if(b==NULL) {
        resultado = a;
    } else {
        aux = a;
        /* vamos percorrer a exaustivamente */
        while(aux != NULL) {
            if(clienteComprouProduto(b,aux->produto)){
                /* se já existe o produto,actualiza a quantidade*/
                if(resultado == NULL) {
                    resultado = insereElemListaCompras(resultado,aux->produto,aux->modo,aux->preco,aux->quantidade);
                } else {
                    resultado = actualizaListaCompras(resultado,aux->quantidade,aux->produto);
                }
            } else {
                /* se não tem o produto ainda, adiciona-o à lista */
                resultado = insereElemListaCompras(resultado,aux->produto,aux->modo,aux->preco,aux->quantidade);
            }
            aux = aux->prox;
        }
    }
    
    return resultado;
}

/**
 * Recece uma lista de compras sem produtos repetidos e insere-os numa nova lista
 * de ficando ordenados de forma decrescente tendo em conta a quantidade
 * @param l
 * @param res
 * @return 
 */
ListaCompras insereComprasOrdenadas(ListaCompras res, ListaCompras l) {
    ListaCompras laux=l;
    ListaCompras raux,novo;
    
    if(l!=NULL) {
        while(laux) {
            raux = res;
            /* insere na cabeça no caso de ser maior ou igual */
            if(res == NULL || laux->quantidade <= res->quantidade) {
                res = insereElemListaCompras(res,laux->produto,laux->modo,laux->preco,laux->quantidade);
            } else {
                /* percorre até encontrar uma quantidade menor */
                while(raux->prox != NULL && raux->prox->quantidade <= laux->quantidade) {
                    raux = raux->prox;
                }
                /* insere no fim/meio da lista */
                novo = (ListaCompras) malloc(sizeof(struct listaCompras));
                    strcpy(novo->produto,laux->produto);
                    novo->modo = laux->modo;
                    novo->preco = laux->preco;
                    novo->quantidade = laux->quantidade;
                    novo->prox = raux->prox;
                    raux->prox = novo;
            }
            laux = laux->prox;
        }
    }
    return res;
}

/**
 * Constroi uma lista ligada com os códigos de compras 
 * @param l
 * @param r
 * @return 
 */
ListaLigada listaLigadaDeCompras(ListaCompras l, ListaLigada r) {
    ListaCompras laux = l;
    
    if(l!=NULL) {
        while(laux) {
            r = insereElemento(r,laux->produto);
            laux = laux->prox;
        }
    }
    return r;
}

/**
 * Dada uma lista de compras actualiza-a de modo a que se houver uma
 * compra com o mesmo produto, soma os valores das quantidades
 * @param r
 * @param l
 * @return 
 */
ListaCompras actualizaListaCompras(ListaCompras l, int q, char *p){
   ListaCompras laux = l;
   int actualizado = 0;
   
   while(laux && !actualizado) {
       if(strcmp(laux->produto,p)==0){
           laux->quantidade += q;
           actualizado = 1;
       }
       laux = laux->prox;
   }
   return l;
}

/**
 * Devolve as compras de um cliente num dado mês
 * @param c
 * @param cl
 * @return 
 */
ListaCompras devolveListaComprasCliente(Compras c, ListaCompras aux, char *cl) {
    if(c!=NULL) {
        if(strcmp(c->cliente,cl)==0) {
            aux = c->lista;
        } else if(strcmp(c->cliente,cl)>0) {
            aux = devolveListaComprasCliente(c->esq,aux,cl);
        } else if(strcmp(c->cliente,cl)<0) {
            aux = devolveListaComprasCliente(c->dir,aux,cl);
        }
    }
    return aux;
}

ListaLigada comprasDosClientesParaLista(ListaLigada l, Compras c) {
    if(c!=NULL) {
        l = comprasDosClientesParaLista(l,c->dir);
        l = insereElemento(l,c->cliente);
        l = comprasDosClientesParaLista(l,c->esq);
    }
    return l;
}

/**
 * Conta os clientes que compram num determinado mes
 * @param c
 * @return 
 */
int contaClientes(Compras c){
    if(c == NULL) return 0;
    return 1 + contaClientes(c->esq) + contaClientes(c->dir);
}

/**
 * Dado um mes, calcula quantas compras foram feitas
 * NOTA: compras != vendas
 * @param c
 * @return 
 */
int contaComprasMes(Compras c){
    if(c == NULL) return 0;
    return comprimentoListaCompras(c->lista) + contaComprasMes(c->esq) + contaComprasMes(c->dir);
}


/**
 * Dada uma lista de compras, diz quais são o 3 produtos mais comprados
 * Lista de compras nao tem repetidos, e tem o somatorio das quantidades
 * Pouco eficiente, faz 3 travessias
 * Inserção na lista de forma inversa
 * @param 
 */
ListaLigada tresProdutosMaisComprados(ListaCompras l, ListaLigada p, int *max1, int *max2, int *max3){
    int i,m1,m2,m3;
    char *produto;
    ListaCompras laux;
    
    
    produto = (char *) malloc(7*sizeof(char));
    
    for(i=0;i<3;i++){
        laux = l;
        if(i==0) {
            /* primeiro maximo */
            m1 = 0;
            produto=NULL;
            while(laux) {
                if(laux->quantidade > m1) {
                    m1 = laux->quantidade;
                    produto = strdup(laux->produto);
                }
                laux = laux->prox;
            }
            (*max1) = m1;
            if(produto) {
                p = insereElemento(p,produto);
            }
            
        } else if(i==1) {
            /* segundo maximo */
            m2 = 0;
            produto=NULL;
            while(laux) {
                if(laux->quantidade > m2 && !existeElemento(p,laux->produto)) {
                    m2 = laux->quantidade;
                    produto = strdup(laux->produto);                
                }
                laux = laux->prox;
            }
            (*max2) = m2;
            if(produto) {
                p = insereElemento(p,produto);
            }
            
        } else {
            /* terceiro maximo */
            m3 = 0;
            produto=NULL;
            while(laux) {
                if(laux->quantidade > m3 && !existeElemento(p,laux->produto)) {
                    m3 = laux->quantidade;
                    produto = strdup(laux->produto);
                }
                laux = laux->prox;
            }
            (*max3) = m3;
            if(produto) {
                p = insereElemento(p,produto);
            }
        }
    }
    free(produto);
    return p;
}

int existeCliente(Compras c, char *p){
    int res = 0;

    if(c != NULL){
        if(strcmp(c->cliente,p) == 0) res = 1;
        else {
            if(!res) res = existeCliente(c->esq, p);
            if(!res) res = existeCliente(c->dir, p);
        }
    }
    return res;
}

int existeProduto(Compras c, char *p){
    int res = 0;
 
    if(c!=NULL){
        if(clienteComprouProduto(c->lista,p)) {
            return 1;
        }
        if(!res) {
            res = existeProduto(c->esq,p);
            if(!res) res = existeProduto(c->dir,p);
        }
    }
    return res;

}

/**
 * Cria uma arvore com os clientes que fizeram compras
 * @param cl
 * @param c
 * @return 
 */
NodoC clientesQueCompraram(NodoC cl, Compras c){
    if(c!=NULL && cl!=NULL) {
        cl = insertC(cl,c->cliente);
        cl->esq = clientesQueCompraram(cl->esq,c->esq);
        cl->dir = clientesQueCompraram(cl->dir,c->dir);
        
    }
    return cl;
}

/**
 * Funcao auxiliar para anterior
 * @param cl
 * @param c
 * @return 
 */
NodoC insereClientesQueCompraramNoMes(NodoC cl, Compras c) {
    if(cl!=NULL) {
        if(!existeC(cl,c->cliente)) {
            cl = insertC(cl,c->cliente);
        }
    } else {
        cl = insertC(cl,c->cliente);
    }
    return cl;
    
}


/*************
 Gestão da AVL
 *************/

/** Função que calcula a alturaCP de um nodo **/
int alturaCP(Compras nodo)
{
	int alt = 0;
    if (nodo != NULL){
        int alturaCP_esq = alturaCP(nodo->esq);
        int alturaCP_dir = alturaCP(nodo->dir);
        int max=0;
        if(alturaCP_esq > alturaCP_dir) max = alturaCP_dir;
        alt = 1 + max;
    }
    return alt;
}

/** Função que calcula o fatorCPCP de balanceamento de um nodo **/
int fatorCP (Compras nodo)
{
	int alturaCP_esq = alturaCP(nodo->esq);
	int alturaCP_dir = alturaCP(nodo->dir);
	int dif = alturaCP_esq - alturaCP_dir;
	return dif;
}

/** Rotação Dir Dir **/
Compras rotacao_dir_dirCP(Compras pai)
{
	Compras  nodo1;
	nodo1=pai->dir;
	pai->dir = nodo1->esq;
	nodo1->esq=pai;
	return nodo1;
}

/** Rotação Esq Esq **/
Compras rotacao_esq_esqCP(Compras pai){
	Compras nodo1;
	nodo1 = pai->esq;
	pai->esq = nodo1->dir;
	nodo1->dir = pai;
	return nodo1;
}

/**Rotação Dir Esq */
Compras rotacao_dir_esqCP(Compras  pai)
{
	Compras  nodo1;
	nodo1 = pai->dir;
	pai->dir = rotacao_esq_esqCP(nodo1);
	return rotacao_dir_dirCP(pai);
}

/** Rotação Esq Dir **/
Compras rotacao_esq_dirCP(Compras  pai) {

	Compras nodo1;
	nodo1 = pai->esq;
	pai->esq = rotacao_dir_dirCP(nodo1);
	return rotacao_esq_esqCP(pai);
}

/** Função para balancearCP a AVL **/
Compras balancearCP(Compras nodo)
{
    int bfatorCP = fatorCP(nodo);
    if (bfatorCP >1) {
        if (fatorCP(nodo->esq) >0)
            nodo=rotacao_esq_esqCP(nodo);
        else
            nodo=rotacao_esq_dirCP(nodo);
    }
    else if(bfatorCP < -1) {
        if(fatorCP(nodo->dir) >0)
            nodo=rotacao_dir_esqCP(nodo);
        else
            nodo=rotacao_dir_dirCP(nodo);
    }
    return nodo;
}  
