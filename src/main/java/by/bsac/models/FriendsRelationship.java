package by.bsac.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "friends_relationships")
public class FriendsRelationship implements Serializable {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "relationship_id")
    private long relationship_id;

    @ManyToOne
    @JoinColumn(name = "friend_1_id")
    private User friend_1_id;

    @ManyToOne
    @JoinColumn(name = "friend_2_id")
    private User friend_2_id;

    // Constructors
    public FriendsRelationship() {
        //Default constructor
    }

    public FriendsRelationship(User common_user, User adding_user) {

        //Mapping
        this.friend_1_id = common_user;
        this.friend_2_id = adding_user;

    }


}
