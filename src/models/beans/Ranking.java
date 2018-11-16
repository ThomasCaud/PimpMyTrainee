package models.beans;

import java.io.Serializable;

public class Ranking implements Serializable {
	private static final long serialVersionUID = 1L;

	private int nbRespondents;
	private int scoreRank;
	private int bestScore;
	private int durationOfBestScore;

	public Ranking(int nbRespondents, int scoreRank, int bestScore, int durationOfBestScore) {
		super();
		this.nbRespondents = nbRespondents;
		this.scoreRank = scoreRank;
		this.bestScore = bestScore;
		this.durationOfBestScore = durationOfBestScore;
	}

	public Ranking() {
		super();
		nbRespondents = 0;
		scoreRank = 0;
		bestScore = 0;
		durationOfBestScore = 0;
	}

	public int getNbRespondents() {
		return nbRespondents;
	}

	public void setNbRespondents(int nbRespondents) {
		this.nbRespondents = nbRespondents;
	}

	public int getScoreRank() {
		return scoreRank;
	}

	public void setScoreRank(int scoreRank) {
		this.scoreRank = scoreRank;
	}

	public int getBestScore() {
		return bestScore;
	}

	public void setBestScore(int bestScore) {
		this.bestScore = bestScore;
	}

	public int getDurationOfBestScore() {
		return durationOfBestScore;
	}

	public void setDurationOfBestScore(int durationOfBestScore) {
		this.durationOfBestScore = durationOfBestScore;
	}

	@Override
	public String toString() {
		return "Ranking [nbRespondents=" + nbRespondents + ", scoreRank=" + scoreRank + ", bestScore=" + bestScore
				+ ", durationOfBestScore=" + durationOfBestScore + "]";
	}
}
