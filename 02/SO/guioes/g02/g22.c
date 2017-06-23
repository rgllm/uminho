#include <unistd.h>
#include <sys/wait.h>
#include <stdio.h>


int main(int argc, char**argv){

	int pid=fork();

	if(pid==0){
		//ESTAMOS NO FILHO
		printf("CHILD// PID: %d PPID: %d\n",getpid(),getppid());

	}
	else{
		//ESTAMOS NO PAI
		printf("FATHER// PID: %d PPID: %d CHILD_PID: %d\n",getpid(),getppid(),pid);
	}

	return 1;

}