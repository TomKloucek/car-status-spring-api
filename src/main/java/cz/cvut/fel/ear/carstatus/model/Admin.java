package cz.cvut.fel.ear.carstatus.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Date;

@Entity
@DiscriminatorValue("ADMIN")
public class Admin extends User {
    @Basic(optional = false)
    @Column(nullable = true)
    private Date expires;

    public Date getExpires() {
        return expires;
    }

    public void setExpires(Date expires) {
        this.expires = expires;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "expires=" + expires +
                '}';
    }
}
