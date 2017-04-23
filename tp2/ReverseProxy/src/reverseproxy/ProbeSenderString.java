/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reverseproxy;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import Utils.*;
import static java.lang.Thread.sleep;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;


class ProbeSenderString extends Thread{
    private HashMap<String,BackendInfo> infoBackends;
    private ServerSocket serverSocket;
    private Pacote probeResponse;
    
    public ProbeSenderString(HashMap<String,BackendInfo> ib , ServerSocket ss , Pacote pr){
        infoBackends=ib;
        serverSocket=ss;
        probeResponse=pr;
    }
    public void run(){
        try{ 
            while(true){
                sleep(1000);
                ArrayList<BackendInfo> aux=new ArrayList<>();
                synchronized(infoBackends){
                    aux=new ArrayList<>(infoBackends.values());
                }
                for(BackendInfo beServer : aux){
                    byte[] sendData = new byte[1024];
                    InetAddress IPAddress = beServer.getIp();
                    int seq=Math.abs(new Random().nextInt()%1000);
                    sendData = ("Probe: "+seq).getBytes();
                    DatagramPacket sendPacket =new DatagramPacket(sendData, sendData.length, IPAddress, 5555);
                    PDU resposta=null; // acho que e preciso mudar isto
                    DatagramPacket receivePacket=null ;
                    boolean timeout=false;
                    System.out.println("PS: vou enviar probe request para o "+beServer.getIpString()+"\n");
                    Date antes = new Date();
                    serverSocket.enviarPacote(sendPacket);
                    do{
                        synchronized(probeResponse){
                            while(probeResponse.getPacote().getPort()==1000){
                                if((new Date().getTime() - antes.getTime()) > 2000){
                                    timeout=true;
                                    System.out.println("PS: TIMEOUT");
                                    break;
                                }
                                probeResponse.wait();
                            }
                            if(!timeout)
                                receivePacket = probeResponse.getPacote();
                            byte []data=new byte[0];
                            probeResponse.setPacote(new DatagramPacket(data, data.length, InetAddress.getByName("0.0.0.0"), 1000));
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
                            System.out.println("PS: ???????????? seq="+seq+" ; seqResposta= "+resposta.getSeq()+"nConexoes= "+nConexoes+"\n");
                        }

                    }while(!timeout && resposta.getSeq()!=seq);
                    if(!timeout){
                        float rtt=new Date().getTime() - antes.getTime();
                        synchronized(infoBackends){
                            infoBackends.get(beServer.getIpString()).atualizaRTT(rtt);
                            infoBackends.get(beServer.getIpString()).setConexoesAtivas(resposta.getnConexoes());
                        }
                        System.out.println("PS: recebi resposta do " + beServer.getIpString() + "\n\trtt: " + rtt + " ; numero de conexoes ativas : " + resposta.getnConexoes()+"\n" );
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }       
}
