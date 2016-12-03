import java.util.*;
class Povoa{
    static final String AB = "0123456789abcdefghijklmnopqrstuvwxyz";
    static Random rnd = new Random();

    static String randomString( int len ){
       StringBuilder sb = new StringBuilder( len );
       for( int i = 0; i < len; i++ )
          sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
       return sb.toString();
    }

    public static void main(String[] args){
        Random rand=new Random();
        ArrayList<String> tipos=new ArrayList<>();
        ArrayList<String> nomes=new ArrayList<>();
        tipos.add("Alfa");
        tipos.add("Urbano");
        tipos.add("Intercidades");
        tipos.add("Regional");
        nomes.add("Daniela Kate");
        nomes.add("Mirela Alexandra");
        nomes.add("Estér Marciano");
        nomes.add("Inês Weiyi");
        nomes.add("Cristiana Isabel");
        nomes.add("Oryana Patrícia");
        nomes.add("Amélia Leonor");
        nomes.add("Ana Jade");
        nomes.add("Constança da Graça");
        nomes.add("Sónia Naiara");
        nomes.add("Mónica Vanessa");
        nomes.add("Sheila Cloe");
        nomes.add("Margarida Francisca");
        nomes.add("Lua Oriana");
        nomes.add("Joana de Sales");
        nomes.add("Matilde Iracema");
        nomes.add("Leila Filipa");
        nomes.add("Madalena Isabela");
        nomes.add("Nayara Alexandra");
        nomes.add("Mara Eduarda");
        nomes.add("Renata Daniela");
        nomes.add("Núria Lorena");
        nomes.add("Jasmine Kyara");
        nomes.add("Ayla Gabriela");
        nomes.add("Riyaa Hemal");
        nomes.add("Matilde Elawoko");
        nomes.add("Michelly Jahel");
        nomes.add("Catarina Salomé");
        nomes.add("Magda Ester");
        nomes.add("Liliana Raissa");
        nomes.add("Margarida Nair");
        nomes.add("Dahlia Sofia");
        nomes.add("Clara Pedro");
        nomes.add("Ciara Reiny");
        nomes.add("Denise Fátima");
        nomes.add("Miriam Gabriela");
        nomes.add("Érica dos Anjos");
        nomes.add("Kyara Fabiana");
        nomes.add("Joyciane Alícia");
        nomes.add("Jandira Sofia");
        nomes.add("Inês Juliana");
        nomes.add("Inês Félix");
        nomes.add("Íris Dalene");
        nomes.add("Nayara Vitória");
        nomes.add("Débora Raísa");
        nomes.add("Sofia Aisha");
        nomes.add("Nathalia Maria");
        nomes.add("Vanessa Isabel");
        nomes.add("Isabel Calisto");
        nomes.add("Íris Jovana");
        nomes.add("Nicole Solange");
        nomes.add("Zita Filipe");
        nomes.add("Marta Anabela");
        nomes.add("Anna do Carmo");
        nomes.add("Liana Sariza");
        nomes.add("Safiyah Farhan");
        nomes.add("Helyara Paixão");
        nomes.add("Isis Francisca");
        nomes.add("Érica Catarina");
        nomes.add("Emily Adele");
        nomes.add("Matilde do Rosário");
        nomes.add("Lia Clara");
        nomes.add("Jaciara Eugénia");
        nomes.add("Thyara Francisca");
        nomes.add("Inaara Alysha");
        nomes.add("Yara Samira");

        /*TABELA COMBOIO*/
        System.out.println("INSERT INTO Comboio (Tipo,Carruagens)\nVALUES ");
        for(int i=1;i<=50;i++){
            System.out.println("( '" +tipos.get(Math.abs(rand.nextInt()%4)) + "'," +
                                                         (Math.abs(rand.nextInt()%4)+2) + ") ,");
        }

        /*TABELA UTILIZADOR*/
        System.out.println("INSERT INTO Utilizador (Email,Nome,Password)\nVALUES ");
        for(int i=1;i<=50;i++){
            System.out.println("('" + randomString(Math.abs(rand.nextInt()%6)+5) + "@hotmail.com" + "','" +
                                      nomes.get(i) + "','" +
                                      randomString(Math.abs(rand.nextInt()%8)+6) + "'),");
        }

        /*TABELA VIAJEM*/
        System.out.println("INSERT INTO Viajem (Id,Origem,Destino,Tipo,PrecoBase,DataPartida,DataChegada)\nVALUES ");
        for(int i=1;i<=50;i++){
            String tipo="'nacional'";
            if(rand.nextInt()%4 ==0) tipo="'internacional'";
            float preco=(float)(rand.nextDouble()%50);
            if(tipo.equals("'internacional'")) preco += 0.7*preco;

            System.out.println("(" + i +  ", <origem> , <destino>," + tipo + ", <precoBase> , <DataPartida>,<DataChegada> ),");
        }

        /*TABELA RESERVA*/


    }






}




