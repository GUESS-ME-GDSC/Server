package gdsc.mju.guessme.domain.quiz.entity;

import gdsc.mju.guessme.domain.info.entity.Info;
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

    @ManyToOne
    @JoinColumn(name = "info_id")
    private Info info;

    @Column(nullable = false)
    private Long wrongFlag;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;

    @Builder
    public Scoring(Info info, Long wrongFlag, Person person) {
        this.info = info;
        this.wrongFlag = wrongFlag;
        this.person = person;
    }
}