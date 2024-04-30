package org.example;

public class Main {
    int a = 5;
    int b = 63;
    Pair c = new Pair(a, b);

    Pair doSmth(Pair c){
        if(c.getX() > 2){
            c.setX(6);
        }else{
            c.setY(57);
        }
        return c;
    }
}