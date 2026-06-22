package com.assessment.accounts.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class NotOffensiveNicknameValidator implements ConstraintValidator<NotOffensiveNickname, String> {

    // TODO: Load the list from a db table or external config
    private static final Set<String> OFFENSIVE_NICKNAMES = Set.of(
            "offensive",
            "badword",
            "abuse",
            "fraud",
            "scam"
    );

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) {
            return true;
        }
        String normalised = value.trim().toLowerCase();
        return OFFENSIVE_NICKNAMES.stream().noneMatch(normalised::contains);
    }
}
