#include <unistd.h>
#include <sys/wait.h>
#include <stdio.h>
#include <time.h>
#include <stdlib.h>
#define COLUNAS 2
#define LINHAS 6000
#define LIMITE 1000000

int matriz[COLUNAS][LINHAS];


void gerarMatrizAleatoria(){
	srand(time(NULL)); 

	for(int c=0;c<COLUNAS;c++){
		for(int l=0;l<LINHAS;l++){
			matriz[c][l]=rand()%LIMITE;
		}
	}

}

int main(int argc, char**argv){

	int encontrar=atoi(argv[1]);
	int status;
	gerarMatrizAleatoria();

	for(int c=0;c<COLUNAS;c++){
	for(int l=0;l<LINHAS;l++){
			if(matriz[c][l]==encontrar){
				printf("##%d##      ",matriz[c][l]);
			}
			else{
			printf("%d      ",matriz[c][l]);
			}
		}
	printf("\n");
	}
	printf("\n\n\n");

	for(int c=0;c<COLUNAS;c++){
		int pid=fork();
		if(pid==0){
			for(int l=0;l<LINHAS;l++){
				if(matriz[c][l]==encontrar){
					printf("\nENCONTRADO: ");
					_exit(l);
				}
				else{
					_exit(-1);
				}
			}
		}
		else{
			wait(&status);
			if((status>=0) &&(WEXITSTATUS(status)!=255)) {printf("matriz[%d][%d]\n",c+1,WEXITSTATUS(status)+1);}
		}
	}
	return 1;
}
