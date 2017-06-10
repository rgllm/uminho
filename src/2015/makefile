virtual : lex.yy.o y.tab.o
	gcc -o virtual y.tab.o lex.yy.o -ll

y.tab.o : y.tab.c
	gcc -c y.tab.c

y.tab.c y.tab.h : virtual.y estrutura.c estrutura.h
	yacc -d virtual.y 

lex.yy.o : lex.yy.c
	gcc -c lex.yy.c

lex.yy.c : virtual.l y.tab.h
	flex virtual.l