#include "header.h"

void carregar(FILE *var, TAB *tab)	
{
	int i,j,v;
	char linha[BUFFER], resto[BUFFER];
	 
	/** 
	 * Utilizamos fgets para armazenar a informação inserida no terminal com espaços, sendo posteriormente
	 * "processada" por sscanf de forma a (na estrutura tab) indicar o número de linhas e colunas respetivamente. 
	 */
	if (fgets(linha,BUFFER,var))
	sscanf(linha,"%d %d",&tab->linhas,&tab->colunas); 

	if (fgets(linha,BUFFER,var))	/** Introdução do número de segmentos por linha.*/
	for(i=0;i<tab->linhas;i++){
		sscanf(linha,"%d %[^\n]",&v,resto);	
		tab->somal[i] = v;	/** Será inserido no elemento de índice i no vetor o valor introduzido anteriormente.*/
		strcpy(linha,resto);	/** Irá copiar o resto para linha de forma a ser novamente "processado".*/
	}
	if (fgets(linha,BUFFER,var))
	for(i=0;i<tab->colunas;i++){
		sscanf(linha,"%d %[^\n]",&v,resto);
		tab->somac[i] = v;
		strcpy(linha,resto);
	}
	/** Vai preencher as entradas do tabuleiro consoante a informação dada. */
	for(i=0;i<tab->linhas;i++){
		if (fgets(linha,BUFFER,var))	/** Informação para preencher uma linha do tabuleiro. */
		for(j=0;j<tab->colunas;j++)
		{		
			sscanf(linha,"%c %[^\n]",&tab->m[i][j],resto);	
			strcpy(linha,resto);
		}
	} 
}

void colocar(TAB *tab,char c,int x,int y){
	tab->m[x-1][y-1]=c;
}	

TAB mostrar(TAB x)	/** Tal como sugere, quando invocada mostrar imprime no ecrã o tabuleiro. */
{
	int i,j;
	for (i=0;i<x.linhas;i++)
	{
		for(j=0;j<x.colunas;j++)
			printf("%c",x.m[i][j]); 
			printf(" %d\n",x.somal[i]);	/** No fim de cada linha é impresso o número de segmentos na mesma e avança de linha. */
	}
	for(i=0;i<x.colunas;i++)
	{	
		printf("%d",x.somac[i]);		/** Imprime sequencialmente o número de segmentos por coluna. */
		if (i==x.colunas-1) printf("\n");
	}
	return x;
}

void preencherl(TAB *tab, int x)
{
	int i;
	for(i=0;i<tab->colunas;i++) if(tab->m[x-1][i]=='.')(tab->m[x-1][i]='~');	/** Vai substituir '.' por '~' numa linha. */
}

void preencherc(TAB* tab, int y)
{
int i;
	for(i=0;i<tab->linhas;i++) if(tab->m[i][y-1]=='.')(tab->m[i][y-1]='~');
}

