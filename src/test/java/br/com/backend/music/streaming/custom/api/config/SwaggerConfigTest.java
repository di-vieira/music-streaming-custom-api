package br.com.backend.music.streaming.custom.api.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SecurityConfiguration;

public class SwaggerConfigTest {
	
	@InjectMocks
	private SwaggerConfig swaggerConfig;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	/**
	 * Testa a configuração do Swagger para documentar a API
	 */
	@Test
	public void apiTest() {
		
		ReflectionTestUtils.setField(swaggerConfig, "scope", "user-read-private user-read-email user-top-read");
		
		Docket docket = swaggerConfig.api();
		
		assertTrue(docket.isEnabled());
	}
	
	/**
	 * Testa o método de securityInfo que configura informações para autenticação com o serviço de streaming
	 */
	@Test
	public void securityInfoTest() {
		ReflectionTestUtils.setField(swaggerConfig, "clientId", "idTeste");
		ReflectionTestUtils.setField(swaggerConfig, "clientSecret", "secretTeste");
		
		SecurityConfiguration configuration = swaggerConfig.securityInfo();
		assertEquals("idTeste", configuration.getClientId());
		assertEquals("secretTeste", configuration.getClientSecret());
		assertEquals("Login API Spotify", configuration.getAppName());
	}
	
}
