
#include <sys/types.h>
#include <sys/stat.h>
#include <stdio.h>
#include <stdlib.h>


int main (){
mkfifo("fifo",0666);
return 1;
}