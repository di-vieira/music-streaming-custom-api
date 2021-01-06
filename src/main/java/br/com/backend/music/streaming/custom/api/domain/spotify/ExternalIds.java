package br.com.backend.music.streaming.custom.api.domain.spotify;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Classe para representar um id externo de uma faixa
 * 
 * @author diego.vieira
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL) 
public class ExternalIds {

	/**
	 * Código do ISRC da faixa (International Standard Recording Code)
	 */
	@JsonProperty("isrc")
	private String isrc;

	/**
	 * @return the isrc
	 */
	public String getIsrc() {
		return isrc;
	}

	/**
	 * @param isrc the isrc to set
	 */
	public void setIsrc(String isrc) {
		this.isrc = isrc;
	}

}
