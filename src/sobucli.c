#include <sys/types.h>
#include <sys/signal.h>
#include <sys/stat.h>
#include <stdio.h>
#include <fcntl.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
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

void hand(int s){}
int main(int argc,char * argv[]){
    if(argc<3){
        printf("Precisa de argumentos!\n");
        printf("./sobucli backup \"<dir>\"  -> criar backup de um ficheiro\n");
        printf("./sobucli restore \"<dir>\" -> restaurar um ficheiro\n");
        exit(1);
    }
    int i,nFiles,fp;
    char buf[strlen(argv[2])+20],* * commands,aux,spid[8];
    int pipe=open("/home/munybt/.backup/pipe",O_WRONLY);
    int pid=getpid();

    signal(SIGUSR1,hand);
    sprintf(spid, "%d", pid);
    if(strcmp(argv[1],"backup")==0){
        strcpy(buf,"ls ");
        strcat(buf,argv[2]);
        strcat(buf," > aux");
        system(buf);
        system("wc -l aux > nLines");

        fp=open("nLines",O_RDONLY);
        readln(fp,buf);
        close(fp);
        system("rm nLines");
        nFiles=atoi(buf);
        commands=malloc(nFiles*sizeof(char*));
        fp=open("aux",O_RDONLY);
        for(i=0;i<nFiles;i++){
            readln(fp,buf);
            strcat(buf," b ");
            strcat(buf,spid);
            strcat(buf,"\n");
            write(pipe,buf,strlen(buf));
            pause();
            printf("%s : copiado\n",strtok(buf," "));
        }
        close(fp);
        system("rm aux");

        

    } 
    else if(strcmp(argv[1],"restore")==0){
        printf("RESTORE\n");
    }
    else{
        printf("Comando inválido\n");
        printf("./sobucli backup \"<dir>\"  -> criar backup de um ficheiro\n");
        printf("./sobucli restore \"<dir>\" -> restaurar um ficheiro\n");
        close(pipe);
        exit(1);
    }
    close(pipe);
}