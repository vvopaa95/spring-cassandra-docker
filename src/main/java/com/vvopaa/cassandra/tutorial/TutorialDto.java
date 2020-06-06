package com.vvopaa.cassandra.tutorial;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class TutorialDto {
    private String id;
    private String title;
    private String description;
    private boolean published;

    public TutorialDto(Tutorial tutorial) {
        this.id = tutorial.getId().toString();
        this.title = tutorial.getTitle();
        this.description = tutorial.getDescription();
        this.published = tutorial.isPublished();
    }
}
