package com.vvopaa.cassandra.tutorial;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Tutorial {
    @PrimaryKey
    private String id;

    private String title;
    private String description;
    private boolean published;
}
