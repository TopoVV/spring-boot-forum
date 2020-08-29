package com.topov.forum.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "posts")
@SequenceGenerator(name = "post_id_seq", allocationSize = 1)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "post_id_seq")
    @Column(name = "post_id")
    private Long postId;
    @Column(name = "title", nullable = false, unique = true)
    private String title;
    @Column(name = "text", nullable = false, length = 2500)
    private String text;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "creator_id")
    @ToString.Exclude
    private ForumUser creator;

    @OneToMany(
        cascade = CascadeType.ALL,
        fetch = FetchType.LAZY,
        mappedBy = "post",
        orphanRemoval = true
    )
    @ToString.Exclude
    private List<Comment> comments = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "post_id")
    @LazyCollection(LazyCollectionOption.EXTRA)
    private List<PostVisit> visits = new ArrayList<>();

    public void disable() {
        this.status = Status.INACTIVE;
    }

    public boolean isActive() { return this.status.equals(Status.ACTIVE); }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Post post = (Post) o;
        return title.equals(post.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title);
    }

    public void addComment(Comment comment) {
        this.comments.add(comment);
        comment.setPost(this);
    }
}
