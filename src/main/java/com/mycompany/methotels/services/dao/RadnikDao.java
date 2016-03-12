/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.methotels.services.dao;

import com.mycompany.methotels.entities.Radnik;
import java.util.List;

/**
 *
 * @author Laki
 */
public interface RadnikDao {
    public List<Radnik> getListaSvihRadnika();
    public Radnik getRadnikById(Integer id);
    public void dodajRadnika(Radnik radnik);
    public void obrisiRadnika(Integer id);
    public void dodajIliUpdatujRadnika(Radnik radnik);

}
