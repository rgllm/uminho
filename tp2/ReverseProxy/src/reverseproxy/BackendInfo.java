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
    
}
