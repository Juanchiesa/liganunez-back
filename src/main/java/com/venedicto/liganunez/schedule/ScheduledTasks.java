package com.venedicto.liganunez.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import com.venedicto.liganunez.service.PictureService;

@Component
public class ScheduledTasks {
	private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);
	private final ThreadPoolTaskScheduler taskScheduler;
	@Autowired
	private PictureService pictureService;
	
	public ScheduledTasks() {
        taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.initialize();
    }

    public void startScheduledTask() {
        taskScheduler.schedule(this::deletePictures, new CronTrigger("0 0 0 1 1/3 ?")); //Expresión cron para ejecutar cada 3 meses
    }

    public void stopScheduledTask() {
        taskScheduler.shutdown();
    }
    
	@Profile("production")
    public void deletePictures() {
		log.info("[Proceso trimestral] Iniciando limpieza de imagenes...");
		pictureService.deleteAllPictures();
    }
}