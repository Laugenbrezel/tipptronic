/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bigzee.tipptronic.test;

import static org.junit.Assert.*;

import de.bigzee.tipptronic.controller.LeagueHome;
import de.bigzee.tipptronic.model.League;
import de.bigzee.tipptronic.model.Season;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.logging.Logger;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import de.bigzee.tipptronic.util.Resources;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 *
 * @author lutz
 */
@RunWith(Arquillian.class)
public class LeagueHomeTest {

    @Deployment
    public static Archive<?> createTestArchive() {
        return ShrinkWrap.create(WebArchive.class, "test.war").
                addClasses(League.class, Season.class, LeagueHome.class, Resources.class).
                addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml").
                addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Inject
    LeagueHome leagueHome;

    @Inject
    Logger log;

    @Test
    public void testSave() throws Exception {
        League newLeague = leagueHome.getNewLeague();
        newLeague.setName("Super League");
        leagueHome.save();
        assertNotNull(newLeague.getId());
        log.info(newLeague.getName() + " was persisted with id " + newLeague.
                getId());
    }

    @Test
    public void testSaveWithSeason() throws Exception {
        League newLeague = leagueHome.getNewLeague();
        newLeague.setName("Super League 2");
        
        Season newSeason = new Season();
        Calendar cal = GregorianCalendar.getInstance();        
        newSeason.setStartDate(cal.getTime());
        cal.roll(Calendar.YEAR, true);
        newSeason.setEndDate(cal.getTime());
        newLeague.addSeason(newSeason);
        
        leagueHome.save();
        assertNotNull(newLeague.getId());
        log.info(newLeague.getName() + " was persisted with id " + newLeague.
                getId());
        assertNotNull(newLeague.getSeasons());
        assertTrue(newLeague.getSeasons().size() == 1);
        for (Season persSeason : newLeague.getSeasons()) {
            assertNotNull(persSeason.getLeague());
            assertSame(newLeague, persSeason.getLeague());
            assertTrue(persSeason.getStartDate().before(persSeason.getEndDate()));
        }
    }

    @Produces
    public Logger produceLog(InjectionPoint injectionPoint) {
        return Logger.getLogger(injectionPoint.getMember().
                getDeclaringClass());
    }
}
