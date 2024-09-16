package com.tatsuya.score;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import java.util.Stack;

public class MyViewModel extends ViewModel {
    private SavedStateHandle handle;
    private final Stack<Integer> scoreHistory = new Stack<>();
    private final Stack<Boolean> teamHistory = new Stack<>();

    public static final String KEY_A_NUMBER = "Key_A_Number";
    public static final String KEY_B_NUMBER = "Key_B_Number";

    public MyViewModel(SavedStateHandle handle) {
        this.handle = handle;
    }

    public MutableLiveData<Integer> getaTeamScore() {
        if (!handle.contains(KEY_A_NUMBER)) {
            handle.set(KEY_A_NUMBER, 0);
        }
        return handle.getLiveData(KEY_A_NUMBER);
    }

    public MutableLiveData<Integer> getbTeamScore() {
        if (!handle.contains(KEY_B_NUMBER)) {
            handle.set(KEY_B_NUMBER, 0);
        }
        return handle.getLiveData(KEY_B_NUMBER);
    }

    public void addaTeamScore(int n) {
        teamHistory.push(true);
        scoreHistory.push(getaTeamScore().getValue());
        getaTeamScore().setValue(getaTeamScore().getValue() + n);
    }

    public void addbTeamScore(int n) {
        teamHistory.push(false);
        scoreHistory.push(getbTeamScore().getValue());
        getbTeamScore().setValue(getbTeamScore().getValue() + n);
    }

    public void resetScore() {
        teamHistory.push(true);
        scoreHistory.push(getaTeamScore().getValue());
        getaTeamScore().setValue(0);

        teamHistory.push(false);
        scoreHistory.push(getbTeamScore().getValue());
        getbTeamScore().setValue(0);
    }

    public void undoScore() {
        if (!scoreHistory.empty()) {
            if (teamHistory.pop()) {
                getaTeamScore().setValue(scoreHistory.pop());
            } else {
                getbTeamScore().setValue(scoreHistory.pop());
            }
        }
    }
}