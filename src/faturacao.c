#include "faturacao.h"

faturacaoProduto tabela[4][13];

/**
 * Inicia a estrutura tabela.
 * @param m
 * @return
 */
void initTabela(){
    int i,j;
    for(i=0;i<4;i++)
        for(j=0;j<13;j++)
            tabela[i][j]=NULL;
}

/**
 * Inicia a estrutura.
 * @param m
 * @return
 */
faturacaoProduto initNComprados(){
    faturacaoProduto ret=malloc(sizeof(faturacaoProduto));
    return ret;
}


/**
 * Coloca os vários parâmetros de um produto na respetiva estrutura infoProduto.
 * @param produto
 * @param qtdNormal
 * @param qtdPromocao
 * @param totalNormal
 * @param totalPromocao
 * @return
 */
infoP criaInfoProduto(char * produto,int qtdNormal,int qtdPromocao,double totalNormal,double totalPromocao ){
    struct infoProduto *ret=(struct infoProduto*)malloc(sizeof(struct infoProduto));
    ret->produto=strdup(produto);
    ret->qtdNormal=qtdNormal;
    ret->qtdPromocao=qtdPromocao;
    ret->totalNormal=totalNormal;
    ret->totalPromocao=totalPromocao;
    return ret;
}


/**
 * Dado um produto coloca as informações desse produto na respetiva célula da matriz faturação
 * e adiciona também os totais nas linhas das filais, linhas dos meses e o total geral.
 * @param produto
 * @param filial
 * @param mes
 * @return
 */
void registaFaturacaoProduto(infoP produto,int filial,int mes){
    nodoFaturacaoProduto nodoProduto;
    if(tabela[filial-1][mes-1]==NULL) tabela[filial-1][mes-1]=criaNodoFat(produto,NULL);
    else{
        tabela[filial-1][mes-1]=insertNodoFat(produto,tabela[filial-1][mes-1]);
        nodoProduto=searchProduto(tabela[filial-1][mes-1],produto->produto);
        if(nodoProduto->produto->qtdNormal!=produto->qtdNormal || nodoProduto->produto->qtdPromocao!=produto->qtdPromocao){
            nodoProduto->produto->qtdNormal+=produto->qtdNormal;
            nodoProduto->produto->qtdPromocao+=produto->qtdPromocao;
            nodoProduto->produto->totalNormal+=produto->totalNormal;
            nodoProduto->produto->totalPromocao+=produto->totalPromocao;
        }
    }

    if(tabela[3][mes-1]==NULL)
        tabela[3][mes-1]=criaNodoFat(produto,NULL);
    else{
        tabela[3][mes-1]=insertNodoFat(produto,tabela[3][mes-1]);
        nodoProduto=searchProduto(tabela[3][mes-1],produto->produto);
        if(nodoProduto->produto->totalNormal!=produto->totalNormal || nodoProduto->produto->totalPromocao!=produto->totalPromocao ){
            nodoProduto->produto->qtdNormal+=produto->qtdNormal;
            nodoProduto->produto->qtdPromocao+=produto->qtdPromocao;
            nodoProduto->produto->totalNormal+=produto->totalNormal;
            nodoProduto->produto->totalPromocao+=produto->totalPromocao;
        }
    }

    if(tabela[filial-1][12]==NULL)
        tabela[filial-1][12]=criaNodoFat(produto,NULL);
    else{
        tabela[filial-1][12]=insertNodoFat(produto,tabela[filial-1][12]);
        nodoProduto=searchProduto(tabela[filial-1][12],produto->produto);
        if(nodoProduto->produto->qtdNormal!=produto->qtdNormal || nodoProduto->produto->qtdPromocao!=produto->qtdPromocao){
            nodoProduto->produto->qtdNormal+=produto->qtdNormal;
            nodoProduto->produto->qtdPromocao+=produto->qtdPromocao;
            nodoProduto->produto->totalNormal+=produto->totalNormal;
            nodoProduto->produto->totalPromocao+=produto->totalPromocao;
        }
    }

    if(tabela[3][12]==NULL)
        tabela[3][12]=criaNodoFat(produto,NULL);
    else{
        tabela[3][12]=insertNodoFat(produto,tabela[3][12]);
        nodoProduto=searchProduto(tabela[3][12],produto->produto);
        if(nodoProduto->produto->qtdNormal!=produto->qtdNormal || nodoProduto->produto->qtdPromocao!=produto->qtdPromocao){
            nodoProduto->produto->qtdNormal+=produto->qtdNormal;
            nodoProduto->produto->qtdPromocao+=produto->qtdPromocao;
            nodoProduto->produto->totalNormal+=produto->totalNormal;
            nodoProduto->produto->totalPromocao+=produto->totalPromocao;
        }
    }
}

/**
 * Carrega um produto para a matriz dos meses/filiais.
 * @param produto
 * @return
 */
void carregaProduto(infoP produto){
    int i;
    for(i=0;i<4;i++){
        if(tabela[i][12]){
            tabela[i][12]=insertNodoFat(produto,tabela[i][12]);
        }
        else {
            tabela[i][12]=criaNodoFat(produto,NULL);
        }
    }
}

/**
 * Dado um mês e um código de um produto retorna o total faturado nesse mês com esse produto em todas as filiais.
 * @param mes
 * @param produto
 * @return
 */
double getTotalFaturadoMes(int mes,char * produto){
    infoP nodoProduto;
    faturacaoProduto nodo=searchProduto(tabela[3][mes-1],produto);
    if(nodo==NULL) return -1;
    nodoProduto= nodo->produto;
    return nodoProduto->totalNormal + nodoProduto->totalPromocao;

}

/**
 * Dado um mês e um código de um produto retorna o total faturado em modo normal nesse mês com esse produto em todas as filiais.
 * @param mes
 * @param produto
 * @return
 */
double getTotalFaturadoMesN(int mes,char * produto){
    infoP nodoProduto;
    faturacaoProduto nodo=searchProduto(tabela[3][mes-1],produto);
    if(nodo==NULL) return -1;
    nodoProduto= nodo->produto;
    return nodoProduto->totalNormal;
}

/**
 * Dado um mês e um código de um produto retorna o total faturado em modo normal nesse mês com esse produto numa
 * determinada filial.
 * @param mes
 * @param produto
 * @param filial
 * @return
 */
double getTotalFaturadoMesN_filial(int mes,char * produto,int filial){
    infoP nodoProduto;
    faturacaoProduto nodo=searchProduto(tabela[filial-1][mes-1],produto);
    if(nodo==NULL) return -1;
    nodoProduto= nodo->produto;
    return nodoProduto->totalNormal;
}

/**
 * Dado um mês e um código de um produto retorna o total faturado em modo promoção nesse mês com esse produto em todas as filiais.
 * @param mes
 * @param produto
 * @return
 */
double getTotalFaturadoMesP(int mes,char * produto){
    infoP nodoProduto;
    faturacaoProduto nodo=searchProduto(tabela[3][mes-1],produto);
    if(nodo==NULL) return -1;
    nodoProduto= nodo->produto;
    return nodoProduto->totalPromocao;
}

/**
 * Dado um mês e um código de um produto retorna o total faturado em modo promoção nesse mês com esse produto numa
 * determinada filial.
 * @param mes
 * @param produto
 * @param filial
 * @return
 */
double getTotalFaturadoMesP_filial(int mes,char * produto, int filial){
    infoP nodoProduto;
    faturacaoProduto nodo=searchProduto(tabela[filial-1][mes-1],produto);
    if(nodo==NULL) return -1;
    nodoProduto= nodo->produto;
    return nodoProduto->totalPromocao;
}

/**
 * Dado um mês e um código de um produto retorna o número total de vendas desse produto em todas as filiais.
 * @param mes
 * @param produto
 * @return
 */
int getTotalVendasMes(int mes,char * produto){
    infoP nodoProduto;
    faturacaoProduto nodo=searchProduto(tabela[3][mes-1],produto);
    if(nodo==NULL) return -1;
    nodoProduto = nodo->produto;
    return nodoProduto->qtdNormal + nodoProduto->qtdPromocao;

}

/**
 * Dado um mês e um código de um produto retorna o número total de vendas em modo normal desse produto em todas as filiais.
 * @param mes
 * @param produto
 * @return
 */
int getTotalVendasMesN(int mes,char * produto){
    infoP nodoProduto;
    faturacaoProduto nodo=searchProduto(tabela[3][mes-1],produto);
    if(nodo==NULL) return -1;
    nodoProduto = nodo->produto;
    return nodoProduto->qtdNormal;

}

/**
 * Dado um mês e um código de um produto retorna o número total de vendas em modo normal desse produto
 * numa determinada filial.
 * @param mes
 * @param produto
 * @param filial
 * @return
 */
int getTotalVendasMesN_filial(int mes,char * produto, int filial){
    infoP nodoProduto;
    faturacaoProduto nodo=searchProduto(tabela[filial-1][mes-1],produto);
    if(nodo==NULL) return -1;
    nodoProduto = nodo->produto;
    return nodoProduto->qtdNormal;

}

/**
 * Dado um mês e um código de um produto retorna o número total de vendas em modo promoção desse produto em todas as filiais.
 * @param mes
 * @param produto
 * @return
 */
int getTotalVendasMesP(int mes,char * produto){
    infoP nodoProduto;
    faturacaoProduto nodo=searchProduto(tabela[3][mes-1],produto);
    if(nodo==NULL) return -1;
    nodoProduto = nodo->produto;
    return nodoProduto->qtdPromocao;

}

/**
 * Dado um mês e um código de um produto retorna o número total de vendas em modo promoção desse produto
 * numa determinada filial.
 * @param mes
 * @param produto
 * @param filial
 * @return
 */
int getTotalVendasMesP_filial(int mes,char * produto, int filial){
    infoP nodoProduto;
    faturacaoProduto nodo=searchProduto(tabela[filial-1][mes-1],produto);
    if(nodo==NULL) return -1;
    nodoProduto = nodo->produto;
    return nodoProduto->qtdPromocao;
}

/**
 * Dado a árvore do respetivo mes/total e respetiva filial/total retorna o total faturado em todos os produtos.
 * Função nodoProdutoiliar da função contaTotalFaturado.
 * @param raiz
 * @return
 */
double contaTotalFaturadoMes(faturacaoProduto raiz){
    infoP produto;
    double faturacao;
    if(raiz==NULL) return 0;
    else {
        produto = raiz->produto;
        faturacao=produto->totalNormal+produto->totalPromocao;
        return faturacao + contaTotalFaturadoMes(raiz->esq) + contaTotalFaturadoMes(raiz->dir);
    }
}

/**
 * Dado um mês inicial e um mês final conta o total faturado entre esse período em todas as filiais.
 * @param mesI
 * @param mesF
 * @return
 */
double contaTotalFaturado(int mesI, int mesF){
    double totFat=0;
    int i;
    for(i=mesI-1; i<mesF; i++){
        totFat+=contaTotalFaturadoMes(tabela[3][i]);
    }
return totFat;
}

/**
 * Dado a árvore do respetivo mes/total e respetiva filial/total retorna o total de vendas em todas as filiais.
 * Função nodoProdutoiliar da função contaTotalVendas.
 * @param raiz
 * @return
 */
int contaTotalVendasMes(faturacaoProduto raiz){
    infoP produto;
    int vendas;
    if(raiz==NULL) return 0;
    else {
        produto = raiz->produto;
        vendas=(produto->qtdNormal+produto->qtdPromocao);
        return vendas + contaTotalVendasMes(raiz->esq) + contaTotalVendasMes(raiz->dir);
    }
}

/**
 * Dado um mês inicial e um mês final conta o total de vendas entre esse período em todas as filiais.
 * @param mesI
 * @param mesF
 * @return
 */
int contaTotalVendas(int mesI, int mesF){
   int totVendas=0,i;
    for(i=mesI-1; i<mesF; i++){
        totVendas+=contaTotalVendasMes(tabela[3][i]);
    }
return totVendas;
}

/**
 * Conta o total de produtos não comprados numa árvore.
 * @param raiz
 * @return
 */
int contaNaoComprados(nodoFaturacaoProduto raiz){
    infoP nodoProduto;
    if(raiz==NULL)
        return 0;
    nodoProduto = raiz->produto;
    if(nodoProduto->qtdNormal == 0 && nodoProduto->qtdPromocao == 0)return 1+contaNaoComprados(raiz->esq)+contaNaoComprados(raiz->dir);
    else return contaNaoComprados(raiz->esq)+contaNaoComprados(raiz->dir);
}


/**
 * Conta o total de produtos não comprados numa filial.
 * @param raiz
 * @return
 */
int contaNaoCompradosFilial(int filial){
    int indice = filial-1;
    return contaNaoComprados(tabela[indice][12]);

}

/**
 * Conta o total de produtos não comprados numa árvore.
 * @param raiz
 * @return
 */
nodo * naoComprados(nodo * nComprados, faturacaoProduto totais){
    infoP produto;
    if(totais==NULL) return NULL;
        produto = totais->produto;
    if(produto->qtdPromocao==0 && produto->qtdNormal==0){
        if(nComprados==NULL){
            nComprados=criaNodo(produto->produto,NULL);
        }
        else{
            nComprados=insert(produto->produto, nComprados);
        }
    }
    nComprados=naoComprados(nComprados, totais->esq);
    nComprados=naoComprados(nComprados, totais->dir);
    return nComprados;
}

/**
 * Conta o total de produtos não comprados numa filial.
 * @param raiz
 * @return
 */
nodo * getNaoCompradosFilial(int filial){
    nodo * nComprados=naoComprados(NULL, tabela[filial-1][12]);
    return nComprados;
}

faturacaoProduto getTotalFilial(int filial){
    return tabela[filial-1][12];
}

void swapString(char * * x, char * * y) {
    char *t = *y;
    *y = *x;
    *x = t;
}

void swapInt(int * x, int * y) {
    *x ^= *y;
    *y ^= *x;
    *x ^= *y;
}


void preencheProdutos(char * * produtos, int * qtd, int n, nodoFaturacaoProduto nodo){
    int i, aux;

    if(nodo==NULL) return;
    aux=nodo->produto->qtdNormal+nodo->produto->qtdPromocao;
    if(nodo==tabela[3][12])
        for(i=0;i<n;i++){
            produtos[i]=strdup(nodo->produto->produto);
            qtd[i]=aux;
        }
    if(aux>qtd[n-1]){
        free(produtos[n-1]);
        produtos[n-1]=strdup(nodo->produto->produto);
        qtd[n-1]=aux;
        for(i = n-2; i >= 0 && qtd[i]<qtd[i+1]; i--){
            swapString(&produtos[i], &produtos[i+1]);
            swapInt(&qtd[i], &qtd[i+1]);
        }
    }
    preencheProdutos(produtos, qtd, n, nodo->esq);
    preencheProdutos(produtos, qtd, n, nodo->dir);
}

/**
 *
 * @param mes
 * @param produto
 * @return
 */
int totalVendasPFilial(char * produto, int filial){
    infoP nodoProduto;
    faturacaoProduto nodo=searchProduto(tabela[filial-1][12],produto);
    if(nodo==NULL) return 0;
    nodoProduto = nodo->produto;
    return nodoProduto->qtdPromocao+nodoProduto->qtdNormal;

}


void printNaoComprados(int filial){
    nodoFaturacaoProduto raiz;
    if(filial==1) raiz=tabela[0][12];
    if(filial==2) raiz=tabela[1][12];
    if(filial==3) raiz=tabela[2][12];
    if(filial==4) raiz=tabela[3][12];
    printNaoCompradosAVL(raiz,contaNaoCompradosFilial(filial));
}