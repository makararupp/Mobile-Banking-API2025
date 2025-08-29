package com.mk.mbanking.api.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class User {
    private Long id;
    private String name;
    private String gender;
    private String oneSignalId;
    private Boolean idDeleted;
    private Boolean isStudent;
    private String studentCardId;
}
