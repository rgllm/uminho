#include "clientes.h"
#include "produtos.h"
#define MAXBUFF 64

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

void registaVenda(info * inf,char produto[], double preco, int qtd, char np, char cliente[], int mes, int filial){
    strcpy(inf->produto,produto);
    inf->preco = preco;
    inf->qtd = qtd;
    inf->np = np;
    strcpy(inf->cliente,cliente);
    inf->mes = mes;
    inf->filial = filial;
}

int validaVenda(catClientes clientes,catProdutos produtos,info* inf){
	int aux;
	aux=inf->produto[0]-65;
	if(search(produtos[aux],inf->produto)!=NULL){
		aux=inf->cliente[0]-65;
		if(search(clientes[aux],inf->cliente)!=NULL){
			if(inf->qtd>0 && 
				inf->preco>=0 &&
				inf->mes>=1 && inf->mes <=12 &&
				(inf->np=='N' || inf->np=='P') &&
				inf->filial>=1 && inf->filial<=3 )
				return 0;
		}
	}
	return 1;
}


int main(){
	catClientes clientes;
	catProdutos produtos;
   /*) catVendas vendas; */
	int aux,c,qtd,mes,fil;
	char cod[10],linha[MAXBUFF], buffer[MAXBUFF],*prod,np,*cli,*precAux;
	double prec;
	info venda;
	FILE *fp,*fp2;
	int pzero=0,unidades=0;
 	int filial1=0, filial2=0, filial3=0;
 	double ftotal=0;


	/*                 Leitura dos clientes                 */
	
	initC(clientes);
	fp=fopen("files/Clientes.txt","r");
	while( fgets (buffer, MAXBUFF, fp)){
	    strcpy(cod,buffer);
	    strtok(cod,"\r\n");
	    aux=cod[0]-65;
	    if(clientes[aux]==NULL){
	        clientes[aux]=criaCliente(cod);
	    }
	   else if(!existeCliente(clientes,aux,cod)){
		        clientes[aux]=insereCliente(cod,clientes[aux]);
		     }
	}
	fclose(fp);
	printf("Clientes: %d\n",totalClientes(clientes) );


	/*                 Leitura dos produtos                 */

	initP(produtos);
	fp=fopen("files/Produtos.txt","r");
	while( fgets (buffer, MAXBUFF, fp)){
	    strcpy(cod,buffer);
	    strtok(cod,"\r\n");
	    aux=cod[0]-65;
	    if(produtos[aux]==NULL){
	        produtos[aux]=criaProduto(cod);
	    }
	    else if(!existeProduto(produtos,aux,cod)){
		        produtos[aux]=insereProduto(cod,produtos[aux]);
		     }
	}
	fclose(fp);
	printf("Produtos: %d\n",totalProdutos(produtos) );
    
	/*                 Leitura das vendas                   */
	c=0;
	/*initV(vendas); */
    fp = fopen( "files/Vendas1.txt", "r" );
    fp2=fopen("Vendas_1MValidas.txt", "w");
	while (fgets(buffer, MAXBUFF,fp)!=NULL){
		strcpy(linha,buffer);
		prod=strtok(buffer," ");
		prec=strtod(strtok(NULL," "),&precAux);
		qtd=atoi(strtok(NULL," "));
		np=strtok(NULL," ")[0];
		cli=strtok(NULL," ");
		mes=atoi(strtok(NULL," "));
		fil=atoi(strtok(NULL," "));
		registaVenda(&venda,prod,prec,qtd,np,cli,mes,fil);
		if(validaVenda(clientes,produtos,&venda)==0){
			fprintf(fp2,"%s", linha);
			c++;
			/* Testes sobre as vendas */
 			if(prec==0) pzero++;
 			ftotal+=(prec*qtd);
 			unidades+=qtd;
			if(fil==1) filial1++;
			if(fil==2) filial2++;
			if(fil==3) filial3++;
		}
	}

	printf("Vendas: %d\n",c );
	printf("Vendas de preço zero: %d\n",pzero);
 	printf("Faturação Total: %f\n",ftotal);
	printf("Unidades Vendidas: %d\n",unidades);
 	printf("Filial 1: %d Filial 2: %d Filial 3: %d \n",filial1,filial2,filial3 );
	fclose(fp2);
	fclose(fp);
	return 1;
}
