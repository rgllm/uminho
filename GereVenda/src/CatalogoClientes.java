
import java.util.TreeSet;

public class CatalogoClientes implements Serializable {
    
    private TreeSet<Cliente> clientes;

    public CatalogoClientes(){
        clientes= new TreeSet<Cliente>();
    }

    public TreeSet<Cliente> getClientes() {
        return clientes;
    }
    
    public void addCliente(Cliente cliente){
        clientes.add(cliente);
    }
    
    public int size(){
        return clientes.size();
    }
    
    public boolean existeCliente(Cliente c){
        return clientes.contains(c);
    }

}
