package br.com.backend.music.streaming.custom.api.domain.spotify;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class containig artists followers data
 * 
 * @author diegovieira
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL) 
public class Followers {

	/**
	 * followers url
	 */
	@JsonProperty("href")
	private String href;

	/**
	 * Number of followers
	 */
	@JsonProperty("total")
	private Integer total;

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
	 * @return the total
	 */
	public Integer getTotal() {
		return total;
	}

	/**
	 * @param total the total to set
	 */
	public void setTotal(Integer total) {
		this.total = total;
	}

}
