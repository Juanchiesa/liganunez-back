package com.venedicto.liganunez.service.external;

import java.nio.charset.Charset;
import java.util.Collections;
import java.util.LinkedHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.venedicto.liganunez.exception.MailSenderException;
import com.venedicto.liganunez.model.MailTypes;

@Service
public class MailSenderService {
	private RestTemplate restTemplate;
	@Autowired
	private MailSenderException mailSenderException;
	@Value("${mail.sender.url}")
	private String url;
	@Value("${mail.sender.api.key}")
	private String apiKey;
	@Value("${mail.sender.host}")
	private String sender;
	
	public MailSenderService() {
		this.restTemplate = new RestTemplate();
	}
	
	@Retryable(retryFor = MailSenderException.class, listeners = "mailSenderRetryListeners", maxAttemptsExpression = "${mail.sender.retry.attempts}", backoff = @Backoff(delayExpression = "${mail.sender.retry.delay}", maxDelayExpression = "${mail.sender.timeout}", multiplier = 1))
	public void sendMail(String receiver, MailTypes mailType, Object data) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.setAcceptCharset(Collections.singletonList(Charset.forName("UTF-8")));
		headers.add("Authorization", apiKey);
		
		LinkedHashMap<String, Object> map= new LinkedHashMap<>();
		map.put("from", sender);
		map.put("to", receiver);
		map.put("subject", mailType.getSubject());
		map.put("project", "ln");
		map.put("template", mailType.getTemplate());
		map.put("data", new Gson().toJson(data).toString());
		
		HttpEntity<Object> request = new HttpEntity<Object>(map, headers);
		try {
			ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
			if(response.getStatusCode() != HttpStatus.OK) {
				throw new Exception();
			}
		} catch(Exception e) {
			mailSenderException.setMessage(e.getMessage());
			throw mailSenderException;
		}
	}
}