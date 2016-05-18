#include <sys/types.h>
#include <sys/signal.h>
#include <sys/stat.h>
#include <sys/wait.h>
#include <stdio.h>
#include <fcntl.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <errno.h>
#define MAXBUFF 128

int readln(int fildes, char *buf){
    int i=0,aux;
    for(i=0;i<MAXBUFF;i++){
        aux=read(fildes,buf+i,1);
        if(((char*)buf)[i]=='\n') break;
        if(aux<=0) return aux;
    }
    ((char*)buf)[i]='\0';
    return i+1;
}

void hand(int s){
    if(s==30) printf("Sucesso!\n");
    if(s==10) printf("Ficheiro não encontrado!\n");
    if(s==6) printf("Já existe backup de um ficheiro com o mesmo nome!\n");
}

int main(int argc,char * argv[]){
    int i,fd[2],aux,status,pid=getpid(),fdPipe;
    char buf[MAXBUFF],spid[8],*home=getenv("HOME"),*pipe_dir;
    struct stat st_buf;
    pipe_dir=malloc((strlen(home)+14)*sizeof(char));
    strcpy(pipe_dir,home);
    strcat(pipe_dir,"/.backup/pipe");
    fdPipe=open(pipe_dir,O_WRONLY);

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
        for(i=2;i<argc;i++){
            status = stat (argv[i],&st_buf);

            if ((aux=S_ISDIR (st_buf.st_mode))) {
                printf("%s é um diretório.\n", argv[i]);
            }
            else{
   
             
                if(!status){
                   strcpy(buf,argv[i]);
                    strcat(buf," B ");
                    strcat(buf,spid);
                    strcat(buf,"\n");     /* "dirFicheiro" B "myPID" */
                    write(fdPipe,buf,strlen(buf));
                    printf("> %s : ",strtok(buf," "));
                    pause();
                }
                else printf("Ficheiro não existe!\n");
            }
        }
}
                                                /*    RESTORE      */
    else if(strcmp(argv[1],"restore")==0){
        for(i=2;i<argc;i++){
            strcpy(buf,argv[i]);
            strcat(buf," R ");
            strcat(buf,spid);
            strcat(buf,"\n");
            write(fdPipe,buf,strlen(buf));     /* "dirFicheiro" R "myPID" */
            printf("> %s : ",argv[i] );
            pause();

        }
    }
    else{
        printf("Comando inválido\n");
        printf("./sobucli backup <dir>  -> criar backup de um ficheiro\n");
        printf("./sobucli restore <dir> -> restaurar um ficheiro\n");
        close(fdPipe);
        exit(1);
    }
    close(fdPipe);


    return 0;
}
