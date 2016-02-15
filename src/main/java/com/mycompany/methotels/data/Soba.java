/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.methotels.data;

import org.apache.tapestry5.ioc.annotations.Inject;

/**
 *
 * @author Laki
 */
public class Soba {
    private String ime;
    private int sprat;
    private boolean imaTv;
    private boolean imaInternet;
    private boolean imaDjakuzi;
    
    @Inject
    public Soba() {
    }
    public Soba(String ime, int sprat, boolean imaTv, boolean imaInternet,boolean imaDjakuzi) 
    {
        this.ime = ime;
        this.sprat = sprat;
        this.imaTv = imaTv;
        this.imaInternet = imaInternet;
        this.imaDjakuzi = imaDjakuzi;
    }
    
    public String getIme() {
        return ime;
    }
    public void setIme(String ime) {
        this.ime = ime;
    }
    
    public int getSprat() {
        return sprat;
    }
    public void setSprat(int sprat) {
        this.sprat = sprat;
    }
    
    public boolean getImaTv() {
        return imaTv;
    }
    public void setImaTv(boolean imaTv) {
        this.imaTv = imaTv;
    }
    
    public boolean getImaInternet() {
        return imaInternet;
    }
    public void setImaInternet(boolean imaInternet) {
        this.imaInternet = imaInternet;
    }
    
    public boolean getImaDjakuzi() {
        return imaDjakuzi;
    }
    public void setImaDjakuzi(boolean imaDjakuzi) {
        this.imaDjakuzi = imaDjakuzi;
    }
}



