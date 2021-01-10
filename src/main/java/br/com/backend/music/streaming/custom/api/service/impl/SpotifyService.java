package br.com.backend.music.streaming.custom.api.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

@Service
public class SpotifyService implements MusicStreamingService {

	@Autowired
	private UserPlaylistService userPlaylistService;
	
	@Autowired
	private GenreService genreService;
	
	@Autowired
	private RestTemplate restTemplate;
	
	private Logger logger = LoggerFactory.getLogger(SpotifyService.class);

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
	
	private String token;

	/**
	 * Search user's top tracks
	 */
	@SuppressWarnings("unchecked")
	@Override
	public StreamingResponse<Track> findFavoriteTracks() {
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(spotifyApiUrl + spotifyTopTracks);
		builder.queryParam(LIMIT, NUMBER_MAX_OF_TRACKS.toString());
		ResponseEntity<String> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, getHttpEntity(), String.class);
		return parseStreamingResponse(response.getBody(), Track.class);
	}

	/**
	 * Search user's top artists
	 */
	@SuppressWarnings("unchecked")
	@Override
	public StreamingResponse<Artist> findFavoriteArtists() {
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(spotifyApiUrl + spotifyTopArtists);
		builder.queryParam(LIMIT, NUMBER_MAX_OF_TRACKS.toString());
		ResponseEntity<String> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, getHttpEntity(), String.class);
		return parseStreamingResponse(response.getBody(), Artist.class);
	}

	/**
	 * Creates a new playlist in user's spotify account based on user's top tracks and top artists
	 */
	@Override
	public Playlist createPersonalPlaylist(CreatePlaylistRequest request) {
		User user = findUser();
		String uri = spotifyApiUrl + spotifyUsers + "/" + user.getId() + spotifyPlaylists;
		ResponseEntity<Playlist> playlistResponse = restTemplate.exchange(uri, HttpMethod.POST, getHttpEntity(request), Playlist.class);
		Playlist playlist = playlistResponse.getBody();
		if (playlistResponse.getStatusCode().equals(HttpStatus.CREATED)) {
			addTracksInPersonalPlaylist(playlist.getId());
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
	
	public void addTracksInPersonalPlaylist(String playlistId) {
		List<Track> tracksForPlaylist = getTracksForPlaylist();
		List<String> trackUris = new ArrayList<String>();
		for(Track track : tracksForPlaylist) {
			trackUris.add(track.getURI());
		}
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(spotifyApiUrl + spotifyPlaylists + "/" + playlistId + spotifyTracks);
		builder.queryParam("uris", String.join(",", trackUris));
		restTemplate.exchange(builder.toUriString(), HttpMethod.POST, getHttpEntity(), String.class);
	}

	/**
	 * @return User
	 */
	private User findUser() {
		return restTemplate.exchange(spotifyApiUrl + spotifyMe, HttpMethod.GET, getHttpEntity(), User.class).getBody();
	}

	/**
	 * Find artist by ID
	 * 
	 * @param id
	 * @return
	 */
	private Artist findArtistById(String id) {
		return restTemplate.exchange(spotifyApiUrl + spotifyArtists + "/" + id, HttpMethod.GET, getHttpEntity(), Artist.class).getBody();
	}

	/**
	 * @param id
	 * @return
	 */
	private List<Track> findArtistTopTracks(String id) {
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(spotifyApiUrl + spotifyArtists + "/" + id + spotifyArtistTopTracks);
		builder.queryParam(LIMIT, NUMBER_MAX_OF_TRACKS.toString());
		builder.queryParam(COUNTRY, spotifyCountry);
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
	private List<Track> getTracksForPlaylist() {
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
	private List<Track> getTracksFromTopTracksToPlaylist() {
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
	private List<Track> getTracksFromTopArtistsToPlaylist() {
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
	
	@Override
	public String findHowBadIsYourMusicalTaste() {
		Map<String, Integer> genres = getSortedGenresCount();
		return getMusicalTaste(genres);
	}

	/**
	 * Retorna as ocorrencias de cada gênero, dentro dos artistas e faixas favoritas
	 * do usuário
	 * 
	 * @param artists
	 * @param tracks
	 * @return
	 */
	private Map<String, Integer> getSortedGenresCount() {
		Map<String, Integer> occurencesByGenre = getOccurencesByGenre();
		return sortGenresByMostListened(occurencesByGenre);
	}
	
	private String getMusicalTaste(Map<String, Integer> genres) {
		Double percentBadGenres = getPercentOfBadGenres(genres);
		return getMusicalTasteByPercentOfBadTastes(percentBadGenres);
	}
	
	private Double getPercentOfBadGenres(Map<String, Integer> genres) {
		Double percentBadGenres = 0.0; 
		Integer sumGenresOcurrences = getAllGenresOccurencesSum(genres);
		Map<String, Integer> userGenresInBlackilist =  getBlacklistGenresByUser(genres);
		for(Map.Entry<String, Integer> occurencesByGenre : userGenresInBlackilist.entrySet()) {
			percentBadGenres += occurencesByGenre.getValue().doubleValue() / sumGenresOcurrences.doubleValue() * 100 ; 
		}
		return percentBadGenres;
	}
	
	private String getMusicalTasteByPercentOfBadTastes(Double percentBadGenres) {
		String result = "";
		if (percentBadGenres.intValue() >= 1 &&  percentBadGenres.intValue() <= 10) {
			result = "No geral você tem bom gosto músical, mas confesse, você tem uns esqueletos no armário ";
		} else if (percentBadGenres.intValue() >= 11 &&  percentBadGenres.intValue() <= 25) {
			result = "No geral seu gosto é bom, mas tem umas coisas aí pra se envergonhar";
		} else if (percentBadGenres.intValue() >= 26 &&  percentBadGenres.intValue() <= 50) {
			result = "Você até se esforça, mas tem coisas de gosto bem duvidoso no meio das coisas que ouve ";
		} else if (percentBadGenres.intValue() >= 51 &&  percentBadGenres.intValue() <= 75) {
			result = "Você é de uma pobreza musical tremenda. Tem um gosto musical muito ruim.";
		} else if (percentBadGenres.intValue() > 75) {
			result = "Seu gosto musical é um chorume total! Deus ajude pra que você nunca seja DJ em uma festa";
		} else {
			result = "Parabéns, você é uma pessoa de excelente gosto musical!";
		}
		return result;
	}
	
	private Integer getAllGenresOccurencesSum(Map<String, Integer> occurencesByGenre) {
		Integer sumGenreOcurrences = 0;
		for (Map.Entry<String, Integer> occurence : occurencesByGenre.entrySet()) {
			sumGenreOcurrences += occurence.getValue();
		}
		return sumGenreOcurrences;
	}
	
	private Map<String, Integer> getBlacklistGenresByUser(Map<String, Integer> genres){
		Map<String, Integer> userGenresInBlackilist = new HashMap<String,Integer>();
		Map<String, Integer> blacklistdGenreNotInGenre = new LinkedHashMap<String, Integer>();
		List<Genre> genresInBlacklist = genreService.findBlacklistedGenres();
		for (Map.Entry<String, Integer> genre : genres.entrySet()) {
			if(containsGenre(genresInBlacklist, genre.getKey())) {
				userGenresInBlackilist.put(genre.getKey(), genre.getValue());
			} else {
				blacklistdGenreNotInGenre.put(genre.getKey(), genre.getValue());
			}
		}
		return userGenresInBlackilist;
	}
	
	private Map<String, Integer> getOccurencesByGenre(){
		Map<String, Integer> occurencesByGenre = new TreeMap<String, Integer>(Collections.reverseOrder());
		List<String> genres = getGenresFromTopArtistsAndTopTracks();	
		Set<String> distinctGenres = new HashSet<>(genres);
		for (String genre : distinctGenres) {
			occurencesByGenre.put(genre, Collections.frequency(genres, genre));
		}
		return occurencesByGenre;
	}
	
	private List<String> getGenresFromTopArtistsAndTopTracks(){
		List<Artist> artists = new ArrayList<Artist>(); 
		List<Track> tracks = new ArrayList<Track>(); 
		List<String> genres = new ArrayList<String>();
		artists = findFavoriteArtists().getItems(); 
		tracks = findFavoriteTracks().getItems();
		genres.addAll(getGenresByTopArtists(artists));
		genres.addAll(getGenresByTopTracks(tracks));
		return genres;
	}
	
	private Map<String, Integer> sortGenresByMostListened(Map<String, Integer> occurencesByGenre ){
		Map<String, Integer> sortedGenresMap = new LinkedHashMap<String, Integer>();
		occurencesByGenre.entrySet().stream()
			.sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
			.forEach(occurence -> sortedGenresMap.put(occurence.getKey(), occurence.getValue()));
		return sortedGenresMap;
	}

	private Boolean containsGenre(List<Genre> genres, String genreName) {
		for(Genre genre : genres) {
			if(genre.getGenreName().toLowerCase().equals(genreName.toLowerCase())) {
				return Boolean.TRUE;
			}
		}
		return Boolean.FALSE;
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
	@SuppressWarnings({ "rawtypes"})
	private <T> StreamingResponse parseStreamingResponse(String json, Class<T> returnType) {
		ObjectMapper mapper = new ObjectMapper();
		JavaType type = mapper.getTypeFactory().constructParametricType(StreamingResponse.class, returnType);
		mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
		StreamingResponse<T> streamingResponse;
		try {
			streamingResponse = mapper.readValue(json, type);
		} catch (JsonProcessingException e) {
			logger.error("Erro ao processar Json de retorno");
			streamingResponse = new StreamingResponse<T>();
		}
		return streamingResponse;
	}
}
