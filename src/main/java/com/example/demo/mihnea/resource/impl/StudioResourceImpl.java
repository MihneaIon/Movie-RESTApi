package com.example.demo.mihnea.resource.impl;

import com.example.demo.mihnea.model.Studio;
import com.example.demo.mihnea.modelDto.StudioDto;
import com.example.demo.mihnea.resource.StudioResource;
import com.example.demo.mihnea.service.StudioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.inject.Inject;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(StudioResourceImpl.PATH)
public class StudioResourceImpl implements StudioResource {

    public static final String PATH = "/studio";

    @Inject
    private StudioService studioService;

    @Override
    @PostMapping("/new-studio")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Create a studio.", responses = {
            @ApiResponse(responseCode = "201", description = "The studio was created.", content = @Content(mediaType = MediaType.APPLICATION_JSON)),
            @ApiResponse(responseCode = "500", description = "Internal Error.")})
    public ResponseEntity<Studio> createStudio(@RequestBody StudioDto studioDto) {
        final Studio saveStudio = studioService.create(studioDto);
        return ResponseEntity.ok(saveStudio);
    }

    @Override
    @GetMapping("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get studio.", responses = {
            @ApiResponse(responseCode = "200", description = "The studio was taken.", content = @Content(mediaType = MediaType.APPLICATION_JSON)),
            @ApiResponse(responseCode = "500", description = "Internal Error.")})
    public ResponseEntity<StudioDto> findStudioById(Integer id) {

        final StudioDto foundStudioDto = studioService.findById(id);
        if(foundStudioDto != null){
            System.out.println("findStudioById method called with id: " + foundStudioDto.getName());
            return ResponseEntity.ok(foundStudioDto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @Override
    @GetMapping()
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get studios.", responses = {
            @ApiResponse(responseCode = "200", description = "The studio was taken.", content = @Content(mediaType = MediaType.APPLICATION_JSON)),
            @ApiResponse(responseCode = "500", description = "Internal Error.")})
    public ResponseEntity<List<StudioDto>> findAllStudies() {
        final List<StudioDto> studioList = studioService.findAllStudios();
        return ResponseEntity.ok(studioList);
    }

    @Override
    @PatchMapping()
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get studio.", responses = {
            @ApiResponse(responseCode = "200", description = "The studio was taken.", content = @Content(mediaType = MediaType.APPLICATION_JSON)),
            @ApiResponse(responseCode = "500", description = "Internal Error.")})
    public ResponseEntity<Studio> updateStudio(@RequestBody StudioDto studioDto) {
        final Studio saveStudio = studioService.updateStudio(studioDto);
        return ResponseEntity.ok(saveStudio);
    }

    @Override
    @DeleteMapping("/delete")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get studio.", responses = {
            @ApiResponse(responseCode = "200", description = "The studio was taken.", content = @Content(mediaType = MediaType.APPLICATION_JSON)),
            @ApiResponse(responseCode = "405", description = "Method not allowed."),
            @ApiResponse(responseCode = "500", description = "Internal Error.")})
    public ResponseEntity<Void> deleteStudio(@RequestParam("id") Integer id) {
        studioService.deleteStudio(id);
        return ResponseEntity.ok().build();
    }

    @Override
    @GetMapping("/popular")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get studio.", responses = {
            @ApiResponse(responseCode = "200", description = "The studio was taken.", content = @Content(mediaType = MediaType.APPLICATION_JSON)),
            @ApiResponse(responseCode = "405", description = "Method not allowed."),
            @ApiResponse(responseCode = "500", description = "Internal Error.")})
    public List<StudioDto> getStudioWithMoreThenOneMovie() {
        List<StudioDto> studioDtoList = studioService.getStudiosWithMoreThanXMovies(1);
        return studioDtoList;
    }

    @Override
    @GetMapping("/eager-movie")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get studio.", responses = {
            @ApiResponse(responseCode = "200", description = "The studio was taken.", content = @Content(mediaType = MediaType.APPLICATION_JSON)),
            @ApiResponse(responseCode = "405", description = "Method not allowed."),
            @ApiResponse(responseCode = "500", description = "Internal Error.")})
    public ResponseEntity<StudioDto> getStudioWithEagerLoadedMovies(Integer id) {
        StudioDto studioDto = studioService.getStudioWithEagerLoadedMovies(id);
        if(studioDto != null){
            return ResponseEntity.ok(studioDto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

}
