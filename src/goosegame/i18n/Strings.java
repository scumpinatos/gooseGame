package goosegame.i18n;

/**
 *
 * @author ScumpinatoS
 */
public abstract class Strings {

    public static String languageClass = "goosegame.i18n.English";
    public static final String CHOOSE_BOARD_GAME = "CHOOSE_BOARD_GAME";
    public static final String WELCOME = "WELCOME";
    public static final String CHOOSE_LANGUAGE = "CHOOSE_LANGUAGE";
    public static final String WARNING_LANGUAGE = "WARNING_LANGUAGE";
    public static final String WARNING_LANGUAGE_NOT_NUMERICAL = "WARNING_LANGUAGE_NOT_NUMERICAL";
    public static final String WARNING_NUMERICAL_VALUE_BETWEEN = "WARNING_NUMERICAL_VALUE_BETWEEN";
    public static final String EMPTY_LINE = "EMPTY_LINE";
    public static final String ALREADY_EXISTING_PLAYER = "ALREADY_EXISTING_PLAYER";
    public static final String GAME_ALREADY_STARTED = "GAME_ALREADY_STARTED";
    public static final String ERROR_ADD_COMMAND = "ERROR_ADD_COMMAND";
    public static final String ERROR_PLAYER_NOT_FOUND = "ERROR_PLAYER_NOT_FOUND";
    public static final String ERROR_ROLL = "ERROR_ROLL";
    public static final String ROLL = "ROLL";
    public static final String BOUNCES = "BOUNCES";
    public static final String MOVE = "MOVE";
    public static final String GOOSE = "GOOSE";
    public static final String SCAM = "SCAM";
    public static final String COMMAND_NOT_FOUND = "COMMAND_NOT_FOUND";
    public static final String BRIDGE = "BRIDGE";
    public static final String WIN = "WIN";

    public static String get(String VAR) {
        try {
            return (String) Class.forName(languageClass).getField(VAR).get(null);
        } catch (ClassNotFoundException | NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException ex) {
            // Logger.getLogger(Strings.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        }
    }

}
