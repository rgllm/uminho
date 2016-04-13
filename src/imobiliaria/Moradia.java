
import java.util.Objects;

public class Moradia extends Imovel{
    private String tipo;
    private double areaImplantacao;
    private double areaCoberta;
    private double areaTerreno;
    private int nQuartos;
    private int nWCs;
    private int nPorta;

    /*           Construtores         */
    public Moradia(){
        super("","",0.0,0.0);
        tipo=new String("");
        areaImplantacao=0.0;
        areaCoberta=0.0;
        areaTerreno=0.0;
        nQuartos=0;
        nWCs=0;
        nPorta=0;       
    }

    public Moradia(String id,String rua,double preco,double precoMinimo,String tipo,double areaImplantacao,double areaCoberta,double areaTerreno,int nQuartos,int nWCs,int nPorta){
        super(id,rua,preco,precoMinimo);
        this.tipo=new String(tipo);
        this.areaImplantacao=areaImplantacao;
        this.areaCoberta=areaCoberta;
        this.areaTerreno=areaTerreno;
        this.nQuartos=nQuartos;
        this.nWCs=nWCs;
        this.nPorta=nPorta;
    }

    public Moradia(Moradia x){
        super(x.getId(),x.getRua(),x.getPreco(),x.getPrecoMinimo());
        this.tipo=new String(x.getTipo());
        this.areaImplantacao=x.getAreaImplantacao();
        this.areaCoberta=x.getAreaCoberta();
        this.areaTerreno=x.getAreaTerreno();
        this.nQuartos=x.getNQuartos();
        this.nWCs=x.getNWCs();
        this.nPorta=x.getNPorta();
    }

    /*      Métodos de instância    */
    public String getTipo() {return tipo;}
    public double getAreaImplantacao() {return areaImplantacao;}
    public double getAreaCoberta() {return areaCoberta;}
    public double getAreaTerreno() {return areaTerreno;}
    public int getNQuartos() {return nQuartos;}
    public int getNWCs() {return nWCs;}
    public int getNPorta() {return nPorta;}

    public void setTipo(String tipo) {this.tipo = tipo;}
    public void setAreaImplantacao(double areaImplantacao) {this.areaImplantacao = areaImplantacao;}
    public void setAreaCoberta(double areaCoberta) {this.areaCoberta = areaCoberta;}
    public void setAreaTerreno(double areaTerreno) {this.areaTerreno = areaTerreno;}
    public void setNQuartos(int nQuartos) {this.nQuartos = nQuartos;}
    public void setNWCs(int nWCs) {this.nWCs = nWCs;}
    public void setNPorta(int nPorta) {this.nPorta = nPorta;}

    public boolean equals(Object obj) {
        if (this == obj) {return true;}
        if (obj == null) {return false;}
        if (getClass() != obj.getClass()){return false;}
        final Moradia other = (Moradia) obj;
        if (this.areaImplantacao == other.areaImplantacao &&
            this.areaCoberta == other.areaCoberta &&
            this.areaTerreno == other.areaTerreno &&
            this.nQuartos == other.nQuartos &&
            this.nWCs == other.nWCs &&
            this.nPorta == other.nPorta &&
            Objects.equals(this.tipo, other.tipo) ) {
            return true;
        }
        return false;
    }

    public String toString() {
        return "Moradia{" + "tipo=" + tipo + ", areaImplantacao=" + areaImplantacao + ", areaCoberta=" + areaCoberta + ", areaTerreno=" + areaTerreno + ", nQuartos=" + nQuartos + ", nWCs=" + nWCs + ", nPorta=" + nPorta + '}';
    }



}
