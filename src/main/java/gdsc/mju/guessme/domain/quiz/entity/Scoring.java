package gdsc.mju.guessme.domain.quiz.entity;

import gdsc.mju.guessme.domain.person.entity.Person;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter @Setter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@Entity
public class Scoring {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime correctAt;

    @Column(nullable = false)
    private String question;

    @Column(nullable = false)
    private Long wrongFlag;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;

    @Column(nullable = false)
    private String answer;

    @Builder
    public Scoring(String question, Long wrongFlag, Person person, String answer) {
        this.question = question;
        this.wrongFlag = wrongFlag;
        this.person = person;
        this.answer = answer;
    }
}