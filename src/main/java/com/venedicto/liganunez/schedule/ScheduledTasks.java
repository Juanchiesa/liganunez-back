package com.venedicto.liganunez.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.venedicto.liganunez.service.PictureService;

@Component
public class ScheduledTasks {
	private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);
	@Autowired
	private PictureService pictureService;
	
	@Scheduled(cron = "0 0 0 1 1/3 ?")
	@Profile("production")
    public void deletePictures() {
		log.info("[Proceso trimestral] Iniciando limpieza de imagenes...");
		pictureService.deleteAllPictures();
    }
}