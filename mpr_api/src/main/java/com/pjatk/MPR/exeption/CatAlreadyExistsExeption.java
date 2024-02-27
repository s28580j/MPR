package com.pjatk.MPR.exeption;

public class CatAlreadyExistsExeption extends RuntimeException{
    public CatAlreadyExistsExeption(){
        super("Cat is already there ");
    }
}
