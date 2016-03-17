#include "clientes.h"
#include "produtos.h"
#include "vendas.h"


/*
void carregaVendas(nodoV* vendas[],nodo* clientes[],nodo* produtos[]){
	int i,c=0,aux,qtd,mes,fil;
	double prec;
	char buffer[MAXBUFF],*prod,np,*cli,*precAux;
	venda info;

	FILE *fp = fopen( "files/Vendas1.txt", "r" );

	for(i=0;i<26;i++)
		vendas[i]=NULL;

	while (fgets(buffer, MAXBUFF,fp)!=NULL){
		prod=strtok(buffer," ");
		aux=prod[0]-65;
		if(search(produtos[aux],prod)!=NULL){
			prec=strtod(strtok(NULL," "),&precAux);
			qtd=atoi(strtok(NULL," "));
			np=strtok(NULL," ")[0];
			cli=strtok(NULL," ");
			aux=cli[0]-65;
			if(search(clientes[aux],cli)!=NULL){
				mes=atoi(strtok(NULL," "));
				fil=atoi(strtok(NULL," "));
				criaVenda(&info,prod,prec,qtd,np,cli,mes,fil);
				aux=prod[0]-65;
				if(vendas[aux]==NULL){
					vendas[aux]=criaNodoV(info,NULL);
				}
				else
					vendas[aux]=insertV(info,vendas[aux]);
				c++;


			}

		}
	}

		 printf("Vendas: %d\n", c);
		fclose(fp);
}
*/


int main(){
	catClientes clientes;
	catProdutos produtos;
	catVendas vendas;
	int aux,c=0,qtd,mes,fil;
	char cod[10], buffer[MAXBUFF],*prod,np,*cli,*precAux;
	double prec;
	info inf;
	FILE *fp;

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
		        else printf("%s\n",cod );

	}
	fclose(fp);
	printf("Clientes: %d\n",c );


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
		        else printf("%s\n",cod );
	}
	fclose(fp);
	printf("Produtos: %d\n",c );
    



	c=0;
	initV(vendas);
    fp = fopen( "files/Vendas1.txt", "r" );
	while (fgets(buffer, MAXBUFF,fp)!=NULL){
		prod=strtok(buffer," ");
		aux=prod[0]-65;
		if(search(produtos[aux],prod)!=NULL){
			prec=strtod(strtok(NULL," "),&precAux);
			qtd=atoi(strtok(NULL," "));
			np=strtok(NULL," ")[0];
			cli=strtok(NULL," ");
			aux=cli[0]-65;
			if(search(clientes[aux],cli)!=NULL){
				mes=atoi(strtok(NULL," "));
				fil=atoi(strtok(NULL," "));
				registaVenda(&inf,prod,prec,qtd,np,cli,mes,fil);
				aux=prod[0]-65;
				if(vendas[aux]==NULL){
					vendas[aux]=criaVenda(inf);
					c++;
				}
				else if (searchV(vendas[aux],inf)==NULL){
						vendas[aux]=insereVenda(inf,vendas[aux]);
						c++;
					 }
				else printf("%s %f %d %c %s %d %d\n",prod,prec,qtd,np,cli,mes,fil );
			}

		}
	}
	fclose(fp);
	printf("Vendas: %d\n", c);

	return 1;
}
