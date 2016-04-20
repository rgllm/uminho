
#include "others.h"


char getch(){
char c = 0;
   struct termios old = {0};
   fflush(stdout);
   tcgetattr(0, &old);
   old.c_lflag&=~ICANON;
    old.c_lflag&=~ECHO;
    old.c_cc[VMIN]=1;
    old.c_cc[VTIME]=0;
    tcsetattr(0, TCSANOW, &old);
    read(0,&c,1);
    old.c_lflag|=ICANON;
    old.c_lflag|=ECHO;
    tcsetattr(0, TCSADRAIN, &old);
    return c;
 }

/**
 * Lê um ficheiro .txt que contem um TextArt
 * NOTA: o ficheiro já tem os \n
 * @param nome_ficheiro
 */
void carregaArt(char *nome_ficheiro) {
    char *lido;
    char *linha = (char *) malloc(256);
    FILE *ficheiro;

    ficheiro = fopen(nome_ficheiro,"r");

    while((lido = fgets(linha,256,ficheiro)) != NULL) {
        printf("%s",linha);
    }

    fclose(ficheiro);
}

/*

char *my_strdup(const char *s) {
    char *p = malloc(strlen(s) + 1);
    if(p) { strcpy(p, s); }
    return p;
}

this goes in whatever header defines my_strdup
char *my_strdup(const char *s);
#define strdup(x) my_strdup(x)

*/