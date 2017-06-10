%{
	#include "y.tab.h"
%}

%option yylineno noyywrap


%%

[ \t\n] {;}

[()\[\]{}=";&]	{ return yytext[0]; }

\+\+		{ return OPINC; } 
\-\-		{ return OPDEC; }

\+\=		{ return OPATRMAIS; }
\-\=		{ return OPATRMENOS; }
\*\= 		{ return OPATRPROD; }
\/\= 		{ return OPATRDIV; }
\%\=		{ return OPATRMOD; }

(?i:ler)	{ return SCAN; }

(?i:escrever)	{ return PRINT; }
\"([^"]|\\\")*\" 	{ yylval.string=yytext;return STRING; }

(?i:se)   	{ return IF; }
(?i:senao) 	{ return ELSE; }
(?i:enquanto)  { return WHILE; }

\>\=	{ return OPGE; }
\<\=	{ return OPLE; }
\=\=	{ return OPEQ; }
\!\= 	{ return OPNE; }
\!		{ return NOT; }
\>		{ return OPGT; }
\<		{ return OPLT; }


\&\&	{ return OPAND; }
\|\| 	{ return OPOR; }

\-		{ return OPARITMENOS; } 
\+		{ return OPARITMAIS; } 
\*		{ return OPARITPROD; } 
\/		{ return OPARITDIV; } 
\%		{ return OPARITMOD; } 

[0-9]+	{ yylval.inteiro = atoi(yytext); return INT; }
[a-z_][a-zA-Z0-9_]* 	{ yylval.var = strdup(yytext);  return VAR; }

.	{return ERRO;}
