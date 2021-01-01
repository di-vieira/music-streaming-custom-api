package br.com.backend.music.streaming.custom.api.domain.spotify;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Classe para representar uma imagem do Spotify
 * 
 * @author diego.vieira
 *
 */
public class Image {

	/**
	 * Altura da imagem
	 */
	@JsonProperty("height")
	private Integer height;
	
	/**
	 * Url da imagem
	 */
	@JsonProperty("url")
	private String url;
	
	/**
	 * Largura da imagem
	 */
	@JsonProperty("width")
	private Integer width;

	/**
	 * @return the height
	 */
	public Integer getHeight() {
		return height;
	}

	/**
	 * @param height the height to set
	 */
	public void setHeight(Integer height) {
		this.height = height;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the width
	 */
	public Integer getWidth() {
		return width;
	}

	/**
	 * @param width the width to set
	 */
	public void setWidth(Integer width) {
		this.width = width;
	}

	
}