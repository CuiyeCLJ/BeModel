package android.bemodel.com.bemodel.db;

/**
 * Created by Administrator on 2017.07.25.
 */

public class ModelCircleInfo {

    int id;     //模特圈ID
    String createdAt;  //模特圈创建时间
    String text;        //模特圈信息内容
    int commentsCount; //评论数
    String imageContext;
    boolean haveImg;
    String userName;
    String userIcon;

    public String getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(String userIcon) {
        this.userIcon = userIcon;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isHaveImg() {
        return haveImg;
    }

    public void setHaveImg(boolean haveImg) {
        this.haveImg = haveImg;
    }

    public String getImageContext() {
        return imageContext;
    }

    public void setImageContext(String imageContext) {
        this.imageContext = imageContext;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(int commentsCount) {
        this.commentsCount = commentsCount;
    }
}
