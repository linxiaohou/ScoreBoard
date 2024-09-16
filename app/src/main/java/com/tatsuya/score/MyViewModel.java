package com.tatsuya.score;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import java.util.Stack;

public class MyViewModel extends ViewModel {
    private final SavedStateHandle handle;
    private final Stack<Integer> scoreHistory = new Stack<>();
    private final Stack<Integer> teamHistory = new Stack<>();

    private final Integer A_TEAM = 1;
    private final Integer B_TEAM = 2;
    private final Integer RESET = 0;

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
        teamHistory.push(A_TEAM);
        scoreHistory.push(getaTeamScore().getValue());
        getaTeamScore().setValue(getaTeamScore().getValue() + n);
    }

    public void addbTeamScore(int n) {
        teamHistory.push(B_TEAM);
        scoreHistory.push(getbTeamScore().getValue());
        getbTeamScore().setValue(getbTeamScore().getValue() + n);
    }

    public void resetScore() {
        teamHistory.push(RESET);
        scoreHistory.push(getaTeamScore().getValue());
        getaTeamScore().setValue(0);
        scoreHistory.push(getbTeamScore().getValue());
        getbTeamScore().setValue(0);
    }

    public void undoScore() {
        if (!scoreHistory.empty()) {
            if (teamHistory.peek().equals(A_TEAM)) {
                teamHistory.pop();
                getaTeamScore().setValue(scoreHistory.pop());
            } else if (teamHistory.peek().equals(B_TEAM)) {
                teamHistory.pop();
                getbTeamScore().setValue(scoreHistory.pop());
            } else if (teamHistory.peek().equals(RESET)) {
                teamHistory.pop();
                getbTeamScore().setValue(scoreHistory.pop());
                getaTeamScore().setValue(scoreHistory.pop());

            }
        }
    }
}