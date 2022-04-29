package com.example.ed_talk.ui.MyArticlesFragment;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class GalleryViewModel extends ViewModel {


    private static MutableLiveData<String> mText;


    public GalleryViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public static  MutableLiveData<String> getText() {
        return mText;
    }
}