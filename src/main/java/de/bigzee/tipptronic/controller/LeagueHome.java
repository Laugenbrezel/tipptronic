/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bigzee.tipptronic.controller;

import de.bigzee.tipptronic.model.League;
import javax.annotation.PostConstruct;
import javax.ejb.Stateful;
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
@Stateful
@Model
public class LeagueHome {

   @Inject
   @Category("tipptronic")
   private Logger log;

   @Inject
   private EntityManager em;

   @Inject
   private Event<League> leagueEventSrc;

   private League instance;

   @Produces
   @Named
   public League getNewLeague() {
      return instance;
   }

   public void save() throws Exception {
      log.info("Saving " + instance.getName());
      em.persist(instance);
      leagueEventSrc.fire(instance);
      initNewInstance();
   }

   @PostConstruct
   public void initNewInstance() {
      instance = new League();
   }
}
