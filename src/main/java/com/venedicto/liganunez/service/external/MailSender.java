package com.venedicto.liganunez.service.external;

import java.util.Base64;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.venedicto.liganunez.exception.MailSenderException;
import com.venedicto.liganunez.model.MailTypes;

@Service
public class MailSender {
	private RestTemplate restTemplate;
	@Value("${mail.sender.url}")
	private String url;
	@Value("${mail.sender.api.key}")
	private String apiKey;
	@Value("${mail.sender.api.user}")
	private String apiUser;
	@Value("${mail.sender.host}")
	private String sender;
	
	public MailSender() {
		this.restTemplate = new RestTemplate();
	}
	
//	@Retryable(retryFor = CannotGetJdbcConnectionException.class,
//			listeners = "dbRetryListeners",
//			maxAttemptsExpression = "${mail.sender.retry.attempts}", 
//			backoff = @Backoff(delayExpression = "${mail.sender.retry.delay}", maxDelayExpression = "${mail.sender.timeout}", multiplier = 1)
//	)
	public void sendMail(String receiver, MailTypes mailType, Object data) {
		String credentials = apiUser+":"+apiKey;
		byte[] encodedAuth = Base64.getEncoder().encode(credentials.getBytes());

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("Authorization", "Basic "+new String(encodedAuth));
		
		MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
		map.add("from", sender);
		map.add("to", receiver);
		map.add("subject", mailType.getSubject());
		map.add("template", mailType.getTemplate());
		map.add("h:X-Mailgun-Variables", new Gson().toJson(data).toString());
		
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String,String>>(map, headers);
		try {
			ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
			if(response.getStatusCode() != HttpStatus.OK) {
				throw new MailSenderException("Error al conectarse con el servicio de correo");
			}
		} catch(Exception e) {
			throw new MailSenderException("Error al conectarse con el servicio de correo");
		}
	}
}