package com.example.symphonia.Activities.User_Management.Notifications;

/**
 * Class that contain notification's data
 *
 * @author Hossam Alaa
 * @version 1.0
 * @since 10-06-2020
 */
public class NotificationItem {

    /**
     * holds image id (if mock service)
     */
    private int mImageResource;
    /**
     * holds title of notification
     */
    private String mText1;
    /**
     * holds body of notification
     */
    private String mText2;
    /**
     * holds the date of notification
     */
    private String mText3 = "";
    /**
     * holds id to target to be sent to
     */
    private String mSenderID ="";
    /**
     * holds notification image url
     */
    private String imageUrl = "";

    /**
     * getter for image url
     * @return return image url
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * setter for image url
     * @param imageUrl holds image url
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    /**
     * constructor for notification item
     * @param imageResource holds image resource
     * @param text1 holds title
     * @param text2 holds body
     */
    public NotificationItem(int imageResource, String text1, String text2) {
        mImageResource = imageResource;
        mText1 = text1;
        mText2 = text2;
    }

    /**
     * constructor for notification item
     * @param mImageResource holds image id
     * @param mText1 holds title text
     * @param mText2 holds body text
     * @param mText3 holds date text
     */
    public NotificationItem(int mImageResource, String mText1, String mText2, String mText3) {
        this.mImageResource = mImageResource;
        this.mText1 = mText1;
        this.mText2 = mText2;
        this.mText3 = mText3;
    }

    /**
     * setter for target id of notification
     * @param senderID holds target id of notification
     */
    public void setSenderID(String senderID){
        this.mSenderID = senderID;
    }

    /**
     * getter for image id of notification
     * @return returns image id of notification
     */
    public int getImageResource() {
        return mImageResource;
    }

    /**
     * getter for title text of notification
     * @return return title text of notification
     */
    public String getText1() {
        return mText1;
    }

    /**
     * getter for body text of notification
     * @return return body text of notification
     */
    public String getText2() {
        return mText2;
    }

    /**
     * getter for target id of notification
     * @return return target id of notification
     */
    public String getSenderID(){
        return mSenderID;
    }

    /**
     * getter for date of notification
     * @return return date of of notification
     */
    public String getText3() {
        return mText3;
    }

    /**
     * setter for date of notification
     * @param mText3 holds date of notification
     */
    public void setText3(String mText3) {
        this.mText3 = mText3;
    }
}
