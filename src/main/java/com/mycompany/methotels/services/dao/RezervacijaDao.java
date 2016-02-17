/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.methotels.services.dao;

import com.mycompany.methotels.entities.Rezervacija;
import com.mycompany.methotels.entities.Soba;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Laki
 */
public interface  RezervacijaDao {
    public List<Rezervacija> getListaRezervacija();
    public List<Rezervacija> getListaRezervacijaZaSobu(Soba sobaId);
    public List<Rezervacija> checkForOverlap(Rezervacija rez); 
    public Rezervacija dodajRezervaciju(Rezervacija rezervacija);
    public void obrisiRezervaciju(Integer id);    
    public Date ukloniVreme(Date d);
}
