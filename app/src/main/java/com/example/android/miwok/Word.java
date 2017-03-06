package com.example.android.miwok;

/**
 *  The class represents one vocabulary word in the ListView
 */

public class Word {
    // English translation for the word
    private int mEnglishTranslation;

    // Miwok translation for the word
    private int mMiwokTranslation;

    // Corresponding image to the word
    private int mImageResourceId;

    // Corresponding audio file to the word
    private int mAudioResourceId;


    // default constructor, creates a new Word object.
    public Word(int englishTranslation, int miwokTranslation, int audioFileId) {
        mEnglishTranslation = englishTranslation;
        mMiwokTranslation = miwokTranslation;
        mAudioResourceId = audioFileId;
    }

    public Word(int englishTranslation, int miwokTranslation, int imageId, int audioFileId) {
        mEnglishTranslation = englishTranslation;
        mMiwokTranslation = miwokTranslation;
        mImageResourceId = imageId;
        mAudioResourceId = audioFileId;
    }


    public int getEnglishTranslationResource() {
        return mEnglishTranslation;
    }

    public int getMiwokTranslationResource() {
        return mMiwokTranslation;
    }

    public int getImageResourceId() {
        return mImageResourceId;
    }

    public int getAudioResourceId() {
        return mAudioResourceId;
    }


}
