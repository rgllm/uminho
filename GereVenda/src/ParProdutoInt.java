
public class ParProdutoInt implements Comparable<ParProdutoInt>{
    private Produto produto;
    private int inteiro;

    public ParProdutoInt(Produto p , int n){
        produto=p;
        inteiro=n;
    }
    
    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public int getInteiro() {
        return inteiro;
    }

    public void setInteiro(int inteiro) {
        this.inteiro = inteiro;
    }
    // ordena primeiramente por ordem descentente do inteiro e de seguida os Produtos por ordem ascendente
    public int compareTo(ParProdutoInt x){
        if(this.inteiro<x.getInteiro())
            return 1;
        if(this.inteiro>x.getInteiro())
            return -1;
        return this.getProduto().compareTo(x.getProduto());
            
    }
    
    public String toString(){
        return inteiro + produto.toString();
    }
    
}
