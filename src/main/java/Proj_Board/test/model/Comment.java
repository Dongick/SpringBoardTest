package Proj_Board.test.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Comment {

    // 댓글 id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 댓글 내용
    @Column(nullable = false, length = 200)
    private String content;

    // 댓글 생성자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    // 댓글이 달린 게시글
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "boardId", nullable = false)
    private Board board;

    // 댓글 생성일
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // 해당 댓글에 달린 대댓글
    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChildrenComment> childrenComments = new ArrayList<>();

    // 수정 삭제 버튼 유무
    @Transient
    private boolean canEditAndDelete;

    public boolean isCanEditAndDelete() {
        return canEditAndDelete;
    }

    public void setCanEditAndDelete(boolean canEditAndDelete) {
        this.canEditAndDelete = canEditAndDelete;
    }

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        if(this.board != board){
            this.board = board;
            if (board != null) {
                board.getComments().add(this);
            }
        }
    }

    public List<ChildrenComment> getChildrenComments() {
        return childrenComments;
    }

    public void setChildrenComments(List<ChildrenComment> childrenComments) {
        this.childrenComments = childrenComments;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
