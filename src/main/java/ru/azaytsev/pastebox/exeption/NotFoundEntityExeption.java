package ru.azaytsev.pastebox.exeption;

public class NotFoundEntityExeption extends RuntimeException {
    public NotFoundEntityExeption(String s) {
        super(s);
    }
}
