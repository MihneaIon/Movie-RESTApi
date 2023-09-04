package com.example.demo.mihnea.resource.impl;


import com.example.demo.mihnea.model.Director;
import com.example.demo.mihnea.modelDto.DirectorDto;
import com.example.demo.mihnea.resource.DirectorResource;
import com.example.demo.mihnea.service.DirectorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.inject.Inject;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(DirectorResourceImpl.PATH)
public class DirectorResourceImpl implements DirectorResource {

    public static final String PATH = "/director";

    @Inject
    private DirectorService directorService;

    @Override
    @PostMapping("/new-director")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Create a studio.", responses = {
            @ApiResponse(responseCode = "201", description = "The studio was created.", content = @Content(mediaType = MediaType.APPLICATION_JSON)),
            @ApiResponse(responseCode = "500", description = "Internal Error.")})
    public ResponseEntity<Director> createDirector(@RequestBody DirectorDto directorDto) {
        final Director saveDirector = directorService.create(directorDto);
        return ResponseEntity.ok(saveDirector);
    }

    @Override
    @GetMapping("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get studio.", responses = {
            @ApiResponse(responseCode = "200", description = "The studio was taken.", content = @Content(mediaType = MediaType.APPLICATION_JSON)),
            @ApiResponse(responseCode = "500", description = "Internal Error.")})
    public ResponseEntity<Director> findDirectorById(Long id) {
        final Director foundDirector = directorService.findById(id.intValue());
        return ResponseEntity.ok(foundDirector);
    }

    @Override
    @GetMapping()
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get studio.", responses = {
            @ApiResponse(responseCode = "200", description = "The studio was taken.", content = @Content(mediaType = MediaType.APPLICATION_JSON)),
            @ApiResponse(responseCode = "500", description = "Internal Error.")})
    public ResponseEntity<List<DirectorDto>> findAllDirector() {
        List<DirectorDto> directorDtos = directorService.findAllDirector();
        if(directorDtos== null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } else {
            return ResponseEntity.ok(directorDtos);
        }
    }

    @Override
    @PatchMapping()
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get studio.", responses = {
            @ApiResponse(responseCode = "200", description = "The studio was taken.", content = @Content(mediaType = MediaType.APPLICATION_JSON)),
            @ApiResponse(responseCode = "500", description = "Internal Error.")})
    public ResponseEntity<Director> updateDirector(@RequestBody DirectorDto directorDto) {
        final Director saveDirector = directorService.updateDirector(directorDto);
        return ResponseEntity.ok(saveDirector);
    }

    @Override
    @DeleteMapping("/delete")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get studio.", responses = {
            @ApiResponse(responseCode = "200", description = "The studio was taken.", content = @Content(mediaType = MediaType.APPLICATION_JSON)),
            @ApiResponse(responseCode = "405", description = "Method not allowed."),
            @ApiResponse(responseCode = "500", description = "Internal Error.")})
    public ResponseEntity<Void> deleteDirector(@RequestParam("id") Long id) {
        directorService.deleteDirector(id.intValue());
        return ResponseEntity.ok().build();
    }

    @Override
    @GetMapping("/director-movies")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get studio.", responses = {
            @ApiResponse(responseCode = "200", description = "The studio was taken.", content = @Content(mediaType = MediaType.APPLICATION_JSON)),
            @ApiResponse(responseCode = "500", description = "Internal Error.")})
    public ResponseEntity<List<DirectorDto>> getDirectorWithMoreThanXMovies(int movieCount) {
        List<DirectorDto> directorDtos = directorService.getDirectorWithMoreThanXMovies(movieCount);
        if(directorDtos== null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } else {
            return ResponseEntity.ok(directorDtos);
        }
    }
}
