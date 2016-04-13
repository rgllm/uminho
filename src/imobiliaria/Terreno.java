public class Terreno extends Imovel{
    private double areaConstrucao;
    private boolean habitacao;
    private boolean armazem;
    private double diamCanalizacoes;
    private boolean eletricidade;
    private double potenciaEletrica;
    private boolean esgotos;

    public double getAreaConstrucao() {return areaConstrucao;}
    public boolean isHabitacao() {return habitacao;}
    public boolean isArmazem() {return armazem;}
    public double getDiamCanalizacoes() {return diamCanalizacoes;}
    public boolean isEletricidade() {return eletricidade;}
    public double getPotenciaEletrica() {return potenciaEletrica;}
    public boolean isEsgotos() {return esgotos;}

    public void setAreaConstrucao(double areaConstrucao) {this.areaConstrucao = areaConstrucao;}
    public void setHabitacao(boolean habitacao) {this.habitacao = habitacao;}
    public void setArmazem(boolean armazem) {this.armazem = armazem;}
    public void setDiamCanalizacoes(double diamCanalizacoes) {this.diamCanalizacoes = diamCanalizacoes;}
    public void setEletricidade(boolean eletricidade) {this.eletricidade = eletricidade;}
    public void setPotenciaEletrica(double potenciaEletrica) {this.potenciaEletrica = potenciaEletrica;}
    public void setEsgotos(boolean esgotos) {this.esgotos = esgotos;}

    public boolean equals(Object obj) {
        if (this == obj) {return true;}
        if (obj == null) {return false;}
        if (getClass() != obj.getClass()) {return false;}
        final Terreno other = (Terreno) obj;
        if (this.areaConstrucao == other.areaConstrucao &&
            this.habitacao == other.habitacao &&
            this.armazem == other.armazem &&
            this.diamCanalizacoes == other.diamCanalizacoes &&
            this.eletricidade == other.eletricidade &&
            this.potenciaEletrica == other.potenciaEletrica &&
            this.esgotos == other.esgotos ){
            return true;}
        else return false;
    }

    public String toString() {
        return "Terreno{" + "areaConstrucao=" + areaConstrucao + ", habitacao=" + habitacao + ", armazem=" + armazem + ", diamCanalizacoes=" + diamCanalizacoes + ", eletricidade=" + eletricidade + ", potenciaEletrica=" + potenciaEletrica + ", esgotos=" + esgotos + '}';
    }



}
