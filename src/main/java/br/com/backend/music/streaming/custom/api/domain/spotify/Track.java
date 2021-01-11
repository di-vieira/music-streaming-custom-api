package br.com.backend.music.streaming.custom.api.domain.spotify;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class to represents an album track
 * 
 * @author diego.vieira
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Track {

	/**
	 * track album
	 */
	@JsonProperty("album")
	private Album album;

	/**
	 * Artists list which participate in that track
	 */
	@JsonProperty("artists")
	private List<Artist> artists;

	/**
	 * List of markets where the track is available
	 */
	@JsonProperty("available_markets")
	private List<String> availableMarkets;

	/**
	 * Disk number (Usually 1. Can be different if album have more than one disk);
	 */
	@JsonProperty("disc_number")
	private Integer discNumber;

	/**
	 * Track duration in miliseconds 
	 */
	@JsonProperty("duration_ms")
	private Integer durationMs;

	/**
	 * Flag that indicates if track has explicit content
	 */
	@JsonProperty("explicit")
	private Boolean explicit;

	/**
	 * External Ids from track
	 */
	@JsonProperty("external_ids")
	private ExternalIds externalIds;

	/**
	 * Track external Urls
	 */
	@JsonProperty("external_urls")
	private ExternalUrls externalUrls;

	/**
	 * Track link in spotify
	 */
	@JsonProperty("href")
	private String href;

	/**
	 * Track id in spotify
	 */
	@JsonProperty("id")
	private String id;

	/**
	 * Indicates if the track is a local file
	 */
	@JsonProperty("is_local")
	private Boolean isLocal;

	/**
	 * Flag to indicate if the track is playable
	 */
	@JsonProperty("is_playable")
	private Boolean isPlayable;

	/**
	 * Track name
	 */
	@JsonProperty("name")
	private String name;

	/**
	 * Track popularity indicator
	 */
	@JsonProperty("popularity")
	private Integer popularity;

	/**
	 * URL para execução do preview de 30 segundos da faixa
	 */
	@JsonProperty("preview_url")
	private String previewUrl;

	/**
	 * Album track number
	 */
	@JsonProperty("track_number")
	private Integer trackNumber;

	/**
	 * Object type
	 */
	@JsonProperty("type")
	private String type;

	/**
	 * Track URI
	 */
	@JsonProperty("uri")
	private String URI;

	/**
	 * @return the album
	 */
	public Album getAlbum() {
		return album;
	}

	/**
	 * @param album the album to set
	 */
	public void setAlbum(Album album) {
		this.album = album;
	}

	/**
	 * @return the artists
	 */
	public List<Artist> getArtists() {
		return artists;
	}

	/**
	 * @param artists the artists to set
	 */
	public void setArtists(List<Artist> artists) {
		this.artists = artists;
	}

	/**
	 * @return the availableMarkets
	 */
	public List<String> getAvailableMarkets() {
		return availableMarkets;
	}

	/**
	 * @param availableMarkets the availableMarkets to set
	 */
	public void setAvailableMarkets(List<String> availableMarkets) {
		this.availableMarkets = availableMarkets;
	}

	/**
	 * @return the discNumber
	 */
	public Integer getDiscNumber() {
		return discNumber;
	}

	/**
	 * @param discNumber the discNumber to set
	 */
	public void setDiscNumber(Integer discNumber) {
		this.discNumber = discNumber;
	}

	/**
	 * @return the durationMs
	 */
	public Integer getDurationMs() {
		return durationMs;
	}

	/**
	 * @param durationMs the durationMs to set
	 */
	public void setDurationMs(Integer durationMs) {
		this.durationMs = durationMs;
	}

	/**
	 * @return the explicit
	 */
	public Boolean getExplicit() {
		return explicit;
	}

	/**
	 * @param explicit the explicit to set
	 */
	public void setExplicit(Boolean explicit) {
		this.explicit = explicit;
	}

	/**
	 * @return the externalIds
	 */
	public ExternalIds getExternalIds() {
		return externalIds;
	}

	/**
	 * @param externalIds the externalIds to set
	 */
	public void setExternalIds(ExternalIds externalIds) {
		this.externalIds = externalIds;
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
	 * @return the isLocal
	 */
	public Boolean getIsLocal() {
		return isLocal;
	}

	/**
	 * @param isLocal the isLocal to set
	 */
	public void setIsLocal(Boolean isLocal) {
		this.isLocal = isLocal;
	}

	/**
	 * @return the isPlayable
	 */
	public Boolean getIsPlayable() {
		return isPlayable;
	}

	/**
	 * @param isPlayable the isPlayable to set
	 */
	public void setIsPlayable(Boolean isPlayable) {
		this.isPlayable = isPlayable;
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
	 * @return the previewUrl
	 */
	public String getPreviewUrl() {
		return previewUrl;
	}

	/**
	 * @param previewUrl the previewUrl to set
	 */
	public void setPreviewUrl(String previewUrl) {
		this.previewUrl = previewUrl;
	}

	/**
	 * @return the trackNumber
	 */
	public Integer getTrackNumber() {
		return trackNumber;
	}

	/**
	 * @param trackNumber the trackNumber to set
	 */
	public void setTrackNumber(Integer trackNumber) {
		this.trackNumber = trackNumber;
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
	 * @return the uRI
	 */
	public String getURI() {
		return URI;
	}

	/**
	 * @param uRI the uRI to set
	 */
	public void setURI(String uRI) {
		URI = uRI;
	}

}
