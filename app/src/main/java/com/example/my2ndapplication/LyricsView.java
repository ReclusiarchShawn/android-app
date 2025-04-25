package com.example.my2ndapplication;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LyricsView extends ViewModel {
    private final MutableLiveData<String> lyrics = new MutableLiveData<>();
    private final MutableLiveData<String> error = new MutableLiveData<>();

    public LiveData<String> getLyrics() {
        return lyrics;
    }

    public LiveData<String> getError() {
        return error;
    }

    public void setLyrics(String lyricsText) {
        this.lyrics.setValue(lyricsText);
    }

    public void setError(String errorMessage) {
        this.error.setValue(errorMessage);
    }
}
