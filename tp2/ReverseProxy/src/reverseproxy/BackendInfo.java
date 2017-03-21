/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reverseproxy;

import java.net.InetAddress;

class BackendInfo {
    InetAddress ip;
    private int medicoesRTT;  //número de probes que já foram feitos a este bakend server
    private float somaRTTs;
    private float mediaRTT;
    private int conexoesAtivas;
    
    public BackendInfo(InetAddress ip, float medicaoRTT, int conexoesAtivas){
        this.ip = ip;
        medicoesRTT=1;
        somaRTTs=medicaoRTT;
        this.mediaRTT = medicaoRTT;
        this.conexoesAtivas = conexoesAtivas;
    }
    
    public InetAddress getIp() {
        return ip;
    }

    public void setIp(InetAddress ip) {
        this.ip = ip;
    }

    public float getMediaRTT() {
        return mediaRTT;
    }

    public void setMediaRTT(float mediaRTT) {
        this.mediaRTT = mediaRTT;
    }
    
    public void atualizaRTT(float medicaoRTT){
        somaRTTs+=medicaoRTT;
        medicoesRTT++;
        mediaRTT=somaRTTs/medicoesRTT;
    } 
    
}
