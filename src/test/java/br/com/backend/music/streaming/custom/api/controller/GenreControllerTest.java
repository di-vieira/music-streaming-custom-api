package br.com.backend.music.streaming.custom.api.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import br.com.backend.music.streaming.custom.api.domain.entity.Genre;
import br.com.backend.music.streaming.custom.api.service.GenreService;

public class GenreControllerTest {

	@InjectMocks
	private GenreController genreController;
	
	@Mock
	private GenreService genreService;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void findAllGenresTest() {
		List<Genre> mockedGenres = new ArrayList<Genre>();
		mockedGenres.add(new Genre());
		Mockito.when(genreService.findAllGenres()).thenReturn(mockedGenres);
		List<Genre> genres = genreController.findAllGenres();
		assertNotNull(genres);
		assertEquals(1, genres.size());
	}
	
	@Test
	public void findBlacklistedGenresTest() {
		List<Genre> mockedGenres = new ArrayList<Genre>();
		mockedGenres.add(new Genre());
		Mockito.when(genreService.findBlacklistedGenres()).thenReturn(mockedGenres);
		List<Genre> genres = genreController.findBlacklistedGenres();
		assertNotNull(genres);
		assertEquals(1, genres.size());
	}
	
	@Test
	public void findGenreByIdTest() {
		Genre mockedGenre = new Genre();
		mockedGenre.setGenreId(1);
		mockedGenre.setGenreName("testName");
		mockedGenre.setInBlacklist("N");
		
		when(genreService.findGenreById(anyInt())).thenReturn(mockedGenre);
		Genre genre = genreController.findGenreById(1);
		assertNotNull(genre);
		assertEquals(Integer.valueOf(1), genre.getGenreId());
		assertEquals("testName", genre.getGenreName());
		assertEquals("N", genre.getInBlacklist());
	}
	
	@Test
	public void createGenreTest() {
		Genre mockedGenre = new Genre();
		doNothing().when(genreService).saveGenre(any(Genre.class));
		genreController.createGenre(mockedGenre);
		verify(genreService, times(1)).saveGenre(mockedGenre);
	}
	
	@Test
	public void updateGenreTest() {
		Genre mockedGenre = new Genre();		
		doNothing().when(genreService).updateGenre(anyInt(), any(Genre.class));
		genreController.updateGenre(1, mockedGenre);
		verify(genreService, times(1)).updateGenre(1, mockedGenre);
	}
	
	@Test
	public void deleteGenreTest() {
		doNothing().when(genreService).deleteGenreById(anyInt());
		genreController.deleteGenre(1);
		verify(genreService, times(1)).deleteGenreById(1);
	}
	
	
}
