package br.com.backend.music.streaming.custom.api.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.backend.music.streaming.custom.api.domain.spotify.Artist;
import br.com.backend.music.streaming.custom.api.domain.spotify.Playlist;
import br.com.backend.music.streaming.custom.api.domain.spotify.StreamingResponse;
import br.com.backend.music.streaming.custom.api.domain.spotify.Track;
import br.com.backend.music.streaming.custom.api.service.MusicStreamingService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * Controller class responsible to manage new operations using streaming services APIs
 * 
 * @author diego.vieira
 *
 */
@RequestMapping("/streaming/api")
@RestController
public class MusicStreamingController {

	@Autowired
	MusicStreamingService musicStreamingService;

	Logger logger = LoggerFactory.getLogger(MusicStreamingController.class);

	@GetMapping("/favorite-tracks")
	@ApiOperation(value = "retorna objeto com lista de músicas favoritas do usuário")
	@ResponseBody
	public ResponseEntity<StreamingResponse<Track>> findFavoriteTracks(
			@RequestHeader(value = "authorization", required = false) @ApiParam("Parâmetro de Token de autenticação. Não é necessário informar, pois o mesmo é extraído do Header da solicitação") String token) {
		try {
			musicStreamingService.setToken(token);
			StreamingResponse<Track> response = musicStreamingService.findFavoriteTracks();
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Erro ao executar criação de playlist personalizada");
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/favorite-artists")
	@ApiOperation(value = "retorna objeto com lista de artistas favoritos do usuário")
	@ResponseBody
	public ResponseEntity<StreamingResponse<Artist>> findFavoriteArtists(
			@RequestHeader(value = "authorization", required = false) @ApiParam("Parâmetro de Token de autenticação. Não é necessário informar, pois o mesmo é extraído do Header da solicitação") String token) {
		try {
			musicStreamingService.setToken(token);
			StreamingResponse<Artist> response = musicStreamingService.findFavoriteArtists();
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Erro ao executar criação de playlist personalizada");
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@PostMapping("/create-personal-playlist")
	@ApiOperation(value = "Cria playlist com base nos artistas e músicas favoritas do usuário")
	public ResponseEntity<Playlist> createPersonalPlaylist(
			@RequestHeader(value = "authorization", required = false) @ApiParam("Parâmetro de Token de autenticação. Não é necessário informar, pois o mesmo é extraído do Header da solicitação") String token,
			@RequestParam("Nome da Playlist que será criada") String playlistName) {
		try {
			musicStreamingService.setToken(token);
			Playlist playlist = musicStreamingService.createPersonalPlaylist(playlistName);
			return new ResponseEntity<>(playlist, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Erro ao executar criação de playlist personalizada");
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/musical-taste")
	@ApiOperation(value = "Analisa os gêneros que o usuário ouve e diz o quão ruim é o seu gosto musical")
	public ResponseEntity<String> findHowBadIsYourMusicalTaste(
			@RequestHeader(value = "authorization", required = false) @ApiParam("Parâmetro de Token de autenticação. Não é necessário informar, pois o mesmo é extraído do Header da solicitação") String token) {
		try {
			musicStreamingService.setToken(token);
			String playlist = musicStreamingService.findHowBadIsYourMusicalTaste();
			return new ResponseEntity<>(playlist, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Erro ao executar criação de playlist personalizada");
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
