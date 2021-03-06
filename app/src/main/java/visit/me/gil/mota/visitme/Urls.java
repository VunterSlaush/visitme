package visit.me.gil.mota.visitme;

/**
 * Created by Slaush on 22/05/2017.
 */
public class Urls
{

    public static final String LOGIN = "/user/auth";
    public static final String REGISTER = "/user";
    public static final String USER_PROFILE = "/user/me";
    public static final String USER_VISITS_SCHEDULED = USER_PROFILE + "/visits/SCHEDULED";
    public static final String USER_VISITS_FREQUENT = USER_PROFILE + "/visits/FREQUENT";
    public static final String USER_VISITS_SPORADIC = USER_PROFILE + "/visits/SPORADIC";
    public static final String USER_COMMUNITIES = USER_PROFILE + "/communities";
    public static final String CREATE_ALERT = "/alerts";
    public static final String USER_DEVICES = USER_PROFILE + "/devices";
    public static final String USER_ALERTS_INCIDENT = USER_PROFILE + "/alerts/INCIDENT";
    public static final String USER_ALERTS_INFORMATION = USER_PROFILE + "/alerts/INFORMATION";
    public static final String USER_ALERTS_OTHER = USER_PROFILE + "/alerts/OTHER";
    public static final String VISITS = "/visits";
    public static final String FORGOT_PASSWORD = "/forgotPassword";
    public static final String FORGOT_PASSWORD_CODE = "/forgotPassword/code";
    public static final String CHANGE_PASSWORD = "/forgotPassword/changePassword";
    public static final String COMPANIES = "/companies";
    public static final String COMMUNITIES = "/communities";
    public static final String FIND_FIRST_USER = "/user/findFirstUser";
    public static final String GIVE_ACCESS = "/visits/:visit/giveAccess";
    public static final String JOIN_COMMUNITY = "/communities/:community/join";
    public static final String GUESTS_VISITS = "/user/me/guests";
}
