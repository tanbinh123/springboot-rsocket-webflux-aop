package com.mycompany.movieserver.service;

import com.mycompany.movieserver.model.Movie;
import com.mycompany.movieserver.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    @Override
    public Mono<Movie> getMovie(String imdb) {
        return movieRepository.findById(imdb);
    }

    @Override
    public Flux<Movie> getMovies() {
        return movieRepository.findAll();
    }

    @Override
    public Mono<Movie> addMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    @Override
    public Mono<String> deleteMovie(Movie movie) {
        return movieRepository.delete(movie).then(Mono.just(movie.getImdb()));
    }

    @Override
    public Mono<Void> likeMovie(Movie movie) {
        movie.setLikes(movie.getLikes() + 1);
        movieRepository.save(movie).subscribe();
        return Mono.empty();
    }

    @Override
    public Mono<Void> dislikeMovie(Movie movie) {
        movie.setDislikes(movie.getDislikes() + 1);
        movieRepository.save(movie).subscribe();
        return Mono.empty();
    }

}
