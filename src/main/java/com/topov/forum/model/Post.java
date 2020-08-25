package com.topov.forum.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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
    @Enumerated(EnumType.STRING)
    private Status status;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id")
    private ForumUser creator;

    public void disable() {
        this.status = Status.INACTIVE;
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

    public enum Status {
        ACTIVE,
        INACTIVE
    }
}
