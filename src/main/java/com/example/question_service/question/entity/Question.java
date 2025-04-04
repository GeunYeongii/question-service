package com.example.question_service.question.entity;

import com.example.question_service.common.entity.BaseEntity;
import com.example.question_service.question.dto.QuestionDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString(of = {"id", "subject", "content", "author", "status"})
@Table(name = "question")
public class Question extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private Long id;

    private String subject;
    private String content;
    private String author;

    @Enumerated(EnumType.STRING)
    private QuestionStatus status;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Answer> answers = new ArrayList<>();

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<QuestionHashTag> questionHashTags = new ArrayList<>();

    public void updateSubject(String subject) {
        this.subject = subject;
    }

    public void updateContent(String content) {
        this.content = content;
    }

    public void updateStatus(QuestionStatus status) {
        this.status = status;
    }

    public void updateAnswer(Answer answer) {
        this.answers.add(answer);
        answer.updateQuestion(this);
    }

    public void updateQuestionHashTag(QuestionHashTag questionHashTag) {
        this.questionHashTags.add(questionHashTag);
        questionHashTag.updateQuestion(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Question)) return false;
        return id != null && id.equals(((Question) o).getId());
    }

    @Override
    public int hashCode() {
        return id != null ? id.intValue() : 0;
    }

    @Builder
    public Question(String subject, String content, String author, QuestionStatus status) {
        this.subject = subject;
        this.content = content;
        this.author = author;
        this.status = status;
    }

    public static Question of(String subject, String content, String author, QuestionStatus status) {
        return Question.builder()
                .subject(subject)
                .content(content)
                .author(author)
                .status(status)
                .build();
    }

    public static Question of(QuestionDto questionDto) {
        return Question.builder()
                .subject(questionDto.getSubject())
                .content(questionDto.getContent())
                .author(questionDto.getAuthor())
                .status(questionDto.getStatus())
                .build();
    }
}
