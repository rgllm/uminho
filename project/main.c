#include <stdio.h>
#include <string.h>
#include "avlLib/avl.h"

#define MAXBUFF 128

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



int nvendas(int clientes, int produtos){

int n=0,i,j;
char buffer[MAXBUFF];
char* aux;
char *s1,*s2;
FILE *fp=fopen("files/Vendas1.txt","r");

while( fgets(buffer, MAXBUFF, fp)){
	s1=strtok(buffer," ");
	strtok(NULL," ");
	strtok(NULL," ");
	strtok(NULL," ");
	s2=strtok(NULL," ");
for(i = 0; i < clientes; i++){
	if(!strcmp(aclientes[i],s2)){
		for(j = 0; j <produtos; j++){
			if(!strcmp(aprodutos[i],s1)) n++;
		}
	}
}
}

fclose(fp);
return n;
}


void carregaClientes(struct avl_table * clientes[]){
	int i=0;
	char aux[10];
	char buffer[MAXBUFF];
	FILE *fp=fopen("files/Clientes.txt","r");

	for(i=0;i<26;i++){
		clientes[i]=avl_create(strcmp,NULL,&avl_allocator_default);
	}
	while( fgets (buffer, MAXBUFF, fp)){
			strcpy(aux,buffer);
			strtok(aux,"\r\n");
		   	avl_t_insert (clientes[(aux[0]-65)], aux);
		}

	fclose(fp);

}


int main(){
int clientes,produtos,vendas;

struct avl_table aClientes[26];
carregaClientes(&aClientes);

clientes=nclientes();
printf("Número de Clientes : %d\n",clientes );
produtos=nprodutos();
printf("Número de Produtos : %d\n",produtos );
vendas=nvendas(clientes, produtos);
printf("Número de Vendas : %d\n",clientes );

return 1;
}
