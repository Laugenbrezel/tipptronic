package de.bigzee.tipptronic.data;

import de.bigzee.tipptronic.model.League;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@RequestScoped
public class LeagueListProducer {
   @Inject
   private EntityManager em;

   private List<League> leagues;

   // @Named provides access the return value via the EL variable name "members" in the UI (e.g.,
   // Facelets or JSP view)
   @Produces
   @Named
   public List<League> getLeagues() {
      return leagues;
   }

   public void onLeagueListChanged(@Observes(notifyObserver = Reception.IF_EXISTS) final League league) {
      retrieveAllLeaguesOrderedByName();
   }

   @PostConstruct
   public void retrieveAllLeaguesOrderedByName() {
      CriteriaBuilder cb = em.getCriteriaBuilder();
      CriteriaQuery<League> criteria = cb.createQuery(League.class);
      Root<League> league = criteria.from(League.class);
      // Swap criteria statements if you would like to try out type-safe criteria queries, a new
      // feature in JPA 2.0
      // criteria.select(member).orderBy(cb.asc(member.get(Member_.name)));
      criteria.select(league).orderBy(cb.asc(league.get("name")));
      leagues = em.createQuery(criteria).getResultList();
   }
}
