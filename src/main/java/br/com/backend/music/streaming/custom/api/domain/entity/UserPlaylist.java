package br.com.backend.music.streaming.custom.api.domain.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiParam;

/**
 * Entity to represents table USER_PLAYLIST
 * @author diegovieira
 *
 */
@Entity
@Table(name="USER_PLAYLIST")
public class UserPlaylist {

	/**
	 * User ID
	 */
	@Id
	@ApiParam("Id do Usuário")
	@Column(name="ID_USER")
	private String userId;
	
	/**
	 * Playlist ID
	 */
	@ApiParam("Id da Playlist")
	@Column(name="ID_PLAYLIST")
	private String playlistId;
	
	/**
	 * Date when personal playlist was created 
	 */
	@ApiParam("Data de Inclusão da Playlist")
	@Column(name="DT_INCL_PLAYLIST")
	private LocalDate includeDate;
	
	public UserPlaylist() {
		
	}

	public UserPlaylist(String userId, String playlistId, LocalDate includeDate) {
		super();
		this.userId = userId;
		this.playlistId = playlistId;
		this.includeDate = includeDate;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the playlistId
	 */
	public String getPlaylistId() {
		return playlistId;
	}

	/**
	 * @param playlistId the playlistId to set
	 */
	public void setPlaylistId(String playlistId) {
		this.playlistId = playlistId;
	}

	/**
	 * @return the includeDate
	 */
	public LocalDate getIncludeDate() {
		return includeDate;
	}

	/**
	 * @param includeDate the includeDate to set
	 */
	public void setIncludeDate(LocalDate includeDate) {
		this.includeDate = includeDate;
	}

	
}
