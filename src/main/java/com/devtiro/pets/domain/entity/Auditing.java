package com.devtiro.pets.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class Auditing implements Persistable<String> {

    @CreatedBy
    protected String createdBy;

    @CreatedDate
    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second_millis)
    protected LocalDateTime createdAt;

    @LastModifiedBy
    protected String lastModifiedBy;

    @LastModifiedDate
    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second_millis)
    protected LocalDateTime updatedAt;

    @Override
    public abstract String getId();

    @Override
    public boolean isNew() {
        return getId() == null || (createdAt == null && createdBy == null);
    }
}
