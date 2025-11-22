package com.bluzon.Community.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommunityPostResponseDTO {
    private Long id;
    private String title;
    private String content;
    private String authorName;
    private String topicTag;
    private String createdAt;
    private String updatedAt;
}
