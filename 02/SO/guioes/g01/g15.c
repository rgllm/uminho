#include <unistd.h>   /* chamadas ao sistema: defs e decls essenciais */
#include <fcntl.h>    /* O_RDONLY, O_WRONLY, O_CREAT, O_* */
#include <sys/types.h>
#include <sys/uio.h>
#define N 10

char buf;

ssize_t readln(int fildes, void *buf, size_t nbyte){
    int p=0,r;
    while( (((r=read(fildes,buf,1))>0)) && buf!='\n'){
   
    }
    return r==1 ? p+1 : 0;
}


int main(int argc, char * argv[]){

    int fd = open(argv[1],O_RDONLY);
    while(readln(fd,buf,1)){
    	write(1,buf,1);
    }

}