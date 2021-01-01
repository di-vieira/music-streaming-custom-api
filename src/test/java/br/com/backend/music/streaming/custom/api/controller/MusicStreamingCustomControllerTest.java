package br.com.backend.music.streaming.custom.api.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.com.backend.music.streaming.custom.api.domain.spotify.response.TopTracksResponse;
import br.com.backend.music.streaming.custom.api.service.MusicStreamingService;

public class MusicStreamingCustomControllerTest {
	
	@InjectMocks
	private MusicStreamingCustomController controller;
	
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
			when(service.findFavoriteTracks(anyString())).thenReturn(new TopTracksResponse());
			ResponseEntity<TopTracksResponse> response = controller.findFavoriteTracks(null);
			assertNotNull(response);
		} catch (JsonProcessingException e) {
			fail("Erro ao processar o JSON no método findFavoriteTracks");
		} catch (Exception e) {
			fail("Erro na execução do teste ");
		}
	}
	
	/**
	 * Testa o fluxo de exceção do método que retorna as faixas favoritas do usuário
	 */
	@Test
	public void findFavoriteTracksJsonProcessingExceptionTest() {
		try {
			when(service.findFavoriteTracks(anyString())).thenThrow(JsonProcessingException.class);
		} catch (JsonProcessingException e) {
			fail("Erro na execução do teste ");
		}
		ResponseEntity<TopTracksResponse> response = controller.findFavoriteTracks(null);
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode() );
	}

}
