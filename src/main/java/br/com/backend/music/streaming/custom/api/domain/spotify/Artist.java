package br.com.backend.music.streaming.custom.api.domain.spotify;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Classe para representar um artista
 * 
 * @author diego.vieira
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL) 
public class Artist {

	/**
	 * Lista de URLs do Artista externas à API
	 */
	@JsonProperty("external_urls")
	private ExternalUrls externalUrls;

	/**
	 * Lista de seguidores do Artista
	 */
	@JsonProperty("followers")
	private Followers followers;

	/**
	 * Lista de Generos aos quais o artista pertence
	 */
	@JsonProperty("genres")
	private List<String> genres;

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
	 * Imagens referentes ao artista
	 */
	@JsonProperty("images")
	private List<Image> images;

	/**
	 * Nome do artista
	 */
	@JsonProperty("name")
	private String name;

	/**
	 * Popularidade do artista
	 */
	@JsonProperty("popularity")
	private Integer popularity;

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
	public ExternalUrls getExternalUrls() {
		return externalUrls;
	}

	/**
	 * @param externalUrls the externalUrls to set
	 */
	public void setExternalUrls(ExternalUrls externalUrls) {
		this.externalUrls = externalUrls;
	}

	/**
	 * @return the followers
	 */
	public Followers getFollowers() {
		return followers;
	}

	/**
	 * @param followers the followers to set
	 */
	public void setFollowers(Followers followers) {
		this.followers = followers;
	}

	/**
	 * @return the genres
	 */
	public List<String> getGenres() {
		return genres;
	}

	/**
	 * @param genres the genres to set
	 */
	public void setGenres(List<String> genres) {
		this.genres = genres;
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
	 * @return the images
	 */
	public List<Image> getImages() {
		return images;
	}

	/**
	 * @param images the images to set
	 */
	public void setImages(List<Image> images) {
		this.images = images;
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
	 * @return the popularity
	 */
	public Integer getPopularity() {
		return popularity;
	}

	/**
	 * @param popularity the popularity to set
	 */
	public void setPopularity(Integer popularity) {
		this.popularity = popularity;
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
