package br.com.backend.music.streaming.custom.api.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.com.backend.music.streaming.custom.api.domain.spotify.Artist;
import br.com.backend.music.streaming.custom.api.domain.spotify.CreatePlaylistRequest;
import br.com.backend.music.streaming.custom.api.domain.spotify.Playlist;
import br.com.backend.music.streaming.custom.api.domain.spotify.StreamingResponse;
import br.com.backend.music.streaming.custom.api.domain.spotify.Track;
import br.com.backend.music.streaming.custom.api.service.MusicStreamingService;

public class MusicStreamingControllerTest {

	@InjectMocks
	private MusicStreamingController musicStreamingController;

	@Mock
	private MusicStreamingService musicStreamingService;

	/**
	 * Inicializa os Mocks
	 */
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	/**
	 * Testa o método do controller que retorna as faixas favoritas do usuário
	 */
	@Test
	public void findFavoriteTracksTest() {
		try {
			when(musicStreamingService.findFavoriteTracks()).thenReturn(new StreamingResponse<Track>());
			ResponseEntity<StreamingResponse<Track>> response = musicStreamingController.findFavoriteTracks(null);
			assertNotNull(response);
		} catch (Exception e) {
			fail("Erro na execução do teste ");
		}
	}

	/**
	 * Testa o método do controller que retorna as faixas favoritas do usuário
	 */
	@Test
	public void findFavoriteArtistsTest() {
		try {
			when(musicStreamingService.findFavoriteArtists()).thenReturn(new StreamingResponse<Artist>());
			ResponseEntity<StreamingResponse<Artist>> response = musicStreamingController.findFavoriteArtists(null);
			assertNotNull(response);
		} catch (Exception e) {
			fail("Erro na execução do teste ");
		}
	}

	/**
	 * Testa o método do controller que cria uma playlist personalizada para o
	 * usuário
	 */
	@Test
	public void createPersonalPlaylistTest() {
		try {
			CreatePlaylistRequest request = new CreatePlaylistRequest();
			when(musicStreamingService.createPersonalPlaylist(request)).thenReturn(new Playlist());
			ResponseEntity<Playlist> response = musicStreamingController.createPersonalPlaylist(null, request);
			assertNotNull(response);
		} catch (Exception e) {
			fail("Erro na execução do teste ");
		}
	}

	/**
	 * Testa o método do controller que retorna as faixas favoritas do usuário
	 */
	@Test
	public void findHowBadIsYourMusicalTasteTest() {
		try {
			when(musicStreamingService.findHowBadIsYourMusicalTaste()).thenReturn("");
			ResponseEntity<String> response = musicStreamingController.findHowBadIsYourMusicalTaste("");
			assertNotNull(response);
		} catch (Exception e) {
			fail("Erro na execução do teste ");
		}
	}

	/**
	 * Testa o fluxo de exceção do método que retorna as faixas favoritas do usuário
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void findFavoriteTracksExceptionTest() {
		ResponseEntity<StreamingResponse<Track>> response;
		when(musicStreamingService.findFavoriteTracks()).thenThrow(Exception.class);
		response = musicStreamingController.findFavoriteTracks("");

		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}

	/**
	 * Testa o fluxo de exceção do método que retorna os artistas favoritos do
	 * usuário
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void findFavoriteArtistsExceptionTest() {
		ResponseEntity<StreamingResponse<Artist>> response;
		when(musicStreamingService.findFavoriteArtists()).thenThrow(Exception.class);
		response = musicStreamingController.findFavoriteArtists("");
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}

	/**
	 * Testa o fluxo de exceção do método que retorna a criação de playlist
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void createPersonalPlaylistExceptionTest() {
		CreatePlaylistRequest request = new CreatePlaylistRequest();
		ResponseEntity<Playlist> response;
		when(musicStreamingService.createPersonalPlaylist(request)).thenThrow(Exception.class);
		response = musicStreamingController.createPersonalPlaylist(null, request);
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}

	/**
	 * Testa o fluxo de exceção do método que retorna as faixas favoritas do usuário
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void findHowBadIsYourMusicalTasteExceptionTest() {
		ResponseEntity<String> response;
		when(musicStreamingService.findHowBadIsYourMusicalTaste()).thenThrow(Exception.class);
		response = musicStreamingController.findHowBadIsYourMusicalTaste("");
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}

}
