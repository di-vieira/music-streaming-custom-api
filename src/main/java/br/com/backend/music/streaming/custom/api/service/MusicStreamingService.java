package br.com.backend.music.streaming.custom.api.service;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.com.backend.music.streaming.custom.api.domain.spotify.Artist;
import br.com.backend.music.streaming.custom.api.domain.spotify.CreatePlaylistRequest;
import br.com.backend.music.streaming.custom.api.domain.spotify.Playlist;
import br.com.backend.music.streaming.custom.api.domain.spotify.StreamingResponse;
import br.com.backend.music.streaming.custom.api.domain.spotify.Track;

public interface MusicStreamingService {

	/**
	 * Método para retornar uma lista com as músicas mais ouvidas pelo usuário do respectivo
	 * serviço de streaming
	 * 
	 * @return StreamingResponse<Track> Objeto com lista das músicas mais ouvidas
	 * @throws JsonProcessingException
	 */
	public StreamingResponse<Track> findFavoriteTracks();

	/**
	 * Método para retornar uma lista com os artistas mais ouvidos pelo usuário do respectivo
	 * serviço de streaming
	 * 
	 * @return StreamingResponse<Artist> 
	 * @throws JsonProcessingException
	 */
	public StreamingResponse<Artist> findFavoriteArtists();
	
	/**
	 * Cria playlist personalizada com base nos artistas e faixas favoritas
	 * 
	 * @param request Object request to create playlist
	 * @return Playlist Playlist created
	 * @throws JsonProcessingException
	 */
	public Playlist createPersonalPlaylist(CreatePlaylistRequest request);
	
	/**
	 * Search the genres of artists listened by the user and returns how bad is 
	 * @return
	 */
	public String findHowBadIsYourMusicalTaste();
	
	/**
	 * Atualiza o valor do token de autenticação para que o objeto acesse API externa
	 * @param token
	 */
	public void setToken(String token);

}
