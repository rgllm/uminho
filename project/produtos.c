#include "produtos.h"

produto criaProduto(char* cod){
    return criaNodo(cod,NULL);
}

produto insereProduto(char * cod,produto raiz){
    return insert(cod,raiz);
}

void carregaProdutos(catProdutos produtos){
    char buffer[MAXBUFF],cod[10];
    FILE *fp;
    int aux;
    int i=0,c=0;
    fp=fopen("files/Produtos.txt","r");
    for(i=0;i<26;i++){
        produtos[i]=NULL;
}
    while( fgets (buffer, MAXBUFF, fp)){
            strcpy(cod,buffer);
            strtok(cod,"\r\n");
            aux=((unsigned char)cod[0])-65;
            if(produtos[aux]==NULL){
                produtos[aux]=criaProduto(cod);
            }
            else{
                produtos[aux]=insereProduto(cod,produtos[aux]);
            }
            c++;
        }
    printf("Produtos: %d\n",c );
    fclose(fp);
}