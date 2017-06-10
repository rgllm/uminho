
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
	char *filename;

	struct variavel *listaVariaveis = NULL, *auxVariavel = NULL;
	struct instrucao *listaInstrucoes = NULL;
	struct ifAddr *pilhaIfAddr = NULL;
	struct whileAddr *pilhaWhileAddr = NULL;
%}

%union
{ 
	int inteiro; 
	char op;
	char *var; 
	char *string; 
}
 
%token <inteiro> INT  
%token <var> VAR
%token <op> OPINCDEC OPASS OPLOG OPREL OPRELEQ OPADD OPMUL 
%token <string> STRING INPUT
%token VARS START END SCAN PRINT IF ELSE WHILE NOT ERRO

%left OPLOG
%left NOT OPREL OPRELEQ
%left OPADD
%left OPMUL 

%start Fonte

%%

Fonte : Cabeca Corpo
	  ;

//--- CABECA ---//
Cabeca : 
	   | VARS Declaracoes { listaInstrucoes = insertInstrucao(iAddr++,"START",listaInstrucoes); }
	   ;

Declaracoes : Declaracoes Declaracao
	        | Declaracao
       		;

Declaracao : VAR ';'
				{ 
					auxVariavel = malloc(sizeof(struct variavel));
		   			auxVariavel->nome = strdup($1);						

					if(!existeVariavel(auxVariavel->nome,listaVariaveis))
					{
						auxVariavel->tipo = strdup("inteiro");
			   			auxVariavel->endereco = gp;
			   			auxVariavel->inicializado = 0;
						listaVariaveis = insertVariavel(auxVariavel,listaVariaveis);
						listaInstrucoes = insertInstrucao(iAddr++,"PUSHI 0",listaInstrucoes);
						gp++;
					}
					else 
					{
						erros = 1;
						fprintf(stderr,"l.%d erro: variável '%s' já declarada\n",yylineno,$1);
					}
				}
		   | VAR '[' INT ']' ';'
		   		{
		   			auxVariavel = malloc(sizeof(struct variavel));
		   			auxVariavel->nome = strdup($1);	
		   			auxVariavel->tipo = strdup("array");
		   			auxVariavel->endereco = gp;
		   			auxVariavel->inicializado = 0;			
		   			
					if(!existeVariavel(auxVariavel->nome,listaVariaveis))
					{
						listaVariaveis = insertVariavel(auxVariavel,listaVariaveis); 
						char buf[20];
						sprintf(buf,"PUSHN %d",$3); 
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
Corpo : 
	  | START Instrucoes END { listaInstrucoes = insertInstrucao(iAddr++,"STOP",listaInstrucoes); } 
      ;

Instrucoes : Instrucoes Instrucao
		   | Instrucao 
		   ;

Instrucao : Atribuicao 
		  | Escrita 
		  | Leitura
		  | Condicional
		  | Ciclo
		  | ';' { listaInstrucoes = insertInstrucao(iAddr++,"NOP",listaInstrucoes);	 }
		  ;

//--- ATRIBUICAO ---//
Atribuicao : VAR '=' Exp ';' 
				{ 
					if(!existeVariavel($1,listaVariaveis))
					{
						erros = 1;
						fprintf(stderr,"l.%d erro: variável '%s' não declarada\n",yylineno,$1);
					}
					else if(strcmp(tipoVariavel($1,listaVariaveis),"inteiro") != 0)
					{
						erros = 1;
						fprintf(stderr,"l.%d erro: variável '%s' não é um escalar\n",yylineno,$1);
					}
					else
					{
						inicializaVariavel($1,listaVariaveis);

						char buf[20];
						sprintf(buf,"STOREG %d",enderecoVariavel($1,listaVariaveis)); 
						listaInstrucoes = insertInstrucao(iAddr++,buf,listaInstrucoes);
					}
					
				}

		   | VAR 
		   		{
		   			if(!existeVariavel($1,listaVariaveis))
					{
						erros = 1;
						fprintf(stderr,"l.%d erro: variável '%s' não declarada\n",yylineno,$1);
					}
					else if(strcmp(tipoVariavel($1,listaVariaveis),"array") != 0)
					{
						erros = 1;
						fprintf(stderr,"l.%d erro: variável '%s' não é um vector\n",yylineno,$1);
					}
					else
					{
						inicializaVariavel($1,listaVariaveis);

	   					char buf[20];
						sprintf(buf,"PUSHI %d",enderecoVariavel($1,listaVariaveis)); 
						listaInstrucoes = insertInstrucao(iAddr++,buf,listaInstrucoes); 
					}
				}
			'[' Exp ']' '=' Exp ';'
		   		{ 
					listaInstrucoes = insertInstrucao(iAddr++,"STOREN",listaInstrucoes);
		   		}

		   | VAR OPINCDEC ';'
		   		{
		   			if(!existeVariavel($1,listaVariaveis))
					{
						erros = 1;
						fprintf(stderr,"l.%d erro: variável '%s' não declarada\n",yylineno,$1);
					}
					else if(strcmp(tipoVariavel($1,listaVariaveis),"inteiro") != 0)
					{
						erros = 1;
						fprintf(stderr,"l.%d erro: variável '%s' não é um escalar\n",yylineno,$1);
					}
					else if(!variavelInicializada($1,listaVariaveis))
					{
						erros = 1;
						fprintf(stderr,"l.%d erro: variável '%s' não está inicializada\n",yylineno,$1);
					}
					else
					{
			   			char buf[20];
						sprintf(buf,"PUSHG %d",enderecoVariavel($1,listaVariaveis)); 
						listaInstrucoes = insertInstrucao(iAddr++,buf,listaInstrucoes);  
						listaInstrucoes = insertInstrucao(iAddr++,"PUSHI 1",listaInstrucoes); 

						if($2 == '+')  
							listaInstrucoes = insertInstrucao(iAddr++,"ADD",listaInstrucoes); 
						else if($2 == '-')
						  	listaInstrucoes = insertInstrucao(iAddr++,"SUB",listaInstrucoes);

						sprintf(buf,"STOREG %d",enderecoVariavel($1,listaVariaveis)); 
						listaInstrucoes = insertInstrucao(iAddr++,buf,listaInstrucoes);
					}
		   		}

		   | VAR 
		   		{
		   			if(!existeVariavel($1,listaVariaveis))
					{
						erros = 1;
						fprintf(stderr,"l.%d erro: variável '%s' não declarada\n",yylineno,$1);
					}
					else if(strcmp(tipoVariavel($1,listaVariaveis),"inteiro") != 0)
					{
						erros = 1;
						fprintf(stderr,"l.%d erro: variável '%s' não é um escalar\n",yylineno,$1);
					}
					else if(!variavelInicializada($1,listaVariaveis))
					{
						erros = 1;
						fprintf(stderr,"l.%d erro: variável '%s' não está inicializada\n",yylineno,$1);
					}
					else 
					{
						inicializaVariavel($1,listaVariaveis);

		   				char buf[20];
		   				sprintf(buf,"PUSHG %d",enderecoVariavel($1,listaVariaveis)); 
						listaInstrucoes = insertInstrucao(iAddr++,buf,listaInstrucoes);
					}
				}
		   	OPASS Exp ';'
		   		{
		   			if($3 == '+')  
		   				listaInstrucoes = insertInstrucao(iAddr++,"ADD",listaInstrucoes); 
		   			else if($3 == '-') 
		   				listaInstrucoes = insertInstrucao(iAddr++,"SUB",listaInstrucoes); 
		   			else if($3 == '*') 
		   				listaInstrucoes = insertInstrucao(iAddr++,"MUL",listaInstrucoes); 
		   			else if($3 == '/') 
		   				listaInstrucoes = insertInstrucao(iAddr++,"DIV",listaInstrucoes); 
		   			else if($3 == '%') 
		   				listaInstrucoes = insertInstrucao(iAddr++,"MOD",listaInstrucoes); 

		   			char buf[20];	
		   			sprintf(buf,"STOREG %d",enderecoVariavel($1,listaVariaveis)); 
					listaInstrucoes = insertInstrucao(iAddr++,buf,listaInstrucoes);
		   		}
		   ;

//--- LEITURA ---//
Leitura : SCAN VAR ';' INPUT
			{
				if(!existeVariavel($2,listaVariaveis))
				{
					erros = 1;
					fprintf(stderr,"l.%d erro: variável '%s' não declarada\n",yylineno,$2);
				}
				else if(strcmp(tipoVariavel($2,listaVariaveis),"inteiro") != 0)
				{
					erros = 1;
					fprintf(stderr,"l.%d erro: variável '%s' não é um escalar\n",yylineno,$2);
				}
				else 
				{	
					inicializaVariavel($2,listaVariaveis);

					char buf[1024];
					sprintf(buf,"READ \"%s\"",$4); 
					listaInstrucoes = insertInstrucao(iAddr++,buf,listaInstrucoes);	
					listaInstrucoes = insertInstrucao(iAddr++,"ATOI",listaInstrucoes);	

					sprintf(buf,"STOREG %d",enderecoVariavel($2,listaVariaveis)); 
					listaInstrucoes = insertInstrucao(iAddr++,buf,listaInstrucoes);	
				}
			}

		| SCAN VAR 
			{
				if(!existeVariavel($2,listaVariaveis))
				{
					erros = 1;
					fprintf(stderr,"l.%d erro: variável '%s' não declarada\n",yylineno,$2);
				}
				else if(strcmp(tipoVariavel($2,listaVariaveis),"array") != 0)
				{
					erros = 1;
					fprintf(stderr,"l.%d erro: variável '%s' não é um vector\n",yylineno,$2);
				}
				else 
				{
					inicializaVariavel($2,listaVariaveis);

	   				char buf[1024];
					sprintf(buf,"PUSHI %d",enderecoVariavel($2,listaVariaveis)); 
					listaInstrucoes = insertInstrucao(iAddr++,buf,listaInstrucoes);
				}  
			}
		'[' Exp ']' ';' INPUT
			{
				char buf[20];
				sprintf(buf,"READ \"%s\"",$8); 
				listaInstrucoes = insertInstrucao(iAddr++,buf,listaInstrucoes);	
				listaInstrucoes = insertInstrucao(iAddr++,"ATOI",listaInstrucoes);	
				listaInstrucoes = insertInstrucao(iAddr++,"STOREN",listaInstrucoes);
			}

		;

//--- ESCRITA ---//
Escrita : PRINT Exp ';' 
			{ 
				listaInstrucoes = insertInstrucao(iAddr++,"WRITEI",listaInstrucoes); 
			}

		| PRINT STRING ';' 
			{ 				
				char buf[1024];
				sprintf(buf,"PUSHS \"%s\"",$2); 
				listaInstrucoes = insertInstrucao(iAddr++,buf,listaInstrucoes);			
				listaInstrucoes = insertInstrucao(iAddr++,"WRITES",listaInstrucoes);
			}
		;


//--- CONDICIONAL IF ---//
Condicional : IF '(' Exp ')' 
				{ 
					pilhaIfAddr = pushIfAddr(iAddr,pilhaIfAddr); 
					listaInstrucoes = insertInstrucao(iAddr++,"if",listaInstrucoes); 
				} 
			'{' Instrucoes '}'  
				{ 
					ifJump(iAddr+1,pilhaIfAddr,listaInstrucoes); 
					pilhaIfAddr = popIfAddr(pilhaIfAddr);
				}
			Else
			;

Else : ELSE 
		{  
			pilhaIfAddr = pushIfAddr(iAddr,pilhaIfAddr); 
			listaInstrucoes = insertInstrucao(iAddr++,"else",listaInstrucoes); 
		} 
		'{' Instrucoes '}'
		{
			elseJump(iAddr,pilhaIfAddr,listaInstrucoes); 
			pilhaIfAddr = popIfAddr(pilhaIfAddr);
		}
	 |  
	 ;


//--- CICLO WHILE ---//
Ciclo : WHILE '(' 
			{ 
				whileAddr = iAddr; 
			} 
		Exp ')' 
			{ 
				pilhaWhileAddr = pushWhileAddr(iAddr,whileAddr,pilhaWhileAddr); 
				listaInstrucoes = insertInstrucao(iAddr++,"while",listaInstrucoes); 
			} 
		'{' Instrucoes 
			{ 
				int jump = whileJump(iAddr+1,pilhaWhileAddr,listaInstrucoes); 
				pilhaWhileAddr = popWhileAddr(pilhaWhileAddr);

				char buf[20]; 
				sprintf(buf,"JUMP %d",jump); 
				listaInstrucoes = insertInstrucao(iAddr++,buf,listaInstrucoes); 
			}
		'}' 
	  ;

//--- EXPRESSAO ---//
Exp : NOT Exp 
		{ 
			listaInstrucoes = insertInstrucao(iAddr++,"NOT",listaInstrucoes);
		}

	| Exp OPREL Exp 
		{ 
			if($2 == '>')
				listaInstrucoes = insertInstrucao(iAddr++,"SUP",listaInstrucoes);
			else if($2 == '<')
				listaInstrucoes = insertInstrucao(iAddr++,"INF",listaInstrucoes);
		}

	| Exp OPRELEQ Exp 
		{ 
			if($2 == '>') 
				listaInstrucoes = insertInstrucao(iAddr++,"SUPEQ",listaInstrucoes);
			if($2 == '<') 
				listaInstrucoes = insertInstrucao(iAddr++,"INFEQ",listaInstrucoes);
			else if($2 == '=')
				listaInstrucoes = insertInstrucao(iAddr++,"EQUAL",listaInstrucoes);
			else if($2 == '!')
			{
				listaInstrucoes = insertInstrucao(iAddr++,"EQUAL",listaInstrucoes);
				listaInstrucoes = insertInstrucao(iAddr++,"NOT",listaInstrucoes);
			}
		}

	| Exp OPLOG Exp 
		{
			if($2 == '&') 
				listaInstrucoes = insertInstrucao(iAddr++,"MUL",listaInstrucoes);
			if($2 == '|') 
				listaInstrucoes = insertInstrucao(iAddr++,"ADD",listaInstrucoes);
		}

	| Exp OPADD Exp
		{
			if($2 == '+') 
				listaInstrucoes = insertInstrucao(iAddr++,"ADD",listaInstrucoes); 
			if($2 == '-') 
				listaInstrucoes = insertInstrucao(iAddr++,"SUB",listaInstrucoes); 	
		}

	| Exp OPMUL Exp
		{
			if($2 == '*') 
				listaInstrucoes = insertInstrucao(iAddr++,"MUL",listaInstrucoes); 
			if($2 == '/') 
				listaInstrucoes = insertInstrucao(iAddr++,"DIV",listaInstrucoes);  
			if($2 == '%') 
				listaInstrucoes = insertInstrucao(iAddr++,"MOD",listaInstrucoes);  
		}

	| VAR 
		{ 
			if(!existeVariavel($1,listaVariaveis))
			{
				erros = 1;
				fprintf(stderr,"l.%d erro: variável '%s' não declarada\n",yylineno,$1);
			}
			else if(strcmp(tipoVariavel($1,listaVariaveis),"inteiro") != 0)
			{
				erros = 1;
				fprintf(stderr,"l.%d erro: variável '%s' não é um escalar\n",yylineno,$1);
			}
			else if(!variavelInicializada($1,listaVariaveis))
			{
				erros = 1;
				fprintf(stderr,"l.%d erro: variável '%s' não está inicializada\n",yylineno,$1);
			}
			else 
			{
				char buf[20];
				sprintf(buf,"PUSHG %d",enderecoVariavel($1,listaVariaveis)); 
				listaInstrucoes = insertInstrucao(iAddr++,buf,listaInstrucoes);  
			}
		}

	| VAR 
		{	
			if(!existeVariavel($1,listaVariaveis))
			{
				erros = 1;
				fprintf(stderr,"l.%d erro: variável '%s' não declarada\n",yylineno,$1);
			}
			else if(strcmp(tipoVariavel($1,listaVariaveis),"array") != 0)
			{
				erros = 1;
				fprintf(stderr,"l.%d erro: variável '%s' não é um vector\n",yylineno,$1);
			}
			else if(!variavelInicializada($1,listaVariaveis))
			{
				erros = 1;
				fprintf(stderr,"l.%d erro: variável '%s' não está inicializada\n",yylineno,$1);
			}
			else 
			{
   				char buf[20];
				sprintf(buf,"PUSHI %d",enderecoVariavel($1,listaVariaveis)); 
				listaInstrucoes = insertInstrucao(iAddr++,buf,listaInstrucoes);	
			}
		}
	'[' Exp ']'
		{
			listaInstrucoes = insertInstrucao(iAddr++,"LOADN",listaInstrucoes);
		}

	| INT 
		{ 
			char buf[20];
			sprintf(buf,"PUSHI %d",$1); 
			listaInstrucoes = insertInstrucao(iAddr++,buf,listaInstrucoes);
		}

	| '(' Exp ')'
	;


%%

void yyerror(char* s)
{
   fprintf(stderr,"erro: %d: %s: %s\n",yylineno,s,yytext);
   erros = 1;
}

int main(int argc, char *argv[])
{
	if(argc < 2) 
	{ 
		printf("Introduzir ficheiro para compilação como argumento!\n"); 
		exit(0); 
	}

	//--- PARSE ---//
	yyin = fopen(argv[1],"r");
   	yyparse();

   	//--- IMPRIMR OUTPUT ---//
   	filename = strdup(argv[1]);
	filename[strrchr(filename,'.')-filename] = '\0';
	FILE* f = fopen(filename,"w");

	if(!erros)
	{
		while(listaInstrucoes)
		{
			if(listaInstrucoes->instrucao)
			{
				fprintf(f,"_%06d: %s\n",listaInstrucoes->endereco,listaInstrucoes->instrucao);
				//printf("_%06d: %s\n",listaInstrucoes->endereco,listaInstrucoes->instrucao);
			}
			listaInstrucoes = listaInstrucoes->next;
		}
	}
	else 
	{
		fprintf(f,"Houve erros durante a compilação\n");
	}

   	return 0;
}
