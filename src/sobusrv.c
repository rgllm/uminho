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


int main(){
    char  buf[MAXBUFF], *dir,digest[MAXBUFF],op,*nome;
    int n,pid,aux;
    int fd,fd1;
    char *pipedir ="/home/munybt/.backup/pipe";
    fd=open(pipedir,O_RDONLY);
    while(1){
        while((n=readln(fd,buf))>0){
            dir=strdup(strtok(buf," "));
            op=strtok(NULL," ")[0];
            pid=atoi(strtok(NULL,"\0"));

                                           /*    BACKUP      */
            if(op=='B'){
                /*FALTA: testar se existe algum ficheiro com o mesmo nome    */
                strcpy(buf,"openssl dgst ");
                strcat(buf,dir);
                strcat(buf," | cut -d ' ' -f2 > aux.txt");
                system(buf);     // openssl dgst "dirFicheiro" | cut -d ' ' -f2 > aux.txt
                fd1=open("aux.txt",O_RDONLY);
                readln(fd1,digest);
                close(fd1);
                system("rm aux.txt");
                if(!temBackup(digest)){
                    strcpy(buf,"gzip -k -c ");
                    strcat(buf,dir);
                    strcat(buf," > /home/munybt/.backup/data/");
                    strcat(buf,digest);
                    strcat(buf,".gz");
                    system(buf);     // gzip -k -c "dirFicheiro" > /home/munybt/.backup/data/"digestFicheiro".gz
                }
                strcpy(buf,"ln -s /home/munybt/.backup/data/");
                strcat(buf,digest);
                strcat(buf,".gz ");
                strcat(buf,"/home/munybt/.backup/metadata/");
                nome=strrchr(dir, '/')+1;
                if(nome==NULL)
                    nome=dir;
                strcat(buf,nome);
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