package com.bjl.tannum.wellnessathome.Model;

/**
 * Created by tannum on 1/15/2017 AD.
 */

public class BenefitInfo {
    int thumbnailId;
    String header;
    String content;

    public BenefitInfo(String content, String header, int thumbnailId) {
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
