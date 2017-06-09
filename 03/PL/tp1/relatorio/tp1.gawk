@include "gets.gawk"
BEGIN {
    RS="<TRANSACCAO>[ \t\n]*";
    FS="\n";
}
NR>1 {
    soma ++;
    # calcular o numero de 'entradas' em cada dia do mes
    entradas[getDia($1)]++;
    # escrever a lista de locais de 'saida' ( sem repeticoes )
    saida = getSaida($6);
    if(!saidas[saida])
        saidas[arr[3]]=1;
    # calcular o total gasto no mes
    preco = getImportancia($7);
    desconto = getDesconto($8);
    totalGasto += (preco-desconto);
    # INICIO - calcular o total gasto no mes apenas em 'parques'
    tipo=getTipo($11);
    if( tipo ~ /Parques de estacionamento/ ){
        totalParques += (preco-desconto);
        # INICIO - escrever a lista de parques de estacionamento e as datas em que estes foram utilizados
        contaParques++;
        operador=getOperador($10);
        locaisParques[contaParques]=operador;
        dataSaida=getDataSaida($4);
        datasParques[contaParques]=dataSaida;
        # FIM - escrever a lista de parques de estacionamento e as datas em que estes foram utilizados
    }
    # FIM - calcular o total gasto no mes apenas em 'parques'
    # calcular o numero de transacoes associadas cada cartao
    cartao=getCartao($13);
    cartoes[cartao]++;  
}
END {
    print toupper("Total de transações: ") soma;
    print "TOTAL GASTO: " totalGasto;
    print "TOTAL GASTO EM PARQUES DE ESTACIONAMENTO: " totalParques;

    print toupper("numero de 'entradas' em cada dia do mes:");
    for( i in entradas )
        print "\tdia " i " : " entradas[i];
    

    print "\nLOCAIS DE SAIDA:";
    for( i in saidas )
        print "\t" i;
    
    print "\nCARTÕES UTILIZADOS:";
    for( i in cartoes )
        print "\tCartão " i " : " cartoes[i];
    
    print "\nDATAS E LOCAIS DE ESTACIONAMENTO:"
    for( i in locaisParques )
        print "\t" datasParques[i] " - " locaisParques[i];
    
}