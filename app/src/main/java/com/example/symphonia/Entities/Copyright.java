package com.example.symphonia.Entities;

/**
 * general object to store the Copyrights data
 * of the albums
 *
 * @author islamahmed1092
 * @version 1.0
 */
public class Copyright {
    /**
     * description text of the copyright
     */
     private String mCopyrightText;
    /**
     * type of the copyright
     * it can be C or P
     */
     private String mType;

    /**
     * constructor of the copyright object
     *
     * @param mCopyrightText copyright description
     * @param mType type of the copyright
     */
    public Copyright(String mCopyrightText, String mType) {
        this.mCopyrightText = mCopyrightText;
        this.mType = mType;
    }

    /**
     * @return copyright text
     */
    public String getCopyrightText() {
        return mCopyrightText;
    }

    /**
     * @param mCopyrightText copyright text
     */
    public void setCopyrightText(String mCopyrightText) {
        this.mCopyrightText = mCopyrightText;
    }

    /**
     * @return copyright type
     */
    public String getType() {
        return mType;
    }

    /**
     * @param mType copyright type
     */
    public void setType(String mType) {
        this.mType = mType;
    }
}
