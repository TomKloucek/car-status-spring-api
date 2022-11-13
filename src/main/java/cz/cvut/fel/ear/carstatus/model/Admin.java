package cz.cvut.fel.ear.carstatus.model;

import javax.persistence.*;
import java.sql.Date;

@Entity
public class Admin extends AbstractEntity{
    @Basic(optional = false)
    @Column(nullable = true)
    private Date expires;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private User user;

    public Date getExpires() {
        return expires;
    }

    public void setExpires(Date expires) {
        this.expires = expires;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
