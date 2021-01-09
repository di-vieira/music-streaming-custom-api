package br.com.backend.music.streaming.custom.api.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
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

import br.com.backend.music.streaming.custom.api.domain.entity.UserPlaylist;
import br.com.backend.music.streaming.custom.api.domain.request.CreatePlaylistRequest;
import br.com.backend.music.streaming.custom.api.domain.response.StreamingResponse;
import br.com.backend.music.streaming.custom.api.domain.response.TracksResponse;
import br.com.backend.music.streaming.custom.api.domain.spotify.Artist;
import br.com.backend.music.streaming.custom.api.domain.spotify.Playlist;
import br.com.backend.music.streaming.custom.api.domain.spotify.Track;
import br.com.backend.music.streaming.custom.api.domain.spotify.User;
import br.com.backend.music.streaming.custom.api.service.MusicStreamingService;
import br.com.backend.music.streaming.custom.api.service.UserPlaylistService;

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
	 * Constant to identify Authorization attribute included in Header
	 */
	private final String AUTHORIZATION = "Authorization";

	/**
	 * Constant to identify the maximum number allowed to retrieve tracks from favorite 
	 * tracks list or favorite artists list
	 */
	private final Integer NUMBER_MAX_OF_TRACKS = 30; 
	
	/**
	 * Minimum value for range used to get random artist top track
	 */
	private final Integer MIN_RANGE = 0;
	
	/**
	 * Maximum value for range used to get random artist top track
	 */
	private final Integer MAX_RANGE = 4;
	
	/**
	 * Query parameter to set the number of entities to return
	 */
	private final String LIMIT = "limit";
	
	/**
	 * Query parameter to set the country of artist's top tracks
	 */
	private final String COUNTRY = "country";
	
	@Autowired
	private UserPlaylistService userPlaylistService;
	
	@Autowired
	private RestTemplate restTemplate;

	private String token;

	/**
	 * Search user's top tracks
	 */
	@SuppressWarnings("unchecked")
	@Override
	public StreamingResponse<Track> findFavoriteTracks() throws JsonProcessingException {
		Map<String, String> queryParameters = new HashMap<String, String>(); 
		queryParameters.put(LIMIT, NUMBER_MAX_OF_TRACKS.toString());
		String uri = buildUriString(spotifyApiUrl + spotifyTopTracks, queryParameters);
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, getHttpEntity(), String.class);
		return parseStreamingResponse(response.getBody(), Track.class);
	}

	/**
	 * Search user's top artists
	 */
	@SuppressWarnings("unchecked")
	@Override
	public StreamingResponse<Artist> findFavoriteArtists() throws JsonProcessingException {
		Map<String, String> queryParameters = new HashMap<String, String>(); 
		queryParameters.put(LIMIT, NUMBER_MAX_OF_TRACKS.toString());
		String uri = buildUriString(spotifyApiUrl + spotifyTopArtists, queryParameters);
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, getHttpEntity(), String.class);
		return parseStreamingResponse(response.getBody(), Artist.class);
	}

	/**
	 * Creates a new playlist in user's spotify account based on user's top tracks and top artists
	 */
	@Override
	public Playlist createPersonalPlaylist(CreatePlaylistRequest request) throws JsonProcessingException {
		List<Track> tracksForPlaylist = getTracksForPlaylist();
		User user = findUser();
		String uri = spotifyApiUrl + spotifyUsers + "/" + user.getId() + spotifyPlaylists;
		ResponseEntity<Playlist> playlistResponse = restTemplate.exchange(uri, HttpMethod.POST, getHttpEntity(request), Playlist.class);
		Playlist playlist = playlistResponse.getBody();
		if (playlistResponse.getStatusCode().equals(HttpStatus.CREATED)) {
			addTracksInPlaylist(playlist.getId(), tracksForPlaylist);
			userPlaylistService.saveUserPlaylist(new UserPlaylist(user.getId(), playlist.getId(), LocalDate.now()));
		}
		return playlist;
	}

	/**
	 * Set authentication token to access external API
	 */
	@Override
	public void setToken(String token) {
		this.token = token;
	}
	
	public void addTracksInPlaylist(String playlistId, List<Track> tracksForPlaylist) {
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(spotifyApiUrl + spotifyPlaylists + "/" + playlistId + spotifyTracks);
		List<String> trackUris = new ArrayList<String>();
		for(Track track : tracksForPlaylist) {
			trackUris.add(track.getURI());
		}
		builder.queryParam("uris", String.join(",", trackUris));
		restTemplate.exchange(builder.toUriString(), HttpMethod.POST, getHttpEntity(), String.class);
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
	private List<Track> findArtistTopTracks(String id) {
		Map<String, String> queryParameters = new HashMap<String, String>(); 
		queryParameters.put(LIMIT, NUMBER_MAX_OF_TRACKS.toString());
		queryParameters.put(COUNTRY, spotifyCountry);
		String uri = buildUriString(spotifyApiUrl + spotifyArtists + "/" + id + spotifyArtistTopTracks, queryParameters);
		ResponseEntity<TracksResponse> response = restTemplate.exchange(uri, HttpMethod.GET, getHttpEntity(), TracksResponse.class);		
		return response.getBody().getTracks();
	}

	/**
	 * Get tracks to add in personal playlist
	 * 
	 * @param artists
	 * @param tracks
	 * @return
	 */
	private List<Track> getTracksForPlaylist() throws JsonProcessingException{
		List<Track> playlistTracks = new ArrayList<Track>();
		playlistTracks.addAll(getTracksFromTopArtistsToPlaylist());
		playlistTracks.addAll(getTracksFromTopTracksToPlaylist());
		return removeDuplicatedTracks(playlistTracks);
	}
	
	
	/**
	 * Get the 
	 * @return
	 * @throws JsonProcessingException
	 */
	private List<Track> getTracksFromTopTracksToPlaylist() throws JsonProcessingException {
		List<Track> playlistTracks = new ArrayList<Track>();
		StreamingResponse<Track> favoriteTracks = findFavoriteTracks();
		for (Track track : favoriteTracks.getItems()) {
			if (!containsArtist(playlistTracks, track.getArtists().get(0))) {
				playlistTracks.add(track);
			}
		}
		return playlistTracks;
	}
	
	/**
	 * @return
	 * @throws JsonProcessingException
	 */
	private List<Track> getTracksFromTopArtistsToPlaylist() throws JsonProcessingException {
		List<Track> playlistTracks = new ArrayList<Track>();
		StreamingResponse<Artist> favoriteArtists = findFavoriteArtists();
		for (Artist artist : favoriteArtists.getItems()) {
			playlistTracks.add(findArtistTopTracks(artist.getId())
					.get((int) (Math.random() * (MAX_RANGE - MIN_RANGE)) + MIN_RANGE));
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
	private Boolean containsArtist(List<Track> playlistTracks, Artist artist) {
		for (Track track : playlistTracks) {
			Comparator<Artist> artistComparator = 
					(Artist artist1, Artist artist2) -> artist1.getId().compareTo(artist.getId());			
			if (artistComparator.compare(track.getArtists().get(0), artist) == 0) {
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
	private Boolean containsTrack(List<Track> playlistTracks, Track trackToAdd) {
		for (Track track : playlistTracks) {
			Comparator<Track> trackComparator =
					(Track track1, Track track2) -> track1.getId().compareTo(track2.getId());
			if (trackComparator.compare(track, trackToAdd) == 0) {
				return Boolean.TRUE;
			}
		}
		return Boolean.FALSE;
	}
	
	private List<Track> removeDuplicatedTracks(List<Track> tracks){
		List<Track> nonDuplicatedTracks = new ArrayList<Track>();
		for(Track track : tracks) {
			if(!containsTrack(nonDuplicatedTracks, track)) {
				nonDuplicatedTracks.add(track);
			}
		}
		return nonDuplicatedTracks;
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
	private <T> StreamingResponse parseStreamingResponse(String json, Class<T> returnType) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		JavaType type = mapper.getTypeFactory().constructParametricType(StreamingResponse.class, returnType);
		mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
		return (StreamingResponse<T>) mapper.readValue(json, type);
	}
	
	/**
	 * @param uri
	 * @param params
	 * @return
	 */
	private String buildUriString(String uri, Map<String, String> params) {
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(uri);
		for(Map.Entry<String, String> param : params.entrySet()) {
			builder.queryParam(param.getKey(), param.getValue());
		}
		return builder.toUriString();
	}

}
