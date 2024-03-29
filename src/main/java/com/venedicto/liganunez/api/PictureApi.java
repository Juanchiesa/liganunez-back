/**
 * NOTE: This class is auto generated by the swagger code generator program (3.0.46).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package com.venedicto.liganunez.api;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.venedicto.liganunez.model.http.DownloadStatsHttpResponse;
import com.venedicto.liganunez.model.http.GetPicturesHttpResponse;
import com.venedicto.liganunez.model.http.HttpResponse;
import com.venedicto.liganunez.model.http.UploadPicturesHttpResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-07-07T20:57:15.350055179Z[GMT]")
@Validated
public interface PictureApi {

    @Operation(summary = "Eliminar una foto", description = "", tags={ "picture" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Imagen eliminada", content = @Content(mediaType = "application/json", schema = @Schema(implementation = HttpResponse.class))),
        
        @ApiResponse(responseCode = "404", description = "Imagen inexistente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = HttpResponse.class))),
        
        @ApiResponse(responseCode = "500", description = "Error del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = HttpResponse.class))),
        
        @ApiResponse(responseCode = "503", description = "Base de datos no disponible", content = @Content(mediaType = "application/json", schema = @Schema(implementation = HttpResponse.class))) })
    @RequestMapping(value = "/picture/{tournamentId}/{id}",
        produces = { "application/json" }, 
        method = RequestMethod.DELETE)
    ResponseEntity<HttpResponse> deletePicture(@Parameter(in = ParameterIn.PATH, description = "ID del torneo al que corresponde la imagen", required=true, schema=@Schema()) @PathVariable("tournamentId") String tournamentId, @Parameter(in = ParameterIn.PATH, description = "ID de la imagen", required=true, schema=@Schema()) @PathVariable("id") String id, @Parameter(in = ParameterIn.HEADER, description = "Token de autenticación del usuario" ,schema=@Schema()) @RequestHeader(value="token", required=false) String token);


    @Operation(summary = "Obtención de fotos", description = "", tags={ "picture" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Imágenes obtenidas con éxito", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GetPicturesHttpResponse.class))),
        
        @ApiResponse(responseCode = "404", description = "No hay imágenes disponibles", content = @Content(mediaType = "application/json", schema = @Schema(implementation = HttpResponse.class))),
        
        @ApiResponse(responseCode = "500", description = "Error del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = HttpResponse.class))),
        
        @ApiResponse(responseCode = "503", description = "Base de datos/AWS no disponibles", content = @Content(mediaType = "application/json", schema = @Schema(implementation = HttpResponse.class))) })
    @RequestMapping(value = "/picture/{tournamentId}",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<GetPicturesHttpResponse> getPictures(@Parameter(in = ParameterIn.PATH, description = "", required=true, schema=@Schema()) @PathVariable("tournamentId") String tournamentId, @NotNull @Parameter(in = ParameterIn.QUERY, description = "" ,required=true,schema=@Schema()) @Valid @RequestParam(value = "pageNumber", required = true) Integer pageNumber, @Parameter(in = ParameterIn.QUERY, description = "" ,schema=@Schema()) @Valid @RequestParam(value = "place", required = false) String place, @Parameter(in = ParameterIn.QUERY, description = "" ,schema=@Schema()) @Valid @RequestParam(value = "date", required = false) String date);

    @Operation(summary = "Obtención de stats de una imagen", description = "", tags={ "picture" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Estadísticas de las imágenes obtenidas con éxito", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DownloadStatsHttpResponse.class))),
        
        @ApiResponse(responseCode = "404", description = "Imagen inexistente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = HttpResponse.class))),
        
        @ApiResponse(responseCode = "500", description = "Error del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = HttpResponse.class))),
        
        @ApiResponse(responseCode = "503", description = "Base de datos no disponible", content = @Content(mediaType = "application/json", schema = @Schema(implementation = HttpResponse.class))) })
    @RequestMapping(value = "/picture/stats",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<DownloadStatsHttpResponse> getPictureStats(@Parameter(in = ParameterIn.HEADER, description = "Token de autenticación del usuario" ,schema=@Schema()) @RequestHeader(value="token", required=false) String token);


    @Operation(summary = "Obtención de stats de una imagen", description = "", tags={ "picture" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Estadísticas de la imagen obtenidas con éxito", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DownloadStatsHttpResponse.class))),
        
        @ApiResponse(responseCode = "404", description = "Imagen inexistente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = HttpResponse.class))),
        
        @ApiResponse(responseCode = "500", description = "Error del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = HttpResponse.class))),
        
        @ApiResponse(responseCode = "503", description = "Base de datos no disponible", content = @Content(mediaType = "application/json", schema = @Schema(implementation = HttpResponse.class))) })
    @RequestMapping(value = "/picture/{id}/stats",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<DownloadStatsHttpResponse> getPictureStats(@Parameter(in = ParameterIn.PATH, description = "", required=true, schema=@Schema()) @PathVariable("id") String id, @Parameter(in = ParameterIn.HEADER, description = "Token de autenticación del usuario" ,schema=@Schema()) @RequestHeader(value="token", required=false) String token);

    
    /**
     * 
     * ATENCIÓN AL REGENERAR
     * Reemplazar Resource por MultipartFile
     * Agreegar MultipartHttpServletRequest request como primer parametro
     * 
     * **/
    @Operation(summary = "Carga de fotos", description = "", tags={ "picture" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Imágenes almacenadas con éxito", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UploadPicturesHttpResponse.class))),
        
        @ApiResponse(responseCode = "400", description = "Error en la request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = HttpResponse.class))),
        
        @ApiResponse(responseCode = "500", description = "Error del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = HttpResponse.class))),
        
        @ApiResponse(responseCode = "503", description = "Base de datos/AWS no disponibles", content = @Content(mediaType = "application/json", schema = @Schema(implementation = HttpResponse.class))) })
    @RequestMapping(value = "/picture/upload",
        produces = { "application/json" }, 
        consumes = { "multipart/form-data" }, 
        method = RequestMethod.POST)
    ResponseEntity<UploadPicturesHttpResponse> uploadPictures(MultipartHttpServletRequest request, @Parameter(in = ParameterIn.HEADER, description = "Token de autenticación del usuario" ,schema=@Schema()) @RequestHeader(value="token", required=false) String token, @Parameter(in = ParameterIn.DEFAULT, description = "",schema=@Schema()) @RequestParam(value="place", required=false)  String place, @Parameter(in = ParameterIn.DEFAULT, description = "",schema=@Schema()) @RequestParam(value="date", required=false)  String date, @Parameter(in = ParameterIn.DEFAULT, description = "",schema=@Schema()) @RequestParam(value="tournamentId", required=false)  String tournamentId, @Parameter(in = ParameterIn.DEFAULT, description = "",schema=@Schema()) @RequestParam(value="files", required=false)  List<MultipartFile> files);
}