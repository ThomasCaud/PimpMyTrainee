package models.forms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.interfaces.QuizDAO;
import dao.interfaces.ThemeDAO;
import models.beans.PossibleAnswer;
import models.beans.Question;
import models.beans.Quiz;
import models.beans.Theme;

public class CreateQuizForm extends AbstractForm {
	// Variables that represents each field of the form
	private static final String FIELD_TITLE = "title";
	private static final String FIELD_THEME = "theme";
	private static final String FIELD_SUBMIT = "submit";
	private QuizDAO quizDAO;
	private ThemeDAO themeDAO;
	
	public CreateQuizForm( QuizDAO quizDAO, ThemeDAO themeDAO ) {
		super();
		this.quizDAO = quizDAO;
		this.themeDAO = themeDAO;
	}
	
	public void processTitleValidation( String title, Quiz quiz ) {
		if( isNullOrEmpty(title) ) {
			setError(FIELD_TITLE, "The title is empty.");
		}
		quiz.setTitle(title);
	}
	
	public void processThemeValidation( String themeStr, Quiz quiz ) {
		if( isNullOrEmpty(themeStr) ) {
			setError(FIELD_THEME, "The theme is empty.");
			return;
		}
		
		Integer themeId;
		try {
			themeId = Integer.parseInt(themeStr);
		} catch(NumberFormatException e) {
			setError(FIELD_THEME, "The theme is incorrect.");
			return;
		}
		
		Theme theme = themeDAO.findThemeByID(themeId);
		if( theme == null ) {
			setError(FIELD_THEME, "The theme is incorrect.");
			return;
		}
		
		quiz.setTheme(theme);
	}
	
	public ArrayList<Question> getQuestionsFromRequest(HttpServletRequest request) {
		
		ArrayList<Question> questions = new ArrayList<Question>();
		
		// Recherche des noms des parametres dans la variable POST qui correspondent aux labels des questions
		String patternQuestionLabel = "(question_)([0-9]+)(_label)";
		List<String> listParamQuestions = request.getParameterMap().keySet().stream().filter(s -> Pattern.matches(patternQuestionLabel, s)).collect(Collectors.toList());
		
		Collections.sort(listParamQuestions, new Comparator<String>() {
		    public int compare(String o1, String o2) {
		    	Integer i1 = Integer.parseInt(o1.replace("question_","").replace("_label",""));
		        Integer i2 = Integer.parseInt(o2.replace("question_","").replace("_label",""));
		        return (i1 < i2 ? -1 : (i1 == i2 ? 0 : 1));
		    }
		});
		
		for(String paramQuestion : listParamQuestions) {
			String labelQuestion = request.getParameter(paramQuestion);
			
			Question question = new Question();
			question.setLabel(labelQuestion);
			
			// Recherche des noms des parametres dans la variable POST qui correspondent aux labels des réponses
			String questionIndex = paramQuestion.replace("question_","").replace("_label","");
			String patternPossibleAnswerLabel = "(question_)"+questionIndex+"(_possibleAnswer_)([0-9]+)(_label)";
			List<String> listParamPossibleAnswers = request.getParameterMap().keySet().stream().filter(s -> Pattern.matches(patternPossibleAnswerLabel, s)).collect(Collectors.toList());
			
			Collections.sort(listParamPossibleAnswers, new Comparator<String>() {
			    public int compare(String o1, String o2) {
			    	Integer i1 = Integer.parseInt(o1.replace("question_"+questionIndex+"_possibleAnswer_","").replace("_label",""));
			        Integer i2 = Integer.parseInt(o2.replace("question_"+questionIndex+"_possibleAnswer_","").replace("_label",""));
			        return (i1 < i2 ? -1 : (i1 == i2 ? 0 : 1));
			    }
			});
			
			// Récupération des labels des réponses pour initialiser les objets
			ArrayList<PossibleAnswer> possibleAnswers = new ArrayList<PossibleAnswer>();
			for(String paramPossibleAnswer : listParamPossibleAnswers) {
				String labelPossibleAnswer = request.getParameter(paramPossibleAnswer);
				PossibleAnswer possibleAnswer = new PossibleAnswer();
				possibleAnswer.setLabel(labelPossibleAnswer);
				possibleAnswer.setIsCorrect(false);
				
				possibleAnswers.add(possibleAnswer);
			}
			
			// Initialisation de la bonne réponse pour cette question-ci
			String possibleAnswerIndexStr = request.getParameter("question_"+questionIndex+"_radio");
			
			Integer indexCorrectAnswer = 1;
			if( possibleAnswerIndexStr != null)
				indexCorrectAnswer = Integer.parseInt(possibleAnswerIndexStr);

			if( !possibleAnswers.isEmpty() ) 
				possibleAnswers.get(indexCorrectAnswer-1).setIsCorrect(true);
			
			question.setPossibleAnswers(possibleAnswers);
			questions.add(question);
		}
		
		
		return questions;
	}
	
	public Quiz newQuestion(HttpServletRequest request) {
		String title = getFieldValue(request,FIELD_TITLE);
		String theme = getFieldValue(request,FIELD_THEME);
		
		Quiz quiz = new Quiz();
		
		processTitleValidation(title,quiz);
		processThemeValidation(theme,quiz);
		
		quiz.setQuestions(getQuestionsFromRequest(request));
		
		Question q = new Question();
		q.setPossibleAnswers(null);
		q.setIsActive(true);
		q.setLabel("");
		q.setPosition(1);
		quiz.getQuestions().add(q);
		
		return quiz;
	}
	
	public Quiz newAnswer(HttpServletRequest request) {
		String title = getFieldValue(request,FIELD_TITLE);
		String theme = getFieldValue(request,FIELD_THEME);
		String questionIndexStr = getFieldValue(request,FIELD_SUBMIT);
		Integer questionIndex = Integer.parseInt(questionIndexStr.replace("newAnswer_",""));
		
		Quiz quiz = new Quiz();
		
		processTitleValidation(title,quiz);
		processThemeValidation(theme,quiz);
		
		quiz.setQuestions(getQuestionsFromRequest(request));
		
		PossibleAnswer p = new PossibleAnswer();
		p.setLabel("");
		p.setIsCorrect(false);
		
		ArrayList<PossibleAnswer> possibleAnswers = quiz.getQuestions().get(questionIndex-1).getPossibleAnswers();
		
		if( possibleAnswers.isEmpty() )
			p.setIsCorrect(true);
		
		possibleAnswers.add(p);
		
		return quiz;
	}
}
