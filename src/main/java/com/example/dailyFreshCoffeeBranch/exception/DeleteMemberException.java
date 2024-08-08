package com.example.dailyFreshCoffeeBranch.exception;

import org.springframework.security.core.AuthenticationException;

public class DeleteMemberException extends AuthenticationException {
    public DeleteMemberException(String message) {
        super(message);
    }
}
