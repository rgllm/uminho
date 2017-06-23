#include <unistd.h>
#include <sys/wait.h>
#include <stdio.h>


int main(int argc, char**argv){


	for(int i=0;i<argc;i++){
		int status;	
		int pid=fork();	
		if(pid==0){
			//ESTAMOS NO FILHO
			execlp(argv[i+1],argv[i+1],NULL);

		}
		else{
			//ESTAMOS NO PAI
			wait(&status);
		}
	}

	return 1;

}