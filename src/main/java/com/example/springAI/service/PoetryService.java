package com.example.springAI.service;

public interface PoetryService {

    String getCatHaiku();

    PoetryDto getPoetryByGenreAndTheme(String genre, String theme);
}
