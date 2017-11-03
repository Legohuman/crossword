package ru.dlevin.cross.dto;

public class PlacementDto {
    public int x;
    public int y;
    public boolean v;
    public int l;
    public String t;

    public PlacementDto() {
    }

    public PlacementDto(int x, int y, boolean v, int l, String t) {
        this.x = x;
        this.y = y;
        this.v = v;
        this.l = l;
        this.t = t;
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

    public boolean isV() {
        return v;
    }

    public void setV(boolean v) {
        this.v = v;
    }

    public int getL() {
        return l;
    }

    public void setL(int l) {
        this.l = l;
    }

    public String getT() {
        return t;
    }

    public void setT(String t) {
        this.t = t;
    }
}
