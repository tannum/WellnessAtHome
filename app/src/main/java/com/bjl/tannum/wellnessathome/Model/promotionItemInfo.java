package com.bjl.tannum.wellnessathome.Model;

/**
 * Created by tannum on 1/3/2017 AD.
 */

public class promotionItemInfo {

    private String imgPromotionUrl;
    private String txtPromotionHeader;
    private String txtPromotionHeaderContent;
    private String txtPromotionContent;
    private int thumnailId;

    public int getThumnailId() {
        return thumnailId;
    }

    public void setThumnailId(int thumnailId) {
        this.thumnailId = thumnailId;
    }

    public String getImgPromotionUrl() {
        return imgPromotionUrl;
    }

    public void setImgPromotionUrl(String imgPromotionUrl) {
        this.imgPromotionUrl = imgPromotionUrl;
    }

    public String getTxtPromotionContent() {
        return txtPromotionContent;
    }

    public void setTxtPromotionContent(String txtPromotionContent) {
        this.txtPromotionContent = txtPromotionContent;
    }

    public String getTxtPromotionHeader() {
        return txtPromotionHeader;
    }

    public void setTxtPromotionHeader(String txtPromotionHeader) {
        this.txtPromotionHeader = txtPromotionHeader;
    }

    public String getTxtPromotionHeaderContent() {
        return txtPromotionHeaderContent;
    }

    public void setTxtPromotionHeaderContent(String txtPromotionHeaderContent) {
        this.txtPromotionHeaderContent = txtPromotionHeaderContent;
    }
}
