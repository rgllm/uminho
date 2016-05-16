#include <sys/signal.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <stdio.h>
#include <fcntl.h>
#include <string.h>
#include <stdlib.h>
#include <unistd.h>
#define MAXBUFF 256

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


int temBackup(char* digest){
    char buf[MAXBUFF];
    strcpy(buf,"find /home/munybt/.backup/data -name ");
    strcat(buf,digest);
    strcat(buf,".gz > aux.txt");
    system(buf);
    int fd=open("aux.txt",O_RDONLY);
    int ret= readln(fd,buf);
    close(fd);
    system("rm aux.txt");
    return ret;
}

int main(int argc, char *argv[], char *envp[]){
    char  buf[MAXBUFF], *dir,digest[MAXBUFF],op,*nome,*home=getenv("HOME");
    int n,pid,aux;
    int fd,fd1;
    char *pipe_dir=strdup(home) , *data_dir= strdup(home) , *metadata_dir=strdup(home);
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
                /*FALTA: testar se existe algum ficheiro com o mesmo nome    */
                strcpy(buf,"sha1sum ");
                strcat(buf,dir);
                strcat(buf," | cut -d ' ' -f1 > aux.txt");
                system(buf);     // openssl dgst "dirFicheiro" | cut -d ' ' -f2 > aux.txt
                fd1=open("aux.txt",O_RDONLY);
                readln(fd1,digest);
                close(fd1);
                system("rm aux.txt");
                if(!temBackup(digest)){
                    strcpy(buf,"gzip -k -c ");
                    strcat(buf,dir);
                    strcat(buf," > ");
                    strcat(buf,data_dir);
                    strcat(buf,digest);
                    strcat(buf,".gz");
                    system(buf);     // gzip -k -c "dirFicheiro" > /home/munybt/.backup/data/"digestFicheiro".gz
                }

                strcpy(buf,"ln -s ");
                strcat(buf,data_dir);
                strcat(buf,digest);
                strcat(buf,".gz ");
                strcat(buf,metadata_dir);
                printf("meta -> %s\n",metadata_dir );
                nome=strrchr(dir, '/')+1;
                if(nome==NULL)
                    nome=dir;
                strcat(buf,nome);
                printf("%s\n",buf );
                system(buf); // ln -s /home/munybt/.backup/data/"digestFicheiro".gz /home/munybt/.backup/metadata/"nomeFicheiro"

                kill(pid,SIGUSR1);
            }

                                            /*    RESTORE      */
            else {
                strcpy(buf,"find /home/munybt/.backup/metadata -name ");
                strcat(buf,dir);
                strcat(buf," > aux.txt");
                system(buf);     //  find /home/munybt/.backup/metadata -name "nomeFicheiro" > aux.txt
                fd1=open("aux.txt",O_RDONLY);
                aux=readln(fd1,buf);
                close(fd1);
                system("rm aux.txt");
                if(aux){
                    strcpy(buf,"ls -l /home/munybt/.backup/metadata | grep ");
                    strcat(buf,dir);
                    strcat(buf," | cut -d ' ' -f11 > aux.txt");
                    system(buf);     // ls -l /home/munybt/.backup/metadata | grep "nomeFicheiro"  | cut -d ' ' -f11 > aux.txt
                    fd1=open("aux.txt",O_RDONLY);
                    readln(fd1,digest);
                    close(fd1);
                    system("rm aux.txt");
                    strcpy(buf,"gunzip < ");
                    strcat(buf,digest);
                    strcat(buf,"> ./");
                    strcat(buf,dir); // restaura o ficheiro para o diretorio do servidor
                    system(buf);     // gunzip < /home/munybt/.backup/metadata/"digestFicheiro" > ./"nomeFicheiro"

                /*FALTA: - testar se existe algum ficheiro em metadata a apontar para o mesmo digest.gz 
                         - caso exista, apagar apenas o link
                         - caso contrario apagar link e .gz
                */   
                    kill(pid,SIGUSR1);
                }
                else kill(pid,SIGUSR2);

            }
        }
    }
    close(fd);
}