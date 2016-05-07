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
    ((char*)buf)[i]='\0';
    return i+1;
}

int main(){
    char  buf[MAXBUFF], *dir,digest[MAXBUFF],op,*nome;
    int n,pid;
    int fd,fd1;
    char *pipedir ="/home/munybt/.backup/pipe";
    fd=open(pipedir,O_RDONLY);
    while(1){
        while((n=readln(fd,buf))>0){
            dir=strdup(strtok(buf," "));
            op=strtok(NULL," ")[0];
            pid=atoi(strtok(NULL,"\0"));
            if(op=='b'){
                strcpy(buf,"openssl dgst ");
                strcat(buf,dir);
                strcat(buf," | cut -d ' ' -f2 > aux.txt");
                system(buf);
                fd1=open("aux.txt",O_RDONLY);
                readln(fd1,digest);
                close(fd1);
                system("rm aux.txt");
                strcpy(buf,"gzip -k -c ");
                strcat(buf,dir);
                strcat(buf," > /home/munybt/.backup/data/");
                strcat(buf,digest);
                strcat(buf,".gz");
                system(buf);
                strcpy(buf,"ln -s /home/munybt/.backup/data/");
                strcat(buf,digest);
                strcat(buf,".gz ");
                strcat(buf,"/home/munybt/.backup/metadata/");
                nome=strrchr(dir, '/')+1;
                if(nome==NULL)
                    nome=dir;
                strcat(buf,nome);
                system(buf);
                kill(pid,SIGUSR1);
            }
            else printf("RESTORE %s\n",dir );
        }
    }
    close(fd);
}