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
@ToString(onlyExplicitlyIncluded = true)
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "post_id_seq")
    private Long postId;
    @Column(unique = true, nullable = false)
    private String title;
    @Column(nullable = false, length = 2500)
    private String text;

    @ManyToOne(
        fetch = FetchType.LAZY,
        optional = false
    )
    @JoinColumn(name = "creator_id")
    private ForumUser creator;

    @OneToMany(
        mappedBy = "post",
        orphanRemoval = true
    )
    @LazyCollection(LazyCollectionOption.EXTRA)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "visitedPostId")
    @LazyCollection(LazyCollectionOption.EXTRA)
    private List<PostVisit> visits = new ArrayList<>();

    public void addComment(Comment comment) {
        this.comments.add(comment);
        comment.setPost(this);
    }

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

}
