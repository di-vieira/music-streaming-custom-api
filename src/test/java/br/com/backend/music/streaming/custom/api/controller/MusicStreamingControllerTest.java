package br.com.backend.music.streaming.custom.api.controller;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import br.com.backend.music.streaming.custom.api.domain.spotify.Artist;
import br.com.backend.music.streaming.custom.api.domain.spotify.CreatePlaylistRequest;
import br.com.backend.music.streaming.custom.api.domain.spotify.Playlist;
import br.com.backend.music.streaming.custom.api.domain.spotify.StreamingResponse;
import br.com.backend.music.streaming.custom.api.domain.spotify.Track;
import br.com.backend.music.streaming.custom.api.service.MusicStreamingService;

public class MusicStreamingControllerTest {
	
	@InjectMocks
	private MusicStreamingController controller;
	
	@Mock
	private MusicStreamingService service;
	
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
			when(service.findFavoriteTracks()).thenReturn(new StreamingResponse<Track>());
			ResponseEntity<StreamingResponse<Track>> response = controller.findFavoriteTracks(null);
			assertNotNull(response);
		} catch (Exception e) {
			fail("Erro na execução do teste ");
		}
	}
	
	/**
	 * Testa o fluxo de exceção do método que retorna as faixas favoritas do usuário
	 */
//	@SuppressWarnings("unchecked")
//	@Test
//	public void findFavoriteTracksJsonProcessingExceptionTest() {
//		try {
//			when(service.findFavoriteTracks()).thenThrow(JsonProcessingException.class);
//		} catch (JsonProcessingException e) {
//			fail("Erro na execução do teste ");
//		}
//		ResponseEntity<StreamingResponse<Track>> response = controller.findFavoriteTracks(null);
//		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode() );
//	}
	
	/**
	 * Testa o método do controller que retorna as faixas favoritas do usuário
	 */
	@Test
	public void findFavoriteArtistsTest() {
		try {
			when(service.findFavoriteArtists()).thenReturn(new StreamingResponse<Artist>());
			ResponseEntity<StreamingResponse<Artist>> response = controller.findFavoriteArtists(null);
			assertNotNull(response);
		} catch (Exception e) {
			fail("Erro na execução do teste ");
		}
	}
	
	/**
	 * Testa o fluxo de exceção do método que retorna as faixas favoritas do usuário
	 */
//	@SuppressWarnings("unchecked")
//	@Test
//	public void findFavoriteArtistsJsonProcessingExceptionTest() {
//		try {
//			when(service.findFavoriteArtists()).thenThrow(JsonProcessingException.class);
//		} catch (JsonProcessingException e) {
//			fail("Erro na execução do teste ");
//		}
//		ResponseEntity<StreamingResponse<Artist>> response = controller.findFavoriteArtists(null);
//		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode() );
//	}
	
	/**
	 * Testa o método do controller que retorna as faixas favoritas do usuário
	 */
	@Test
	public void createPersonalPlaylistTest() {
		try {
			CreatePlaylistRequest request = new CreatePlaylistRequest();
			when(service.createPersonalPlaylist(request)).thenReturn(new Playlist());
			ResponseEntity<Playlist> response = controller.createPersonalPlaylist(null, request);
			assertNotNull(response);
		} catch (Exception e) {
			fail("Erro na execução do teste ");
		}
	}
	
	/**
	 * Testa o fluxo de exceção do método que retorna as faixas favoritas do usuário
	 */
//	@SuppressWarnings("unchecked")
//	@Test
//	public void createPersonalPlaylistExceptionTest() {
//		CreatePlaylistRequest request = new CreatePlaylistRequest();
//		try {
//			when(service.createPersonalPlaylist(request)).thenThrow(Exception.class);
//		} catch (JsonProcessingException e) {
//			fail("Erro na execução do teste ");
//		}
//		ResponseEntity<Playlist> response = controller.createPersonalPlaylist(null, request);
//		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode() );
//	}

}
