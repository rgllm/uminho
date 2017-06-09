%option noyywrap
%x VERBO INTERROG
%{
    #include "string.h"
    #include <stdlib.h>
    #define MAX 4000
    char * verbos[MAX];
    int countVerbos=0;
    static int compare (const void * a, const void * b)
    {
    return strcmp (*(const char **) a, *(const char **) b);
    }
%}
%%
[^a-zA-Z]+(?i:(to|can('t)?|could|shall|should|will|would|may|might|did(n't)))\ +  { ECHO; BEGIN VERBO; }
<VERBO>(?i:(i|me|my(self)?|mine|you(r|rs|rself)?|he|him(self)?|his|she|her(s|self)?|it(s)?|we|us|our(s|selves)?|the(y)|them(selves)?|a|th(is|at)))[\.\?\!\;\',\:\ \n\t] { ECHO ; BEGIN INTERROG;}
<VERBO>[a-z]+ { 
    int i=0;
    while(i<countVerbos){
        if(strcmp(verbos[i],yytext)==0)
            break;
        i++;
    }
    if(i==countVerbos)
        verbos[countVerbos++]=strdup(yytext);
    ECHO;
    BEGIN INITIAL;
} 
<VERBO>.|\n                    { ECHO ; BEGIN INITIAL ; }
<INTERROG>[a-z]+(\ [a-z]+)?\? { 
    int i=0;
    yytext[strlen(yytext)-1]='\0';
    char * aux=strtok(yytext," ");
    aux=strtok(aux,"?");
    while(i<countVerbos){
        if(strcmp(verbos[i],aux)==0)
            break;
        i++;
    }
    if(i==countVerbos)
        verbos[countVerbos++]=strdup(aux);
    ECHO;
    BEGIN INITIAL;
} 
<INTERROG>.|\n                    { ECHO ; BEGIN INITIAL ; }



[gG]onna[\.\?\!\;,\ \n\t]                    { printf("%coing to%c",yytext[0],yytext[strlen(yytext)-1]); }
[wW]anna[\.\?\!\;,\ \n\t]                    { printf("%cant to%c",yytext[0],yytext[strlen(yytext)-1]); }
[cC]an't[\.\?\!\;,\ \n\t]                    { printf("%cannot%c",yytext[0],yytext[strlen(yytext)-1]); }
[wW]on't[\.\?\!\;,\ \n\t]                    { printf("%cill not%c",yytext[0],yytext[strlen(yytext)-1]); }
I\ ain't[\.\?\!\;,\ \n\t]              { printf("I am not%c",yytext[strlen(yytext)-1]); }
([hH]e|[sS]he|[iI]t)\ ain't[\.\?\!\;,\ \n\t]  { printf("%s is not%c",strtok(yytext," "),yytext[strlen(yytext)-1]);}
([yY]ou|[tT]hey|[wW]e)\ ain't[\.\?\!\;,\ \n\t] { printf("%s are not%c",strtok(yytext," "),yytext[strlen(yytext)-1]);}
[tT]hat\ ain't[\.\?\!\;,\ \n\t]           { printf("%chat is not%c",yytext[0],yytext[strlen(yytext)-1]); }
[tT]his\ ain't[\.\?\!\;,\ \n\t]           { printf("%chis is not%c",yytext[0],yytext[strlen(yytext)-1]); }
\'[cC]ause[\.\?\!\;,\ \n\t]                  { printf("%cecause%c", yytext[1]-1,yytext[strlen(yytext)-1]); }
'm[\.\?\!\;,\ \n\t]                          { printf(" am%c",yytext[strlen(yytext)-1]); }
're[\.\?\!\;,\ \n\t]                         { printf(" are%c",yytext[strlen(yytext)-1]); }
n't[\.\?\!\;,\ \n\t]                         { printf(" not%c",yytext[strlen(yytext)-1]); }
'll[\.\?\!\;,\ \n\t]                         { printf(" will%c",yytext[strlen(yytext)-1]); }
've[\.\?\!\;,\ \n\t]                         { printf(" have%c",yytext[strlen(yytext)-1]); }
'd[\.\?\!\;,\ \n\t]                          { printf(" would%c",yytext[strlen(yytext)-1]); }
's[\.\?\!\;,\ \n\t]                          { printf(" [is/has]%c",yytext[strlen(yytext)-1]); }
%%
int main(){
    yylex();
    int i=0;printf("\n\n");
    FILE *fp;
    fp= fopen("verbos.html", "w");
    qsort (verbos, countVerbos, sizeof (const char *), compare);
    fprintf(fp,"<html><body><h1>verbos:%d</h1><ul>\n",countVerbos);
    for(i=0;i<countVerbos;i++)
        fprintf(fp,"<li>to %s</li>\n",verbos[i]);
    fprintf(fp,"</ul></body></html>");
    fclose(fp);
    return 1;
}



