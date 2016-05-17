#include <sys/types.h>
#include <sys/signal.h>
#include <sys/stat.h>
#include <stdio.h>
#include <fcntl.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#define MAXBUFF 128

void hand(int s){
    if(s==30) printf("Sucesso!\n");
    if(s==10) printf("Ficheiro não encontrado!\n");
    if(s==6) printf("Já existe backup de um ficheiro com o mesmo nome!\n");
}

int main(int argc,char * argv[]){
    int i;
    char buf[MAXBUFF],spid[8],*home=getenv("HOME"),*pipe_dir;
    pipe_dir=malloc((strlen(home)+14)*sizeof(char));
    strcpy(pipe_dir,home);
    strcat(pipe_dir,"/.backup/pipe");
    int pipe=open(pipe_dir,O_WRONLY);
    int pid=getpid();
    printf("1");
    if(argc<3){
        printf("Precisa de argumentos!\n");
        printf("./sobucli backup <dir>  -> criar backup de um ficheiro\n");
        printf("./sobucli restore <dir> -> restaurar um ficheiro\n");
        exit(1);
    }

    signal(30,hand);
    signal(10,hand);
    signal(6,hand);
    sprintf(spid, "%d", pid);
                                               /*    BACKUP      */
    if(strcmp(argv[1],"backup")==0){
            printf("2");
        for(i=0;i<argc-2;i++){
                printf("3");
            strcpy(buf,argv[i+2]);
                printf("4");
            strcat(buf," B ");
            strcat(buf,spid);
                printf("5");
            strcat(buf,"\n");     /* "dirFicheiro" B "myPID" */
            write(pipe,buf,strlen(buf));
                printf("6");
            printf("> %s : ",strtok(buf," "));
            pause();

        }

    }
                                                /*    RESTORE      */
    else if(strcmp(argv[1],"restore")==0){
        for(i=0;i<argc-2;i++){
            strcpy(buf,argv[i+2]);
            strcat(buf," R ");
            strcat(buf,spid);
            strcat(buf,"\n");
            write(pipe,buf,strlen(buf));     /* "dirFicheiro" R "myPID" */
            printf("> %s : ",argv[i+2] );
            pause();

        }
    }
    else{
        printf("Comando inválido\n");
        printf("./sobucli backup <dir>  -> criar backup de um ficheiro\n");
        printf("./sobucli restore <dir> -> restaurar um ficheiro\n");
        close(pipe);
        exit(1);
    }
    close(pipe);

    return 0;
}
