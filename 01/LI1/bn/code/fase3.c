#include "header.h"

void E1(TAB* tab);
void E2(TAB* tab);
void E3(TAB* tab);
int isSame(TAB* tab1, TAB* tab2);

int randomGen(int min,int max){
	int x;
	srand ( time(NULL) );
	x=(rand() %(max-min))+min;
	return x;
}

void submarines (TAB *tab,int l, int c){ /**Função que coloca os submarinos */
	int x,y;
	x = randomGen(0,l);
	y = randomGen(0,c);
	if (tab->m[x][y]=='.'){
		tab->m[x][y]='O';
		tab->m[x+1][y]='~';
		tab->m[x][y+1]='~';
		tab->m[x-1][y]='~';
		tab->m[x][y-1]='~';
		tab->somal[x]++;
		tab->somac[y]++;
	}
	else submarines (tab,l,c);
}

void destroyers (TAB *tab,int l,int c){ /**Função que coloca os destroyers */
	int o,x,y;
	o = randomGen(0,100);
	x = randomGen(0,l);
	y = randomGen(0,c);
	if (o%2){ /*barco horizontal */
		if (tab->m[x][y]=='.' && tab->m[x][y+1]=='.'){
			tab->m[x][y]='<';
			tab->m[x][y+1]='>';
			tab->m[x+1][y]='~';
			tab->m[x-1][y]='~';
			tab->m[x][y-1]='~';
			tab->m[x][y+2]='~';
			tab->m[x+1][y+1]='~';
			tab->m[x-1][y+1]='~';
			tab->somal[x]++;
			tab->somac[y]++;
			tab->somal[x]++;
		  tab->somac[y+1]++;
		}
		else destroyers (tab,l,c);
	}
	else{
		if (tab->m[x][y]=='.' && tab->m[x+1][y]=='.'){
			tab->m[x][y]='<';
			tab->m[x][y+1]='>';
			tab->m[x+1][y]='~';
			tab->m[x-1][y]='~';
			tab->m[x][y-1]='~';
			tab->m[x][y+2]='~';
			tab->m[x+1][y+1]='~';
			tab->m[x-1][y+1]='~';
			tab->somal[x]++;
			tab->somac[y]++;
			tab->somal[x]++;
		  tab->somac[y+1]++;
		}
		else destroyers (tab,l,c);
	}
}

void cruisers(TAB *tab, int l, int c,int t){ /**Função que coloca todos os restantes barcos */
	int o,x,y,seg,card;
	seg=t-2;
	o=randomGen(0,c);
	x = randomGen(0,l);
	y = randomGen(0,c);
		if (o%2){ /*barco horizontal */
		if (tab->m[x][y]=='.' && tab->m[x][y+1]=='.'){
			tab->m[x][y]='<';
			for(card=0;seg>0 && card<seg;seg--,card++){tab->m[x][y+card]='#';}
			tab->m[x][y+card]='>';
		}
		else cruisers (tab,l,c,t);
	}
	else{ /*barco vertical */
		if (tab->m[x][y]=='.' && tab->m[x+1][y]=='.'){
			tab->m[x][y]='^';
			for(card=0;seg>0 && card<seg;seg--,card++){tab->m[x+card][y]='#';}
			tab->m[x+card][y]='v';
		}
		else cruisers (tab,l,c,t);
	}

}


void criarTab (TAB *tab, int l, int c){ /**Função que cria um tabuleiro, correspondente ao comando G */
	int *t,*q,i,j,n,n2;
	n=(l*c)*0.04;
	n2=n;
	printf("%d\n",n);
	t=(int*)malloc((n-1)*sizeof(int));
	q=(int*)malloc((n-1)*sizeof(int));
	tab->linhas=l;
	tab->colunas=c;
	for(i=0;i<l;i++) tab->somal[i]=0;
	for(j=0;j<c;j++) tab->somac[j]=0;
	for(i=0;i<l;i++){for(j=0;j<c;j++) tab->m[i][j]='.';}
	for(i=0;n2>0;n2--,i++){q[i]=n2;} /*Preencher o array de n até 1, correspondente ao número de barcos (quantidade). */
	n2=1;
	for(i=0;n>=n2;n2++,i++){t[i]=n2;} /*Preencher o array de 1 até n, correspondente ao tamanho dos barcos. */
	for(i=0;i<n;i++)
	{
		for (j=0;j<q[i];j++)
		{
			if (t[i]==1) (submarines (tab,l,c));
			else if (t[i]==2) (destroyers (tab,l,c));
			else cruisers(tab,l,c,t[i]);

		}
	}
}


int isFinished(TAB *tab){ /**Função que verifica se um tabuleiro já está terminado/completo */
	int i,j;
	for(i=0;i<tab->linhas;i++){
		for(j=0;i<tab->colunas;j++){
			if (tab->m[i][j]=='o' || tab->m[i][j]=='.') return 1; 
		}
	}
return 0;
}

void resolverTab (TAB *tab){ /**Função que resolve o tabuleiro apenas aplicado as estratégias E1,E2 e E3. */
	TAB ant;
do{
	ant=*tab;
	E1(tab);
	E2(tab);
	E3(tab);
}
while(isFinished(tab)==1 && isSame(&ant,tab)==1);
}
