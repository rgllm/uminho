#include "header.h"

void initLCHR(LCHR *lchr);
void carregar(FILE *var, TAB *tab);
LCHR backup(TAB* prev, TAB* next, LCHR lchr,int flag);
int isSame(TAB* tab1, TAB* tab2);
TAB mostrar(TAB x);
void colocar(TAB *tab,char c,int x,int y);
void preencherl(TAB *tab, int x);
void preencherc(TAB *tab, int y);
void escrever (FILE *var, TAB *tab);
int validar(TAB tab);
int validarAux (TAB tab);
void E1(TAB *tab);
void E2(TAB* tab);
void E3(TAB* tab);
void resolverTab (TAB *tab);
void criarTab (TAB *tab, int l, int c);

int main()
{
TAB prev, next, aux;
LCHR lchr;
int x,y,linha,coluna;
char c;
FILE *fp=NULL;
char cmd[BUFFER]={'0'};		/* Inicialização da variável. */
char name_f[32];
lchr=(LCHR)malloc(sizeof(struct lchr));
while(cmd[0] != 'q')	/* Até ser inserido 'q' continuará a pedir comandos. */
{
	if(fgets(cmd,BUFFER,stdin))
	
	if (cmd[0] == 'c')		/** A informação da estrutura passará a ser aquela que introduzirmos. */ 
	{
			carregar(stdin,&aux);
			if (isSame(&aux,&next)!=0)
			{
				prev=next;
				next=aux;
				lchr=backup(&prev,&next,lchr,1);
			}		
	}		
	
	if (cmd[0] == 'm') mostrar(next);
	
	if (cmd[0] == 'p')
	{
		sscanf(cmd,"%c %c %d %d",&cmd[0],&c,&x,&y);
		colocar(&aux,c,x,y);
		if (isSame(&aux,&next)!=0)
		{
			prev=next;
			next=aux;
			lchr=backup(&prev,&next,lchr,0); 
		}
	}

	if (cmd[0] == 'D')
	{
		CHR temp;
		int i;

		if (lchr!=NULL)
		{
			if(lchr->flag==0)
			{
				for (i=0; i < lchr->nchr; i++)
				{
					temp=lchr->vchr[i];
					next.m[temp.i][temp.j]=temp.c;
					aux=next;
				}
			}
			else 
				{
					next=lchr->tab;
					aux=lchr->tab;
				}		
			
			lchr=lchr->next;
		}
		else 
		{
			TAB newtab;
			aux=newtab;
			next=newtab;
		}				
	}
	
	if (cmd[0] == 'h')
	{
		sscanf(cmd,"%c %d",&cmd[0],&x);
		preencherl(&aux,x);
		if (isSame(&aux,&next)!=0)
		{
			prev=next;
			next=aux;
			lchr=backup(&prev,&next,lchr,0); 
		}
	}
	
	if (cmd[0] == 'v')
	{
		sscanf(cmd,"%c %d",&cmd[0],&y);
		preencherc(&aux,y);
		if (isSame(&aux,&next)!=0)
		{
			prev=next;
			next=aux;
			lchr=backup(&prev,&next,lchr,0); 
		}
	}
	
	if (cmd[0] == 'l')
	{
		sscanf(cmd,"%c %s",&cmd[0],name_f);
			
			fp = fopen(name_f,"r");
				carregar(fp,&aux);
			fclose(fp);
			
			if (isSame(&aux,&next)!=0)
			{
				prev=next;
				next=aux;
				lchr=backup(&prev,&next,lchr,1);
			}	
	}
	
	if (cmd[0] == 'e')
	{
		sscanf(cmd,"%c %s",&cmd[0],name_f);
		
		fp=fopen(name_f,"w");
			escrever(fp,&next);
		fclose(fp);
	}
	
	if (cmd[0] == 'V')
	{
		if (validar(next)==0) printf("SIM\n");
			else printf("NAO\n");
	}

	if (cmd[0]=='E')
	{
		if (cmd[1]=='1')
			E1(&aux);
		else if (cmd[1]=='2')
			E2(&aux);
		else if (cmd[1]=='3')
			E3(&aux);
		
		if (isSame(&aux,&next)!=0)
		{
			prev=next;
			next=aux;
			lchr=backup(&prev,&next,lchr,0); 
		}
	}
	
	if (cmd[0]=='R')
	{	
		resolverTab(&aux);
		if (isSame(&aux,&next)!=0)
		{
			prev=next;
			next=aux;
			lchr=backup(&prev,&next,lchr,0); 
		}
	}
	
	if (cmd[0]=='G')
	{
		sscanf(cmd,"%c %d %d",&cmd[0],&linha,&coluna);
		criarTab(&aux,linha,coluna);
	
		if (isSame(&aux,&next)!=0)
		{
			prev=next;
			next=aux;
			lchr=backup(&prev,&next,lchr,1);
		}
	}
}
return 0;
}
