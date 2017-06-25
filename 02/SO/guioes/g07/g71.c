#include <signal.h>
#include <sys/types.h>
#include <stdlib.h>
#include <unistd.h>
#include <stdio.h>
typedef void (*sighandler_t)(int);

int vezes=0;
int seg=0;

void hand(int s){
    seg++;
}

void show(int s){
    vezes++;
    printf("%d:%d:%d\n",seg/3600,seg/60,seg%60);
}

void showb(int s){
    printf("%d\n",vezes);
    exit(1);
}

int main(){
    signal(SIGALRM,hand);
    signal(SIGINT,show);
    signal(SIGQUIT,showb);
    while(1){alarm(1); pause();}
    return 1;
}