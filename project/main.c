#include <stdio.h>
#include <string.h>
<<<<<<< HEAD
=======
#include <stdlib.h>
#define MAXBUFF 64
>>>>>>> 6b2b31d70ef5a906a049d6a34e68dc1e996fb0ae

void main(){

	int c=0,j,i,nc,np;
	char buffer[MAXBUFF], clientes[20000][10], produtos[200000][10];
	FILE *fp;

//CLIENTES
	fp = fopen( "files/Clientes.txt", "r" );
	while (fgets(buffer, MAXBUFF,fp)!=NULL) {strcpy(clientes[c],buffer);strtok(clientes[c],"\r\n");c++;}
	printf("%d\n", c);
	fclose(fp);
	nc=c;

//PRODUTOS
	c=0;
	fp = fopen( "files/Produtos.txt", "r" );
	while (fgets(buffer, MAXBUFF,fp)!=NULL) {strcpy(produtos[c],buffer);strtok(produtos[c],"\r\n");c++;}

<<<<<<< HEAD
int nvendas(int nclientes, int nprodutos){

int c=0,i,j;
char buffer[MAXBUFF];
char* s1, *s2;
FILE *fp= fopen( "files/Vendas1.txt", "r" );
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


int main(){
int clientes,produtos,vendas;

clientes=nclientes();
printf("Número de Clientes : %d\n",clientes );
produtos=nprodutos();
printf("Número de Produtos : %d\n",produtos );
vendas=nvendas(clientes, produtos);
printf("Número de Vendas : %d\n",clientes );

return 1;
=======
	printf("%d\n", c);
	fclose(fp);
	np=c;

//VENDAS
	c=0;i=0;
	char* s1, *s2;
	fp = fopen( "files/Vendas1.txt", "r" );
	while (fgets(buffer, MAXBUFF,fp)!=NULL){
			s1=strtok(buffer," ");
			strtok(NULL," ");
			strtok(NULL," ");
			strtok(NULL," ");
			s2=strtok(NULL," ");

			for(i = 0; i < nc; i++)

		   		 if(strcmp(clientes[i],s2)==0)

		     			for(j = 0; j < np; j++)

			 		  		if(strcmp(produtos[j],s1)==0) {c++; break;}

		    }

    printf("%d\n", c);
	fclose(fp);
>>>>>>> 6b2b31d70ef5a906a049d6a34e68dc1e996fb0ae
}
