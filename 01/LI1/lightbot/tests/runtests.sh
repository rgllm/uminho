#!/bin/bash
#
# "runtests.sh <prefix> <cmd>" executa os testes com prefixo "<prefix>",
# utilizando para o efeito o comando "<cmd>".
# Cada caso de teste  e especificado com um par de ficheiros: a entrada
# com extensao ".in", e a saida esperada com extensao ".out". #

TESTS="${1}*.in"
for i in $TESTS;
do TEST=$(basename $i .in)
	if test -f $TEST.out
	then $2 < $TEST.in > $TEST.res
		DIFF=$(diff -q $TEST.res $TEST.out)
		if test "$DIFF" != ""
		then echo "ERRO NO TESTE $TEST! (comparar ficheiros $TEST.res e $TEST.out)"
		else echo "$TEST OK!"
		fi
else echo "Nao existe resultado para $TEST.in!!! Teste ignorado."
fi done