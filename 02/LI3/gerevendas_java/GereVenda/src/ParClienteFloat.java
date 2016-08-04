public class ParClienteFloat implements Comparable<ParClienteFloat>{
    private Cliente cliente;
    private float valor;

    public ParClienteFloat(Cliente c , float n){
        cliente=c;
        valor=n;
    }
    
    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }
    // ordena primeiramente por ordem descentente do float e de seguida os clientes por ordem ascendente
    public int compareTo(ParClienteFloat x){
        if(this.valor<x.getValor())
            return 1;
        if(this.valor>x.getValor())
            return -1;
        return this.getCliente().compareTo(x.getCliente());
            
    }
    
    public String toString(){
        return valor + cliente.toString();
    }
    
}
