
/**
 * Escreva a descrição da classe EmailDoesNotExistException aqui.
 * 
 * @author (seu nome) 
 * @version (número de versão ou data)
 */
public class EmailDoesNotExistException extends Exception
{
    public EmailDoesNotExistException(){
        super();
    }
    
    public EmailDoesNotExistException(String msg){
        super(msg);
    }
}
