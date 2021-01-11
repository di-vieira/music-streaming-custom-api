package br.com.backend.music.streaming.custom.api.domain.spotify;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class to represents an album
 * 
 * @author diego.vieira
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL) 
public class Album {

	/**
	 * Album type
	 */
	@JsonProperty("album_type")
	private String albumType;

	/**
	 * List of album artists
	 */
	@JsonProperty("artists")
	private List<Artist> artists;

	/**
	 * List of markets where the album is available
	 */
	@JsonProperty("available_markets")
	private List<String> availableMarkets;

	/**
	 * List of external urls from API
	 */
	@JsonProperty("external_urls")
	private ExternalUrls externalUrls;

	/**
	 * Album link
	 */
	@JsonProperty("href")
	private String href;

	/**
	 * Album Id
	 */
	@JsonProperty("id")
	private String id;

	/**
	 * Image list from album
	 */
	@JsonProperty("images")
	private List<Image> images;

	/**
	 * Album name
	 */
	@JsonProperty("name")
	private String name;

	/**
	 * Data de lançamento do album
	 */
	@JsonProperty("release_date")
	private String releaseDate;

	/**
	 * Precision of release date of album (day, year etc)
	 */
	@JsonProperty("release_date_precision")
	private String releaseDatePrecision;

	/**
	 * Number of tracks for this album
	 */
	@JsonProperty("total_tracks")
	private Integer totalTracks;

	/**
	 * album type
	 */
	@JsonProperty("type")
	private String type;

	/**
	 * Album URI
	 */
	@JsonProperty("uri")
	private String uri;

	/**
	 * @return the albumType
	 */
	public String getAlbumType() {
		return albumType;
	}

	/**
	 * @param albumType the albumType to set
	 */
	public void setAlbumType(String albumType) {
		this.albumType = albumType;
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
	 * @return the releaseDate
	 */
	public String getReleaseDate() {
		return releaseDate;
	}

	/**
	 * @param releaseDate the releaseDate to set
	 */
	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}

	/**
	 * @return the releaseDatePrecision
	 */
	public String getReleaseDatePrecision() {
		return releaseDatePrecision;
	}

	/**
	 * @param releaseDatePrecision the releaseDatePrecision to set
	 */
	public void setReleaseDatePrecision(String releaseDatePrecision) {
		this.releaseDatePrecision = releaseDatePrecision;
	}

	/**
	 * @return the totalTracks
	 */
	public Integer getTotalTracks() {
		return totalTracks;
	}

	/**
	 * @param totalTracks the totalTracks to set
	 */
	public void setTotalTracks(Integer totalTracks) {
		this.totalTracks = totalTracks;
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
