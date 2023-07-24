package com.venedicto.liganunez.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.venedicto.liganunez.service.PictureService;

@Component
public class DeletePictures {
	private static final Logger log = LoggerFactory.getLogger(DeletePictures.class);
	@Autowired
	private PictureService pictureService;
	
	@Scheduled(cron = "${cron.tarea.trimestral}")
    public void tarea() {
		log.info("[Proceso trimestral] Iniciando limpieza de imagenes...");
		pictureService.deleteAllPictures();
    }
}