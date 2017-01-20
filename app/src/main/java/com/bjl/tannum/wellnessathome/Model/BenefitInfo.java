package com.bjl.tannum.wellnessathome.Model;

/**
 * Created by tannum on 1/15/2017 AD.
 */

public class BenefitInfo {
    int thumbnailId;
    String header;
    String content;
    int benefit_total;
    int benefit_rest;




    public BenefitInfo( String content, String header, int thumbnailId, int benefit_total, int benefit_rest) {
        this.thumbnailId = thumbnailId;
        this.header = header;
        this.content = content;
        this.benefit_total = benefit_total;
        this.benefit_rest = benefit_rest;
    }

    public int getBenefit_total() {
        return benefit_total;
    }

    public void setBenefit_total(int benefit_total) {
        this.benefit_total = benefit_total;
    }

    public int getBenefit_rest() {
        return benefit_rest;
    }

    public void setBenefit_rest(int benefit_rest) {
        this.benefit_rest = benefit_rest;
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
