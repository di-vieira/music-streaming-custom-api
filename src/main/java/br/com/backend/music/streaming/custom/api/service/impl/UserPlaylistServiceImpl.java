package br.com.backend.music.streaming.custom.api.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.backend.music.streaming.custom.api.domain.entity.UserPlaylist;
import br.com.backend.music.streaming.custom.api.repository.UserPlaylistRepository;
import br.com.backend.music.streaming.custom.api.service.UserPlaylistService;

@Service
public class UserPlaylistServiceImpl implements UserPlaylistService{

	@Autowired
	UserPlaylistRepository userPlaylistRepository;
	
	@Override
	public List<UserPlaylist> listAllUserPlaylists() {
		return userPlaylistRepository.findAll();
	}

	@Override
	public UserPlaylist findUserPlaylistById(String id) {
		Optional<UserPlaylist> userPlaylist = userPlaylistRepository.findById(id);
		return userPlaylist.get();
	}

	@Override
	public void saveUserPlaylist(UserPlaylist userPlaylist) {
		userPlaylistRepository.save(userPlaylist);
	}

	@Override
	public void updateUserPlaylist(UserPlaylist userPlaylist) {
		userPlaylistRepository.save(userPlaylist);
	}

	@Override
	public void deleteUserPlaylist(String id) {
		userPlaylistRepository.deleteById(id);		
	}

}
