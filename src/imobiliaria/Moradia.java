
import java.util.Objects;

public class Moradia extends Imovel{
    private String tipo;
    private double areaImplantacao;
    private double areaCoberta;
    private double areaTerreno;
    private int nQuartos;
    private int nWCs;
    private int nPorta;

    public String getTipo() {return tipo;}
    public double getAreaImplantacao() {return areaImplantacao;}
    public double getAreaCoberta() {return areaCoberta;}
    public double getAreaTerreno() {return areaTerreno;}
    public int getnQuartos() {return nQuartos;}
    public int getnWCs() {return nWCs;}
    public int getnPorta() {return nPorta;}

    public void setTipo(String tipo) {this.tipo = tipo;}
    public void setAreaImplantacao(double areaImplantacao) {this.areaImplantacao = areaImplantacao;}
    public void setAreaCoberta(double areaCoberta) {this.areaCoberta = areaCoberta;}
    public void setAreaTerreno(double areaTerreno) {this.areaTerreno = areaTerreno;}
    public void setnQuartos(int nQuartos) {this.nQuartos = nQuartos;}
    public void setnWCs(int nWCs) {this.nWCs = nWCs;}
    public void setnPorta(int nPorta) {this.nPorta = nPorta;}

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
