#include "clientes.h"
#include "produtos.h"

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


carregaClientes(clientes);
carregaProdutos(produtos);

return 1;
}
