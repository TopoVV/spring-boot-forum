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
    @Column(name = "title", unique = true)
    private String title;

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
