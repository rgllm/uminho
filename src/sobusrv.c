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
    int fd[2],ret;
    data_dir=malloc((strlen(home)+15)*sizeof(char));
    strcpy(data_dir,home);
    strcat(data_dir,"/.backup/data/");

    strcat(digest,".gz");    /*  find ".backup/data" -name "digest".gz   */
    pipe(fd);
    if(fork()==0){
        close(fd[0]);
        dup2(fd[1],1);
        close(2);
        execlp("find","find",data_dir,"-name",digest,NULL);
    }
    close(fd[1]);
    wait(NULL);
    ret=readln(fd[0],buf);
    close(fd[0]);
    return ret;
}

int main(){
    char  buf[MAXBUFF], *dir,digest[MAXBUFF],op,*nome,*home=getenv("HOME");
    int n,pid,aux,unPipe[2],up2[2],up3[2],i;
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
                    wait(NULL);
                    dup2(unPipe[0],0);
                    execlp("cut","cut","-d"," ","-f1",NULL);
                }
                close(up2[1]);
                wait(NULL);
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
                    close(2);
                    execlp("find","find",metadata_dir,"-name",nome,NULL);  /* find "metadata" -name "nomeFicheiro" */
                }
                wait(NULL);
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
                            fd1=open(buf,O_CREAT | O_WRONLY | O_TRUNC,0666); /* gzip -k -c "dirFicheiro" > /home/<user>/.backup/data/"digestFicheiro".gz*/
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
                        /* ln -s /home/<user>/.backup/data/"digestFicheiro".gz /home/<user>/.backup/metadata/"nomeFicheiro"*/
                    }
                    kill(pid,30);      /*  sucesso   */
                }
            }
            else { 
                pipe(unPipe);            /*   procurar pelo nome em metadata */ 
                if(fork()==0){
                    close(unPipe[0]);
                    dup2(unPipe[1],1);
                    close(2);
                    execlp("find","find",metadata_dir,"-name",dir,NULL);
                }              /*  find /home/<user>/.backup/metadata -name "nomeFicheiro"  */
                close(unPipe[1]);
                wait(NULL);
                aux=readln(unPipe[0],buf);
                close(unPipe[0]);

                if(aux){     /* se o nome existir */
                    pipe(up3);                  /* encontrar o digest    */
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
                        /* ls -l /home/<user>/.backup/metadata | grep "nomeFicheiro"  | cut -d ' ' -f11 */
                            }

                            close(unPipe[1]);
                            wait(NULL);
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
                                         /*    RESTORE      */
                    if (op=='R'){
                        /* restaura o ficheiro para o diretorio do servidor*/
                        if(fork()==0){
                            unPipe[0]=open(digest,O_RDONLY,0666);
                            unPipe[1]=open(dir,O_CREAT | O_WRONLY | O_TRUNC,0666); 
                            dup2(unPipe[0],0);
                            dup2(unPipe[1],1);   /* gunzip < /home/<user>/.backup/metadata/"digestFicheiro" > ./"nomeFicheiro" */
                            execlp("gunzip","gunzip",NULL);
                        }
                        kill(pid,30);
                    }
                    else{
                        /*  neste momento digest="/home/<user>/.backup/metadata/"digestFicheiro.gz"   */
                        strcpy(digest,strrchr(digest, '/')+1); 
                        /*  neste momento digest="digestFicheiro.gz"   */
                        pipe(up3);
                        if(fork()==0){
                            close(up3[0]);
                            dup2(up3[1],1);         /*calcular quantas ligaçoes existem para o ficheiro */
                            pipe(up2);
                            if(fork()==0){ 
                                close(up2[0]);
                                dup2(up2[1],1);
                                pipe(unPipe);
                                if(fork()==0){
                                    close(unPipe[0]);
                                    close(2);
                                    dup2(unPipe[1],1);
                                    execlp("ls","ls","-l",metadata_dir,NULL);
                                }                           /*    ls -l /home/<user>/.backup/metadata/ | grep "digest".gz | wc -l   */
                                close(unPipe[1]);
                                wait(NULL);
                                dup2(unPipe[0],0);
                                execlp("grep","grep",digest,NULL);
                            }
                            close(up2[1]);
                            dup2(up2[0],0);
                            execlp("wc","wc","-l",NULL);
                        }
                        close(up3[1]);
                        wait(NULL);
                        readln(up3[0],buf);
                        close(up3[0]);
                        aux=atoi(buf);  /*  aux = numero de ligaçoes para o ficheiro  */

                        if(aux==1){
                            if(fork()==0){
                                strcpy(buf,data_dir);
                                strcat(buf,digest);
                                execlp("rm","rm",buf,NULL);  /* rm /home/<user>/.backup/data/"digest".gz */
                            }
                        }
                        if(fork()==0){
                            strcpy(buf,metadata_dir);
                            strcat(buf,dir);
                            execlp("rm","rm",buf,NULL);  /* rm /home/<user>/.backup/metadata/"nome"  */
                        }
                        kill(pid,30);   /* sucesso */
                    }
                }
                else kill(pid,10);    /* o nome não existe em metadata   */
            }
        }
    }
    close(fd);
    return 0;
}
