
import java.util.Objects;
import java.util.List;
import java.util.ArrayList;
public class Vendedor extends Utilizador{
    ArrayList<Imovel> portfolio;
    ArrayList<Imovel> historico;

    public ArrayList<Imovel> getPortfolio() {return portfolio;}
    public ArrayList<Imovel> getHistorico() {return historico;}
    
    public void setPortfolio(ArrayList<Imovel> portfolio){this.portfolio = (ArrayList<Imovel>)portfolio.clone();}
    public void setHistorico(ArrayList<Imovel> historico){this.historico = (ArrayList<Imovel>)historico.clone();}


    public boolean equals(Object obj) {
        if (this == obj) {return true;}
        if (obj == null) {return false;}
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Vendedor other = (Vendedor) obj;
        if (Objects.equals(this.portfolio, other.portfolio) &&
            Objects.equals(this.historico, other.historico)) {
            return true;
        }
        return false;
    }


/**
* Colocar um imóvel à venda;
*/
/*
public void registaImovel(Imovel im) throws ImovelExisteException , SemAutorizacaoException{}
*/
/**
* Visualizar uma lista com as datas (e emails, caso exista essa informação) das
* 10 últimas consultas aos imóveis que tem para venda
*/
/*
public List<Consulta> getConsultas() throws SemAutorizacaoException{}
*/
/**
* Alterar o estado de um imóvel, de acordo com as acções feitas sobre ele
*/
/*
public void setEstado(String idImovel , String estado) throws ImovelInexistenteException , SemAutorizacaoException , EstadoInvalidoException{}
*/
/**
* Obter um conjunto com os códigos dos imóveis
* mais consultados (ou seja, com mais de N consultas)
*/
/*
public Set<String> getTopImoveis(int n){}
*/
}

