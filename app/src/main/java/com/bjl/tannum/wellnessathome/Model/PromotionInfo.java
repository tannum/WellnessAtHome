package com.bjl.tannum.wellnessathome.Model;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by tannum on 1/19/2017 AD.
 */


public class PromotionInfo {

    public int thumbnailId;
    public String header;
    public String content;
    public String imageUrl;

    public PromotionInfo() {
    }

    public PromotionInfo(int thumbnailId, String header, String content) {
        this.content = content;
        this.header = header;
        this.thumbnailId = thumbnailId;
    }

    public PromotionInfo(String header,String content,  String imageUrl) {
        this.content = content;
        this.header = header;
        this.imageUrl = imageUrl;
    }



    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public int getThumbnailId() {
        return thumbnailId;
    }

    public void setThumbnailId(int thumbnailId) {
        this.thumbnailId = thumbnailId;
    }
}
