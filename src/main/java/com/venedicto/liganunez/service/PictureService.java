package com.venedicto.liganunez.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.venedicto.liganunez.repository.PictureRepository;

@Service
public class PictureService {
	@Autowired
	private PictureRepository pictureRepository;
}
