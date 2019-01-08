package models.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Bean that represents a record
 * 
 * @author Thomas
 *
 */
public class Record implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private int score;
	private int duration;
	private Quiz quiz;
	private User trainee;
	private UUID contextID;
	private ArrayList<Answer> answers;
	private Ranking ranking;

	/**
	 * Default constructor
	 */
	public Record() {
		this.id = -1;
		this.score = 0;
		this.duration = 0;
		this.quiz = new Quiz();
		this.trainee = new User();
		this.answers = new ArrayList<Answer>();
		this.contextID = UUID.randomUUID();
		this.ranking = new Ranking();
	}

	/**
	 * Constructor with parameters
	 * 
	 * @param duration
	 * @param quiz
	 * @param trainee
	 * @param answers
	 */
	public Record(int duration, Quiz quiz, User trainee, ArrayList<Answer> answers) {
		this.score = this.calculateScore();
		this.duration = duration;
		this.quiz = quiz;
		this.trainee = trainee;
		this.answers = answers;
		this.contextID = UUID.randomUUID();
		this.ranking = new Ranking();
	}

	/**
	 * Constructor with parameters
	 * 
	 * @param duration
	 * @param quiz
	 * @param traine
	 * @param answers
	 * @param contextID
	 */
	public Record(int duration, Quiz quiz, User traine, ArrayList<Answer> answers, UUID contextID) {
		this.score = this.calculateScore();
		this.duration = duration;
		this.quiz = quiz;
		this.trainee = traine;
		this.answers = answers;
		this.contextID = contextID;
		this.ranking = new Ranking();
	}

	/**
	 * 
	 * @return int the number of correct answers in the record
	 */
	private int calculateScore() {
		int score = 0;
		for (Answer answer : answers) {
			if (answer.getIsCorrect()) {
				score++;
			}
		}
		return score;
	}

	/**
	 * get id
	 * 
	 * @return id
	 */
	public int getId() {
		return id;
	}

	/**
	 * set id
	 * 
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * get contextID
	 * 
	 * @return contextID
	 */
	public UUID getContextId() {
		return contextID;
	}

	/**
	 * set contextID
	 * 
	 * @param contextID
	 */
	public void setContextId(UUID contextID) {
		this.contextID = contextID;
	}

	/**
	 * get score
	 * 
	 * @return score
	 */
	public int getScore() {
		return score;
	}

	/**
	 * set score
	 * 
	 * @param score
	 */
	public void setScore(int score) {
		this.score = score;
	}

	/**
	 * get getDuration
	 * 
	 * @return getDuration
	 */
	public int getDuration() {
		return duration;
	}

	/**
	 * set duration
	 * 
	 * @param duration
	 */
	public void setDuration(int duration) {
		this.duration = duration;
	}

	/**
	 * get quiz
	 * 
	 * @return quiz
	 */
	public Quiz getQuiz() {
		return quiz;
	}

	/**
	 * set quiz
	 * 
	 * @param quiz
	 */
	public void setQuiz(Quiz quiz) {
		this.quiz = quiz;
	}

	/**
	 * get trainee
	 * 
	 * @return trainee
	 */
	public User getTrainee() {
		return trainee;
	}

	/**
	 * set trainee
	 * 
	 * @param trainee
	 */
	public void setTrainee(User trainee) {
		this.trainee = trainee;
	}

	/**
	 * get answers
	 * 
	 * @return answers an ArrayList of Answer
	 */
	public ArrayList<Answer> getAnswers() {
		return answers;
	}

	/**
	 * set answers
	 * 
	 * @param answers
	 */
	public void setAnswers(ArrayList<Answer> answers) {
		this.answers = answers;
	}

	/**
	 * get ranking
	 * 
	 * @return ranking of type Ranking
	 */
	public Ranking getRanking() {
		return ranking;
	}

	/**
	 * set ranking
	 * 
	 * @param ranking
	 */
	public void setRanking(Ranking ranking) {
		this.ranking = ranking;
	}

	@Override
	public String toString() {
		return "Record [id=" + id + ", score=" + score + ", duration=" + duration + ", quiz=" + quiz + ", trainee="
				+ trainee + ", answers=" + answers + "]";
	}

	/**
	 * get getScoreRank of the ranking
	 * 
	 * @return scoreRank of the ranking
	 */
	public int getScoreRank() {
		return this.ranking.getScoreRank();
	}
}
