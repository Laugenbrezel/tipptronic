/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bigzee.tipptronic.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 *
 * @author lutz
 */
@Entity
public class Season implements Serializable {

    /** Default value included to remove warning. Remove or modify at will. **/
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue
    private Long id;
    
    @Temporal(TemporalType.DATE)
    @NotNull
    private Date startDate;
    
    @Temporal(TemporalType.DATE)
    @NotNull
    private Date endDate;
    
    @ManyToOne(optional=false)
    @JoinColumn(name="league_fk", nullable=false, updatable=false)
    private League league;

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the startDate
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * @return the endDate
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * @param endDate the endDate to set
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public League getLeague() {
        return league;
    }

    public void setLeague(League league) {
        this.league = league;
    }
    
    
}
