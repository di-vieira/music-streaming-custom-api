package br.com.backend.music.streaming.custom.api.domain.spotify;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Classe para representar um artista
 * 
 * @author diego.vieira
 *
 */
public class Artist {

	/**
	 * Lista de URLs do Artista externas à API
	 */
	@JsonProperty("external_urls")
	private List<ExternalUrl> externalUrls;

	/**
	 * URL do artista na API
	 */
	@JsonProperty("href")
	private String href;

	/**
	 * Id do artista
	 */
	@JsonProperty("id")
	private String id;

	/**
	 * Nome do artista
	 */
	@JsonProperty("name")
	private String name;

	/**
	 * Tipo do artista
	 */
	@JsonProperty("type")
	private String type;

	/**
	 * URI do artista
	 */
	@JsonProperty("uri")
	private String uri;

	/**
	 * @return the externalUrls
	 */
	public List<ExternalUrl> getExternalUrls() {
		return externalUrls;
	}

	/**
	 * @param externalUrls the externalUrls to set
	 */
	public void setExternalUrls(List<ExternalUrl> externalUrls) {
		this.externalUrls = externalUrls;
	}

	/**
	 * @return the href
	 */
	public String getHref() {
		return href;
	}

	/**
	 * @param href the href to set
	 */
	public void setHref(String href) {
		this.href = href;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the uri
	 */
	public String getUri() {
		return uri;
	}

	/**
	 * @param uri the uri to set
	 */
	public void setUri(String uri) {
		this.uri = uri;
	}

}
