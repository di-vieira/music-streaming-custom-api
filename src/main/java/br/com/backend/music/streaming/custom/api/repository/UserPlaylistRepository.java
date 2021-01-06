package br.com.backend.music.streaming.custom.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.backend.music.streaming.custom.api.domain.entity.UserPlaylist;

@Repository
public interface UserPlaylistRepository extends JpaRepository<UserPlaylist, String>{
 
}
