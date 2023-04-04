package com.bachtx.manga.dto.request;

import com.bachtx.manga.models.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Null;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Validated
@Data
public class MangaRequest {
    @NotEmpty
    @NotBlank
    @Length(min = 10)
    private String title;
    private String description;
    private String author;
    private List<Long> genresId;
    private MultipartFile image;
}
