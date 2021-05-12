package com.balakin.qa.services;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class AudioServiceImpl implements AudioService {

    @Override
    public void downloadAudio(String fileName) {
        try {
            Properties props = new Properties();
            props.load(new FileReader("C:/java/QA.properties"));
            URL url = new URL(props.getProperty("NginxAdresse") + fileName);
            URLConnection connection = url.openConnection();
            InputStream inputStream = connection.getInputStream();
            if (Files.notExists(Path.of("c:/java/" + fileName))) {
                Files.createDirectory(Path.of("c:/java/" + fileName.substring(0, fileName.indexOf("/"))));
                Files.copy(inputStream, Path.of("c:/java/" + fileName));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void mergeAudio(String audio1, String audio2) {
        String folder = "C:/java/audio/";
        String wavFile1 = folder + audio1;
        String wavFile2 = folder + audio2;
        String command = "ffmpeg -i " + wavFile1 + " -i " + wavFile2
                + " -filter_complex amix=inputs=2:duration=longest " + folder + "output2.wav";

        try {
            Runtime.getRuntime().exec(command);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        AudioService audioService = new AudioServiceImpl();
        audioService.downloadAudio("22/test2.wav");
//        audioService.mergeAudio("2021_05_07_07_08_21_273_1003.wav", "2021_05_07_07_08_21_273_9040.wav");


    }
}
