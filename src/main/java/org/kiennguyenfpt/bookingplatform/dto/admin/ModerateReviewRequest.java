package org.kiennguyenfpt.bookingplatform.dto.admin;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.kiennguyenfpt.bookingplatform.constant.ReviewStatus;

@Data
public class ModerateReviewRequest {
    
    @NotBlank(message = "Review ID is required")
    private String reviewId;
    
    @NotNull(message = "Status is required")
    private ReviewStatus status;
    
    private String moderationNotes;
}
