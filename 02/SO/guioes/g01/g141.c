#include <unistd.h>   /* chamadas ao sistema: defs e decls essenciais */
#include <fcntl.h>    /* O_RDONLY, O_WRONLY, O_CREAT, O_* */
#include <sys/types.h>
#include <sys/uio.h>
#include <stdio.h>
#include <stdlib.h> /* atoi */


int main(int argc, char **argv){

	int n,N,fd;

	if(argc < 2){
			printf("Please you have to specify the file name.\n");
			printf("./g141 <filename> <buffer_size>\n");
			return 1;
	}
	else{
			fd = open(argv[1],O_RDONLY,0666);
	}

	if(argc < 3){
		N = 1;
	}
	else{
		N=atoi(argv[2]);
	}
	char buf[N];

	while((n=read(fd, &buf, N))>0){
		write(1,&buf,N);
	}

return 1;
}
