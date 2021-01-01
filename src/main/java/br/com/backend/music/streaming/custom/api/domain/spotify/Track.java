package br.com.backend.music.streaming.custom.api.domain.spotify;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Classe para representar uma faixa de um album
 * 
 * @author diego.vieira
 *
 */
public class Track {

	/**
	 * Album da faixa
	 */
	@JsonProperty("album")
	private Album album;

	/**
	 * Lista de artistas que participam da faixa
	 */
	@JsonProperty("artists")
	private List<Artist> artists;

	/**
	 * Lista de mercados aonde a faixa está disponível
	 */
	@JsonProperty("available_markets")
	private List<String> availableMarkets;

	/**
	 * Número do disco (Geralmente valor 1. Pode ser diferente em albuns com mais de
	 * um disco);
	 */
	@JsonProperty("disc_number")
	private Integer discNumber;

	/**
	 * Duração da faixa em milissegundos
	 */
	@JsonProperty("duration_ms")
	private Integer durationMs;

	/**
	 * Flag que indica se a faixa possui conteúdo explícito
	 */
	@JsonProperty("explicit")
	private Boolean explicit;

	@JsonProperty("external_ids")
	private List<ExternalId> externalIds;

	/**
	 * Lista de urls da faixa
	 */
	@JsonProperty("external_urls")
	private List<ExternalUrl> externalUrls;

	/**
	 * Link da faixa na API Spotify
	 */
	@JsonProperty("href")
	private String href;

	/**
	 * Id da faixa no Spotify
	 */
	@JsonProperty("id")
	private String id;

	/**
	 * Indica se a faixa é de um arquivo local
	 */
	@JsonProperty("is_local")
	private Boolean isLocal;

	/**
	 * Nome da Faixa
	 */
	@JsonProperty("name")
	private String name;

	/**
	 * Indicador de popularidade da faixa
	 */
	@JsonProperty("popularity")
	private Integer popularity;

	/**
	 * URL para execução do preview de 30 segundos da faixa
	 */
	@JsonProperty("preview_url")
	private String previewUrl;

	/**
	 * Número da faixa no album. Em albuns com mais de um disco, se refere ao número
	 * daquele disco em específico
	 */
	@JsonProperty("track_number")
	private Integer trackNumber;

	/**
	 * Tipo da faixa
	 */
	@JsonProperty("type")
	private String type;

	/**
	 * URI da faixa
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
	public List<ExternalId> getExternalIds() {
		return externalIds;
	}

	/**
	 * @param externalIds the externalIds to set
	 */
	public void setExternalIds(List<ExternalId> externalIds) {
		this.externalIds = externalIds;
	}

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
	 * @return the track
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setTrack(String type) {
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
