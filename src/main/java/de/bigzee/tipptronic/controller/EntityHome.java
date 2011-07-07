/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bigzee.tipptronic.controller;

import de.bigzee.tipptronic.model.BaseEntity;
import java.lang.reflect.ParameterizedType;
import javax.annotation.PostConstruct;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import org.jboss.logging.Logger;
import org.jboss.seam.solder.logging.Category;

/**
 *
 * @author lutz
 */
@Model
public abstract class EntityHome<T extends BaseEntity> {

    @Inject
    @Category("tipptronic")
    protected Logger log;

    @Inject
    protected EntityManager em;

    @Inject
    protected Event<T> instanceEventSrc;

    protected T instance;

    @Produces
    @Named
    public T getInstance() {
        return instance;
    }

    public void save() {
        log.info("Saving Id: " + instance.getId());
        em.persist(instance);
        em.flush();
        instanceEventSrc.fire(instance);
        initNewInstance();
    }

    @PostConstruct
    public void initNewInstance() {
        ParameterizedType superClass = (ParameterizedType) getClass().
                getGenericSuperclass();
        Class<T> type = (Class<T>) superClass.getActualTypeArguments()[0];
        try {
            instance = type.newInstance();
        } catch (Exception e) {
            // Oops, no default constructor
            throw new RuntimeException(e);
        }
    }
}
