package com.free.agent.service;


import com.free.agent.model.Film;

import java.util.List;

public interface FilmService {

    List<Film> getAllFilms();

    Film getFilm(Integer id);

}
