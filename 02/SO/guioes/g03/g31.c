#include <unistd.h>     /* chamadas ao sistema: defs e decls essenciais */



int main(int argc, char**argv){

	execlp("ls","ls","-l",NULL);


}