package com.pjatk.MPR.exeption;

public class CatNotFoundExeption extends RuntimeException{
    public CatNotFoundExeption() {
        super("Cat is not found!");}
}
