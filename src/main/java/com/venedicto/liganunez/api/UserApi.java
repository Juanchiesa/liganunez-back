/**
 * NOTE: This class is auto generated by the swagger code generator program (3.0.46).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package com.venedicto.liganunez.api;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.venedicto.liganunez.model.http.HttpResponse;
import com.venedicto.liganunez.model.http.User;
import com.venedicto.liganunez.model.http.UserLoginHttpResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-06-18T18:56:59.203396144Z[GMT]")
@Validated
public interface UserApi {

    @Operation(summary = "Validación de existencia de usuario por email", description = "", tags={ "user" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Usuario registrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = HttpResponse.class))),
        
        @ApiResponse(responseCode = "400", description = "Error en la request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = HttpResponse.class))),
        
        @ApiResponse(responseCode = "404", description = "Usuario no registrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = HttpResponse.class))),
        
        @ApiResponse(responseCode = "500", description = "Error del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = HttpResponse.class))),
        
        @ApiResponse(responseCode = "503", description = "Base de datos/Servicio email no disponibles", content = @Content(mediaType = "application/json", schema = @Schema(implementation = HttpResponse.class))) })
            @GetMapping(value = "/user",
                    produces = {"application/json"})
    ResponseEntity<HttpResponse> checkUserExistence(@Parameter(in = ParameterIn.HEADER, description = "Email ingresado" ,required=true,schema=@Schema()) @RequestHeader(required = true) String email);


    @Operation(summary = "Registro de un nuevo usuario", description = "", tags={ "user" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "201", description = "Usuario generado con éxito", content = @Content(mediaType = "application/json", schema = @Schema(implementation = HttpResponse.class))),
        
        @ApiResponse(responseCode = "400", description = "Error en la request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = HttpResponse.class))),
        
        @ApiResponse(responseCode = "500", description = "Error del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = HttpResponse.class))),
        
        @ApiResponse(responseCode = "503", description = "Base de datos/Servicio email no disponibles", content = @Content(mediaType = "application/json", schema = @Schema(implementation = HttpResponse.class))) })
            @PostMapping(value = "/user",
                    produces = {"application/json"},
                    consumes = {"application/json"})
    ResponseEntity<HttpResponse> createUser(@Parameter(in = ParameterIn.DEFAULT, description = "Información del usuario (debe enviarse sin accessKey)", required=true, schema=@Schema()) @Valid @RequestBody User body);


    @Operation(summary = "Ingreso de un usuario al sistema", description = "", tags={ "user" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Login exitoso (sin email ni accessKey)", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserLoginHttpResponse.class))),
        
        @ApiResponse(responseCode = "400", description = "Error en la request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = HttpResponse.class))),
        
        @ApiResponse(responseCode = "500", description = "Error del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = HttpResponse.class))),
        
        @ApiResponse(responseCode = "503", description = "Base de datos no disponible", content = @Content(mediaType = "application/json", schema = @Schema(implementation = HttpResponse.class))) })
            @GetMapping(value = "/user/login",
                    produces = {"application/json"})
    ResponseEntity<UserLoginHttpResponse> loginUser(@Parameter(in = ParameterIn.HEADER, description = "Correo electrónico ingresado" ,required=true,schema=@Schema()) @RequestHeader(required = true) String email, @Parameter(in = ParameterIn.HEADER, description = "Contraseña ingresada" ,required=true,schema=@Schema()) @RequestHeader(required = true) String password);


    @Operation(summary = "Cierre de sesión de un usuario", description = "", tags={ "user" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Sesión cerrada", content = @Content(mediaType = "application/json", schema = @Schema(implementation = HttpResponse.class))),
        
        @ApiResponse(responseCode = "400", description = "Error en la request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = HttpResponse.class))),
        
        @ApiResponse(responseCode = "404", description = "Usuario inexistente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = HttpResponse.class))),
        
        @ApiResponse(responseCode = "500", description = "Error del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = HttpResponse.class))) })
            @GetMapping(value = "/user/logout",
                    produces = {"application/json"})
    ResponseEntity<HttpResponse> logoutUser(@Parameter(in = ParameterIn.HEADER, description = "Email del usuario" ,required=true,schema=@Schema()) @RequestHeader(required = true) String email);


    @Operation(summary = "Solicitud de actualización de password", description = "", tags={ "user" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "201", description = "Solicitud de actualización de password realizada", content = @Content(mediaType = "application/json", schema = @Schema(implementation = HttpResponse.class))),
        
        @ApiResponse(responseCode = "404", description = "Usuario inexistente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = HttpResponse.class))),
        
        @ApiResponse(responseCode = "500", description = "Error del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = HttpResponse.class))),
        
        @ApiResponse(responseCode = "503", description = "Base de datos/Servicio email no disponibles", content = @Content(mediaType = "application/json", schema = @Schema(implementation = HttpResponse.class))) })
            @PostMapping(value = "/user/update/password",
                    produces = {"application/json"})
    ResponseEntity<HttpResponse> requestUserPasswordUpdate();


    @Operation(summary = "Actualización de password", description = "", tags={ "user" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Contraseña actualizada", content = @Content(mediaType = "application/json", schema = @Schema(implementation = HttpResponse.class))),
        
        @ApiResponse(responseCode = "403", description = "No autorizado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = HttpResponse.class))),
        
        @ApiResponse(responseCode = "404", description = "Usuario inexistente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = HttpResponse.class))),
        
        @ApiResponse(responseCode = "500", description = "Error del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = HttpResponse.class))),
        
        @ApiResponse(responseCode = "503", description = "Base de datos no disponible", content = @Content(mediaType = "application/json", schema = @Schema(implementation = HttpResponse.class))) })
            @PatchMapping(value = "/user/update/password",
                    produces = {"application/json"})
    ResponseEntity<HttpResponse> updateUserPassword(@Parameter(in = ParameterIn.HEADER, description = "Nueva contraseña ingresada" ,required=true,schema=@Schema()) @RequestHeader(required = true) String newPassword, @Parameter(in = ParameterIn.HEADER, description = "Código recibido por correo" ,schema=@Schema()) @RequestHeader(required = false) String authCode);


    @Operation(summary = "Actualización del nivel de permisos", description = "", tags={ "user" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Permisos actualizados con éxito", content = @Content(mediaType = "application/json", schema = @Schema(implementation = HttpResponse.class))),
        
        @ApiResponse(responseCode = "403", description = "No autorizado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = HttpResponse.class))),
        
        @ApiResponse(responseCode = "404", description = "Usuario inexistente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = HttpResponse.class))),
        
        @ApiResponse(responseCode = "500", description = "Error del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = HttpResponse.class))),
        
        @ApiResponse(responseCode = "503", description = "Base de datos no disponible", content = @Content(mediaType = "application/json", schema = @Schema(implementation = HttpResponse.class))) })
            @PatchMapping(value = "/user/update/permissions",
                    produces = {"application/json"})
    ResponseEntity<HttpResponse> updateUserPermissionLevel(@Parameter(in = ParameterIn.HEADER, description = "Nivel de permisos" ,required=true,schema=@Schema()) @RequestHeader(required = true) Integer permissionLevel, @Parameter(in = ParameterIn.HEADER, description = "Token de autenticación del usuario" ,schema=@Schema()) @RequestHeader(required = false) String token);

}

