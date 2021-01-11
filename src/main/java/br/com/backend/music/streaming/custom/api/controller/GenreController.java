package br.com.backend.music.streaming.custom.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.backend.music.streaming.custom.api.domain.entity.Genre;
import br.com.backend.music.streaming.custom.api.service.GenreService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RequestMapping("/streaming/api")
@RestController
public class GenreController {

	@Autowired
	GenreService genreService;

	@GetMapping("/genres")
	@ApiOperation("Busca todos os Gêneros cadastrados")
	@ResponseBody
	public List<Genre> findAllGenres() {
		return genreService.findAllGenres();
	}

	@GetMapping("/genres/{id}")
	@ApiOperation("Busca um gênero pelo seu Id")
	@ResponseBody
	public Genre findGenreById(@PathVariable @ApiParam("ID do gênero que será pesquisado") Integer id) {
		return genreService.findGenreById(id);
	}

	@GetMapping("/genres/blacklist")
	@ApiOperation("Busca a lista de gêneros de gosto duvidoso...")
	@ResponseBody
	public List<Genre> findBlacklistedGenres() {
		return genreService.findBlacklistedGenres();
	}

	@PostMapping("/genres")
	@ApiOperation("Cria um novo gênero")
	@ResponseBody
	public void createGenre(Genre genre) {
		genreService.saveGenre(genre);
	}

	@PutMapping("/genres/{id}")
	@ApiOperation("Atualiza um gênero previamente cadastrado")
	@ResponseBody
	public void updateGenre(@PathVariable @ApiParam("ID do gênero que será atualizado") Integer id, @RequestBody Genre genre) {
		genreService.updateGenre(id, genre);
	}

	@DeleteMapping("/genres/{id}")
	@ApiOperation("Deleta um gênero cadastrado")
	@ResponseBody
	public void deleteGenre(@PathVariable @ApiParam("ID do gênero que será excluído") Integer id) {
		genreService.deleteGenreById(id);
	}

}
