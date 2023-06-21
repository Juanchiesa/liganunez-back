package com.venedicto.liganunez.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.venedicto.liganunez.service.PictureService;

@Component
public class PictureApiHandler {
	@Autowired
	private PictureService pictureService;
}
