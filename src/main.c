/*
    duvida typedefs
    comparaProduto
 */


#include "clientes.h"
#include "produtos.h"
#include "faturacao.h"
#include "others.h"
#include "filiais.h"
#define MAXBUFF 64
#include <unistd.h>



int validaVenda(CatClientes c,CatProdutos p,char * produto,double preco,int qtd,char np,char * cliente,int mes,int filial){
    int lC,lP;
    lP=produto[0]-65;
    lC=cliente[0]-65;
    if(search(getAVLProd(p,lP),produto)!=NULL){
        if(search(getAVLCli(c,lC),cliente)!=NULL){
            if(qtd>0 &&
                preco>=0 &&
                mes>=1 && mes <=12 &&
                (np=='N' || np=='P') &&
                filial>=1 && filial<=3 )
                return 0;
        }
    }
    return 1;
}

void query1(CatClientes catClientes, CatProdutos catProd){
    int c,qtd,mes,fil;
    char cod[10],linha[MAXBUFF], buffer[MAXBUFF],*produto,np,*cli,*precAux;
    double prec;
    infoP aux;
    FILE *fp;
    Produto prod;
    Cliente cliente;
    char ficheiro_vendas[100];

    printf("\nDiretório do ficheiro vendas: ");
    scanf("%s", ficheiro_vendas);
    printf("\n");

    /*                 Leitura dos clientes                 */
    fp=fopen("files/Clientes.txt","r");
    while( fgets (buffer, MAXBUFF, fp)){
       strcpy(cod,buffer);
       strtok(cod,"\r\n");
       cliente = criaCliente(cod);
       if(!existeCliente(catClientes,cliente)){
            catClientes=insereCliente(catClientes,cliente);
        }
    }
    fclose(fp);

    c=0;
    initTabela();

    /*                 Leitura dos produtos                 */
    fp=fopen("files/Produtos.txt","r");
    while( fgets (buffer, MAXBUFF, fp)){
        strcpy(cod,buffer);
        strtok(cod,"\r\n");
        prod=criaProduto(cod);
        if(!existeProduto(catProd,prod)){
            catProd=insereProduto(catProd,prod);
            carregaProduto(criaInfoProduto(cod,0,0,0,0));
        }
    }
    fclose(fp);

    /*                 Leitura das vendas    */

    fp = fopen(ficheiro_vendas, "r" );
    while (fgets(buffer, MAXBUFF,fp)!=NULL){
        strcpy(linha,buffer);
        produto=strtok(buffer," ");
        prec=strtod(strtok(NULL," "),&precAux);
        qtd=atoi(strtok(NULL," "));
        np=strtok(NULL," ")[0];
        cli=strtok(NULL," ");
        mes=atoi(strtok(NULL," "));
        fil=atoi(strtok(NULL," "));

        if(validaVenda(catClientes,catProd,produto,prec,qtd,np,cli,mes,fil)==0){
            prec=qtd*prec;
            carregaVenda(produto,cli,qtd,np,mes,prec,fil);
            if(np=='N'){
                aux=(infoP)criaInfoProduto(produto,qtd,0,prec,0);
                registaFaturacaoProduto(aux, fil , mes );
            }
            else {
                aux=(infoP)criaInfoProduto(produto,0,qtd,0,prec);
                registaFaturacaoProduto(aux , fil , mes);
            }
        }
    }

    fclose(fp);

printf("Vendas carregadas!\n");
printf("(Prima ENTER para voltar ao menu)\n");
getchar();
getchar();
}

void query2(CatProdutos catProd){
    char letra;
    int indice,status,count,i;
    if(fork()==0)
        execlp("clear","clear",NULL);
    wait(&status);
    carregaArt("LOGO.txt");
    printf("|-------------------------------------Query 2--------------------------------------------|\n\n");
    printf("Letra: ");
    scanf(" %c",&letra);
    if(letra >=97 && letra <= 122) letra-=32; /*Caso em que a letra inserida é minúscula */
    else if(letra < 65 || letra > 90){ /*Caso em que o caracter introduzido não é uma letra minúscula nem maiúscula */
        printf("Letra inválida!\n");
        printf("(Prima ENTER para voltar ao menu)\n");
        getchar();
        getchar();
        return;
    }
    indice=letra-65;
    printf("\n\nTotal de produtos: %d\n",totalProdutosLetra(catProd,letra));
    printPages(getAVLProd(catProd,indice));
    getchar();
    getchar();
}

void query3(){
    int mes,totalVendasN, totalVendasP, status, filial, totalVendasNF, totalVendasPF;
    double totalFaturadoN,totalFaturadoP,totalFaturadoNF,totalFaturadoPF;
    char produto[10],tipo;
    if(fork()==0)
        execlp("clear","clear",NULL);
    wait(&status);
    carregaArt("LOGO.txt");
    printf("|-------------------------------------Query 3--------------------------------------------|\n\n");
    printf("Mês: ");
    scanf("%d",&mes);
    if(mes<1 || mes >12){
        printf("Mês inválido!\n");
        printf("(Prima ENTER para voltar ao menu)\n");
        getchar();
        getchar();
        return;
    }
    printf("Produto: ");
    scanf("%s",produto);
    printf("Apresentar resultados totais ou por filial? (T ou F): ");
    scanf(" %c", &tipo);
    if (tipo==84||tipo==116){
        totalFaturadoN=getTotalFaturadoMesN(mes,produto);
        totalFaturadoP=getTotalFaturadoMesP(mes,produto);
        totalVendasN=getTotalVendasMesN(mes,produto);
        totalVendasP=getTotalVendasMesP(mes,produto);

        if(totalFaturadoN==-1 && totalFaturadoP==-1){
            printf("Produto não encontrado!\n");
            printf("(Prima ENTER para voltar ao menu)\n");
            getchar();
            getchar();
            return;
        }
        printf("Total faturado modo Normal: %.2f\nTotal faturado modo Promoção: %.2f\n",totalFaturadoN,totalFaturadoP);
        printf("Total de vendas em modo Normal: %d\nTotal de vendas em modo Promoção %d\n",totalVendasN,totalVendasP);
        printf("(Prima ENTER para voltar ao menu)\n");
        getchar();
        getchar();
        return;
    }
     else if (tipo==70||tipo==102){
        for(filial=1;filial<=3;filial++){
            totalFaturadoNF=0;totalFaturadoPF=0;totalVendasNF=0;totalVendasPF=0;
            totalFaturadoNF=getTotalFaturadoMesN_filial(mes,produto,filial);
            totalFaturadoPF=getTotalFaturadoMesP_filial(mes,produto,filial);
            totalVendasNF=getTotalVendasMesN_filial(mes,produto,filial);
            totalVendasPF=getTotalVendasMesP_filial(mes,produto,filial);
            printf("\nFilial %d\n", filial);
            if(totalFaturadoNF==-1 && totalFaturadoPF==-1 && totalVendasNF==-1 &&  totalVendasPF==-1){
                printf("Produto não comprado nesta filial neste mês;");
                printf("\n\n");
            }
            else{
            printf("Total faturado modo Normal: %.2f\nTotal faturado modo Promoção: %.2f\n",totalFaturadoNF,totalFaturadoPF);
            printf("Total de vendas em modo Normal: %d\nTotal de vendas em modo Promoção %d\n",totalVendasNF,totalVendasPF);
            printf("\n\n");
            }
        }
        printf("(Prima ENTER para voltar ao menu)\n");
        getchar();
        getchar();
        return;
    }
    else printf("informação inválida\n");
         printf("(Prima ENTER para voltar ao menu)\n");
         getchar();
         getchar();
}


void query4(){
    int filial, status;
    char tipo;
    if(fork()==0)
        execlp("clear","clear",NULL);
    wait(&status);
    carregaArt("LOGO.txt");
    printf("|-------------------------------------Query 4--------------------------------------------|\n\n");
    printf("Apresentar resultado total ou por filial? (T ou F): ");

    scanf(" %c", &tipo);
    if (tipo==84||tipo==116){
        printf("Total: %d\n", contaNaoCompradosFilial(4));
        printNaoComprados(getTotalFilial(4),0);
        printf("(Prima ENTER para voltar ao menu)\n");
        getchar();
        getchar();
        return;
    }
    else if (tipo==70||tipo==102){
        printf("\nIndique a filial pretendida (1, 2 ou 3): ");
        scanf(" %d", &filial);
        if(filial<1 || filial>3){printf("Filial inválida!\n");printf("(Prima ENTER para voltar ao menu)\n");getchar();getchar();return;}
        printf("Total filial %d: %d\n", filial, contaNaoCompradosFilial(filial));
        printf("(Prima ENTER para voltar ao menu)\n");
        getchar();
        getchar();
        return;

    }
    else printf("informação inválida\n");
         printf("(Prima ENTER para voltar ao menu)\n");
         getchar();
         getchar();
}


void query6(){
    int mesI, mesF,totalVendas=0,status;
    double totalFaturado=0;
    if(fork()==0)
        execlp("clear","clear",NULL);
    wait(&status);
    carregaArt("LOGO.txt");
    printf("|-------------------------------------Query 6--------------------------------------------|\n\n");
    printf("Mês inicial: ");
    scanf("%d",&mesI);
    printf("Mês Final: ");
    scanf("%d",&mesF);
    if((mesI<1 || mesI >12) || (mesF<1 || mesF >12) || (mesF-mesI<0)){
        printf("Mês inválido!\n");
        printf("(Prima ENTER para voltar ao menu)\n");
        getchar();
        getchar();
        return;
    }
    totalVendas=contaTotalVendas(mesI,mesF);
    totalFaturado=contaTotalFaturado(mesI,mesF);
    printf("Total de vendas registadas entre o mês %d e o mês %d é: %d\n", mesI,mesF,totalVendas);
    printf("Total faturado entre o mês %d e o mês %d é: %.2f\n", mesI,mesF,totalFaturado);
    printf("(Prima ENTER para voltar ao menu)\n");
    getchar();
    getchar();
}


void query7(CatClientes clientes){
    int status, i,count=0;
    char cliente;
    if(fork()==0)
        execlp("clear","clear",NULL);
    wait(&status);
    carregaArt("LOGO.txt");
    printf("|-------------------------------------Query 7--------------------------------------------|\n\n");
    
   
    for (i=0; i<26; i++)
        count+=percorreClientes(getAVLCli(clientes,i));
    printf("%d\n",count);

    printf("(Prima ENTER para voltar ao menu)\n");
    getchar();
    getchar();
}



/*
void query4(){
    printf("%d\n",getNaoComprados());
}

*/

int main(){
    CatClientes catClientes;
    CatProdutos catProd;
    int op=-1,status,carregado=0;
    catClientes=initCatClientes();
    catProd=initCatProdutos();
    while(op!=0){
        if(fork()==0)
            execlp("clear","clear",NULL);
        wait(&status);
        carregaArt("LOGO.txt");
        printf("|--------------------------------------MENU----------------------------------------------|\n\n");
        printf("1- Ler ficheiros para memória\n");
        printf("2- Determinar a lista e o total de produtos cujo código se inicia por uma dada letra\n");
        printf("3- Dado um mês e um código de produto determinar e apresentar o número total de vendas e o total faturado com esse produto\n");
        printf("4- Determinar a lista ordenada dos códigos dos produtos que ninguém comprou\n");
        printf("6- Dado um intervalo de meses determinar o total de vendas registadas e o total faturado\n");
        printf("\nEscolha uma query ou 0 para sair: ");
        scanf("%d",&op);
        if(op==1) {query1(catClientes, catProd);carregado=1;}
        else if (op==2 && carregado) query2(catProd);
        else if (op==3 && carregado) query3();
        else if (op==4 && carregado) query4();
        else if (op==6 && carregado) query6();
        else if (op==7 && carregado) query7(catClientes);
        else if(!carregado && op) {printf("Precisa de carregar os ficheiros!\n"); getchar(); getchar();}
        else if(op!=0){
            printf("Ainda não está mas vai estar\n");
            getchar();
            getchar();
        }
}
    if(fork()==0)
            execlp("clear","clear",NULL);
    wait(&status);
    return 1;
}



/*


    printf("4- Determinar a lista ordenada dos códigos dos produtos que ninguém comprou\n");
    printf("5- Dado um código de cliente criar uma tabela com o número total de produtos comprados mês a mês\n");
    printf("7- Determinar a lista ordenada de códigos de clientes que realizaram compras em todas a filiais\n");
    printf("8- Dado um código de produto e uma filial determinar os códigos dos clientes que o compraram\n");
    printf("9- Dado um código de cliente e um mês determinar a lista de códigos de produtos que mais comprou por quantidade\n");
    printf("10- Criar uma lista nos N produtos mais vendidos em todo o ano\n");
    printf("11- Dado um código de cliente determinar quais os códigos dos 3 produtos em que gastou mais dinheiro durante o ano\n");
    printf("12- Determinar o número de clientes registados que não realizaram compras e o número de produtos que ninguém comprou\n");

    while(fgets(querie,MAXBUFF,0)!="q"){
        if(querie=="1"){
            catClientes clientes;
            catProdutos produtos;

            carregar(clientes,produtos);
        }


}
*/
