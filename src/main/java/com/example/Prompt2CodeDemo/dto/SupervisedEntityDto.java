package com.example.Prompt2CodeDemo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SupervisedEntityDto {
    
    private Long id;
    private String entityType;
    private String uknfCode;
    private String entityName;
    private String lei;
    private String nip;
    private String krs;
    private String street;
    private String buildingNumber;
    private String premisesNumber;
    private String postalCode;
    private String city;
    private String phoneNumber;
    private String email;
    private String uknfRegistrationNumber;
    private String entityStatus;
    private String entityCategory;
    private String operatorSector;
    private String entitySubsector;
    private Boolean isCrossBorder;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Set<UserDto> users;
}
