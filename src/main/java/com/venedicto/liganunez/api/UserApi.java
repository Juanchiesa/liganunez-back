/**
 * NOTE: This class is auto generated by the swagger code generator program (3.0.46).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package com.venedicto.liganunez.api;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.venedicto.liganunez.model.http.GetUsersHttpResponse;
import com.venedicto.liganunez.model.http.HttpResponse;
import com.venedicto.liganunez.model.http.User;
import com.venedicto.liganunez.model.http.UserLoginHttpResponse;
import com.venedicto.liganunez.model.http.UserStatsResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-07-07T20:57:15.350055179Z[GMT]")
@Validated
public interface UserApi {

    @Operation(summary = "Validación de existencia de usuario por email", description = "", tags={ "user" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Usuario registrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = HttpResponse.class))),
        
        @ApiResponse(responseCode = "400", description = "Error en la request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = HttpResponse.class))),
        
        @ApiResponse(responseCode = "404", description = "Usuario no registrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = HttpResponse.class))),
        
        @ApiResponse(responseCode = "500", description = "Error del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = HttpResponse.class))),
        
        @ApiResponse(responseCode = "503", description = "Base de datos/Servicio email no disponibles", content = @Content(mediaType = "application/json", schema = @Schema(implementation = HttpResponse.class))) })
    @RequestMapping(value = "/user",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<HttpResponse> checkUserExistence(@Parameter(in = ParameterIn.HEADER, description = "Email ingresado" ,required=true,schema=@Schema()) @RequestHeader(value="email", required=true) String email);


    @Operation(summary = "Registro de un nuevo usuario", description = "", tags={ "user" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "201", description = "Usuario generado con éxito", content = @Content(mediaType = "application/json", schema = @Schema(implementation = HttpResponse.class))),
        
        @ApiResponse(responseCode = "400", description = "Error en la request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = HttpResponse.class))),
        
        @ApiResponse(responseCode = "500", description = "Error del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = HttpResponse.class))),
        
        @ApiResponse(responseCode = "503", description = "Base de datos/Servicio email no disponibles", content = @Content(mediaType = "application/json", schema = @Schema(implementation = HttpResponse.class))) })
    @RequestMapping(value = "/user",
        produces = { "application/json" }, 
        consumes = { "application/json" }, 
        method = RequestMethod.POST)
    ResponseEntity<HttpResponse> createUser(@Parameter(in = ParameterIn.DEFAULT, description = "Información del usuario (debe enviarse sin accessKey)", required=true, schema=@Schema()) @Valid @RequestBody User body);
    
    
    @Operation(summary = "Información del usuario", description = "", tags={ "user" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Usuarios listados correctamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GetUsersHttpResponse.class))),
        
        @ApiResponse(responseCode = "500", description = "Error del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = HttpResponse.class))),
        
        @ApiResponse(responseCode = "503", description = "Base de datos no disponible", content = @Content(mediaType = "application/json", schema = @Schema(implementation = HttpResponse.class))) })
    @RequestMapping(value = "/users",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<GetUsersHttpResponse> getUsers(@Parameter(in = ParameterIn.HEADER, description = "Token de autenticación del usuario" ,schema=@Schema()) @RequestHeader(value="token", required=false) String token);
    
    
    @Operation(summary = "Obtención de stats de usuarios", description = "", tags={ "user" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Stats obtenidas", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserStatsResponse.class))),
        
        @ApiResponse(responseCode = "500", description = "Error del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = HttpResponse.class))),
        
        @ApiResponse(responseCode = "503", description = "Base de datos no disponible", content = @Content(mediaType = "application/json", schema = @Schema(implementation = HttpResponse.class))) })
    @RequestMapping(value = "/user/stats",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<UserStatsResponse> getUserStats(@Parameter(in = ParameterIn.HEADER, description = "Token de autenticación del usuario" ,schema=@Schema()) @RequestHeader(value="token", required=false) String token);
    
    
    @Operation(summary = "Ingreso de un usuario al sistema", description = "", tags={ "user" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Login exitoso (sin email ni accessKey)", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserLoginHttpResponse.class))),
        
        @ApiResponse(responseCode = "400", description = "Error en la request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = HttpResponse.class))),
        
        @ApiResponse(responseCode = "500", description = "Error del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = HttpResponse.class))),
        
        @ApiResponse(responseCode = "503", description = "Base de datos no disponible", content = @Content(mediaType = "application/json", schema = @Schema(implementation = HttpResponse.class))) })
    @RequestMapping(value = "/user/login",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<UserLoginHttpResponse> loginUser(@Parameter(in = ParameterIn.HEADER, description = "Correo electrónico ingresado" ,required=true,schema=@Schema()) @RequestHeader(value="email", required=true) String email, @Parameter(in = ParameterIn.HEADER, description = "Contraseña ingresada" ,required=true,schema=@Schema()) @RequestHeader(value="password", required=true) String password);


    @Operation(summary = "Solicitud de actualización de password", description = "", tags={ "user" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "201", description = "Solicitud de actualización de password generada", content = @Content(mediaType = "application/json", schema = @Schema(implementation = HttpResponse.class))),
        
        @ApiResponse(responseCode = "404", description = "Usuario inexistente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = HttpResponse.class))),
        
        @ApiResponse(responseCode = "500", description = "Error del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = HttpResponse.class))),
        
        @ApiResponse(responseCode = "503", description = "Base de datos/Servicio email no disponibles", content = @Content(mediaType = "application/json", schema = @Schema(implementation = HttpResponse.class))) })
    @RequestMapping(value = "/user/update/password",
        produces = { "application/json" }, 
        method = RequestMethod.POST)
    ResponseEntity<HttpResponse> requestUserPasswordUpdate(@Parameter(in = ParameterIn.HEADER, description = "" ,required=true,schema=@Schema()) @RequestHeader(value="userEmail", required=true) String userEmail);


    @Operation(summary = "Actualización de password", description = "", tags={ "user" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Contraseña actualizada", content = @Content(mediaType = "application/json", schema = @Schema(implementation = HttpResponse.class))),
        
        @ApiResponse(responseCode = "404", description = "Código inexistente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = HttpResponse.class))),
        
        @ApiResponse(responseCode = "500", description = "Error del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = HttpResponse.class))),
        
        @ApiResponse(responseCode = "503", description = "Base de datos no disponible", content = @Content(mediaType = "application/json", schema = @Schema(implementation = HttpResponse.class))) })
    @RequestMapping(value = "/user/update/password",
        produces = { "application/json" }, 
        method = RequestMethod.PATCH)
    ResponseEntity<HttpResponse> updateUserPassword(@Parameter(in = ParameterIn.HEADER, description = "Código generado previamente" ,required=true,schema=@Schema()) @RequestHeader(value="requestCode", required=true) String requestCode);

    @Operation(summary = "Descarga de imagen", description = "", tags={ "user" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Descarga registrada", content = @Content(mediaType = "application/json", schema = @Schema(implementation = HttpResponse.class))),
        
        @ApiResponse(responseCode = "400", description = "Error en la request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = HttpResponse.class))),
        
        @ApiResponse(responseCode = "404", description = "Imagen inexistente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = HttpResponse.class))),
        
        @ApiResponse(responseCode = "500", description = "Error del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = HttpResponse.class))),
        
        @ApiResponse(responseCode = "503", description = "Base de datos no disponible", content = @Content(mediaType = "application/json", schema = @Schema(implementation = HttpResponse.class))) })
    @RequestMapping(value = "/user/download/picture/{pictureId}",
        produces = { "application/json" }, 
        method = RequestMethod.POST)
    ResponseEntity<HttpResponse> downloadPicture(@Parameter(in = ParameterIn.PATH, description = "ID de la imagen", required=true, schema=@Schema()) @PathVariable("pictureId") String pictureId, @Parameter(in = ParameterIn.HEADER, description = "Token de autenticación del usuario" ,schema=@Schema()) @RequestHeader(value="token", required=false) String token);
}