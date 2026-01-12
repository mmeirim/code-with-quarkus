package org.mmeirim.fiap.taskList.domain;

import java.time.LocalDateTime;

public class Task {
    private Long id;
    private String description;
    private String status;
    private LocalDateTime createdAt;

    public Task(String description) {
        this.description = description;
        this.status = "PENDENTE";
        this.createdAt = LocalDateTime.now();
    }

    public Task(Long id, String description, String status, LocalDateTime createdAt) {
        this.id = id;
        this.description = description;
        this.status = status;
        this.createdAt = createdAt;
    }

    public void close() {
        this.status = "CONCLUIDA";
    }

    public void reopen() {
        this.status = "PENDENTE";
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}


