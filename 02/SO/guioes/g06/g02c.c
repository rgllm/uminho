
#include <sys/types.h>
#include <sys/stat.h>
#include <stdlib.h>
#include <fcntl.h>
#include <unistd.h>


int main(int argc, char * argv[]){

	int pipe=open("fifo",O_WRONLY,0666);
	char buf;

	while(read(0,&buf,1)){
		write(pipe,&buf,1);
	}
	
	return 1;
}