package gdsc.mju.guessme.domain.person.entity;

import gdsc.mju.guessme.domain.user.entity.User;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Entity
public class Person {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String relation;

    @Column(nullable = false)
    private LocalDate birth;

    @Column(nullable = false)
    private String residence;

    @Column(nullable = false)
    private String image;

    @Column
    private String voice;

    @Column
    private Long score;

    @ColumnDefault("false")
    private Boolean favorite;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;
}
