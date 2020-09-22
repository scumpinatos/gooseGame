package goosegame.models;

public class Block {

    public static final int BLK_TYPE_WINNING = 1;
    public static final int BLK_TYPE_BRIDGE = 2;
    public static final int BLK_TYPE_GOOSE = 3;
    public static final int BLK_TYPE_START = 4;
    public static final int BLK_TYPE_NORMAL = -1;

    private String imgPath;
    private int type;

    public Block() {
        this.type = BLK_TYPE_NORMAL;
    }

    public Block(int type) {
        this.type = type;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        if (type > 4 || type < -1) { //TODO: Redefine this check
            this.type = BLK_TYPE_NORMAL;
        } else {
            this.type = type;
        }
    }

}
