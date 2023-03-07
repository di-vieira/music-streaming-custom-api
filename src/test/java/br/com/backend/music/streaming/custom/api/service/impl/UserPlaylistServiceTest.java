package br.com.backend.music.streaming.custom.api.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import br.com.backend.music.streaming.custom.api.domain.entity.UserPlaylist;
import br.com.backend.music.streaming.custom.api.repository.UserPlaylistRepository;
import br.com.backend.music.streaming.custom.api.service.UserPlaylistService;

public class UserPlaylistServiceTest {

	@InjectMocks
	private UserPlaylistService userPlaylistService = new UserPlaylistServiceImpl();
	
	@Mock
	private UserPlaylistRepository userPlaylistRepository;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void findAllUserPlaylistsTest() {
		List<UserPlaylist> mockedUserPlaylists = new ArrayList<UserPlaylist>();
		mockedUserPlaylists.add(new UserPlaylist());
		Mockito.when(userPlaylistRepository.findAll()).thenReturn(mockedUserPlaylists);
		List<UserPlaylist> userPlaylists = userPlaylistService.findAllUserPlaylists();
		assertNotNull(userPlaylists);
		assertEquals(1, userPlaylists.size());
	}
	
	@Test
	public void findUserPlaylistByIdTest() {
		UserPlaylist mockedUserPlaylist = new UserPlaylist();
		mockedUserPlaylist.setUserId("ID_TEST");
		mockedUserPlaylist.setPlaylistId("AiJksuSNFSoSP");
		mockedUserPlaylist.setIncludeDate(LocalDate.now());
		
		when(userPlaylistRepository.findById(anyString())).thenReturn(Optional.of(mockedUserPlaylist));
		UserPlaylist userPlaylist = userPlaylistService.findUserPlaylistById("");
		assertNotNull(userPlaylist);
		assertEquals("ID_TEST", userPlaylist.getUserId());
		assertEquals("AiJksuSNFSoSP", userPlaylist.getPlaylistId());
		assertEquals(LocalDate.now(), userPlaylist.getIncludeDate());
	}
	
	@Test
	public void findUserPlaylistByIdWhenIdNotExistsTest() {
		UserPlaylist mockedUserPlaylist = new UserPlaylist();
		
		when(userPlaylistRepository.findById(anyString())).thenReturn(Optional.of(mockedUserPlaylist));
		UserPlaylist userPlaylist = userPlaylistService.findUserPlaylistById("");
		assertNotNull(userPlaylist);
		assertNull(userPlaylist.getUserId());
		assertNull(userPlaylist.getPlaylistId());
		assertNull(userPlaylist.getIncludeDate());
	}
	
	@Test
	public void saveUserPlaylistTest() {
		UserPlaylist mockedUserPlaylist = new UserPlaylist();
		when(userPlaylistRepository.save(any(UserPlaylist.class))).thenReturn(new UserPlaylist());
		userPlaylistService.saveUserPlaylist(mockedUserPlaylist);
		verify(userPlaylistRepository, times(1)).save(mockedUserPlaylist);
	}
	
	@Test
	public void updateUserPlaylistTest() {
		UserPlaylist mockedUserPlaylist = new UserPlaylist();
		mockedUserPlaylist.setUserId("ID_TEST");
		mockedUserPlaylist.setPlaylistId("AiJksuSNFSoSP");
		mockedUserPlaylist.setIncludeDate(LocalDate.now());
		
		when(userPlaylistRepository.findById(anyString())).thenReturn(Optional.of(mockedUserPlaylist));
		when(userPlaylistRepository.save(any(UserPlaylist.class))).thenReturn(new UserPlaylist());
		userPlaylistService.updateUserPlaylist(mockedUserPlaylist);
		verify(userPlaylistRepository, times(1)).save(mockedUserPlaylist);
		verify(userPlaylistRepository, times(1)).findById(mockedUserPlaylist.getUserId());
	}
	
	@Test
	public void updateUserPlaylistWhenUserPlaylistNotExistsTest() {
		UserPlaylist mockedUserPlaylist = new UserPlaylist();
		mockedUserPlaylist.setUserId("ID_TEST");
		mockedUserPlaylist.setPlaylistId("AiJksuSNFSoSP");
		mockedUserPlaylist.setIncludeDate(LocalDate.now());
		
		when(userPlaylistRepository.findById(anyString())).thenReturn(Optional.empty());
		when(userPlaylistRepository.save(any(UserPlaylist.class))).thenReturn(new UserPlaylist());
		userPlaylistService.updateUserPlaylist(mockedUserPlaylist);
		verify(userPlaylistRepository, times(1)).findById(mockedUserPlaylist.getUserId());
	}
	
	@Test
	public void deleteUserPlaylistTest() {
		doNothing().when(userPlaylistRepository).deleteById(anyString());
		userPlaylistService.deleteUserPlaylist("");
		verify(userPlaylistRepository, times(1)).deleteById("");
	}

}
