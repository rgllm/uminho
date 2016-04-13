
import java.util.Objects;

public class Loja extends Imovel{
    private double area;
    private boolean wc;
    private String tipoNegocio;
    private int nPorta;
    /*parte habitacional */

    /*           Construtores         */
    public Loja(){
        super("","",0.0,0.0);
        area=0.0;
        wc=false;
        tipoNegocio=new String("");
        nPorta=0;        
    }

    public Loja(String id,String rua,double preco,double precoMinimo,double area,boolean wc,String tipoNegocio,int nPorta){
        super(id,rua,preco,precoMinimo);
        this.area=area;
        this.wc=wc;
        this.tipoNegocio=new String(tipoNegocio);
        this.nPorta=nPorta;
    }

    public Loja(Loja x){
        super(x.getId(),x.getRua(),x.getPreco(),x.getPrecoMinimo());
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



}
