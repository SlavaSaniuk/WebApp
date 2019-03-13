package by.bsac.models;

import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Proxy(lazy = false)
@Table(name = "friends_relationships")
public class FriendsRelationship implements Serializable {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "relationship_id")
    private long relationship_id;

    @ManyToOne
    @JoinColumn(name = "friend_1_id")
    private User friend_master;

    @ManyToOne
    @JoinColumn(name = "friend_2_id")
    private User friend_slave;

    // Constructors
    public FriendsRelationship() {
        //Default constructor
    }

    public FriendsRelationship(User common_user, User adding_user) {

        //Mapping
        this.friend_master = common_user;
        this.friend_slave = adding_user;

    }

    //Methods
    //Getters and setters
    public User getFriendshipMaster() {
        return friend_master;
    }

    public User getFriendshipSlave() {
        return friend_slave;
    }

    //Override java.lang.Object
    @Override
    public String toString() {
        return relationship_id + ": " +friend_master.getUserEmail() + " -> " +friend_slave.getUserEmail();
    }


}
