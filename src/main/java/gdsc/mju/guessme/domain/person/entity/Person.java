package gdsc.mju.guessme.domain.person.entity;

import gdsc.mju.guessme.domain.person.dto.UpdatePersonDto;
import gdsc.mju.guessme.domain.person.dto.UpdatePersonReqDto;
import gdsc.mju.guessme.domain.user.entity.User;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

@Getter
@DynamicInsert
@NoArgsConstructor
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

    @Column
    private String image;

    @Column
    private String voice;

    @Column(columnDefinition = "bigint default 0")
    private Long score;

    @Column(columnDefinition = "boolean default false")
    private Boolean favorite;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Person(String name, String relation, LocalDate birth, String residence, String image, String voice, Long score, Boolean favorite, User user) {
        this.name = name;
        this.relation = relation;
        this.birth = birth;
        this.residence = residence;
        this.image = image;
        this.voice = voice;
        this.score = score;
        this.favorite = favorite;
        this.user = user;
    }

    public void update(UpdatePersonDto updatePersonDto) {
        this.name = updateIfNotNull(this.name, updatePersonDto.getName());
        this.relation = updateIfNotNull(this.relation, updatePersonDto.getRelation());
        this.birth = updateIfNotNull(this.birth, updatePersonDto.getBirth());
        this.residence = updateIfNotNull(this.residence, updatePersonDto.getResidence());
        this.image = updateIfNotNull(this.image, updatePersonDto.getImageUrl());
        this.voice = updateIfNotNull(this.voice, updatePersonDto.getVoiceUrl());
    }

    private <T> T updateIfNotNull(T property, T value) {
        return value != null ? value : property;
    }
}
