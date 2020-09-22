package goosegame.main;

import goosegame.i18n.Strings;
import goosegame.models.Block;
import goosegame.models.Board;
import goosegame.models.NormalBoard;
import goosegame.models.Player;
import goosegame.models.ScamBoard;
import goosegame.utils.CMDUtils;
import java.util.ArrayList;
import java.util.Random;

public class GooseGame {

    private static Board board;

    public static void main(String[] args) {
        System.out.println(Strings.get(Strings.WELCOME));

        while (true) {
            System.out.println(Strings.get(Strings.CHOOSE_LANGUAGE));

            String language = CMDUtils.getLine(null);
            try {
                int choice = Integer.parseInt(language);
                if (choice < 1 || choice > 2) {
                    System.out.println(Strings.get(Strings.WARNING_LANGUAGE));
                } else {
                    switch (choice) {
                        case 1:
                            Strings.languageClass = "goosegame.i18n.Italian";
                            break;
                        case 2:
                            Strings.languageClass = "goosegame.i18n.English";
                            break;
                    }
                    break;
                }
            } catch (NumberFormatException ex) {
                System.out.println(Strings.get(Strings.WARNING_LANGUAGE_NOT_NUMERICAL));
            }
        }

        while (true) {
            System.out.println(Strings.get(Strings.CHOOSE_BOARD_GAME));
            String typeOfGame = CMDUtils.getLine(null);
            try {
                int choice = Integer.parseInt(typeOfGame);
                if (choice < 1 || choice > 2) {
                    System.out.println(String.format(Strings.get(Strings.WARNING_NUMERICAL_VALUE_BETWEEN), "1", "2"));
                } else {
                    switch (choice) {
                        case 1:
                            board = new NormalBoard();
                            break;
                        case 2:
                            board = new ScamBoard();
                            break;
                    }
                    break;
                }
            } catch (NumberFormatException ex) {
                System.out.println(String.format(Strings.get(Strings.WARNING_NUMERICAL_VALUE_BETWEEN), "1", "2"));
            }
        }
        board.init();

        while (true) {
            String input = CMDUtils.getLine(null);
            analyzeInput(input);
        }
    }

    private static void analyzeInput(String input) {
        if (input.length() <= 0) {
            System.out.println(Strings.get(Strings.EMPTY_LINE));
            return;
        }

        String[] commands = input.split(" ");

        switch (commands[0].toLowerCase()) {
            case "add":
                if (!checkCommand("add", input)) {
                    return;
                }
                if (commands[1].equalsIgnoreCase("player")) {
                    if (board.getStatus() == Board.BOARD_STATUS_WAITING) {
                        Player toAdd = new Player(commands[2]);
                        if (board.getPlayers().contains(toAdd)) {
                            System.out.println(String.format(Strings.get(Strings.ALREADY_EXISTING_PLAYER), toAdd.getNickname()));
                        } else {
                            board.getPlayers().add(toAdd);
                            StringBuilder playerBuildString = new StringBuilder();
                            playerBuildString.append("players: ");
                            for (int i = 0; i < board.getPlayers().size(); i++) {
                                playerBuildString.append(board.getPlayers().get(i).getNickname());

                                if (i < board.getPlayers().size() - 1) {
                                    playerBuildString.append(", ");
                                }
                            }

                            System.out.println(playerBuildString.toString());
                        }
                    } else {
                        System.out.println(Strings.get(Strings.GAME_ALREADY_STARTED));
                    }
                } else {
                    System.out.println(Strings.get(Strings.ERROR_ADD_COMMAND));
                }
                break;
            case "move":
                if (!checkCommand("move", input)) {
                    return;
                }
                String player = commands[1];
                Player p = board.getPlayerFromNickname(player);
                if (p == null) {
                    System.out.println(String.format(Strings.get(Strings.ERROR_PLAYER_NOT_FOUND), p.getNickname()));
                } else {
                    board.setStatus(Board.BOARD_STATUS_RUNNING);
                    int prevPos = p.getPosition();
                    StringBuilder builder = new StringBuilder();
                    int roll1, roll2;

                    if (commands.length > 2) {
                        String[] rolls = String.join("", commands[2], commands[3]).split(",");
                        roll1 = Integer.parseInt(rolls[0]);
                        roll2 = Integer.parseInt(rolls[1]);
                    } else {
                        roll1 = new Random().nextInt(5) + 1;
                        roll2 = new Random().nextInt(5) + 1;
                    }

                    if (roll1 < 1 || roll1 > 6) {
                        System.out.println(String.format(Strings.get(Strings.ERROR_ROLL), "1"));
                        return;
                    }

                    if (roll2 < 1 || roll2 > 6) {
                        System.out.println(String.format(Strings.get(Strings.ERROR_ROLL), "2"));
                        return;
                    }

                    int sum = roll1 + roll2;

                    builder.append(String.format(Strings.get(Strings.ROLL), p.getNickname(), roll1, roll2));

                    if (p.getPosition() + sum > 63) {
                        int newPos = 63 - (p.getPosition() + sum - 63);
                        builder.append(String.format(Strings.get(Strings.BOUNCES), p.getNickname(), p.getNickname(), newPos));
                        p.setPosition(newPos);
                    } else {
                        builder.append(String.format(Strings.get(Strings.MOVE), p.getNickname(), p.getPosition(), p.getPosition() + sum));
                        p.setPosition(p.getPosition() + sum);
                    }

                    while (isOnTheGoose(p)) {
                        builder.append(String.format(Strings.get(Strings.GOOSE), p.getNickname(), p.getPosition() + sum));
                        p.setPosition(p.getPosition() + sum);
                    }
                    System.out.println(builder.toString());
                    analyzeFurtherAction(p);

                    if (board instanceof ScamBoard) {
                        ArrayList<Player> plInPosition = board.getPlayersOnPosition(p.getPosition());
                        if (plInPosition.size() > 1) {
                            plInPosition.forEach(pl -> {
                                if (!pl.getNickname().equalsIgnoreCase(p.getNickname())) {
                                    System.out.println(String.format(Strings.get(Strings.SCAM), pl.getNickname(), prevPos, p.getNickname()));
                                    pl.setPosition(prevPos);
                                }
                            });
                        }
                    }
                }
                break;
            case "debug":
                board.printBoardSituation();
                break;
            case "play":
                while (true) {
                    board.getPlayers().forEach(pl -> {
                        analyzeInput("move " + pl.getNickname());
                    });
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                    }
                }
            default:
                System.out.println(Strings.get(Strings.COMMAND_NOT_FOUND));
                break;
        }
    }

    private static void analyzeFurtherAction(Player p) {
        switch (board.getBlocks()[p.getPosition() - 1].getType()) {
            case Block.BLK_TYPE_BRIDGE:
                System.out.println(String.format(Strings.get(Strings.BRIDGE), p.getNickname(), p.getNickname()));
                p.setPosition(12);
                break;
            case Block.BLK_TYPE_WINNING:
                System.out.println(String.format(Strings.get(Strings.WIN), p.getNickname()));
                analyzeInput("debug");
                System.exit(1);
                break;
        }
    }

    private static boolean isOnTheGoose(Player p) {
        return board.getBlocks()[p.getPosition() - 1].getType() == Block.BLK_TYPE_GOOSE;
    }

    private static boolean checkCommand(String command, String line) {
        String[] splitted = line.split(" ");
        switch (command) {
            case "add":
                if (splitted.length != 3) {
                    System.out.println("Warning! Incorrect use of the 'add' command!");
                    System.out.println("Usage: add player nameOfThePlayer\n");
                    System.out.println("Please be aware that spaces are NOT allowed in the nickname.");
                    return false;
                }
                return true;
            case "move":
                if (splitted.length != 4 && splitted.length != 2 || (splitted.length == 4 && !line.contains(","))) {
                    System.out.println("Warning! Incorrect use of the 'move' command!");
                    System.out.println("Usage: move player [firstRoll, secondRoll]\n");
                    System.out.println("Note that firstRoll and secondRoll must be both populated or both empty and, if they are populated, they have to be numerical value and with a space after the ','.");
                    return false;
                }

                if (splitted.length == 4) {
                    String[] rolls = String.join("", splitted[2], splitted[3]).split(",");
                    try {
                        Integer.parseInt(rolls[0]);
                        Integer.parseInt(rolls[1]);
                    } catch (NumberFormatException ex) {
                        System.out.println("Warning! Incorrect use of the 'move' command!");
                        System.out.println("Usage: move player [firstRoll, secondRoll]\n");
                        System.out.println("Note that firstRoll and secondRoll must be both populated or both empty and, if they are populated, they have to be numerical value and with a space after the ','.");
                        return false;
                    }
                }
        }

        return true;
    }

}
