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

    // 'key'는 mysql의 예약어이므로 쓰면 에러.
    @Column(nullable = false)
    private Long infoId;
//    @ManyToOne
//    @JoinColumn(name = "info_id")
//    private Info info;

    @Column(nullable = false)
    private Long wrongFlag;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;

    @Builder
    public Scoring(Long infoId, Long wrongFlag, Person person) {
        this.infoId = infoId;
        this.wrongFlag = wrongFlag;
        this.person = person;
    }
}