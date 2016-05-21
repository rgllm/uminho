import java.util.Objects;
import java.lang.*;

public class Apartamento extends Imovel implements Habitavel{
    private Tipo_Apartamento tipo;
    private double area;
    private int nQuartos;
    private int nWCs;
    private boolean garagem;
    private int nPorta;
    private int andar;

    /*           Construtores         */
    public Apartamento(){
        super();
        tipo=Tipo_Apartamento.Outro;
        area=0.0;
        nQuartos=0;
        nWCs=0;
        garagem=false;
        nPorta=0;
        andar=0;
        gerarID();
    }

    public Apartamento(int consultas, String rua,double preco,double precoMinimo,Tipo_Apartamento tipo,double area,int nQuartos,int nWCs,boolean garagem, int nPorta, int andar,Estado_Imovel estado){
        super(0,rua,preco,precoMinimo,estado);
        this.tipo=tipo;
        this.area=area;
        this.nQuartos=nQuartos;
        this.nWCs=nWCs;
        this.garagem=garagem;
        this.nPorta=nPorta;
        this.andar=andar;
        gerarID();
    }

    public Apartamento(Apartamento x){
        this.tipo=x.getTipo();
        this.area=x.getArea();
        this.nQuartos=x.getNQuartos();
        this.nWCs=x.getNWCs();
        this.garagem=x.isGaragem();
        this.nPorta=x.getnPorta();
        this.andar=x.getAndar();
    }

    /*      Métodos de instância    */
    public Tipo_Apartamento getTipo() {return tipo;}
    public double getArea() {return area;}
    public int getNQuartos() {return nQuartos;}
    public int getNWCs() {return nWCs;}
    public boolean isGaragem() {return garagem;}
    public int getnPorta(){return nPorta;}
    public int getAndar(){return andar;}

    public void setTipo(Tipo_Apartamento tipo) {this.tipo = tipo;}
    public void setArea(double area) {this.area = area;}
    public void setNQuartos(int nQuartos) {this.nQuartos = nQuartos;}
    public void setNWCs(int nWCs) {this.nWCs = nWCs;}
    public void setGaragem(boolean garagem) {this.garagem = garagem;}
    public void setnPorta(int nPorta){this.nPorta=nPorta;}
    public void setAndar(int andar){this.andar=andar;}

    public boolean equals(Object obj) {
        if (this == obj) {return true;}
        if (obj == null) {return false;}
        if (getClass() != obj.getClass()) {return false;}
        final Apartamento other = (Apartamento) obj;
        if (this.area ==other.area &&
            this.nQuartos == other.nQuartos &&
            this.nWCs == other.nWCs &&
            this.garagem == other.garagem &&
            this.tipo == other.tipo &&
            this.andar==other.andar &&
            this.nPorta==other.nPorta){
            return true;
        }
        return false;
    }

    public String toString() {
        return "Apartamento{" + "tipo=" + tipo + ", area=" + area + ", nQuartos=" + nQuartos + ", nWCs=" + nWCs + ", garagem=" + garagem + ", Andar" + andar + ", Porta" + nPorta + '}';
    }

    public void gerarID(){
        String id;
        id=super.gerarIDImovel();
        super.setId(id);
    } 


}
