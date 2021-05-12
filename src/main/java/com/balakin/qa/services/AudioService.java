package com.balakin.qa.services;

public interface AudioService {
    void downloadAudio(String fileName);
    void mergeAudio(String audio1, String audio2);
}
