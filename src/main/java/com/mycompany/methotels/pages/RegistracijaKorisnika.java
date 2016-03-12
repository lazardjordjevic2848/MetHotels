/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.methotels.pages;

import com.mycompany.methotels.data.Role;
import com.mycompany.methotels.entities.User;
import com.mycompany.methotels.services.ProtectedPage;
import com.mycompany.methotels.services.dao.UserDao;
import javax.annotation.security.RolesAllowed;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.beaneditor.Validate;
import org.apache.tapestry5.corelib.components.BeanEditForm;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import com.mycompany.methotels.components.GenericEditor;

/**
 *
 * @author Laki
 */

@ProtectedPage
@RolesAllowed(value={"Admin"})
public class RegistracijaKorisnika extends GenericEditor<User> {
}
/*
public class RegistracijaKorisnika {

    @Property
    private User userReg;
    @SessionState
    private User loggedInUser;
    @Inject
    private UserDao userDao;
    //@Property
    //@Validate("required")
    //private Role userRola;
    @Component
    private BeanEditForm form;

    public String getMD5Hash(String yourString) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(yourString.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (Exception e) {
            return "";
        }
    }

    @CommitAfter
    Object onSuccess() {
        if (!userDao.checkIfEmailExists(userReg.getEmail())) {
            String unhashPassword = userReg.getSifra();
            userReg.setSifra(getMD5Hash(unhashPassword));
            userReg.setRola(Role.Korisnik);
            User u = userDao.registerUser(userReg);
            loggedInUser = u;
            return Index.class;
        } else {
            form.recordError("Email koji ste uneli vec postoji");
            return null;
        }
    }
}
*/