#include "header.h"

void escrever (FILE *var, TAB *tab) /** Função que escreve num ficheiro o tabuleiro atual */
{
	
	int i,j;
	
	fprintf(var,"%d %d", tab->linhas,tab->colunas);
	fprintf(var,"\n");
	
	for(i=0;i<tab->linhas-1;i++){
		fprintf(var, "%d ", tab->somal[i]);}
		fprintf(var,"%d",tab->somal[i]);
		fprintf(var,"\n");
	
	for(j=0;j<tab->colunas-1;j++){
		fprintf(var, "%d ", tab->somac[j]);}
		fprintf(var,"%d",tab->somac[j]);
		fprintf(var,"\n");
	
	for (i=0;i<tab->linhas;i++)
	{
		for(j=0;j<tab->colunas;j++)
			fprintf(var,"%c",tab->m[i][j]); 
		fprintf(var,"\n");
	}
}

void completal(TAB *tab, int x) /** que substitui '.' por '~' numa linha. */
{
	int i;
	for(i=0;i<tab->colunas;i++) if(tab->m[x-1][i]=='.')(tab->m[x-1][i]='~');
}

void completac(TAB* tab, int y) /** que substitui '.' por '~' numa coluna. */
{
int i;
	for(i=0;i<tab->linhas;i++) if(tab->m[i][y-1]=='.')(tab->m[i][y-1]='~');
}

int pertence (char x){ /** Função que verifica se numa determinada posição x,y está alguma parte de um barco/submarino */
		int i;
		char navios[7]={'O','<','>','#','o','^','v'};
		for (i=0;i<7;i++){
			if (x==navios[i]) return 1;
		}
	return 0;
	}

int isSame(TAB* tab1, TAB* tab2) /** Compara dois tabuleiros para saber se são iguais, 1 corresponde a diferentes e 0 a iguais */
{
int i, j, r=0;
	
if ((tab1->linhas!=tab2->linhas)||(tab1->colunas!=tab2->colunas))
	r=1;
	
else{
	for(i=0;i<tab1->linhas;i++)
		for(j=0;j<tab1->colunas;j++)
			if (tab1->m[i][j]!=tab2->m[i][j])
			{
				r=1;
				break;
			}
	for(i=0;i<tab1->linhas;i++)
	{
		if (tab1->somal[i]!=tab2->somal[i])
		{
			r=1;
			break;
		}
	} 

	for(j=0;j<tab1->colunas;j++)
	{
		if (tab1->somac[j]!=tab2->somac[j])
		{
			r=1;
			break;
		}
	} 

}
return r;
}

int contaL (TAB *tab, int l){ /**Conta as celulas diferentes de '~' numa linha*/
	int i,count=0;
	for(i=0;i<tab->colunas;i++)
		if(tab->m[l][i]!='~')count++;
	return count; 
}

int contaC (TAB *tab, int c){ /**Conta as celulas diferentes de '~' numa coluna*/
	int i,count=0;
	for(i=0;i<tab->linhas;i++)
		if(tab->m[i][c]!='~')count++;
	return count; 
}

int partesL (TAB *tab,int l){ /**Conta o máximo de navios numa determinada linha*/
	int i,count=0,aux=1;
	for(i=0;i<tab->colunas;i++)
		if(tab->m[l][i]!='~' && aux==1){count++;aux=0;}
		else if(tab->m[l][i]=='~') aux=1;
	return count;
}

int partesC (TAB *tab,int c){ /**Conta o máximo de navios numa determinada coluna*/
	int i,count=0,aux=1;
	for(i=0;i<tab->linhas;i++)
		if(tab->m[i][c]!='~' && aux==1){count++;aux=0;}
		else if(tab->m[i][c]=='~') aux=1;
	return count;
}

int faltamL (TAB *tab, int l){ /**Conta quantas peças de barco faltam descobrir numa determinada linha */
	int i,count=0;
	for(i=0;i<tab->colunas;i++)
		if(tab->m[l][i]!='~' && tab->m[l][i]!='.') count++;
	return (tab->somal[l]-count);
}

int faltamC (TAB *tab, int c){ /**Conta quantas peças de barco faltam descobrir numa determinada coluna */
	int i,count=0;
	for(i=0;i<tab->linhas;i++)
		if(tab->m[i][c]!='~' && tab->m[i][c]!='.') count++;
	return (tab->somac[c]-count);
}

void E1(TAB *tab){ /**Função principal da estratégia 1 que coloca água à volta de todos os navios que já estão no tabuleiro */
	int i,j,c;
	for (c=0;c<2;c++){
	/* percorre o tabuleiro todo, caratér a caratér */
	for(i=0;i<tab->linhas;i++)
		for(j=0;j<tab->colunas;j++)
		{
			/* no caso do submarino rodeamos todos os lados */
			if (tab->m[i][j]=='O')		
			{
				tab->m[i+1][j]='~'; tab->m[i-1][j]='~'; tab->m[i][j+1]='~'; tab->m[i][j-1]='~';
				tab->m[i+1][j+1]='~'; tab->m[i-1][j+1]='~'; tab->m[i+1][j-1]='~'; tab->m[i-1][j-1]='~';
			}
			
			/* se for segmento do corpo de um barco ou indefinido preenchemos as diagonais com água */
			else if (tab->m[i][j]=='#' || tab->m[i][j]=='o')	
			{
				tab->m[i+1][j+1]='~'; tab->m[i-1][j+1]='~'; tab->m[i+1][j-1]='~'; tab->m[i-1][j-1]='~';
				if (tab->m[i][j]=='#')
				{
					if (tab->m[i][j+1]=='~') tab->m[i][j-1]='~';
					else if (tab->m[i][j-1]=='~') tab->m[i][j+1]='~';
					if (tab->m[i+1][j]=='~') tab->m[i-1][j]='~';
					else if (tab->m[i-1][j]=='~') tab->m[i+1][j]='~';
				}
			}
			
			/* para os segmentos do inicio ou fim de um navio, apenas não colocamos àgua na parte onde existe continuação do mesmo */
			else if (tab->m[i][j]=='^'){	
				tab->m[i-1][j]='~'; tab->m[i][j+1]='~'; tab->m[i][j-1]='~';
				tab->m[i+1][j+1]='~'; tab->m[i-1][j+1]='~'; tab->m[i+1][j-1]='~'; tab->m[i-1][j-1]='~';
			}
			else if (tab->m[i][j]=='v'){
				tab->m[i+1][j]='~'; tab->m[i][j+1]='~'; tab->m[i][j-1]='~';
				tab->m[i+1][j+1]='~'; tab->m[i-1][j+1]='~'; tab->m[i+1][j-1]='~'; tab->m[i-1][j-1]='~';
			}
			else if (tab->m[i][j]=='>'){
				tab->m[i+1][j]='~'; tab->m[i-1][j]='~'; tab->m[i][j+1]='~'; 
				tab->m[i+1][j+1]='~'; tab->m[i-1][j+1]='~'; tab->m[i+1][j-1]='~'; tab->m[i-1][j-1]='~';
			}
			else if (tab->m[i][j]=='<'){
				tab->m[i+1][j]='~'; tab->m[i-1][j]='~'; tab->m[i][j-1]='~';
				tab->m[i+1][j+1]='~'; tab->m[i-1][j+1]='~'; tab->m[i+1][j-1]='~'; tab->m[i-1][j-1]='~';
			}
		}
}
}

void E2(TAB* tab) /**Função principal da estratégia 2 que coloca água nas linhas e colunas em que todos os segmentos de barcos já foram colocados */
{
	int i,j,r;		/* r é o contador de segmentos de barcos */
	for(i=0;i<tab->linhas;i++){		/* percorre toda a linha */
		r=0;	
		for(j=0;j<tab->colunas;j++){
			if(tab->m[i][j]!='.'&& tab->m[i][j]!='~') r++;	
		}
	if(r==tab->somal[i]) completal(tab, i+1);	/* no caso de estarem todos os segmentos já posicionados podemos preencher as restantes células com àgua */
	}

	/* Percorre agora o tabuleiro coluna a coluna e efectua o mesmo procedimento. */
	for(j=0;j<tab->colunas;j++){
		r=0;
		for(i=0;i<tab->linhas;i++){
			if(tab->m[i][j]!='.'&&tab->m[i][j]!='~') r++;
		}
	if(r==tab->somac[j]) completac(tab, j+1);
	}
}
int isSurrounded (TAB *tab, int l, int c)
{	
	if(l==0 && c==0){
		if(tab->m[l][c+1]=='~' && tab->m[l+1][c]=='~') return 1;
		else return 0;
	}
	else if(l==0 && c==tab->colunas-1){
		if(tab->m[l][c-1]=='~' && tab->m[l+1][c]=='~') return 1;
		else return 0;
	}
	else if(l==tab->linhas-1 && c==0){
		if(tab->m[l][c+1]=='~' && tab->m[l-1][c]=='~') return 1;
		else return 0;
	}
	else if(l==tab->linhas-1 && c==tab->colunas-1){
		if(tab->m[l][c-1]=='~' && tab->m[l-1][c]=='~') return 1;
		else return 0;
	}
	else if(c==0){
		if(tab->m[l-1][c]=='~' && tab->m[l][c+1]=='~' && tab->m[l+1][c]=='~') return 1;
		else return 0;
	}
	else if(l==0){
		if(tab->m[l][c-1]=='~' && tab->m[l][c+1]=='~' && tab->m[l+1][c]=='~') return 1;
		else return 0;
	}
	else if(l==tab->linhas-1){
		if(tab->m[l][c-1]=='~' && tab->m[l][c+1]=='~' && tab->m[l-1][c]=='~') return 1;
		else return 0;
	}
	else if(c==tab->colunas-1){
		if(tab->m[l-1][c]=='~' && tab->m[l][c-1]=='~' && tab->m[l+1][c]=='~') return 1;
		else return 0;
	}
	else if(tab->m[l+1][c]=='~' && tab->m[l-1][c]=='~' && tab->m[l][c+1]=='~' && tab->m[l][c-1]=='~') return 1;
	
	else return 0;
}

/* Verifica se a linha já está preparada para a colocação da frota - todas as células de àgua colocadas */
int toShipH (TAB *tab, int l){

	int s=0, j, r=1;
	for (j=0;j<tab->colunas;j++)
		if (tab->m[l][j]!='~') s++;
	
	if (tab->somal[l]==s) r=0;
	return r;
}

int toShipV (TAB *tab, int c){

	int s=0, i, r=1;
	for (i=0;i<tab->linhas;i++)
		if (tab->m[i][c]!='~') s++;
	
	if (tab->somac[c]==s) r=0;
	return r;
}

/* Vai preencher a linha célula a célula */
void auxE3H(TAB *tab,int l){
	int j;
	for(j=0;j < tab->colunas;j++)
	{
		if (tab->m[l][j]!='~'){
			if (tab->m[l][j+1]=='~' || j==tab->colunas-1) { if (tab->m[l][j]=='.') tab->m[l][j]='o';}	/* No caso do próximo carater ser '~' coloca segmento indefinido */
			else{
				tab->m[l][j++]='<';
				while(tab->m[l][j+1]!='~' && j < tab->colunas-1){
					tab->m[l][j]='#';
					j++;
				}
				tab->m[l][j]='>';
			}
		}
	}
}

void auxE3V(TAB *tab,int c){
	int i;
	for(i=0;i < tab->linhas;i++)
	{
		if (tab->m[i][c]!='~'){
			if (tab->m[i+1][c]=='~' || i==tab->linhas-1) { if (tab->m[i][c]=='.') tab->m[i][c]='o';}
			else{
				tab->m[i++][c]='^';
				while(tab->m[i+1][c]!='~' && i < tab->linhas-1){
					tab->m[i][c]='#';
					i++;
				}
				tab->m[i][c]='v';
			}
		}
	}
}

/** Verifica se o navio é horizontal e está pronto para ser completado */
int readyToShipH(TAB *tab, int l, int c)
{
	if (tab->m[l][c+1]=='~'||tab->m[l][c+1]=='.'||c==tab->colunas-1) return 1;
	else
	{
		for(;tab->m[l][c]!='.' && tab->m[l][c]!='~' && c<tab->colunas;c++);		/** Se depois de concluir o ciclo */
			if (tab->m[l][c]=='~' || c==tab->colunas-1) return 0;				/** tiver terminado em àgua ou na ultima coluna retornar verdadeiro */
				else return 1;
	}
}

int readyToShipV(TAB *tab, int l, int c)
{
	if (tab->m[l+1][c]=='~' || tab->m[l+1][c]=='.' || l==tab->linhas-1) return 1;
	else
	{
		for(;tab->m[l][c]!='.' && tab->m[l][c]!='~' && l<tab->linhas;l++);
			if (tab->m[l][c]=='~' || l==tab->linhas-1) return 0;
				else return 1;
	}
}


/** esta função auxiliar apenas será corrida quando o caratér for '#' */
void auxE3Card(TAB *tab,int l, int c)
{
	if (tab->m[l+1][c]=='~'||tab->m[l-1][c]=='~')		/** Se em cima ou em baixo do '#' tiver àgua o tabuleiro é horizontal */  
	{
		if (tab->m[l][c-1]=='.') tab->m[l][c-1]='o';	/** Preenchemos a esquerda e direita do '#' com 'o' */	
		if (tab->m[l][c+1]=='.') tab->m[l][c+1]='o';
	}
	else if (tab->m[l][c+1]=='~'||tab->m[l][c-1]=='~')
	{
		if (tab->m[l-1][c]=='.') tab->m[l-1][c]='o';
		if (tab->m[l+1][c]=='.') tab->m[l+1][c]='o';	
	} 
	else if (c==0||c==tab->colunas-1)
	{
		if (tab->m[l-1][c]=='.') tab->m[l-1][c]='o';
		if (tab->m[l+1][c]=='.') tab->m[l+1][c]='o';
	}
	else if (l==0||l==tab->linhas-1)
	{
		if (tab->m[l][c-1]=='.') tab->m[l][c-1]='o';
		if (tab->m[l][c+1]=='.') tab->m[l][c+1]='o';
	}	

}

void E3(TAB* tab)
{
	int i,j;
	for(i=0;i<tab->linhas;i++)	/* Vai colocar navios em linhas onde todos os carateres '~' já estiverem colocados: */
		if (toShipH(tab,i)==0) 	/* verifica se é possível, */
			auxE3H(tab,i);		/* efectua o procedimento. */
	
	for(j=0;j<tab->colunas;j++)
		if (toShipV(tab,j)==0)
			auxE3V(tab,j);				

	for (i=0;i<tab->linhas;i++)
		for (j=0;j<tab->colunas;j++)
			if (tab->m[i][j]!='~' && isSurrounded(tab,i,j)==1 && tab->somac[j]>0 && tab->somal[i]>0 && (toShipH(tab,i)==0 || toShipV(tab,j)==0)) tab->m[i][j]='O';	

	for (i=0;i<tab->linhas;i++)
		for (j=0;j<tab->colunas;j++)
		{
			if (tab->m[i][j]=='#') auxE3Card(tab,i,j);
			else if (tab->m[i][j]=='<' && tab->m[i][j+1]=='.') tab->m[i][j+1]='o';
			else if (tab->m[i][j]=='>' && tab->m[i][j-1]=='.') tab->m[i][j-1]='o';
			else if (tab->m[i][j]=='^' && tab->m[i+1][j]=='.') tab->m[i+1][j]='o';
			else if (tab->m[i][j]=='v' && tab->m[i-1][j]=='.') tab->m[i-1][j]='o';
		}
		
	for (i=0;i<tab->linhas;i++)
		for (j=0;j<tab->colunas;j++)
			if (tab->m[i][j]=='o')
			{
				if(readyToShipH(tab,i,j)==0)
				{
					if (tab->m[i][j-1]=='~'){
						tab->m[i][j]='<';
					}
					j++;
					while(tab->m[i][j+1]!='~' && j!=tab->colunas-1)
					{
						tab->m[i][j]='#';
						j++;
					}
					tab->m[i][j]='>';
				}
			}

for (j=0;j<tab->colunas;j++)
	for (i=0;i<tab->linhas;i++)
	if (tab->m[i][j]=='o'){
		if (readyToShipV(tab,i,j)==0)
		{
			if (tab->m[i-1][j]=='~'){
				tab->m[i][j]='^';
			}
			i++;
				while(tab->m[i+1][j]!='~' && i!=tab->linhas-1)
				{
					tab->m[i][j]='#';
					i++;
				}
				tab->m[i][j]='v';
		} 	
	}
}

int validarAux (TAB tab) /** Função de validação do tabuleiro com várias etapas */
{
	int min,max,l,c;
	for (l=0;l<tab.linhas;l++) /*verificação se não há navios impossíveis na primeira e ultima coluna.*/
	{
		if((tab.m[l][0])=='>') return 1;
		if(tab.m[l][tab.colunas-1]=='<') return 1;
	}
	for (c=0;c<tab.colunas;c++) /*verificação se não há navios impossíveis na primeira e última coluna.*/
	{
		if (tab.m[0][c]=='v') return 1;
		if (tab.m[tab.linhas-1][c]=='^') return 1;
	}
	for (l=0;l<tab.linhas;l++) /*verificação se as somas dos navios das linhas são possíveis no tabuleiro atual */
	{
		min=0;
		max=0;
		for (c=0;c<tab.colunas;c++)
		{
			if ((pertence(tab.m[l][c]))==1){min++;max++;}
			if (tab.m[l][c] == '.') (max++);
		}
		if ((tab.somal[l]>max) || (tab.somal[l]<min)) return 1;

	}
	for (c=0;c<tab.colunas;c++) /*verificação se as somas dos navios das colunas são possíveis no tabuleiro atual */
	{
		min=0;
		max=0;
		for (l=0;l<tab.linhas;l++)
		{
			if ((pertence(tab.m[l][c]))==1){min++;max++;}
			if (tab.m[l][c] == '.') (max++);
		}
		if ((tab.somac[c]>max) || (tab.somac[c]<min)) return 1;

	}
	for (l=0;l<tab.linhas;l++) /*verificação se não há navios a tocarem-se*/
	{
		for (c=0;c<tab.colunas;c++)
		{
			if (tab.m[l][c]=='O') /* caso em que [l][c] esteja um submarino */
			{
				if (c+1<tab.colunas) {if (tab.m[l][c+1]!='~' && tab.m[l][c+1]!='.') return 1;};
				if ((l+1)<tab.linhas){if (tab.m[l+1][c]!='~' && tab.m[l+1][c]!='.') return 1;};
				if ((c+1)<tab.colunas && (l+1)<tab.linhas){if (tab.m[l+1][c+1]!='~' && tab.m[l+1][c+1]!='.') return 1;};
				if ((l+1)<tab.linhas && (c-1)>=0){if (tab.m[l+1][c-1]!='~' && tab.m[l+1][c-1]!='.') return 1;};
				if ((l-1)>=0 && (c-1)>=0){if (tab.m[l-1][c-1]!='~' && tab.m[l-1][c-1]!='.') return 1;};
				if ((l-1)>=0 && (c+1)<tab.colunas){if (tab.m[l-1][c+1]!='~' && tab.m[l-1][c+1]!='.') return 1;};
			}
			if (tab.m[l][c]=='>') /* caso em que [l][c] esteja o fim de um barco horizontal */
			{
				if ((c+1)<tab.colunas) {if (tab.m[l][c+1]!='~' && tab.m[l][c+1]!='.') return 1;};
				if ((l+1)<tab.linhas){if (tab.m[l+1][c]!='~' && tab.m[l+1][c]!='.') return 1;};
				if (l-1>=0) {if (tab.m[l-1][c]!='~' && tab.m[l-1][c]!='.') return 1;};
				if (c-1>=0) {if (tab.m[l][c-1]!='#' && tab.m[l][c-1]!='<') return 1;};
				if (l+1<tab.linhas && c+1<tab.colunas) {if (tab.m[l+1][c+1]!='~' && tab.m[l+1][c+1]!='.') return 1;};
				if (l+1<tab.linhas && c-1>=0) {if (tab.m[l+1][c-1]!='~' && tab.m[l+1][c-1]!='.') return 1;};
				if (l-1>=0 && c-1>=0) {if (tab.m[l-1][c-1]!='~' && tab.m[l-1][c-1]!='.') return 1;};
				if (l-1>=0 && c+1<tab.colunas) {if (tab.m[l-1][c+1]!='~' && tab.m[l-1][c+1]!='.') return 1;};
			}
			if (tab.m[l][c]=='#') /* caso em que [l][c] esteja o meio de um barco */
			{
				if ((c+1)<tab.colunas) {if ((tab.m[l][c+1]!='#') && (tab.m[l][c+1]!='>') && (tab.m[l][c+1]!='.') && (tab.m[l][c+1]!='o') && (tab.m[l][c+1]!='~')) return 1;};
				if ((l+1)<tab.linhas) {if ((tab.m[l+1][c]!='#') && (tab.m[l+1][c]!='v') && (tab.m[l+1][c]!='.') && (tab.m[l+1][c]!='o') && (tab.m[l+1][c]!='~')) return 1;};
				if ((l+1)<tab.linhas && (c+1)<tab.colunas) {if (tab.m[l+1][c+1]!='~' && tab.m[l+1][c+1]!='.') return 1;};
				if ((l+1)<tab.linhas && (c-1)>=0) {if (tab.m[l+1][c-1]!='~' && tab.m[l+1][c-1]!='.') return 1;};
				if ((l-1)>=0 && (c-1)>=0) {if (tab.m[l-1][c-1]!='~' && tab.m[l-1][c-1]!='.') return 1;};
				if ((l-1)>=0 && (c+1)<tab.colunas) {if (tab.m[l-1][c+1]!='~' && tab.m[l-1][c+1]!='.') return 1;};
			}
			if (tab.m[l][c]=='<') /* caso em que [l][c] esteja o inicio de um barco horizontal*/
			{
				if (c+1<tab.colunas) {if ((tab.m[l][c+1]!='>') && (tab.m[l][c+1]!='#') && (tab.m[l][c+1]!='.') && (tab.m[l][c+1]!='o')) return 1;};
				if (l+1<tab.linhas) {if ((tab.m[l+1][c]!='~') && (tab.m[l+1][c]!='.')) return 1;};
				if (c-1>=0) {if ((tab.m[l][c-1]!='~') && (tab.m[l][c-1]!='.')) return 1;};
				if (l-1>=0) {if ((tab.m[l-1][c]!='~') && (tab.m[l-1][c]!='.')) return 1;};
				if (l+1<tab.linhas && c+1<tab.colunas) {if (tab.m[l+1][c+1]!='~' && tab.m[l+1][c+1]!='.') return 1;};
				if (l+1<tab.linhas && c-1>=0) {if (tab.m[l+1][c-1]!='~' && tab.m[l+1][c-1]!='.') return 1;};
				if (l-1>=0 && c-1>=0) {if (tab.m[l-1][c-1]!='~' && tab.m[l-1][c-1]!='.') return 1;};
				if (l-1>=0 && c+1<tab.colunas) {if (tab.m[l-1][c+1]!='~' && tab.m[l-1][c+1]!='.') return 1;};
			}
			if (tab.m[l][c]=='^') /* caso em que [l][c] esteja o inicio de um barco vertical */
			{
				if (c+1<tab.colunas) {if ((tab.m[l][c+1]!='.') && (tab.m[l][c+1]!='~')) return 1;};
				if (l+1<tab.linhas) {if ((tab.m[l+1][c]!='#') && (tab.m[l+1][c]!='v') && (tab.m[l+1][c]!='o')) return 1;};
				if (c-1>=0) {if ((tab.m[l][c-1]!='~') && (tab.m[l][c-1]!='.')) return 1;};
				if (l-1>=0) {if ((tab.m[l-1][c]!='~') && (tab.m[l-1][c]!='.')) return 1;};
				if (l+1<tab.linhas && c+1<tab.colunas) {if (tab.m[l+1][c+1]!='~' && tab.m[l+1][c+1]!='.') return 1;};
				if (l+1<tab.linhas && c-1>=0) {if (tab.m[l+1][c-1]!='~' && tab.m[l+1][c-1]!='.') return 1;};
				if (l-1>=0 && c-1>=0) {if (tab.m[l-1][c-1]!='~' && tab.m[l-1][c-1]!='.') return 1;};
				if (l-1>=0 && c+1<tab.colunas) {if (tab.m[l-1][c+1]!='~' && tab.m[l-1][c+1]!='.') return 1;};
			}
			if (tab.m[l][c]=='v') /* caso em que [l][c] esteja o inicio de um barco horizontal */
			{
				if (c+1<tab.colunas) {if ((tab.m[l][c+1]!='.') && (tab.m[l][c+1]!='~')) return 1;};
				if (l+1<tab.linhas) {if ((tab.m[l+1][c]!='.') && (tab.m[l+1][c]!='~')) return 1;};
				if (c-1>=0) {if ((tab.m[l][c-1]!='~') && (tab.m[l][c-1]!='.')) return 1;};
				if (l-1>=0) {if ((tab.m[l-1][c]!='#') && (tab.m[l-1][c]!='^') && (tab.m[l-1][c]!='o')) return 1;};
				if (l+1<tab.linhas && c+1<tab.colunas) {if (tab.m[l+1][c+1]!='~' && tab.m[l+1][c+1]!='.') return 1;};
				if (l+1<tab.linhas && c-1>=0) {if (tab.m[l+1][c-1]!='~' && tab.m[l+1][c-1]!='.') return 1;};
				if (l-1>=0 && c-1>=0) {if (tab.m[l-1][c-1]!='~' && tab.m[l-1][c-1]!='.') return 1;};
				if (l-1>=0 && c+1<tab.colunas) {if (tab.m[l-1][c+1]!='~' && tab.m[l-1][c+1]!='.') return 1;};
			}
		}
	}
return 0;
}

int validar(TAB tab)
{
	
	if (validarAux(tab)==0){
		E1(&tab);
		return validarAux(tab);	
	}
	else return 1;
}
