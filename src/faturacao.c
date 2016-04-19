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

    nodoFaturacaoProduto aux;
    if(tabela[filial-1][mes-1]==NULL)
        tabela[filial-1][mes-1]=criaNodoFat(produto,NULL);
    else{
        tabela[filial-1][mes-1]=insertNodoFat(produto,tabela[filial-1][mes-1]);
        aux=searchProduto(tabela[filial-1][mes-1],produto->produto);
        if(aux->produto->qtdNormal!=produto->qtdNormal || aux->produto->qtdPromocao!=produto->qtdPromocao){
            aux->produto->qtdNormal+=produto->qtdNormal;
            aux->produto->qtdPromocao+=produto->qtdPromocao;
            aux->produto->totalNormal+=produto->totalNormal;
            aux->produto->totalPromocao+=produto->totalPromocao;
        }
    }

    if(tabela[3][mes-1]==NULL)
        tabela[3][mes-1]=criaNodoFat(produto,NULL);
    else{
        tabela[3][mes-1]=insertNodoFat(produto,tabela[3][mes-1]);
        aux=searchProduto(tabela[3][mes-1],produto->produto);
        if(aux->produto->totalNormal!=produto->totalNormal || aux->produto->totalPromocao!=produto->totalPromocao ){
            aux->produto->qtdNormal+=produto->qtdNormal;
            aux->produto->qtdPromocao+=produto->qtdPromocao;
            aux->produto->totalNormal+=produto->totalNormal;
            aux->produto->totalPromocao+=produto->totalPromocao;
        }
    }

    if(tabela[filial-1][12]==NULL)
        tabela[filial-1][12]=criaNodoFat(produto,NULL);
    else{
        tabela[filial-1][12]=insertNodoFat(produto,tabela[filial-1][12]);
        aux=searchProduto(tabela[filial-1][12],produto->produto);
        if(aux->produto->qtdNormal!=produto->qtdNormal || aux->produto->qtdPromocao!=produto->qtdPromocao){
            aux->produto->qtdNormal+=produto->qtdNormal;
            aux->produto->qtdPromocao+=produto->qtdPromocao;
            aux->produto->totalNormal+=produto->totalNormal;
            aux->produto->totalPromocao+=produto->totalPromocao;
        }
    }

    if(tabela[3][12]==NULL)
        tabela[3][12]=criaNodoFat(produto,NULL);
    else{
        tabela[3][12]=insertNodoFat(produto,tabela[3][12]);
        aux=searchProduto(tabela[3][12],produto->produto);
        if(aux->produto->qtdNormal!=produto->qtdNormal || aux->produto->qtdPromocao!=produto->qtdPromocao){
            aux->produto->qtdNormal+=produto->qtdNormal;
            aux->produto->qtdPromocao+=produto->qtdPromocao;
            aux->produto->totalNormal+=produto->totalNormal;
            aux->produto->totalPromocao+=produto->totalPromocao;
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
    infoP aux;
    faturacaoProduto nodo=searchProduto(tabela[3][mes-1],produto);
    if(nodo==NULL) return -1;
    aux= nodo->produto;
    return aux->totalNormal + aux->totalPromocao;

}

/**
 * Dado um mês e um código de um produto retorna o número total de vendas desse produto em todas as filiais.
 * @param mes
 * @param produto
 * @return
 */
int getTotalVendasMes(int mes,char * produto){
    infoP aux;
    faturacaoProduto nodo=searchProduto(tabela[3][mes-1],produto);
    if(nodo==NULL) return -1;
    aux = nodo->produto;
    return aux->qtdNormal + aux->qtdPromocao;

}

/**
 * Dado a árvore do respetivo mes/total e respetiva filial/total retorna o total faturado em todos os produtos.
 * Função auxiliar da função contaTotalFaturado.
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
 * Função auxiliar da função contaTotalVendas.
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

int contaNaoComprados(nodoFaturacaoProduto raiz){
    infoP aux;
    if(raiz==NULL)
        return 0;
    aux = raiz->produto;
    if(aux->qtdNormal == 0 && aux->qtdPromocao == 0)return 1+contaNaoComprados(raiz->esq)+contaNaoComprados(raiz->dir);
    else return contaNaoComprados(raiz->esq)+contaNaoComprados(raiz->dir);
}



int contaNaoCompradosFilial(int filial){
    int indice = filial-1;
    return contaNaoComprados(tabela[indice][12]);

}


nodo * naoComprados(nodo * nComprados, faturacaoProduto totais){
    if(totais==NULL) return NULL;
    infoP produto = totais->produto;
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

nodo * getNaoCompradosFilial(int filial){
    nodo * nComprados=naoComprados(NULL, tabela[filial-1][12]);
    return nComprados;
}




/*
int getNaoComprados(){
    return contaNaoComprados(tabela[3][12]);
}

int contaNaoComprados(faturacaoProduto raiz){
    if(raiz==NULL)
        return 0;
    infoP aux=raiz->produto;
    if(aux->qtdPromocao==0 && aux->qtdNormal==0)
        return (1 + contaNaoComprados(raiz->esq) + contaNaoComprados(raiz->dir));
    return contaNaoComprados(raiz->esq) + contaNaoComprados(raiz->dir);
}
*/

/*void getQuery3(int mes,char * produto,double * totFat,int * totVendas){
    infoP aux;
    faturacaoProduto nodo=searchProduto(tabela[3][mes-1],produto);
    if(nodo==NULL) *totFat=-1;
    else{
        aux= nodo->produto;
        *totFat=aux->totalNormal+aux->totalPromocao;
        *totVendas=aux->qtdNormal+aux->qtdPromocao;
    }
}*/
