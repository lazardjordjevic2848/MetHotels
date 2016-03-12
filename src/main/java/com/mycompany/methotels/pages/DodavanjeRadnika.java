/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.methotels.pages;

import com.mycompany.methotels.components.GenericEditor;
import com.mycompany.methotels.entities.Radnik;
import com.mycompany.methotels.services.ProtectedPage;
import javax.annotation.security.RolesAllowed;

/**
 *
 * @author Laki
 */
@ProtectedPage
@RolesAllowed(value={"Admin"})
public class DodavanjeRadnika extends GenericEditor<Radnik> {
	
}

/*
public class DodavanjeRadnika {

    @Property
    private Radnik radnik;
    @Property
    private Radnik oneradnik;
    @Inject
    private RadnikDao radnikDao;
    @Property
    private List<Radnik> radnici;

    void onActivate() {
        if (radnici == null) {
            radnici = new ArrayList<Radnik>();
        }
        radnici = radnikDao.getListaSvihRadnika();
    }

    @CommitAfter
    Object onSuccess() {
        radnikDao.dodajRadnika(radnik);
        return this;
    }

    @CommitAfter
    Object onActionFromDelete(int id) {
        radnikDao.obrisiRadnika(id);
        return this;
    }
}*/