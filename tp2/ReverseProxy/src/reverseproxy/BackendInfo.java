package reverseproxy;

import java.net.InetAddress;

class BackendInfo  {
    InetAddress ip;
    private int medicoesRTT; //number of probe requests done to a specific backend server
    private float somaRTTs; //sum of the time of probe requests
    private int conexoesAtivas; //number of active connections of a specific backend server
    private int nPerdas; //number of missed packages of a specific backend server
    private int perdasConsecutivas; //number of consecutive missed packages of a specific backend server
    
    public BackendInfo(InetAddress ip){
        this.ip = ip;
        medicoesRTT=0;
        somaRTTs=0;
        this.conexoesAtivas = -1;
        nPerdas=0;
        perdasConsecutivas=0;
        
    }
    
    public InetAddress getIp() {
        return ip;
    }
    
    public String getIpString() {
        return ip.toString().substring(1);
    }

    public void setIp(InetAddress ip) {
        this.ip = ip;
    }

    public float getMediaRTT() {
        return somaRTTs/medicoesRTT;
    }
    
    public void atualizaRTT(float medicaoRTT){
        somaRTTs+=medicaoRTT;
        medicoesRTT++;
    }

    public int getConexoesAtivas() {
        return conexoesAtivas;
    }

    public void setConexoesAtivas(int conexoesAtivas) {
        this.conexoesAtivas = conexoesAtivas;
    }

    public int getMedicoesRTT() {
        return medicoesRTT;
    }

    public void setMedicoesRTT(int medicoesRTT) {
        this.medicoesRTT = medicoesRTT;
    }

    public float getSomaRTTs() {
        return somaRTTs;
    }

    public void setSomaRTTs(float somaRTTs) {
        this.somaRTTs = somaRTTs;
    }

    public int getnPerdas() {
        return nPerdas;
    }
    
    public void incrementnPerdas() {
        nPerdas++;
    }

    public void setnPerdas(int nPerdas) {
        this.nPerdas = nPerdas;
    }

    public int getPerdasConsecutivas() {
        return perdasConsecutivas;
    }
    
    public float getTaxaPerdas(){
        return nPerdas/(medicoesRTT+nPerdas);
    }
    
    public void incrementPerdasConsecutivas() {
        perdasConsecutivas++;
    }

    public void setPerdasConsecutivas(int perdasConsecutivas) {
        this.perdasConsecutivas = perdasConsecutivas;
    }
    
}

