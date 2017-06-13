gramatica: lex.yy.c y.tab.c
	gcc -o gramatica y.tab.c
	rm lex.yy.c
	rm *.tab.*
	rm y.output

y.tab.c: gramatica.y
	yacc -d -v gramatica.y

lex.yy.c: gramatica.l
	flex gramatica.l
