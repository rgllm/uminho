#include <unistd.h>   /* chamadas ao sistema: defs e decls essenciais */
#include <fcntl.h>    /* O_RDONLY, O_WRONLY, O_CREAT, O_* */
#include <sys/types.h>
#include <sys/uio.h>
#include <stdio.h>


int main(int argc, char **argv){

		char a=a;
		int fd;

		if(argc < 2){
			printf("Please you have to specify the file name.\n");
			return 1;
		}
		else{
			fd = open(argv[1],O_WRONLY | O_CREAT | O_TRUNC,0666);
		}

		if(fd==-1){
			printf("ERROR!\n");
			return -1;
		}

	    for (int i=0;i<10*1024*1024;i++){
	       write(fd,&a,1);
	   }

return 1;
}
