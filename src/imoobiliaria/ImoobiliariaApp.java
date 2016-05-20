import java.io.*;
import java.util.*;

public class ImoobiliariaApp {

    private ImoobiliariaApp() {}

    private static Imoobiliaria tab;
    private static Utilizador user;
    private static Menu menumain, menuRU, menuVendedor,menuRegistarLoja,menuRegistarImovel,menuComprador;

     /**
     * Menu principal da aplicação. Aparece apenas a quem não tem login feito.
     */
    public static void main(String[] args) throws ClassNotFoundException, UtilizadorExistenteException {
        carregarMenus();
        initApp();
        do{
            menumain.executa();
            switch(menumain.getOpcao()){
                case 1:
                    clearScreen();
                    iniciaSessao();
                    break;
                case 2:
                    clearScreen();
                    inserirUser();
                    break;
            }

        }while (menumain.getOpcao()!=0);
        try{
            tab.gravaObj("estado.im");
        }
        catch(IOException e){
            System.out.println("Não consegui gravar os dados.");
        }
        System.out.println("Até à próxima!");
    }

    /**
      * Método para carregar e definir todos os menus da aplicação.
     */
    private static void carregarMenus() {
        String[] ops={"Login","Registar Utilizador"};
        String [] opsUtilizador = {"Adicionar Vendedor","Adicionar Comprador"};
        String [] vendedor={"Registar Imóvel","Top de imóveis mais consultados","Alterar Estado de um Imóvel","Consultar Imóveis por Tipo","Consultar Imóveis Habitáveis","Consultar Mapeamento de Imóveis"};
        String [] registarloja={"Sem habitação","Com habitação"};
        String [] imoveis={"Moradia","Apartamento","Loja","Terreno"};
        String [] comprador={"Marcar um Imóvel como Favorito","Consultar os meus Imóveis Favoritos","Consultar Imóveis por Tipo","Consultar Imóveis Habitáveis","Consultar Mapeamento de Imóveis"};

        menumain = new Menu(ops);
        menuRU = new Menu(opsUtilizador);
        menuVendedor = new Menu(vendedor);
        menuRegistarLoja=new Menu(registarloja);
        menuRegistarImovel=new Menu(imoveis);
        menuComprador=new Menu(comprador);

    }

    /**
    * Método que apenas invoca o método carregarDados (conforme o enunciado).
    */
    public static void initApp (){
        carregarDados();
    }

    /**
    * Método que carrega inicialmente a aplicação.
    */
    private static void carregarDados() {
        try {
            tab = Imoobiliaria.leObj("estado.im");
        }
        catch (IOException e) {
            tab = new Imoobiliaria();
            System.out.println("Não consegui ler os dados!\nErro de leitura.");
        }
        catch (ClassNotFoundException e) {
           tab = new Imoobiliaria();
            System.out.println("Não consegui ler os dados!\nFicheiro com formato desconhecido.");
        }
        catch (ClassCastException e) {
           tab = new Imoobiliaria();
            System.out.println("Não consegui ler os dados!\nErro de formato.");
        }
    }

    /**
    * Método responsável pelo login dos utilizadores, quer vendedores quer compradores.
    */
    public static void iniciaSessao(){
        Scanner scan=new Scanner(System.in);
        String email,password;
        clearScreen();
        System.out.print("Email: ");
        email=scan.next();
        System.out.print("\n");
        System.out.print("Password: ");
        password=scan.next();
        try{
           tab.iniciaSessao(email,password);
           System.out.println("\nUtilizador logado com sucesso!");
        }
        catch(SemAutorizacaoException e){
            System.out.println(e.getMessage());
        }
        comLogin();
    }

    /**
    * Método responsável por sair da aplicação. Chama um método do Imoobiliaria.java que para além de fazer logout do utilizador
    * grava os dados da aplicação.
    */
    public static void fechaSessao(){
      tab.fechaSessao();
   }

    /**
    * Méotodo que recolhe todos os campos de um utilizador (quer vendedor quer comprador)
    * e passa ao método principal que regista o utilizador.
    */
    private static void inserirUser() throws UtilizadorExistenteException {
        clearScreen();
        Utilizador u=null;
        Scanner scin = new Scanner(System.in);
        menuRU.executa();
        if (menuRU.getOpcao() != 0) {
            String email,nome,password,morada,data_nascimento;
            clearScreen();
            System.out.print("Email: ");
            email = scin.nextLine();
            System.out.print("Nome: ");
            nome = scin.nextLine();
            System.out.print("Password: ");
            password = scin.nextLine();
            System.out.print("Morada: ");
            morada = scin.nextLine();
             System.out.print("Data de Nascimento: ");
            data_nascimento = scin.nextLine();
            switch (menuRU.getOpcao()) {
                case 1: u = new Vendedor(email,nome,password,morada,data_nascimento);
                        break;
                case 2: u = new Comprador(email,nome,password,morada,data_nascimento);
                        break;
            }
            try{
            tab.registarUtilizador(u);
            System.out.println("\n    Utilizador "+u.getNome()+" registado com sucesso!");
            }
            catch (UtilizadorExistenteException e){
                 System.out.println(e.getMessage());
            }
        }
        else {
            System.out.println("Inserção cancelada!");
            clearScreen();
        }
        scin.close();
    }

    /**
    * Registar uma Moradia.
    */
    public static void registarMoradia(){
        String rua;
        String tipo;
        double preco, precoMinimo, areaImplantacao, areaCoberta, areaTerreno;
        int nQuartos, nWCs, nPorta;
        Scanner scan=new Scanner(System.in);
        clearScreen();
        System.out.println("Registar Moradia");
        System.out.print("Rua: ");
        rua= scan.nextLine();
        System.out.print("\nNúmero da Porta: ");
        nPorta=scan.nextInt();
        System.out.print("\nPreço Pedido: ");
        preco=scan.nextDouble();
        System.out.print("\nPreço Mínimo: ");
        precoMinimo=scan.nextDouble();
        System.out.print("\nTipo (Isolada,Germinada,Banda,Gaveto ou Outro): ");
        tipo=scan.next();
        System.out.print("\nÁrea de Implantação: ");
        areaImplantacao=scan.nextDouble();
        System.out.print("\nÁrea Coberta: ");
        areaCoberta=scan.nextDouble();
        System.out.print("\nÁrea do terreno envolvente: ");
        areaTerreno=scan.nextDouble();
        System.out.print("\nNúmero de Quartos: ");
        nQuartos=scan.nextInt();
        System.out.print("\nNúmero de WCs: ");
        nWCs=scan.nextInt();
        Moradia moradia= new Moradia(0,rua,preco,precoMinimo,Tipo_Moradia.valueOf(tipo),areaImplantacao,areaCoberta,areaTerreno,nQuartos,nWCs,nPorta,Estado_Imovel.valueOf("Para_Venda"));
        try{
            tab.registaImovel(moradia);
            System.out.println("\n A Moradia com o ID (" + String.valueOf(moradia.gerarIDImovel())+") foi registada com Sucesso!");
        }
        catch(ImovelExisteException | SemAutorizacaoException e){
            System.out.println(e.getMessage());
        }
   }

     /**
    * Registar um Apartamento.
    */
    public static void registarApartamento(){
        String rua,tipo, garagem;
        Double preco, precoMinimo, area;
        int nQuartos, nWCs, nPorta, andar;
        Scanner scan=new Scanner(System.in);
        clearScreen();
        System.out.println("Registar Apartamento");
        System.out.print("Rua: ");
        rua= scan.nextLine();
        System.out.print("\nNúmero da Porta: ");
        nPorta=scan.nextInt();
        System.out.print("\nNúmero do Andar: ");
        andar=scan.nextInt();
        System.out.print("\nPreço Pedido: ");
        preco=scan.nextDouble();
        System.out.print("\nPreço Mínimo: ");
        precoMinimo=scan.nextDouble();
        System.out.print("\nTipo (Simples, Duplex, Triplex, Outro): ");
        tipo=scan.next();
        System.out.print("\nÁrea Total: ");
        area=scan.nextDouble();
        System.out.print("\nNúmero de Quartos: ");
        nQuartos=scan.nextInt();
        System.out.print("\nNúmero de WCs: ");
        nWCs=scan.nextInt();
        System.out.print("\nTem garagem? (true or false): "); // ver como fazer melhor isto
        garagem=scan.next();
        Apartamento apartamento= new Apartamento(0,rua,preco,precoMinimo,Tipo_Apartamento.valueOf(tipo),area,nQuartos,nWCs,Boolean.valueOf(garagem),nPorta,andar,Estado_Imovel.valueOf("Para_Venda"));
        try{
            tab.registaImovel(apartamento);
            System.out.println("\n O Apartamento com o ID (" + String.valueOf(apartamento.gerarIDImovel())+") foi registado com Sucesso!");
        }
        catch(ImovelExisteException | SemAutorizacaoException e){
                System.out.println(e.getMessage());
        }
   }

    /**
    * Registar uma Loja (Sem Habitação).
    */
    public static void registarLoja(){
        String rua, wc, tipoNegocio;
        Double preco, precoMinimo,area;
        int nPorta;
        Scanner scan=new Scanner(System.in);
        clearScreen();
        System.out.println("Registar Loja");
        System.out.print("Rua: ");
        rua= scan.nextLine();
        System.out.print("\nPreço: ");
        preco=scan.nextDouble();
        System.out.print("\nPreço Mínimo: ");
        precoMinimo=scan.nextDouble();
        System.out.print("\nTipo de Negócio: ");
        tipoNegocio=scan.next();
        System.out.print("\nÁrea Total: ");
        area=scan.nextDouble();
        System.out.print("\nNúmero da Porta: ");
        nPorta=scan.nextInt();
        System.out.print("\nTem WC? (true or false): ");
        wc=scan.next();
        Loja loja= new Loja(0,rua,preco,precoMinimo,area,Boolean.valueOf(wc),Estado_Imovel.valueOf("Para_Venda"),tipoNegocio,nPorta);
        try{
            tab.registaImovel(loja);
            System.out.println("\n A Loja com o ID (" + String.valueOf(loja.gerarIDImovel())+") foi registada com Sucesso!");
        }
        catch(ImovelExisteException | SemAutorizacaoException e){
            System.out.println(e.getMessage());
        }
    }

    /**
    * Registar uma Loja (Com Habitação).
    */
    public static void registarLojaHabitavel(){
        String rua,tipo,garagem,wc,tipoNegocio;
        Double preco, precoMinimo, area, areaAP;
        int nQuartos, nWCs, nPorta, andar;
        Scanner scan=new Scanner(System.in);
        clearScreen();
        System.out.println("Registar Loja Habitável");
        System.out.print("Rua: ");
        rua= scan.nextLine();
        System.out.print("\nNúmero da Porta: ");
        nPorta=scan.nextInt();
        System.out.print("\nPreço: ");
        preco=scan.nextDouble();
        System.out.print("\nPreço Mínimo: ");
        precoMinimo=scan.nextDouble();
        System.out.print("\nTipo de Negócio: ");
        tipoNegocio=scan.next();
        System.out.print("\nÁrea Total da Loja: ");
        area=scan.nextDouble();
        System.out.print("\nTem WC? (true ou false): ");
        wc=scan.next();
        System.out.print("\nÁrea Total do Apartamento: ");
        areaAP=scan.nextDouble();
        System.out.print("\nTipo do Apartamento (Simples, Duplex, Triplex, Outro): ");
        tipo=scan.next();
        System.out.print("\nNúmero de Quartos: ");
        nQuartos=scan.nextInt();
        System.out.print("\nNúmero de WCs do Apartamento: ");
        nWCs=scan.nextInt();
        System.out.print("\nNúmero do Andar: ");
        andar=scan.nextInt();
        System.out.print("\nTem garagem? (true or false): "); // ver como fazer melhor isto
        garagem=scan.next();
        LojaHabitavel loja= new LojaHabitavel(0,rua,preco,precoMinimo,area,Boolean.valueOf(wc),Estado_Imovel.valueOf("Para_Venda"),tipoNegocio,nPorta,Tipo_Apartamento.valueOf(tipo),areaAP,nQuartos,nWCs,Boolean.valueOf(garagem),andar);
        try{
            tab.registaImovel(loja);
            System.out.println("\n A loja com habitação com o ID (" + String.valueOf(loja.gerarIDImovel())+") foi registada com Sucesso!");
        }
        catch(ImovelExisteException | SemAutorizacaoException e){
            System.out.println(e.getMessage());
        }
   }

    /**
    * Registar um Terreno.
    */
    public static void registarTerreno(){
        String rua,habitacao,armazem,eletricidade,esgotos;
        Double preco, precoMinimo, areaConstrucao,diamCanalizacoes,potenciaEletrica;
        Scanner scan= new Scanner(System.in);
        clearScreen();
        System.out.println("Registar Terreno");
        System.out.print("Rua: ");
        rua= scan.nextLine();
        System.out.print("\nPreço: ");
        preco=scan.nextDouble();
        System.out.print("\nPreço Mínimo: ");
        precoMinimo=scan.nextDouble();
        System.out.print("\nÁrea Total de Construção: ");
        areaConstrucao=scan.nextDouble();
        System.out.print("\nPermite construir habitação? (true ou false): ");
        habitacao=scan.next();
        System.out.print("\nPermite construir armazém? (true ou false): ");
        armazem=scan.next();
        System.out.print("\nDiâmetro das canalizações: ");
        diamCanalizacoes=scan.nextDouble();
        System.out.print("\nTem eletricidade? (true ou false): ");
        eletricidade=scan.next();
        System.out.print("\nPotência elétrica: ");
        potenciaEletrica=scan.nextDouble();
        System.out.print("\nTem esgotos? (true ou false): ");
        esgotos=scan.next();
        Terreno terreno = new Terreno(0,rua,preco,precoMinimo,areaConstrucao,Boolean.valueOf(habitacao),Boolean.valueOf(armazem),diamCanalizacoes,Boolean.valueOf(eletricidade),potenciaEletrica,Boolean.valueOf(esgotos),Estado_Imovel.valueOf("Para_Venda"));
        try{
            tab.registaImovel(terreno);
            System.out.println("\n O Terreno com o ID (" + String.valueOf(terreno.gerarIDImovel())+") foi registado com Sucesso!");
        }
        catch(ImovelExisteException | SemAutorizacaoException e){
            System.out.println(e.getMessage());
        }
   }

    /**
    * Menu principal para registar um imóvel.
    */
    public static void RegistarImovel(){
     Scanner scin = new Scanner(System.in);
     menuRegistarImovel.executa();

       if (menuRegistarImovel.getOpcao() != 0) {
       switch(menuRegistarImovel.getOpcao()){
            case 1:
                registarMoradia();
                break;
            case 2:
                registarApartamento();
                break;
            case 3:{
                    menuRegistarLoja.executa();
                    switch(menuRegistarLoja.getOpcao()){
                        case 1:
                            registarLoja();
                            break;
                        case 2:
                            registarLojaHabitavel();
                            break;
                    }
                }break;
            case 4:
                registarTerreno();
                break;
        }
    }
   else {
            System.out.println("Registo do imóvel cancelado.");
    }
    scin.close();
}
    /**
    * Método que altera o estado de um imóvel, pedindo ao utilizador o ID e o estado para o qual
    * quer mudar o imóvel.
    */
    public static void AlterarEstado(){
            Scanner scan=new Scanner(System.in);
            String id, estado;
            clearScreen();
            System.out.print("Inserir ID do imóvel: ");
            id = scan.nextLine();
            System.out.print("Insira o estado do imovel (Para_Venda, Reservado, Vendido, Outro): ");
            estado = scan.nextLine();
            try{
                tab.setEstado(id,estado);
            }
            catch(ImovelInexistenteException e){
                System.out.println(e.getMessage());
                return;
            }
            catch(SemAutorizacaoException f){
                System.out.println(f.getMessage());
                return;
            }
            catch(EstadoInvalidoException g){
                System.out.println(g.getMessage());
                return;
            }
            System.out.println("\nEstado do imovel " + id + " alterado para " + estado);
       }

    /**
     * Método que imprime no ecrã, por páginas, os imóveis de um dado tipo (Moradia, Apartamento, Loja, Loja Habitável ou Terreno).
    */
    public static void ImoveisTipo(){
           String classe;
           int preco,i;
           Imovel imovel=null;
           Scanner scan= new Scanner(System.in);
           clearScreen();
           System.out.print("Classe dos imóveis \n (Moradia, Apartamento, Loja, LojaHabitavel ou Terreno) : ");
           classe=scan.next();
           System.out.print("\nPreço máximo dos imóveis: ");
           preco=scan.nextInt();
           List<Imovel> lista= tab.getImovel(classe,preco);
           if(lista.size()==0){
               System.out.println("Não há imóveis com estas especificações.");
           }
           else{
            clearScreen();
            for(i=0;i<lista.size();i++){
                imovel=lista.get(i);
                System.out.println("Código: " + imovel.gerarIDImovel());
                System.out.println("Rua: " + imovel.getRua());
                System.out.println("Preço Pedido: " + imovel.getPreco());
                System.out.println("Preço Mínimo: " + imovel.getPrecoMinimo());
                System.out.println("Estado do Imóvel: " + imovel.getEstado() + "\n\n");
            }
           }
        }

    /**
    * Método que imprime no ecrã, por páginas, todos os imóveis com habitação.
    */
    public static void Habitaveis(){
        Scanner scan=new Scanner(System.in);
        int preco;
        int i;
        Habitavel imovel = null;
        clearScreen();
        System.out.print("Insira o preço máximo do imovel: ");
        preco = scan.nextInt();
        List<Habitavel> lista=tab.getHabitaveis(preco);
        if(lista.size()==0){
           System.out.println("Não há imóveis com essas especificações");
       }
       else{
           clearScreen();
           for(i=0;i<lista.size();i++){
                imovel=lista.get(i);
                Imovel habitavel = (Imovel) imovel;
                System.out.println("Código: " + habitavel.gerarIDImovel());
                System.out.println("Rua: " + habitavel.getRua());
                System.out.println("Preço Pedido: " + habitavel.getPreco());
                System.out.println("Preço mínimo: " + habitavel.getPrecoMinimo());
                System.out.println("Estado: " + habitavel.getEstado() + "\n\n");
            }
       }
   }

     public static void TopImoveis(){
        Scanner scan=new Scanner(System.in);
        int consultas;
        int i;
        clearScreen();
        System.out.print("Número de consultas mínimo: ");
        consultas = scan.nextInt();
        try{
        List<String> lista=tab.getTopImoveis(consultas);
        if(lista.size()==0){
           System.out.println("Não há imóveis com essas especificações");
       }
       else{
            clearScreen();
            for(i=0;i<lista.size();i++){
                 System.out.println("ID do Imóvel: " + lista.get(i));
            }
       }
    }
    catch(SemAutorizacaoException g){
            System.out.println(g.getMessage());
    }
   }
     /**
     * Método que imprime o mapeamento dos imóveis, ou seja, imprime o vendedor e os imóveis correspondentes.
     */
    public static void Mapeamento(){
        Scanner scan=new Scanner(System.in);
        int i;
        Habitavel imovel = null;
        clearScreen();
       Map<Imovel,Vendedor> lista=tab.getMapeamentoImoveis();
        if(lista.size()==0){
           System.out.println("Não há imóveis registados.");
       }
       else{
            clearScreen();
            for (Map.Entry<Imovel, Vendedor> entry : lista.entrySet()) {
                String key = (entry.getKey()).toString();
                String value = (entry.getValue()).toString();
                System.out.printf("%s : %s\n", key, value);
            }
        }
    }

   /*
   *
   * MÉTODOS RELATIVOS APENAS AO COMPRADOR
   *
   */

     /**
     * Método que dado o ID de um imóvel adiciona aos favoritos do comprador esse imóvel.
     */
      public static void MarcarFavorito() {
        String id;
        Scanner scan = new Scanner(System.in);
        clearScreen();
        System.out.print("Insira id do imovel: ");
        id = scan.next();
        try{
           tab.setFavorito(id);
           System.out.println("Imóvel marcado como favorito!");
        }
        catch(ImovelInexistenteException e){
            System.out.println(e.getMessage());
        }
        catch(SemAutorizacaoException g){
            System.out.println(g.getMessage());
        }
       }

    /**
     * Método que imprime os favoritos do comprador com login feito.
     */
    public static void ConsultarFavoritos(){
        Scanner scan=new Scanner(System.in);
        int i;
        Imovel imovel = null;
        clearScreen();
        try{
        List<Imovel> lista=tab.getFavoritos();
        if(lista.size()==0){
           System.out.println("Não tem imóveis favoritos");
       }
       else{
           clearScreen();
           for(i=0;i<lista.size();i++){
                imovel=lista.get(i);
                Imovel favorito = (Imovel) imovel;
                System.out.println("Código: " + favorito.gerarIDImovel());
                System.out.println("Rua: " + favorito.getRua());
                System.out.println("Preço Pedido: " + favorito.getPreco());
                System.out.println("Preço mínimo: " + favorito.getPrecoMinimo());
                System.out.println("Estado: " + favorito.getEstado() + "\n\n");
           }
       }
    }
   catch(SemAutorizacaoException e){
            System.out.println(e.getMessage());
    }
  }

     /**
     * Menu principal da aplicação para utilizadores com login feito.
     * O menu é diferente caso o utilizador seja um vendedor ou seja um comprador.
     */
    public static void comLogin(){
       Scanner scan=new Scanner(System.in);
       if(tab.getUserAtual() instanceof Vendedor){
           do{
            menuVendedor.executa();
                switch(menuVendedor.getOpcao()){
                    case 1:
                        clearScreen();
                        RegistarImovel();
                        break;
                    case 2:
                        clearScreen();
                        TopImoveis();
                        break;
                    case 3:
                        clearScreen();
                        AlterarEstado();
                        break;
                    case 4:
                        clearScreen();
                        ImoveisTipo();
                        break;
                    case 5:
                        clearScreen();
                        Habitaveis();
                        break;
                    case 6:
                        clearScreen();
                        Mapeamento();
                        break;
                }
           }while(menuVendedor.getOpcao()!=0);
           fechaSessao();
           return;
           }
        if(tab.getUserAtual() instanceof Comprador){
        do{
        menuComprador.executa();
           switch(menuComprador.getOpcao()){
                case 1:
                    clearScreen();
                    MarcarFavorito();
                    break;
               case 2:
                    clearScreen();
                    ConsultarFavoritos();
                    break;
                case 3:
                    clearScreen();
                    ImoveisTipo();
                    break;
                case 4:
                    clearScreen();
                    Habitaveis();
                    break;
                case 5:
                    clearScreen();
                    Mapeamento();
                    break;

           }
        }while(menuComprador.getOpcao()!=0);
        fechaSessao();
        return;
    }
   }

    /**
     * How To Clear Screen
     * from the blueJ.org tips page
     *
     * This method will clear the BlueJ terminal window, but may not work
     * in other IDE terminal windows -- avoid except to test in BlueJ
     */
   private static void clearScreen()
   {
      System.out.print('\u000C');
   }

}