package android.bemodel.com.bemodel.db;

import java.lang.ref.SoftReference;

/**
 * Created by Administrator on 2017.07.28.
 */

public class CommentInfo {

    private int id;
    private String text;
    private UserInfo reviewer;
    private String createdAt;
    private ModelCircleInfo status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public UserInfo getReviewer() {
        return reviewer;
    }

    public void setReviewer(UserInfo reviewer) {
        this.reviewer = reviewer;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public ModelCircleInfo getStatus() {
        return status;
    }

    public void setStatus(ModelCircleInfo status) {
        this.status = status;
    }
}
