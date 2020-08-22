package com.topov.forum.dto.request;

import com.topov.forum.validation.group.SuperuserTokenGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.GroupSequence;
import javax.validation.constraints.NotEmpty;
import javax.validation.groups.Default;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class SuperuserRegistrationRequest extends RegistrationRequest {
    @NotEmpty(
        message = "The token must not be empty",
        groups = SuperuserTokenGroup.class
    )
    private String token;
    public SuperuserRegistrationRequest(String token, String username, String email) {
        super(username, email);
        this.token =  token;
    }

    @GroupSequence(value = {SuperuserTokenGroup.class, Default.class})
    public interface SuperuserRegistrationValidationSequence {}
}
