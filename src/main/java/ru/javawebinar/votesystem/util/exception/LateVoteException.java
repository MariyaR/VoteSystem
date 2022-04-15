package ru.javawebinar.votesystem.util.exception;

public class LateVoteException extends RuntimeException {
    public LateVoteException(String message) {
        super(message);
    }
}
