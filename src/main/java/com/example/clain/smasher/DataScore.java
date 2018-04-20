package com.example.clain.smasher;

/**
 * Created by CLAIN on 18/04/2018.
 */



public class DataScore {
    private long id;
    private int score;
    private String pseudo;


    public DataScore(long id, int score, String pseudo, String date) {
        this.id = id;
        this.score = score;
        this.pseudo = pseudo;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }
}


