package gdsc.mju.guessme.domain.character.entity;

import gdsc.mju.guessme.domain.user.entity.User;
import java.time.LocalDate;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;

public class Character {

    @Id
    @GeneratedValue(generator = "uuid4")
    @GenericGenerator(name = "uuid4", strategy = "uuid4")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

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

    @Column
    @ColumnDefault("false")
    private Boolean like;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
