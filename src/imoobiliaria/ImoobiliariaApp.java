import java.io.*;
import java.util.*;

public class ImoobiliariaApp {

    private ImoobiliariaApp() {}

    private static Imoobiliaria tab;
    private static Utilizador user;
    private static Menu menumain, menuRU, menuVendedor,menuRegistarLoja,menuRegistarImovel;

    // Método principal
    public static void main(String[] args) throws ClassNotFoundException, UtilizadorExistenteException {
        carregarMenus();
        initApp();
        do{
            menumain.executa();

            switch(menumain.getOpcao()){
                case 1:
                    iniciaSessao();
                    break;
                case 2:
                    inserirUser();
                    break;
            }

        }while (menumain.getOpcao()!=0);
        try{
            tab.gravaObj("estado.im");
        }
        catch(IOException e){
            System.out.println("Não consegui gravar os Dados");
        }
        System.out.println("Até breve!....");
    }

    /* Carregamento dos menus */

    private static void carregarMenus() {
        String[] ops={"Login","Registar Utilizador"};
        String [] opsUtilizador = {"Adicionar Vendedor","Adicionar Comprador"};
        String [] vendedor={"Registar Imóvel","Imóveis para Venda","Alterar Estado de um Imóvel","Imóveis mais consultados","Lista de Imóveis por Tipo","Lista Imóveis Habitáveis","Consultar Mapeamento de Imóveis"};
        String [] registarloja={"Sem habitação","Com habitação"};
        String [] imoveis={"Moradia","Apartamento","Loja","Terreno"};

        menumain = new Menu(ops);
        menuRU = new Menu(opsUtilizador);
        menuVendedor = new Menu(vendedor);
        menuRegistarLoja=new Menu(registarloja);
        menuRegistarImovel=new Menu(imoveis);

    }

    /* Carregamento dos dados */

    public static void initApp (){
        carregarDados();
    }

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

    /* Outros métodos */

    public static void iniciaSessao(){
        Scanner scan=new Scanner(System.in);
        String email,password;
        System.out.println("============================");
        System.out.println("|     IMOOBILIARIA APP     |");
        System.out.println("============================");
        System.out.print("       Email: ");
        email=scan.next();
        System.out.print("\n");
        System.out.print("       Password: ");
        password=scan.next();
        try{
           tab.iniciaSessao(email,password);
           System.out.println("\n    Utilizador logado com sucesso!");
        }
        catch(SemAutorizacaoException e){
            System.out.println(e.getMessage());
        }
        comLogin();
    }

    public static void fechaSessao(){
      tab.fechaSessao();
   }

    private static void inserirUser() throws UtilizadorExistenteException {
        Utilizador u=null;
        Scanner scin = new Scanner(System.in);

        menuRU.executa();
        if (menuRU.getOpcao() != 0) {
            String email,nome,password,morada,data_nascimento;
             System.out.println("============================");
            System.out.println("|     IMOOBILIARIA APP     |");
            System.out.println("============================");
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
            tab.registarUtilizador(u);
            System.out.println("\n    Utilizador "+u.getNome()+" registado com sucesso!");
            //menuLogado();
        } else {
            System.out.println("Inserção cancelada!");
        }
        scin.close();
    }

    public static void registarMoradia(){
        String rua;
        String tipo;
        double preco, precoMinimo, areaImplantacao, areaCoberta, areaTerreno;
        int nQuartos, nWCs, nPorta;
        Scanner scan=new Scanner(System.in);
        System.out.println("============================");
        System.out.println("|     IMOOBILIARIA APP     |");
        System.out.println("============================");
        System.out.println("    Registar Moradia");
        System.out.print("           Rua: ");
        rua= scan.nextLine();
        System.out.print("\n           Número da Porta: ");
        nPorta=scan.nextInt();
        System.out.print("\n           Preço Pedido: ");
        preco=scan.nextDouble();
        System.out.print("\n           Preço Mínimo: ");
        precoMinimo=scan.nextDouble();
        System.out.print("\n           Tipo (Isolada,Germinada,Banda,Gaveto ou Outro): ");
        tipo=scan.next();
        System.out.print("\n           Área de Implantação: ");
        areaImplantacao=scan.nextDouble();
        System.out.print("\n           Área Coberta: ");
        areaCoberta=scan.nextDouble();
        System.out.print("\n           Área do terreno envolvente: ");
        areaTerreno=scan.nextDouble();
        System.out.print("\n           Número de Quartos: ");
        nQuartos=scan.nextInt();
        System.out.print("\n           Número de WCs: ");
        nWCs=scan.nextInt();
        Moradia moradia= new Moradia(rua,preco,precoMinimo,Tipo_Moradia.valueOf(tipo),areaImplantacao,areaCoberta,areaTerreno,nQuartos,nWCs,nPorta,Estado_Imovel.valueOf("Para_Venda"));
        try{
            tab.registaImovel(moradia);
        }
        catch(ImovelExisteException | SemAutorizacaoException e){
            System.out.println(e.getMessage());
        }
        System.out.println("\n Moradia registada com Sucesso!");
   }

    public static void registarApartamento(){
        String rua,tipo, garagem;
        Double preco, precoMinimo, area;
        int nQuartos, nWCs, nPorta, andar;
        Scanner scan=new Scanner(System.in);
        System.out.println("============================");
        System.out.println("|     IMOOBILIARIA APP     |");
        System.out.println("============================");
        System.out.println("    Registar Apartamento");
        System.out.print("           Rua: ");
        rua= scan.nextLine();
        System.out.print("\n           Número da Porta: ");
        nPorta=scan.nextInt();
        System.out.print("\n           Número do Andar: ");
        andar=scan.nextInt();
        System.out.print("\n           Preço Pedido: ");
        preco=scan.nextDouble();
        System.out.print("\n           Preço Mínimo: ");
        precoMinimo=scan.nextDouble();
        System.out.print("\n           Tipo (Simples, Duplex, Triplex, Outro): ");
        tipo=scan.next();
        System.out.print("\n           Área Total: ");
        area=scan.nextDouble();
        System.out.print("\n           Número de Quartos: ");
        nQuartos=scan.nextInt();
        System.out.print("\n           Número de WCs: ");
        nWCs=scan.nextInt();
        System.out.print("\n    Tem garagem? (true or false): "); // ver como fazer melhor isto
        garagem=scan.nextLine();
        Apartamento apartamento= new Apartamento(rua,preco,precoMinimo,Tipo_Apartamento.valueOf(tipo),area,nQuartos,nWCs,Boolean.valueOf(garagem),nPorta,andar,Estado_Imovel.valueOf("Para_Venda"));
        try{
            tab.registaImovel(apartamento);
        }
        catch(ImovelExisteException | SemAutorizacaoException e){
                System.out.println(e.getMessage());
        }
        System.out.println("\n Apartamento registada com Sucesso!");
   }

    public static void registarLoja(){
        String rua, wc, tipoNegocio;
        Double preco, precoMinimo,area;
        int nPorta;
        Scanner scan=new Scanner(System.in);
        System.out.println("============================");
        System.out.println("|     IMOOBILIARIA APP     |");
        System.out.println("============================");
        System.out.println("    Registar Loja");
        System.out.print("           Rua: ");
        rua= scan.nextLine();
        System.out.print("\n           Preço: ");
        preco=scan.nextDouble();
        System.out.print("\n           Preço Mínimo: ");
        precoMinimo=scan.nextDouble();
        System.out.print("\n           Tipo de Negócio: ");
        tipoNegocio=scan.next();
        System.out.print("\n           Área Total: ");
        area=scan.nextDouble();
        System.out.print("\n           Número da Porta: ");
        nPorta=scan.nextInt();
        System.out.print("           Tem WC? (true or false): ");
        wc= scan.nextLine();
        Loja loja= new Loja(rua,preco,precoMinimo,area,Boolean.valueOf(wc),Estado_Imovel.valueOf("Para_Venda"),tipoNegocio,nPorta);
        try{
            tab.registaImovel(loja);
        }
        catch(ImovelExisteException | SemAutorizacaoException e){
            System.out.println(e.getMessage());
        }
        System.out.println("\n Loja registada com Sucesso!");
    }


  public static void registarLojaHabitavel(){
        String rua,tipo,garagem,wc,tipoNegocio;
        Double preco, precoMinimo, area, areaAP;
        int nQuartos, nWCs, nPorta, andar;
        Scanner scan=new Scanner(System.in);
        System.out.println("============================");
        System.out.println("|     IMOOBILIARIA APP     |");
        System.out.println("============================");
        System.out.println("    Registar Loja Habitável");
        System.out.print("           Rua: ");
        rua= scan.nextLine();
        System.out.print("\n           Número da Porta: ");
        nPorta=scan.nextInt();
        System.out.print("\n           Preço: ");
        preco=scan.nextDouble();
        System.out.print("\n           Preço Mínimo: ");
        precoMinimo=scan.nextDouble();
        System.out.print("\n           Tipo de Negócio: ");
        tipoNegocio=scan.next();
        System.out.print("\n           Área Total da Loja: ");
        area=scan.nextDouble();
        System.out.print("           Tem WC? (true or false): ");
        wc= scan.nextLine();
        System.out.print("\n           Área Total do Apartamento: ");
        areaAP=scan.nextDouble();
        System.out.print("\n           Tipo do Apartamento (Simples, Duplex, Triplex, Outro): ");
        tipo=scan.next();
        System.out.print("\n           Número de Quartos: ");
        nQuartos=scan.nextInt();
        System.out.print("\n           Número de WCs do Apartamento: ");
        nWCs=scan.nextInt();
        System.out.print("\n           Número do Andar: ");
        andar=scan.nextInt();
        System.out.print("\n    Tem garagem? (true or false): "); // ver como fazer melhor isto
        garagem=scan.nextLine();
        LojaHabitavel loja= new LojaHabitavel(rua,preco,precoMinimo,area,Boolean.valueOf(wc),Estado_Imovel.valueOf("Para_Venda"),tipoNegocio,nPorta,Tipo_Apartamento.valueOf(tipo),areaAP,nQuartos,nWCs,Boolean.valueOf(garagem),andar);
        try{
            tab.registaImovel(loja);
        }
        catch(ImovelExisteException | SemAutorizacaoException e){
            System.out.println(e.getMessage());
        }
        System.out.println("\n Loja Habitável registada com Sucesso!");
   }

    public static void registarTerreno(){
        String rua,habitacao,armazem,eletricidade,esgotos;
        Double preco, precoMinimo, areaConstrucao,diamCanalizacoes,potenciaEletrica;
        Scanner scan= new Scanner(System.in);
        System.out.println("============================");
        System.out.println("|     IMOOBILIARIA APP     |");
        System.out.println("============================");
        System.out.println("    Registar Terreno");
        System.out.print("           Rua: ");
        rua= scan.nextLine();
        System.out.print("\n           Preço: ");
        preco=scan.nextDouble();
        System.out.print("\n           Preço Mínimo: ");
        precoMinimo=scan.nextDouble();
        System.out.print("\n           Área Total de Construção: ");
        areaConstrucao=scan.nextDouble();
        System.out.print("\n           Permite habitação? (true ou false): ");
        habitacao=scan.nextLine();
        System.out.print("\n           Permite armazém? (true ou false): ");
        armazem=scan.nextLine();
        System.out.print("\n           Diâmetro das canalizações: ");
        diamCanalizacoes=scan.nextDouble();
        System.out.print("\n           Tem eletricidade? (true ou false): ");
        eletricidade=scan.nextLine();
        System.out.print("\n           Potência elétrica: ");
        potenciaEletrica=scan.nextDouble();
        System.out.print("\n           Tem esgotos? (true ou false): ");
        esgotos=scan.nextLine();
        Terreno terreno = new Terreno(rua,preco,precoMinimo,areaConstrucao,Boolean.valueOf(habitacao),Boolean.valueOf(armazem),diamCanalizacoes,Boolean.valueOf(eletricidade),potenciaEletrica,Boolean.valueOf(esgotos),Estado_Imovel.valueOf("Para_Venda"));
        try{
            tab.registaImovel(terreno);
        }
        catch(ImovelExisteException | SemAutorizacaoException e){
            System.out.println(e.getMessage());
        }
        System.out.println("\n Terreno registado com Sucesso!");
   }

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
            case 3:
                do{
                    menuRegistarLoja.executa();
                    switch(menuRegistarLoja.getOpcao()){
                        case 1:
                            registarLoja();
                            break;
                        case 2:
                            registarLojaHabitavel();
                            break;
                    }
                }while(menuRegistarImovel.getOpcao()!=0);
            case 4:
                registarTerreno();
                break;
        }
    }
   else {
            System.out.println("Inserção cancelada!");
    }
    scin.close();
}

public static void AlterarEstado(){
        Scanner scan=new Scanner(System.in);
        String id, estado;
        System.out.println("============================");
        System.out.println("|     IMOOBILIARIA APP     |");
        System.out.println("============================");
        System.out.print("Insira ID do Imovel: ");
        id = scan.nextLine();
        System.out.print("Insira estado do imovel (Para_Venda, Reservado, Vendido, Outro): ");
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
        System.out.println("\nEstado do imovel " + id + "alterado para " + estado);
   }

    public static void comLogin(){
       Scanner scan=new Scanner(System.in);
       if(tab.getUserAtual() instanceof Vendedor){
           do{
            menuVendedor.executa();
                switch(menuVendedor.getOpcao()){
                    case 1:
                        RegistarImovel();
                    case 2:
                        break;
                    case 3:
                        AlterarEstado();
                        break;
                    /*case 5:
                        menugetImoveis();
                        break;
                    case 6:
                        MenuGetHabitaveis(); */
                }
           }while(menuVendedor.getOpcao()!=0);
           fechaSessao();
           return;
           }
        /*if(tab.getUserAtual() instanceof Comprador){
        do{
        menuloggedComprador.executa();
           switch(menuloggedComprador.getOpcao()){
                case 1:
                    limpaEcra();
                    menugetImoveis();
                    break;
                case 2:
                    limpaEcra();
                    MenuGetHabitaveis();
                    break;
                case 3:
                    break;
                case 4:
                    limpaEcra();
                    MenuSetFavoritos();
           }
        }while(menuloggedVendedor.getOpcao()!=0);
        fechaSessao();
        return;
    } */
   }

}
