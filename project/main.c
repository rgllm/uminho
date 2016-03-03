#include <stdio.h>
#include <string.h>
/*
#include "clientes.h"
#include "produtos.h"
#include "vendas.h"
*/
#define MAXBUFF 64

void main()
{
	int c,j,i,nc,np;
	char buffer[MAXBUFF], clientes[20000][10], produtos[200000][10];
	char* aux;
	FILE *fp;

	//CLIENTES
	c=0;
	fp=fopen("files/Clientes.txt","r"); 
	while( fgets (buffer, MAXBUFF, fp)!=NULL ) 
    {
    	strcpy(clientes[c],buffer);
    	strtok(clientes[c],"\r\n");
      	c++;
    }
    printf("Clientes : %d\n",c );
    fclose(fp);

    nc=c;

    // PRODUTOS
    c=0;
    fp=fopen("files/Produtos.txt","r");
    while( fgets (buffer, MAXBUFF, fp)!=NULL ) 
    {
    	strcpy(produtos[c],buffer);
    	strtok(produtos[c],"\r\n");
      	c++;
    }
    printf("Produtos : %d\n",c );
    fclose(fp);
    np=c;

	//VENDAS
	char *s1,*s2;

	c=0;
    fp=fopen("files/Vendas1.txt","r");
    while( fgets(buffer, MAXBUFF, fp)!=NULL ) 
    {
  		s1=strtok(buffer," ");
    	strtok(NULL," ");
    	strtok(NULL," ");
    	strtok(NULL," ");
    	s2=strtok(NULL," ");
    	for(i = 0; i < nc; i++)
		{
		    if(!strcmp(clientes[i],s2))
		    {
		        for(j = 0; j < np; j++)
				{
				    if(!strcmp(produtos[i],s1))
				    {
				        c++;
				    }
				}
		    }
		}
    }
    printf("Vendas : %d \n",c );
    fclose(fp);

}