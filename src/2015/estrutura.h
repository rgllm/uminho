#ifndef _ESTRUTURA
#define _ESTRUTURA

#include <stdio.h>
#include <string.h>
#include <stdlib.h>

struct variavel
{
	char *nome;
	char *tipo; 
	int endereco;
	int inicializado;
	struct variavel *next;
};

struct instrucao
{
	int endereco;
	char *instrucao;
	struct instrucao *next;
};

struct ifAddr
{
	int jz;
	struct ifAddr *next;
};

struct whileAddr
{
	int jump;
	int jz;
	struct whileAddr *next;
};

struct variavel* insertVariavel(struct variavel *variavel, struct variavel *listaVariaveis);
int existeVariavel(char *variavel, struct variavel *listaVariaveis);
int enderecoVariavel(char *variavel, struct variavel *listaVariaveis);
char* tipoVariavel(char *variavel, struct variavel *listaVariaveis);

struct instrucao* insertInstrucao(int endereco, char *instrucao, struct instrucao *listaInstrucoes);

struct ifAddr* pushIfAddr(int endereco, struct ifAddr *pilhaIfAddr);
struct ifAddr* popIfAddr(struct ifAddr *pilhaIfAddr);

struct whileAddr* pushWhileAddr(int endereco, int whileAddr, struct whileAddr *pilhaWhileAddr);
struct whileAddr* popWhileAddr(struct whileAddr *pilhaWhileAddr);

void ifJump(int endereco, struct ifAddr *pilhaIfAddr, struct instrucao *pilhaInstrucoes);
void elseJump(int endereco, struct ifAddr *pilhaIfAddr, struct instrucao *pilhaInstrucoes);
int whileJump(int endereco, struct whileAddr *pilhaWhileAddr, struct instrucao *pilhaInstrucoes);

#endif
