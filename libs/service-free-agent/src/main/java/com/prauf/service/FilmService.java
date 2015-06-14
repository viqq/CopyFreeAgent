package com.prauf.service;





import com.prauf.model.Film;

import java.util.List;

public interface FilmService {

    List<Film> getAllFilms();

    Film getFilm(Integer id);

}
