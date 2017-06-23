#include <unistd.h>   /* chamadas ao sistema: defs e decls essenciais */
#include <fcntl.h>    /* O_RDONLY, O_WRONLY, O_CREAT, O_* */
#include <sys/types.h>
#include <sys/uio.h>
#define N 10


int main(int argc, char **argv){

	char buf[N];
	int n;

	while((n=read(0, &buf, N))>0){
		write(1,&buf,N);
	}
return 1;
}
