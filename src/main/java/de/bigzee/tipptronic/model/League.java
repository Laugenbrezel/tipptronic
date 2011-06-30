/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bigzee.tipptronic.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author lutz
 */
@Entity
@XmlRootElement
@Table(uniqueConstraints =
@UniqueConstraint(columnNames = "name"))
public class League implements Serializable {

    /** Default value included to remove warning. Remove or modify at will. **/
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue
    private Long id;
    
    @NotNull
    @Size(min = 1, max = 40)
    private String name;
    
    @OneToMany(mappedBy="league", cascade= CascadeType.ALL)
    private List<Season> seasons = new ArrayList<Season>();
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Season> getSeasons() {
        return seasons;
    }

    public void setSeasons(List<Season> seasons) {
        this.seasons = seasons;
    }
    
    public void addSeason(Season season) {
        season.setLeague(this);
        this.seasons.add(season);
    }
}
