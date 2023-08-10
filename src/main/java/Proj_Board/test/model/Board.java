package Proj_Board.test.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable=false)
    // 게시물 id
    private Long id;
    @Column(name = "title", nullable = false, length = 20)
    // 게시물  제목
    private String title;
    @Column(name = "comment", nullable = false, length = 100)
    // 게시물 내용
    private String comment;
    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    // 게시물 생성일
    private LocalDateTime createdAt;
    @Column(name = "updated_at", nullable = false)
    @UpdateTimestamp
    // 게시물 수정일
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @Transient
    private boolean canEditAndDelete;

    public boolean isCanEditAndDelete() {
        return canEditAndDelete;
    }

    public void setCanEditAndDelete(boolean canEditAndDelete) {
        this.canEditAndDelete = canEditAndDelete;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getComment() {
        return comment;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public User getUser() {
        return user;
    }
}
