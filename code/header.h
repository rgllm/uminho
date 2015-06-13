#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <time.h>

#define MAXL 150
#define MAXC 150
#define BUFFER 1024

/** Estrutura para armazenar toda a informação do tabuleiro. */
typedef struct tab{
	int linhas;
	int colunas;
	int somal[MAXL];
	int somac[MAXC];
	char m[MAXL][MAXC];
    }TAB;

/** Armazena a posição i,j do caratér c */
typedef struct chr{
	int i,j;
	char c;
}CHR;

/** Em cada lchr é armazenada a informação necessária para proceder com o desfazer */
typedef struct lchr
{
	int sizeVCHR;		/** tamanho máximo do vetor v */
	int nchr;			/** número de alterações em tabuleiros (não é contabilizada a entrada de tabuleiros novos) */	
	CHR *vchr;			/** vetor de chr's */
   	
   	int flag;
   	TAB tab;

   	struct lchr *next;	/** aponta para a próxima lchr */
 
 }*LCHR;
