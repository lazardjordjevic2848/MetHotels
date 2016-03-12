/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.methotels.services.dao;

import com.mycompany.methotels.entities.User;
import java.util.List;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
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
    public User getUserByEmail(String email) {
        return (User) session.createCriteria(User.class).add(Restrictions.eq("email", email)).uniqueResult();
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
        session.saveOrUpdate(user);
	return user;
        //return (User) session.merge(user);
    }

    @Override
    public List<User> getListaUseraPoEmailu(String email) {
        return session.createCriteria(User.class).add(Restrictions.ilike("email", email + "%")).list();
    }
    
    @Override
    public int allActiveSizeUseri() {
        Long l = (Long) session.createCriteria(User.class).setProjection(Projections.rowCount()).uniqueResult();
        return l.intValue();
    }
            
    @Override
    public List<User> loadActiveFromTo(int from)
    {
        int page = (from - 1) * 3;
        List<User> lista = session.createCriteria(User.class).setFirstResult(page).setMaxResults(3).addOrder(Order.asc("id")).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
        return lista;
    }
    
    @Override
    public User merge(User user)
    {
        return (User) session.merge(user);
    }
}
