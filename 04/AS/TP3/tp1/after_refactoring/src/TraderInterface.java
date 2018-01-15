package trader;

import java.util.ArrayList;
import java.util.Map;

public interface TraderInterface {

	boolean isLogged();

	User getloggedUser();

	Map<String, User> getUsers();

	void setUserAtual(User loggedUser);

	void setUsers(Map<String, User> newUsers);

	boolean existeUtilizador(User u);

	void registarUtilizador(User u) throws UtilizadorExistenteException;

	void iniciaSessao(String email, String password) throws SemAutorizacaoException;

	void fechaSessao();

	void addToWatchList(String company);

	void addCFD(CFD invest) throws SaldoException, CompanyNotFoundException;

	void removeCFD(int pos) throws Exception;

	ArrayList<CFD> userPortfolio();

	ArrayList<String> userWatchList();

	double userSaldo();

	void portfolioUpdater();

}