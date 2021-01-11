package br.com.backend.music.streaming.custom.api.domain.spotify;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class to represents an spotify external
 * 
 * @author diego.vieira
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL) 
public class ExternalUrls {
	/**
	 * Spotify external URL
	 */
	@JsonProperty("spotify")
	public String spotify;

	/**
	 * @return the spotify
	 */
	public String getSpotify() {
		return spotify;
	}

	/**
	 * @param spotify the spotify to set
	 */
	public void setSpotify(String spotify) {
		this.spotify = spotify;
	}

}
