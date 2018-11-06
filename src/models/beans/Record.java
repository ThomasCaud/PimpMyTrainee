package models.beans;

import java.io.Serializable;
import java.util.ArrayList;

public class Record implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private int score;
    private int duration;
    private Quiz quiz;
    private User trainee;
    private ArrayList<Answer> answers;

    public Record() {
	this.id = -1;
	this.score = 0;
	this.duration = 0;
	this.quiz = new Quiz();
	this.trainee = new User();
	this.answers = new ArrayList<Answer>();
    }

    public Record(int duration, Quiz quiz, User traine, ArrayList<Answer> answers) {
	this.score = this.calculateScore();
	this.duration = duration;
	this.quiz = quiz;
	this.trainee = traine;
	this.answers = answers;
    }

    private int calculateScore() {
	int score = 0;
	for (Answer answer : answers) {
	    if (answer.isCorrect()) {
		score++;
	    }
	}
	return score;
    }

    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
    }

    public int getScore() {
	return score;
    }

    public void setScore(int score) {
	this.score = score;
    }

    public int getDuration() {
	return duration;
    }

    public void setDuration(int duration) {
	this.duration = duration;
    }

    public Quiz getQuiz() {
	return quiz;
    }

    public void setQuiz(Quiz quiz) {
	this.quiz = quiz;
    }

    public User getTrainee() {
	return trainee;
    }

    public void setTrainee(User trainee) {
	this.trainee = trainee;
    }

    public ArrayList<Answer> getAnswers() {
	return answers;
    }

    public void setAnswers(ArrayList<Answer> answers) {
	this.answers = answers;
    }

    @Override
    public String toString() {
	return "Record [id=" + id + ", score=" + score + ", duration=" + duration + ", quiz=" + quiz + ", trainee="
		+ trainee + ", answers=" + answers + "]";
    }
}
