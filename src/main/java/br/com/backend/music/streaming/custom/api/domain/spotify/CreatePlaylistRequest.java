package br.com.backend.music.streaming.custom.api.domain.spotify;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Classe para representar o request body da criação de playlist
 * 
 * @author diegovieira
 *
 */
public class CreatePlaylistRequest {

	/**
	 * Nome da Playlist criada
	 */
	@JsonProperty("name")
	private String name;

	/**
	 * Flag que indica se a playlist é pública
	 */
	@JsonProperty("public")
	private Boolean isPublic;

	/**
	 * Flag que indica se a playlist é colaborativa
	 */
	@JsonProperty("collaborative")
	private Boolean collaborative;

	/**
	 * Descrição da playlist
	 */
	@JsonProperty("description")
	private String description;

	public CreatePlaylistRequest(String name, Boolean isPublic, Boolean collaborative,
			String description) {
		super();
		this.name = name;
		this.isPublic = isPublic;
		this.collaborative = collaborative;
		this.description = description;
	}
	
	public CreatePlaylistRequest() {

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

}
