package br.com.backend.music.streaming.custom.api.service;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.com.backend.music.streaming.custom.api.domain.spotify.response.TopTracksResponse;

public interface MusicStreamingService {

	/**
	 * Método para retornar uma lista com as músicas mais ouvidas pelo respectivo serviço de streaming
	 * 
	 * @return TopTracksResponse lista das músicas mais ouvidas
	 */
	public TopTracksResponse findFavoriteTracks(String token) throws JsonProcessingException;
		
}
