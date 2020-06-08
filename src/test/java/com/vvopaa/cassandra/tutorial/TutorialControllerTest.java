package com.vvopaa.cassandra.tutorial;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = TutorialController.class)
class TutorialControllerTest {
    @MockBean
    TutorialRepository tutorialRepository;
    @Autowired
    TutorialController tutorialController;

    @AfterEach
    void runAfterTest() {
        verifyNoMoreInteractions(tutorialRepository);
    }

    @Test
    void shouldGetAllTutorialResponseWhenNoTitlePassed() {
        List<Tutorial> tutorialList = List.of(new Tutorial(), new Tutorial());
        List<TutorialDto> tutorialDtoList = tutorialList.stream().map(TutorialDto::new).collect(Collectors.toList());
        when(tutorialRepository.findAll()).thenReturn(tutorialList);
        ResponseEntity<List<TutorialDto>> tutorialsResponse = tutorialController.getAllTutorials(null);
        assertEquals(HttpStatus.OK, tutorialsResponse.getStatusCode());
        assertNotNull(tutorialsResponse.getBody());
        assertEquals(tutorialDtoList, tutorialsResponse.getBody());
        verify(tutorialRepository, times(1)).findAll();
    }
}
