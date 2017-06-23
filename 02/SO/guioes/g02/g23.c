#include <unistd.h>
#include <sys/wait.h>
#include <stdio.h>


int main(int argc, char**argv){

	
	for(int i=0;i<10;i++){
		int pid=fork();
		int status=i+1;
		if(pid==0){
			//ESTAMOS NO FILHO
			printf("CHILD %d// PID: %d PPID: %d\n",i+1,getpid(),getppid());
			_exit(status);

		}
		else{
			//ESTAMOS NO PAI
			printf("FATHER// PID: %d PPID: %d CHILD_PID: %d\n",getpid(),getppid(),pid);
			wait(&status);
			printf("FATHER// CHILD DIED WITH %d",WEXITSTATUS(status));
		}
	}

	return 1;

}