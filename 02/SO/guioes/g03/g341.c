#include <unistd.h>
#include <sys/wait.h>
#include <stdio.h>


int main(int argc, char**argv){
	int vezes[argc];
	char* fila[argc];
	int nprogramas=1;
	
	for(int i=0;i<argc && nprogramas!=0;i++){
		int status;	
		int pid=fork();	
		if(pid==0){
			//ESTAMOS NO FILHO
			execlp(argv[i+1],argv[i+1],NULL);

		}
		else{
			//ESTAMOS NO PAI
			wait(&status);
			/*if(&status!=0){

			}
			vezes[i]++; */
		}
	}

	return 1;

}