package com.topov.forum.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "comments")
@SequenceGenerator(name = "comment_id_seq", allocationSize = 1)
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comment_id_seq")
    private Long commentId;
    private String text;
    @Enumerated(EnumType.STRING)
    private Status status;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "post_id")
    private Post post;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "creator_id")
    private ForumUser creator;

    public boolean isActive() { return this.status.equals(Status.ACTIVE); }

    public void disable() {
        this.status = Status.INACTIVE;
    }

}
