/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.methotels.services.dao;

import com.mycompany.methotels.entities.Radnik;
import java.util.List;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Laki
 */
public class RadnikDaoImpl implements RadnikDao {

    @Inject
    private Session session;

    @Override
    public Radnik getRadnikById(Integer id) {
        System.out.println("Integer je "+ id);
        return (Radnik) session.createCriteria(Radnik.class).add(Restrictions.eq("id", id)).uniqueResult();
    }

    @Override
    public void dodajRadnika(Radnik radnik) {
        session.persist(radnik);
    }

    @Override
    public void obrisiRadnika(Integer id) {
        Radnik radnik = (Radnik) session.createCriteria(Radnik.class).add(Restrictions.eq("id",
                id)).uniqueResult();
        session.delete(radnik);
    }

    @Override
    public List<Radnik> getListaSvihRadnika() {
        return session.createCriteria(Radnik.class).list();
    }
    
}
