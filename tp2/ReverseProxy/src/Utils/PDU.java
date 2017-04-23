package Utils;

import java.net.InetAddress;


public class PDU {
    private int seq;
    private int nConexoes;
    private InetAddress ip;
    //Date sendTime;
    
    public PDU(int s, int nc, InetAddress ip){
        seq=s;
        nConexoes=s;
        this.ip=ip;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public int getnConexoes() {
        return nConexoes;
    }

    public void setnConexoes(int nConexoes) {
        this.nConexoes = nConexoes;
    }

    public InetAddress getIp() {
        return ip;
    }

    public void setIp(InetAddress ip) {
        this.ip = ip;
    }
    
}
