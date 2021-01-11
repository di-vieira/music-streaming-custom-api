package br.com.backend.music.streaming.custom.api.service;

import br.com.backend.music.streaming.custom.api.domain.spotify.Artist;
import br.com.backend.music.streaming.custom.api.domain.spotify.Playlist;
import br.com.backend.music.streaming.custom.api.domain.spotify.StreamingResponse;
import br.com.backend.music.streaming.custom.api.domain.spotify.Track;

public interface MusicStreamingService {

	/**
	 * Returns a list of user's favorite tracks
	 * @return StreamingResponse<Track> Object containing list of favorite tracks
	 */
	public StreamingResponse<Track> findFavoriteTracks();

	/**
	 * Returns a list of user's favorite artists
	 * @return StreamingResponse<Artist> Object containing list of favorite artists
	 */
	public StreamingResponse<Artist> findFavoriteArtists();
	
	/**
	 * Creates new playlist based on user's favorite artists and tracks
	 * @param playlistName Name of created playlist
	 * @return Playlist Playlist created
	 */
	public Playlist createPersonalPlaylist(String playlistName);
	
	/**
	 * Search the genres of artists listened by the user and returns how bad is 
	 * @return String
	 */
	public String findHowBadIsYourMusicalTaste();
	
	/**
	 * Updates value of authorization token
	 * @param token
	 */
	public void setToken(String token);

}
