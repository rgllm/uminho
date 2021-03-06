/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reverseproxy;

import java.net.DatagramPacket;
import java.net.InetAddress;

import Utils.*;
import static java.lang.Thread.sleep;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;


class ProbeSender extends Thread{
    private ConcurrentHashMap<String,BackendInfo> infoBackends;
    private UDPServerSocket serverSocket;
    private Pacote probeResponse;
    
    public ProbeSender(ConcurrentHashMap<String,BackendInfo> ib , UDPServerSocket ss , Pacote pr){
        infoBackends=ib;
        serverSocket=ss;
        probeResponse=pr;
    }
    public void run(){
        try{ 
            while(true){
                sleep(2000);
                ArrayList<BackendInfo> aux=new ArrayList<>(infoBackends.values());
                for(BackendInfo beServer : aux){
                    byte[] sendData = new byte[1024];
                    InetAddress IPAddress = beServer.getIp();
                    int seq=Math.abs(new Random().nextInt()%1000);
                    sendData = ("Probe: "+seq).getBytes();
                    DatagramPacket sendPacket =new DatagramPacket(sendData, sendData.length, IPAddress, 5555);
                    PDU resposta=null; // acho que e preciso mudar isto
                    DatagramPacket receivePacket=null ;
                    boolean timeout=false;
                    System.out.println("PS: vou enviar probe request para o "+beServer.getIpString()+" com o numero de sequencia "+seq+"\n");
                    long antes = System.currentTimeMillis();
                    serverSocket.enviarPacote(sendPacket);
                    do{
                        synchronized(probeResponse){
                            while(probeResponse.getPacote().getPort()==1000){
                                probeResponse.wait(2000);
                                if(probeResponse.getPacote().getPort()==1000){
                                    timeout=true;
                                    break;
                                }
                            }
                            if(!timeout)
                                receivePacket = probeResponse.getPacote();
                            probeResponse.reset();
                        }
                        if(!timeout){
                            byte[] receiveData = new byte[1024];
                            receiveData=receivePacket.getData();
                            String message=new String(receiveData);
                            System.out.println("PS: recebi uma resposta: "+ message+"\n");
                            String []arr=message.split(",");
                            int nSeq= Integer.parseInt(arr[0].trim());
                            int nConexoes= Integer.parseInt(arr[1].trim());
                            resposta = new PDU(nSeq,nConexoes,receivePacket.getAddress());
                        }
                        if(System.currentTimeMillis() - antes>2000)
                            timeout=true;
                    }while(!timeout && resposta.getSeq()!=seq);
                    
                    if(!timeout){
                        float rtt=(float)(System.currentTimeMillis() - antes);
                        infoBackends.get(beServer.getIpString()).atualizaRTT(rtt);
                        infoBackends.get(beServer.getIpString()).setConexoesAtivas(resposta.getnConexoes());
                        infoBackends.get(beServer.getIpString()).setPerdasConsecutivas(0); 
                        System.out.println("PS: recebi resposta do " + beServer.getIpString() + "\n\trtt: " + rtt + " ; numero de conexoes ativas : " + resposta.getnConexoes()+"\n" );
                    }
                    else{
                        infoBackends.get(beServer.getIpString()).incrementnPerdas();
                        if(infoBackends.get(beServer.getIpString()).getPerdasConsecutivas()+1 >=5){
                            infoBackends.remove(beServer.getIpString());
                        }
                        else
                            infoBackends.get(beServer.getIpString()).incrementPerdasConsecutivas();
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }       
}
