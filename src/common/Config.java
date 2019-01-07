package common;

public class Config {
	public static final String ATT_SESSION_USER = "sessionUser";
	public static final String ATT_SESSION_QUIZ = "sessionQuiz";
	public static final String ATT_SESSION_CONTEXT_ID = "sessionContextID";
	public static final String ATT_SESSION_QUIZ_BEGINNING_TIMESTAMP = "sessionQuizBeginningTimestamp";
	public static final String ATT_STATS_NB_ACTIVE_USERS = "nbActiveUser";
	public static final String ATT_STATS_NB_INACTIVE_USERS = "nbInactiveUser";
	public static final String ATT_STATS_NB_CREATED_QUIZZES = "nbCreatedQuizzes";
	public static final String ATT_STATS_NB_RECORDS = "nbRecords";

	public static final String CONF_DAO_FACTORY = "daofactory";
	public static final Integer NB_USERS_PER_PAGE = 3;
	public static final Integer NB_QUIZZES_PER_PAGE = 3;
	public static final Integer PAGINATION_MAX_SIZE = 10;

	// URLS
	public static final String URL_ROOT = "";
	public static final String URL_LOGIN = "login";
	public static final String URL_MY_PROFILE = "profile";
	public static final String URL_LOGOUT = "logout";
	public static final String URL_USERS = "users";
	public static final String URL_VIEW_USER = "viewUser";
	public static final String URL_REGISTER_USER = "registerUser";
	public static final String URL_QUIZZES = "quizzes";
	public static final String URL_CREATE_QUIZ = "createQuiz";
	public static final String URL_VIEW_QUIZ = "viewQuiz";
	public static final String URL_START_QUIZ = "startQuiz";
	public static final String URL_RUN_QUIZ = "runQuiz";
	public static final String URL_CREATE_THEME = "createTheme";
	public static final String URL_RESULTS = "records";
	public static final String URL_VIEW_RECORD = "viewRecord";
	public static final String URL_SETTINGS = "settings";
	public static final String URL_DATABASE_ADMINISTRATION = "databaseAdministration";

}
