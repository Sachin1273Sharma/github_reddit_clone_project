package io.mountblue.reddit.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class SubReddit {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long subRedditId;

    private String name;


    private String description;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
    private LocalDateTime createdAt;
    private String avatar;

    @OneToMany(mappedBy = "subReddit", cascade = CascadeType.ALL)
    private List<Post> posts = new ArrayList<>();

    public Long getSubRedditId() {
        return subRedditId;
    }

    public void setSubRedditId(Long subRedditId) {
        this.subRedditId = subRedditId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }
}

