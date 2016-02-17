/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.methotels.pages;

import com.mycompany.methotels.entities.Rezervacija;
import com.mycompany.methotels.entities.Soba;
import com.mycompany.methotels.entities.User;
import com.mycompany.methotels.services.ProtectedPage;
import com.mycompany.methotels.services.dao.RezervacijaDao;
import com.mycompany.methotels.services.dao.SobaDao;
import com.mycompany.methotels.services.dao.UserDao;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.BeanEditForm;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;

/**
 *
 * @author Laki
 */
@ProtectedPage
@RolesAllowed(value={"Admin"})
public class Rezervisanje {
    @Property
    private Rezervacija rez;

    @SessionState
    private User loggedInUser;

    @Inject
    private RezervacijaDao rezDao;

    @Inject
    private Session session;

    @Component
    private BeanEditForm form;

    @Property
    private List<Rezervacija> rezervacije;

    @Property
    Rezervacija rowrezervacija;

    @Property
    @Persist
    private List<User> useri;
    @Inject
    private UserDao userDao;
    @Property
    private User userId;

    public ValueEncoder getUserEncoder() {
        return new ValueEncoder<User>() {
            @Override
            public String toClient(User u) {
                return String.valueOf(u.getId());
            }

            @Override
            public User toValue(String str) {
                return userDao.getUserById(Integer.parseInt(str));
            }
        };
    }

    @Property
    @Persist
    private List<Soba> sobe;
    @Inject
    private SobaDao sobeDao;
    @Property
    private Soba sobaId;

    public ValueEncoder getSobaEncoder() {
        return new ValueEncoder<Soba>() {
            @Override
            public String toClient(Soba s) {
                return String.valueOf(s.getId());
            }

            @Override
            public Soba toValue(String str) {
                return sobeDao.getSobaById(Integer.parseInt(str));
            }
        };
    }

    void onActivate() {
        if (rezervacije == null) {
            rezervacije = new ArrayList<Rezervacija>();
        }
        rezervacije = rezDao.getListaRezervacija();
        useri = userDao.getListaSvihUsera();
        sobe = sobeDao.getListaSvihSoba();
    }

    void onValidateFromForm() {

        rez.setKraj(rezDao.ukloniVreme(rez.getKraj()));
        rez.setPocetak(rezDao.ukloniVreme(rez.getPocetak()));
        Date danas = new Date();
        danas = rezDao.ukloniVreme(danas);
        if (rez.getPocetak().compareTo(danas) < 0) {
            form.recordError("Pocetak ne moze biti u proslosti");
        }
        if (rez.getPocetak().compareTo(rez.getKraj()) >= 0) {
            form.recordError("Pocetak mora biti pre kraja");
        }
    }

    @CommitAfter
    Object onSuccess() {
        rez.setSobaId(sobaId);
        rez.setUserId(userId);
        List<Rezervacija> overlapList = rezDao.checkForOverlap(rez);
        if (overlapList.size() > 0) {
            String izlaz = "Soba je zauzeta: ";
            for (Rezervacija r : overlapList) {
                izlaz += r.getPocetak() + " do " + r.getKraj() + ", ";
            }
            form.recordError(izlaz);
        } else {
            rezDao.dodajRezervaciju(rez);
        }
        return null;
    }

    @CommitAfter
    Object onActionFromDelete(int id) {
        rezDao.obrisiRezervaciju(id);
        return this;
    }

    public String getRowUser() {
        if (rowrezervacija.getUserId() != null) {
            return rowrezervacija.getUserId().toString();
        } else {
            return "";
        }
    }

    public String getRowSoba() {
        if (rowrezervacija.getSobaId() != null) {
            return rowrezervacija.getSobaId().toString();
        } else {
            return "";
        }
    }
}
