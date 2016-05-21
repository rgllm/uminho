import java.util.Objects;
import java.lang.*;

public class Loja extends Imovel{
    private double area;
    private boolean wc;
    private String tipoNegocio;
    private int nPorta;

    /*           Construtores         */
    public Loja(){
        super();
        area=0.0;
        wc=false;
        tipoNegocio=new String("");
        nPorta=0;
        gerarID();
    }

    public Loja(int consultas, String rua,double preco,double precoMinimo,double area,boolean wc, Estado_Imovel estado, String tipoNegocio,int nPorta){
        super(consultas,rua,preco,precoMinimo,estado);
        this.area=area;
        this.wc=wc;
        this.tipoNegocio=new String(tipoNegocio);
        this.nPorta=nPorta;
        gerarID();
    }

    public Loja(Loja x){
        this.area=x.getArea();
        this.wc=x.isWc();
        this.tipoNegocio=new String(x.getTipoNegocio());
        this.nPorta=x.getNPorta();
    }

    /*      Métodos de instância    */
    public double getArea() {return area;}
    public boolean isWc() {return wc;}
    public String getTipoNegocio() {return tipoNegocio;}
    public int getNPorta() {return nPorta;}

    public void setArea(double area) {this.area = area;}
    public void setWc(boolean wc) {this.wc = wc;}
    public void setTipoNegocio(String tipoNegocio) {this.tipoNegocio = tipoNegocio;}
    public void setNPorta(int nPorta) {this.nPorta = nPorta;}

    public boolean equals(Object obj) {
        if (this == obj) {return true;}
        if (obj == null) {return false;}
        if (getClass() != obj.getClass()) {return false;}
        final Loja other = (Loja) obj;
        if (this.area == other.area &&
            this.wc == other.wc &&
            this.nPorta == other.nPorta &&
            Objects.equals(this.tipoNegocio, other.tipoNegocio)){
            return true;}
        return false;
    }

    public String toString() {
        return "Loja{" + "area=" + area + ", wc=" + wc + ", tipoNegocio=" + tipoNegocio + ", nPorta=" + nPorta + '}';
    }

   public Loja clone(){
        return new Loja(this);
    }

    public void gerarID(){
        String id;
        id=super.gerarIDImovel();
        super.setId(id);
    }


}
