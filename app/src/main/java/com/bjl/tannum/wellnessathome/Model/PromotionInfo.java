package com.bjl.tannum.wellnessathome.Model;

/**
 * Created by tannum on 1/19/2017 AD.
 */

public class PromotionInfo {

    int thumbnailId;
    String header;
    String content;

    public PromotionInfo( int thumbnailId,String header,String content) {
        this.content = content;
        this.header = header;
        this.thumbnailId = thumbnailId;
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
