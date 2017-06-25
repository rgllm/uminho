#include <sys/types.h>
#include <sys/stat.h>
#include <stdlib.h>
#include <fcntl.h>
#include <unistd.h>

int main(int argc, char * argv[]){

    char b;
    int fd;

    if (argc < 2) exit(1);
	
	while(1){
    	fd=open(argv[1],O_RDONLY);

    while(read(fd,&b,1) > 0 ){
        write(1,&b,fd);
    }

    close(fd);
	}
}