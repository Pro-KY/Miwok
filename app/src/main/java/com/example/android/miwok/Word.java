package com.example.android.miwok;

/**
 *  The class represents one vocabulary word in the ListView
 */

public class Word {
    // English translation for the word
    private String mEnglishTranslation;

    // Miwok translation for the word
    private String mMiwokTranslation;

    // Corresponding image to the word
    private int mImageResourceId;

    // Corresponding audio file to the word
    private int mAudioResourceId;


    // default constructor, creates a new Word object.
    public Word(String englishTranslation, String miwokTranslation) {
        mEnglishTranslation = englishTranslation;
        mMiwokTranslation = miwokTranslation;
    }

    public Word(String englishTranslation, String miwokTranslation, int imageId, int audioFileId) {
        mEnglishTranslation = englishTranslation;
        mMiwokTranslation = miwokTranslation;
        mImageResourceId = imageId;
        mAudioResourceId = audioFileId;
    }


    public String getEnglishTranslation() {
        return mEnglishTranslation;
    }

    public String getMiwokTranslation() {
        return mMiwokTranslation;
    }

    public int getImageResourceId() {
        return mImageResourceId;
    }

    public int getAudioResourceId() {
        return mAudioResourceId;
    }


}
