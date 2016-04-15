/*   
    duvida typedefs
    comparaProduto

 */


#include "clientes.h"
#include "produtos.h"
#include "faturacao.h"
#define MAXBUFF 64





/*
typedef nodoV* venda;
typedef venda catVendas[26];

#ifndef INFO
#define INFO
typedef struct info{
    char produto[8];
    double preco;
    int qtd;
    char np;
    char cliente[8];
    int mes;
    int filial;
}info;
#endif

venda criaVenda(info inf){
    return criaNodoV(inf,NULL);
}

venda insereVenda(info cod,venda raiz){
    return insertV(cod,raiz);
}


void initV(venda * c){
    int i;
    for(i=0;i<26;i++)
        c[i]=NULL;
}



void registaVenda(info * inf,char produto[], double preco, int qtd, char np, char cliente[], int mes, int filial){
    strcpy(inf->produto,produto);
    inf->preco = preco;
    inf->qtd = qtd;
    inf->np = np;
    strcpy(inf->cliente,cliente);
    inf->mes = mes;
    inf->filial = filial;
}
*/
int validaVenda(CatClientes c,CatProdutos p,char * produto,double preco,int qtd,char np,char * cliente,int mes,int filial){
	int lC,lP;
    printf("INICIO\n");
	lP=produto[0]-65;
    lC=cliente[0]-65;
	if(search(getAVLProd(p,lP),produto)!=NULL){
		if(search(getAVLCli(c,lC),cliente)!=NULL){

			if(qtd>0 && 
				preco>=0 &&
				mes>=1 && mes <=12 &&
				(np=='N' || np=='P') &&
				filial>=1 && filial<=3 )
                printf("FIM\n");
				return 0;
		}

	}
    printf("FIM\n");
	return 1;
}


int main(){
	CatClientes catClientes;
	CatProdutos catProd;
	int c,qtd,mes,fil;
	char cod[10],linha[MAXBUFF], buffer[MAXBUFF],*produto,np,*cli,*precAux;
	double prec;
	infoP aux;
	FILE *fp;
	int pzero=0,unidades=0;
 	int filial1=0, filial2=0, filial3=0;
 	double ftotal=0;
    Produto prod;
    Cliente cliente;


	/*                 Leitura dos clientes                 */
	
	catClientes=initCatClientes();
	fp=fopen("files/Clientes.txt","r");
	while( fgets (buffer, MAXBUFF, fp)){
       strcpy(cod,buffer);
       strtok(cod,"\r\n");
       cliente = criaCliente(cod);
       if(!existeCliente(catClientes,cliente)){
            catClientes=insereCliente(catClientes,cliente);
        }
    }
    fclose(fp);


	/*                 Leitura dos produtos                 */
    catProd=initCatProdutos();
    fp=fopen("files/Produtos.txt","r");
    while( fgets (buffer, MAXBUFF, fp)){
        strcpy(cod,buffer);
        strtok(cod,"\r\n");
        prod=criaProduto(cod);
        if(!existeProduto(catProd,prod)){
            catProd=insereProduto(catProd,prod);
        }
    }
    fclose(fp);
    

    /*                 Leitura das vendas                   */
    c=0;
    initTabela();
    fp = fopen( "files/Vendas1.txt", "r" );
	while (fgets(buffer, MAXBUFF,fp)!=NULL){
		strcpy(linha,buffer);
		produto=strtok(buffer," ");
		prec=strtod(strtok(NULL," "),&precAux);
		qtd=atoi(strtok(NULL," "));
		np=strtok(NULL," ")[0];
		cli=strtok(NULL," ");
		mes=atoi(strtok(NULL," "));
		fil=atoi(strtok(NULL," "));
        
		if(validaVenda(catClientes,catProd,produto,prec,qtd,np,cli,mes,fil)==0){
            printf("-\n");
            prec=qtd*prec;
            if(np=='N'){
                printf("    NORMAL\n");

                aux=(infoP)criaInfoProduto(produto,qtd,0,prec,0);
                registaFaturacaoProduto(aux, mes , fil );

            }
            else {
                printf("    PROMOCAO\n");
                aux=(infoP)criaInfoProduto(produto,0,qtd,0,prec);
                printf("    %f\n",aux->totalPromocao);
                registaFaturacaoProduto(aux , mes , fil);
                printf("    PROMOCAO\n");
            }
            c++;
		}
        else printf("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$\n");
	}

	printf("Vendas: %d\n",c );
	printf("Vendas de preço zero: %d\n",pzero);
 	printf("Faturação Total: %f\n",ftotal);
	printf("Unidades Vendidas: %d\n",unidades);
 	printf("Filial 1: %d\nFilial 2: %d\nFilial 3: %d\n",filial1,filial2,filial3 );

	fclose(fp);
	return 1;
}



/*


    printf("1- Ler ficheiros para memória\n");
    printf("2- Determinar a lista e o total de produtos cujo código se inicia por uma dada letra\n");
    printf("3- Dado um mês e um código de produto determinar e apresentar o número total de vendas e o total faturado com esse produto\n");
    printf("4- Determinar a lista ordenada dos códigos dos produtos que ninguém comprou\n");
    printf("5- Dado um código de cliente criar uma tabela com o número total de produtos comprados mês a mês\n");
    printf("6- Dado um intervalo de meses determinar o total de vendas registadas e o total faturado\n");
    printf("7- Determinar a lista ordenada de códigos de clientes que realizaram compras em todas a filiais\n");
    printf("8- Dado um código de produto e uma filial determinar os códigos dos clientes que o compraram\n");
    printf("9- Dado um código de cliente e um mês determinar a lista de códigos de produtos que mais comprou por quantidade\n");
    printf("10- Criar uma lista nos N produtos mais vendidos em todo o ano\n");
    printf("11- Dado um código de cliente determinar quais os códigos dos 3 produtos em que gastou mais dinheiro durante o ano\n");
    printf("12- Determinar o número de clientes registados que não realizaram compras e o número de produtos que ninguém comprou\n");
 
    while(fgets(querie,MAXBUFF,0)!="q"){
        if(querie=="1"){
            catClientes clientes;
            catProdutos produtos;

            carregar(clientes,produtos);
        }


}
*/
