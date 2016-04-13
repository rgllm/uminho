
import java.util.Objects;

public class Apartamento extends Imovel{
    private String tipo;
    private double area;
    private int nQuartos;
    private int nWCs;
    private boolean garagem;

    public Apartamento(){
        super("","",0,0);
        tipo="";
        area=0;
        nQuartos=0;
        nWCs=0;
        garagem=false;
    }

    public String getTipo() {return tipo;}
    public double getArea() {return area;}
    public int getnQuartos() {return nQuartos;}
    public int getnWCs() {return nWCs;}
    public boolean isGaragem() {return garagem;}

    public void setTipo(String tipo) {this.tipo = tipo;}
    public void setArea(double area) {this.area = area;}
    public void setnQuartos(int nQuartos) {this.nQuartos = nQuartos;}
    public void setnWCs(int nWCs) {this.nWCs = nWCs;}
    public void setGaragem(boolean garagem) {this.garagem = garagem;}



    public boolean equals(Object obj) {
        if (this == obj) {return true;}
        if (obj == null) {return false;}
        if (getClass() != obj.getClass()) {return false;}
        final Apartamento other = (Apartamento) obj;
        if (this.area ==other.area &&
            this.nQuartos == other.nQuartos &&
            this.nWCs == other.nWCs &&
            this.garagem == other.garagem &&
            Objects.equals(this.tipo, other.tipo)){
            return true;
        }
        return false;
    }

    public String toString() {
        return "Apartamento{" + "tipo=" + tipo + ", area=" + area + ", nQuartos=" + nQuartos + ", nWCs=" + nWCs + ", garagem=" + garagem + '}';
    }


}
