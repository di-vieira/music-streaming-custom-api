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
	 * Spotify API URL
	 */
	@Value("${spotify.api.url}")
	private String spotifyApiUrl;

	/**
	 * URN to make top tracks endpoint
	 */
	@Value("${spotify.api.top.tracks}")
	private String spotifyTopTracks;

	/**
	 * UURN to make top artists endpoint
	 */
	@Value("${spotify.api.top.artists}")
	private String spotifyTopArtists;

	/**
	 * URN to make artist search endpoint
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
	
	/**
	 * Description of created playlist
	 */
	private final String PLAYLIST_DESCRIPTION = "Playlist Criada por API";
	
	/**
	 * Authorization token
	 */
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
	public Playlist createPersonalPlaylist(String playlistName) {
		CreatePlaylistRequest request = new CreatePlaylistRequest(playlistName, false, false, PLAYLIST_DESCRIPTION);
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
	
	/**
	 * Add some of user's favorite tracks in personal playlist
	 * @param playlistId Playlist ID
	 */
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
	 * Returns basic data from logged user
	 * @return User object
	 */
	private User findUser() {
		return restTemplate.exchange(spotifyApiUrl + spotifyMe, HttpMethod.GET, getHttpEntity(), User.class).getBody();
	}

	/**
	 * Find artist by ID
	 * @param id Artist ID
	 * @return Artist object
	 */
	private Artist findArtistById(String id) {
		return restTemplate.exchange(spotifyApiUrl + spotifyArtists + "/" + id, HttpMethod.GET, getHttpEntity(), Artist.class).getBody();
	}

	/**
	 * Find top tracks by artist
	 * @param id Artist ID
	 * @return List<Track> list of top artist's top tracks
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
	 * @return List<Track> list of tracks to add in playlist
	 */
	private List<Track> getTracksForPlaylist() {
		List<Track> playlistTracks = new ArrayList<Track>();
		playlistTracks.addAll(getTracksFromTopArtistsToPlaylist());
		playlistTracks.addAll(getTracksFromTopTracksToPlaylist());
		return removeDuplicatedTracks(playlistTracks);
	}
	
	
	/**
	 * Get the top tracks from user's favorite tracks list to add in personal playlist
	 * @return List<Track> list of tracks
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
	 * Get the top tracks from user's favorite artists list to add in personal playlist
	 * @return List<Track> list of tracks
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
	 * @param playlistTracks List of tracks
	 * @param artist artist to verify if there's some track in list
	 * @return Boolean
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
	 * @param playlistTracks List of tracks
	 * @param trackToAdd track to verify if there's some occurrence of it in list
	 * @return Boolean
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
	
	/**
	 * Remove occurrences of duplicated tracks in a track list
	 * @param tracks list of tracks to remove duplicates
	 * @return List<Track> Track list without duplicates
	 */
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
	 * Returns a string with user's musical taste analysis
	 */
	@Override
	public String findHowBadIsYourMusicalTaste() {
		Map<String, Integer> genres = getSortedGenresCount();
		return getMusicalTaste(genres);
	}


	/**
	 * Sort map with all genres occurences ordered by value descending 
	 * @return Map<String, Integer> genres map
	 */
	private Map<String, Integer> getSortedGenresCount() {
		Map<String, Integer> occurencesByGenre = getOccurencesByGenre();
		return sortGenresByMostListened(occurencesByGenre);
	}
	
	/**
	 * Get the musical taste analysis of user
	 * @param genres map containing most listened genres by user
	 * @return String returns a string with user's musical taste analysis
	 */
	private String getMusicalTaste(Map<String, Integer> genres) {
		Double percentBlacklistedGenres = getPercentOfBlacklistedGenres(genres);
		return getMusicalTasteByPercentOfBlacklistedTastes(percentBlacklistedGenres);
	}
	
	/**
	 * Find the percent value of blacklisted genres in user's favorites tracks and artists
	 * @param genres map containing most listened genres by user
	 * @return Double percentBlacklistedGenres percent value of blacklisted genres in user's most listened genres
	 */
	private Double getPercentOfBlacklistedGenres(Map<String, Integer> genres) {
		Double percentBlacklistedGenres = 0.0; 
		Integer sumGenresOcurrences = getAllGenresOccurencesSum(genres);
		Map<String, Integer> userGenresInBlackilist =  getBlacklistGenresByUser(genres);
		for(Map.Entry<String, Integer> occurencesByGenre : userGenresInBlackilist.entrySet()) {
			percentBlacklistedGenres += occurencesByGenre.getValue().doubleValue() / sumGenresOcurrences.doubleValue() * 100 ; 
		}
		return percentBlacklistedGenres;
	}
	
	/**
	 * Get user's musical taste analysis based in a blacklisted genres percent value 
	 * @param percentBlacklistedGenres percent value of blacklisted genres in user's most listened genres
	 * @return String description of user's musical taste analysis
	 */
	private String getMusicalTasteByPercentOfBlacklistedTastes(Double percentBlacklistedGenres) {
		String result = "";
		if (percentBlacklistedGenres.intValue() >= 1 &&  percentBlacklistedGenres.intValue() <= 5) {
			result = "No geral você tem bom gosto músical, mas confesse, você tem uns esqueletos no armário ";
		} else if (percentBlacklistedGenres.intValue() >= 6 &&  percentBlacklistedGenres.intValue() <= 15) {
			result = "No geral seu gosto é bom, mas tem umas coisas aí pra se envergonhar";
		} else if (percentBlacklistedGenres.intValue() >= 16 &&  percentBlacklistedGenres.intValue() <= 35) {
			result = "Você até se esforça, mas tem coisas de gosto bem duvidoso no meio das coisas que ouve ";
		} else if (percentBlacklistedGenres.intValue() >= 36 &&  percentBlacklistedGenres.intValue() <= 60) {
			result = "Você é de uma pobreza musical tremenda. Tem um gosto musical muito ruim.";
		} else if (percentBlacklistedGenres.intValue() > 60) {
			result = "Seu gosto musical é um chorume total! Deus ajude pra que você nunca seja DJ em uma festa";
		} else {
			result = "Parabéns, você é uma pessoa de excelente gosto musical!";
		}
		return result;
	}
	
	/**
	 * Get the sum of all genre occurences 
	 * @param occurencesByGenre Map of genres occurrences
	 * @return Integer Sum of all genre occurrences
	 */
	private Integer getAllGenresOccurencesSum(Map<String, Integer> occurencesByGenre) {
		Integer sumGenreOcurrences = 0;
		for (Map.Entry<String, Integer> occurence : occurencesByGenre.entrySet()) {
			sumGenreOcurrences += occurence.getValue();
		}
		return sumGenreOcurrences;
	}
	
	/**
	 * Gets a list of all user's genres which are in genres blacklist
	 * @param genres list of genres to verify if some of them is in blacklist
	 * @return Map<String, Integer> a map with all user's blacklisted genres
	 */
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
	
	/**
	 * Gets a map containing user's most listened genres occurrences
	 * @return Map<String, Integer> Occurrences by genre map 
	 */
	private Map<String, Integer> getOccurencesByGenre(){
		Map<String, Integer> occurencesByGenre = new TreeMap<String, Integer>(Collections.reverseOrder());
		List<String> genres = getGenresFromTopArtistsAndTopTracks();	
		Set<String> distinctGenres = new HashSet<>(genres);
		for (String genre : distinctGenres) {
			occurencesByGenre.put(genre, Collections.frequency(genres, genre));
		}
		return occurencesByGenre;
	}
	
	/**
	 * Gets a list of genres from top artists and top tracks
	 * @return List<String> List of genres from top artists and top tracks
	 */
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
	
	/**
	 * Returns a sorted genres map
	 * @param occurencesByGenre Map of genres occurrences to be sorted
	 * @return Map<String, Integer> 
	 */
	private Map<String, Integer> sortGenresByMostListened(Map<String, Integer> occurencesByGenre ){
		Map<String, Integer> sortedGenresMap = new LinkedHashMap<String, Integer>();
		occurencesByGenre.entrySet().stream()
			.sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
			.forEach(occurence -> sortedGenresMap.put(occurence.getKey(), occurence.getValue()));
		return sortedGenresMap;
	}

	/**
	 * Verifies if a genre are in a genres list
	 * @param genres list of genres to be verified
	 * @param genreName name of genre to be verified
	 * @return Boolean
	 */
	private Boolean containsGenre(List<Genre> genres, String genreName) {
		for(Genre genre : genres) {
			if(genre.getGenreName().toLowerCase().equals(genreName.toLowerCase())) {
				return Boolean.TRUE;
			}
		}
		return Boolean.FALSE;
	}
	
	
	/**
	 * Returns list with artist's top tracks genres
	 * @param artists
	 * @return list of artist's top tracks genres
	 */
	public List<String> getGenresByTopArtists(List<Artist> artists) {
		List<String> genres = new ArrayList<String>();
		for (Artist artist : artists) {
			genres.addAll(artist.getGenres());
		}
		return genres;
	}

	/**
	 * Returns list with top tracks genres
	 * @param tracks list of tracks to 
	 * @return list of top tracks genres
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
