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
void hand2(int s){ printf("Ficheiro não encontrado\n");}

int main(int argc,char * argv[]){
    int i,nFiles;
    char buf[MAXBUFF],aux,spid[8];
    int pipe=open("/home/munybt/.backup/pipe",O_WRONLY);
    int pid=getpid();

    if(argc<3){
        printf("Precisa de argumentos!\n");
        printf("./sobucli backup \"<dir>\"  -> criar backup de um ficheiro\n");
        printf("./sobucli restore \"<dir>\" -> restaurar um ficheiro\n");
        exit(1);
    }

    signal(SIGUSR1,hand);
    signal(SIGUSR2,hand2);

    sprintf(spid, "%d", pid);
                                               /*    BACKUP      */
    if(strcmp(argv[1],"backup")==0){         
        for(i=0;i<argc-2;i++){
            strcpy(buf,argv[i+2]);
            strcat(buf," B ");
            strcat(buf,spid);
            strcat(buf,"\n");     // "dirFicheiro" B "myPID" 
            write(pipe,buf,strlen(buf));
            pause();
            printf("%s : copiado\n",strtok(buf," ")); // verificar sucesso ou insucesso antes do print
        }

    } 
                                                /*    RESTORE      */
    else if(strcmp(argv[1],"restore")==0){
        for(i=0;i<argc-2;i++){
            strcpy(buf,argv[i+2]);
            strcat(buf," R ");
            strcat(buf,spid);
            strcat(buf,"\n");
            write(pipe,buf,strlen(buf));     // "dirFicheiro" R "myPID" 
            pause(); 
            printf("%s : restaurado\n",argv[2] ); // verificar sucesso ou insucesso antes do print
        }
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