package reverseproxy;

import java.net.InetAddress;

class BackendInfo {
    InetAddress ip;
    private int medicoesRTT;  //número de probes que já foram feitos a este bakend server
    private float somaRTTs;
    private int conexoesAtivas;
    
    public BackendInfo(InetAddress ip){
        this.ip = ip;
        medicoesRTT=0;
        somaRTTs=0;
        this.conexoesAtivas = -1;
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
    
    
    
}
