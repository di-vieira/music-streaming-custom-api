package br.com.backend.music.streaming.custom.api.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.com.backend.music.streaming.custom.api.domain.spotify.Artist;
import br.com.backend.music.streaming.custom.api.domain.spotify.Playlist;
import br.com.backend.music.streaming.custom.api.domain.spotify.StreamingResponse;
import br.com.backend.music.streaming.custom.api.domain.spotify.Track;
import br.com.backend.music.streaming.custom.api.service.MusicStreamingService;

public class MusicStreamingControllerTest {

	@InjectMocks
	private MusicStreamingController musicStreamingController;

	@Mock
	private MusicStreamingService musicStreamingService;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

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

	@Test
	public void createPersonalPlaylistTest() {
		try {
			when(musicStreamingService.createPersonalPlaylist(anyString())).thenReturn(new Playlist());
			ResponseEntity<Playlist> response = musicStreamingController.createPersonalPlaylist(null, "");
			assertNotNull(response);
		} catch (Exception e) {
			fail("Erro na execução do teste ");
		}
	}

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

	@Test
	public void findFavoriteTracksExceptionTest() {
		ResponseEntity<StreamingResponse<Track>> response;
		when(musicStreamingService.findFavoriteTracks()).thenThrow(RuntimeException.class);
		response = musicStreamingController.findFavoriteTracks("");

		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}

	@Test
	public void findFavoriteArtistsExceptionTest() {
		ResponseEntity<StreamingResponse<Artist>> response;
		when(musicStreamingService.findFavoriteArtists()).thenThrow(RuntimeException.class);
		response = musicStreamingController.findFavoriteArtists("");
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}

	@Test
	public void createPersonalPlaylistExceptionTest() {
		ResponseEntity<Playlist> response;
		when(musicStreamingService.createPersonalPlaylist(anyString())).thenThrow(RuntimeException.class);
		response = musicStreamingController.createPersonalPlaylist(null, "");
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}

	@Test
	public void findHowBadIsYourMusicalTasteExceptionTest() {
		ResponseEntity<String> response;
		when(musicStreamingService.findHowBadIsYourMusicalTaste()).thenThrow(RuntimeException.class);
		response = musicStreamingController.findHowBadIsYourMusicalTaste("");
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}

}
