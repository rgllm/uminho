#include "header.h"

/** inicialização da estrutura */
void initLCHR(LCHR lchr)
{
	lchr->sizeVCHR=20000;		
	lchr->nchr=0;
	lchr->vchr=(CHR*)malloc(lchr->sizeVCHR*sizeof(CHR));			

}

/** Realoca mais memória no vetor de chr */
void moreVCHR(LCHR lchr)	
{
	CHR *tmp;
	tmp=realloc(lchr->vchr,2*lchr->sizeVCHR);
	lchr->sizeVCHR*=2;
	
	if (tmp==NULL)
	{ 
		/** Se não for possível realocar retorna a seguinte mensagem de erro */
		printf("ERROR: COULD NOT RE-ALLOCATE MEMORY...\n"); 
	}
	else lchr->vchr=tmp;
}

/** Função que por cada vez que fôr chamada guarda as últimas alterações do tabuleiro */
LCHR backup (TAB *prev, TAB *next, LCHR lchr, int flag)
{
	LCHR aux;
	CHR chr;
	int i,j;
	
	aux=(LCHR)malloc(sizeof(struct lchr));
	initLCHR(aux);

	aux->flag=flag;
	
	/** se flag fôr 0, então estamos na presença de alterações parciais (não foi introduzido novo tabuleiro) */
	if (flag==0)
	{		
		
		for (i=0;i<next->linhas;i++)
			for (j=0;j<next->colunas;j++)
			{
				if (prev->m[i][j]!=next->m[i][j])
				{
					if (aux->nchr > (aux->sizeVCHR-1)) /** se atingido o limite é alocada mais memória */
						moreVCHR(aux);

					chr.c=prev->m[i][j];
					chr.i=i;
					chr.j=j;
					
					aux->vchr[aux->nchr]=chr;
					aux->nchr++;
				}
			}	
	}
	/** por outro lado, se a flag fôr 1, apenas guardamos o tabuleiro */
	else
	{ 
		aux->tab=*prev;
	}

	/** juntamos aux à cabeça de lchr, retornando a nova lista de seguida */ 
	aux->next=lchr;
	return aux;
}
