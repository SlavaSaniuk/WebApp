package by.bsac.models;

import javax.persistence.*;
import java.util.Set;

/**
 *
 */
@Entity
@Table(name = "friends_relationships")
public class Friends {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "relationship_id")
    private long relationship_id;

    @ManyToMany(mappedBy = "friends")
    private Set<User> friend_one_id;

    @ManyToMany(mappedBy = "friends")
    private Set<User> friend_two_id;


    /*
        Constructors
     */
    public Friends(){}

    /*
        Getters and setters
     */

    public long getRelationshipId() {
        return relationship_id;
    }

    public void setRelationshipId(long relationship_id) {
        this.relationship_id = relationship_id;
    }

    public Set<User> getFriendOneId() {
        return friend_one_id;
    }

    public void setFriendOneId(Set<User> friend_one_id) {
        this.friend_one_id = friend_one_id;
    }

    public Set<User> getFriendTwoId() {
        return friend_two_id;
    }

    public void setFriendTwoId(Set<User> friend_two_id) {
        this.friend_two_id = friend_two_id;
    }
}
