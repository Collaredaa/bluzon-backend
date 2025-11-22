package com.bluzon.Community.controller;


import com.bluzon.Community.DTO.CommunityPostRequestDTO;
import com.bluzon.Community.DTO.CommunityPostResponseDTO;
import com.bluzon.Community.service.CommunityPostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/public/community/posts")
public class CommunityPostController {

    @Autowired
    private CommunityPostService communityPostService;

    @PostMapping("/create")
    public ResponseEntity<?> createPost(@Valid @RequestBody CommunityPostRequestDTO dto) {
        try {
            CommunityPostResponseDTO response = communityPostService.createPost(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao criar post");
        }
    }

    @GetMapping
    public ResponseEntity<List<CommunityPostResponseDTO>> listAll() {
        List<CommunityPostResponseDTO> posts = communityPostService.listAll();
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/tag/{tag}")
    public ResponseEntity<List<CommunityPostResponseDTO>> listByTag(@PathVariable String tag) {
        List<CommunityPostResponseDTO> posts = communityPostService.listByTag(tag);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        CommunityPostResponseDTO post = communityPostService.getById(id);
        if (post != null) {
            return ResponseEntity.ok(post);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post não encontrado");
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updatePost(
            @PathVariable Long id,
            @RequestBody CommunityPostRequestDTO dto
    ) {
        try {
            CommunityPostResponseDTO updated = communityPostService.updatePost(id, dto);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao atualizar post");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id) {
        try {
            communityPostService.deletePost(id);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao deletar post");
        }
    }
}
