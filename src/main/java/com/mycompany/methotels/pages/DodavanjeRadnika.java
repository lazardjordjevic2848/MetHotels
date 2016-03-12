/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.methotels.pages;

import com.mycompany.methotels.components.GenericEditor;
import com.mycompany.methotels.entities.Radnik;
import com.mycompany.methotels.services.ProtectedPage;
import com.mycompany.methotels.services.dao.RadnikDao;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;
import org.got5.tapestry5.jquery.components.InPlaceEditor;

/**
 *
 * @author Laki
 */
//@ProtectedPage
@RequiresRoles("Admin")
//@RolesAllowed(value={"Admin"})

/*public class DodavanjeRadnika extends GenericEditor<Radnik> {
	
}*/


public class DodavanjeRadnika {

    @Property
    @Persist
    private Radnik radnik;
    @Property
    private Radnik oneradnik;
    @Inject
    private RadnikDao radnikDao;
    @Property
    private List<Radnik> radnici;

    @InjectComponent
    private Zone zoneRadnici;
    @InjectComponent
    private Zone formZone;
    @Inject
    private Request request;
    @Inject
    private AjaxResponseRenderer ajaxResponseRenderer;
    @Inject
    private ComponentResources _componentResources;
    
    void onActivate() {
        if (radnici == null) {
            radnici = new ArrayList<Radnik>();
        }
        radnici = radnikDao.getListaSvihRadnika();
    }

    @CommitAfter
    Object onSuccess() {
        radnikDao.dodajIliUpdatujRadnika(radnik);
        radnik = new Radnik();

        if (request.isXHR()) {
            ajaxResponseRenderer.addRender(zoneRadnici).addRender(formZone);
        }
        return request.isXHR() ? zoneRadnici.getBody() : null;    
        //return this;
    }

    @CommitAfter
    Object onActionFromDelete(int id) {
        radnikDao.obrisiRadnika(id);
        return request.isXHR() ? zoneRadnici.getBody() : null;
        //return this;
    }
    
    @CommitAfter
    Object onActionFromEdit(Radnik radnik) {
        this.radnik = radnik;
        return request.isXHR() ? formZone.getBody() : null;
        //return this;
    }
    
    @CommitAfter
    @OnEvent(component = "ime", value = InPlaceEditor.SAVE_EVENT)
    void setImeDrzave(Long id, String value) {
        Radnik radnik2 = (Radnik) radnikDao.getRadnikById(id.intValue());
        radnik2.setIme(value);
        System.out.println("cuvam radnika");
        radnikDao.dodajIliUpdatujRadnika(radnik2);
    }
}