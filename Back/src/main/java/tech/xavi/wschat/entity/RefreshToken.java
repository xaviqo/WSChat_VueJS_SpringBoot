package tech.xavi.wschat.entity;

import lombok.*;

import javax.persistence.*;


@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RefreshToken {

    @Id @Column
    private String token;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private ChatUser user;

}
