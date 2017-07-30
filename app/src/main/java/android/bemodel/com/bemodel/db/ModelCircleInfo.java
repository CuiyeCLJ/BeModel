package android.bemodel.com.bemodel.db;

/**
 * Created by Administrator on 2017.07.25.
 */

public class ModelCircleInfo {

    private int id;                 //帖文ID
    private String text;            //帖文信息内容
    private String createdAt;       //帖文创建时间
    private String thumbnailPic;    //缩略图片地址
    private String bmiddlePic;      //中等图片地址
    private String originalPic;     //原始图片地址
    private int commentsCount;      //评论数
    private UserInfo user;          //帖文作者的用户信息
    private Object geo;             //地理信息

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

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getThumbnailPic() {
        return thumbnailPic;
    }

    public void setThumbnailPic(String thumbnailPic) {
        this.thumbnailPic = thumbnailPic;
    }

    public String getBmiddlePic() {
        return bmiddlePic;
    }

    public void setBmiddlePic(String bmiddlePic) {
        this.bmiddlePic = bmiddlePic;
    }

    public String getOriginalPic() {
        return originalPic;
    }

    public void setOriginalPic(String originalPic) {
        this.originalPic = originalPic;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(int commentsCount) {
        this.commentsCount = commentsCount;
    }

    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }

    public Object getGeo() {
        return geo;
    }

    public void setGeo(Object geo) {
        this.geo = geo;
    }
}
