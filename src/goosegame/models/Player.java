package goosegame.models;

import java.util.Objects;

/**
 *
 * @author Postazione B3
 */
public class Player {

    private String nickname;
    private int position;

    public Player() {
    }

    public Player(String nickname) {
        this.nickname = nickname;
        this.position = 0;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Player other = (Player) obj;
        if (!Objects.equals(this.nickname.toLowerCase(), other.nickname.toLowerCase())) {
            return false;
        }
        return true;
    }

}
