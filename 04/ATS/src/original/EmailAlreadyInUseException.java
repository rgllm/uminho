
/**
 * Escreva a descrição da classe EmailAlreadyInUseException aqui.
 * 
 * @author (seu nome) 
 * @version (número de versão ou data)
 */
public class EmailAlreadyInUseException extends Exception
{
    /**
     * Construtor vazio
     */
    public EmailAlreadyInUseException(){
        super();
    }
    
    /**
     * Construtor parametrizado
     * @param msg
     */
    public EmailAlreadyInUseException(String msg){
        super(msg);
    }
}
