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
import org.apache.tapestry5.annotations.RequestParameter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.util.TextStreamResponse;

/**
 *
 * @author Laki
 */
public class PaginationGrid {

    @Inject
    private UserDao userDao;

    Object onActivate(@RequestParameter("page") int page) {
        Class<?> act = null;
        int sizeOfAll = userDao.allActiveSizeUseri();
        List<User> lista = new ArrayList<User>();
        String response = "<table class=\"navigation\" > <th>\n"
                + " Email usera\n"
                + " </th>\n"
                + " ";
        lista = userDao.loadActiveFromTo(page);
        for (User u : lista) {
            response += (" <tr>\n"
                    + " <td> " + u.getEmail()+ "</td>\n"
                    + " </tr>");
        }
        response += "</table>";
        float ceil = (float) sizeOfAll / (float) 3;
        int totalPageSizes = (int) Math.ceil(ceil);
        response += "<ul class=\"pagination\">";
        for (int i = 1; i <= totalPageSizes; i++) {
            if (page == i) {
                response += ("<li class=\"callservice active\"><a href=\"#\">" + i + "</a></li>\n");
            } else {
                response += ("<li class=\"callservice\"><a href=\"#\">" + i + "</a></li>\n");
            }
        }
        response += "</ul>";
        return new TextStreamResponse("text/plain", response);
    }
}
