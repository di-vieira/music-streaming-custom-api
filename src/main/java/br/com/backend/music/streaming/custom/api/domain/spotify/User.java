package br.com.backend.music.streaming.custom.api.domain.spotify;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Classe para representar o usuário autorizado a consumir a API
 * 
 * @author diegovieira
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL) 
public class User {

	/**
	 * País do usuário
	 */
	@JsonProperty("country")
	private String country;
	/**
	 * Nome do usuário exibido pelo serviço de streaming
	 */
	@JsonProperty("display_name")
	private String displayName;
	/**
	 * URLs externas do usuário
	 */
	@JsonProperty("external_urls")
	private ExternalUrls externalUrls;
	/**
	 * Dados dos seguidores do usuário
	 */
	@JsonProperty("followers")
	private Followers followers;
	/**
	 * Url do usuário
	 */
	@JsonProperty("href")
	private String href;
	/**
	 * id do usuário
	 */
	@JsonProperty("id")
	private String id;
	/**
	 * Lista de imagens do usuário
	 */
	@JsonProperty("images")
	private List<Image> images;
	/**
	 * Tipo de assinatura do usuário
	 */
	@JsonProperty("product")
	private String product;
	/**
	 * tipo de objeto
	 */
	@JsonProperty("type")
	private String type;
	/**
	 * URI do usuário
	 */
	@JsonProperty("uri")
	private String uri;

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return the displayName
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * @param displayName the displayName to set
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

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
	 * @return the product
	 */
	public String getProduct() {
		return product;
	}

	/**
	 * @param product the product to set
	 */
	public void setProduct(String product) {
		this.product = product;
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
