package com.topov.forum.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "post_views")
@Data
@NoArgsConstructor
public class ViewCounter {
    @Id
    private Long viewCounterId;
    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;
    @Column(name = "views_amount")
    private int count;

    public void increment() {
        this.count++;
    }
}
