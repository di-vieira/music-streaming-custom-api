package br.com.backend.music.streaming.custom.api.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.backend.music.streaming.custom.api.domain.entity.Genre;
import br.com.backend.music.streaming.custom.api.repository.GenreRepository;
import br.com.backend.music.streaming.custom.api.service.GenreService;

@Service
public class GenreServiceImpl implements GenreService {

	@Autowired
	private GenreRepository genreRepository;

	@Override
	public List<Genre> listAllGenres() {
		return genreRepository.findAll();
	}

	@Override
	public List<Genre> listBlacklistedGenres(String inBlacklist) {
		return genreRepository.findByInBlacklist(inBlacklist);
	}

	@Override
	public Genre findGenreById(Integer id) {
		Optional<Genre> genre = genreRepository.findById(id);
		return genre.get();
	}

	@Override
	public void saveGenre(Genre genre) {
		genreRepository.save(genre);
	}

	@Override
	public void updateGenre(Genre genre) {
		genreRepository.save(genre);
	}

	@Override
	public void deleteGenreById(Integer id) {
		genreRepository.deleteById(id);
	}

}
