#include <sys/signal.h>
#include <sys/wait.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <signal.h>
#include <stdio.h>
#include <fcntl.h>
#include <string.h>
#include <stdlib.h>
#include <unistd.h>
#define MAXBUFF 256

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


int temBackup(char* digest){
    char buf[MAXBUFF],*home=getenv("HOME"),*data_dir;
    int fd,ret;
    data_dir=malloc((strlen(home)+15)*sizeof(char));
    strcpy(data_dir,home);
    strcat(data_dir,"/.backup/data/");

    strcat(digest,".gz");    /*  find ".backup/data" -name "digest".gz   */
    fd=open("aux.txt",O_CREAT | O_TRUNC | O_RDONLY,0666);
    if(fork()==0){
        dup2(fd,1);
        dup2(fd,2);
        execlp("find","find",data_dir,"-name",digest,NULL);
    }

    ret=readln(fd,buf);
    close(fd);
    return ret;
}

int main(){
    char  buf[MAXBUFF], *dir,digest[MAXBUFF],op,*nome,*home=getenv("HOME");
    int n,pid,aux,unPipe[2],up2[2],up3[2],status,i;
    int fd,fd1;
    char *pipe_dir, *data_dir, *metadata_dir;

    pipe_dir=malloc((strlen(home)+14)*sizeof(char));
    data_dir=malloc((strlen(home)+15)*sizeof(char));
    metadata_dir=malloc((strlen(home)+19)*sizeof(char));
    strcpy(pipe_dir,home);
    strcpy(data_dir,home);
    strcpy(metadata_dir,home);
    strcat(pipe_dir,"/.backup/pipe");
    strcat(data_dir,"/.backup/data/");
    strcat(metadata_dir,"/.backup/metadata/");
    fd=open(pipe_dir,O_RDONLY);
    while(1){
        while((n=readln(fd,buf))>0){
            dir=strdup(strtok(buf," "));
            op=strtok(NULL," ")[0];
            pid=atoi(strtok(NULL,"\0"));

                                           /*    BACKUP      */

            if(op=='B'){
                   
                pipe(up2);
                if(fork()==0){
                    close(up2[0]);
                    dup2(up2[1],1);
                    dup2(up2[1],2);                              /**/ 
                    pipe(unPipe);                                /**/
                    if(fork()==0){                               /**/
                        close(unPipe[0]);                        /**/
                        dup2(unPipe[1],1);                       /**/                      
                        execlp("sha1sum","sha1sum",dir,NULL);    /* sha1sum "dirFicheiro" | cut -d ' ' -f1   */
                    }                                            /**/
                    close(unPipe[1]);
                    wait(&status);
                    dup2(unPipe[0],0);
                    execlp("cut","cut","-d"," ","-f1",NULL);
                }
                close(up2[1]);
                wait(&status);
                readln(up2[0],digest);
                close(up2[0]);

                aux=0;
                for(i=0;i<strlen(dir);i++){
                    if(dir[i]=='/') aux=1;
                }

                if(aux==0)
                    nome=dir;
                else nome=strrchr(dir, '/')+1;

              /*testar se existe algum ficheiro com o mesmo nome    */
                pipe(unPipe);

                if(fork()==0){
                    close(unPipe[0]);
                    dup2(unPipe[1],1);
                    execlp("find","find",metadata_dir,"-name",nome,NULL);  /* find "metadata" -name "nomeFicheiro" */
                }
                wait(&status);
                close(unPipe[1]);
                aux=readln(unPipe[0],buf);
                close(unPipe[0]);

                if(aux!=0){
                    kill(pid,6); /* ja existe um backup com o nome fornecido */
                }

                else{
                    if(!temBackup(digest)){
                        if(fork()==0){
                            strcpy(buf,data_dir);
                            strcat(buf,digest);
                            fd1=open(buf,O_CREAT | O_WRONLY | O_TRUNC,0666); /* gzip -k -c "dirFicheiro" > /home/munybt/.backup/data/"digestFicheiro".gz*/
                            dup2(fd1,1);
                            execlp("gzip","gzip","-k","-c",dir,NULL);
                        }
                    }
                    if(fork()==0){
                        char buf2[MAXBUFF];
                        strcpy(buf,data_dir);
                        strcat(buf,digest); 
                        strcpy(buf2,metadata_dir);
                        strcat(buf2,nome);
                        execlp("ln","ln","-s",buf,buf2,NULL);
                        /* ln -s /home/munybt/.backup/data/"digestFicheiro".gz /home/munybt/.backup/metadata/"nomeFicheiro"*/
                    }
                    kill(pid,30);
                }
            }

                                            /*    RESTORE      */
            else {
                
                pipe(unPipe);
                if(fork()==0){
                    close(unPipe[0]);
                    dup2(unPipe[1],1);
                    execlp("find","find",metadata_dir,"-name",dir,NULL);
                }  /*  find /home/munybt/.backup/metadata -name "nomeFicheiro" > aux.txt*/
                close(unPipe[1]);
                wait(&status);
                aux=readln(unPipe[0],buf);
                close(unPipe[0]);

                if(aux){  /* se o nome existir */
                    pipe(up3);
                    if(fork()==0){
                        close(up3[0]);
                        dup2(up3[1],1);
                        pipe(up2);
                        if(fork()==0){ 
                            close(up2[0]);
                            dup2(up2[1],1);
                            pipe(unPipe);

                            if(fork()==0){
                                close(unPipe[0]);
                                dup2(unPipe[1],1);
                                execlp("ls","ls","-l",metadata_dir,NULL);
                        /* ls -l /home/munybt/.backup/metadata | grep "nomeFicheiro"  | cut -d ' ' -f11 > aux.txt*/
                            }

                            close(unPipe[1]);
                            wait(&status);
                            dup2(unPipe[0],0);
                            execlp("grep","grep",dir,NULL);
                        }
                        close(up2[1]);
                        dup2(up2[0],0);
                        execlp("cut","cut","-d"," ","-f11",NULL);
                    }
                    close(up3[1]);
                    readln(up3[0],digest);
                    close(up3[0]);

                    strcpy(buf,"gunzip < ");
                    strcat(buf,digest);
                    strcat(buf,"> ./");
                    strcat(buf,dir); /* restaura o ficheiro para o diretorio do servidor*/
                    system(buf);     /* gunzip < /home/munybt/.backup/metadata/"digestFicheiro" > ./"nomeFicheiro"*/

                    strcpy(digest,strrchr(digest, '/')+1);

                    strcpy(buf,"ls -l ");
                    strcat(buf,metadata_dir);
                    strcat(buf," | grep ");
                    strcat(buf,digest);
                    strcat(buf," | wc -l > aux.txt");
                    system(buf); /* ls -l /home/munybt/.backup/metadata/ | grep "digest".gz | wc -l > aux.txt */

                    fd1=open("aux.txt",O_RDONLY);
                    readln(fd1,buf);
                    close(fd1);
                    aux=atoi(buf);

                    if(aux==1){
                        if(fork()==0){
                            strcpy(buf,data_dir);
                            strcat(buf,digest);
                            execlp("rm","rm",buf,NULL);  /* rm /home/munybt/.backup/data/"digest".gz */
                        }
                    }

                    if(fork()==0){
                        strcpy(buf,metadata_dir);
                        strcat(buf,dir);
                        execlp("rm","rm",buf,NULL);  /* rm /home/munybt/.backup/metadata/"nome"  */
                    }

                    kill(pid,30);
                }
                else kill(pid,10);

            }
        }
    }
    system("rm aux.txt");
    close(fd);
    return 0;
}
