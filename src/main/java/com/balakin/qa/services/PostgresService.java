package com.balakin.qa.services;


import java.util.List;

public interface PostgresService {
    List<String> getAudioFileNames(String id);
}
