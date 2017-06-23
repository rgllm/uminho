#include <unistd.h>
#include <sys/wait.h>
#include <stdio.h>


int main(int argc, char**argv){

	int pid=fork();

	if(pid==0){
		//ESTAMOS NO FILHO
		execlp("./g33","./g33","-l","-o","p",NULL);

	}
	else{
		//ESTAMOS NO PAI
	}

	return 1;

}