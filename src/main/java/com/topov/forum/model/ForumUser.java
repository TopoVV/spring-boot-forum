package com.topov.forum.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "forum_users")
@SequenceGenerator(name = "user_id_seq", allocationSize = 1)
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
public class ForumUser {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_seq")
    private Long userId;
    @Column(unique = true)
    private String username;
    private String password;
    @Column(unique = true)
    private String email;
    private Boolean enabled;

    @ManyToMany(
        cascade = CascadeType.MERGE,
        fetch = FetchType.LAZY
    )
    @JoinTable(
        name = "user_role",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    @OneToMany(
        cascade = CascadeType.ALL,
        fetch = FetchType.LAZY,
        orphanRemoval = true,
        mappedBy = "creator"
    )
    private Set<Post> posts = new HashSet<>();

    @OneToMany(
        cascade = CascadeType.ALL,
        fetch = FetchType.LAZY,
        mappedBy = "creator",
        orphanRemoval = true
    )
    private List<Comment> comments = new ArrayList<>();

    public ForumUser(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public void addRole(Role role) {
        this.roles.add(role);
        role.addUser(this);
    }

    public void addPost(Post newPost) {
        this.posts.add(newPost);
        newPost.setCreator(this);
    }

    public void addComment(Comment comment) {
        this.comments.add(comment);
        comment.setCreator(this);
    }

    public void enable() { this.enabled = true; }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ForumUser forumUser = (ForumUser) o;
        return username.equals(forumUser.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }
}
