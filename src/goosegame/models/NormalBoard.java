package goosegame.models;

/**
 *
 * @author Postazione B3
 */
public class NormalBoard extends Board {

    public NormalBoard() {
        super();
    }

    @Override
    public void init() {
        Block[] blocks = new Block[63];
        for (int i = 0; i < blocks.length; i++) {
            switch (i) {
                case 0:
                    blocks[i] = new Block(Block.BLK_TYPE_START);
                    break;
                case 5:
                    blocks[i] = new Block(Block.BLK_TYPE_BRIDGE);
                    break;
                case 4:
                case 8:
                case 13:
                case 17:
                case 22:
                case 26:
                    blocks[i] = new Block(Block.BLK_TYPE_GOOSE);
                    break;
                case 62:
                    blocks[i] = new Block(Block.BLK_TYPE_WINNING);
                    break;
                default:
                    blocks[i] = new Block();
                    break;
            }
            
        }
        
        setBlocks(blocks);
    }
    
    
}
