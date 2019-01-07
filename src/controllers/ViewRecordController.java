package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import common.Config;
import dao.DAOFactory;
import dao.interfaces.RecordDAO;
import models.beans.Answer;
import models.beans.Question;
import models.beans.Quiz;
import models.beans.Record;
import models.beans.User;

@WebServlet("/" + Config.URL_VIEW_RECORD + "/*")
public class ViewRecordController extends AbstractController {

	private static Logger logger = Logger.getLogger(QuizzesController.class);
	private static final long serialVersionUID = 1L;
	private static final String VIEW = "/WEB-INF/trainee_view_record.jsp";
	private static final String ATT_RECORD = "record";
	private static final String ATT_QUIZ = "quiz";
	private static final String ATT_ANSWERS_ID = "answersID";
	private static final String ATT_NOTANSWEREDQUESTIONS_ID = "notAnsweredQuestionsID";
	private static RecordDAO recordDAO;

	public static void setDAOs(RecordDAO recordDAO) {
		ViewRecordController.recordDAO = recordDAO;
	}

	public void init() throws ServletException {
		ViewRecordController.setDAOs(((DAOFactory) getServletContext()
				.getAttribute(Config.CONF_DAO_FACTORY)).getRecordDAO());
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		User user = checkSessionUser(request, response);

		Integer recordId = getIntegerFromURL(request, response);

		HashMap<String, Object> filters = new HashMap<String, Object>();
		filters.put("id", recordId);

		ArrayList<Record> records = new ArrayList<Record>();
		try {
			records = recordDAO.findBy(filters);
		} catch (Exception e) {
			logger.error(e.getMessage());
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		}

		if (records.size() != 1) {
			logger.error("Records size should be equal to 1, but is equal to "
					+ records.size());
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}

		Record record = records.get(0);
		Quiz quiz = record.getQuiz();

		ArrayList<Integer> answersID = new ArrayList<Integer>();
		for (Answer a : record.getAnswers()) {
			answersID.add(a.getId());
		}

		ArrayList<Integer> notAnsweredQuestionsID = new ArrayList<Integer>();
		for (Question q : quiz.getQuestions()) {
			int notAnsweredQuestionID = q.getId();
			for (Answer a : q.getPossibleAnswers()) {
				if (answersID.contains(a.getId())) {
					notAnsweredQuestionID = -1;
				}
			}
			if (notAnsweredQuestionID != -1) {
				notAnsweredQuestionsID.add(notAnsweredQuestionID);
			}
		}

		request.setAttribute(ATT_RECORD, record);
		request.setAttribute(ATT_QUIZ, quiz);
		request.setAttribute(ATT_ANSWERS_ID, answersID);
		request.setAttribute(ATT_NOTANSWEREDQUESTIONS_ID,
				notAnsweredQuestionsID);

		this.getServletContext().getRequestDispatcher(VIEW).forward(request,
				response);
	}

}
