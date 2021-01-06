package br.com.backend.music.streaming.custom.api.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.backend.music.streaming.custom.api.domain.request.CreatePlaylistRequest;
import br.com.backend.music.streaming.custom.api.domain.response.StreamingResponse;
import br.com.backend.music.streaming.custom.api.domain.response.TracksResponse;
import br.com.backend.music.streaming.custom.api.domain.spotify.Artist;
import br.com.backend.music.streaming.custom.api.domain.spotify.Playlist;
import br.com.backend.music.streaming.custom.api.domain.spotify.Track;
import br.com.backend.music.streaming.custom.api.domain.spotify.User;
import br.com.backend.music.streaming.custom.api.service.MusicStreamingService;

@Service
public class SpotifyService implements MusicStreamingService {

	/**
	 * URL da API do Spotify
	 */
	@Value("${spotify.api.url}")
	private String spotifyApiUrl;

	/**
	 * URN para formar o endpoint de consulta de Top Tracks
	 */
	@Value("${spotify.api.top.tracks}")
	private String spotifyTopTracks;

	/**
	 * URN para formar o endpoint de consulta de Top Artists
	 */
	@Value("${spotify.api.top.artists}")
	private String spotifyTopArtists;

	/**
	 * URN para format o endpoint de consulta de Artistas
	 */
	@Value("${spotify.api.artists}")
	private String spotifyArtists;

	/**
	 * URN to get artist's top tracks endpoint
	 */
	@Value("${spotify.api.topTracks}")
	private String spotifyArtistTopTracks;

	/**
	 * URN to get artist's top tracks endpoint
	 */
	@Value("${spotify.api.playlists}")
	private String spotifyPlaylists;

	/**
	 * URN to get logged user data endpoint
	 */
	@Value("${spotify.api.me}")
	private String spotifyMe;

	/**
	 * URN to get users endpoint
	 */
	@Value("${spotify.api.users}")
	private String spotifyUsers;
	
	/**
	 * URN to get tracks endpoint
	 */
	@Value("${spotify.api.tracks}")
	private String spotifyTracks;
	
	/**
	 * Country Query Parameter value
	 */
	@Value("${spotify.api.params.country}")
	private String spotifyCountry;

	/**
	 * Constante para propriedade de do Token de Autorização incluído no Header
	 */
	private final String AUTHORIZATION = "Authorization";

	@Autowired
	private RestTemplate restTemplate;

	private String token;

	/**
	 * Search user's top tracks
	 */
	@SuppressWarnings("unchecked")
	@Override
	public StreamingResponse<Track> findFavoriteTracks() throws JsonProcessingException {

		// Consulta a API do Spotify para retornar as top tracks do usuário logado
		ResponseEntity<String> response = restTemplate.exchange(spotifyApiUrl + spotifyTopTracks, HttpMethod.GET,
				getHttpEntity(), String.class);

		return parseStreamingResponse(response.getBody(), Track.class);
	}

	/**
	 * Search user's top artists
	 */
	@SuppressWarnings("unchecked")
	@Override
	public StreamingResponse<Artist> findFavoriteArtists() throws JsonProcessingException {

		// Consulta a API do Spotify para retornar os top artistas do usuário logado
		ResponseEntity<String> response = restTemplate.exchange(spotifyApiUrl + spotifyTopArtists, HttpMethod.GET,
				getHttpEntity(), String.class);

		return parseStreamingResponse(response.getBody(), Artist.class);
	}

	@Override
	public Playlist createPersonalPlaylist(CreatePlaylistRequest request) throws JsonProcessingException {

		// find Top Artists
		StreamingResponse<Artist> favoriteArtists = findFavoriteArtists();

		// find Top Tracks
		StreamingResponse<Track> favoriteTracks = findFavoriteTracks();

		// Get User data
		User user = findUser();

		// Busca Gêneros de maior evidência
		// List<Entry<String, Integer>> genres = genresCount(favoriteArtists.getItems(),
		// favoriteTracks.getItems());

		// Generate a list of tracks to include in playlist
		Set<Track> tracksForPlaylist = getTracksForPlaylist(favoriteArtists.getItems(), favoriteTracks.getItems());

		// Generates URI to call service to create playlist
		String uri = spotifyApiUrl + spotifyUsers + "/" + user.getId() + spotifyPlaylists;
		
		// Creates Playlist
		ResponseEntity<Playlist> playlistResponse = restTemplate.exchange(uri, HttpMethod.POST, getHttpEntity(request), Playlist.class);

		// Add tracks based on top tracks an top artists from user
		if (playlistResponse.getStatusCode().equals(HttpStatus.CREATED)) {
			UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(spotifyApiUrl + spotifyPlaylists + "/" + playlistResponse.getBody().getId() + spotifyTracks);
			List<String> trackUris = new ArrayList<String>();
			for(Track track : tracksForPlaylist) {
				trackUris.add(track.getURI());
			}
			builder.queryParam("uris", String.join(",", trackUris));
			restTemplate.exchange(builder.toUriString(), HttpMethod.POST, getHttpEntity(), String.class);
		}
		
		return playlistResponse.getBody();
	}

	/**
	 * Set authentication token to access external API
	 */
	@Override
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * @return User
	 */
	public User findUser() {

		return restTemplate.exchange(spotifyApiUrl + spotifyMe, HttpMethod.GET, getHttpEntity(), User.class).getBody();
	}

	/**
	 * Find artist by ID
	 * 
	 * @param id
	 * @return
	 */
	public Artist findArtistById(String id) {

		return restTemplate.exchange(spotifyApiUrl + spotifyArtists + "/" + id, HttpMethod.GET, getHttpEntity(), Artist.class).getBody();
	}

	/**
	 * @param id
	 * @return
	 */
	public List<Track> findArtistTopTracks(String id) throws JsonProcessingException{
		String uri = spotifyApiUrl + spotifyArtists + "/" + id + spotifyArtistTopTracks;
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(uri);
		builder.queryParam("country", spotifyCountry);

		ResponseEntity<TracksResponse> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, getHttpEntity(), TracksResponse.class);		
		
		return response.getBody().getTracks();
	}

	/**
	 * Get tracks to add in personal playlist
	 * 
	 * @param artists
	 * @param tracks
	 * @return
	 */
	public Set<Track> getTracksForPlaylist(List<Artist> artists, List<Track> tracks) throws JsonProcessingException{

		Set<Track> playlistTracks = new HashSet<Track>();
		int countTracks = 0;
		// Includes up to 20 tracks from different artists from top tracks
		for (Track track : tracks) {
			// Verifies if there's some track from same artist in playlist
			if (!containsArtist(playlistTracks, track.getArtists().get(0))) {
				playlistTracks.add(track);
				countTracks++;
				if (countTracks >= 20) {
					break;
				}
			}
		}

		countTracks = 0;
		// Includes up to 20 tracks from artist's top tracks
		for (Artist artist : artists) {
			List<Track> artistTopTracks = findArtistTopTracks(artist.getId());
			for (Track artistTrack : artistTopTracks) {
				// Verifies if the track is duplicated in playlist
				if (!containsTrack(playlistTracks, artistTrack)) {
					playlistTracks.add(artistTrack);
					countTracks++;
					break;
				}
			}
			if (countTracks >= 20) {
				break;
			}
		}

		return playlistTracks;
	}

	/**
	 * Verifies if Set of tracks contains at least one track from an especific
	 * artist
	 * 
	 * @param playlistTracks
	 * @param artist
	 * @return
	 */
	public Boolean containsArtist(Set<Track> playlistTracks, Artist artist) {
		for (Track track : playlistTracks) {
			if (track.getArtists().contains(artist)) {
				return Boolean.TRUE;
			}
		}
		return Boolean.FALSE;
	}

	/**
	 * Verifies if Set of tracks contains at least one occurrence of track
	 * 
	 * @param playlistTracks
	 * @param trackToAdd
	 * @return
	 */
	public Boolean containsTrack(Set<Track> playlistTracks, Track trackToAdd) {
		for (Track track : playlistTracks) {
			if (track.equals(trackToAdd)) {
				return Boolean.TRUE;
			}
		}

		return Boolean.FALSE;
	}

	/**
	 * Retorna as ocorrencias de cada gênero, dentro dos artistas e faixas favoritas
	 * do usuário
	 * 
	 * @param artists
	 * @param tracks
	 * @return
	 */
	public List<Entry<String, Integer>> genresCount(List<Artist> artists, List<Track> tracks) {
		Map<String, Integer> ocurrenciesByGenre = new TreeMap<String, Integer>();

		List<String> genres = new ArrayList<String>();
		genres.addAll(getGenresByTopArtists(artists));
		genres.addAll(getGenresByTopTracks(tracks));

		Set<String> distinctGenres = new HashSet<>(genres);
		for (String genre : distinctGenres) {
			ocurrenciesByGenre.put(genre, Collections.frequency(genres, genre));
		}

		// Ordena lista de gêneros
		List<Entry<String, Integer>> sortedGenres = new ArrayList<Entry<String, Integer>>(
				ocurrenciesByGenre.entrySet());
		Collections.sort(sortedGenres, new Comparator<Entry<String, Integer>>() {
			@Override
			public int compare(Entry<String, Integer> genre1, Entry<String, Integer> genre2) {
				return genre2.getValue().compareTo(genre1.getValue());
			}
		});
		return sortedGenres;
	}

	/**
	 * Retorna lista com todos os gêneros contidos dos top artistas do usuário
	 * 
	 * @param artists
	 * @return
	 */
	public List<String> getGenresByTopArtists(List<Artist> artists) {
		List<String> genres = new ArrayList<String>();

		for (Artist artist : artists) {
			genres.addAll(artist.getGenres());
		}

		return genres;
	}

	/**
	 * Retorna Lista com todos os gêneros contidos nas top tracks do usuário
	 * 
	 * @param tracks
	 * @return
	 */
	public List<String> getGenresByTopTracks(List<Track> tracks) {
		List<String> genres = new ArrayList<String>();

		for (Track track : tracks) {
			List<Artist> artists = track.getArtists();

			for (Artist artist : artists) {
				artist = findArtistById(artist.getId());
				genres.addAll(artist.getGenres());
			}
		}

		return genres;
	}

	/**
	 * Creates a HTTP Entity to consume Spotify API
	 * 
	 * @param token
	 * @return HttpEntity<String>
	 */
	private HttpEntity<Object> getHttpEntity(Object body) {
		// Creates header to set the authentication token
		HttpHeaders headers = new HttpHeaders();

		// Set parameters MediaType and authentication token
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set(AUTHORIZATION, token);
		
		if(body != null) {
			return new HttpEntity<Object>(body, headers);
		}

		return new HttpEntity<Object>(headers);
	}
	
	/**
	 * Creates a HTTP Entity to consume Spotify API
	 * 
	 * @param token
	 * @return HttpEntity<String>
	 */
	private HttpEntity<Object> getHttpEntity() {
		return getHttpEntity(null);
	}

	/**
	 * Serializes JSON response into a StreamingResponse<T> object
	 * 
	 * @param <T>        List type from StreamingResponse (Ex. Artists or Tracks)
	 * @param json       JSON string that will be converted into a StreamingResponse
	 * @param returnType StreamingResponse's content type
	 * @return StreamingResponse<T>
	 * @throws JsonProcessingException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private <T> StreamingResponse parseStreamingResponse(String json, Class<T> returnType)
			throws JsonProcessingException {

		ObjectMapper mapper = new ObjectMapper();
		JavaType type = mapper.getTypeFactory().constructParametricType(StreamingResponse.class, returnType);
		mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);

		return (StreamingResponse<T>) mapper.readValue(json, type);
	}

}
