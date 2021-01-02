package br.com.backend.music.streaming.custom.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.com.backend.music.streaming.custom.api.domain.spotify.response.TopTracksResponse;
import br.com.backend.music.streaming.custom.api.service.MusicStreamingService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * Classe controller responsável por orquestrar novas operações utilizando uma
 * API de serviço de streaming de música
 * 
 * @author diego.vieira
 *
 */

@RequestMapping("/streaming/api/v0")
@RestController
public class MusicStreamingCustomController {

	@Autowired
	MusicStreamingService musicStreamingService;

	/**
	 * 
	 * @param token
	 * @return
	 */
	@GetMapping("/favorite-tracks")
	@ApiOperation(value = "retorna lista de músicas favoritas")
	@ResponseBody
	public ResponseEntity<TopTracksResponse> findFavoriteTracks(
			@RequestHeader(value = "authorization", required = false)
			@ApiParam("Parâmetro de Token de autenticação. Não é necessário informar, pois o mesmo é extraído do Header da solicitação") 
			String token) {
		try {
			TopTracksResponse response = musicStreamingService.findFavoriteTracks(token);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (JsonProcessingException e) {
			System.out.println("Erro ao processar JSON de retorno da API Spotify");
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
