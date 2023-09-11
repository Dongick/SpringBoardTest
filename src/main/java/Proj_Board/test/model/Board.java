package Proj_Board.test.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    @Column(name = "detail", nullable = false, length = 100)
    // 게시물 내용
    private String detail;
    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    // 게시물 생성일
    private LocalDateTime createdAt;
    @Column(name = "updated_at", nullable = false)
    @UpdateTimestamp
    // 게시물 수정일
    private LocalDateTime updatedAt;

    // 게시물 생성자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    // 해당 게시물에 달린 댓글
    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    // 수정 삭제 버튼 유무
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

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDetail() {
        return detail;
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

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
