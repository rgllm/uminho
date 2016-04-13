
import java.util.Objects;

public class Apartamento extends Imovel{
    private String tipo;
    private double area;
    private int nQuartos;
    private int nWCs;
    private boolean garagem;

    /*           Construtores         */
    public Apartamento(){
        super("","",0.0,0.0);
        tipo="";
        area=0.0;
        nQuartos=0;
        nWCs=0;
        garagem=false;
    }

    public Apartamento(String id,String rua,double preco,double precoMinimo,String tipo,double area,int nQuartos,int nWCs,boolean garagem){
        super(id,rua,preco,precoMinimo);
        this.tipo=new String(tipo);
        this.area=area;
        this.nQuartos=nQuartos;
        this.nWCs=nWCs;
        this.garagem=garagem;
    }

    public Apartamento(Apartamento x){
        super(x.getId(),x.getRua(),x.getPreco(),x.getPrecoMinimo());
        this.tipo=new String(x.getTipo());
        this.area=x.getArea();
        this.nQuartos=x.getNQuartos();
        this.nWCs=x.getNWCs();
        this.garagem=x.isGaragem();
    }

    /*      Métodos de instância    */
    public String getTipo() {return tipo;}
    public double getArea() {return area;}
    public int getNQuartos() {return nQuartos;}
    public int getNWCs() {return nWCs;}
    public boolean isGaragem() {return garagem;}

    public void setTipo(String tipo) {this.tipo = tipo;}
    public void setArea(double area) {this.area = area;}
    public void setNQuartos(int nQuartos) {this.nQuartos = nQuartos;}
    public void setNWCs(int nWCs) {this.nWCs = nWCs;}
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
