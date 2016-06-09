/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.Objects;
import java.util.TreeSet;

public class Filial {
    private TreeSet<Venda> vendas;

    public Filial(TreeSet<Venda> vendas) {
        this.vendas = vendas;
    }
    
    public Filial(){
        this.vendas = new TreeSet<>();
    }

    public TreeSet<Venda> getVendas() {
        return vendas;
    }

    public void setVendas(TreeSet<Venda> vendas) {
        this.vendas = vendas;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + Objects.hashCode(this.vendas);
        return hash;
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
