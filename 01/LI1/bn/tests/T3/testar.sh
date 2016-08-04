#!/bin/bash

bn=$1

if test -z $bn
then
    echo "Uso: $0 executavel"
    exit
fi

if test ! -x $bn
then
    echo "Ficheiro $bn nao e um executavel"
    exit
fi

> resultado.tst
declare -A rok
declare -A rno

for t in l e V D E1 E2 E3
do
    let "rok[$t] = 0"
    let "rno[$t] = 0"
done

echo "Comando		Ok	No	Percentagem"

for i in V_*.in
do
    echo -n ${i%%.in} >> resultado.tst
    echo -n ' ' >> resultado.tst
    { $bn < $i; } >res 2> /dev/null
    if diff res ${i%%.in}.out &> /dev/null
    then
        echo ok >> resultado.tst
        let "rok[V] = ${rok[V]} + 1"
    else
        echo no >> resultado.tst
        let "rno[V] = ${rno[V]} + 1"
    fi
    rm res
done

for i in [leD]_*.in E[1-3]_*.in
do
    tst=`echo $i | cut -f1 -d_`
    echo -n ${i%%.in} >> resultado.tst
    echo -n ' ' >> resultado.tst
    { $bn < $i; } &> /dev/null
    if diff res ${i%%.in}.out &> /dev/null
    then
        echo ok >> resultado.tst
        let "rok[$tst] = ${rok[$tst]} + 1"
    else
        echo no >> resultado.tst
        let "rno[$tst] = ${rno[$tst]} + 1"
    fi
    rm res
done

for t in l e V D E1 E2 E3
do
    perc=`echo "scale=2; ${rok[$t]} / (${rok[$t]} + ${rno[$t]})" | bc -l`
    echo "$t		${rok[$t]}	${rno[$t]}	$perc"
done

