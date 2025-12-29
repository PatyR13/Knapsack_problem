/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package klockimans;

import java.util.ArrayList;

/**
 *
 * @author pfigat
 */
public class Tafla {
    
    private int width;
    private int height;
    
    private int posx;
    private int posy;
    
    private ArrayList <Klocek> tabKlocki;

    public Tafla(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getPosx() {
        return posx;
    }

    public void setPosx(int posx) {
        this.posx = posx;
    }

    public int getPosy() {
        return posy;
    }

    public void setPosy(int posy) {
        this.posy = posy;
    }

    public ArrayList <Klocek> getTabKlocki() {
        return tabKlocki;
    }

    public void setTabKlocki(ArrayList <Klocek> tabKlocki) {
        this.tabKlocki = tabKlocki;
    }
    
    
    
    
}
