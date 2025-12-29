/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package klockimans;

import java.io.Serializable;

/**
 *
 * @author pfigat
 */
public class Klocek implements Serializable{
    
    
    private int width;
    private int height;
    
    private int posx;
    private int posy;

    public Klocek(int width, int height) {
        this.width = width;
        this.height = height;
        posx=0;
        posy=0;
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

    public void rotate() {
        int temp = width;
        width = height;
        height = temp;
    }
    
    public Klocek copy() {
        Klocek k = new Klocek(this.width, this.height);
        k.setPosx(this.posx);
        k.setPosy(this.posy);
        return k;
    }
    
    public int getArea() {
        return width * height;
    }

    @Override
    public String toString() {
        return "Klocek{" + "width=" + width + ", height=" + height + ", posx=" + posx + ", posy=" + posy + '}';
    }
    
}
