package br.com.backend.music.streaming.custom.api.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import java.nio.file.Files;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.backend.music.streaming.custom.api.domain.entity.Genre;
import br.com.backend.music.streaming.custom.api.domain.entity.UserPlaylist;
import br.com.backend.music.streaming.custom.api.domain.spotify.Artist;
import br.com.backend.music.streaming.custom.api.domain.spotify.CreatePlaylistRequest;
import br.com.backend.music.streaming.custom.api.domain.spotify.Playlist;
import br.com.backend.music.streaming.custom.api.domain.spotify.StreamingResponse;
import br.com.backend.music.streaming.custom.api.domain.spotify.Track;
import br.com.backend.music.streaming.custom.api.domain.spotify.TracksResponse;
import br.com.backend.music.streaming.custom.api.domain.spotify.User;
import br.com.backend.music.streaming.custom.api.service.GenreService;
import br.com.backend.music.streaming.custom.api.service.MusicStreamingService;
import br.com.backend.music.streaming.custom.api.service.UserPlaylistService;

public class SpotifyServiceTest {

	@InjectMocks
	private MusicStreamingService musicStreamingService = new SpotifyService();

	@Mock
	private RestTemplate restTemplate;
	
	@Mock
	private UserPlaylistService userPlaylistService;

	@Mock
	private GenreService genreService;

	private String jsonMockStreamingArtists;
	private String jsonMockStreamingTracks;
	private String jsonMockPlaylist;
	private String jsonMockTracksResponse;
	private String jsonMockArtist;
	
	/**
	 * Inicializa os Mocks
	 */
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		ReflectionTestUtils.setField(musicStreamingService, "spotifyApiUrl", "https://api.spotify.com/v1");
		ReflectionTestUtils.setField(musicStreamingService, "spotifyTopTracks", "/me/top/tracks");
		ReflectionTestUtils.setField(musicStreamingService, "spotifyTopArtists", "/me/top/artists");
		ReflectionTestUtils.setField(musicStreamingService, "spotifyArtists", "/artists");
		ReflectionTestUtils.setField(musicStreamingService, "spotifyArtistTopTracks", "/top-tracks");
		ReflectionTestUtils.setField(musicStreamingService, "spotifyPlaylists", "/playlists");
		ReflectionTestUtils.setField(musicStreamingService, "spotifyMe", "/me");	
		ReflectionTestUtils.setField(musicStreamingService, "spotifyUsers", "/users");	
		ReflectionTestUtils.setField(musicStreamingService, "spotifyTracks", "/tracks");	
		ReflectionTestUtils.setField(musicStreamingService, "spotifyCountry", "BR");	

		jsonMockStreamingTracks = readMockFile("mockStreamingTracks.json");
		jsonMockStreamingArtists = readMockFile("mockStreamingArtists.json");
		jsonMockPlaylist = readMockFile("mockPlaylist.json");
		jsonMockTracksResponse = readMockFile("mockTracksResponse.json");
		jsonMockArtist = readMockFile("mockArtist.json");

	}
	
	public String readMockFile(String mockFile) {
		String json = "";
		try {
			json = new String(Files.readAllBytes(ResourceUtils.getFile("classpath:" + mockFile).toPath()));
		} catch (FileNotFoundException e){
			fail("Arquivo mock não existente: " + mockFile);
		} catch (IOException e){
		
		}			
		return json;
	}
	
	final Integer NUMBER_MAX_OF_TRACKS = 30;
	
	/**
	 * Teste para o método que consulta a API do Spotify e retorna as faixas
	 * favoritas do usuário O teste é feito retornando um Mock de um JSON Válido
	 */
	@Test
	public void findFavoriteTracks() {
		
		StreamingResponse<Track> response = new StreamingResponse<Track>();
		try {
			when(restTemplate.exchange(anyString(), any(HttpMethod.class), any(HttpEntity.class), eq(String.class)))
					.thenReturn(new ResponseEntity<String>(jsonMockStreamingTracks, HttpStatus.OK));
			response = musicStreamingService.findFavoriteTracks();

			assertEquals(response.getItems().size(), 20);
			assertEquals(response.getItems().get(0).getAvailableMarkets().size(), 76);

			assertEquals(response.getItems().get(0).getAlbum().getAlbumType(), "ALBUM");
			assertEquals(response.getItems().get(0).getAlbum().getArtists().size(), 1);
			assertEquals(response.getItems().get(0).getAlbum().getAvailableMarkets().size(), 76);
			assertEquals(response.getItems().get(0).getAlbum().getHref(),
					"https://api.spotify.com/v1/albums/1PuCoaLQNyCeaLBgGtNyW3");
			assertEquals(response.getItems().get(0).getAlbum().getId(), "1PuCoaLQNyCeaLBgGtNyW3");
			assertEquals(response.getItems().get(0).getAlbum().getImages().get(0).getHeight(), Integer.valueOf(640));
			assertEquals(response.getItems().get(0).getAlbum().getImages().get(0).getUrl(),
					"https://i.scdn.co/image/ab67616d0000b273c6fb913ce65ee5844f3ece58");
			assertEquals(response.getItems().get(0).getAlbum().getImages().get(0).getWidth(), Integer.valueOf(640));
			assertEquals(response.getItems().get(0).getAlbum().getName(), "With Teeth (Bonus Tracks)");
			assertEquals(response.getItems().get(0).getAlbum().getReleaseDate(), "2005-05-03");
			assertEquals(response.getItems().get(0).getAlbum().getReleaseDatePrecision(), "day");
			assertEquals(response.getItems().get(0).getAlbum().getTotalTracks(), Integer.valueOf(15));
			assertEquals(response.getItems().get(0).getAlbum().getType(), "album");
			assertEquals(response.getItems().get(0).getAlbum().getUri(), "spotify:album:1PuCoaLQNyCeaLBgGtNyW3");

			assertEquals(response.getItems().get(0).getArtists().size(), 1);
			assertEquals(response.getItems().get(0).getArtists().get(0).getExternalUrls().getSpotify(),
					"https://open.spotify.com/artist/0X380XXQSNBYuleKzav5UO");
			assertEquals(response.getItems().get(0).getArtists().get(0).getHref(),
					"https://api.spotify.com/v1/artists/0X380XXQSNBYuleKzav5UO");
			assertEquals(response.getItems().get(0).getArtists().get(0).getId(), "0X380XXQSNBYuleKzav5UO");
			assertEquals(response.getItems().get(0).getArtists().get(0).getName(), "Nine Inch Nails");
			assertEquals(response.getItems().get(0).getArtists().get(0).getType(), "artist");
			assertEquals(response.getItems().get(0).getArtists().get(0).getUri(),
					"spotify:artist:0X380XXQSNBYuleKzav5UO");

			assertEquals(response.getItems().get(0).getDiscNumber(), Integer.valueOf(1));
			assertEquals(response.getItems().get(0).getDurationMs(), Integer.valueOf(243160));
			assertEquals(response.getItems().get(0).getExplicit(), Boolean.TRUE);
			assertEquals(response.getItems().get(0).getExternalIds().getIsrc(), "USIR10500490");
			assertEquals(response.getItems().get(0).getHref(),
					"https://api.spotify.com/v1/tracks/56kUvKRD65pvGCaCZcnaBx");
			assertEquals(response.getItems().get(0).getId(), "56kUvKRD65pvGCaCZcnaBx");
			assertEquals(response.getItems().get(0).getIsLocal(), Boolean.FALSE);
			assertEquals(response.getItems().get(0).getName(), "Sunspots");
			assertEquals(response.getItems().get(0).getPopularity(), Integer.valueOf(41));
			assertEquals(response.getItems().get(0).getPreviewUrl(),
					"https://p.scdn.co/mp3-preview/e05d8e198303f830a5a7a7d214aeb1e9889d0c9f?cid=08c5fd71eb9d45fd9cf760e8d0d62040");
			assertEquals(response.getItems().get(0).getTrackNumber(), Integer.valueOf(10));
			assertEquals(response.getItems().get(0).getType(), "track");
			assertEquals(response.getItems().get(0).getURI(), "spotify:track:56kUvKRD65pvGCaCZcnaBx");

			assertEquals(response.getTotal(), Integer.valueOf(50));
			assertEquals(response.getLimit(), Integer.valueOf(20));
			assertEquals(response.getOffset(), Integer.valueOf(0));
			assertEquals(response.getHref(), "https://api.spotify.com/v1/me/top/tracks");
			assertNull(response.getPrevious());
			assertEquals(response.getNext(), "https://api.spotify.com/v1/me/top/tracks?limit=20&offset=20");

		} catch (Exception e) {
			fail("Erro na execução do teste");
		}
	}

	/**
	 * Teste do Fluxo de Exceção de parse do JSON na busca de top-tracks. Utiliza um
	 * JSON vazio
	 */
//	@Test
//	public void findFavoriteTracksJsonProcessingExceptionTest() {
//		StreamingResponse<Track> response = new StreamingResponse<Track>();
//		try {
//			when(restTemplate.exchange(anyString(), any(HttpMethod.class), any(HttpEntity.class), eq(String.class)))
//					.thenReturn(new ResponseEntity<String>("", HttpStatus.BAD_REQUEST));
//			response = musicStreamingService.findFavoriteTracks();
//			fail("Método deveria ter caído no fluxo de exceção");
//		} catch (Exception e) {
//			fail("Erro na execução do teste");
//		}
//	}

	@Test
	public void findFavoriteArtistsTest() {
		
		StreamingResponse<Artist> response = new StreamingResponse<Artist>();
		try {
			when(restTemplate.exchange(anyString(), any(HttpMethod.class), any(HttpEntity.class), eq(String.class))).thenReturn(new ResponseEntity<String>(jsonMockStreamingArtists, HttpStatus.OK));
			response = musicStreamingService.findFavoriteArtists();

			assertEquals(response.getItems().size(), 1);
			assertEquals(response.getItems().get(0).getExternalUrls().getSpotify(),	"https://open.spotify.com/artist/0X380XXQSNBYuleKzav5UO");
			assertEquals(response.getItems().get(0).getHref(), "https://api.spotify.com/v1/artists/0X380XXQSNBYuleKzav5UO");
			assertEquals(response.getItems().get(0).getId(), "0X380XXQSNBYuleKzav5UO");
			assertEquals(response.getItems().get(0).getName(), "Nine Inch Nails");
			assertEquals(response.getItems().get(0).getType(), "artist");
			assertEquals(response.getItems().get(0).getUri(), "spotify:artist:0X380XXQSNBYuleKzav5UO");

			assertEquals(response.getItems().get(0).getPopularity(), Integer.valueOf(69));
			assertEquals(response.getItems().get(0).getType(), "artist");

			assertEquals(response.getTotal(), Integer.valueOf(50));
			assertEquals(response.getLimit(), Integer.valueOf(20));
			assertEquals(response.getOffset(), Integer.valueOf(0));
			assertEquals(response.getHref(), "https://api.spotify.com/v1/me/top/artists");
			assertNull(response.getPrevious());
			assertEquals(response.getNext(), "https://api.spotify.com/v1/me/top/artists?limit=20&offset=20");

		} catch (Exception e) {
			fail("Erro na execução do teste");
		}
	}

	@Test
	public void createPersonalPlaylistTest() {
		
		try {
			User user = new User();
			user.setId("12150045193");
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
			Playlist playlist = mapper.readValue(jsonMockPlaylist, Playlist.class);
			TracksResponse mockTracksResponse = mapper.readValue(jsonMockTracksResponse, TracksResponse.class);
			CreatePlaylistRequest request = new CreatePlaylistRequest();
			request.setCollaborative(Boolean.FALSE);
			request.setDescription("teste");
			request.setIsPublic(Boolean.FALSE);
			request.setName("teste");
			
			HttpHeaders headers = new HttpHeaders();
			String token = "teste";

			// Set parameters MediaType and authentication token
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set("Authorization", token);
			musicStreamingService.setToken(token);

			when(restTemplate.exchange(anyString(), any(HttpMethod.class), any(HttpEntity.class), eq(Playlist.class)))
					.thenReturn(new ResponseEntity<Playlist>(playlist, HttpStatus.CREATED));
			
			when(restTemplate.exchange("https://api.spotify.com/v1/users/12150045193/playlists", HttpMethod.POST, 
					new HttpEntity<Object>(request, headers), String.class)).thenReturn(new ResponseEntity<String>(
							"{ \"snaphot_id\": \"JbtmHBDBAYu3/bt8BOXKjzKx3i0b6LCa/wVjyl6qQ2Yf6nFXkbmzuEa+ZI/U1yF+\"}",
							HttpStatus.OK));
			
			when(restTemplate.exchange("https://api.spotify.com/v1/me/top/artists?limit=" + NUMBER_MAX_OF_TRACKS, HttpMethod.GET, 
					new HttpEntity<Object>(headers), String.class)).thenReturn(
							new ResponseEntity<String>(jsonMockStreamingArtists, HttpStatus.OK));
	
			when(restTemplate.exchange("https://api.spotify.com/v1/me/top/tracks?limit=" + NUMBER_MAX_OF_TRACKS, HttpMethod.GET, 
					new HttpEntity<Object>(headers), String.class)).thenReturn(
							new ResponseEntity<String>(jsonMockStreamingTracks, HttpStatus.OK));
	
			when(restTemplate.exchange("https://api.spotify.com/v1/me", HttpMethod.GET, new HttpEntity<Object>(headers), User.class))
			.thenReturn(new ResponseEntity<User>(user, HttpStatus.OK));
			
			when(restTemplate.exchange(anyString(), any(HttpMethod.class), any(HttpEntity.class), eq(TracksResponse.class)))
			.thenReturn(new ResponseEntity<TracksResponse>(mockTracksResponse, HttpStatus.OK));
			
			doNothing().when(userPlaylistService).saveUserPlaylist(any(UserPlaylist.class));
			
			Playlist response = musicStreamingService.createPersonalPlaylist("");
			
			assertNotNull(response);
		} catch (JsonProcessingException e) {
			fail("Erro no parse do JSON " + e.getMessage());
		}

	}
	
	@Test
	public void findHowBadIsYourMusicalTasteTest() {
		
		Artist artist = new Artist();
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
		try {
			artist = mapper.readValue(jsonMockArtist, Artist.class);
		} catch (JsonProcessingException e) {
			fail("Erro durante parse do Json de artista para teste");
		}
		
		
		HttpHeaders headers = new HttpHeaders();
		String token = "teste";

		// Set parameters MediaType and authentication token
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", token);
		musicStreamingService.setToken(token);

		when(restTemplate.exchange("https://api.spotify.com/v1/me/top/artists?limit=" + NUMBER_MAX_OF_TRACKS, HttpMethod.GET, 
				new HttpEntity<Object>(headers), String.class)).thenReturn(
						new ResponseEntity<String>(jsonMockStreamingArtists, HttpStatus.OK));

		when(restTemplate.exchange("https://api.spotify.com/v1/me/top/tracks?limit=" + NUMBER_MAX_OF_TRACKS, HttpMethod.GET, 
				new HttpEntity<Object>(headers), String.class)).thenReturn(
						new ResponseEntity<String>(jsonMockStreamingTracks, HttpStatus.OK));
		
		when(restTemplate.exchange(anyString(), any(HttpMethod.class), any(HttpEntity.class), eq(Artist.class)))
		.thenReturn(new ResponseEntity<Artist>(artist, HttpStatus.OK));
		
		List<Genre> mockedGenres = new ArrayList<Genre>();		
		mockedGenres.add(new Genre(1, "pop", "N"));
		mockedGenres.add(new Genre(2, "dance pop", "N"));
		mockedGenres.add(new Genre(6, "rock", "N"));
		mockedGenres.add(new Genre(74, "sertanejo universitario", "S"));
		mockedGenres.add(new Genre(75, "funk carioca", "S"));
		
		Mockito.when(genreService.findBlacklistedGenres()).thenReturn(mockedGenres);
		
		String response = musicStreamingService.findHowBadIsYourMusicalTaste();
		
		assertNotNull(response);
		
	}

}
