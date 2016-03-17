#include "clientes.h"


cliente criaCliente(char* cod){
    return criaNodo(cod,NULL);
}

cliente insereCliente(char * cod,cliente raiz){
    return insert(cod,raiz);
}

void carregaClientes(catClientes clientes){
    char buffer[MAXBUFF],cod[10];
    int aux;
        FILE *fp;
    int i=0,c=0;
    fp=fopen("files/Clientes.txt","r");
    for(i=0;i<26;i++)
        clientes[i]=NULL;

    while( fgets (buffer, MAXBUFF, fp)){
            strcpy(cod,buffer);
            strtok(cod,"\r\n");
            aux=((unsigned char)cod[0])-65;
            if(clientes[aux]==NULL)
                clientes[aux]=criaCliente(cod);
            else
                clientes[aux]=insereCliente(cod,clientes[aux]);
            c++;
        }
    printf("Clientes: %d\n",c );
    fclose(fp);
}