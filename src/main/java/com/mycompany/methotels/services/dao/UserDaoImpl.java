/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.methotels.services.dao;

import com.mycompany.methotels.entities.User;
import java.util.List;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Laki
 */
public class UserDaoImpl implements UserDao {

    @Inject
    private Session session;

    @Override
    public List<User> getListaSvihUsera() {
        return session.createCriteria(User.class).list();
    }
 
    @Override
    public User getUserById(Integer id) {
        return (User) session.createCriteria(User.class).add(Restrictions.eq("id", id)).uniqueResult();
    }
    
    @Override
    public User checkUser(String email, String password) {
        try {
            User u = (User) session.createCriteria(User.class).add(Restrictions.eq("email",
                    email)).add(Restrictions.eq("sifra", password)).uniqueResult();
            if (u != null) {
                return u;
            }
            return null;
        } catch (NullPointerException e) {
            return null;
        }
    }

    @Override
    public boolean checkIfEmailExists(String email) {
        Long rows = (Long) session.createCriteria(User.class).add(Restrictions.eq("email",
                email)).setProjection(Projections.rowCount()).uniqueResult();
        return (rows == 0) ? false : true;
    }

    @Override
    public User registerUser(User user) {
        return (User) session.merge(user);
    }
}
