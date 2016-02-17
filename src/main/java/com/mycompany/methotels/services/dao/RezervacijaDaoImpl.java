/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.methotels.services.dao;

import com.mycompany.methotels.entities.Rezervacija;
import com.mycompany.methotels.entities.Soba;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Laki
 */
public class RezervacijaDaoImpl implements RezervacijaDao {
    @Inject
    private Session session;

    @Override
    public List<Rezervacija> getListaRezervacija() {
        Criteria cr = session.createCriteria(Rezervacija.class);
        return cr.list();
    }

    @Override
    public List<Rezervacija> getListaRezervacijaZaSobu(Soba sobaId) {
        Criteria cr = session.createCriteria(Rezervacija.class);
        cr.add(Restrictions.eq("soba_id", sobaId));
        return cr.list();
    }

    @Override
    public List<Rezervacija> checkForOverlap(Rezervacija rez) {
        Criteria cr = session.createCriteria(Rezervacija.class);
        Criterion sobaCr = Restrictions.eq("sobaId", rez.getSobaId());
        Criterion krajCr = Restrictions.gt("kraj", rez.getPocetak());
        Criterion pocetakCr = Restrictions.lt("pocetak", rez.getKraj());
        Criterion komb = Restrictions.and(sobaCr, krajCr, pocetakCr);
        cr.add(komb);
        List<Rezervacija> overlapList = cr.list();
//        int overlapping = overlapList.size();
//        Long overlapping = (Long) cr.setProjection(Projections.rowCount()).uniqueResult();
        return overlapList;
    }

    @Override
    public Rezervacija dodajRezervaciju(Rezervacija rezervacija) {
        return (Rezervacija) session.merge(rezervacija);
    }

    @Override
    public void obrisiRezervaciju(Integer id) {
        Rezervacija rezervacija = (Rezervacija) session.createCriteria(Rezervacija.class).add(Restrictions.eq("id",
                id)).uniqueResult();
        session.delete(rezervacija);
    }

//    private Date krajRezervacije(Date pocetak, int trajanje) {
//        Calendar cal = Calendar.getInstance();
//        cal.setTime(pocetak);
//        cal.add(Calendar.DATE, trajanje);
//        return cal.getTime();
//    }

    @Override
    public Date ukloniVreme(Date d) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
}
