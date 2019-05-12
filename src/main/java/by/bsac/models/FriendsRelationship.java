package by.bsac.models;

import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Proxy(lazy = false)
@Table(name = "friends_relationships")
public class FriendsRelationship implements Serializable {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "relationship_id")
    @NotNull
    private long relationship_id;

    @ManyToOne
    @JoinColumn(name = "friend_1_id")
    @NotNull
    private User friend_master;

    @ManyToOne
    @JoinColumn(name = "friend_2_id")
    @NotNull
    private User friend_slave;

    /*
        Friends relationship status.
        May be:
        0 - master sent request to slave user;
        1 - slave user accept request;
        2 - slave user don't accept request or delete master user from friends set;
     */
    @Column(name = "rel_status")
    @NotNull
    private int rel_status;



    // Constructors
    public FriendsRelationship() {
        //Default constructor
    }

    public FriendsRelationship(User common_user, User adding_user) {

        //Mapping
        this.friend_master = common_user;
        this.friend_slave = adding_user;
        this.rel_status = 0;

    }

    public long getRelationshipId() {
        return relationship_id;
    }

    public void setRelationshipId(long relationship_id) {
        this.relationship_id = relationship_id;
    }

    //Methods
    //Getters and setters
    public User getFriendshipMaster() {
        return friend_master;
    }

    public User getFriendshipSlave() {
        return friend_slave;
    }

    public int getRelStatus() {
        return rel_status;
    }

    public void setRelStatus(int rel_status) {
        this.rel_status = rel_status;
    }

    //Override java.lang.Object
    @Override
    public String toString() {
        return relationship_id + ": " +friend_master.getUserEmail() + " -> " +friend_slave.getUserEmail();
    }


}
