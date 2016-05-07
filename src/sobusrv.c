#include <sys/signal.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <stdio.h>
#include <fcntl.h>
#include <string.h>
#include <stdlib.h>
#include <unistd.h>
#define MAXBUFF 128

ssize_t readln(int fildes, void *buf){
    int i=0,aux;
    for(i=0;i<MAXBUFF;i++){
        aux=read(fildes,buf+i,1);
        if(((char*)buf)[i]=='\n') break;
        if(aux<=0) return aux;
    }
    return i+1;
}

int main(){
    char  buf[MAXBUFF], *dir,op;
    int n,pid;
    int fd;
    char *pipedir ="/home/munybt/.backup/pipe";
    fd=open(pipedir,O_RDONLY);
    while(1){
        while((n=readln(fd,buf))>0){
            dir=strtok(buf," ");
            op=strtok(NULL," ")[0];
            pid=atoi(strtok(NULL,"\0"));
            if(op=='b'){
                printf("BACKUP %s\n",dir);
                kill(pid,SIGUSR1);
            }
            else printf("RESTORE %s\n",dir );
        }
    }
    close(fd);
}