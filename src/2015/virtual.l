%{
	#include "y.tab.h"
%}

%option yylineno

espaco 		[ \t]
inteiro 	[0-9]+
var 		[a-zA-Z_][a-zA-Z0-9_]*

vars 		(?i:VARS)
start		(?i:START)
end			(?i:END)
inc 		\+\+
dec			\-\-
addass 		\+\=
subass 		\-\=
mulass 		\*\=
divass 		\/\=
modass 		\%\=
scan 		(?i:SCAN) 
print 		(?i:PRINT) 
string 		\"([^"]|\\\")*\"
if 			(?i:IF) 
else 		(?i:ELSE) 
while 		(?i:WHILE) 

%%

[ \t\n] ;

[\(\)\[\]\{\}\;\=\"]	{ return yytext[0]; }

{vars}	{ return VARS; }
{start}	{ return START; }
{end}	{ return END; }

{inc} 		{ yylval.op = yytext[0]; return OPINCDEC; } 
{dec} 		{ yylval.op = yytext[0]; return OPINCDEC; }
{addass} 	{ yylval.op = yytext[0]; return OPASS; }
{subass} 	{ yylval.op = yytext[0]; return OPASS; }
{mulass} 	{ yylval.op = yytext[0]; return OPASS; }
{divass} 	{ yylval.op = yytext[0]; return OPASS; }
{modass} 	{ yylval.op = yytext[0]; return OPASS; }

{scan}	{ return SCAN; }
\n{espaco}*\>[^\n]*\n 	{
					yylval.string = strdup(strchr(yytext,'>')+1); 
					yylval.string[strchr(yylval.string,'\n')-yylval.string] = '\0'; 
					return INPUT; 
				}

{print}	{ return PRINT; }
{string} 	{	yylval.string = strdup(yytext+1); 
				yylval.string[strchr(yylval.string,'"')-yylval.string] = '\0';
				return STRING; 
			}

{if}  	{ return IF; }
{else} 	{ return ELSE; }
{while} { return WHILE; }

\!		{ return NOT; }
\>		{ yylval.op = yytext[0]; return OPREL; }
\<		{ yylval.op = yytext[0]; return OPREL; }

\>\=	{ yylval.op = yytext[0]; return OPRELEQ; }
\<\=	{ yylval.op = yytext[0]; return OPRELEQ; }
\=\=	{ yylval.op = yytext[0]; return OPRELEQ; }
\!\= 	{ yylval.op = yytext[0]; return OPRELEQ; }

\&\&	{ yylval.op = yytext[0]; return OPLOG; }
\|\| 	{ yylval.op = yytext[0]; return OPLOG; }

[\-\+] 	{ yylval.op = yytext[0]; return OPADD; } 
[\*\/\%] 	{ yylval.op = yytext[0]; return OPMUL; } 

{inteiro}	{ yylval.inteiro = atoi(yytext); return(INT); }
{var} 	{ yylval.var = strdup(yytext); return VAR; }

.	return ERRO;
