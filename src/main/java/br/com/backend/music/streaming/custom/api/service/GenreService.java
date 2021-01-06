package br.com.backend.music.streaming.custom.api.service;

import java.util.List;

import br.com.backend.music.streaming.custom.api.domain.entity.Genre;

public interface GenreService {
	
	/**
	 * Returns a list containing all genres
	 * @return the genres 
	 */
	public List<Genre> listAllGenres();
	
	/**
	 * Returns a list containing only the blacklisted genres
	 * @return the genres
	 */
	public List<Genre> listBlacklistedGenres(String inBlacklist);
	
	/**
	 * Find a genre by its id
	 * @param id genre id
	 * @return
	 */
	public Genre findGenreById(Integer id);
	
	/**
	 * Creates a new genre record
	 * @param genre
	 */
	public void saveGenre(Genre genre);
	
	/**
	 * Update an existing genre
	 * @param genre
	 */
	public void updateGenre(Genre genre);
	
	/**
	 * remove a genre from entity
	 * @param id
	 */
	public void deleteGenreById(Integer id);

}
