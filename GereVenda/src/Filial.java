/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.HashSet;
import java.util.Objects;
import java.util.TreeSet;

public class Filial implements java.io.Serializable {
    private HashSet<Venda> vendas;

    public Filial(HashSet<Venda> vendas) {
        this.vendas = vendas;
    }
    
    public Filial(){
        this.vendas = new HashSet<>();
    }

    public HashSet<Venda> getVendas() {
        return vendas;
    }

    public void setVendas(HashSet<Venda> vendas) {
        this.vendas = vendas;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Filial other = (Filial) obj;
        if (!Objects.equals(this.vendas, other.vendas)) {
            return false;
        }
        return true;
    }
    
}
