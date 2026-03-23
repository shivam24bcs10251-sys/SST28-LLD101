package board;

import strategy.BoardSetupStrategy;
import java.util.*;

public class Board {

    private int size;
    private Map<Integer, BoardEntity> entities = new HashMap<>();
    private List<BoardEntity> list = new ArrayList<>();

    public Board(int n) {
        this.size = n * n;
    }

    public void setupBoard(BoardSetupStrategy strategy) {
        strategy.setupBoard(this);
    }

    public void addBoardEntity(BoardEntity entity) {
        if (!entities.containsKey(entity.getStart())) {
            entities.put(entity.getStart(), entity);
            list.add(entity);
        }
    }

    public BoardEntity getEntity(int pos) {
        return entities.get(pos);
    }

    public int getBoardSize() {
        return size;
    }

    public boolean canAddEntity(int pos) {
        return !entities.containsKey(pos);
    }
}