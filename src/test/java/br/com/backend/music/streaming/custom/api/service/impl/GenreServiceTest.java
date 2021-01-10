package br.com.backend.music.streaming.custom.api.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import br.com.backend.music.streaming.custom.api.domain.entity.Genre;
import br.com.backend.music.streaming.custom.api.repository.GenreRepository;
import br.com.backend.music.streaming.custom.api.service.GenreService;

public class GenreServiceTest {

	@InjectMocks
	private GenreService genreService = new GenreServiceImpl();
	
	@Mock
	private GenreRepository genreRepository;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void findAllGenresTest() {
		List<Genre> mockedGenres = new ArrayList<Genre>();
		mockedGenres.add(new Genre());
		Mockito.when(genreRepository.findAll()).thenReturn(mockedGenres);
		List<Genre> genres = genreService.findAllGenres();
		assertNotNull(genres);
		assertEquals(1, genres.size());
	}
	
	@Test
	public void findBlacklistedGenresTest() {
		List<Genre> mockedGenres = new ArrayList<Genre>();
		mockedGenres.add(new Genre());
		Mockito.when(genreRepository.findByInBlacklist("S")).thenReturn(mockedGenres);
		List<Genre> genres = genreService.findBlacklistedGenres();
		assertNotNull(genres);
		assertEquals(1, genres.size());
	}
	
	@Test
	public void findGenreByIdTest() {
		Genre mockedGenre = new Genre();
		mockedGenre.setGenreId(1);
		mockedGenre.setGenreName("testName");
		mockedGenre.setInBlacklist("N");
		
		when(genreRepository.findById(anyInt())).thenReturn(Optional.of(mockedGenre));
		Genre genre = genreService.findGenreById(1);
		assertNotNull(genre);
		assertEquals(Integer.valueOf(1), genre.getGenreId());
		assertEquals("testName", genre.getGenreName());
		assertEquals("N", genre.getInBlacklist());
	}
	
	@Test
	public void findGenreByIdWhenIdNotExistsTest() {
		Genre mockedGenre = new Genre();
		
		when(genreRepository.findById(anyInt())).thenReturn(Optional.of(mockedGenre));
		Genre genre = genreService.findGenreById(1);
		assertNotNull(genre);
		assertNull(genre.getGenreId());
		assertNull(genre.getGenreName());
		assertNull(genre.getInBlacklist());
	}
	
	@Test
	public void saveGenreTest() {
		Genre mockedGenre = new Genre();
		when(genreRepository.save(any(Genre.class))).thenReturn(new Genre());
		genreService.saveGenre(mockedGenre);
		verify(genreRepository, times(1)).save(mockedGenre);
	}
	
	@Test
	public void updateGenreTest() {
		Genre mockedGenre = new Genre();
		mockedGenre.setGenreId(1);
		mockedGenre.setGenreName("testName");
		mockedGenre.setInBlacklist("N");
		
		when(genreRepository.findById(anyInt())).thenReturn(Optional.of(mockedGenre));
		when(genreRepository.save(any(Genre.class))).thenReturn(new Genre());
		genreService.updateGenre(1, mockedGenre);
		verify(genreRepository, times(1)).findById(1);
		verify(genreRepository, times(1)).save(mockedGenre);
	}
	
	@Test
	public void updateGenreWhenGenreNotExistsTest() {
		Genre mockedGenre = new Genre();
		mockedGenre.setGenreId(1);
		mockedGenre.setGenreName("testGenreNotExists");
		mockedGenre.setInBlacklist("N");
		
		when(genreRepository.findById(anyInt())).thenReturn(Optional.empty());
		when(genreRepository.save(any(Genre.class))).thenReturn(new Genre());
		genreService.updateGenre(1, mockedGenre);
		verify(genreRepository, times(1)).findById(1);
	}
	
	@Test
	public void deleteGenreTest() {
		doNothing().when(genreRepository).deleteById(anyInt());
		genreService.deleteGenreById(1);
		verify(genreRepository, times(1)).deleteById(1);
	}

}
