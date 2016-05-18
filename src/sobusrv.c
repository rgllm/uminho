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
    int n,pid,aux,unPipe[2],status,i;
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
            fd1=open("aux.txt", O_CREAT | O_TRUNC,0666);

            if(op=='B'){
                strcpy(buf,"sha1sum ");
                strcat(buf,dir);
                strcat(buf," | cut -d ' ' -f1 > aux.txt");
                system(buf);     /* sha1sum "dirFicheiro" | cut -d ' ' -f1 > aux.txt*/

                if(fork()==0){
                    pipe(unPipe);
                    if(fork()==0){
                        close(unPipe[0]);
                        dup2(unPipe[1],1);
                        execlp("sha1sum","sha1sum",dir,NULL);
                    }
                    close(unPipe[1]);
                    dup2(unPipe[0],0);
                    dup2(fd1,1);
                    dup2(fd1,2);
                    execlp("cut","cut","-d","' '","-f1",NULL);
                }

                wait(&status);
                readln(fd1,digest);
                close(fd1);

                aux=0;
                for(i=0;i<strlen(dir);i++){
                    if(dir[i]=='/') aux=1;
                }

                if(aux==0)
                    nome=dir;
                else nome=strrchr(dir, '/')+1;

              /*testar se existe algum ficheiro com o mesmo nome    */
                strcpy(buf,"find ");
                strcat(buf,metadata_dir);
                strcat(buf," -name ");
                strcat(buf,nome);
                strcat(buf," > aux.txt");
                system(buf);
                fd1=open("aux.txt",O_RDONLY);  /* find "metadata" -name "nomeFicheiro" > aux.txt */

                if(readln(fd1,buf)!=0){
                    kill(pid,6); /* ja existe um backup com o nome fornecido */
                }
                else{
                    if(!temBackup(digest)){
                        strcpy(buf,"gzip -k -c ");
                        strcat(buf,dir);
                        strcat(buf," > ");
                        strcat(buf,data_dir);
                        strcat(buf,digest);
                        system(buf);     /* gzip -k -c "dirFicheiro" > /home/munybt/.backup/data/"digestFicheiro".gz*/
                    }

                    strcpy(buf,"ln -s ");
                    strcat(buf,data_dir);
                    strcat(buf,digest);
                    strcat(buf,"  ");
                    strcat(buf,metadata_dir);
                    strcat(buf,nome);
                    system(buf); /* ln -s /home/munybt/.backup/data/"digestFicheiro".gz /home/munybt/.backup/metadata/"nomeFicheiro"*/
                    kill(pid,30);
                }
                close(fd1);
            }

                                            /*    RESTORE      */
            else {
                strcpy(buf,"find ");
                strcat(buf,metadata_dir);
                strcat(buf," -name ");
                strcat(buf,dir);
                strcat(buf," > aux.txt");
                system(buf);     /*  find /home/munybt/.backup/metadata -name "nomeFicheiro" > aux.txt*/
                fd1=open("aux.txt",O_RDONLY);
                aux=readln(fd1,buf);
                close(fd1);
                if(aux){  /* se o nome existir */
                    strcpy(buf,"ls -l ");
                    strcat(buf,metadata_dir);
                    strcat(buf," | grep ");
                    strcat(buf,dir);
                    strcat(buf," | cut -d ' ' -f11 > aux.txt");
                    system(buf);     /* ls -l /home/munybt/.backup/metadata | grep "nomeFicheiro"  | cut -d ' ' -f11 > aux.txt*/
                    fd1=open("aux.txt",O_RDONLY);
                    readln(fd1,digest);
                    close(fd1);
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
                        strcpy(buf,"rm ");
                        strcat(buf,data_dir);
                        strcat(buf,digest);
                        system(buf); /* rm /home/munybt/.backup/data/"digest".gz */
                    }
                    strcpy(buf,"rm ");
                        strcat(buf,metadata_dir);
                        strcat(buf,dir);
                        system(buf); /* rm /home/munybt/.backup/metadata/"nome"*/

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
