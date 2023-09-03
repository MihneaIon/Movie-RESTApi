package com.example.demo.mihnea.resource.impl;


import com.example.demo.mihnea.model.Actor;
import com.example.demo.mihnea.modelDto.ActorDto;
import com.example.demo.mihnea.resource.ActorResource;
import com.example.demo.mihnea.service.ActorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.inject.Inject;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping(ActorResourceImpl.PATH)
public class ActorResourceImpl implements ActorResource {

    public static final String PATH = "/actor";

    @Inject
    private ActorService actorService;

    @Override
    @PostMapping("/new-actor")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Create a studio.", responses = {
            @ApiResponse(responseCode = "201", description = "The studio was created.", content = @Content(mediaType = MediaType.APPLICATION_JSON)),
            @ApiResponse(responseCode = "500", description = "Internal Error.")})
    public ResponseEntity<Actor> createActor(@RequestBody ActorDto actorDto) {
        final Actor savedActor = actorService.create(actorDto);
        return ResponseEntity.ok(savedActor);
    }

    @Override
    @GetMapping("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get studio.", responses = {
            @ApiResponse(responseCode = "200", description = "The studio was taken.", content = @Content(mediaType = MediaType.APPLICATION_JSON)),
            @ApiResponse(responseCode = "500", description = "Internal Error.")})
    public ResponseEntity<ActorDto> findActorById(Long id) {
        final ActorDto foundActorSto = actorService.findById(id);
        return ResponseEntity.ok(foundActorSto);
    }

    @Override
    @GetMapping()
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get studio.", responses = {
            @ApiResponse(responseCode = "200", description = "The studio was taken.", content = @Content(mediaType = MediaType.APPLICATION_JSON)),
            @ApiResponse(responseCode = "500", description = "Internal Error.")})
    public ResponseEntity<Set<ActorDto>> findAllActor() {
        Set<ActorDto> actorDtos = actorService.findAllActor();
        return ResponseEntity.ok(actorDtos);
    }

    @Override
    @PatchMapping()
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get studio.", responses = {
            @ApiResponse(responseCode = "200", description = "The studio was taken.", content = @Content(mediaType = MediaType.APPLICATION_JSON)),
            @ApiResponse(responseCode = "500", description = "Internal Error.")})
    public ResponseEntity<Actor> updateActor(@RequestBody ActorDto actorDto) {
        final Actor savedActor = actorService.updateActor(actorDto);
        return ResponseEntity.ok(savedActor);
    }

    @Override
    @DeleteMapping("/delete")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get studio.", responses = {
            @ApiResponse(responseCode = "200", description = "The studio was taken.", content = @Content(mediaType = MediaType.APPLICATION_JSON)),
            @ApiResponse(responseCode = "405", description = "Method not allowed."),
            @ApiResponse(responseCode = "500", description = "Internal Error.")})
    public ResponseEntity<Void> deleteActor(@RequestParam("id") Long id) {
        actorService.deleteActor(id);
        return ResponseEntity.ok().build();
    }
}
