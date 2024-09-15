package com.tatsuya.score;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Map;
import java.util.Stack;

public class MyViewModel extends ViewModel {
    private MutableLiveData<Integer> aTeamScore;
    private MutableLiveData<Integer> bTeamScore;
    private final Stack<Integer> scoreHistory = new Stack<>();
    private final Stack<Boolean> teamHistory = new Stack<>();

    public MutableLiveData<Integer> getaTeamScore() {
        if (aTeamScore == null) {
            aTeamScore = new MutableLiveData<>();
            aTeamScore.setValue(0);
        }
        return aTeamScore;
    }

    public MutableLiveData<Integer> getbTeamScore() {
        if (bTeamScore == null) {
            bTeamScore = new MutableLiveData<>();
            bTeamScore.setValue(0);
        }
        return bTeamScore;
    }

    public void addaTeamScore(int n) {
        teamHistory.push(true);
        scoreHistory.push(aTeamScore.getValue());
        aTeamScore.setValue(aTeamScore.getValue() + n);
    }

    public void addbTeamScore(int n) {
        teamHistory.push(false);
        scoreHistory.push(bTeamScore.getValue());
        bTeamScore.setValue(bTeamScore.getValue() + n);
    }

    public void resetScore() {
        aTeamScore.setValue(0);
        bTeamScore.setValue(0);
    }

    public void undoScore() {
        if (!scoreHistory.empty()) {
            if (teamHistory.pop()) {
                aTeamScore.setValue(scoreHistory.pop());
            } else {
                bTeamScore.setValue(scoreHistory.pop());
            }
        }
    }
}