package cz.cvut.fel.ear.carstatus.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Date;

@Entity
@Getter
@Setter
@DiscriminatorValue("ADMIN")
public class Admin extends User {
    @Basic(optional = false)
    @Column(nullable = true)
    private Date expires;


    @Override
    public String toString() {
        return "Admin{" +
                "expires=" + expires +
                '}';
    }
}
