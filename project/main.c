#include <stdio.h>
#include <string.h>
#include "avl.h"



#define MAXBUFF 128
/*
char aclientes[20000][10], aprodutos[200000][10];

int nclientes(){

char buffer[MAXBUFF];
int n=0;
FILE *fp=fopen("files/Clientes.txt","r");

while( fgets (buffer, MAXBUFF, fp)){
		strcpy(aclientes[n],buffer);
		strtok(aclientes[n],"\r\n");
	   n++;
	}

fclose(fp);
return n;
}

int nprodutos(){

char buffer[MAXBUFF];
int n=0;
FILE *fp=fopen("files/Produtos.txt","r");

while( fgets (buffer, MAXBUFF, fp)){
	strcpy(aprodutos[n],buffer);
	strtok(aprodutos[n],"\r\n");
	n++;
}
fclose(fp);
return n;
}



int nvendas(int nclientes, int nprodutos){

int c=0,i,j;
char* s1, *s2;
char buffer[MAXBUFF];
FILE *fp = fopen( "files/Vendas1.txt", "r" );
while (fgets(buffer, MAXBUFF,fp)!=NULL){
			s1=strtok(buffer," ");
			strtok(NULL," ");
			strtok(NULL," ");
			strtok(NULL," ");
			s2=strtok(NULL," ");

			for(i = 0; i < nclientes; i++)

					 if(strcmp(aclientes[i],s2)==0)

						for(j = 0; j < nprodutos; j++)

							if(strcmp(aprodutos[j],s1)==0) {c++; break;}

			 }

	 printf("%d\n", c);
	fclose(fp);
	}


*/
void carregaClientes(nodo* clientes[]){
	char buffer[MAXBUFF],cod[8],aux;
	int i=0,c=0;
	FILE *fp=fopen("files/Clientes.txt","r");
	for(i=0;i<26;i++) 
		clientes[i]=NULL;

	while( fgets (buffer, MAXBUFF, fp)){
			strcpy(cod,buffer);
			strtok(cod,"\r\n");
			aux=cod[0]-65;
			if(clientes[aux]==NULL)
				clientes[aux]=criaNodo(cod,NULL);
			else
				clientes[aux]=insert(cod,clientes[aux]);
			c++;
		}
	printf("Clientes: %d\n",c );
	fclose(fp);
}

void carregaProdutos(nodo* produtos[]){
	char buffer[MAXBUFF],cod[8],aux;
	int i=0,c=0;
	FILE *fp=fopen("files/Produtos.txt","r");
	for(i=0;i<26;i++) 
		produtos[i]=NULL;

	while( fgets (buffer, MAXBUFF, fp)){
			strcpy(cod,buffer);
			strtok(cod,"\r\n");
			aux=cod[0]-65;
			if(produtos[aux]==NULL)
				produtos[aux]=criaNodo(cod,NULL);
			else
				produtos[aux]=insert(cod,produtos[aux]);
			c++;
		}
	printf("Produtos: %d\n",c );
	fclose(fp);
}




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




int main(){
int clientes,produtos,vendas;
nodo * aClientes[26], * aProdutos[26];
nodoV * aVendas[26];



//clientes=nclientes();
//printf("Número de Clientes : %d\n",clientes );
//produtos=nprodutos();
//printf("Número de Produtos : %d\n",produtos );
//vendas=nvendas(clientes, produtos);
//printf("Número de Vendas : %d\n",clientes );
carregaClientes(aClientes);
carregaProdutos(aProdutos);
//if(search(aProdutos['X'-65],"X4054"))printf("sdfasdfasdfasdf\n");
/*
int i;
for(i=0;i<26;i++)
	printf("clientes:%d\n",altura(aClientes[i]) );

for(i=0;i<26;i++)
	printf("produtos:%d\n",altura(aProdutos[i]) );
*/
carregaVendas(aVendas,aClientes,aProdutos);
//print_tree(aClientes[24],0);
return 1;
}
