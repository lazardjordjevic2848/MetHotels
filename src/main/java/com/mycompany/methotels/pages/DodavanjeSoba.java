/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.methotels.pages;

import com.mycompany.methotels.entities.Radnik;
import com.mycompany.methotels.entities.Soba;
import com.mycompany.methotels.services.dao.RadnikDao;
import com.mycompany.methotels.services.dao.SobaDao;
import java.util.ArrayList;
import java.util.List;
import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;

/**
 *
 * @author Laki
 */
public class DodavanjeSoba {

    @Property
    private Soba soba;
    @Property
    private Soba onesoba;
    @Inject
    private SobaDao sobaDao;
    @Inject
    private Messages messages;
    @Property
    private Radnik radId;
    @Inject
    private RadnikDao radnikDao;
    @Property
    private List<Radnik> radnici;
    //@Inject
    //private Session session;
    
    @Property
    private List<Soba> sobe;

    
    public ValueEncoder getEncoder() {
        return new ValueEncoder<Radnik>() {
            @Override
            public String toClient(Radnik r) {
                System.out.println("toClient string je "+ r.getId());
                return String.valueOf(r.getId());
            }

            @Override
            public Radnik toValue(String string) {
                System.out.println("toValue string je "+ string);
                Radnik rad = radnikDao.getRadnikById(Integer.parseInt(string));
                return rad;
            }
        };
    }
    
    
    void onActivate() {
        if (sobe == null) {
            sobe = new ArrayList<Soba>();
        }
        // createCriteria metoda pravi Select * upit nad prosle?enom klasom
        //sobe = (ArrayList<Soba>) session.createCriteria(Soba.class).list();
        sobe = sobaDao.getListaSvihSoba();
        radnici = radnikDao.getListaSvihRadnika();
    }

    @CommitAfter
    Object onSuccess() {
        // persist metoda ?uva objekatu bazi podataka
        //session.persist(soba);
        soba.setRadId(radId.getId());
        sobaDao.dodajSobu(soba);
        return this;
    }
    
    
    @CommitAfter
    Object onActionFromDelete(int id) {
        sobaDao.obrisiSobu(id);
        return this;
    }
    
    public String getRadnik() {
        System.out.println("getRadnik");
        if (onesoba.getRadId()!= null) {
            return radnikDao.getRadnikById(onesoba.getRadId()).getIme();
        } else {
            return "";
        }
    }
}

