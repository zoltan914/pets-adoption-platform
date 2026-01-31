package com.devtiro.pets.domain.entity;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.GeoPointField;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

import java.util.ArrayList;
import java.util.List;


@EqualsAndHashCode(callSuper = true)
@Document(indexName = "pets")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pet extends Auditing {

    @Id
    private String id;

    @Field(type = FieldType.Keyword)
    private String staffId;

    @Field(type = FieldType.Text)
    private String staffName;

    @Field(type = FieldType.Keyword)
    private String staffEmail;

    @Field(type = FieldType.Text)
    private String name;

    @Field(type = FieldType.Text)
    private String description;

    @Field(type = FieldType.Keyword)
    private Species species;

    @Field(type = FieldType.Integer)
    private Integer age;

    @Field(type = FieldType.Keyword)
    private PetStatus status;

    @Field(type = FieldType.Keyword)
    private PetSize petSize;

    @Field(type = FieldType.Nested)
    private Address address;

    @GeoPointField
    private GeoPoint location;

    @Field(type = FieldType.Nested)
    private List<Photo> photos = new ArrayList<>();

}
