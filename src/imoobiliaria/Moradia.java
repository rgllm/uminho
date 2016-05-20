import java.util.Objects;
import java.lang.*;

public class Moradia extends Imovel implements Habitavel{
    private Tipo_Moradia tipo;
    private double areaImplantacao;
    private double areaCoberta;
    private double areaTerreno;
    private int nQuartos;
    private int nWCs;
    private int nPorta;

    /*           Construtores         */
    public Moradia(){
        super();
        tipo=Tipo_Moradia.Outro;
        areaImplantacao=0.0;
        areaCoberta=0.0;
        areaTerreno=0.0;
        nQuartos=0;
        nWCs=0;
        nPorta=0;
        gerarID();
    }

    public Moradia(int consultas, String rua,double preco,double precoMinimo,Tipo_Moradia tipo,double areaImplantacao,double areaCoberta,double areaTerreno,int nQuartos,int nWCs,int nPorta,Estado_Imovel estado){
        super(consultas,rua,preco,precoMinimo,estado);
        this.tipo=tipo;
        this.areaImplantacao=areaImplantacao;
        this.areaCoberta=areaCoberta;
        this.areaTerreno=areaTerreno;
        this.nQuartos=nQuartos;
        this.nWCs=nWCs;
        this.nPorta=nPorta;
        gerarID();
    }

    public Moradia(Moradia x){
        this.tipo=x.getTipo();
        this.areaImplantacao=x.getAreaImplantacao();
        this.areaCoberta=x.getAreaCoberta();
        this.areaTerreno=x.getAreaTerreno();
        this.nQuartos=x.getNQuartos();
        this.nWCs=x.getNWCs();
        this.nPorta=x.getNPorta();
    }

    /*      Métodos de instância    */
    public Tipo_Moradia getTipo() {return tipo;}
    public double getAreaImplantacao() {return areaImplantacao;}
    public double getAreaCoberta() {return areaCoberta;}
    public double getAreaTerreno() {return areaTerreno;}
    public int getNQuartos() {return nQuartos;}
    public int getNWCs() {return nWCs;}
    public int getNPorta() {return nPorta;}

    public void setTipo(Tipo_Moradia tipo) {this.tipo = tipo;}
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
            this.tipo == other.tipo ) {
            return true;
        }
        return false;
    }

    public String toString() {
        return "Moradia{" + "tipo=" + tipo + ", areaImplantacao=" + areaImplantacao + ", areaCoberta=" + areaCoberta + ", areaTerreno=" + areaTerreno + ", nQuartos=" + nQuartos + ", nWCs=" + nWCs + ", nPorta=" + nPorta + '}';
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
        if(tipo==Tipo_Moradia.Isolada) id++;
        else if(tipo==Tipo_Moradia.Germinada) id+=2;
        else if(tipo==Tipo_Moradia.Banda) id+=3;
        else if(tipo==Tipo_Moradia.Gaveto) id+=4;
        else if(tipo==Tipo_Moradia.Outro) id+=5;
        bits=Double.doubleToLongBits(areaImplantacao);
        id+=(int)(bits ^ (bits >>> 32));
        bits=Double.doubleToLongBits(areaCoberta);
        id+=(int)(bits ^ (bits >>> 32));
        bits=Double.doubleToLongBits(areaTerreno);
        id+=(int)(bits ^ (bits >>> 32));
        bits=Double.doubleToLongBits((double)nQuartos);
        id+=(int)(bits ^ (bits >>> 32));
        bits=Double.doubleToLongBits((double)nWCs);
        id+=(int)(bits ^ (bits >>> 32));
        bits=Double.doubleToLongBits((double)nPorta);
        id+=(int)(bits ^ (bits >>> 32));

        stringID=id+"";
        super.setId(stringID);*/
    }


}
