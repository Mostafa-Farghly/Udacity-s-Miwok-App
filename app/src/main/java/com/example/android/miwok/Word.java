package com.example.android.miwok;

/**
 * {@link Word} represent a vocabulary word that the user wants to learn.
 * It contains a default translation and a miwok translation for that word.
 */
public class Word {
    /** Miwok translation for the word */
    private String mMiwokTranslation;
    /** Default translation for the word */
    private String mDefaultTranslation;
    /** Image resource ID for the word */
    private int mImageResourceId = NO_IMAGE_PROVIDED;
    /** Audio resource ID for the word */
    private int mAudioResourceId;

    private static final int NO_IMAGE_PROVIDED = -1;

    /**
     * Create a new Word object.
     * @param defaultTranslation is the word in the language that the user is using
     * @param miwokTranslation is the word in the miwok language
     * @param audioResourceId is the raw resource ID for the audio associated with the word
     */
    public Word(String defaultTranslation, String miwokTranslation, int audioResourceId) {
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
        mAudioResourceId = audioResourceId;
    }

    /**
     * Create a new Word object.
     * @param defaultTranslation is the word in the language that the user is using
     * @param miwokTranslation is the word in the miwok language
     * @param imageResourceId is the drawable resource id for the image associated with the word
     * @param audioResourceId is the raw resource ID for the audio associated with the word
     */
    public Word(String defaultTranslation, String miwokTranslation, int imageResourceId, int audioResourceId) {
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
        mImageResourceId = imageResourceId;
        mAudioResourceId = audioResourceId;
    }

    /**
     * Get the default translation of the word.
     */
    public String getDefaultTranslation() {
        return mDefaultTranslation;
    }

    /**
     * Get the Miwok translation of the word.
     */
    public String getMiwokTranslation() {
        return mMiwokTranslation;
    }

    /**
     * Get the image resource ID of the word.
     */
    public int getImageResourceId() {
        return mImageResourceId;
    }

    /**
     * Returns whether or not there is an image for this word.
     */
    public boolean hasImage() {
        return mImageResourceId != NO_IMAGE_PROVIDED;
    }

    /**
     * Get the audio resource ID of the word.
     */
    public int getAudioResourceId() {
        return mAudioResourceId;
    }
}
