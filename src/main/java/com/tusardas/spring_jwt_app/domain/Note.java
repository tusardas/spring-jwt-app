package com.tusardas.spring_jwt_app.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Note {
    @Id
    Long id;
    String content;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
}
