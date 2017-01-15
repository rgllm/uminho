
package billsplitter;
import java.util.*;

public class Despesa{
    private int idDespesa;
    private String descricao;
    private Date data_inicio;
    private Date data_fim;
    private Date data_limite;
    private float montante;
    private String observacoes;
    private String pagador; //email
    private Boolean estado;
    private int idcasa;
    private HashMap<String,Float> participantes; //emails

     public Despesa(int iddespesa, String descricao, Date data_inicio, Date data_fim, Date data_limite, float montante, String observacoes, String pagador, Boolean estado, int idcasa, HashMap<String,Float> participantes) {
        this.idDespesa=iddespesa;
        this.descricao = descricao;
        this.data_inicio = data_inicio;
        this.data_fim = data_fim;
        this.data_limite = data_limite;
        this.montante = montante;
        this.observacoes = observacoes;
        this.pagador = pagador;
        this.estado = estado;
        this.idcasa = idcasa;
        this.participantes=new HashMap<>(participantes);
    }

    public Despesa(String descricao, Date data_inicio, Date data_fim, Date data_limite, float montante, String observacoes, String pagador, Boolean estado, int idcasa, HashMap<String,Float> participantes) {
        this.descricao = descricao;
        this.data_inicio = data_inicio;
        this.data_fim = data_fim;
        this.data_limite = data_limite;
        this.montante = montante;
        this.observacoes = observacoes;
        this.pagador = pagador;
        this.estado = estado;
        this.idcasa = idcasa;
        this.participantes=new HashMap<>(participantes);
    }

    public int getIdDespesa(){
        return idDespesa;
    }

    public void setIdDespesa(int idDespesa){
        this.idDespesa = idDespesa;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getData_inicio() {
        return data_inicio;
    }

    public void setData_inicio(Date data_inicio) {
        this.data_inicio = data_inicio;
    }

    public Date getData_fim() {
        return data_fim;
    }

    public void setData_fim(Date data_fim) {
        this.data_fim = data_fim;
    }

    public Date getData_limite() {
        return data_limite;
    }

    public void setData_limite(Date data_limite) {
        this.data_limite = data_limite;
    }

    public float getMontante() {
        return montante;
    }

    public void setMontante(float montante) {
        this.montante = montante;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public String getPagador() {
        return pagador;
    }

    public void setPagador(String pagador) {
        this.pagador = pagador;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public int getIdcasa() {
        return idcasa;
    }

    public void setIdcasa(int idcasa) {
        this.idcasa = idcasa;
    }

    public HashMap<String,Float> getParticipantes() {
        return participantes;
    }

    public void setParticipantes(HashMap<String,Float> participantes) {
        this.participantes = participantes;
    }

}