package com.devtiro.pets.domain.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Document(indexName = "adoption_applications")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdoptionApplication extends Auditing {

    @Id
    private String id;

    // Reference to the pet being applied for
    @Field(type = FieldType.Keyword)
    private String petId;

    // Reference to the applicant user
    @Field(type = FieldType.Keyword)
    private String applicantId;

    // Personal Contact Information
    @Field(type = FieldType.Text)
    private String firstName; // TODO: get from auth info

    @Field(type = FieldType.Text)
    private String lastName; // TODO: get from auth info

    @Field(type = FieldType.Keyword)
    private String phoneNumber; // TODO: get from auth info

    @Field(type = FieldType.Keyword)
    private String alternatePhone;

    @Field(type = FieldType.Keyword)
    private String email; // TODO: get from auth info

    @Field(type = FieldType.Nested)
    private Address address;

    // Living Situation Details
    @Field(type = FieldType.Nested)
    private LivingSituation livingSituation;

    // Additional Information
    @Field(type = FieldType.Text)
    private String adoptionReason;

    @Field(type = FieldType.Text)
    private String petExperience;

    @Field(type = FieldType.Text)
    private String veterinarianName;

    @Field(type = FieldType.Text)
    private String veterinarianContact;

    @Field(type = FieldType.Text)
    private String references; // Personal references

    // Application Status
    @Field(type = FieldType.Keyword)
    private AdoptionApplicationStatus status;

    // Additional applicant comments
    @Field(type = FieldType.Text)
    private String additionalComments;

    @Field(type = FieldType.Text)
    private String staffNotes; // Notes added by staff during review

    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second)
    private LocalDateTime submittedAt; // When the application was submitted (not draft anymore)
}
