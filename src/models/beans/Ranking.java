package models.beans;

import java.io.Serializable;

/**
 * This class does not exist in the data model It only exists in order to get
 * statistics with the DAO Record
 * 
 * @author Thomas
 *
 */
public class Ranking implements Serializable {
	private static final long serialVersionUID = 1L;

	private int nbRespondents;
	private int scoreRank;
	private int bestScore;
	private int durationOfBestScore;

	/**
	 * Default constructor
	 */
	public Ranking() {
		super();
		nbRespondents = 0;
		scoreRank = 0;
		bestScore = 0;
		durationOfBestScore = 0;
	}

	/**
	 * Constructor with parameters
	 * 
	 * @param nbRespondents
	 * @param scoreRank
	 * @param bestScore
	 * @param durationOfBestScore
	 */
	public Ranking(int nbRespondents, int scoreRank, int bestScore, int durationOfBestScore) {
		super();
		this.nbRespondents = nbRespondents;
		this.scoreRank = scoreRank;
		this.bestScore = bestScore;
		this.durationOfBestScore = durationOfBestScore;
	}

	/**
	 * get nbRespondents
	 * 
	 * @return nbRespondents
	 */
	public int getNbRespondents() {
		return nbRespondents;
	}

	/**
	 * set nbRespondents
	 * 
	 * @param nbRespondents
	 *            of type int
	 */
	public void setNbRespondents(int nbRespondents) {
		this.nbRespondents = nbRespondents;
	}

	/**
	 * get scoreRank
	 * 
	 * @return scoreRank
	 */
	public int getScoreRank() {
		return scoreRank;
	}

	/**
	 * set scoreRank
	 * 
	 * @param scoreRank
	 *            of type int
	 */
	public void setScoreRank(int scoreRank) {
		this.scoreRank = scoreRank;
	}

	/**
	 * get bestScore
	 * 
	 * @return bestScore
	 */
	public int getBestScore() {
		return bestScore;
	}

	/**
	 * set bestScore
	 * 
	 * @param bestScore
	 *            of type int
	 */
	public void setBestScore(int bestScore) {
		this.bestScore = bestScore;
	}

	/**
	 * get durationOfBestScore
	 * 
	 * @return durationOfBestScore
	 */
	public int getDurationOfBestScore() {
		return durationOfBestScore;
	}

	/**
	 * set durationOfBestScore
	 * 
	 * @param durationOfBestScore
	 *            of type int
	 */
	public void setDurationOfBestScore(int durationOfBestScore) {
		this.durationOfBestScore = durationOfBestScore;
	}

	/**
	 * Override toString
	 */
	@Override
	public String toString() {
		return "Ranking [nbRespondents=" + nbRespondents + ", scoreRank=" + scoreRank + ", bestScore=" + bestScore
				+ ", durationOfBestScore=" + durationOfBestScore + "]";
	}
}
