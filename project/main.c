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

/*

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

*/

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
	int aux,c=0,qtd,mes,fil;
	char cod[10],linha[MAXBUFF], buffer[MAXBUFF],*prod,np,*cli,*precAux;
	double prec;
	info venda;
	FILE *fp,*fp2;
    int pzero=0,unidades=0;
    double ftotal=0, filial1=0, filial2=0, filial3=0;

	/*                 Leitura dos clientes                 */
	
	initC(clientes);
	fp=fopen("files/Clientes.txt","r");
	while( fgets (buffer, MAXBUFF, fp)){
	    strcpy(cod,buffer);
	    strtok(cod,"\r\n");
	    aux=cod[0]-65;
	    if(clientes[aux]==NULL){
	        clientes[aux]=criaCliente(cod);
	        c++;
	    }
	   else if(search(clientes[aux],cod)==NULL){
		        clientes[aux]=insereCliente(cod,clientes[aux]);
		    	c++;
		     }
	}
	fclose(fp);
	printf("Clientes: %d\n",c );


	/*                 Leitura dos produtos                 */

	c=0;
	initP(produtos);
	fp=fopen("files/Produtos.txt","r");
	while( fgets (buffer, MAXBUFF, fp)){
	    strcpy(cod,buffer);
	    strtok(cod,"\r\n");
	    aux=cod[0]-65;
	    if(produtos[aux]==NULL){
	        produtos[aux]=criaProduto(cod);
	        c++;
	    }
	    else if(search(produtos[aux],cod)==NULL){
		        produtos[aux]=insereProduto(cod,produtos[aux]);
		     	c++;
		     }
	}
	fclose(fp);
	printf("Produtos: %d\n",c );
    
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
	printf("Vendas:%d\n",c );
    printf("Vendas preço zero: %d\n",pzero);
    printf("Faturação Total: %f\n",ftotal);
    printf("Unidades Vendidas: %d\n",unidades);
    printf("Filial 1: %f Filial 2: %f Filial 3: %f \n",filial1,filial2,filial3 );
	fclose(fp2);
	fclose(fp);
	return 1;
}
