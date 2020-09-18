package com.topov.forum.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "post_visit")
@Data
@NoArgsConstructor
public class PostVisit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long visitId;
    private Long visitorId;
    private Long visitedPostId;

    public PostVisit(Long visitorId, Long visitedPostId) {
        this.visitorId = visitorId;
        this.visitedPostId = visitedPostId;
    }
}
