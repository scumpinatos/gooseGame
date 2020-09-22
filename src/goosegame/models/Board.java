package goosegame.models;

import java.util.ArrayList;

public abstract class Board {

    private Block[] blocks;
    private ArrayList<Player> players;
    private int status;
    
    public static final int BOARD_STATUS_WAITING = 1;
    public static final int BOARD_STATUS_RUNNING = 2;
    public static final int BOARD_STATUS_PAUSED = 3;
    
    public Board() {
        players = new ArrayList<>();
        this.status = BOARD_STATUS_WAITING;
    }
    
    public Block[] getBlocks() {
        return blocks;
    }

    public void setBlocks(Block[] blocks) {
        this.blocks = blocks;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }
    
    public Player getPlayerFromNickname(String nick) {
        if(this.players == null) return null;
        
        Player p1 = new Player(nick);
        if(this.players.contains(p1)) {
            return this.players.get(this.players.indexOf(p1));
        }else {
           return null;
        }
    }
    
    public void printBoardSituation() {
        players.forEach(player -> {
            System.out.println("Player: " + player.getNickname());
            System.out.println("\tPosition: " + player.getPosition() + "\n");
        });
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    
    public ArrayList<Player> getPlayersOnPosition(int position) {
        ArrayList<Player> toReturn = new ArrayList<>();
        
        players.forEach(pl -> {if(pl.getPosition() == position) toReturn.add(pl);});
        
        return toReturn;
    }
    
    public abstract void init();
    
}
