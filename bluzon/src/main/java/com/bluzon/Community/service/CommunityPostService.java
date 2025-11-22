package com.bluzon.Community.service;



import com.bluzon.Community.DTO.CommunityPostRequestDTO;
import com.bluzon.Community.DTO.CommunityPostResponseDTO;
import com.bluzon.Community.Entity.CommunityPostEntity;
import com.bluzon.Community.repository.CommunityPostRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class CommunityPostService {

    private final CommunityPostRepository communityPostRepository;

    public CommunityPostService(CommunityPostRepository communityPostRepository) {
        this.communityPostRepository = communityPostRepository;
    }

    public CommunityPostResponseDTO createPost(CommunityPostRequestDTO dto) {
        if (dto.getTitle() == null || dto.getTitle().isBlank()) {
            throw new IllegalArgumentException("Título é obrigatório");
        }

        if (dto.getContent() == null || dto.getContent().isBlank()) {
            throw new IllegalArgumentException("Conteúdo é obrigatório");
        }

        if (dto.getAuthorName() == null || dto.getAuthorName().isBlank()) {
            throw new IllegalArgumentException("Nome do autor é obrigatório");
        }

        LocalDateTime now = LocalDateTime.now();

        CommunityPostEntity entity = new CommunityPostEntity();
        entity.setTitle(dto.getTitle());
        entity.setContent(dto.getContent());
        entity.setAuthorName(dto.getAuthorName());
        entity.setTopicTag(dto.getTopicTag());
        entity.setCreatedAt(now);
        entity.setUpdatedAt(now);

        CommunityPostEntity saved = communityPostRepository.save(entity);
        return toResponseDTO(saved);
    }

    public List<CommunityPostResponseDTO> listAll() {
        List<CommunityPostEntity> posts = communityPostRepository.findAll();

        posts.sort(Comparator.comparing(CommunityPostEntity::getCreatedAt).reversed());

        return posts.stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public List<CommunityPostResponseDTO> listByTag(String tag) {
        List<CommunityPostEntity> posts = communityPostRepository
                .findByTopicTagIgnoreCaseOrderByCreatedAtDesc(tag);

        return posts.stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public CommunityPostResponseDTO getById(Long id) {
        Optional<CommunityPostEntity> post = communityPostRepository.findById(id);

        return post
                .map(this::toResponseDTO)
                .orElse(null);
    }

    public CommunityPostResponseDTO updatePost(Long id, CommunityPostRequestDTO dto) {
        CommunityPostEntity post = communityPostRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Post não encontrado"));

        if (dto.getTitle() != null && !dto.getTitle().isBlank()) {
            post.setTitle(dto.getTitle());
        }
        if (dto.getContent() != null && !dto.getContent().isBlank()) {
            post.setContent(dto.getContent());
        }
        if (dto.getAuthorName() != null && !dto.getAuthorName().isBlank()) {
            post.setAuthorName(dto.getAuthorName());
        }
        if (dto.getTopicTag() != null && !dto.getTopicTag().isBlank()) {
            post.setTopicTag(dto.getTopicTag());
        }

        post.setUpdatedAt(LocalDateTime.now());

        CommunityPostEntity updated = communityPostRepository.save(post);
        return toResponseDTO(updated);
    }

    public void deletePost(Long id) {
        if (!communityPostRepository.existsById(id)) {
            throw new IllegalArgumentException("Post não encontrado");
        }
        communityPostRepository.deleteById(id);
    }

    // 🔁 Helpers de mapeamento (igual estilo SOS, mas isoladinhos)

    private CommunityPostResponseDTO toResponseDTO(CommunityPostEntity entity) {
        CommunityPostResponseDTO dto = new CommunityPostResponseDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setContent(entity.getContent());
        dto.setAuthorName(entity.getAuthorName());
        dto.setTopicTag(entity.getTopicTag());
        dto.setCreatedAt(String.valueOf(entity.getCreatedAt()));
        dto.setUpdatedAt(String.valueOf(entity.getUpdatedAt()));
        return dto;
    }
}