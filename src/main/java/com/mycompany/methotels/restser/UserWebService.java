/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.methotels.restser;

import com.mycompany.methotels.data.Role;
import com.mycompany.methotels.entities.User;
import com.mycompany.methotels.pages.RegistracijaKorisnika;
import com.mycompany.methotels.services.dao.UserDao;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import org.apache.tapestry5.ioc.annotations.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;

/**
 *
 * @author Laki
 */
public class UserWebService implements UserServiceInterface{

    @Inject
    private UserDao userDao;

    public List<User> getAll() {
        return (userDao.getListaSvihUsera());
    }
    
    public User getDrzava(@PathParam("id") Integer id) {
        return userDao.getUserById(id);
    }
    
    public Response post(User user)
    {
        if (!userDao.checkIfEmailExists(user.getEmail())) {
            String unhashPassword = user.getSifra();
            user.setSifra(RegistracijaKorisnika.getMD5Hash(unhashPassword));
            user.setRola(Role.Korisnik);
            User u = userDao.registerUser(user);
            return Response.ok().entity(u).build();
        }
        return Response.status(Response.Status.NOT_ACCEPTABLE).build();//.ok().build();
    }
}
