package Model;

import java.awt.*;
import java.util.Random;

public class Item {
    private int id;
    private int x;
    private int y;
    private int width;
    private int height;
    private Color color;
    private boolean rotated;

    public Item(int id, int width, int height) {
        this.id = id;

        this.width = width;
        this.height = height;
        this.rotated = false;
        Random random = new Random();
        color = new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
    }

    public Item(Item other) {
        this.id = other.id;
        this.x = other.x;
        this.y = other.y;
        this.width = other.width;
        this.height = other.height;
        this.color = other.color;
        this.rotated = other.rotated;
    }
    // Getters et Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public boolean isRotated() {
        return rotated;
    }

    public void setRotated(boolean rotated) {
        this.rotated = rotated;
    }

    // Methods
    public Item rotate() {

        if (!rotated) {
            int temp = width;
            width = height;
            height = temp;
            rotated = true;
        }

        return this;
    }
}