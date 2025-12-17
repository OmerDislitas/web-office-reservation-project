package com.officerental.backend.model.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "office_id", nullable = false)
    private Office office;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private int rating; // 1-5

    @Column(length = 1000)
    private String comment;

    private LocalDateTime createdAt = LocalDateTime.now();

    // getters & setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Office getOffice() { return office; }
    public void setOffice(Office office) { this.office = office; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
