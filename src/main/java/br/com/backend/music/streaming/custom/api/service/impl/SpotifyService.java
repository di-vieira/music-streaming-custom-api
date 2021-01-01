package br.com.backend.music.streaming.custom.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.backend.music.streaming.custom.api.domain.spotify.response.TopTracksResponse;
import br.com.backend.music.streaming.custom.api.service.MusicStreamingService;

@Service
public class SpotifyService implements MusicStreamingService {
	
	/**
	 * URL da API do Spotify
	 */
	@Value("${spotify.api.url}")
	private String spotifyApiUrl;
	
	/**
	 * URN para formar o endpoint de consulta de Top Tracks
	 */
	@Value("${spotify.api.top.tracks}")
	private String spotifyTopTracks;
	
	/**
	 * Constante para propriedade de do Token de Autorização incluído no Header
	 */
	private final String AUTHORIZATION = "Authorization";
	
	@Autowired
	private RestTemplate restTemplate;

	/**
	 * Busca as faixas favoritas da pessoa
	 */
	@Override
	public TopTracksResponse findFavoriteTracks(String token) throws JsonProcessingException{
		
		// Cria objeto header para incluir token de atualização
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set(AUTHORIZATION, token);
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		
		// Consulta a API do Spotify para retornar as top tracks do usuário logado
		ResponseEntity<String> response = restTemplate.exchange(spotifyApiUrl + spotifyTopTracks, HttpMethod.GET, entity, String.class);
		
		// Serializa o retorno em um objeto TopTracksResponse
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
		TopTracksResponse topTracksResponse = mapper.readValue(response.getBody(), TopTracksResponse.class);

		return topTracksResponse;
	}
	
}
