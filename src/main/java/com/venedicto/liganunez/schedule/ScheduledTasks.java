package com.venedicto.liganunez.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.venedicto.liganunez.service.PictureService;

/**
 * 
 * cron format
 * segundo | hora | minuto | dia_del_mes | mes1,mes2,mes3,...,mesN | dia_de_la_semana(? si no es necesario)
 * 
 * **/

@Component
public class ScheduledTasks {
	private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);
	@Autowired
	private PictureService pictureService;
    
	@Profile("production")
	@Scheduled(cron = "0 0 4 1 1,4,7,10 ?")
    public void deletePictures() {
		log.info("[Proceso trimestral] Iniciando limpieza de imagenes...");
		pictureService.deleteAllPictures();
    }
}