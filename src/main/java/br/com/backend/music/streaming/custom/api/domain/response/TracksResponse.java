package br.com.backend.music.streaming.custom.api.domain.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.backend.music.streaming.custom.api.domain.spotify.Track;

/**
 * Response class for services which returns tracks
 * @author diegovieira
 *
 */
public class TracksResponse {

	/**
	 * List of Tracks
	 */
	@JsonProperty("tracks")
	List<Track> tracks;

	/**
	 * @return the tracks
	 */
	public List<Track> getTracks() {
		return tracks;
	}

	/**
	 * @param tracks the tracks to set
	 */
	public void setTracks(List<Track> tracks) {
		this.tracks = tracks;
	}
	

}
