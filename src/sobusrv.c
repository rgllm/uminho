#include <sys/signal.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <stdio.h>
#include <fcntl.h>
#include <string.h>
#include <stdlib.h>
#include <unistd.h>
#define MAXBUFF 256

ssize_t readln(int fildes, char *buf){
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
    char buf[MAXBUFF];
    int fd,ret;
    strcpy(buf,"find /home/munybt/.backup/data -name ");
    strcat(buf,digest);
    strcat(buf,".gz > aux.txt");
    system(buf);
    fd=open("aux.txt",O_RDONLY);
    ret= readln(fd,buf);
    close(fd);
    return ret;
}

int main(){
    char  buf[MAXBUFF], *dir,digest[MAXBUFF],op,*nome,*home=getenv("HOME");
    int n,pid,aux;
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
                strcpy(buf,"sha1sum ");
                strcat(buf,dir);
                strcat(buf," | cut -d ' ' -f1 > aux.txt");
                system(buf);     /* sha1sum "dirFicheiro" | cut -d ' ' -f2 > aux.txt*/

                fd1=open("aux.txt",O_RDONLY);
                readln(fd1,digest);
                close(fd1);

                nome=strrchr(dir, '/')+1;
                if(nome==NULL)
                    nome=dir;
 
              /*testar se existe algum ficheiro com o mesmo nome    */

                strcpy(buf,"find ");
                strcat(buf,metadata_dir);
                strcat(buf," -name ");
                strcat(buf,nome);
                strcat(buf," > aux.txt");
                system(buf);
                fd1=open("aux.txt",O_RDONLY);

                if(readln(fd1,buf)){
                    kill(pid,6); /* ja existe um backup com o nome fornecido */
                }
                else{
                    close(fd1);
                    if(!temBackup(digest)){
                        strcpy(buf,"gzip -k -c ");
                        strcat(buf,dir);
                        strcat(buf," > ");
                        strcat(buf,data_dir);
                        strcat(buf,digest);
                        strcat(buf,".gz");
                        system(buf);     /* gzip -k -c "dirFicheiro" > /home/munybt/.backup/data/"digestFicheiro".gz*/
                    }

                    strcpy(buf,"ln -s ");
                    strcat(buf,data_dir);
                    strcat(buf,digest);
                    strcat(buf,".gz ");
                    strcat(buf,metadata_dir);
                    strcat(buf,nome);
                    system(buf); /* ln -s /home/munybt/.backup/data/"digestFicheiro".gz /home/munybt/.backup/metadata/"nomeFicheiro"*/

                    kill(pid,30);
                }
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
                if(aux){
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
}