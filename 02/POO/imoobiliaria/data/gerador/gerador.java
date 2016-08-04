private static Random rnd = new Random();

    public static String getRandomStringL(int length) {
        return getRandomString("abcdefghijklmnopqrstuvwxyz1234567890", length);
    }

    public static String getRandomString(String allowedChars, int length) {
        if (allowedChars == null || allowedChars.trim().length() == 0 || length <= 0) {
                throw new IllegalArgumentException("Please provide valid input");
        }

        Random rand = new Random();
        StringBuilder sb = new StringBuilder();

        for (int i = 0 ; i < length ; i ++) {
          sb.append(allowedChars.charAt(rand.nextInt(allowedChars.length())));
        }
        return sb.toString();
  }

    public static Tipo_Moradia getRTipoMoradia (){
        int pick = new Random().nextInt(Tipo_Moradia.values().length);
        return Tipo_Moradia.values()[pick];
    }

    public static Tipo_Apartamento getRTipoApartamento(){
        int pick = new Random().nextInt(Tipo_Apartamento.values().length);
        return Tipo_Apartamento.values()[pick];
    }

    public static boolean getRandomBoolean() {
        return rnd.nextBoolean();
   }

   /* Utilizadores */

        System.out.println("Começou a gerar!!!!");
        for(i=0;i<2000;i++){
            //Utilizadores
            Vendedor v = new Vendedor(getRandomStringL(15),getRandomString("abcdefghijklmnopqrstuvwxyz",10),getRandomStringL(5),getRandomString("abcdefghijklmnopqrstuvwxyz ",20),getRandomString("1234567890",9));
            Comprador c = new Comprador(getRandomStringL(15),getRandomString("abcdefghijklmnopqrstuvwxyz",10),getRandomStringL(5),getRandomString("abcdefghijklmnopqrstuvwxyz ",20),getRandomString("1234567890",9));
            try{
            tab.registarUtilizador(v);
            tab.registarUtilizador(c);
             }
            catch(UtilizadorExistenteException e){
                    System.out.println(e.getMessage());
            }
        }

        System.out.println("Acabou os utilizadores!!!");


/* Imóveis */

 for(i=0;i<5000;i++){
            //Imóveis
            Moradia m = new Moradia(Integer.parseInt(getRandomString("1234567890",2)),getRandomString("abcdefghijklmnopqrstuvwxyz ",20),Double.parseDouble(getRandomString("1234567890",8)),Double.parseDouble(getRandomString("1234567890",7)),getRTipoMoradia(),Double.parseDouble(getRandomString("1234567890",3)),Double.parseDouble(getRandomString("1234567890",3)),Double.parseDouble(getRandomString("1234567890",3)),Integer.parseInt(getRandomString("1234567890",1)),Integer.parseInt(getRandomString("1234567890",1)),Integer.parseInt(getRandomString("1234567890",4)),Estado_Imovel.valueOf("Para_Venda"));

            Apartamento a = new Apartamento(Integer.parseInt(getRandomString("1234567890",2)),getRandomString("abcdefghijklmnopqrstuvwxyz ",20),Double.parseDouble(getRandomString("1234567890",8)),Double.parseDouble(getRandomString("1234567890",7)),getRTipoApartamento(),Double.parseDouble(getRandomString("1234567890",3)),Integer.parseInt(getRandomString("1234567890",1)),Integer.parseInt(getRandomString("1234567890",1)),getRandomBoolean(),Integer.parseInt(getRandomString("1234567890",4)),Integer.parseInt(getRandomString("1234567890",1)),Estado_Imovel.valueOf("Para_Venda"));

            Loja l = new Loja(Integer.parseInt(getRandomString("1234567890",2)),getRandomString("abcdefghijklmnopqrstuvwxyz ",20),Double.parseDouble(getRandomString("1234567890",8)),Double.parseDouble(getRandomString("1234567890",7)),Double.parseDouble(getRandomString("1234567890",3)),getRandomBoolean(),Estado_Imovel.valueOf("Para_Venda"),getRandomString("abcdefghijklmnopqrstuvwxyz ",10),Integer.parseInt(getRandomString("1234567890",4)));

            LojaHabitavel lh = new LojaHabitavel(Integer.parseInt(getRandomString("1234567890",2)),getRandomString("abcdefghijklmnopqrstuvwxyz ",20),Double.parseDouble(getRandomString("1234567890",8)),Double.parseDouble(getRandomString("1234567890",7)),Double.parseDouble(getRandomString("1234567890",3)),getRandomBoolean(),Estado_Imovel.valueOf("Para_Venda"),getRandomString("abcdefghijklmnopqrstuvwxyz ",10),Integer.parseInt(getRandomString("1234567890",4)),getRTipoApartamento(),Double.parseDouble(getRandomString("1234567890",3)),Integer.parseInt(getRandomString("1234567890",1)),Integer.parseInt(getRandomString("1234567890",1)),getRandomBoolean(),Integer.parseInt(getRandomString("1234567890",1)));

            Terreno t = new Terreno(Integer.parseInt(getRandomString("1234567890",2)),getRandomString("abcdefghijklmnopqrstuvwxyz ",20),Double.parseDouble(getRandomString("1234567890",8)),Double.parseDouble(getRandomString("1234567890",7)),Double.parseDouble(getRandomString("1234567890",3)),getRandomBoolean(),getRandomBoolean(),Double.parseDouble(getRandomString("1234567890",2)),getRandomBoolean(),Double.parseDouble(getRandomString("1234567890",2)),getRandomBoolean(),Estado_Imovel.valueOf("Para_Venda"));

                try{
                tab.registaImovel(m);
                tab.registaImovel(a);
                tab.registaImovel(l);
                tab.registaImovel(lh);
                tab.registaImovel(t);
                }
                catch(ImovelExisteException | SemAutorizacaoException e){
                    System.out.println(e.getMessage());
                }
        }

        System.out.println("Acabou os imóveis!!!");