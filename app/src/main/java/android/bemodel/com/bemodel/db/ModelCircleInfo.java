package android.bemodel.com.bemodel.db;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobGeoPoint;

/**
 * Created by Administrator on 2017.07.25.
 */

public class ModelCircleInfo extends BmobObject {

    private Integer id;                 //帖文ID
    private String text;            //帖文信息内容
    private String thumbnailPic;    //缩略图片地址
    private String bmiddlePic;      //中等图片地址
    private String originalPic;     //原始图片地址
    private Integer commentsCount;  //评论数
    private UserInfo user;          //帖文作者的用户信息
    private BmobGeoPoint geo;             //地理信息
//    private String address;         //地址信息
//
//    public String getAddress() {
//        return address;
//    }
//
//    public void setAddress(String address) {
//        this.address = address;
//    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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

    public Integer getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(Integer commentsCount) {
        this.commentsCount = commentsCount;
    }

    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }

    public BmobGeoPoint getGeo() {
        return geo;
    }

    public void setGeo(BmobGeoPoint geo) {
        this.geo = geo;
    }
}
