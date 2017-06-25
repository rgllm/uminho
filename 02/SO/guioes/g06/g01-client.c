
#include <sys/types.h>
#include <sys/stat.h>
#include <stdlib.h>
#include <fcntl.h>
#include <unistd.h>


int main(int argc, char * argv[]){

    char b;

    if (argc < 2) exit(1);

    int fd=open(argv[1],O_WRONLY);

    while(read(0,&b,1) > 0 )
        write(fd,&b,1);

    close(fd);
}