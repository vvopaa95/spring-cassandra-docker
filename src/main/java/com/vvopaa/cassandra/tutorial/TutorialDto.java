package com.vvopaa.cassandra.tutorial;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;
import java.util.UUID;


@Data
@NoArgsConstructor
public class TutorialDto {
    private String id;
    private String title;
    private String description;
    private boolean published;

    public TutorialDto(Tutorial tutorial) {
        this.id = Optional.ofNullable(tutorial.getId()).map(UUID::toString).orElse(null);
        this.title = tutorial.getTitle();
        this.description = tutorial.getDescription();
        this.published = tutorial.isPublished();
    }
}
