package com.kurodev.filecompressor.table;

/**
 * @author kuro
 **/
public class CharCounter {
    private final char chara;
    private int counter;
    private int leadingZeros;

    CharCounter(char chara, int counter) {
        this.chara = chara;
        this.counter = counter;
    }

    public CharCounter(char character) {
        this(character, 0);
    }

    public int getLeadingZeros() {
        return leadingZeros;
    }

    public void setLeadingZeros(int i) {
        leadingZeros = i;
    }

    @Override
    public String toString() {
        return "{" +
                "chara=" + chara +
                ", counter=" + counter +
                '}';
    }

    void increment() {
        counter++;
    }

    public char getCharacter() {
        return chara;
    }

    public int getCount() {
        return counter;
    }
}
