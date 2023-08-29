package tech.xavi.wschat.entity.sub;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Role {
    USER("USER");
    private final String role;

}
