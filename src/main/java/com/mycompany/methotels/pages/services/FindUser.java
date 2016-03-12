/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.methotels.pages.services;

import com.mycompany.methotels.entities.User;
import com.mycompany.methotels.services.dao.UserDao;
import java.util.ArrayList;
import java.util.List;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.RequestParameter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.util.TextStreamResponse;

/**
 *
 * @author Laki
 */
public class FindUser {
    @Inject
    private Request request;
    @Property
    private List<User> useri;
    @Property
    private User user;
    @Inject
    private UserDao userDao;

    Object onActivate(@RequestParameter("email") String email) {
        System.out.println("email string je "+ email);
        if (useri == null) {
            useri = new ArrayList<User>();
        }
        String response = "<table class=\"navigation\" > <th>\n"
                + " Email usera\n"
                + " </th>\n"
                + " ";
        useri = userDao.getListaUseraPoEmailu(email);
        for (User u : useri) {
            response += (" <tr>\n"
                    + " <td> " + u.getEmail() + "</td>\n"
                    + " </tr>");
        }
        response += "</table>";
        return new TextStreamResponse("text/plain", response);
    }
}
