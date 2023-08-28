package com.venedicto.liganunez.controller;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.venedicto.liganunez.handler.PictureApiHandler;
import com.venedicto.liganunez.model.ErrorCodes;
import com.venedicto.liganunez.model.http.DownloadStatsHttpResponse;
import com.venedicto.liganunez.model.http.Error;
import com.venedicto.liganunez.model.http.GetPicturesHttpResponse;
import com.venedicto.liganunez.model.http.HttpResponse;
import com.venedicto.liganunez.model.http.UploadPicturesHttpResponse;
import com.venedicto.liganunez.utils.HttpUtils;
import com.venedicto.liganunez.validator.PictureValidator;

@RunWith(MockitoJUnitRunner.class)
public class PictureApiControllerTest {
	@InjectMocks
	private PictureApiController controller;
	@Mock
    private PictureApiHandler handler;
    @Mock
    private PictureValidator validator;
    @Mock
    private MultipartHttpServletRequest uploadRequest;
    
    @Test
    public void deletePicture_ok() {
    	controller.deletePicture("xxxx-xxx-xx-x", "aaa-aa-a", "daskdasjodhasiud");
    	Mockito.verify(handler, Mockito.times(1)).deletePicture(Mockito.any(HttpResponse.class), Mockito.eq("daskdasjodhasiud"), Mockito.eq("xxxx-xxx-xx-x"), Mockito.eq("aaa-aa-a"));
    }
    
    @Test
    public void getPictures_withoutFilters_ok() {
    	controller.getPictures("xxxx", 1, null, null);
    	Mockito.verify(handler, Mockito.times(1)).getPictures(Mockito.any(GetPicturesHttpResponse.class), Mockito.eq("xxxx"), Mockito.eq(1), Mockito.eq(null), Mockito.eq(null));
    }
    @Test
    public void getPictures_withFilters_ok() {
    	Mockito.when(validator.validateDateFormat("2023-07-11")).thenReturn(new ArrayList<>());
    	
    	controller.getPictures("xxxx", 1, "test", "2023-07-11");
    	
    	Mockito.verify(handler, Mockito.times(1)).getPictures(Mockito.any(GetPicturesHttpResponse.class), Mockito.eq("xxxx"), Mockito.eq(1), Mockito.eq("test"), Mockito.eq("2023-07-11"));
    }
    @Test
    public void getPictures_withFilters_error() {
    	List<Error> errors = new ArrayList<>();
    	errors.add(HttpUtils.generateError(ErrorCodes.LN0019));
    	
    	Mockito.when(validator.validateDateFormat("2023-07-11")).thenReturn(errors);
    	
    	controller.getPictures("xxxx", 1, "test", "2023-07-11");
    	Mockito.verify(handler, Mockito.times(0)).getPictures(Mockito.any(GetPicturesHttpResponse.class), Mockito.eq("xxxx"), Mockito.eq(1), Mockito.eq("test"), Mockito.eq("2023-07-11"));
    }
    
    @Test
    public void uploadPictures_ok() {
    	controller.uploadPictures(uploadRequest, "12345", "place", "2023-01-17", "xxxx", new ArrayList<>());
    	Mockito.verify(handler, Mockito.times(1)).uploadPicture(Mockito.eq(uploadRequest), Mockito.any(UploadPicturesHttpResponse.class), Mockito.eq("12345"), Mockito.eq("xxxx"), Mockito.eq("place"), Mockito.eq("2023-01-17"), Mockito.anyList());
    }
    @Test
    public void uploadPictures_error() {
    	List<Error> errors = new ArrayList<>();
    	errors.add(HttpUtils.generateError(ErrorCodes.LN0019));
    	errors.add(HttpUtils.generateError(ErrorCodes.LN0020));
    	errors.add(HttpUtils.generateError(ErrorCodes.LN0021));
    	
    	Mockito.when(validator.validateFileUpload("xxxx", "place", "2023-01-17")).thenReturn(errors);
    	
    	controller.uploadPictures(uploadRequest, "12345", "place", "2023-01-17", "xxxx", new ArrayList<>());
    	
    	Mockito.verify(handler, Mockito.times(0)).uploadPicture(Mockito.eq(uploadRequest), Mockito.any(UploadPicturesHttpResponse.class), Mockito.eq("12345"), Mockito.eq("xxxx"), Mockito.eq("place"), Mockito.eq("2023-01-17"), Mockito.anyList());
    }
    
    @Test
    public void getPicturesStats_ok() {
    	controller.getPictureStats("xxxxx");
    	Mockito.verify(handler, Mockito.times(1)).getPicturesStats(Mockito.any(DownloadStatsHttpResponse.class), Mockito.eq("xxxxx"));
    }
    
    @Test
    public void getPictureStats_ok() {
    	controller.getPictureStats("aaaa", "xxxxx");
    	Mockito.verify(handler, Mockito.times(1)).getPictureStats(Mockito.any(DownloadStatsHttpResponse.class), Mockito.eq("aaaa"), Mockito.eq("xxxxx"));
    }
}