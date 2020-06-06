package com.vvopaa.cassandra.tutorial;

import com.datastax.oss.driver.api.core.uuid.Uuids;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Log4j2
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TutorialController {
    private final TutorialRepository tutorialRepository;

    @GetMapping("/tutorials")
    public ResponseEntity<List<TutorialDto>> getAllTutorials(@RequestParam(required = false) String title) {
        return Optional.of(Optional.ofNullable(title)
                .map(tutorialRepository::findByTitleContaining)
                .orElseGet(tutorialRepository::findAll))
                .filter(tutorials -> !tutorials.isEmpty())
                .map(tutorials -> tutorials.stream().map(TutorialDto::new).collect(Collectors.toList()))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }

    @GetMapping("/tutorials/{id}")
    public ResponseEntity<TutorialDto> getTutorialById(@PathVariable("id") String id) {
        return tutorialRepository.findById(UUID.fromString(id))
                .map(TutorialDto::new)
                .map(tutorial -> new ResponseEntity<>(tutorial, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/tutorials")
    public ResponseEntity<TutorialDto> createTutorial(@RequestBody TutorialDto tutorialDto) {
        try {
            Tutorial savedTutorial = tutorialRepository.save(new Tutorial(Uuids.timeBased(), tutorialDto.getTitle(), tutorialDto.getDescription(), tutorialDto.isPublished()));
            return new ResponseEntity<>(new TutorialDto(savedTutorial), HttpStatus.CREATED);
        } catch (Exception e) {
            log.info(e.getMessage());
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PutMapping("/tutorials/{id}")
    public ResponseEntity<TutorialDto> updateTutorial(@PathVariable("id") String id, @RequestBody TutorialDto tutorial) {
        return tutorialRepository.findById(UUID.fromString(id))
                .map(innerTutorial -> innerTutorial.setTitle(tutorial.getTitle())
                        .setDescription(tutorial.getDescription())
                        .setPublished(tutorial.isPublished()))
                .map(innerTutorial -> ResponseEntity.ok(new TutorialDto(tutorialRepository.save(innerTutorial))))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/tutorials/{id}")
    public ResponseEntity<HttpStatus> deleteTutorial(@PathVariable("id") String id) {
        try {
            tutorialRepository.deleteById(UUID.fromString(id));
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

    @DeleteMapping("/tutorials")
    public ResponseEntity<HttpStatus> deleteAllTutorials() {
        tutorialRepository.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @GetMapping("/tutorials/published")
    public ResponseEntity<List<TutorialDto>> findByPublished() {
        return Optional.of(tutorialRepository.findByPublished(true))
                .filter(tutorials -> !tutorials.isEmpty())
                .map(tutorials -> tutorials.stream().map(TutorialDto::new).collect(Collectors.toList()))
                .map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

}
