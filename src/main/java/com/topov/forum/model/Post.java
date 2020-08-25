package com.topov.forum.model;

import com.topov.forum.model.converter.AtomicIntegerConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

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
    @Column(name = "views_amount")
    @Convert(converter = AtomicIntegerConverter.class)
    private AtomicInteger views = new AtomicInteger(0);
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "creator_id")
    private ForumUser creator;

    @OneToMany(
        cascade = CascadeType.ALL,
        fetch = FetchType.LAZY,
        mappedBy = "post",
        orphanRemoval = true
    )
    private List<Comment> comments = new ArrayList<>();

    public void viewed() {
        this.views.incrementAndGet();
    }

    public void disable() {
        this.status = Status.INACTIVE;
    }

    public BigInteger getViews() {
        return BigInteger.valueOf(views.get());
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

    public void addComment(Comment comment) {
        this.comments.add(comment);
        comment.setPost(this);
    }
}
