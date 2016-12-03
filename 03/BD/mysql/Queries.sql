-- Nome dos utilizadores que compraram bilhetes para Lisboa em viagens já efetuadas

SELECT Nome FROM Utilizador WHERE Email in
(select Utilizador_Email from Reserva where Viagem_Id in 
(SELECT Id FROM Viagem WHERE Destino='Lisboa' AND `Data Chegada` <NOW()));


-- Número de passageiros que viajaram no comboio 2

SELECT count(*) AS 'Passageiros no Comboio 2' from 
    Viagem join Reserva on Reserva.Viagem_Id = Viagem.Id
	join Comboio on Viagem.Comboio_Id=Comboio.Id
    where Comboio.Id=2 and Viagem.`Data Chegada`<NOW() ;


-- Tempo de serviço do comboio 2

select sum('Tempo Serviço') from
(SELECT time_to_sec(TIMEDIFF(`Data Chegada`,`Data Partida`)) AS 'Tempo Serviço' FROM 
		VIAGEM WHERE Comboio_Id=2) as x;
        

-- Duração das viagens do comobio 2

SELECT TIMEDIFF(`Data Chegada`,`Data Partida`) AS 'Tempo Serviço' FROM 
		VIAGEM WHERE Comboio_Id=2;