#include <unistd.h>
#include <sys/wait.h>
#include <stdio.h>


int main(int argc, char**argv){

	int pid=fork();

	if(pid==0){
		//ESTAMOS NO FILHO
		execlp("ls","ls","-l",NULL);

	}
	else{
		//ESTAMOS NO PAI
	}

	return 1;

}