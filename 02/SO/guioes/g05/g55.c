#include <unistd.h>

int main(){
    int fd[2];

    pipe(fd);

    if(fork()!=0){
        close(fd[0]);
        dup2(fd[1],1);
        execlp("grep","grep","-v","^#","/etc/passwd",NULL);
    }
    else{
        close(fd[1]);
        dup2(fd[0],0);

        int fd2[2];

        pipe(fd2);

        if(fork()!=0){
            close(fd2[0]);
            dup2(fd2[1],1);
            execlp("cut","cut","-f7","-d:",NULL);
        }
        else{
            close(fd2[1]);
            dup2(fd2[0],0);
            int fd3[2];
            pipe(fd3);
            if(fork()!=0){
                close(fd3[0]);
                dup2(fd3[1],1);
                execlp("uniq","uniq",NULL);
            }
            else{
                close(fd3[1]);
                dup2(fd3[0],0);
                execlp("wc","wc","-l",NULL);


            }



        }

    }
}