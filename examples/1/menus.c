#include"menus.h"


int menuOpcoes() {
    int op=0;
    
    while(op<1 || op>15) {
        puts("Query  1 - Ler os ficheiros.");
        puts("Query  2 - Dada uma letra, determina a lista e o total de produtos cujo código se inicia pela mesma.");
        puts("Query  3 - Dado um mês e um código, determina e apresenta o número total de vendas (N e P) e o total facturado.");
        puts("Query  4 - Determina a lista de códigos dos produtos que ninguem comprou.");
        puts("Query  5 - Dado um código de cliente, cria uma tabela com o número total de produtos comprados, mês a mês.");
        puts("Query  6 - Dada uma letra, determina a lista de todos os códigos de clientes iniciados pela mesma.");
        puts("Query  7 - Dado um intervalo fechado de meses, determina o total de compras registadas e o total facturado.");
        puts("Query  8 - Dado um código de produto, determina os códigos (e número total) dos clientes que o compraram, distinguindo entre modo N e P.");
        puts("Query  9 - Dado um código de cliente e um mês, determina a lista de códigos de produtos que mais comprou.");
        puts("Query 10 - Determina a lista de clientes que efectuaram compras em todos os meses do ano.");
        puts("Query 11 - Cria um ficheiro CSV que contém para todos os meses, o número de compras realizadas e número de clientes que realizaram tais compras.");
        puts("Query 12 - Cria a lista dos N produtos mais vendidos durante o ano, indicando o número total de clientes e unidades vendidas.");
        puts("Query 13 - Dado um código de cliente, determina os três produtos que mais comprou durante o ano.");
        puts("Query 14 - Determina o número de clientes que não realizaram compras e os produtos que ninguém comprou.");
        puts("Sair  15\n");
        
        printf("Escolha uma opção: ");
        scanf("%d",&op);
        
        if(op<1 || op>15) {
            puts("\n**** Opção inválida ***\n");
        }
    }
    return op;
}

/**
 * Lê um ficheiro .txt que contem um TextArt
 * NOTA: o ficheiro já tem os \n
 * @param nome_ficheiro
 */
void carregaTextArt(char *nome_ficheiro) {
    char *lido;
    char *linha = (char *) malloc(256);
    FILE *ficheiro;
    
    ficheiro = fopen(nome_ficheiro,"r");
    
    while((lido = fgets(linha,256,ficheiro)) != NULL) {
        printf("\t%s",linha);
    }
        
    fclose(ficheiro); 
}

void imprimeNumQuery(int nq) {
    system("clear");
      puts("==========================");
    printf("|        QUERY %2d        |\n",nq);
      puts("==========================");
}
