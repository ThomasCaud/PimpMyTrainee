package models.forms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import dao.interfaces.AnswerDAO;
import dao.interfaces.QuestionDAO;
import dao.interfaces.QuizDAO;
import dao.interfaces.ThemeDAO;
import models.beans.Answer;
import models.beans.Question;
import models.beans.Quiz;
import models.beans.Theme;

public class QuizForm extends AbstractForm {
	// Variables that represents each field of the form
	private static final String FIELD_TITLE = "title";
	private static final String FIELD_THEME = "theme";
	private static final String FIELD_QUIZID = "quiz_id";
	private static final String FIELD_SUBMIT = "submit";
	private QuizDAO quizDAO;
	private ThemeDAO themeDAO;
	private QuestionDAO questionDAO;
	private AnswerDAO answerDAO;

	public QuizForm(QuizDAO quizDAO, ThemeDAO themeDAO, QuestionDAO questionDAO, AnswerDAO possibleAnswerDAO) {
		super();
		this.quizDAO = quizDAO;
		this.themeDAO = themeDAO;
		this.questionDAO = questionDAO;
		this.answerDAO = possibleAnswerDAO;
	}

	public void processTitleValidation(String title, Quiz quiz) {
		if (isNullOrEmpty(title)) {
			setError(FIELD_TITLE, "The title is empty.");
		}
		quiz.setTitle(title);
	}

	public void processThemeValidation(String themeStr, Quiz quiz) {
		if (isNullOrEmpty(themeStr)) {
			setError(FIELD_THEME, "The theme is empty.");
			return;
		}

		Integer themeId;
		try {
			themeId = Integer.parseInt(themeStr);
		} catch (NumberFormatException e) {
			setError(FIELD_THEME, "The theme is incorrect.");
			return;
		}

		Theme theme = themeDAO.find(themeId);
		if (theme == null) {
			setError(FIELD_THEME, "The theme is incorrect.");
			return;
		}

		quiz.setTheme(theme);
	}

	public void processQuestionsValidation(ArrayList<Question> questions, Quiz quiz) {
		if (questions.isEmpty()) {
			setError("questions", "The quiz must contain at least 1 question.");
		} else {
			int questionIndex = 1;
			for (Question question : questions) {
				if (isNullOrEmpty(question.getLabel())) {
					setError("question_" + questionIndex + "_label", "The label cannot be empty.");
				}

				ArrayList<Answer> possibleAnswers = question.getPossibleAnswers();
				if (possibleAnswers == null || possibleAnswers.isEmpty()) {
					setError("question_" + questionIndex + "_answers", "There must be at least 1 answer.");
				} else {
					int answerIndex = 1;
					boolean oneIsCorrect = false;
					for (Answer possibleAnswer : possibleAnswers) {
						if (isNullOrEmpty(possibleAnswer.getLabel()))
							setError("question_" + questionIndex + "_answer_" + answerIndex,
									"The label cannot be empty.");

						if (possibleAnswer.getIsCorrect())
							oneIsCorrect = true;

						possibleAnswer.setPosition(answerIndex);
						answerIndex++;
					}

					if (!oneIsCorrect)
						setError("question_" + questionIndex + "_answers", "There must be 1 correct answer selected.");
				}

				question.setPosition(questionIndex);
				questionIndex++;
			}
		}
	}

	public ArrayList<Question> getQuestionsFromRequest(HttpServletRequest request) {

		ArrayList<Question> questions = new ArrayList<Question>();

		// Recherche des noms des parametres dans la variable POST qui correspondent aux
		// labels des questions
		String patternQuestionLabel = "(question_)([0-9]+)(_label)";
		List<String> listParamQuestions = request.getParameterMap().keySet().stream()
				.filter(s -> Pattern.matches(patternQuestionLabel, s)).collect(Collectors.toList());

		// Tri selon le numéro présent dans le label des questions
		Collections.sort(listParamQuestions, new Comparator<String>() {
			public int compare(String o1, String o2) {
				Integer i1 = Integer.parseInt(o1.replace("question_", "").replace("_label", ""));
				Integer i2 = Integer.parseInt(o2.replace("question_", "").replace("_label", ""));
				return (i1 < i2 ? -1 : (i1 == i2 ? 0 : 1));
			}
		});

		for (String paramQuestion : listParamQuestions) {
			String labelQuestion = request.getParameter(paramQuestion);

			Question question = new Question();
			question.setLabel(labelQuestion);

			// Recherche des noms des parametres dans la variable POST qui correspondent aux
			// labels des réponses
			String questionIndex = paramQuestion.replace("question_", "").replace("_label", "");

			String questionIdStr = request.getParameter("question_" + questionIndex + "_id");
			Integer questionId = Integer.parseInt(questionIdStr);
			question.setId(questionId);

			String patternPossibleAnswerLabel = "(question_)" + questionIndex + "(_possibleAnswer_)([0-9]+)(_label)";
			List<String> listParamPossibleAnswers = request.getParameterMap().keySet().stream()
					.filter(s -> Pattern.matches(patternPossibleAnswerLabel, s)).collect(Collectors.toList());

			Collections.sort(listParamPossibleAnswers, new Comparator<String>() {
				public int compare(String o1, String o2) {
					Integer i1 = Integer.parseInt(
							o1.replace("question_" + questionIndex + "_possibleAnswer_", "").replace("_label", ""));
					Integer i2 = Integer.parseInt(
							o2.replace("question_" + questionIndex + "_possibleAnswer_", "").replace("_label", ""));
					return (i1 < i2 ? -1 : (i1 == i2 ? 0 : 1));
				}
			});

			// Récupération des labels des réponses pour initialiser les objets
			ArrayList<Answer> possibleAnswers = new ArrayList<Answer>();
			for (String paramPossibleAnswer : listParamPossibleAnswers) {
				String labelPossibleAnswer = request.getParameter(paramPossibleAnswer);
				Answer possibleAnswer = new Answer();
				possibleAnswer.setLabel(labelPossibleAnswer);
				possibleAnswer.setIsCorrect(false);

				String answerIndex = paramPossibleAnswer.replace("question_" + questionIndex + "_possibleAnswer_", "")
						.replace("_label", "");

				String answerIdStr = request
						.getParameter("question_" + questionIndex + "_possibleAnswer_" + answerIndex + "_id");
				Integer answerId = Integer.parseInt(answerIdStr);
				possibleAnswer.setId(answerId);

				possibleAnswers.add(possibleAnswer);
			}

			// Initialisation de la bonne réponse pour cette question-ci
			String possibleAnswerIndexStr = request.getParameter("question_" + questionIndex + "_radio");

			Integer indexCorrectAnswer = 1;
			if (possibleAnswerIndexStr != null)
				indexCorrectAnswer = Integer.parseInt(possibleAnswerIndexStr);

			Answer correctAnswer = new Answer();
			if (!possibleAnswers.isEmpty())
				correctAnswer = possibleAnswers.get(indexCorrectAnswer - 1);
			correctAnswer.setIsCorrect(true);

			question.setPossibleAnswers(possibleAnswers);
			question.setCorrectAnswer(correctAnswer);

			questions.add(question);
		}

		return questions;
	}

	public Quiz newQuestion(HttpServletRequest request) {
		String title = getFieldValue(request, FIELD_TITLE);
		String theme = getFieldValue(request, FIELD_THEME);

		Quiz quiz = new Quiz();
		setQuizIdIfExists(request, quiz);

		processThemeValidation(theme, quiz);

		quiz.setTitle(title);
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
		String title = getFieldValue(request, FIELD_TITLE);
		String theme = getFieldValue(request, FIELD_THEME);
		String questionIndexStr = getFieldValue(request, FIELD_SUBMIT);
		Integer questionIndex = Integer.parseInt(questionIndexStr.replace("newAnswer_", ""));

		Quiz quiz = new Quiz();
		setQuizIdIfExists(request, quiz);

		processThemeValidation(theme, quiz);

		quiz.setTitle(title);
		quiz.setQuestions(getQuestionsFromRequest(request));

		Answer p = new Answer();
		p.setLabel("");
		p.setIsCorrect(false);

		ArrayList<Answer> possibleAnswers = quiz.getQuestions().get(questionIndex - 1).getPossibleAnswers();

		if (possibleAnswers.isEmpty())
			p.setIsCorrect(true);

		possibleAnswers.add(p);

		return quiz;
	}

	public Quiz deleteQuestion(HttpServletRequest request) {
		String title = getFieldValue(request, FIELD_TITLE);
		String theme = getFieldValue(request, FIELD_THEME);
		String questionIndexStr = getFieldValue(request, FIELD_SUBMIT);
		Integer questionIndex = Integer.parseInt(questionIndexStr.replace("deleteQuestion_", ""));

		Quiz quiz = new Quiz();
		setQuizIdIfExists(request, quiz);

		processThemeValidation(theme, quiz);

		quiz.setTitle(title);
		quiz.setQuestions(getQuestionsFromRequest(request));

		quiz.getQuestions().remove(questionIndex - 1);

		return quiz;
	}

	public Quiz deleteAnswerFromQuestion(HttpServletRequest request) {
		String title = getFieldValue(request, FIELD_TITLE);
		String theme = getFieldValue(request, FIELD_THEME);
		String indexesStrRaw = getFieldValue(request, FIELD_SUBMIT).replace("deleteAnswer_", "")
				.replace("fromQuestion_", "");
		String[] indexesStr = indexesStrRaw.split("_");

		Integer answerIndex = Integer.parseInt(indexesStr[0]);
		Integer questionIndex = Integer.parseInt(indexesStr[1]);

		Quiz quiz = new Quiz();
		setQuizIdIfExists(request, quiz);

		processThemeValidation(theme, quiz);

		quiz.setTitle(title);
		quiz.setQuestions(getQuestionsFromRequest(request));

		ArrayList<Answer> possibleAnswers = quiz.getQuestions().get(questionIndex - 1).getPossibleAnswers();
		possibleAnswers.remove(answerIndex - 1);

		if (possibleAnswers.size() == 1)
			possibleAnswers.get(0).setIsCorrect(true);

		return quiz;
	}

	public Quiz moveUpQuestion(HttpServletRequest request) {
		String title = getFieldValue(request, FIELD_TITLE);
		String theme = getFieldValue(request, FIELD_THEME);
		String indexQuestionStr = getFieldValue(request, FIELD_SUBMIT).replace("moveUpQuestion_", "");
		Integer indexQuestion = Integer.parseInt(indexQuestionStr);

		Quiz quiz = new Quiz();
		setQuizIdIfExists(request, quiz);

		processThemeValidation(theme, quiz);

		quiz.setTitle(title);
		quiz.setQuestions(getQuestionsFromRequest(request));

		ArrayList<Question> questions = quiz.getQuestions();
		if (indexQuestion > 0 && indexQuestion <= questions.size()) {
			if (indexQuestion != 1) {
				Collections.swap(questions, indexQuestion - 1, indexQuestion - 2);
			}
		}

		return quiz;
	}

	public Quiz moveDownQuestion(HttpServletRequest request) {
		String title = getFieldValue(request, FIELD_TITLE);
		String theme = getFieldValue(request, FIELD_THEME);
		String indexQuestionStr = getFieldValue(request, FIELD_SUBMIT).replace("moveDownQuestion_", "");
		Integer indexQuestion = Integer.parseInt(indexQuestionStr);

		Quiz quiz = new Quiz();
		setQuizIdIfExists(request, quiz);

		processThemeValidation(theme, quiz);

		quiz.setTitle(title);
		quiz.setQuestions(getQuestionsFromRequest(request));

		ArrayList<Question> questions = quiz.getQuestions();
		if (indexQuestion > 0 && indexQuestion <= questions.size()) {
			if (indexQuestion != questions.size()) {
				Collections.swap(questions, indexQuestion - 1, indexQuestion);
			}
		}

		return quiz;
	}

	public Quiz moveUpAnswerFromQuestion(HttpServletRequest request) {
		String title = getFieldValue(request, FIELD_TITLE);
		String theme = getFieldValue(request, FIELD_THEME);
		String indexesStrRaw = getFieldValue(request, FIELD_SUBMIT).replace("moveUpAnswer_", "")
				.replace("fromQuestion_", "");
		String[] indexesStr = indexesStrRaw.split("_");

		Integer answerIndex = Integer.parseInt(indexesStr[0]);
		Integer questionIndex = Integer.parseInt(indexesStr[1]);

		Quiz quiz = new Quiz();
		setQuizIdIfExists(request, quiz);

		processThemeValidation(theme, quiz);

		quiz.setTitle(title);
		quiz.setQuestions(getQuestionsFromRequest(request));

		ArrayList<Answer> possibleAnswers = quiz.getQuestions().get(questionIndex - 1).getPossibleAnswers();
		if (answerIndex > 0 && answerIndex <= possibleAnswers.size()) {
			if (answerIndex != 1) {
				Collections.swap(possibleAnswers, answerIndex - 1, answerIndex - 2);
			}
		}

		return quiz;
	}

	public Quiz moveDownAnswerFromQuestion(HttpServletRequest request) {
		String title = getFieldValue(request, FIELD_TITLE);
		String theme = getFieldValue(request, FIELD_THEME);
		String indexesStrRaw = getFieldValue(request, FIELD_SUBMIT).replace("moveDownAnswer_", "")
				.replace("fromQuestion_", "");
		String[] indexesStr = indexesStrRaw.split("_");

		Integer answerIndex = Integer.parseInt(indexesStr[0]);
		Integer questionIndex = Integer.parseInt(indexesStr[1]);

		Quiz quiz = new Quiz();
		setQuizIdIfExists(request, quiz);

		processThemeValidation(theme, quiz);

		quiz.setTitle(title);
		quiz.setQuestions(getQuestionsFromRequest(request));

		ArrayList<Answer> possibleAnswers = quiz.getQuestions().get(questionIndex - 1).getPossibleAnswers();
		if (answerIndex > 0 && answerIndex <= possibleAnswers.size()) {
			if (answerIndex != possibleAnswers.size()) {
				Collections.swap(possibleAnswers, answerIndex - 1, answerIndex);
			}
		}

		return quiz;
	}

	public Quiz prepareQuiz(HttpServletRequest request) {
		String title = getFieldValue(request, FIELD_TITLE);
		String theme = getFieldValue(request, FIELD_THEME);

		Quiz quiz = new Quiz();
		setQuizIdIfExists(request, quiz);

		ArrayList<Question> questions = getQuestionsFromRequest(request);

		processThemeValidation(theme, quiz);
		processTitleValidation(title, quiz);
		processQuestionsValidation(questions, quiz);

		quiz.setQuestions(questions);

		return quiz;
	}

	public void setQuizIdIfExists(HttpServletRequest request, Quiz quiz) {
		String quizIdStr = getFieldValue(request, FIELD_QUIZID);
		if (quizIdStr != null) {
			Integer quizId = Integer.parseInt(quizIdStr);
			quiz.setId(quizId);
		}
	}

	public void createQuiz(Quiz quiz) {
		quizDAO.createQuiz(quiz);

		ArrayList<Question> questions = quiz.getQuestions();
		for (Question question : questions) {
			questionDAO.create(quiz, question);

			ArrayList<Answer> possibleAnswers = question.getPossibleAnswers();
			for (Answer answer : possibleAnswers) {
				answerDAO.create(question, answer);
			}
		}
	}

	public Quiz updateQuiz(HttpServletRequest request) {
		// Initialisation d'un objet Quiz à partir des données POST de la requête
		// Puis validation des champs de ce Quiz
		Quiz quiz = prepareQuiz(request);

		Quiz previousQuiz = quizDAO.find(quiz.getId());

		// Si la validation du Quiz n'a pas généré d'erreurs, on passe à l'update en bdd
		if (getErrors().isEmpty()) {
			// Initialisation des champs qui ne doivent pas changer lors de l'update
			quiz.setCreator(previousQuiz.getCreator());
			quiz.setCreationDate(previousQuiz.getCreationDate());
			quiz.setIsActive(true);

			// Update BDD des champs volatiles d'un quiz : title, theme
			quizDAO.updateQuiz(quiz);

			// Tri des questions selon leur ID dans le but de convertir la liste en Map
			Collections.sort(previousQuiz.getQuestions(), new Comparator<Question>() {
				@Override
				public int compare(Question q1, Question q2) {
					Integer i1 = q1.getId();
					Integer i2 = q2.getId();
					return (i1 < i2 ? -1 : (i1 == i2 ? 0 : 1));
				}
			});

			// Création d'une Map<ID,Question> pour accéder rapidement à une question selon
			// son ID
			HashMap<Integer, Question> previousQuizQuestions = new HashMap<Integer, Question>();
			for (Question question : previousQuiz.getQuestions()) {
				previousQuizQuestions.put(question.getId(), question);
			}

			// Processus d'update des questions du quiz courant
			for (Question question : quiz.getQuestions()) {
				Integer questionId = question.getId();
				// Si l'ID de cette question est égal à -1, il faut créer cette question
				// Sinon, il faut la mettre à jour
				if (questionId != -1) {
					// Update des champs volatile de la question :
					questionDAO.updateQuestion(question);

					// Conversion de la list de réponses en map pour accéder rapidement aux IDs
					// (comme précédemment)
					Collections.sort(previousQuizQuestions.get(questionId).getPossibleAnswers(),
							new Comparator<Answer>() {
								@Override
								public int compare(Answer q1, Answer q2) {
									Integer i1 = q1.getId();
									Integer i2 = q2.getId();
									return (i1 < i2 ? -1 : (i1 == i2 ? 0 : 1));
								}
							});
					HashMap<Integer, Answer> previousQuizAnswers = new HashMap<Integer, Answer>();
					for (Answer answer : previousQuizQuestions.get(questionId).getPossibleAnswers()) {
						previousQuizAnswers.put(answer.getId(), answer);
					}

					/*
					 * Même processus que pour les questions : on parcourt les réponses Si l'id est
					 * égal à -1, il faut créer la réponse, Sinon, cela signifie qu'il faut l'update
					 * Lorsqu'on update, on enlève cette réponse de la map précédemment créée A la
					 * fin, on supprime toutes les réponses restantes dans la map car cela veut dire
					 * qu'elles ont été supprimées dans le formulaire
					 */
					for (Answer answer : question.getPossibleAnswers()) {
						Integer answerId = answer.getId();
						if (answerId != -1) {
							answerDAO.update(answer);
							previousQuizAnswers.remove(answer.getId());

						} else {
							answerDAO.create(question, answer);
						}
					}

					for (Answer answerToDisable : previousQuizAnswers.values()) {
						answerDAO.disable(answerToDisable);
					}
					previousQuizQuestions.remove(question.getId());
				} else {
					// Création de la question et de ses réponses associées
					questionDAO.create(quiz, question);
					ArrayList<Answer> possibleAnswers = question.getPossibleAnswers();
					for (Answer answer : possibleAnswers) {
						answerDAO.create(question, answer);
					}
				}
			}

			for (Question questionToDisable : previousQuizQuestions.values()) {
				questionDAO.disable(questionToDisable);

				ArrayList<Answer> possibleAnswers = questionToDisable.getPossibleAnswers();
				for (Answer answer : possibleAnswers) {
					answerDAO.disable(answer);
				}
			}
		}

		return quiz;
	}
}
