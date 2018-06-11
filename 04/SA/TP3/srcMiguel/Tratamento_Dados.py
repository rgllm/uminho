#!/usr/bin/env python3

import numpy as np
import matplotlib.pyplot as plt
import pandas as pd  # to read csv
import csv


class Tratamento_Dados():
	def load_preprocessed_csv(self, file_name=None):
		if not file_name:
			file_name = './data/sales_dataset_for_ANN.csv'  # sales_name

		column_names = ['Advertising', 'Jan', 'Fev', 'Mar', 'Apr',
						'May', 'Jun', 'Jul', 'Ago', 'Sep', 'Oct', 'Nov', 'Dec', 'Spring',
						'Summer', 'Autumn', 'Winter', 'fst_tri', 'snd_tri', 'trd_tri',
						'fth_tri', 'fst_sem', 'snd_sem', 'Sales']

		# fica numa especie de tabela exactamente como estava no csv (1350 linhas,7 colunas)
		sales = pd.read_csv(file_name, header=0, names=column_names, sep=",")

		# Por usar o pandas já vem em dataframe. Se fosse lido com numpy  era necessário
		df = pd.DataFrame(sales)  # neste caso não vai fazer nada

		return df

	def get_sales_data(self, file_name=None):
		if not file_name:
			file_name = './data/advertising-and-sales-data-36-co.csv'  # sales_name

		column_names = ['Month', 'Advertising', 'Sales']

		# fica numa especie de tabela exactamente como estava no csv (1350 linhas,7 colunas)
		sales = pd.read_csv(file_name, header=0, names=column_names, sep=",")

		# Por usar o pandas já vem em dataframe. Se fosse lido com numpy  era necessário
		df = pd.DataFrame(sales)  # neste caso não vai fazer nada

		# new attributes to mark the month and weather stations
		new_attr = ['Jan', 'Fev', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Ago',
					'Sep', 'Oct', 'Nov', 'Dec', 'Spring', 'Summer', 'Autumn', 'Winter']
		for att in new_attr:
			df[str(att)] = 0

		# new attributes to mark trimester and semester
		new_attr = ['fst_tri', 'snd_tri', 'trd_tri',
					'fth_tri', "fst_sem", "snd_sem"]
		for att in new_attr:
			df[str(att)] = 0

		# Partir a data para que as informações sejam efetivamente uteis e usadas pela rede
		date_split = df['Month'].str.split('-').str
		df['Month'] = date_split[1]

		nrows = df.shape[0]
		for i in range(0, nrows):
			mes = int(df.loc[:, "Month"][i])
			if(mes == 1):
				df.at[i, "Jan"] = 1
			elif(mes == 2):
				df.at[i, "Fev"] = 1
			elif(mes == 3):
				df.at[i, "Mar"] = 1
			elif(mes == 4):
				df.at[i, "Apr"] = 1
			elif(mes == 5):
				df.at[i, "May"] = 1
			elif(mes == 6):
				df.at[i, "Jun"] = 1
			elif(mes == 7):
				df.at[i, "Jul"] = 1
			elif(mes == 8):
				df.at[i, "Ago"] = 1
			elif(mes == 9):
				df.at[i, "Sep"] = 1
			elif(mes == 10):
				df.at[i, "Oct"] = 1
			elif(mes == 11):
				df.at[i, "Nov"] = 1
			else:
				df.at[i, "Dec"] = 1

			# atributos Estação ano
			if(mes >= 3 and mes <= 5):
				df.at[i, "Spring"] = 1
			elif(mes >= 6 and mes <= 8):
				df.at[i, "Summer"] = 1
			elif(mes >= 9 and mes <= 11):
				df.at[i, "Autumn"] = 1
			else:
				df.at[i, "Winter"] = 1

			# Atributos para o trimestre e semestre
			if(mes < 4):
				df.at[i, "fst_tri"] = 1
				df.at[i, "fst_sem"] = 1
			if(mes < 6):
				df.at[i, "snd_tri"] = 1
				df.at[i, "fst_sem"] = 1
			if(mes < 9):
				df.at[i, "trd_tri"] = 1
				df.at[i, "snd_sem"] = 1
			else:
				df.at[i, "fth_tri"] = 1
				df.at[i, "snd_sem"] = 1

		df.drop(df.columns[[0]], axis=1,
				inplace=True)  # remover coluna inicial do mês

		# no final, fica com as colunas
		# ['Advertising', 'Jan', 'Fev', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Ago',
		# 'Sep', 'Oct', 'Nov', 'Dec',
		# 'Spring', 'Summer', 'Autumn', 'Winter', --> estações ano
		# 'fst_tri', 'snd_tri', 'trd_tri', 'fth_tri', -> trimestres
		# 'fst_sem', 'snd_sem', 'Sales'] -> valores iniciais +  semestres
		# print(df.columns.tolist())

		#Swap sales colum to the end, because the ANN expects the regression label as
		# the last column
		df["sales"] = df['Sales']
		df.drop(df.columns[[1]], axis=1,
				inplace=True)  # remover coluna inicial da sales, pq passou para o final

		self.save_csv(df.as_matrix(), "sales_dataset_for_ANN.csv")
		return df

	def pre_processar_sales_dataset(self, df):
		# Normalização dos valores
		# como estão na ordem das dezenas, podemos justificar a normalização
		# se à entreda /10 --> o valor de saida da ANN multiplica por 10

		df['sales'] = df['sales'] / 10
		df['Advertising'] = df['Advertising'] / 10
		return df

	# Visualizar os top registos da tabela
	def load_sales_dataset(self):
		file_name = './data/advertising-and-sales-data-36-co.csv'

		df = self.get_sales_data(file_name)
		df = self.pre_processar_sales_dataset(df)

	# Função para guardar um csv

	def save_csv(self, M_data, file_name):
		file_name = "./data/" + str(file_name)

		with open(file_name, "w+", newline='', encoding='utf-8') as f:
			writer = csv.writer(f, delimiter=",")
			falhou = 0
			for row in range(0, len(M_data)):
				try:
					writer.writerow(M_data[row])
				except Exception as e:
					falhou += 1
					f.close()
			print("Save " + file_name + " terminado. Falhou " +
				  str(falhou) + " linhas.")
