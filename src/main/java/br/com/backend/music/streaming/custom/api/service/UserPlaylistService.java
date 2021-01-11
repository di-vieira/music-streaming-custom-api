package br.com.backend.music.streaming.custom.api.service;

import java.util.List;

import br.com.backend.music.streaming.custom.api.domain.entity.UserPlaylist;

public interface UserPlaylistService {

	/**
	 * Returns a list containing all personal playlists and their respective users
	 * @return the user playlists 
	 */
	public List<UserPlaylist> findAllUserPlaylists();
	
	/**
	 * Find user's personal playlist
	 * @param id user id
	 * @return
	 */
	public UserPlaylist findUserPlaylistById(String id);
	
	/**
	 * Creates a new user playlist
	 * @param genre
	 */
	public void saveUserPlaylist(UserPlaylist userPlaylist);
	
	/**
	 * Update an existing user playlist
	 * @param genre
	 */
	public void updateUserPlaylist(UserPlaylist userPlaylist);
	
	/**
	 * remove a user playlist from entity
	 * @param id
	 */
	public void deleteUserPlaylist(String id);
}
