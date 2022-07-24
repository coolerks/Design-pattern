package com.xiaoxu.principle.iterator;

import java.util.*;

public class Main implements Iterator<Main> {
    public static void main(String[] args) {
        Main main = new Main();
        while (main.hasNext()) {
            Main m = main.next();


        }
    }

    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public Main next() {
        return null;
    }
}
