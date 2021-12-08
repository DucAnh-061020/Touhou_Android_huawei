package com.touhou.game.staticData;

import androidx.annotation.NonNull;

public class ScoreValue {
    private String Name;
    private int id,Score;
    public ScoreValue(){

    }
    public ScoreValue(String Name,int Score){
        this.Name= Name;
        this.Score = Score;
    }
    public String getName(){
        return Name;
    }
    public int getScore(){
        return Score;
    }

    public void setName(String Name){
        this.Name = Name;
    }
    public void setScore(int Score){
        this.Score = Score;
    }

    @NonNull
    @Override
    public String toString() {
        return Name+" Score: "+Score;
    }
}
