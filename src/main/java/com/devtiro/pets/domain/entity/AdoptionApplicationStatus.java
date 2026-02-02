package com.devtiro.pets.domain.entity;

/**
 * Enum representing the status of an adoption application
 */
public enum AdoptionApplicationStatus {
    DRAFT,          // Application saved as draft, not submitted yet
    SUBMITTED,      // Application submitted and awaiting review
    UNDER_REVIEW,   // Application is being reviewed by staff
    APPROVED,       // Application has been approved
    REJECTED,       // Application has been rejected
    WITHDRAWN       // Application has been withdrawn by the applicant
}
