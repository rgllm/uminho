#!/usr/bin/env python3

import numpy as np
import matplotlib.pyplot as plt
import pandas as pd
import math, time
import datetime
from keras.models import Sequential
from keras.layers.core import Dense, Dropout, Activation
from keras.layers.recurrent import LSTM

seed = 9
np.random.seed(seed)

class ANN_LSTM(): 

	#------------------------------------------------------------------------
	''' Função para construir a rede com uma determinada topologia LSTM
		Deve ser adequada ao tamado da janela e complexidade que se procure realizar '''
	def build_model(self, janela):
		nr_nodos = self.nr_nodos
		
		model = Sequential()

		# return sequences = true -> passa os valores processados para a frente 
		model.add(LSTM(256, input_shape=(janela, nr_nodos), return_sequences=True))
		model.add(Dropout(0.2))
		
		model.add(LSTM(128, input_shape=(janela, nr_nodos), return_sequences=True))

		# return sequences = false, na ultima camada LSTM 
		model.add(LSTM(64, input_shape=(janela, nr_nodos), return_sequences=False))
		
		# ANN "normal"
		model.add(Dense(50, activation="relu", kernel_initializer="uniform"))
		model.add(Dense(16, activation="relu", kernel_initializer="uniform"))
		model.add(Dense(1, activation="linear", kernel_initializer="uniform"))
		
		# Para evitar minimos locais no optimizador, explorar outras variantes: 
		# -> sgd 		adam		 RMSprop	Ada[grad|delta] 	...
		model.compile(loss='mse',optimizer='adam',metrics=['accuracy','mean_squared_error'])
		return model
	#------------------------------------------------------------------------

	#------------------------------------------------------------------------
	''' Função para partir o dataset em dados de treino e teste
	Primeiros dois anos utilizados para treino da ANN. 
	Ultimo (3º) ano usado para teste '''
	def load_data(self, df_dados, janela):
		qt_atributos = len(df_dados.columns)

		#converter dataframe para matriz (lista com lista de cada registo)
		mat_dados = df_dados.as_matrix()
		
		# Tamanho da janela define a quantidade de casos passados.
		# Para este dataset, cada linha é um mês! 
		# janela 1 -> usa 1 mês anterior
		# janela 3 -> usa 3 meses anteriores (trimestre)
		# janela 6 -> usa 6 meses anteriores. 
		# - janelas maiores exigem uma maior complexidade na aprendizagem e topologia da rede 
		# - Se a janela for de N, perde-se N + 1 casos. 
		tam_sequencia = janela + 1
		
		res = []
		# for i in  (linhas_dataset - tamanho da janela) 
		for i in range(len(mat_dados) - tam_sequencia):
			res.append(mat_dados[i: i + tam_sequencia])
		res = np.array(res)
		
		# Os primeiros dois anos são para treino. 
		# Dada a ordem dos dados, as 24 primeiras linhas são para treino. 
		qt_casos_treino = 23 # [0 .. 23] = 24 casos dos 2 primeiros anos

		train = res[:qt_casos_treino, :]
		x_train = train[:, :-1] #menos um registo pois o ultimo registo é o registo a seguir à janela
		y_train = train[:, -1][:,-1]  #para ir buscar o último atributo para a lista dos labels

		# usa menos um registo por causa da janela deslizante no final 
		x_test = res[qt_casos_treino:, :-1]
		y_test = res[qt_casos_treino:, -1][:,-1]

		x_train = np.reshape(x_train, (x_train.shape[0], x_train.shape[1], qt_atributos))
		x_test = np.reshape(x_test, (x_test.shape[0], x_test.shape[1], qt_atributos))

		return [x_train, y_train, x_test, y_test]
	#------------------------------------------------------------------------

	#------------------------------------------------------------------------
	''' Função para realizar o load do dataset, treinar e testar a rede '''
	def LSTM_data(self, df):
		# ['Advertising', -> valores iniciais
		# 'Jan', 'Fev', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Ago',
		# 'Sep', 'Oct', 'Nov', 'Dec',
		# 'Spring', 'Summer', 'Autumn', 'Winter', --> estações ano
		# 'fst_tri', 'snd_tri', 'trd_tri', 'fth_tri', -> trimestres
		# 'fst_sem', 'snd_sem', 'Sales'] -> semestres
		
		# ignorar dados de trimestre e semestre 
		df.drop(df.columns[[range(17,df.shape[1]-1)]], axis=1,
				inplace=True)
		self.nr_nodos = df.shape[1]

		#tamanho da Janela deslizante
		janela = 1	# analise com influencia do mês anterior 

		''' As datas estão por ondem crescente, logo a rede treina com os casos num sentido progressivo no tempo
			Se as ordem temporal fosse decrescente, tinha que se inverter, para a rede não aprender
		a tendencia andando para o passado. # o df[::-1] é o df por ordem inversa
		'''
		X_train, y_train, X_test, y_test = self.load_data(df, janela)
		
		'''# prints só para confirmação 
		print("X_train", X_train.shape)
		print("y_train", y_train.shape)
		print("X_test", X_test.shape)
		print("y_test", y_test.shape) '''
		 
		# Construir a topologia da rede 
		model = self.build_model(janela)

		# Treinar a rede 
		# validations split com 10% dos 24 casos dos dados anteriormente filtrados para treino 
		model.fit(X_train, y_train, batch_size=1, epochs=500, verbose=2)

		#print_model(model,"lstm_model.png")
		
		# Testar a rede com dados de treino -> possivel overfiting 
		# util para perceber se a rede entendeu a tendência
		trainScore = model.evaluate(X_train, y_train, verbose=1)
		print('Train Score: %.2f MSE (%.2f RMSE)' % (trainScore[0], math.sqrt(trainScore[0])))
		
		# Testar a rede com dados de testes --> Teste efetivamente relevante 
		testScore = model.evaluate(X_test, y_test, verbose=0)
		print('Test Score: %.2f MSE (%.2f RMSE)' % (testScore[0], math.sqrt(testScore[0])))
		
		print(model.metrics_names)
		
		p = model.predict(X_test)
		#para transformar uma matriz de uma coluna e n linhas num np array de n elementos
		predic = np.squeeze(np.asarray(p)) 
		self.print_series_prediction(y_test,predic)
	#------------------------------------------------------------------------

	#------------------------------------------------------------------------
	''' Função para imprimir um esquema com a topologia da rede -> erro em import do keras'''
	def print_model(self, model,fich):
		from keras.utils import plot_model
		plot_model(model, to_file=fich, show_shapes=True, show_layer_names=True)
	#------------------------------------------------------------------------

	#------------------------------------------------------------------------
	''' Função para imprimir o gráfico final com a accuracy da rede nas diferentes fases '''
	def print_series_prediction(self, y_test, predic):
		diff=[]
		racio=[]

		for i in range(len(y_test)): #para imprimir tabela de previsoes
			racio.append( (y_test[i]/predic[i])-1)
			diff.append( abs(y_test[i]- predic[i]))
			print('valor: %f ---> Previsão: %f Diff: %f Racio: %f' % (y_test[i],predic[i], diff[i],racio[i]))
		
		plt.plot(y_test,color='blue', label='y_test')
		plt.plot(predic,color='red', label='prediction')
		plt.plot(diff,color='green', label='diff')
		plt.plot(racio,color='yellow', label='racio')
		plt.legend(loc='upper left')
		plt.show()

	''' Funções para imprimir histórico de accuracy da ANN '''
	def print_history_accuracy(self, history):
		print(history.history.keys())
		plt.plot(history.history['acc'])
		plt.plot(history.history['val_acc'])
		plt.title('model accuracy')
		plt.ylabel('accuracy')
		plt.xlabel('epoch')
		plt.legend(['train', 'test'], loc='upper left')
		plt.show()

	def print_history_loss(self, history):
		print(history.history.keys())
		plt.plot(history.history['loss'])
		plt.plot(history.history['val_loss'])
		plt.title('model loss')
		plt.ylabel('loss')
		plt.xlabel('epoch')
		plt.legend(['train', 'test'], loc='upper left')
		plt.show()
	#------------------------------------------------------------------------