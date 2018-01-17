/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rgllm.trader;

import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author rgllm
 */
public interface TraderInterface {

    void addCFD(CFD invest) throws SaldoException, CompanyNotFoundException, IOException;

    void addToWatchList(String company) throws IOException, CompanyNotFoundException;

    boolean existeUtilizador(User u);

    void fechaSessao();

    ArrayList<Asset> getUserNotifications();

    User getloggedUser();

    void iniciaSessao(String email, String password) throws SemAutorizacaoException;

    void insertNotification(Asset asset);
    
    public boolean isLogged();

    void notificationUpdater();

    void portfolioUpdater();

    void registarUtilizador(User u) throws UtilizadorExistenteException;

    void removeCFD(int pos) throws Exception;

    void setUserAtual(User loggedUser);

    ArrayList<CFD> userPortfolio();

    double userSaldo();

    ArrayList<String> userWatchList();
    
}
