import numpy as np
import matplotlib.pyplot as plt
import pandas as pd # to read csv
import math, time # time for mesuaring performance 
import datetime

from keras.models import Sequential
from keras.layers.core import Dense, Dropout, Activation
from keras.layers.recurrent import LSTM

# fixar random seed para se puder reproduzir os resultados
seed = 9
np.random.seed(seed)

# Etapa 1 - preparar o dataset
'''
fazer o download da sequencias do valor das ações da google GOOGL stock data (fonte yahoo.com)
dataset:
http://chart.finance.yahoo.com/table.csv?s=GOOGL&a=11&b=15&c=2011&d=29&e=10&f=2016&g=d&ignore=.csv
A função get_stock_data é generica para ir buscar dados à yahoo.com
trata-se uma tabela com: ['Date','Open','High','Low','Close','Volume','Adj Close']
Vamos śo utilizar os campos ['Open','High','Close']
'''
def get_stock_data(stock_name, normalized=0,file_name=None):
	if not file_name:
		file_name = 'http://chart.finance.yahoo.com/table.csv?s=%s&a=11&b=15&c=2011&d=29&e=10&f=2016&g=d&ignore=.csv' # stock_name
	col_names = ['Date','Open','High','Low','Close','Volume','Adj Close']

	#fica numa especie de tabela exactamente como estava no csv (1350 linhas,7 colunas)
	stocks = pd.read_csv(file_name, header=0, names=col_names) 
	
	# Por usar o pandas já vem em dataframe. Se fosse lido com numpy  era necessário
	df = pd.DataFrame(stocks) # neste caso não vai fazer nada
	
	# Partir a data para que as informações sejam efetivamente uteis e usadas pela rede
	date_split = df['Date'].str.split('-').str # não vai servir para nada
	df['Year'], df['Month'], df['Day'] = date_split # não vai servir para nada

	# Se for relevante avaliar atributos como "feriado" ou "dia da semana", cria-se um conjunto
	# de atributos binários que avaliem essas questões
	# Sendo um dataset de ações, já só vai ter dias da semana. Sem dias de fim de semana
	# contudo, distinguir segunda feira da sexta feira é relevante no contexto de bolsa
	# segunda tem alta afluencia (pos fds) e sextas têm poucas transações (fecho semanal bolsa)

	df["Volume"] = df["Volume"] / 10000 # não vai servir para nada
	df.drop(df.columns[[0,3,5,6, 7,8,9]], axis=1, inplace=True) # vou só ficar com as colunas 1,2,4
	return df

def load_GOOGL_stock_dataset():
	stock_name = 'GOOGL'
	return get_stock_data(stock_name, 0, 'table.csv')

def pre_processar_GOOGL_stock_dataset(df):
	# Normalização dos valores
	# como estão na ordem das centenas, normaliza
	# se à entreda /100, à saida multiplica por 100

	df['High'] = df['High'] / 100
	df['Open'] = df['Open'] / 100
	df['Close'] = df['Close'] / 100
	return df

# Visualizar os top registos da tabela
def visualize_GOOGL():
	df = load_GOOGL_stock_dataset()
	print('### Antes do pré-processamento ###')
	print(df.head()) #mostra só os primeiros 5 registos
	df = pre_processar_GOOGL_stock_dataset(df)
	print('### Após o pré-processamento ###')
	print(df.head())


#função load_data do lstm.py configurada para aceitar qualquer número de parametros
#o último atributo é que fica como label (resultado)
#stock é um dataframe do pandas (uma especie de dicionario + matriz)
#seq_len é o tamanho da janela a ser utilizada na serie temporal
def load_data(df_dados, janela):
	qt_atributos = len(df_dados.columns)

	#converter dataframe para matriz (lista com lista de cada registo)
	mat_dados = df_dados.as_matrix() 

	#Tamanho da janela define a quantidade de casos passados
	# janela 7 -> 7 dias anterioes
	# janela 30 -> mês anterior 

	# Se a janela for de N, perde-se N + 1 casos. 
	# Por exemplo, se a janela for 30, pede os 30 primeiros casos para avaliar o 31º dia
	
	tam_sequencia = janela + 1
	
	res = []
	for i in range(len(mat_dados) - tam_sequencia): #numero de registos - tamanho da sequencia
		res.append(mat_dados[i: i + tam_sequencia])
	
	res = np.array(res) #dá como resultado um np com uma lista de matrizes (janela deslizante ao longo da serie)
	
	qt_casos_treino = int(round(0.9 * res.shape[0])) #90% passam a ser casos de treino
	
	train = res[:qt_casos_treino, :]
	x_train = train[:, :-1] #menos um registo pois o ultimo registo é o registo a seguir à janela
	y_train = train[:, -1][:,-1] #para ir buscar o último atributo para a lista dos labels
	
	# usa menos um registo por causa da janela deslizante. 
	x_test = res[qt_casos_treino:, :-1]
	y_test = res[qt_casos_treino:, -1][:,-1]
	
	x_train = np.reshape(x_train, (x_train.shape[0], x_train.shape[1], qt_atributos))
	x_test = np.reshape(x_test, (x_test.shape[0], x_test.shape[1], qt_atributos))

	return [x_train, y_train, x_test, y_test]

# Etapa 2 - Definir a topologia da rede (arquitectura do modelo) e compilar '''
def build_model2(janela):
	model = Sequential()
	# return sequences = true -> passa os valores processados para a frente 
	model.add(LSTM(128, input_shape=(janela, 3), return_sequences=True))
	
	model.add(Dropout(0.2))

	# return sequences = false, por ser a ultima camada LSTM 
	model.add(LSTM(64, input_shape=(janela, 3), return_sequences=False))
	
	#model.add(Dropout(0.2))
	
	model.add(Dense(16, activation="relu", kernel_initializer="uniform"))
	
	# Por ser regressão de valores de ação da bolsa, linear passa o valor que recebe
	# Relu tambem podia ser, pq nao há ações negativos 
	model.add(Dense(1, activation="linear", kernel_initializer="uniform"))
	
	model.compile(loss='mse', optimizer='sgd',metrics=['accuracy'])
	return model


#imprime um grafico com os valores de teste e com as correspondentes tabela de previsões
def print_series_prediction(y_test,predic):
	diff=[]
	racio=[]
	for i in range(len(y_test)): #para imprimir tabela de previsoes
		racio.append( (y_test[i]/predic[i])-1)
		diff.append( abs(y_test[i]- predic[i]))
		print('valor: %f ---> Previsão: %f Diff: %f Racio: %f' % (y_test[i],predic[i], diff[i], racio[i]))
	
	plt.plot(y_test,color='blue', label='y_test')
	plt.plot(predic,color='red', label='prediction') #este deu uma linha em branco
	plt.plot(diff,color='green', label='diff')
	plt.plot(racio,color='yellow', label='racio')
	plt.legend(loc='upper left')
	plt.show()

def LSTM_utilizando_GOOGL_data():
	df = load_GOOGL_stock_dataset()
	df = pre_processar_GOOGL_stock_dataset(df)
	print("df", df.shape)

	# 22 dias = mês acionista (não há fim de semana na bolsa)
	janela = 22 #tamanho da Janela deslizante
	
	# As datas estão por ondem decrescente. Se não for alterado, a rede treina previsão para o passado
	# Ao inverter, a rede treina o padrão à medida que o mesmo evolui temporalmente para o presente 
	X_train, y_train, X_test, y_test = load_data(df[::-1], janela)# o df[::-1] é o df por ordem inversa
	
	# prints só para confirmação 
	print("X_train", X_train.shape)
	print("y_train", y_train.shape)
	print("X_test", X_test.shape)
	print("y_test", y_test.shape)
	
	#model = build_model(janela)
	model = build_model2(janela)

	# validations split com 10% dos 90% dos dados anteriormente filtrados para treino 
	#model.fit(X_train, y_train, batch_size=512, epochs=500, validation_split=0.1, verbose=1)
	model.fit(X_train, y_train, batch_size=250, epochs=250, validation_split=0.1, verbose=1)
	
	# print_model(model,"lstm_model.png")
	print(model.summary())

	trainScore = model.evaluate(X_train, y_train, verbose=0)
	print('Train Score: %.2f MSE (%.2f RMSE)' % (trainScore[0], math.sqrt(trainScore[0])))
	
	testScore = model.evaluate(X_test, y_test, verbose=0)
	print('Test Score: %.2f MSE (%.2f RMSE)' % (testScore[0], math.sqrt(testScore[0])))
	print(model.metrics_names)
	
	p = model.predict(X_test)
	predic = np.squeeze(np.asarray(p)) #para transformar uma matriz de uma coluna e n linhas em um np array de n elementos
	print_series_prediction(y_test,predic)
	
	''' MSE- (Mean square error), RMSE- (root mean square error) –
	o significado de RMSE depende do range da label. para o mesmo range menor é melhor.
	'''

if __name__ == '__main__':
	#visualize_GOOGL()
	LSTM_utilizando_GOOGL_data()