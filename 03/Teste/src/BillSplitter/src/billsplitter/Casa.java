
package billsplitter;

public class Casa {
    private int id;
    private String morada;
    private String cpostal;
    private String localidade;
    private String telefone;
    private String observacoes;

    public Casa(int id, String morada, String cpostal,String localidade,String telefone, String observacoes) {
        this.id=id;
        this.morada=morada;
        this.cpostal=cpostal;
        this.localidade=localidade;
        this.telefone=telefone;
        this.observacoes=observacoes;
    }

    public Casa(String morada, String cpostal,String localidade,String telefone, String observacoes) {
        this.morada=morada;
        this.cpostal=cpostal;
        this.localidade=localidade;
        this.telefone=telefone;
        this.observacoes=observacoes;
    }

    public int getId() {
        return id;
    }

    public String getMorada() {
        return morada;
    }

    public void setMorada(String morada) {
        this.morada = morada;
    }

    public String getCpostal() {
        return cpostal;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCpostal(String cpostal) {
        this.cpostal = cpostal;
    }

    public String getLocalidade() {
        return localidade;
    }

    public void setLocalidade(String localidade) {
        this.localidade = localidade;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

}
