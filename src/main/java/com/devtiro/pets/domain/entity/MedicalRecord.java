package com.devtiro.pets.domain.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Document(indexName = "medical_records")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MedicalRecord extends Auditing {

    @Id
    private String id;

    @Field(type = FieldType.Keyword)
    private String petId;

    @Field(type = FieldType.Keyword)
    private String staffId;

    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute)
    private LocalDateTime appointmentDate;
    
    @Field(type = FieldType.Text)
    private String type;
    
    @Field(type = FieldType.Text)
    private String description;
    
    @Field(type = FieldType.Text)
    private String veterinarian;
    
    @Field(type = FieldType.Text)
    private String notes;

}
