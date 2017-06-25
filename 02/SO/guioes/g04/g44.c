#include <unistd.h>
#include <sys/wait.h>
#include <stdio.h>
#include <sys/types.h>
#include <sys/uio.h>
#include <fcntl.h>    /* O_RDONLY, O_WRONLY, O_CREAT, O_* */
#include <sys/types.h>
#include <string.h>
#define N 100
#define STDIN 0
#define STDOUT 1
#define STDERR 2

int main(int argc, char**argv){

	int fich_entrada,fich_saida;

	if(strcmp(argv[1],"-i")){
		fich_entrada = open(argv[2],O_RDONLY);
		dup2(fich_entrada,STDIN);
	}
	else{
		if(strcmp(argv[1],"-o")){
			fich_saida=open(argv[2],O_WRONLY|O_CREAT,O_TRUNC,0666);
			dup2(fich_saida,STDOUT);
		}
	}
	if(strcmp(argv[3],"-o")){
		fich_saida=open(argv[2],O_WRONLY|O_CREAT,O_TRUNC,0666);
		dup2(fich_saida,STDOUT);
	 	execvp(argv[5],&argv[5]);
    }
    else {
    	execvp(argv[3],&argv[3]);
    }

	return 1;
}