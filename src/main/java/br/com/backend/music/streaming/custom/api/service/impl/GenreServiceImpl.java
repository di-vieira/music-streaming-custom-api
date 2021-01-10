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
	
	private final String IN_BLACKLIST = "S";

	@Override
	public List<Genre> findAllGenres() {
		return genreRepository.findAll();
	}

	@Override
	public List<Genre> findBlacklistedGenres() {
		return genreRepository.findByInBlacklist(IN_BLACKLIST);
	}

	@Override
	public Genre findGenreById(Integer id) {
		Optional<Genre> genre = genreRepository.findById(id);
		if(genre.isEmpty()) {
			genre = Optional.of(new Genre());
		}
		return genre.get();
	}

	@Override
	public void saveGenre(Genre genre) {
		genreRepository.save(genre);
	}

	@Override
	public void updateGenre(Integer id, Genre genre) {
		Genre gen = findGenreById(id);
		if(gen.getGenreId() != null) {
			gen.setInBlacklist(genre.getInBlacklist());
			genreRepository.save(gen);
		}
	}

	@Override
	public void deleteGenreById(Integer id) {
		genreRepository.deleteById(id);
	}

}
