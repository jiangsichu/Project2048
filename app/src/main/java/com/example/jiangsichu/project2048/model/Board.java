package com.example.jiangsichu.project2048.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiangsichu on 07/05/15.
 */
public class Board {

    final private int size = 4;
    final private int initTiles = 2;

    public int[][] board = new int[size][size];

    private Board instance;

    public Board getInstance() {
        if (instance == null) {
            initialise();
        }
        return instance;
    }

    public void initialise() {
        instance = new Board();
        for (int i = 0; i < initTiles; i++) {
            addRandom();
        }
    }

    public void addTile(int x, int y, int tile) {
        board[x][y] = tile;
    }

    public void addRandom() {
        List<Integer> emptyCells = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] == 0) {
                    emptyCells.add(4 * i + j);
                }
            }
        }
        int cell = emptyCells.get((int) Math.random() * emptyCells.size());
        int randomTile = (Math.random() < 0.9 ? 2 : 4);
        board[cell / 4][cell % 4] = randomTile;
    }

    public boolean isFull() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean hasMove() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] == 0) {
                    return true;
                } else if (i + 1 < size && board[i][j] == board[i + 1][j]) {
                    return true;
                } else if (j + 1 < size && board[i][j] == board[i][j + 1]) {
                    return true;
                }
            }
        }
        return false;
    }

    public void move(int direction) {
        for (int i = 0; i < size; i++) {
            List<Integer> list = new ArrayList<>();
            for (int j = 0; j < size; j++) {
                if (direction == 0 && board[j][i] > 0) {                // up
                    list.add(board[j][i]);
                } else if (direction == 1 && board[i][size - j] > 0) {  // right
                    list.add(board[i][size - j]);
                } else if (direction == 2 && board[size - j][i] > 0) {  // down
                    list.add(board[size - j][i]);
                } else if (direction == 3 && board[i][j] > 0) {             // left
                    list.add(board[i][j]);
                }
            }
            list = mergeList(list);
            for (int j = 0; j < size; j++) {
                if (direction == 0) {           // up
                    board[j][i] = (j < list.size() ? list.get(j) : 0);
                } else if (direction == 1) {    // right
                    board[i][size - j] = (j < list.size() ? list.get(j) : 0);
                } else if (direction == 2) {    // down
                    board[size - j][i] = (j < list.size() ? list.get(j) : 0);
                } else {                        // left
                    board[i][j] = (j < list.size() ? list.get(j) : 0);
                }
            }
        }
    }

    private List<Integer> mergeList(List<Integer> list) {
        List<Integer> ans = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (i + 1 < list.size() && list.get(i) == list.get(i + 1)) {
                ans.add(list.get(i) * 2);
                i++;
            } else {
                ans.add(list.get(i));
            }
        }
        return ans;
    }

}
