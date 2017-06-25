
#include <unistd.h>
#include <stdio.h>
#include <sys/wait.h>
#define READ 0
#define WRITE 1
#define N 32

int main(int argc, char** argv){

	int fd[2],status;
	pipe(fd);
	int pid=fork();

	if(pid==0){
		//PROCESSO FILHO
		printf("FILHO\n");
		close(fd[WRITE]);
		dup2(fd[READ],0);
		execlp("wc","wc","-l",NULL);

	}
	else{
		//PROCESSO PAI
		printf("PAI\n");
		close(fd[READ]);
		dup2(fd[WRITE],1);
		execlp("ls","ls","/etc",NULL);

	}

	return 1;
}