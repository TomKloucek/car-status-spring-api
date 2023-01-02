package cz.cvut.fel.ear.carstatus.model;

import javax.persistence.*;
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
