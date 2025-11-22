package com.bluzon.Community.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommunityPostRequestDTO {
    @NotBlank
    @Size(min = 3, max = 150)
    private String title;

    @NotBlank
    @Size(min = 5)
    private String content;

    @NotBlank
    @Size(min = 2, max = 80)
    private String authorName;

    @Size(max = 50)
    private String topicTag;
}
