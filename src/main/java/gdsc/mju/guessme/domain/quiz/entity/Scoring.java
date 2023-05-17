package gdsc.mju.guessme.domain.quiz.entity;

import gdsc.mju.guessme.domain.person.entity.Person;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter @Setter
@NoArgsConstructor
@Entity
public class Scoring {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String question;

    @Column(nullable = false)
    private Long wrongFlag;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;

    @Builder
    public Scoring(String question, Long wrongFlag, Person person) {
        this.question = question;
        this.wrongFlag = wrongFlag;
        this.person = person;
    }
}