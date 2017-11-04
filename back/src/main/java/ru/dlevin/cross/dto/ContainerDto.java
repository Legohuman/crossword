package ru.dlevin.cross.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ContainerDto {
    private int x;
    private int y;
    @JsonProperty("v")
    private boolean isVertical;
    @JsonProperty("l")
    private int length;
    @JsonProperty("t")
    private String text;

    public ContainerDto() {
    }

    public ContainerDto(int x, int y, boolean isVertical, int length, String text) {
        this.x = x;
        this.y = y;
        this.isVertical = isVertical;
        this.length = length;
        this.text = text;
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

    public boolean isVertical() {
        return isVertical;
    }

    public void setVertical(boolean vertical) {
        this.isVertical = vertical;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
