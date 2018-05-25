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

def print_history_accuracy(history):
    print(history.history.keys())
    plt.plot(history.history['acc'])
    plt.plot(history.history['val_acc'])
    plt.title('model accuracy')
    plt.ylabel('accuracy')
    plt.xlabel('epoch')
    plt.legend(['train', 'test'], loc='upper left')
    plt.show()

def print_history_loss(history):
    print(history.history.keys())
    plt.plot(history.history['loss'])
    plt.plot(history.history['val_loss'])
    plt.title('model loss')
    plt.ylabel('loss')
    plt.xlabel('epoch')
    plt.legend(['train', 'test'], loc='upper left')
    plt.show()

def load_dataset(normalized=0,file_name=None):
    col_names = ['Jan','Fev','Mar','Apr','May','Jun','Jul','Ago','Sep','Oct','Nov','Dec','Spring','Summer','Autumn','Winter','Advertising','Sales']
    stocks = pd.read_csv(file_name, header=0, names=col_names, sep=";")
    df = pd.DataFrame(stocks)
    return df

def pre_processar_dataset(df):
    df['Sales'] = df['Sales'] / 10
    df['Advertising'] = df['Advertising'] / 10
    return df

def load_data(df_dados, janela):
    qt_atributos = len(df_dados.columns)
    mat_dados = df_dados.as_matrix()
    tam_sequencia = janela + 1
    res = []
    for i in range(len(mat_dados) - tam_sequencia):
        res.append(mat_dados[i: i + tam_sequencia])
    res = np.array(res)
    qt_casos_treino = int(round(0.68 * res.shape[0]))
    train = res[:qt_casos_treino, :]
    x_train = train[:, :-1]
    y_train = train[:, -1][:,-1]
    x_test = res[qt_casos_treino:, :-1]
    y_test = res[qt_casos_treino:, -1][:,-1]
    x_train = np.reshape(x_train, (x_train.shape[0], x_train.shape[1], qt_atributos))
    x_test = np.reshape(x_test, (x_test.shape[0], x_test.shape[1], qt_atributos))
    return [x_train, y_train, x_test, y_test]

def build_model2(janela):
    model = Sequential()
    model.add(LSTM(128, input_shape=(janela, 18), return_sequences=True))
    model.add(Dropout(0.2))
    model.add(LSTM(64, input_shape=(janela, 18), return_sequences=False))
    model.add(Dense(16, activation="relu", kernel_initializer="uniform"))
    model.add(Dense(1, activation="linear", kernel_initializer="uniform"))
    model.compile(loss='mse',optimizer='adam',metrics=['accuracy','mean_squared_error'])
    return model

def print_series_prediction(y_test,predic):
    diff=[]
    racio=[]
    for i in range(len(y_test)):
        racio.append( (y_test[i]/predic[i])-1)
        diff.append( abs(y_test[i]- predic[i]))
        print('valor: %f ---> Previsão: %f Diff: %f Racio: %f' % (y_test[i],predic[i], diff[i],racio[i]))
    plt.plot(y_test,color='blue', label='y_test')
    plt.plot(predic,color='red', label='prediction')
    plt.plot(diff,color='green', label='diff')
    plt.plot(racio,color='yellow', label='racio')
    plt.legend(loc='upper left')
    plt.show()

def LSTM_data():
    df = load_dataset(0,"tratado.csv")
    df = pre_processar_dataset(df)
    janela = 1
    X_train, y_train, X_test, y_test = load_data(df[::1], janela)

    model = build_model2(janela)

    model.fit(X_train, y_train, batch_size=5, epochs=200, verbose=1)
    print_model(model,"lstm_model.png")
    trainScore = model.evaluate(X_train, y_train, verbose=1)
    print('Train Score: %.2f MSE (%.2f RMSE)' % (trainScore[0], math.sqrt(trainScore[0])))
    testScore = model.evaluate(X_test, y_test, verbose=0)
    print('Test Score: %.2f MSE (%.2f RMSE)' % (testScore[0], math.sqrt(testScore[0])))
    print(model.metrics_names)
    p = model.predict(X_test)
    predic = np.squeeze(np.asarray(p)) 
    print_series_prediction(y_test,predic)

def print_model(model,fich):
    from keras.utils import plot_model
    plot_model(model, to_file=fich, show_shapes=True, show_layer_names=True)

if __name__ == '__main__':
    LSTM_data()