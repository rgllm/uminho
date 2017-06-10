#include "estrutura.h"

//--- VARIAVEIS ---// 
struct variavel* insertVariavel(struct variavel *variavel, struct variavel *listaVariaveis)
{
	struct variavel *ant=NULL,*pt=listaVariaveis,*aux=variavel;

	while(pt && pt->nome && strcmp(variavel->nome,pt->nome)>=0)
	{
		ant = pt;
		pt = pt->next;
	}

	if(!ant)
	{
		aux->next = listaVariaveis;
		return aux;
	}
	else
	{
		aux->next = pt;
		ant->next = aux;
		return listaVariaveis;
	}
}

int existeVariavel(char *variavel, struct variavel *listaVariaveis)
{
	struct variavel *pt=listaVariaveis;
	int res = 0;
	
	while(pt && pt->nome && strcmp(variavel,pt->nome)>=0)
	{
		if(strcmp(variavel,pt->nome)==0) 
			res = 1;
		pt = pt->next;
	}

	return res;
}

int enderecoVariavel(char *variavel, struct variavel *listaVariaveis)
{
	struct variavel *pt=listaVariaveis;
	int res = -1;
	
	while(pt && pt->nome && strcmp(variavel,pt->nome)>=0)
	{
		if(strcmp(variavel,pt->nome)==0) 
			res = pt->endereco;
		pt = pt->next;
	}

	return res;
}

char* tipoVariavel(char *variavel, struct variavel *listaVariaveis)
{
	struct variavel *pt=listaVariaveis;
	char* res = NULL;
	
	while(pt && pt->nome && strcmp(variavel,pt->nome)>=0)
	{
		if(strcmp(variavel,pt->nome)==0) 
			res = pt->tipo;
		pt = pt->next;
	}

	return res;
}

void inicializaVariavel(char *variavel, struct variavel *listaVariaveis)
{
	struct variavel *pt=listaVariaveis;	

	while(pt && pt->nome && strcmp(variavel,pt->nome)>=0)
	{
		if(strcmp(variavel,pt->nome)==0) 
			pt->inicializado = 1;
		pt = pt->next;
	}
}

int variavelInicializada(char *variavel, struct variavel *listaVariaveis)
{
	struct variavel *pt=listaVariaveis;
	int res = 0;
	
	while(pt && pt->nome && strcmp(variavel,pt->nome)>=0)
	{
		if(strcmp(variavel,pt->nome)==0) 
			res = pt->inicializado;
		pt = pt->next;
	}

	return res;
}

//--- INSTRUCOES ---// 
struct instrucao* insertInstrucao(int endereco, char *instrucao, struct instrucao *listaInstrucoes)
{
	struct instrucao *ant=NULL,*pt=listaInstrucoes,*aux=NULL;

	aux = malloc(sizeof(struct instrucao));
	aux->endereco = endereco;
	aux->instrucao = strdup(instrucao);
	aux->next = NULL;

	while(pt && aux->endereco >= pt->endereco)
	{
		ant = pt;
		pt = pt->next;
	}

	if(!ant)
	{
		aux->next = listaInstrucoes;
		return aux;
	}
	else
	{
		aux->next = pt;
		ant->next = aux;
		return listaInstrucoes;
	}
}

//--- CONDICIONAL IF ---//
struct ifAddr* pushIfAddr(int endereco, struct ifAddr *pilhaIfAddr)
{
	struct ifAddr *aux = malloc(sizeof(struct ifAddr));
	aux->jz = endereco;
	aux->next = pilhaIfAddr;
	return aux;
}

struct ifAddr* popIfAddr(struct ifAddr *pilhaIfAddr)
{
	struct ifAddr *res = pilhaIfAddr->next;
	free(pilhaIfAddr);
	return res;
}

void ifJump(int endereco, struct ifAddr *pilhaIfAddr, struct instrucao *listaInstrucoes)
{
	struct instrucao *pt=listaInstrucoes, *aux=NULL;

	while(pt && pt->endereco != pilhaIfAddr->jz)
		pt = pt->next;

	char buf[20];
	sprintf(buf,"JZ _%06d",endereco);
	pt->instrucao = strdup(buf);
}

void elseJump(int endereco, struct ifAddr *pilhaIfAddr, struct instrucao *listaInstrucoes)
{
	struct instrucao *pt=listaInstrucoes, *aux=NULL;

	while(pt && pt->endereco != pilhaIfAddr->jz)
		pt = pt->next;

	char buf[20];
	sprintf(buf,"JUMP _%06d",endereco);
	pt->instrucao = strdup(buf);
}

//--- CICLO WHILE ---//
struct whileAddr* pushWhileAddr(int endereco, int whileAddr, struct whileAddr *pilhaWhileAddr)
{
	struct whileAddr *aux = malloc(sizeof(struct whileAddr));
	aux->jump = whileAddr;
	aux->jz = endereco;
	aux->next = pilhaWhileAddr;
	return aux;
}

struct whileAddr* popWhileAddr(struct whileAddr *pilhaWhileAddr)
{
	struct whileAddr *res = pilhaWhileAddr->next;
	free(pilhaWhileAddr);
	return res;
}

int whileJump(int endereco, struct whileAddr *pilhaWhileAddr, struct instrucao *listaInstrucoes)
{
	struct instrucao *pt=listaInstrucoes, *aux=NULL;

	while(pt && pt->endereco != pilhaWhileAddr->jz)
		pt = pt->next;

	char buf[20];
	sprintf(buf,"JZ _%06d",endereco);
	pt->instrucao = strdup(buf);

	return pilhaWhileAddr->jump;
}




