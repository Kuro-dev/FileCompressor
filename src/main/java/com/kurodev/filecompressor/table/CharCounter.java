package com.kurodev.filecompressor.table;

/**
 * @author kuro
 **/
public class CharCounter {
    private final byte chara;
    private int counter;
    private int leadingZeros;

    CharCounter(byte chara, int counter) {
        this.chara = chara;
        this.counter = counter;
    }

    public CharCounter(byte character) {
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
                "char=" + (char) chara +
                ", counter=" + counter +
                '}';
    }

    void increment() {
        counter++;
    }

    public byte getCharacter() {
        return chara;
    }

    public int getCount() {
        return counter;
    }
}
