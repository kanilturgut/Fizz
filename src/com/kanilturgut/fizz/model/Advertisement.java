package com.kanilturgut.fizz.model;

import org.json.JSONObject;

/**
 * Author   : kanilturgut
 * Date     : 24/06/14
 * Time     : 17:32
 */
public class Advertisement extends SocialNetwork{

    String id;
    String horizontalImageUrl;
    String verticalImageUrl;
    int initialDisplayCount;
    int remainingDisplayCount;
    int type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHorizontalImageUrl() {
        return horizontalImageUrl;
    }

    public void setHorizontalImageUrl(String horizontalImageUrl) {
        this.horizontalImageUrl = horizontalImageUrl;
    }

    public String getVerticalImageUrl() {
        return verticalImageUrl;
    }

    public void setVerticalImageUrl(String verticalImageUrl) {
        this.verticalImageUrl = verticalImageUrl;
    }

    public int getInitialDisplayCount() {
        return initialDisplayCount;
    }

    public void setInitialDisplayCount(int initialDisplayCount) {
        this.initialDisplayCount = initialDisplayCount;
    }

    public int getRemainingDisplayCount() {
        return remainingDisplayCount;
    }

    public void setRemainingDisplayCount(int remainingDisplayCount) {
        this.remainingDisplayCount = remainingDisplayCount;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public static Advertisement fromJSON(JSONObject jsonObject) {
        Advertisement advertisement = new Advertisement();

        advertisement.setId(jsonObject.optString("_id"));
        advertisement.setHorizontalImageUrl(jsonObject.optString("horizontalImageUrl"));
        advertisement.setVerticalImageUrl(jsonObject.optString("verticalImageUrl"));
        advertisement.setRemainingDisplayCount(jsonObject.optInt("remainingDisplayCount"));
        advertisement.setInitialDisplayCount(jsonObject.optInt("initialDisplayCount"));
        advertisement.setType(SocialNetwork.TYPE_ADVERTISEMENT);

        return advertisement;
    }

}
