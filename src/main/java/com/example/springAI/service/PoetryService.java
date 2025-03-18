package com.example.springAI.service;

import com.example.springAI.model.dtos.PoetryDto;

public interface PoetryService {

    String getCatHaiku();

    PoetryDto getPoetryByGenreAndTheme(String genre, String theme);
}
