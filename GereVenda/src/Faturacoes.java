
public class Faturacoes {
    
    private Faturacao faturacaoGlobal;
    private Faturacao faturacaoFilial1;
    private Faturacao faturacaoFilial2;
    private Faturacao faturacaoFilial3;
   
    public Faturacoes(Faturacao faturacaoGlobal, Faturacao faturacaoFilial1, Faturacao faturacaoFilial2, Faturacao faturacaoFilial3) {
        this.faturacaoGlobal = faturacaoGlobal;
        this.faturacaoFilial1 = faturacaoFilial1;
        this.faturacaoFilial2 = faturacaoFilial2;
        this.faturacaoFilial3 = faturacaoFilial3;
    }

    public Faturacoes() {
        this.faturacaoGlobal = new Faturacao();
        this.faturacaoFilial1 = new Faturacao();
        this.faturacaoFilial2 = new Faturacao();
        this.faturacaoFilial3 = new Faturacao();
        
    }

    public Faturacao getFaturacaoGlobal() {
        return faturacaoGlobal;
    }

    public void setFaturacaoGlobal(Faturacao faturacaoGlobal) {
        this.faturacaoGlobal = faturacaoGlobal;
    }

    public Faturacao getFaturacaoFilial1() {
        return faturacaoFilial1;
    }

    public void setFaturacaoFilial1(Faturacao faturacaoFilial1) {
        this.faturacaoFilial1 = faturacaoFilial1;
    }

    public Faturacao getFaturacaoFilial2() {
        return faturacaoFilial2;
    }

    public void setFaturacaoFilial2(Faturacao faturacaoFilial2) {
        this.faturacaoFilial2 = faturacaoFilial2;
    }

    public Faturacao getFaturacaoFilial3() {
        return faturacaoFilial3;
    }

    public void setFaturacaoFilial3(Faturacao faturacaoFilial3) {
        this.faturacaoFilial3 = faturacaoFilial3;
    }
    
    
    
    
    
}
