%--------------------------------------------------------------------------------------------
%--------------------------------------------------------------------------------------------
% SIST. REPR. CONHECIMENTO E RACIOCINIO - MiEI/3

% TRABALHO DE GRUPO - 1 EXERCICIO

% GRUPO 2

%--------------------------------------------------------------------------------------------
%--------------------------------------------------------------------------------------------


%--------------------------------------------------------------------------------------------
%--------------------Declaracões Iniciais----------------------------------------------------
%--------------------------------------------------------------------------------------------

:- set_prolog_flag( discontiguous_warnings,off ).
:- set_prolog_flag( single_var_warnings,off ).
:- set_prolog_flag( unknown,fail ).

:- op( 900,xfy,'::' ).
:- dynamic utente/4.
:- dynamic medico/4.
:- dynamic especialidade/2.
:- dynamic cuidado/4.
:- dynamic atomedico/6.

%--------------------------------------------------------------------------------------------
%------Extensão do predicado que permite encontrar todas as soluções-------------------------
%--------------------------------------------------------------------------------------------

solucoes(X,Y,Z):-
      findall(X,Y,Z).


%--------------------------------------------------------------------------------------------
%------Extensao do predicado removerElemento: [X | L], Y, [A | B] -> {V,F}-------------------
%--------------------------------------------------------------------------------------------

removerElemento( [],_,[] ).

removerElemento( [X|L],X,NL ) :-
    removerElemento( L,X,NL ).

removerElemento( [X|L],Y,[X|NL] ) :-
    X \== Y, removerElemento( L,Y,NL ).


%--------------------------------------------------------------------------------------------
%------Extensao do predicado removerRepetidos: [X | L], [A | B] -> {V,F}---------------------
%--------------------------------------------------------------------------------------------

removerRepetidos( [],[] ).

removerRepetidos( [X|L],[X|NL] ) :-
    removerElemento( L,X,TL ), removerRepetidos( TL,NL ).


%--------------------------------------------------------------------------------------------
%------Extensão do predicado que permite a remocao do conhecimento: T -> {V,F}---------------
%--------------------------------------------------------------------------------------------

remocao( Termo ) :- solucoes(Inv, -Termo::Inv, Linv),
                    retract(Termo),
                    testa(Linv).
remocao( Termo ) :- assert(Termo).

%--------------------------------------------------------------------------------------------
%------Extensão do predicado que calcula os elementos de uma lista: T -> {V,F}---------------
%--------------------------------------------------------------------------------------------

comprimento(S,N) :- length(S,N).


%--------------------------------------------------------------------------------------------
%------Extensão do predicado contem: H,[H|T] -> {V, F}---------------------------------------
%--------------------------------------------------------------------------------------------

contem(H, [H|T]).
contem(X, [H|T]) :-
    contem(X, T).


%--------------------------------------------------------------------------------------------
%------Extensão do predicado contem: [H|T],L -> {V, F}---------------------------------------
%--------------------------------------------------------------------------------------------

contemTodos([], _).
contemTodos([H|T], L) :-
    contem(H, L), contemTodos(T, L).


%--------------------------------------------------------------------------------------------
%------Extensão do predicado que permite a evolucao do conhecimento: T -> {V,F}--------------
%--------------------------------------------------------------------------------------------

insere( Termo ) :- solucoes( Inv, +Termo::Inv, Linv),
                   assert(Termo),
                   testa(Linv).
insere( Termo ) :- retract(Termo).

testa([]).
testa([H|T]):- H, testa(T).


%--------------------------------------------------------------------------------------------
%------Extensão do predicado que permite juntar conhecimento: L1,L2,L  -> {V,F}--------------
%--------------------------------------------------------------------------------------------

acrescenta(L1,L2,L) :- append(L1,L2,L).



%--------------------------------------------------------------------------------------------
%------Extensão do predicado que permite somar os elementos de uma lista: [H|T],R -> {V, F}--
%--------------------------------------------------------------------------------------------

somatorio([],0).
somatorio([H|T],Sum):-
  somatorio(T,Rest),
  Sum is H+Rest.


%--------------------------------------------------------------------------------------------
%------Utente: #IdUt, Nome, Idade, Morada -> {V,F}-------------------------------------------
%--------------------------------------------------------------------------------------------

utente(u1,'Bruno Pereira',21,'Braga').
utente(u2,'Maria Brito',21,'Braga').
utente(u3,'Gustavo Andrez',36,'Guimaraes').
utente(u4,'Rogerio Moreira',20,'Braga').
utente(u5,'Samuel Ferreira',20,'Sao Joao da Madeira').
utente(u6,'Asdrubal Amora',80,'Guimaraes').


%--------------------------------------------------------------------------------------------
%------Médico : #IdMed, Nome, Idade, #IdEsp -> {V,F}-----------------------------------------
%--------------------------------------------------------------------------------------------

medico(m1, 'Fernando Fernandes', 34, e1).
medico(m2, 'Paulo Pinho', 48, e2).
medico(m3, 'Augusto Agostinho', 52, e3).
medico(m4, 'Bernardo Barros', 44, e4).
medico(m5, 'Carlos Costa', 45, e5).
medico(m6, 'Dario Dias', 61, e6).
medico(m7, 'Nadia Nascimento', 29, e4).
medico(m8, 'Antonio Egas Moniz',163,e7).


%--------------------------------------------------------------------------------------------
%------Especialidade : #IdEspecialidade, Designação------------------------------------------
%--------------------------------------------------------------------------------------------
especialidade(e1, 'geral').
especialidade(e2, 'pediatria').
especialidade(e3, 'urologia').
especialidade(e4, 'ginecologia').
especialidade(e5, 'psiquiatria').
especialidade(e6, 'radiologia').
especialidade(e7, 'cirurgia').


%--------------------------------------------------------------------------------------------
%------Cuidado prestado: #IdServ, Descrição, Instituição, Cidade -> {V,F}--------------------
%--------------------------------------------------------------------------------------------

cuidado(s1,e5,'hospital sao joao','porto').
cuidado(s2,e4,'hospital sao marcos','braga').
cuidado(s3,e6,'hospital da luz','lisboa').
cuidado(s4,e2,'hospital sao joao','porto').
cuidado(s5,e1,'centro de saude do caranda','braga').
cuidado(s6,e3,'hospital sao joao','porto').
cuidado(s7,e2,'maternidade alfredo da costa','lisboa').
cuidado(s8,e7,'hospital da luz','lisboa').


%-----------------------------------------------------------------------------------------------------------------
%------Ato medico: Hora (hh:mm) ,Data(dd-mm-yyyy), #IdUt, #IdServ, #IdMed, Custo(€€.€€) -> {V,F}----------
%-----------------------------------------------------------------------------------------------------------------

atomedico('12:20',10-03-2017, u1, s5, m1, 12.20).
atomedico('21:00',2-03-2017, u2, s4, m2, 16.50).
atomedico('09:40',14-02-2017, u3, s6, m3, 21.00).
atomedico('22:00',21-01-2017, u4, s1, m5, 18.20).
atomedico('17:20',12-03-2017, u5, s2, m4,  14.80).
atomedico('08:20',20-02-2017, u5, s3, m6,  54.32).
atomedico('10:20',28-03-2017, u5, s2, m4,  54.32).
atomedico('11:20',28-02-2017, u2, s6, m3,  12.20).
atomedico('14:20',15-03-2017, u3, s4, m2,  12.20).
atomedico('13:20',15-03-2017, u5, s1, m5,  12.20).
atomedico('09:45',15-03-2017, u1, s5, m1,  12.20).
atomedico('00:00',25-12-2016, u2, s7, m8,  891.24).


%--------------------------------------------------------------------------------------------
%------Registo de Utente: #IdUt, Nome, Idade, Morada) -> {V,F}-------------------------------
%--------------------------------------------------------------------------------------------

registoUtente(Id,Nome,Idade,Morada) :-
            insere(utente(Id,Nome,Idade,Morada)).


%--------------------------------------------------------------------------------------------
%------Registo de Médico: #IdMed, Nome, Idade, IdEsp) -> {V,F}-------------------------------
%--------------------------------------------------------------------------------------------

registoMedico(IdMed,Nome,Idade,IdEsp) :-
            insere(medico(IdMed,Nome,Idade,IdEsp)).


%--------------------------------------------------------------------------------------------
%------Registo de Especialidade: #IdEspecialidade, Designação -> {V,F}-----------------------
%--------------------------------------------------------------------------------------------

registoEspecialidade(IdEsp,Desig) :-
            insere(especialidade(IdEsp,Desig)).


%--------------------------------------------------------------------------------------------
%------Registo de Cuidado: #IdCuidado,Descricao,Instituicao,Cidade -> {V,F}------------------
%--------------------------------------------------------------------------------------------

registoCuidado(Id,Descricao,Instituicao,Cidade) :-
            insere(cuidado(Id,Descricao,Instituicao,Cidade)).


%-------------------------------------------------------------------------------------------------------
%------Registo de atos medicos: Hora, Data, Id-Utilizador, Id-Servico, Id-Medico, Custo -> {V,F}--------
%-------------------------------------------------------------------------------------------------------

registoAtoMedico(Hora,Data,IdU,IdS,IdMed,Custo) :-
            insere(atomedico(Hora,Data,IdU,IdS,IdMed,Custo)).


%--------------------------------------------------------------------------------------------
%------Predicado que remove um utente--------------------------------------------------------
%--------------------------------------------------------------------------------------------

removerUtente(Id,Nome,Idade,Morada) :-
        remocao(utente(Id,Nome,Idade,Morada )).


%--------------------------------------------------------------------------------------------
%-------Predicado que remove um medico-------------------------------------------------------
%--------------------------------------------------------------------------------------------

removerMedico(IdMed, Nome, Idade, IdEsp) :-
        remocao(medico(IdMed, Nome, Idade, IdEsp)).


%--------------------------------------------------------------------------------------------
%------Predicado que remove uma especialidade------------------------------------------------
%--------------------------------------------------------------------------------------------

removerEspecialidade(IdEsp, Desig) :-
        remocao(especialidade(IdEsp, Desig)).


%--------------------------------------------------------------------------------------------
%------Predicado que remove um cuidado-------------------------------------------------------
%--------------------------------------------------------------------------------------------

removerCuidado(Id,Descricao,Instituicao,Cidade) :-
        retract(cuidado(Id,Descricao,Instituicao,Cidade)),
        solucoes(Id, atomedico(_, _, _, Id, _, _), L),
        removerTodosAtosMedicosPorIdS(Id,L).


%--------------------------------------------------------------------------------------------
%------Predicado que remove todos os atos médicos cujo cuidado já foi removido---------------
%--------------------------------------------------------------------------------------------

removerTodosAtosMedicosPorIdS(Id, []).
removerTodosAtosMedicosPorIdS(Id, [H|T]) :-
        retract(atomedico(_,_,_,Id,_,_)),
        removerTodosAtosMedicosPorIdS(Id,T).


%-------------------------------------------------------------------------------------------------------------
%------Predicado que conta o numero de atos medicos efetuados por um determinado medico: IdM , L-> {V,F}------
%-------------------------------------------------------------------------------------------------------------

contaAtosMedicos(IdM, L):-
  solucoes(IdM, atomedico(_,_,_,_,IdM,_),L1),
  length(L1,L).


%--------------------------------------------------------------------------------------------
%------Predicado que remove um ato medico----------------------------------------------------
%--------------------------------------------------------------------------------------------

removerAtoMedico(Hora,Data,IdU,IdS,IdMed,Custo) :-
       remocao(atomedico(Hora,Data,IdU,IdS,IdMed,Custo)).


%--------------------------------------------------------------------------------------------
%------Predicado que identifica um servico pelo seu id: {S,A},Y -> {V,F}---------------------
%--------------------------------------------------------------------------------------------

mostrarServico({S,A},Y) :-
  solucoes({T,A},especialidade(S, T),Y).


%--------------------------------------------------------------------------------------------
%------Predicado que identifica um servico pelo seu id numa lista: L, Y -> {V,F}-------------
%--------------------------------------------------------------------------------------------

mostrarServicoLista([],[]).
mostrarServicoLista([H|T],Y):-
    mostrarServico(H,X),
    mostrarServicoLista(T,Z),
    acrescenta(X,Z,Y).


%--------------------------------------------------------------------------------------------
%-------Predicado que identifica os utentes pela sua idade: C,L -> {V,F}---------------------
%--------------------------------------------------------------------------------------------

listarUtentesIdade(C,L) :-
    solucoes({I1,I2,I3},utente(I1,I2,C,I3),L).


%--------------------------------------------------------------------------------------------
%------Predicado que identifica os utentes pela sua cidade: C,L -> {V,F}---------------------
%--------------------------------------------------------------------------------------------

listarUtentesCidade(C,L) :-
    solucoes({I1,I2,I3},utente(I1,I2,I3,C),L).


%--------------------------------------------------------------------------------------------
%------Predicado que identifica os utentes pelo seu nome: C,L -> {V,F}-----------------------
%--------------------------------------------------------------------------------------------

listarUtentesNome(C,L) :-
    solucoes({I1,C,I2,I3},utente(I1,C,I2,I3),L).


%--------------------------------------------------------------------------------------------
%------Predicado que identifica os utentes dado todos os seus dados: C,L -> {V,F}------------
%--------------------------------------------------------------------------------------------

listarUtentesDados(C,L) :-
    solucoes({I1,I2,I3},utente(C,I1,I2,I3),L).


%------------------------------------------------------------------------------------------------
%------Predicado que identifica as instituicoes prestadoras de cuidados de saude: C,L -> {V,F}---
%------------------------------------------------------------------------------------------------

listarInstituicoes(L) :-
    solucoes(C,cuidado(I1,I2,C,I3),L1),
    removerRepetidos(L1,L).


%-----------------------------------------------------------------------------------------------------
%------Predicado que identifica os cuidados prestados por uma determinada instituicao: C,L -> {V,F}---
%-----------------------------------------------------------------------------------------------------

listarCuidadosInstituicao(C,L) :-
  solucoes({I2,I3,I1},cuidado(I1,I2,C,I3),L1),
  removerRepetidos(L1,L2),
  mostrarServicoLista(L2,L).


%--------------------------------------------------------------------------------------------
%------Predicado que identifica os cuidados prestados numa determinada cidade: C,L -> {V,F}--
%--------------------------------------------------------------------------------------------

listarCuidadosCidade(C,L) :-
   solucoes({I2,I3},cuidado(I1,I2,I3,C),L1),
   removerRepetidos(L1,L2),
   mostrarServicoLista(L2,L).


%--------------------------------------------------------------------------------------------
%------Predicado que identifica os utentes por cuidado: C,L -> {V,F}-------------------------
%--------------------------------------------------------------------------------------------

listarUtentesCuidado(C,L) :-
    solucoes(Idu, atomedico(_,_,Idu,C,_,_),AUX),
    listarUtentesPorIds(AUX,L).


%--------------------------------------------------------------------------------------------
%------Predicado que identifica os utentes de uma instituicao: Inst,L -> {V,F}---------------
%--------------------------------------------------------------------------------------------

listarUtentesInstituicao(Inst,S) :-
    solucoes(I1,cuidado(I1,I2,Inst,I3),S1),
    listarUtentesPorCuidados(S1,L),
    listarUtentesPorIds(L,S2),
    removerRepetidos(S2,S).


%--------------------------------------------------------------------------------------------
%------Predicado que identifica os utentes através do seu id: T, L -> {V,F} -----------------
%--------------------------------------------------------------------------------------------

listarUtentesPorIds([],[]).
listarUtentesPorIds([H|T],L) :-
    utente(H,I1,I2,I3),
    listarUtentesPorIds(T,X),
    acrescenta([{H,I1,I2,I3}],X,L).


%--------------------------------------------------------------------------------------------
%------Predicado que identifica os atos médios por Id de Serviços: T, L -> {V,F}-------------
%--------------------------------------------------------------------------------------------

listarAtosMedicosPorIdServico([],[]).
listarAtosMedicosPorIdServico([H|T],L) :-
    solucoes({Hora,Data,Idu,IdMed,Custo},atomedico(Hora,Data,Idu,H,IdMed,Custo),AUX),
    listarAtosMedicosPorIdServico(T,X),
    acrescenta(AUX,X,L).


%--------------------------------------------------------------------------------------------
%------Predicado que lista os Ids dos medicos por Ids de serviço: T, L -> {V,F}-------------
%--------------------------------------------------------------------------------------------
listarIdMedicosPorIdServico([],[]).
listarIdMedicosPorIdServico([H|T],L) :-
    solucoes(IdMed,atomedico(_,_,_,H,IdMed,_),AUX),
    listarIdMedicosPorIdServico(T,X),
    acrescenta(AUX,X,Z),
    removerRepetidos(Z,L). 

%--------------------------------------------------------------------------------------------
%------Predicado que identifica os atos médios por cuidado: T, L -> {V,F}--------------------
%--------------------------------------------------------------------------------------------

listarUtentesPorCuidados([],[]).
listarUtentesPorCuidados([H|T],L) :-
    solucoes(Idu,atomedico(_,_,Idu,H,_,_),AUX),
    listarUtentesPorCuidados(T,X),
    acrescenta(AUX,X,L).


%--------------------------------------------------------------------------------------------
%------Predicado que determina as instituições de um médico: IdMed, L -> {V,F}---------------
%--------------------------------------------------------------------------------------------

instituicoesPorMedico(IdMed, L) :-
    solucoes(IdS, atomedico(_,_,_,IdS,IdMed,_),R),
    listarInstituicoesVariosServicos(R,Z),
    removerRepetidos(Z,L).


%--------------------------------------------------------------------------------------------
%------Predicado que lista  todas instituições de um conjunto de Serviços: T, P -> {V,F}-----
%--------------------------------------------------------------------------------------------

listarInstituicoesVariosServicos([], []).
listarInstituicoesVariosServicos([H|T], P) :-
    listarInstituicoesServico(H,R),
    listarInstituicoesVariosServicos(T,L),
    acrescenta(R,L,P).


%--------------------------------------------------------------------------------------------
%------Predicado que identifica as instituições por um determinado serviço: C, L -> {V,F}----
%--------------------------------------------------------------------------------------------

listarInstituicoesServico(C,L) :-
    solucoes(Inst,cuidado(C,_,Inst,_),L1),
    removerRepetidos(L1,L).


%--------------------------------------------------------------------------------------------
%------Predicado que lista toda a informação de uma lista de serviços: T, L -> {V,F}---------
%--------------------------------------------------------------------------------------------

listarInfoServicos([],[]).
listarInfoServicos([H|T],L) :-
    solucoes({Desc,Inst,Cidade},cuidado(H,Desc,Inst,Cidade),AUX),
    listarInfoServicos(T,X),
    acrescenta(AUX,X,L1),
    mostrarServicoLista(L1,L).


%--------------------------------------------------------------------------------------------
%------Predicado que lista os atos médicos para um utente: Idu, L -> {V,F}-------------------
%--------------------------------------------------------------------------------------------

atosMedicosPorUtente(Idu,L) :-
    solucoes({Hora,Data,Ids,IdMed,Custo},atomedico(Hora,Data,Idu,Ids,IdMed,Custo),L).


%--------------------------------------------------------------------------------------------
%------Predicado que lista os atos médicos para um serviço: Ids, L -> {V,F}------------------
%--------------------------------------------------------------------------------------------

atosMedicosPorServico(Ids,L) :-
    solucoes({Hora,Data,Idu,IdMed,Custo},atomedico(Hora,Data,Idu,Ids,IdMed,Custo),L).


%--------------------------------------------------------------------------------------------
%------Predicado que lista os atos médicos para uma instituicao: Inst, L -> {V,F}-----------
%--------------------------------------------------------------------------------------------

atosMedicosPorInstituicao(Inst,L) :-
    solucoes(Ids,cuidado(Ids,Desc,Inst,Cidade),L1),
    listarAtosMedicosPorIdServico(L1,L).


%--------------------------------------------------------------------------------------------
%------Predicado que lista os ids dos médicos de uma instituicao: Inst, L -> {V,F}-----------
%--------------------------------------------------------------------------------------------

idMedicosPorInstituicao(Inst,L) :-
    solucoes(Ids,cuidado(Ids,Desc,Inst,Cidade),L1),
    listarIdMedicosPorIdServico(L1,L).


%--------------------------------------------------------------------------------------------
%------Predicado que lista os serviços associados a um utente: Idu, L -> {V,F}---------------
%--------------------------------------------------------------------------------------------

servicosPorUtente(Idu,L) :-
    solucoes(Ids,atomedico(Hora,Data,Idu,Ids,IdMed,Custo),L1),
    listarInfoServicos(L1,L).


%--------------------------------------------------------------------------------------------
%------Predicado que calcula o custo total de atos medicos de um utente: Idu, R -> {V,F}----
%--------------------------------------------------------------------------------------------

calculoCustoTotalAtosUtente(Idu,R) :-
    solucoes(C, atomedico(_,_,Idu,_,_,C),X),
    somatorio(X,R).


%--------------------------------------------------------------------------------------------
%------Predicado que calcula o custo total dos atos medicos: R -> {V,F}----------------------
%--------------------------------------------------------------------------------------------

calculoCustoTotalAtos(R) :-
    solucoes(P,atomedico(_,_,_,_,_,P),X),
    somatorio(X,R).


%--------------------------------------------------------------------------------------------
%------Predicado que calcula o custo total dos atos medicos numa data: C, R -> {V,F}---------
%--------------------------------------------------------------------------------------------

calculoCustoTotalData(C,R) :-
    solucoes(P,atomedico(_,C,_,_,_,P),X),
    somatorio(X,R).

%--------------------------------------------------------------------------------------------
%------Predicado que calcula o custo total dos atos medicos de um cuidado: C, R -> {V,F}---------
%--------------------------------------------------------------------------------------------

calculoCustoTotalServico(C,R) :-
    solucoes(P,atomedico(_,_,_,C,_,P),X),
    somatorio(X,R).

%--------------------------------------------------------------------------------------------
%-------Predicado que calcula o custo total dos atos medicos numa instituição: C, R -> {V,F}---------
%--------------------------------------------------------------------------------------------

calculoCustoTotalInstituicao(IdI, L):-
  solucoes(IdS,cuidado(IdS,_,IdI,_),P),
  listaCustosServicos(P,L1),
  somatorio(L1,L).

listaCustosServicos([],[]).
listaCustosServicos([H|T],R):-
  listaCustosServicos(T,R1),
  solucoes(C, atomedico(_,_,_,H,_,C),X),
  somatorio(X,Y),
  acrescenta([Y],R1,R).


%--------------------------------------------------------------------------------------------
%------Predicado que mostra os utentes associados a um médico: C, R -> {V,F}-----------------
%--------------------------------------------------------------------------------------------

utentesPorMedico(C,R) :-
   solucoes(U,atomedico(_,_,U,_,C,_),R1),
   removerRepetidos(R1,R2),
   listarUtentesPorIds(R2,R).


%--------------------------------------------------------------------------------------------
%------Predicado que lista todos os médicos por Id: T, L -> {V,F}----------------------------
%--------------------------------------------------------------------------------------------

listarMedicosPorIds([],[]).
listarMedicosPorIds([H|T],L) :-
    medico(H,I1,I2,I3),
    listarMedicosPorIds(T,X),
    acrescenta([{H,I1,I2,I3}],X,L).


%--------------------------------------------------------------------------------------------
%------Predicado que determina os médicos de um utente: IdU, R -> {V,F}----------------------
%--------------------------------------------------------------------------------------------

medicoPorUtente(IdU,R) :-
    solucoes(IdM,atomedico(_,_,IdU,_,IdM,_),R1),
    removerRepetidos(R1,R2),
    listarMedicosPorIds(R2,R).


% ---------------------------------------------------------------------------------------------------------
%---------------------------------- INVARIANTES -----------------------------------------------------------
% ---------------------------------------------------------------------------------------------------------

% --------------- Invariantes de Inserção ---------------------------------------

%--------------------------------------------------------------------------------
%------Invariante que não permite a inserção de utentes com o mesmo ID-----------
%--------------------------------------------------------------------------------

+utente(Idu,Nome,Idade,Morada) :: (solucoes(Idu,utente(Idu,_,_,_),S),
                                  comprimento(S,N),
                                  N == 1).


%--------------------------------------------------------------------------------
%------Invariante que não  permite a inserção de médicos com o mesmo ID----------
%--------------------------------------------------------------------------------

+medico(IdMed,Nome,Idade,IdEsp) :: (solucoes(IdMed,medico(IdMed,_,_,_),S),
                                   comprimento(S,N),
                                   N == 1).
                                   
%--------------------------------------------------------------------------------
%------Invariante que não  permite a inserção de médicos com especialidade que não exista----------
%--------------------------------------------------------------------------------

+medico(IdMed,Nome,Idade,IdEsp) :: (especialidade(IdEsp,_)).


%--------------------------------------------------------------------------------
%------Invariante que não permite a inserção de especialidades com o mesmo ID----
%--------------------------------------------------------------------------------

+especialidade(IdEsp, Desig) :: (solucoes(IdEsp,especialidade(IdEsp,_),S),
                                   comprimento(S,N),
                                   N == 1).


%---------------------------------------------------------------------------------------------------------------------------------------------
%------Invariante que não permite a inserção de cuidados com o mesmo ID, ou, em alternativa, com a descrição, instituição e cidades iguais----
%---------------------------------------------------------------------------------------------------------------------------------------------

+cuidado(Ids,Desc,Inst,Cidade) :: (solucoes(Ids,cuidado(Ids,_,_,_),S),
                                   comprimento(S,N),
                                   N == 1,
                                   solucoes(Ids,cuidado(_,Desc,Inst,Cidade),S),
                                   comprimento(S,N),
                                   N == 1).


%------------------------------------------------------------------------------------------------------------
%------Invariante que não permite a inserção de atos médicos em que o custo seja inferior ou igual a zero----
%------------------------------------------------------------------------------------------------------------

+atomedico(Hora,Data,IdU,IdS,IdMed,Custo) :: Custo > 0.


%---------------------------------------------------------------------------------------------------------------------------------
%------Invariante que não permite a inserção de atos médicos em que a especialidade do cuidado seja diferente da do ato médico----
%---------------------------------------------------------------------------------------------------------------------------------

+atomedico(Hora,Data,IdU,IdS,IdMed,Custo) :: (medico(IdMed,_,_,IdEsp),
                                             cuidado(IdS,IdEsp,_,_)).

%-------------------------------------------------------------------------------------------------------------------
%------Invariante que não permite a inserção de um ato médico em que o utente, o serviço ou o médico não existam----
%-------------------------------------------------------------------------------------------------------------------

+atomedico(Hora,Data,IdU,IdS,IdMed,Custo) :: (solucoes(IdU, utente(IdU,_,_,_),S),
                                                   comprimento(S,N),
                                                   N == 1,
                                                   solucoes(IdMed, medico(IdMed,_,_,_),L),
                                                   comprimento(L,M),
                                                   M == 1,
                                                   solucoes(IdS, cuidado(IdS,_,_,_),R),
                                                   comprimento(R,Z),
                                                   Z == 1).


%---------------------------------------------------------------------------------------------------------------------
%------Invariante que não permite a inserção de um ato médico em que o médico já tenha consulta à hora estipulada-----
%---------------------------------------------------------------------------------------------------------------------

+atomedico(Hora,Data,IdU,IdS,IdMed,Custo) :: (solucoes(IdMed, atomedico(Hora,Data,_,_,IdMed,_),S),
                                             comprimento(S,N),
                                             N==1).


%-----------------------------------------------------------------------------------------------------------------
%------Invariante que não permite a inserção de um ato médico à mesma hora, na mesma data com o mesmo utente------
%-----------------------------------------------------------------------------------------------------------------

+atomedico(Hora,Data,IdU,IdS,IdMed,Custo) :: (solucoes(IdU, atomedico(Hora,Data,IdU,_,_,_),S),
                                              comprimento(S,N),
                                              N == 1).

% --------------- Invariantes de Remoção---------------------------------------

%---------------------------------------------------------------------------------------
%------Invariante que não permite a remoção de um utente com atos médicos marcados------
%---------------------------------------------------------------------------------------

-utente(Id, Nome, Idade, Morada) :: (solucoes(Id, atomedico(_,_,Id,_,_,_), S),
                                    comprimento(S,N),
                                    N==0).


%---------------------------------------------------------------------------------------
%------Invariante que não permite a remoção de um médico com atos médicos marcados------
%---------------------------------------------------------------------------------------

-medico(Id,Nome,Idade,IdEsp) :: (solucoes(Id,atomedico(_,_,_,_,Id,_),S),
                                 comprimento(S,N),
                                 N==0).


%--------------------------------------------------------------------------------------------
%------Invariante que não permite a remoção de uma especialidade se um médico a possuir------
%--------------------------------------------------------------------------------------------

-especialidade(IdEsp, Desc) :: (solucoes(IdEsp,medico(_,_,_,IdEsp),S),
                                comprimento(S,N),
                                N==0).


%----------------------------------------------------------------------------------------------
%------Invariante que não permite a remoção de uma especialidade se um cuidado a possuir-------
%----------------------------------------------------------------------------------------------

-especialidade(IdEsp, Desc) :: (solucoes(IdEsp,cuidado(_,IdEsp,_,_),S),
                                comprimento(S,N),
                                N==0).

