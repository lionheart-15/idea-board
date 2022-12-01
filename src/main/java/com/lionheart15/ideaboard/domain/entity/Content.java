package com.lionheart15.ideaboard.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "contents")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Content {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "text", nullable = false)
    private String content;

    @Column(name = "createAt", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createDate;

    @Column(name = "updateAt", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime editDate;

    @Column(name = "image_files")
    private String imageFile;

    @Column(name = "category", nullable = false)
    private String category;
}
