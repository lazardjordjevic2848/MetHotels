/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.methotels.components;

import com.mycompany.methotels.entities.AbstractEntity;
import com.mycompany.methotels.services.dao.GenericDao;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.PageLoaded;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.beaneditor.BeanModel;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.BeanModelSource;
import org.apache.tapestry5.services.PropertyConduitSource;

/**
 *
 * @author Laki
 * @param <T>
 */
public class GenericEditor<T extends AbstractEntity> {

    @Inject
    private PropertyConduitSource conduit;

    @Inject
    private GenericDao genericDao;

    @Property
    private T bean;

    @Property
    private T row;

    @Inject
    private BeanModelSource beanModelSource;

    @Property
    private List<T> listaGenerika;

    @Inject
    private ComponentResources componentResources;

    @SuppressWarnings("unchecked")
    private Class<T> klasa = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    @PageLoaded
    public void pageLoaded() {
        this.listaGenerika = genericDao.loadAllActive(klasa);
    }

    @SuppressWarnings({"unchecked"})
    public void onActivate(Integer beanId) throws Exception {
        this.bean = (beanId != null) ? (T) genericDao.getElementById(beanId, klasa) : klasa.newInstance();
        //this.listaGenerika = genericDao.loadAllActive(klasa);
    }

    public T onPassivate() {
        return bean;
    }

    public BeanModel<T> getFormModel() {
        return beanModelSource.createEditModel(klasa,
                componentResources.getMessages()).exclude("id");
    }

    public BeanModel<T> getGridModel() {
        return beanModelSource.createDisplayModel(klasa,
                componentResources.getMessages()).exclude("id");
    }

    @CommitAfter
    Object onActionFromBrisanje(int id) {
        genericDao.delete(id, klasa);
        return this;
    }

    @CommitAfter
    Object onActionFromEdit(int row) {
        bean = (T) genericDao.getElementById(row, klasa);
        return this;
    }

    @SuppressWarnings("unchecked")
    @CommitAfter
    public Object onSuccess() throws Exception {
        T toAdd = (T) genericDao.saveOrUpdate(bean);
        /*
        Proveravamo da li lista ve? sadrži element
        Ako sadrži onda ga dodaje u listu, u suprotnom, 
        prolazi kroz listu, nalazi element i menja ga novim elementom
         */
        if (!listaGenerika.contains(toAdd)) {
            listaGenerika.add(toAdd);
        } else {
            for (int i = 0; i < listaGenerika.size(); i++) {
                if (listaGenerika.get(i).equals(toAdd)) {
                    listaGenerika.set(i, toAdd);
                }
            }
        }

        bean = (T) klasa.newInstance();
        return this;
    }
}