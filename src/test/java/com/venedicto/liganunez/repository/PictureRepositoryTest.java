package com.venedicto.liganunez.repository;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.jdbc.core.JdbcTemplate;

import com.venedicto.liganunez.model.http.Picture;
import com.venedicto.liganunez.repository.mappers.PictureRowMapper;

@RunWith(MockitoJUnitRunner.class)
public class PictureRepositoryTest {
	@InjectMocks
	private PictureRepository repository;
	@Mock
	private JdbcTemplate jdbcTemplate;
	
	@Test
	public void createPicture_ok() {
		repository.createPicture("1", "Place", "2023-10-10", "xxxx-xxx-xx-x");
		Mockito.verify(jdbcTemplate, Mockito.times(1)).update(Mockito.anyString(), Mockito.eq("1"), Mockito.eq("2023-10-10"), Mockito.eq("Place"), Mockito.eq("xxxx-xxx-xx-x"));
	}
	
	@Test
	public void getPictures_withoutFilters_ok() {
		Mockito.when(jdbcTemplate.query(Mockito.anyString(), Mockito.any(PictureRowMapper.class), Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(generatePicturesList(20));
		
		List<Picture> response = repository.getPictures("xxxx-xxx-xx-x", 1, null, null);
		
		assertEquals(20, response.size());
	}
	@Test
	public void getPictures_withPlaceFilter_ok() {
		Mockito.when(jdbcTemplate.query(Mockito.anyString(), Mockito.any(PictureRowMapper.class), Mockito.anyString(), Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(generatePicturesList(5));
		
		List<Picture> response = repository.getPictures("xxxx-xxx-xx-x", 1, "Place2", null);
		
		assertEquals(5, response.size());
	}
	@Test
	public void getPictures_withDateFilter_ok() {
		Mockito.when(jdbcTemplate.query(Mockito.anyString(), Mockito.any(PictureRowMapper.class), Mockito.anyString(), Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(generatePicturesList(7));
		
		List<Picture> response = repository.getPictures("xxxx-xxx-xx-x", 1, null, "2023-07-07");
		
		assertEquals(7, response.size());
	}
	@Test
	public void getPictures_withBothFilters_ok() {
		Mockito.when(jdbcTemplate.query(Mockito.anyString(), Mockito.any(PictureRowMapper.class), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(generatePicturesList(2));
		
		List<Picture> response = repository.getPictures("xxxx-xxx-xx-x", 1, "Place2", "2023-07-07");
		
		assertEquals(2, response.size());
	}
	
	@Test
	public void deletePicture_ok() {
		repository.deletePicture("xxxx-xxx-xx-x");
		Mockito.verify(jdbcTemplate, Mockito.times(1)).update(Mockito.anyString(), Mockito.eq("xxxx-xxx-xx-x"));
	}
	
	@Test
	public void getPicturesStats_ok() {
		Mockito.when(jdbcTemplate.queryForObject(Mockito.anyString(), Mockito.eq(Integer.class))).thenReturn(5);
		
		int response = repository.getPicturesStats();
		
		assertEquals(5, response);
	}
	
	@Test
	public void getPictureStats_ok() {
		Mockito.when(jdbcTemplate.queryForObject(Mockito.anyString(), Mockito.eq(Integer.class), Mockito.eq("xxxx-xxx-xx-x"))).thenReturn(2);
		
		int response = repository.getPictureStats("xxxx-xxx-xx-x");
		
		assertEquals(2, response);
	}
	
	@Test
	public void getPictures_ok() {
		Mockito.when(jdbcTemplate.query(Mockito.anyString(), Mockito.any(PictureRowMapper.class))).thenReturn(generatePicturesList(50));
		
		List<Picture> response = repository.getPictures();
		
		assertEquals(50, response.size());
	}
	
	/** Adicional **/
	private List<Picture> generatePicturesList(int nPictures) {
		List<Picture> pictures = new ArrayList<>();
		
		for(int i=0; i<nPictures; i++) {
			Picture picture = new Picture();
			picture.setId(String.valueOf(i));
			pictures.add(picture);
		}
		
		return pictures;
	}
}