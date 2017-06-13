
%{

	#include "estrutura.c"
	extern int yylex();
	extern int yylineno;
	extern FILE *yyin;	
	extern char *yytext;
	extern void yyerror(char *s); 

	int gp = 0;
	int iAddr = 0;
	int whileAddr = 0;
	int erros = 0;
	int ifs=0 , elses=0 , whiles=0;
	char *filename;

	struct variavel *listaVariaveis = NULL, *auxVariavel = NULL;
	struct instrucao *listaInstrucoes = NULL;
	struct ifAddr *pilhaIfAddr = NULL;
	struct whileAddr *pilhaWhileAddr = NULL;

%}
%union
{ 
	int inteiro; 
	char op[2];
	char *var; 
	char *string; 
}
%token <inteiro>INT
%token <var>VAR
%token <op>OPINC OPDEC OPATRMAIS OPATRMENOS OPATRPROD OPATRDIV OPATRMOD OPAND OPOR OPGT OPLT OPGE OPLE OPEQ OPNE  
%token <op>OPARITMAIS OPARITMENOS OPARITPROD OPARITDIV OPARITMOD
%token <string>STRING
%token SCAN PRINT IF ELSE NOT ERRO
%token <inteiro>WHILE


%left OPAND OPOR
%left NOT OPGT OPLT OPGE OPLE OPEQ OPNE 
%left OPARITMAIS OPARITMENOS
%left OPARITPROD OPARITDIV OPARITMOD

%start Programa


%%

Programa : Declaracoes { listaInstrucoes = insertInstrucao(iAddr++,"start",listaInstrucoes); }
			'&' Instrucoes { listaInstrucoes = insertInstrucao(iAddr++,"stop",listaInstrucoes); }
	  	 ;

//--- CABECA ---//

Declaracoes : Declaracoes Declaracao
			| 
       		;

Declaracao : VAR ';' { 
	//printf("reconheceu declaração de inteiro\n");
					auxVariavel = malloc(sizeof(struct variavel));
		   			auxVariavel->nome = strdup($1);						

					if(!existeVariavel(auxVariavel->nome,listaVariaveis)){
						auxVariavel->tipo = strdup("inteiro");
			   			auxVariavel->endereco = gp;
						listaVariaveis = insertVariavel(auxVariavel,listaVariaveis);
						listaInstrucoes = insertInstrucao(iAddr++,"\t\tpushi 0",listaInstrucoes);
						gp++;
					}
					else 
					{
						erros = 1;
						fprintf(stderr,"l.%d erro: variável '%s' já declarada\n",yylineno,$1);
					}
				}
		   | VAR '[' INT ']' ';' {
	//printf("reconheceu declaração de array\n");
		   			auxVariavel = malloc(sizeof(struct variavel));
		   			auxVariavel->nome = strdup($1);	
		   			auxVariavel->tipo = strdup("array");
		   			auxVariavel->endereco = gp;
		   			
					if(!existeVariavel(auxVariavel->nome,listaVariaveis)){
						listaVariaveis = insertVariavel(auxVariavel,listaVariaveis); 
						char buf[20];
						sprintf(buf,"\t\tpushn %d",$3); 
						listaInstrucoes = insertInstrucao(iAddr++,buf,listaInstrucoes);
						gp += $3;
					}
					else 
					{
						erros = 1;
						fprintf(stderr,"l.%d erro: variável '%s' já declarada\n",yylineno,$1);
					}
		   		}
		   ;

		   //--- CORPO ---//
Instrucoes : Instrucoes Instrucao
		   | Instrucao
		   ;

Instrucao : Atribuicao 
		  | Escrita 
		  | Leitura
		  | Condicional
		  | Ciclo
		  ;

//--- ATRIBUICAO ---//
Atribuicao : VAR '=' Exp ';' { 
	//printf("reconheceu atribuição de inteiro\n");
					if(!existeVariavel($1,listaVariaveis)){
						erros = 1;
						fprintf(stderr,"l.%d erro: variável '%s' não declarada\n",yylineno,$1);
					}
					else if(strcmp(tipoVariavel($1,listaVariaveis),"inteiro") != 0){
						erros = 1;
						fprintf(stderr,"l.%d erro: variável '%s' não é um escalar\n",yylineno,$1);
					}
					else{
						char buf[20];
						sprintf(buf,"\t\tstoreg %d",enderecoVariavel($1,listaVariaveis)); 
						listaInstrucoes = insertInstrucao(iAddr++,buf,listaInstrucoes);
					}
				}
		   | VAR {
		   			if(!existeVariavel($1,listaVariaveis)){
						erros = 1;
						fprintf(stderr,"l.%d erro: variável '%s' não declarada\n",yylineno,$1);
					}
					else if(strcmp(tipoVariavel($1,listaVariaveis),"array") != 0){
						erros = 1;
						fprintf(stderr,"l.%d erro: variável '%s' não é um vector\n",yylineno,$1);
					}
					else{
	   					char buf[20];
						sprintf(buf,"\t\tpushgp\n\t\tpushi %d\n\t\tpadd ",enderecoVariavel($1,listaVariaveis)); 
						listaInstrucoes = insertInstrucao(iAddr++,buf,listaInstrucoes); 
					}
				}
			'[' Exp ']' '=' Exp ';'{ 
	//printf("reconheceu atribuição de array\n");
						listaInstrucoes = insertInstrucao(iAddr++,"\t\tstoren",listaInstrucoes);
					}
					

		   | VAR OPINC ';'{
		   			if(!existeVariavel($1,listaVariaveis)){
						erros = 1;
						fprintf(stderr,"l.%d erro: variável '%s' não declarada\n",yylineno,$1);
					}
					else if(strcmp(tipoVariavel($1,listaVariaveis),"inteiro") != 0){
						erros = 1;
						fprintf(stderr,"l.%d erro: variável '%s' não é um escalar\n",yylineno,$1);
					}
					else{
			   			char buf[20];
						sprintf(buf,"\t\tpushg %d",enderecoVariavel($1,listaVariaveis)); 
						listaInstrucoes = insertInstrucao(iAddr++,buf,listaInstrucoes);  
						listaInstrucoes = insertInstrucao(iAddr++,"\t\tpushi 1",listaInstrucoes); 
						listaInstrucoes = insertInstrucao(iAddr++,"\t\tadd",listaInstrucoes); 

						sprintf(buf,"\t\tstoreg %d",enderecoVariavel($1,listaVariaveis)); 
						listaInstrucoes = insertInstrucao(iAddr++,buf,listaInstrucoes);
					}
		   		}
		   | VAR OPDEC ';'{
		   			if(!existeVariavel($1,listaVariaveis)){
						erros = 1;
						fprintf(stderr,"l.%d erro: variável '%s' não declarada\n",yylineno,$1);
					}
					else if(strcmp(tipoVariavel($1,listaVariaveis),"inteiro") != 0){
						erros = 1;
						fprintf(stderr,"l.%d erro: variável '%s' não é um escalar\n",yylineno,$1);
					}
					else{
			   			char buf[20];
						sprintf(buf,"\t\tpushg %d",enderecoVariavel($1,listaVariaveis)); 
						listaInstrucoes = insertInstrucao(iAddr++,buf,listaInstrucoes);  
						listaInstrucoes = insertInstrucao(iAddr++,"\t\tpushi 1",listaInstrucoes); 
						listaInstrucoes = insertInstrucao(iAddr++,"\t\tsub",listaInstrucoes); 

						sprintf(buf,"\t\tstoreg %d",enderecoVariavel($1,listaVariaveis)); 
						listaInstrucoes = insertInstrucao(iAddr++,buf,listaInstrucoes);
					}
		   		}
   		   | VAR {
		   			if(!existeVariavel($1,listaVariaveis)){
						erros = 1;
						fprintf(stderr,"l.%d erro: variável '%s' não declarada\n",yylineno,$1);
					}
					else if(strcmp(tipoVariavel($1,listaVariaveis),"inteiro") != 0){
						erros = 1;
						fprintf(stderr,"l.%d erro: variável '%s' não é um escalar\n",yylineno,$1);
					}
					else {
		   				char buf[20];
		   				sprintf(buf,"\t\tpushg %d",enderecoVariavel($1,listaVariaveis)); 
						listaInstrucoes = insertInstrucao(iAddr++,buf,listaInstrucoes);
					}
				}
			OPATRMAIS Exp ';'{
		   			listaInstrucoes = insertInstrucao(iAddr++,"\t\tadd",listaInstrucoes); 
		   			char buf[20];	
		   			sprintf(buf,"\t\tstoreg %d",enderecoVariavel($1,listaVariaveis)); 
					listaInstrucoes = insertInstrucao(iAddr++,buf,listaInstrucoes);
		   		}
   		   | VAR {
		   			if(!existeVariavel($1,listaVariaveis)){
						erros = 1;
						fprintf(stderr,"l.%d erro: variável '%s' não declarada\n",yylineno,$1);
					}
					else if(strcmp(tipoVariavel($1,listaVariaveis),"inteiro") != 0){
						erros = 1;
						fprintf(stderr,"l.%d erro: variável '%s' não é um escalar\n",yylineno,$1);
					}
					else {
		   				char buf[20];
		   				sprintf(buf,"\t\tpushg %d",enderecoVariavel($1,listaVariaveis)); 
						listaInstrucoes = insertInstrucao(iAddr++,buf,listaInstrucoes);
					}
				}
			OPATRMENOS Exp ';'{
		   			listaInstrucoes = insertInstrucao(iAddr++,"sub",listaInstrucoes); 
		   			char buf[20];	
		   			sprintf(buf,"\t\tstoreg %d",enderecoVariavel($1,listaVariaveis)); 
					listaInstrucoes = insertInstrucao(iAddr++,buf,listaInstrucoes);
		   		}
   		   | VAR {
		   			if(!existeVariavel($1,listaVariaveis)){
						erros = 1;
						fprintf(stderr,"l.%d erro: variável '%s' não declarada\n",yylineno,$1);
					}
					else if(strcmp(tipoVariavel($1,listaVariaveis),"inteiro") != 0){
						erros = 1;
						fprintf(stderr,"l.%d erro: variável '%s' não é um escalar\n",yylineno,$1);
					}
					else {
		   				char buf[20];
		   				sprintf(buf,"\t\tpushg %d",enderecoVariavel($1,listaVariaveis)); 
						listaInstrucoes = insertInstrucao(iAddr++,buf,listaInstrucoes);
					}
				}
			OPATRPROD Exp ';'{
		   			listaInstrucoes = insertInstrucao(iAddr++,"\t\tmul",listaInstrucoes); 
		   			char buf[20];	
		   			sprintf(buf,"\t\tstoreg %d",enderecoVariavel($1,listaVariaveis)); 
					listaInstrucoes = insertInstrucao(iAddr++,buf,listaInstrucoes);
		   		}
   		   | VAR {
		   			if(!existeVariavel($1,listaVariaveis)){
						erros = 1;
						fprintf(stderr,"l.%d erro: variável '%s' não declarada\n",yylineno,$1);
					}
					else if(strcmp(tipoVariavel($1,listaVariaveis),"inteiro") != 0){
						erros = 1;
						fprintf(stderr,"l.%d erro: variável '%s' não é um escalar\n",yylineno,$1);
					}
					else {
		   				char buf[20];
		   				sprintf(buf,"\t\tpushg %d",enderecoVariavel($1,listaVariaveis)); 
						listaInstrucoes = insertInstrucao(iAddr++,buf,listaInstrucoes);
					}
				}
			OPATRDIV Exp ';'{
		   			listaInstrucoes = insertInstrucao(iAddr++,"div",listaInstrucoes); 
		   			char buf[20];	
		   			sprintf(buf,"\t\tstoreg %d",enderecoVariavel($1,listaVariaveis)); 
					listaInstrucoes = insertInstrucao(iAddr++,buf,listaInstrucoes);
		   		}
   		   | VAR {
		   			if(!existeVariavel($1,listaVariaveis)){
						erros = 1;
						fprintf(stderr,"l.%d erro: variável '%s' não declarada\n",yylineno,$1);
					}
					else if(strcmp(tipoVariavel($1,listaVariaveis),"inteiro") != 0){
						erros = 1;
						fprintf(stderr,"l.%d erro: variável '%s' não é um escalar\n",yylineno,$1);
					}
					else {
		   				char buf[20];
		   				sprintf(buf,"\t\tpushg %d",enderecoVariavel($1,listaVariaveis)); 
						listaInstrucoes = insertInstrucao(iAddr++,buf,listaInstrucoes);
					}
				}
			OPATRMOD Exp ';'{
		   			listaInstrucoes = insertInstrucao(iAddr++,"mod",listaInstrucoes); 
		   			char buf[20];	
		   			sprintf(buf,"\t\tstoreg %d",enderecoVariavel($1,listaVariaveis)); 
					listaInstrucoes = insertInstrucao(iAddr++,buf,listaInstrucoes);
		   		}
		   ;

//--- LEITURA ---//
Leitura : SCAN VAR ';' {
				if(!existeVariavel($2,listaVariaveis)){
					erros = 1;
					fprintf(stderr,"l.%d erro: variável '%s' não declarada\n",yylineno,$2);
				}
				else if(strcmp(tipoVariavel($2,listaVariaveis),"inteiro") != 0){
					erros = 1;
					fprintf(stderr,"l.%d erro: variável '%s' não é um escalar\n",yylineno,$2);
				}
				else{	
					char buf[1024];
					sprintf(buf,"\t\tread"); 
					listaInstrucoes = insertInstrucao(iAddr++,buf,listaInstrucoes);	
					listaInstrucoes = insertInstrucao(iAddr++,"\t\tatoi",listaInstrucoes);	

					sprintf(buf,"\t\tstoreg %d",enderecoVariavel($2,listaVariaveis)); 
					listaInstrucoes = insertInstrucao(iAddr++,buf,listaInstrucoes);	
				}
			}
		| SCAN VAR {
				if(!existeVariavel($2,listaVariaveis)){
					erros = 1;
					fprintf(stderr,"l.%d erro: variável '%s' não declarada\n",yylineno,$2);
				}
				else if(strcmp(tipoVariavel($2,listaVariaveis),"array") != 0){
					erros = 1;
					fprintf(stderr,"l.%d erro: variável '%s' não é um vector\n",yylineno,$2);
				}
				else {
					listaInstrucoes = insertInstrucao(iAddr++,"\t\tpushgp",listaInstrucoes);
	   				char buf[20];

					sprintf(buf,"\t\tpushi %d",enderecoVariavel($2,listaVariaveis)); 
					listaInstrucoes = insertInstrucao(iAddr++,buf,listaInstrucoes);
					listaInstrucoes = insertInstrucao(iAddr++,"\t\tpadd",listaInstrucoes);
				}  
			}
		'[' Exp ']' ';'
			{
				listaInstrucoes = insertInstrucao(iAddr++,"\t\tread",listaInstrucoes);	
				listaInstrucoes = insertInstrucao(iAddr++,"\t\tatoi",listaInstrucoes);	
				listaInstrucoes = insertInstrucao(iAddr++,"\t\tstoren",listaInstrucoes);
			}
		;

//--- ESCRITA ---//
Escrita : PRINT Exp ';' { 
				listaInstrucoes = insertInstrucao(iAddr++,"\t\twritei",listaInstrucoes); 
			}
		| PRINT STRING ';'{ 				
				char buf[1024];
				sprintf(buf,"\t\tpushs %s",$2); 
				listaInstrucoes = insertInstrucao(iAddr++,buf,listaInstrucoes);			
				listaInstrucoes = insertInstrucao(iAddr++,"\t\twrites",listaInstrucoes);
			} 
		;

//--- CONDICIONAL IF ---//
Condicional : IF '(' Exp ')' {

					pilhaIfAddr = pushIfAddr(iAddr,pilhaIfAddr); 
					listaInstrucoes = insertInstrucao(iAddr++,"\t\tif",listaInstrucoes); 
				} 
			 '{' Instrucoes '}' { 
					ifJump(ifs,pilhaIfAddr,listaInstrucoes); 
					pilhaIfAddr = popIfAddr(pilhaIfAddr);
					
				}
			  Else
			;

Else : ELSE {  
				pilhaIfAddr = pushIfAddr(iAddr,pilhaIfAddr); 
				listaInstrucoes = insertInstrucao(iAddr++,"else",listaInstrucoes); 

				elseJump(elses,pilhaIfAddr,listaInstrucoes);

				char buf1[20];
				sprintf(buf1,"if%d:",ifs++);
				listaInstrucoes = insertInstrucao(iAddr++,buf1,listaInstrucoes);
			} 
		'{' Instrucoes '}' {
				pilhaIfAddr = popIfAddr(pilhaIfAddr);
				char buf[20];
				sprintf(buf,"else%d:",elses++);
				listaInstrucoes = insertInstrucao(iAddr++,buf,listaInstrucoes);
			}
	 |  {		char buf1[20];
				sprintf(buf1,"if%d:",ifs++);
				listaInstrucoes = insertInstrucao(iAddr++,buf1,listaInstrucoes);}
	 ;

//--- CICLO WHILE ---//
Ciclo : WHILE '(' { 
				whileAddr = iAddr;
				char buf[20];
				$1=whiles;
				whiles+=2;
				sprintf(buf,"while%d:",$1);
				listaInstrucoes = insertInstrucao(iAddr++,buf,listaInstrucoes); 
			}
		 Exp ')' { 

				pilhaWhileAddr = pushWhileAddr(iAddr,whileAddr,pilhaWhileAddr); 
				listaInstrucoes = insertInstrucao(iAddr++,"while",listaInstrucoes); 
			}
	 	'{' Instrucoes { 
				int jump = whileJump($1+1,pilhaWhileAddr,listaInstrucoes); 
				pilhaWhileAddr = popWhileAddr(pilhaWhileAddr);

				char buf[20]; 
				sprintf(buf,"\t\tjump while%d",$1); 
				listaInstrucoes = insertInstrucao(iAddr++,buf,listaInstrucoes);
				char buf1[20];
				sprintf(buf1,"while%d:",$1+1);
				listaInstrucoes = insertInstrucao(iAddr++,buf1,listaInstrucoes);
			}
		'}' 
	  ;

//--- EXPRESSAO ---//
Exp : NOT Exp { listaInstrucoes = insertInstrucao(iAddr++,"\t\tpushi 0\n\t\tequal",listaInstrucoes); }
	| Exp OPLT Exp { listaInstrucoes = insertInstrucao(iAddr++,"\t\tinf",listaInstrucoes); }
	| Exp OPGT Exp { listaInstrucoes = insertInstrucao(iAddr++,"\t\tsup",listaInstrucoes); }
	| Exp OPLE Exp { listaInstrucoes = insertInstrucao(iAddr++,"\t\tinfeq",listaInstrucoes); }
	| Exp OPGE Exp { listaInstrucoes = insertInstrucao(iAddr++,"\t\tsupeq",listaInstrucoes); }
	| Exp OPEQ Exp { listaInstrucoes = insertInstrucao(iAddr++,"\t\tequal",listaInstrucoes); }
	| Exp OPNE Exp { listaInstrucoes = insertInstrucao(iAddr++,"\t\tequal",listaInstrucoes);
					 listaInstrucoes = insertInstrucao(iAddr++,"\t\tpushi 0\n\t\tequal",listaInstrucoes); }
	| Exp OPAND Exp { listaInstrucoes = insertInstrucao(iAddr++,"\t\tmul",listaInstrucoes);	}
	| Exp OPOR Exp { listaInstrucoes = insertInstrucao(iAddr++,"\t\tadd",listaInstrucoes); }
	| Exp OPARITMAIS Exp { listaInstrucoes = insertInstrucao(iAddr++,"\t\tadd",listaInstrucoes); }
	| Exp OPARITMENOS Exp { listaInstrucoes = insertInstrucao(iAddr++,"\t\tsub",listaInstrucoes); }
	| Exp OPARITPROD Exp { listaInstrucoes = insertInstrucao(iAddr++,"\t\tmul",listaInstrucoes); }
	| Exp OPARITDIV Exp { listaInstrucoes = insertInstrucao(iAddr++,"\t\tdiv",listaInstrucoes); }
	| Exp OPARITMOD Exp { listaInstrucoes = insertInstrucao(iAddr++,"\t\tmod",listaInstrucoes); }
	| VAR { 
			if(!existeVariavel($1,listaVariaveis)){
				erros = 1;
				fprintf(stderr,"l.%d erro: variável '%s' não declarada\n",yylineno,$1);
			}
			else if(strcmp(tipoVariavel($1,listaVariaveis),"inteiro") != 0){
				erros = 1;
				fprintf(stderr,"l.%d erro: variável '%s' não é um escalar\n",yylineno,$1);
			}
			else {
				char buf[20];
				sprintf(buf,"\t\tpushg %d",enderecoVariavel($1,listaVariaveis)); 
				listaInstrucoes = insertInstrucao(iAddr++,buf,listaInstrucoes);  
			}
		}
	| VAR {	
			if(!existeVariavel($1,listaVariaveis)){
				erros = 1;
				fprintf(stderr,"l.%d erro: variável '%s' não declarada\n",yylineno,$1);
			}
			else if(strcmp(tipoVariavel($1,listaVariaveis),"array") != 0){
				erros = 1;
				fprintf(stderr,"l.%d erro: variável '%s' não é um vector\n",yylineno,$1);
			}
			else {
   				char buf[20];
				sprintf(buf,"\t\tpushgp\n\t\tpushi %d\n\t\tpadd",enderecoVariavel($1,listaVariaveis)); 
				listaInstrucoes = insertInstrucao(iAddr++,buf,listaInstrucoes);	
			}
		}
	'[' Exp ']'{
			listaInstrucoes = insertInstrucao(iAddr++,"\t\tloadn",listaInstrucoes);
		}

	| INT { 
			char buf[20];
			sprintf(buf,"\t\tpushi %d",$1); 
			listaInstrucoes = insertInstrucao(iAddr++,buf,listaInstrucoes);
		}
	| '(' Exp ')'
	;

%%
#include "lex.yy.c"

void yyerror(char* s){
   fprintf(stderr,"erro: %d: %s: %s\n",yylineno,s,yytext);
   erros = 1;
}

int main(int argc, char *argv[]){
	if(argc < 2){ 
		printf("Introduzir ficheiro para compilação como argumento!\n"); 
		exit(0); 
	}

	//--- PARSE ---//
	yyin = fopen(argv[1],"r");
   	yyparse();

   	//--- IMPRIMR OUTPUT ---//
   	filename = strdup(argv[1]);
	filename[strrchr(filename,'.')-filename] = '\0';
	strcat(filename,".vm");
	FILE* f = fopen(filename,"w");
	if(!erros){
		while(listaInstrucoes){
			if(listaInstrucoes->instrucao){
				fprintf(f,"%s\n",listaInstrucoes->instrucao);
				//printf("%s\n",listaInstrucoes->instrucao);
			}
			listaInstrucoes = listaInstrucoes->next;
		}
	}
	else {
		printf("Houve erros durante a compilação\n");
	}
	fclose(f);
   	return 0;
}
