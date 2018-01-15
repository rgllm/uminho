/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trader;

/**
 *
 * @author rgllm
 */
public class CompanyNotFoundException extends Exception {
    public CompanyNotFoundException(String msg){
        super(msg);
    }
}
