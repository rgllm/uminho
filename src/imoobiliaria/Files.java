import java.io.*;
import java.util.*;

public class Files
{

	public void pass(String email,String password)	
			throws SemAutorizacaoException{//Exceptions.java
		File arquivo = new File("/home/paulo/workspace/projeto_poo/Utilizadores/Dados/"+email);
		FileReader fr=null;
		try {fr = new FileReader( arquivo ); 
		} 
		catch(FileNotFoundException exc){ throw new SemAutorizacaoException("Utilizador nao registado!"); }
		BufferedReader br = new BufferedReader( fr ); 
		int i=0;
		String linha="";
		while(i<3){
			
		 try {linha = br.readLine();} 
		 catch(IOException exc){ exc.toString();}
		 i++;
		} 
		try{br.close();
		fr.close();} 
		catch(IOException exc){exc.toString();}
	   
		if(!password.equals(linha)) 
			throw new SemAutorizacaoException("Password errada!");//Exceptions.java
		}
	
	/**
	 * Carregar imoveis existentes na pasta Imoveis
	 */
	public ImoveisR carregaImoveis(){
		ArrayList<String> array = new ArrayList<String>();
		ImoveisR imovel = new ImoveisR();
		File pasta = new File("Imoveis");
		File[] todos = pasta.listFiles();
		for(File x:todos){
			try{
			FileReader fr = new FileReader(x);
			BufferedReader br = new BufferedReader(fr);
			while(br.ready()){
				array.add(br.readLine());
			}
			fr.close();	
			}catch(IOException e2){
				System.out.println("Erro!");
			}
			char inicial = x.getName().charAt(0);
			switch(inicial){
				case '1':
			}
			array.clear();
		}
		
		return imovel;
	}
	
	 public void exist(String s) throws UtilizadorExistenteException{
		File arquivo = new File("/home/paulo/workspace/projeto_poo/Utilizadores/Dados/" + s);
		if(arquivo.exists())
			throw new UtilizadorExistenteException("Utilizador existente!");//Exceptions.java
	}
}
