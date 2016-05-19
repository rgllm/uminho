/**
 * Write a description of class LojaHabitavel here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class LojaHabitavel extends Loja implements Habitavel{
    private Tipo_Apartamento tipo;
    private double area;
    private int nQuartos;
    private int nWCs;
    private boolean garagem;
    private int nPorta;
    private int andar;

    public LojaHabitavel (){
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

    public LojaHabitavel(String rua,double preco,double precoMinimo,double area,boolean wc, Estado_Imovel estado, String tipoNegocio,int nPorta,Tipo_Apartamento tipo, double areaAP, int nQuartos, int nWCs, boolean garagem,int andar) {
        super(rua,preco,precoMinimo,area,wc,estado,tipoNegocio,nPorta);
        this.tipo = tipo;
        this.area = areaAP;
        this.nQuartos = nQuartos;
        this.nWCs = nWCs;
        this.garagem = garagem;
        this.nPorta = nPorta;
        this.andar = andar;
        gerarID();
    }

     public LojaHabitavel(LojaHabitavel x){
        this.tipo=x.getTipo();
        this.area=x.getArea();
        this.nQuartos=x.getNQuartos();
        this.nWCs=x.getNWCs();
        this.garagem=x.isGaragem();
        this.nPorta=x.getnPorta();
        this.andar=x.getAndar();
    }

    //Gets e Sets

    public Tipo_Apartamento getTipo() {return tipo;}
    public double getArea() {return area;}
    public int getNQuartos() {return nQuartos;}
    public int getNWCs() {return nWCs;}
    public int getnPorta() {return nPorta;}
    public int getAndar() {return andar;}
    public boolean isGaragem() {return garagem;}

    public void setTipo(Tipo_Apartamento tipo) {this.tipo = tipo;}
    public void setArea(double area) {this.area = area;}
    public void setNQuartos(int nQuartos) {this.nQuartos = nQuartos;}
    public void setNWCs(int nWCs) {this.nWCs = nWCs;}
    public void setnPorta(int nPorta) {this.nPorta = nPorta;}
    public void setAndar(int andar) {this.andar = andar;}
    public void setGaragem(boolean garagem) {this.garagem = garagem;}


    public void gerarID(){
        int id=0;
        long bits;
        String stringID;

        id=super.gerarIDImovel();
        id+=this.getClass().getSimpleName().hashCode();
        if(tipo==Tipo_Apartamento.Simples) id++;
        else if(tipo==Tipo_Apartamento.Duplex) id+=2;
        else if(tipo==Tipo_Apartamento.Triplex) id+=3;
        else if(tipo==Tipo_Apartamento.Outro) id+=4;
        bits=Double.doubleToLongBits((double)area);
        id+=(int)(bits ^ (bits >>> 32));
        bits=Double.doubleToLongBits((double)nQuartos);
        id+=(int)(bits ^ (bits >>> 32));
        bits=Double.doubleToLongBits((double)nWCs);
        id+=(int)(bits ^ (bits >>> 32));
        bits=Double.doubleToLongBits((double)nPorta);
        id+=(int)(bits ^ (bits >>> 32));
        bits=Double.doubleToLongBits((double)andar);
        id+=(int)(bits ^ (bits >>> 32));
        if(garagem==true) id++;

        stringID=id+"";
        super.setId(stringID);
    }

}