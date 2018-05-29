
#!/usr/bin/env python3

import Tratamento_Dados
import ANN_LSTM

def main():
    T =  Tratamento_Dados.Tratamento_Dados()
    #df = T.load_sales_dataset() # -> only done once, to generate the processed csv
    df = T.load_preprocessed_csv()

    ANN = ANN_LSTM.ANN_LSTM()
    rede = ANN .LSTM_data(df)
    

if __name__ == "__main__": main()