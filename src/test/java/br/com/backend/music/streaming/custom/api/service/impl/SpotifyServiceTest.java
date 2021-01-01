package br.com.backend.music.streaming.custom.api.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.com.backend.music.streaming.custom.api.domain.spotify.response.TopTracksResponse;
import br.com.backend.music.streaming.custom.api.service.MusicStreamingService;

public class SpotifyServiceTest {

	@InjectMocks
	private MusicStreamingService musicStreamingService = new SpotifyService();
	
	@Mock
	private RestTemplate restTemplate;

	/**
	 * Inicializa os Mocks
	 */
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	/**
	 * Teste para o método que consulta a API do Spotify e retorna as faixas favoritas do usuário
	 * O teste é feito retornando um Mock de um JSON Válido
	 */
	@Test
	public void findFavoriteTracks() {
		String jsonMockResponse = "{\r\n"
				+ "  \"items\" : [ {\r\n"
				+ "    \"album\" : {\r\n"
				+ "      \"album_type\" : \"ALBUM\",\r\n"
				+ "      \"artists\" : [ {\r\n"
				+ "        \"external_urls\" : {\r\n"
				+ "          \"spotify\" : \"https://open.spotify.com/artist/0X380XXQSNBYuleKzav5UO\"\r\n"
				+ "        },\r\n"
				+ "        \"href\" : \"https://api.spotify.com/v1/artists/0X380XXQSNBYuleKzav5UO\",\r\n"
				+ "        \"id\" : \"0X380XXQSNBYuleKzav5UO\",\r\n"
				+ "        \"name\" : \"Nine Inch Nails\",\r\n"
				+ "        \"type\" : \"artist\",\r\n"
				+ "        \"uri\" : \"spotify:artist:0X380XXQSNBYuleKzav5UO\"\r\n"
				+ "      } ],\r\n"
				+ "      \"available_markets\" : [ \"AD\", \"AE\", \"AR\", \"AT\", \"AU\", \"BE\", \"BG\", \"BH\", \"BO\", \"BR\", \"CH\", \"CL\", \"CO\", \"CR\", \"CY\", \"CZ\", \"DE\", \"DK\", \"DO\", \"DZ\", \"EC\", \"EE\", \"EG\", \"ES\", \"FI\", \"FR\", \"GB\", \"GR\", \"GT\", \"HK\", \"HN\", \"HU\", \"ID\", \"IE\", \"IL\", \"IN\", \"IS\", \"IT\", \"JO\", \"JP\", \"KW\", \"LB\", \"LI\", \"LT\", \"LU\", \"LV\", \"MA\", \"MC\", \"MT\", \"MY\", \"NI\", \"NL\", \"NO\", \"NZ\", \"OM\", \"PA\", \"PE\", \"PH\", \"PL\", \"PS\", \"PT\", \"PY\", \"QA\", \"RO\", \"SA\", \"SE\", \"SG\", \"SK\", \"SV\", \"TH\", \"TN\", \"TR\", \"TW\", \"UY\", \"VN\", \"ZA\" ],\r\n"
				+ "      \"external_urls\" : {\r\n"
				+ "        \"spotify\" : \"https://open.spotify.com/album/1PuCoaLQNyCeaLBgGtNyW3\"\r\n"
				+ "      },\r\n"
				+ "      \"href\" : \"https://api.spotify.com/v1/albums/1PuCoaLQNyCeaLBgGtNyW3\",\r\n"
				+ "      \"id\" : \"1PuCoaLQNyCeaLBgGtNyW3\",\r\n"
				+ "      \"images\" : [ {\r\n"
				+ "        \"height\" : 640,\r\n"
				+ "        \"url\" : \"https://i.scdn.co/image/ab67616d0000b273c6fb913ce65ee5844f3ece58\",\r\n"
				+ "        \"width\" : 640\r\n"
				+ "      }, {\r\n"
				+ "        \"height\" : 300,\r\n"
				+ "        \"url\" : \"https://i.scdn.co/image/ab67616d00001e02c6fb913ce65ee5844f3ece58\",\r\n"
				+ "        \"width\" : 300\r\n"
				+ "      }, {\r\n"
				+ "        \"height\" : 64,\r\n"
				+ "        \"url\" : \"https://i.scdn.co/image/ab67616d00004851c6fb913ce65ee5844f3ece58\",\r\n"
				+ "        \"width\" : 64\r\n"
				+ "      } ],\r\n"
				+ "      \"name\" : \"With Teeth (Bonus Tracks)\",\r\n"
				+ "      \"release_date\" : \"2005-05-03\",\r\n"
				+ "      \"release_date_precision\" : \"day\",\r\n"
				+ "      \"total_tracks\" : 15,\r\n"
				+ "      \"type\" : \"album\",\r\n"
				+ "      \"uri\" : \"spotify:album:1PuCoaLQNyCeaLBgGtNyW3\"\r\n"
				+ "    },\r\n"
				+ "    \"artists\" : [ {\r\n"
				+ "      \"external_urls\" : {\r\n"
				+ "        \"spotify\" : \"https://open.spotify.com/artist/0X380XXQSNBYuleKzav5UO\"\r\n"
				+ "      },\r\n"
				+ "      \"href\" : \"https://api.spotify.com/v1/artists/0X380XXQSNBYuleKzav5UO\",\r\n"
				+ "      \"id\" : \"0X380XXQSNBYuleKzav5UO\",\r\n"
				+ "      \"name\" : \"Nine Inch Nails\",\r\n"
				+ "      \"type\" : \"artist\",\r\n"
				+ "      \"uri\" : \"spotify:artist:0X380XXQSNBYuleKzav5UO\"\r\n"
				+ "    } ],\r\n"
				+ "    \"available_markets\" : [ \"AD\", \"AE\", \"AR\", \"AT\", \"AU\", \"BE\", \"BG\", \"BH\", \"BO\", \"BR\", \"CH\", \"CL\", \"CO\", \"CR\", \"CY\", \"CZ\", \"DE\", \"DK\", \"DO\", \"DZ\", \"EC\", \"EE\", \"EG\", \"ES\", \"FI\", \"FR\", \"GB\", \"GR\", \"GT\", \"HK\", \"HN\", \"HU\", \"ID\", \"IE\", \"IL\", \"IN\", \"IS\", \"IT\", \"JO\", \"JP\", \"KW\", \"LB\", \"LI\", \"LT\", \"LU\", \"LV\", \"MA\", \"MC\", \"MT\", \"MY\", \"NI\", \"NL\", \"NO\", \"NZ\", \"OM\", \"PA\", \"PE\", \"PH\", \"PL\", \"PS\", \"PT\", \"PY\", \"QA\", \"RO\", \"SA\", \"SE\", \"SG\", \"SK\", \"SV\", \"TH\", \"TN\", \"TR\", \"TW\", \"UY\", \"VN\", \"ZA\" ],\r\n"
				+ "    \"disc_number\" : 1,\r\n"
				+ "    \"duration_ms\" : 243160,\r\n"
				+ "    \"explicit\" : true,\r\n"
				+ "    \"external_ids\" : {\r\n"
				+ "      \"isrc\" : \"USIR10500490\"\r\n"
				+ "    },\r\n"
				+ "    \"external_urls\" : {\r\n"
				+ "      \"spotify\" : \"https://open.spotify.com/track/56kUvKRD65pvGCaCZcnaBx\"\r\n"
				+ "    },\r\n"
				+ "    \"href\" : \"https://api.spotify.com/v1/tracks/56kUvKRD65pvGCaCZcnaBx\",\r\n"
				+ "    \"id\" : \"56kUvKRD65pvGCaCZcnaBx\",\r\n"
				+ "    \"is_local\" : false,\r\n"
				+ "    \"name\" : \"Sunspots\",\r\n"
				+ "    \"popularity\" : 40,\r\n"
				+ "    \"preview_url\" : \"https://p.scdn.co/mp3-preview/e05d8e198303f830a5a7a7d214aeb1e9889d0c9f?cid=08c5fd71eb9d45fd9cf760e8d0d62040\",\r\n"
				+ "    \"track_number\" : 10,\r\n"
				+ "    \"type\" : \"track\",\r\n"
				+ "    \"uri\" : \"spotify:track:56kUvKRD65pvGCaCZcnaBx\"\r\n"
				+ "  } ],\r\n"
				+ "  \"total\" : 50,\r\n"
				+ "  \"limit\" : 20,\r\n"
				+ "  \"offset\" : 0,\r\n"
				+ "  \"href\" : \"https://api.spotify.com/v1/me/top/tracks\",\r\n"
				+ "  \"previous\" : null,\r\n"
				+ "  \"next\" : \"https://api.spotify.com/v1/me/top/tracks?limit=20&offset=20\"\r\n"
				+ "}";
		TopTracksResponse response = new TopTracksResponse();
		try {
			when(restTemplate.exchange(anyString(), any(HttpMethod.class), any(HttpEntity.class), eq(String.class))).thenReturn(new ResponseEntity<String>(jsonMockResponse, HttpStatus.OK));
			response = musicStreamingService.findFavoriteTracks("");
			
			assertEquals(response.getItems().size(), 1);
			assertEquals(response.getItems().get(0).getAvailableMarkets().size(), 76);

			assertEquals(response.getItems().get(0).getAlbum().getAlbumType(), "ALBUM");
			assertEquals(response.getItems().get(0).getAlbum().getArtists().size(), 1);
			assertEquals(response.getItems().get(0).getAlbum().getAvailableMarkets().size(), 76);
			assertEquals(response.getItems().get(0).getAlbum().getExternalUrls().size(), 1);
			assertEquals(response.getItems().get(0).getAlbum().getHref(), "https://api.spotify.com/v1/albums/1PuCoaLQNyCeaLBgGtNyW3");
			assertEquals(response.getItems().get(0).getAlbum().getId(), "1PuCoaLQNyCeaLBgGtNyW3");
			assertEquals(response.getItems().get(0).getAlbum().getImages().get(0).getHeight(), Integer.valueOf(640));
			assertEquals(response.getItems().get(0).getAlbum().getImages().get(0).getUrl(), "https://i.scdn.co/image/ab67616d0000b273c6fb913ce65ee5844f3ece58");
			assertEquals(response.getItems().get(0).getAlbum().getImages().get(0).getWidth(), Integer.valueOf(640));
			assertEquals(response.getItems().get(0).getAlbum().getName(), "With Teeth (Bonus Tracks)");
			assertEquals(response.getItems().get(0).getAlbum().getReleaseDate(), "2005-05-03");
			assertEquals(response.getItems().get(0).getAlbum().getReleaseDatePrecision(), "day");
			assertEquals(response.getItems().get(0).getAlbum().getTotalTracks(), Integer.valueOf(15));
			assertEquals(response.getItems().get(0).getAlbum().getType(), "album");
			assertEquals(response.getItems().get(0).getAlbum().getUri(), "spotify:album:1PuCoaLQNyCeaLBgGtNyW3");
			
			assertEquals(response.getItems().get(0).getArtists().size(), 1);
			assertEquals(response.getItems().get(0).getArtists().get(0).getExternalUrls().get(0).getSpotify(), "https://open.spotify.com/artist/0X380XXQSNBYuleKzav5UO");
			assertEquals(response.getItems().get(0).getArtists().get(0).getHref(),"https://api.spotify.com/v1/artists/0X380XXQSNBYuleKzav5UO");
			assertEquals(response.getItems().get(0).getArtists().get(0).getId(), "0X380XXQSNBYuleKzav5UO");
			assertEquals(response.getItems().get(0).getArtists().get(0).getName(), "Nine Inch Nails");
			assertEquals(response.getItems().get(0).getArtists().get(0).getType(), "artist");
			assertEquals(response.getItems().get(0).getArtists().get(0).getUri(), "spotify:artist:0X380XXQSNBYuleKzav5UO");
			
			assertEquals(response.getItems().get(0).getDiscNumber(), Integer.valueOf(1));
			assertEquals(response.getItems().get(0).getDurationMs(), Integer.valueOf(243160));
			assertEquals(response.getItems().get(0).getExplicit(), Boolean.TRUE);
			assertEquals(response.getItems().get(0).getExternalIds().size(), 1);
			assertEquals(response.getItems().get(0).getExternalIds().get(0).getIsrc(), "USIR10500490");
			assertEquals(response.getItems().get(0).getExternalUrls().size(), 1);
			assertEquals(response.getItems().get(0).getHref(), "https://api.spotify.com/v1/tracks/56kUvKRD65pvGCaCZcnaBx");
			assertEquals(response.getItems().get(0).getId(), "56kUvKRD65pvGCaCZcnaBx");
			assertEquals(response.getItems().get(0).getIsLocal(), Boolean.FALSE);
			assertEquals(response.getItems().get(0).getName(), "Sunspots");
			assertEquals(response.getItems().get(0).getPopularity(), Integer.valueOf(40));
			assertEquals(response.getItems().get(0).getPreviewUrl(), "https://p.scdn.co/mp3-preview/e05d8e198303f830a5a7a7d214aeb1e9889d0c9f?cid=08c5fd71eb9d45fd9cf760e8d0d62040");
			assertEquals(response.getItems().get(0).getTrackNumber(), Integer.valueOf(10));
			assertEquals(response.getItems().get(0).getType(), "track");
			assertEquals(response.getItems().get(0).getURI(), "spotify:track:56kUvKRD65pvGCaCZcnaBx");

			assertEquals(response.getTotal(), Integer.valueOf(50));
			assertEquals(response.getLimit(), Integer.valueOf(20));
			assertEquals(response.getOffset(), Integer.valueOf(0));
			assertEquals(response.getHref(), "https://api.spotify.com/v1/me/top/tracks");
			assertNull(response.getPrevious());
			assertEquals(response.getNext(), "https://api.spotify.com/v1/me/top/tracks?limit=20&offset=20");
			
		} catch (JsonProcessingException e) {
			fail("Erro ao processar o JSON no método findFavoriteTracks");
		} catch (Exception e) {
			fail("Erro na execução do teste");
		}
	}
	
	/**
	 * Teste do Fluxo de Exceção de parse do JSON na busca de top-tracks. Utiliza um JSON vazio
	 */
	@Test
	public void findFavoriteTracksJsonProcessingExceptionTest() {
		TopTracksResponse response = new TopTracksResponse();
		try {
			when(restTemplate.exchange(anyString(), any(HttpMethod.class), any(HttpEntity.class), eq(String.class))).thenReturn(new ResponseEntity<String>("", HttpStatus.BAD_REQUEST));
			response = musicStreamingService.findFavoriteTracks("");
			fail("Método deveria ter caído no fluxo de exceção");
		} catch (JsonProcessingException e) {
			assertEquals(response.getItems().size() , 0);
		} catch (Exception e) {
			fail("Erro na execução do teste");
		}
	}

}
