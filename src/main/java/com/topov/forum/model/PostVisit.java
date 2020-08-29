package com.topov.forum.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "post_views")
@Data
@NoArgsConstructor
public class PostVisit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long visitId;
    @Column(name = "visitor_id")
    private Long visitorId;
    @Column(name = "post_id")
    private Long visitedPostId;

    public PostVisit(Long visitorId, Long visitedPostId) {
        this.visitorId = visitorId;
        this.visitedPostId = visitedPostId;
    }
}
