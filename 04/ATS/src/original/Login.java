
/**
 * Write a description of class Login here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.io.IOException;
import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.Set;
import java.util.TreeSet;
import java.util.List;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Random;
public class Login{
    private Set<Motorista> motoristas;
    
    public void executa(Utilizadores utilizadores, HashMap<String,Veiculo> veiculos) {
        String email,password;
        Scanner scin = new Scanner(System.in);
        motoristas = new TreeSet<>();
        for(Ator u:utilizadores.getUtilizadores().values()){
            if(u instanceof Motorista)
                motoristas.add((Motorista) u);            
        }
        
        System.out.println("\n-----------Login-----------");
        System.out.print("Email: ");
        email = scin.nextLine();
        
        System.out.print("Password: ");
        password = scin.nextLine();
            
        if(utilizadores.getUtilizadores().containsKey(email) && utilizadores.getUtilizadores().get(email).getPassword().equals(password)){
            System.out.println("Login efetuado com sucesso!");
            if(utilizadores.getUtilizadores().get(email) instanceof Cliente)
                loginCliente(utilizadores,veiculos,email);
            else
                loginMotorista(utilizadores,veiculos,email);
        }            
        else
            System.out.println("Dados de Login inválidos!");            

        
    }
    
    private void loginCliente( Utilizadores utilizadores,HashMap<String,Veiculo> veiculos,String email){
        Cliente c = (Cliente) utilizadores.getUtilizadores().get(email);
        Scanner scin = new Scanner(System.in);
        int i;
        do{
            
            System.out.println("\n-----------Bem-vindo "+ c.getNome() + "-----------");
            System.out.println("1 - Chamar viatura");        
            System.out.println("2 - Ver dados pessoais");
            System.out.println("3 - Ver histórico de viagens");
            System.out.println("0 - Logout");        
            System.out.print("Opção: ");
        
        
            try {
                i = scin.nextInt();
            }
            catch (InputMismatchException e) {
                i=-1;
            }
        
            if (i==1){
                int nLugares;
                System.out.println("Indique o número de lugares pretendidos.");
                nLugares = scin.nextInt();
                if(nLugares > 0 && nLugares < 10) chamarViatura(c, utilizadores, nLugares); 
                else System.out.println("A UMeR não suporta veículos com essa capacidade.");
            }
            else if(i==2)
                verDados(c);
            else if(i==3)
                verHistorico(c);
            else if(i!=0)
                System.out.println("Opção Inválida!!!"); 
        }while(i!=0);
        
        System.out.println("Até breve!"); 

    }
    
    
    private void chamarViatura(Cliente c, Utilizadores utilizadores, int nLugares){
        Scanner scin = new Scanner(System.in);
        int x=0,y=0,a=0,b=0;
        boolean sucesso;
        
        System.out.println("\n-----------Chamar viatura mais próxima-----------");
        System.out.println("Insira as coordenadas em que se encontra: ");
        do{
            sucesso = true;
            System.out.print("X: ");
            try {
                x = scin.nextInt();
            }
            catch (InputMismatchException e) {
                sucesso = false;
            }
            System.out.print("Y: ");
            try {
                y = scin.nextInt();
            }
            catch (InputMismatchException e) {
                sucesso = false;
            }
            if(!sucesso) System.out.println("Erro nas coordenadas!!!");
        }while(!sucesso);        
        
        System.out.println("Insira as coordenadas de destino: ");
        do{
            sucesso = true;
            System.out.print("X: ");
            try {
                a = scin.nextInt();
            }
            catch (InputMismatchException e) {
                sucesso = false;
            }
            System.out.print("Y: ");
            try {
                b = scin.nextInt();
            }
            catch (InputMismatchException e) {
                sucesso = false;
            }
            if(!sucesso) System.out.println("Erro nas coordenadas!!!");
        }while(!sucesso);
        Coordenada inicio = new Coordenada(x,y);
        Coordenada fim = new Coordenada(a,b);
        
        String emailmotorista = closerMotorista(inicio, nLugares);
        
        if(emailmotorista==null){
            System.out.println("Não existem motoristas que cumpram os seus requisitos disponíveis de momento.");           
            
        }
        else{
            System.out.println("O motorista " + emailmotorista +" vem a caminho.");  
            try{
                Motorista taxistaEscolhido =  (Motorista)utilizadores.getAtor(emailmotorista);
                realizarViagem(c, taxistaEscolhido, inicio.distancia(taxistaEscolhido.getVeiculo().getCoordenadas()), inicio, fim);
            }
            catch(EmailDoesNotExistException e){System.out.println("Unexpected Error. Estrutura corrompida?");}
        }
        
    }
    
    private String closerMotorista(Coordenada loc, int nLugares){
        String email=null;
        int d=Integer.MAX_VALUE;
        
        for(Motorista m :this.motoristas){
            if(m.getDisponibilidade() && m.getVeiculo().getLugares() >= nLugares && d>m.getVeiculo().getCoordenadas().distancia(loc))
                email = m.getMail();
        }
               
        return email;
    }    
    
    private void loginMotorista( Utilizadores utilizadores,HashMap<String,Veiculo> veiculos,String email){
        Motorista m = (Motorista) utilizadores.getUtilizadores().get(email);
        Scanner scin = new Scanner(System.in);
        int i;
        do{
            
            System.out.println("\n-----------Bem-vindo "+ m.getNome() + "-----------");
            if(m.getDisponibilidade())
                System.out.println("--Status : Em horário de trabalho--");
            else 
                System.out.println("--Status : Fora de horário de trabalho--");
            System.out.println("1 - Mudar status");        
            System.out.println("2 - Ver dados pessoais");
            System.out.println("3 - Ver histórico de viagens");
            System.out.println("4 - Associar viatura");
            System.out.println("5 - Ver dados da viatura");
            System.out.println("6 - Ver montante faturado num período de tempo");
            System.out.println("0 - Logout");        
            System.out.print("Opção: ");
             
            try {
                i = scin.nextInt();
            }
            catch (InputMismatchException e) {
                i=-1;
            }
        
            if (i==1){
                if(!m.getDisponibilidade() && m.getVeiculo()==null)
                    System.out.println("Só pode estar em horário de trabalho se tiver uma viatura associada(Opção 4)");
                else if(m.getDisponibilidade()){
                    m.getVeiculo().setCoordenadas(new Coordenada(0,0));
                    m.getVeiculo().setOcupado(false);
                    m.setVeiculo(null);
                    m.setDisponibilidade(false);
                }
                else
                    m.setDisponibilidade(!m.getDisponibilidade());                
            }
            else if(i==2)
                verDados(m);
            else if(i==3)
                verHistorico(m);
            else if(i==4)
                associarViatura(m,veiculos);
            else if(i==5){
                System.out.println("\n-----------Dados da viatura-----------");
                if(m.getVeiculo()!=null) System.out.println(m.getVeiculo().toString());
            }
            else if(i==6)
                verMontanteFaturado(m);
            else if(i!=0)
                System.out.println("Opção Inválida!!!"); 
        }while(i!=0);
        
        System.out.println("Até breve!"); 

    }
    
    private void associarViatura(Motorista m,HashMap<String,Veiculo> veiculos){
        int i=0;
        if(m.getVeiculo()!=null){
            m.getVeiculo().setOcupado(false);
            m.setVeiculo(null);
        }
            
        System.out.println("\n-----------Lista de viaturas disponiveis-----------");
        for(Veiculo v:veiculos.values())
            if(!v.getOcupado()){
                System.out.println(v.getMatricula().toString());
                i++;
            }
        
        if(i==0) 
            System.out.println("\nNão existem viaturas disponiveis");
        else{
            System.out.println("\nIndique a matricula da viatura que quer associar:");
            Scanner scin = new Scanner(System.in);
            String matricula = scin.nextLine();
            
            if(veiculos.containsKey(matricula) && veiculos.get(matricula).getOcupado()==false){
                m.setVeiculo(veiculos.get(matricula));
                veiculos.get(matricula).setOcupado(true);
                System.out.println("\nViatura adicionada com sucesso"); 
            }
            else
                System.out.println("\nMatricula inexistente");
        }
    }
    
    private void verDados(Ator a){
        System.out.println("\n-----------Dados pessoais-----------");
        System.out.println("Email: " + a.getMail());        
        System.out.println("Nome: " + a.getNome());
        System.out.println("Password: " + a.getPassword());        
        System.out.println("Morada: " + a.getMorada());
        System.out.println("Data de nascimento: " + a.getDataDeNascimento());
        if(a instanceof Cliente){
            Cliente c = (Cliente) a;
            System.out.println("Dinheiro gasto em serviços: " + String.format("%.2f",c.getMS()) +"€");
        }
        else{
            Motorista m = (Motorista) a;
            System.out.println("Grau de cumprimento: " + m.getGrau());        
            System.out.println("Classificação: " + m.getClassificacao());
            System.out.println("Kms totais: " + String.format("%.2f",m.getKmsTotais()));         
        }
        
    }
    
    private void verHistorico(Ator a){
        int op;
        GregorianCalendar aux = new GregorianCalendar();
        boolean cond = true;
        Scanner sc = new Scanner(System.in);
        
        System.out.println("\n-----------Historico de viagens-----------");
        System.out.println("1 - Todas as viagens");
        System.out.println("2 - Viagens entre datas");
        System.out.print("Opção: ");
        op = sc.nextInt();
        
        if(op == 1){
            System.out.println("\n-----------Historico de viagens-----------");
            Set<Viagem> historico = a.getHistorico();
            
            if(historico.size() > 0){
                for(Viagem v:historico){
                    System.out.println("\n--Viagem--");
                    System.out.println(v.toString());
                }
            }
            else System.out.println("O utilizador não realizou nenhuma viagem até ao momento.");
        }
        else if(op == 2){
            while(cond){
                System.out.println("\nIntroduza a data de início.");
                GregorianCalendar inicio = dataInicial();
                
                System.out.println("\nIntroduza a data final.");
                GregorianCalendar fim = dataFinal();
                
                try{
                    List<Viagem> hist = a.viagensEntreDatas(inicio, fim);
                    
                    System.out.println("\n-----------Historico de viagens-----------");
                    if(hist.size() > 0) hist.forEach(v -> System.out.println(v.toString()));
                    else System.out.println("Não existem registos de viagem no período considerado.");
                    cond = false;
                }
                catch(InvalidIntervalException e){System.out.println("Certefique-se de que a data de inicio é mais antiga que a de fim."); cond = true;}
            }
        }
        else System.out.println("Opção inválida!");
        

    }
    
    /**
     * Realizar uma viagem.
     * @param c
     * @param m
     */
    public void realizarViagem(Cliente c, Motorista m, double distanciaAteCliente, Coordenada inicio, Coordenada fim){
        m.setDisponibilidade(false); //Motorista não pode ser solicitado por outros clientes enquanto realiza uma viagem.
        
        double distanciaViagem = inicio.distancia(fim);
        
        double tempoTotalEsperado = m.tempoViagem(distanciaViagem + distanciaAteCliente);
        
        /** Calcula-se aqui o tempo real tendo em conta os condicionantes.*/
        Random r = new Random();
        double fiab = r.nextDouble() * (1+(double)(m.getVeiculo().getFiabilidade()/100));
        
        if(fiab > 1) fiab = 1; /** Garantimos que a fiabilidade não passa de 100 */
        
        double tempoReal = tempoTotalEsperado + (1 - fiab)*tempoTotalEsperado;
        
        /** Por consequencia de que, pra já, tempo real = tempo esperado, o preço a pagar vai se calcular para a diferença entre tempo <25%.*/
        double preco = m.precoViagem(distanciaViagem);
        
        double diferenca = tempoReal - tempoTotalEsperado;
        
        if(diferenca > 0.25 * tempoTotalEsperado){
            System.out.println("Desculpe pelo atraso, terá um desconto de "+String.format("%.2f",50*diferenca/tempoTotalEsperado)+"%.");
            preco = preco*(diferenca/tempoTotalEsperado);
        }
        System.out.println("Preço a pagar: "+ String.format("%.2f",preco)+"€");
        /** Atualização do dinheiro total investido no sistema UMeR por este cliente. */
        c.setMS(c.getMS() + preco);
        
        System.out.println("Atribua ao motorista um classificação entre 0 e 100.");
        Scanner sc = new Scanner(System.in);
        int classificacao;
        boolean flag = true;
        do{
            try{
                classificacao = sc.nextInt();
            }
            catch (InputMismatchException e){classificacao = (-1);}
            
            try{ 
                /** Atualização da classificação do motorista. */
                m.atualizaClassificacao(classificacao);
                flag = false;
            }
            catch (ValueOutOfBoundsException e){
                System.out.println("Por favor, introduza uma classificação válida.");
            }
        }while(flag);
        
        /** Atualização do registo de viagem de ambos, cliente e motorista. */
        c.registaViagem(new Viagem(inicio, fim, tempoReal, m.getMail(), new GregorianCalendar(), preco, diferenca));
        m.registaViagem(new Viagem(inicio, fim, tempoReal, c.getMail(), new GregorianCalendar(), preco, diferenca));
        
        /** Atualização dos dados do motorista e do seu veículo. */
        m.atualizaDados(fim, distanciaViagem + distanciaAteCliente, fiab, diferenca/tempoTotalEsperado);
    }
    
    /**
     * Ver o montante faturado num período de tempo do motorista.
     * @param m
     */
    public void verMontanteFaturado(Motorista m){
        Scanner sc = new Scanner(System.in);
        boolean cond = true;
        int op;
        
        System.out.println("\n-----------Montante faturado-----------");
        System.out.println("1 - Até ao momento");
        System.out.println("2 - Entre datas");
        System.out.print("Opção: ");
        
        op = sc.nextInt();
        
        if(op == 1){
           System.out.println("\n-----------Montante faturado-----------");
           System.out.println(String.format("%.2f",m.totalFaturado())+"€");
        }
        else if(op == 2){
            System.out.println("\nIntroduza a data de início.");
            GregorianCalendar inicio = dataInicial();
                    
            System.out.println("\nIntroduza a data final.");
            GregorianCalendar fim = dataFinal();
            
            System.out.println("\n-----------Montante faturado-----------");
            System.out.println(String.format("%.2f",m.totalFaturado(inicio, fim))+"€");
        }
        else System.out.println("Opção inválida!!");
        

    }
    
    /**
     * Pede ao utilizador para introduzir uma data inicial.
     * @return
     */
    public GregorianCalendar dataInicial(){
        int anoI, mesI, diaI;
        anoI = mesI = diaI = 0;
        boolean cond = true;
        GregorianCalendar aux = new GregorianCalendar();
        Scanner sc = new Scanner(System.in);
        
        System.out.print("Ano:");
        while(cond){
           anoI = sc.nextInt();
           if(anoI < 0) System.out.println("Introduza um ano válido!");
           else cond = false;
        }
                
        System.out.print("Mês:");
        cond = true;
        while(cond){
           mesI = sc.nextInt();
           if(mesI < 0 || mesI > 12) System.out.println("Introduza um mês válido!");
           else cond = false;
        }
                
        System.out.print("Dia:");
        cond = true;
        while(cond){
           diaI = sc.nextInt();
           if(diaI > 0 && diaI < 29 
           || diaI > 28 && diaI < 32 && (mesI == 1 || mesI == 3 || mesI == 5 || mesI == 7 || mesI == 8 || mesI == 10 || mesI == 12)
           || diaI > 28 && diaI < 31 && (mesI == 4 || mesI == 6 || mesI == 9 || mesI == 11)
           || diaI == 29 && aux.isLeapYear(anoI)) cond = false;
           else System.out.println("Introduza um dia válido!");
        }
        

        
        return new GregorianCalendar(anoI,mesI,diaI);
    }
    
    /**
     * Pede ao utilizador para introduzir uma data final.
     * @return
     */
    public GregorianCalendar dataFinal(){
        int anoF, mesF, diaF;
        anoF = mesF = diaF = 0;
        GregorianCalendar aux = new GregorianCalendar();
        Scanner sc = new Scanner(System.in);
        
        System.out.print("Ano:");
        boolean cond = true;
        while(cond){
          anoF = sc.nextInt();
          if(anoF < 0) System.out.println("Introduza um ano válido!");
          else cond = false;
        }
                
        System.out.print("Mês:");
        cond = true;
        while(cond){
           mesF = sc.nextInt();
           if(mesF < 0 || mesF > 12) System.out.println("Introduza um mês válido!");
           else cond = false;
        }
                
        System.out.print("Dia:");
        cond = true;
        while(cond){
           diaF = sc.nextInt();
           if(diaF > 0 && diaF < 29 
           || diaF > 28 && diaF < 32 && (mesF == 1 || mesF == 3 || mesF == 5 || mesF == 7 || mesF == 8 || mesF == 10 || mesF == 12)
           || diaF > 28 && diaF < 31 && (mesF == 4 || mesF == 6 || mesF == 9 || mesF == 11)
           || diaF == 29 && aux.isLeapYear(anoF)) cond = false;
           else System.out.println("Introduza um dia válido!");
        }
        

        
        return new GregorianCalendar(anoF,mesF,diaF,23,59,59);
    }
}
