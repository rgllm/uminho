/*
    duvida typedefs
    comparaProduto
 */


#include "clientes.h"
#include "produtos.h"
#include "faturacao.h"
#define MAXBUFF 64
#include <unistd.h>


void query3(){
    int mes,totalVendas;
    double totalFaturado;
    char produto[10];
    if(fork()==0)
        execlp("clear","clear",NULL);
    wait();
    printf("--- QUERY 3 ---\n");
    printf("Mês: ");
    scanf("%d",&mes);
    if(mes<1 || mes >12){
        printf("Mês inválido!\n");
        printf("(Prima ENTER para continuar)\n");
        getchar();
        getchar();
        return;
    }
    printf("Produto: ");
    scanf("%s",produto);

    totalFaturado=getTotalFaturado(mes,produto);
    totalVendas=getTotalVendas(mes,produto);
    if(totalFaturado==-1){
        printf("Produto inválido!\n");
        printf("(Prima ENTER para continuar)\n");
        getchar();
        getchar();
        return;
    }
    printf("Total faturado: %.2f\nTotal de vendas: %d\n",totalFaturado,totalVendas );
    printf("(Prima ENTER para continuar)\n");
    getchar();
    getchar();
}

void query6(){
    int mesI, mesF,totVendas;
    double totFat;
    if(fork()==0)
        execlp("clear","clear",NULL);
    wait();
    printf("--- QUERY 6 ---\n");
    printf("Mês inicial: ");
    scanf("%d",&mesI);
    printf("Mês Final: ");
    scanf("%d",&mesF);
    getQuery6(mesI,mesF,&totFat,&totVendas);
    printf("Total de vendas registadas entre o mês %d e o mês %d é: %d\n", mesI,mesF,totVendas);
    printf("Total faturado entre o mês %d e o mês %d é: %.2f\n", mesI,mesF,totFat);
    printf("(Prima ENTER para continuar)\n");
    getchar();
    getchar();
}

/*
void query4(){
    printf("%d\n",getNaoComprados());
}

*/

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

int main(){
	CatClientes catClientes;
	CatProdutos catProd;
	int c,qtd,mes,fil,op=1;
	char cod[10],linha[MAXBUFF], buffer[MAXBUFF],*produto,np,*cli,*precAux;
	double prec;
	infoP aux;
	FILE *fp;
    Produto prod;
    Cliente cliente;


	/*                 Leitura dos clientes                 */

	catClientes=initCatClientes();
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
    catProd=initCatProdutos();
    fp=fopen("files/Produtos.txt","r");
    while( fgets (buffer, MAXBUFF, fp)){
        strcpy(cod,buffer);
        strtok(cod,"\r\n");
        prod=criaProduto(cod);
        if(!existeProduto(catProd,prod)){
            catProd=insereProduto(catProd,prod);
            c++;
            carregaProduto(criaInfoProduto(cod,0,0,0,0));
        }
    }
    fclose(fp);


    /*                 Leitura das vendas    */
    c=0;

    fp = fopen( "files/Vendas1.txt", "r" );
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

    while(op!=0){
        if(fork()==0)
            execlp("clear","clear",NULL);
        wait();
        printf("3- Dado um mês e um código de produto determinar e apresentar o número total de vendas e o total faturado com esse produto\n");
        printf("6- Dado um intervalo de meses determinar o total de vendas registadas e o total faturado\n");
        printf("\nEscolha uma query (6 ou 3) ou 0 para sair: ");
        scanf("%d",&op);
        if (op==3) query3();
        else if (op==6) query6();
        else if(op!=0) printf("Ainda não está mas vai estar\n");
}
	return 1;
}



/*


    printf("1- Ler ficheiros para memória\n");
    printf("2- Determinar a lista e o total de produtos cujo código se inicia por uma dada letra\n");
    printf("3- Dado um mês e um código de produto determinar e apresentar o número total de vendas e o total faturado com esse produto\n"); - DONE
    printf("4- Determinar a lista ordenada dos códigos dos produtos que ninguém comprou\n"); - PARTIAL DONE
    printf("5- Dado um código de cliente criar uma tabela com o número total de produtos comprados mês a mês\n");
    printf("6- Dado um intervalo de meses determinar o total de vendas registadas e o total faturado\n"); - DONE
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
