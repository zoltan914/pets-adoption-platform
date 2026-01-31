package com.devtiro.pets.domain.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Document(indexName = "photos")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Photo extends Auditing {

    @Id
    private String id;

    @Field(type = FieldType.Keyword)
    private String petId;

    @Field(type = FieldType.Text)
    private String url;

}
