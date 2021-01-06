package br.com.backend.music.streaming.custom.api.domain.spotify;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.backend.music.streaming.custom.api.domain.response.StreamingResponse;

/**
 * Classe que representa uma playlist
 * 
 * @author diegovieira
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Playlist {

	/**
	 * 
	 */
	@JsonProperty("collaborative")
	private Boolean collaborative;
	
	/**
	 * 
	 */
	@JsonProperty("description")
	private String description;
	
	/**
	 * 
	 */
	@JsonProperty("external_urls")
	private ExternalUrls externalUrls;
	
	/**
	 * 
	 */
	@JsonProperty("followers")
	private Followers followers;
	
	/**
	 * 
	 */
	@JsonProperty("href")
	private String href;
	
	/**
	 * 
	 */
	@JsonProperty("id")
	private String id;
	
	/**
	 * 
	 */
	@JsonProperty("images")
	private List<Image> images;
	
	/**
	 * 
	 */
	@JsonProperty("name")
	private String name;
	
	/**
	 * Proprietário da playlist
	 */
	@JsonProperty("owner")
	private User owner;
	
	/**
	 * Flag que indica se a playlist é publica ou privada
	 */
	@JsonProperty("public")
	private Boolean isPublic;
	
	/**
	 * Identificador da versão da playlist
	 */
	@JsonProperty("snapshot_id")
	private String snapshotId;
	
	/**
	 * Lista de faixas que compoem a playlist
	 */
	@JsonProperty("tracks")
	private StreamingResponse<Track> tracks;
	
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
	 * @return the collaborative
	 */
	public Boolean getCollaborative() {
		return collaborative;
	}

	/**
	 * @param collaborative the collaborative to set
	 */
	public void setCollaborative(Boolean collaborative) {
		this.collaborative = collaborative;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
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
	 * @return the owner
	 */
	public User getOwner() {
		return owner;
	}

	/**
	 * @param owner the owner to set
	 */
	public void setOwner(User owner) {
		this.owner = owner;
	}

	/**
	 * @return the isPublic
	 */
	public Boolean getIsPublic() {
		return isPublic;
	}

	/**
	 * @param isPublic the isPublic to set
	 */
	public void setIsPublic(Boolean isPublic) {
		this.isPublic = isPublic;
	}

	/**
	 * @return the snapshotId
	 */
	public String getSnapshotId() {
		return snapshotId;
	}

	/**
	 * @param snapshotId the snapshotId to set
	 */
	public void setSnapshotId(String snapshotId) {
		this.snapshotId = snapshotId;
	}

	/**
	 * @return the tracks
	 */
	public StreamingResponse<Track> getTracks() {
		return tracks;
	}

	/**
	 * @param tracks the tracks to set
	 */
	public void setTracks(StreamingResponse<Track> tracks) {
		this.tracks = tracks;
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
