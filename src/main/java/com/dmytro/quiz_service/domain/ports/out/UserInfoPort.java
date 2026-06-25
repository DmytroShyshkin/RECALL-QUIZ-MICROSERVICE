package com.dmytro.quiz_service.domain.ports.out;

import java.util.UUID;

public interface UserInfoPort {
    UUID getUserIdByEmail(String email);
    String extractEmailFromJwt(String jwt);
}
