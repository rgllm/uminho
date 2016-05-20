import java.util.Objects;
import java.lang.*;

public class Terreno extends Imovel{

    private double areaConstrucao;
    private boolean habitacao;
    private boolean armazem;
    private double diamCanalizacoes;
    private boolean eletricidade;
    private double potenciaEletrica;
    private boolean esgotos;

    /*           Construtores         */
    public Terreno(){
        areaConstrucao=0.0;
        habitacao=false;
        armazem=false;
        diamCanalizacoes=0.0;
        eletricidade=false;
        potenciaEletrica=0.0;
        esgotos=false;
        gerarID();
    }

    public Terreno(int consultas,String rua,double preco,double precoMinimo,double areaConstrucao,boolean habitacao,boolean armazem,double diamCanalizacoes,boolean eletricidade,double potenciaEletrica,boolean esgotos,Estado_Imovel estado){
        super(consultas,rua,preco,precoMinimo,estado);
        this.areaConstrucao=areaConstrucao;
        this.habitacao=habitacao;
        this.armazem=armazem;
        this.diamCanalizacoes=diamCanalizacoes;
        this.eletricidade=eletricidade;
        this.potenciaEletrica=potenciaEletrica;
        this.esgotos=esgotos;
        gerarID();
    }

    public Terreno(Terreno x){
        this.areaConstrucao=x.getAreaConstrucao();
        this.habitacao=x.isHabitacao();
        this.armazem=x.isArmazem();
        this.diamCanalizacoes=x.getDiamCanalizacoes();
        this.eletricidade=x.isEletricidade();
        this.potenciaEletrica=x.getPotenciaEletrica();
        this.esgotos=x.isEsgotos();
    }

    /*      Métodos de instância    */
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

    public Terreno clone(){
        return new Terreno(this);
    }

    public void gerarID(){
        String id;
        id=super.gerarIDImovel();
        super.setId(id);
        /*int id=0;
        long bits;
        String stringID;

        id=super.gerarIDImovel();
        id+=this.getClass().getSimpleName().hashCode();
        bits=Double.doubleToLongBits(areaConstrucao);
        id+=(int)(bits ^ (bits >>> 32));
        if(habitacao==true) id++;
        if(armazem==true) id++;
        bits=Double.doubleToLongBits(diamCanalizacoes);
        id+=(int)(bits ^ (bits >>> 32));
        if(eletricidade==true) id++;
        bits=Double.doubleToLongBits(potenciaEletrica);
        id+=(int)(bits ^ (bits >>> 32));
        if(esgotos==true) id++;

        stringID=id+"";
        super.setId(stringID);*/
    }

}
