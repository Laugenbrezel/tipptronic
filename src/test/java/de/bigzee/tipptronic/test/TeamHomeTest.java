package de.bigzee.tipptronic.test;

import de.bigzee.tipptronic.controller.EntityHome;
import static org.junit.Assert.assertNotNull;

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

import de.bigzee.tipptronic.controller.TeamHome;
import de.bigzee.tipptronic.model.BaseEntity;
import de.bigzee.tipptronic.model.Team;
import de.bigzee.tipptronic.util.Resources;

@RunWith(Arquillian.class)
public class TeamHomeTest {

    @Deployment
    public static Archive<?> createTestArchive() {
        return ShrinkWrap.create(WebArchive.class, "test.war").
                addClasses(Team.class, TeamHome.class, EntityHome.class, BaseEntity.class, Resources.class).
                addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml").
                addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Inject
    TeamHome teamHome;

    @Inject
    Logger log;

    @Test
    public void testRegister() throws Exception {
        Team team = teamHome.getInstance();
        team.setName("My Team");
        teamHome.save();
        assertNotNull(team.getId());
        log.info(team.getName() + " was persisted with id " + team.getId());
    }

    @Produces
    public Logger produceLog(InjectionPoint injectionPoint) {
        return Logger.getLogger(injectionPoint.getMember().
                getDeclaringClass());
    }
}
