package br.com.backend.music.streaming.custom.api.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiParam;

/**
 * Entity to represents table GENRE
 * @author diegovieira
 *
 */
@Entity
@Table(name = "GENRE")
public class Genre {

	/**
	 * Genre ID
	 */
	@Id
	@ApiParam("Id do Gênero")
	@Column(name = "ID_GENRE")
	private Integer genreId;

	/**
	 * 
	 */
	@ApiParam("Nome do Gênero")
	@Column(name = "NM_GENRE")
	private String genreName;

	/**
	 * Flag that indicate if that genre is a bad taste genre ;)
	 */
	@ApiParam("Flag que indica se o gênero está na blacklist (Valores possíveis: S ou N)")
	@Column(name = "IN_BLACKLIST")
	private String inBlacklist;

	/**
	 * @return the genreId
	 */
	public Integer getGenreId() {
		return genreId;
	}

	/**
	 * @param genreId the genreId to set
	 */
	public void setGenreId(Integer genreId) {
		this.genreId = genreId;
	}

	/**
	 * @return the genreName
	 */
	public String getGenreName() {
		return genreName;
	}

	/**
	 * @param genreName the genreName to set
	 */
	public void setGenreName(String genreName) {
		this.genreName = genreName;
	}

	/**
	 * @return the inBlacklist
	 */
	public String getInBlacklist() {
		return inBlacklist;
	}

	/**
	 * @param inBlacklist the inBlacklist to set
	 */
	public void setInBlacklist(String inBlacklist) {
		this.inBlacklist = inBlacklist;
	}

}
