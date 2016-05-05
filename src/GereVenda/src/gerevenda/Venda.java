/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author 
 */

public static void main(String [] args){
    crono.start();
    ArrayList<String> linhas = readLinesArrayWithScanner;
    crono.stop();
    System.out.println("Tempo: " + Crono.print);
}

public static ArrayList<String> readLinesArrayWithScanner(String ficheiro) {
        ArrayList<String> linhas = new ArrayList<>();
        Scanner scanFile = null;
        try {
            scanFile = new Scanner(new FileReader(ficheiro));
            scanFile.useDelimiter("\n\r");
            while(scanFile.hasNext())
                linhas.add(scanFile.nextLine());
        }
        catch(IOException ioExc){ 
            out.println(ioExc.getMessage()); 
            return null; 
        } 
    finally { 
            if(scanFile != null) scanFile.close(); 
     } 
return linhas;
}


public class Venda Serializable {

    private String produto;
    private double preco;
    private int unidades;
    private char modo;
    private String cliente;
    private int filial;


    public Venda(String po, double pr, int u, char m, String c, int f){
        produto=po;
        preco=pr;
        unidades=u;
        modo=m;
        cliente=c;
        filial=f;
    }
    
    public Venda(Venda v){
        produto=v.getProduto();
        preco=v.getPreco();
        unidades=v.getUnidades();
        modo=v.getModo();
        cliente=v.getCliente();
        filial=v.getFilial();
    }

    
    public String getProduto(){return produto;}
    public double getPreco(){return preco;}
    public int getUnidades(){return unidades;}
    public char getModo(){return modo;}
    public String getCliente(){return cliente;}
    public int getFilial(){return filial;}


    public Venda clone(){
       return new Venda(this);
    }

    public String toString(){
        StringBuilder venda = new StringBuilder();

        venda.append=("Produto ");
        venda.append=(this.produto+" ");
        venda.append=("Preco ");
        venda.append=(this.preco+" ");
        venda.append=("Unidades ");
        venda.append=(this.unidades+" ");
        venda.append=("Modo ");
        venda.append=(this.modo+" ");
        venda.append=("Cliente ");
        venda.append=(this.cliente+" ");
        venda.append=("Filial ");
        venda.append=(this.filial+"\n");
  
        return venda.toString();
}
    
    public boolean equals(Object o){
        if(o==null) return false;
        if (o==this) return true;
        if (this.getClass()!=o.getClass()) return false;
        Venda obj = (Venda) o;
        if( produto.equals(o.getProduto())==true &&
            preco==o.getPreco() &&
            unidades=o.getUnidades() &&
            modo=o.getModo() &&
            cliente.equals(o.getCliente())==true &&
            filial=o.getFilial()) return true;
       else return false;
    }

}
