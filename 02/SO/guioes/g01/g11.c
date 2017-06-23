#include <unistd.h>   /* chamadas ao sistema: defs e decls essenciais */
#include <fcntl.h>    /* O_RDONLY, O_WRONLY, O_CREAT, O_* */
#include <sys/types.h>
#include <sys/uio.h>


int main(int argc, char **argv){

	char buf;

	while(read(0, &buf, 1)){
		write(1,&buf,1);
	}
return 1;
}
