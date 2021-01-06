package br.com.backend.music.streaming.custom.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.backend.music.streaming.custom.api.domain.entity.Genre;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Integer>{

	public List<Genre> findByInBlacklist(String inBlacklist);
}
