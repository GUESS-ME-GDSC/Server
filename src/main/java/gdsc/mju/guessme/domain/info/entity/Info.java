package gdsc.mju.guessme.domain.info.entity;

import java.time.LocalDateTime;
import java.util.Set;
import javax.persistence.*;

import gdsc.mju.guessme.domain.quiz.entity.Scoring;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import gdsc.mju.guessme.domain.person.entity.Person;

@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@Entity
public class Info {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private String infoKey;

    @Column(nullable = false)
    private String infoValue;

    @ManyToOne(optional = false)
    @JoinColumn(name = "person_id")
    private Person person;

    @OneToMany(mappedBy = "info", cascade = CascadeType.ALL)
    private Set<Scoring> ScoringList;

    @Builder
    public Info(String infoKey, String infoValue, Person person) {
        this.infoKey = infoKey;
        this.infoValue = infoValue;
        this.person = person;
    }
}
