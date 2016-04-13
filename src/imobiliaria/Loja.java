
import java.util.Objects;

public class Loja extends Imovel{
    private double area;
    private boolean wc;
    private String tipoNegocio;
    private int nPorta;
    /*parte habitacional */

    public double getArea() {return area;}
    public boolean isWc() {return wc;}
    public String getTipoNegocio() {return tipoNegocio;}
    public int getnPorta() {return nPorta;}

    public void setArea(double area) {this.area = area;}
    public void setWc(boolean wc) {this.wc = wc;}
    public void setTipoNegocio(String tipoNegocio) {this.tipoNegocio = tipoNegocio;}
    public void setnPorta(int nPorta) {this.nPorta = nPorta;}

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
