#include <unistd.h>
#include <sys/wait.h>
#include <stdio.h>
#include <sys/types.h>
#include <sys/uio.h>
#include <fcntl.h>    /* O_RDONLY, O_WRONLY, O_CREAT, O_* */
#include <sys/types.h>
#define N 100
#define STDIN 0
#define STDOUT 1
#define STDERR 2

int main(int argc, char**argv){
	
	int passwd = open("/etc/passwd",O_RDONLY);
	int saida = open("saida.txt",O_WRONLY | O_CREAT | O_TRUNC,0666);
	int erros = open("erros.txt",O_WRONLY | O_CREAT | O_TRUNC,0666);
	char buf[N];

	dup2(passwd,STDIN);
	dup2(saida,STDOUT);
	dup2(erros,STDERR);

	while(read(0,&buf,N)){
		write(1,&buf,N);
	}

	return 1;
}