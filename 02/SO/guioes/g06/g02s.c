
#include <sys/types.h>
#include <sys/stat.h>
#include <stdlib.h>
#include <fcntl.h>
#include <unistd.h>


int main(int argc, char * argv[]){

	int pipe=open("fifo",O_RDONLY);
	int log=open("log",O_WRONLY|O_CREAT|O_TRUNC,0666);
	char buf;

	while(1){
		while(read(pipe,&buf,1)){
			write(log,&buf,1);
		}
	}

	return 1;
}