package Dao;

import javax.swing.*;
import java.awt.*;

public class Effect {
    protected int step = 1;
    protected int steps = 0;
    protected int x, y;
    protected int oldX, oldY;
    protected int originX, originY;
    protected int efid;
    protected boolean live = true;
    protected String str = null;
    protected Image img = null;
    //定义特效信息
    protected int[][] effecttList = {
            /*w     h     dx   dy   f  tm  fs*/
            {0,     0,    0,    0,  1,  1, 0},//被击特效0
            {200, 200,    15,  15,  4, 17, 0},//物理箭特效1
            {300, 300,     0,  20,  5, 23, 0},//冰霜箭特效2
            {200, 200,    15,  15,  5, 17, 0},//火球特效3
            {200, 200,    15,  15,  5, 23, 0},//毒球特效4
            {420, 420,    30,  30,  6, 29, 0},//黑暗球特效5
            {300, 300,    30,   0,  9, 47, 0},//死神特效6
            {40,   50,    17,   0,  5, 23, 0},//燃烧特效7
            {40,   50,    15,   0,  5, 23, 0},//中毒特效8
            {80,   80,    20,  30,  5, 23, 0},//冰冻特效9
            {110, 110,    20,  15,  5, 23, 0},//致死特效10
            {64,   64,    10,  10,  5, 23, 0},//角色受伤特效11
            {350, 300,    20,  8, 10, 53, 0},//复活特效12
    };

    //通常
    public Effect() {
    }

    public Image getImage() {
        str = "src/image/Effect/ef" + efid + "_" + step + ".png";
        return img = (new ImageIcon(str)).getImage();
    }

    public int getWidth() {
        return effecttList[efid][0];
    }

    public int getHeight() {
        return effecttList[efid][1];
    }

    public int getDeviationx() {
        return effecttList[efid][2];
    }

    public int getDeviationy() {
        return effecttList[efid][3];
    }

    public int getFrame() {
        return effecttList[efid][4];
    }

    public int getTime() {
        return effecttList[efid][5];
    }

    public int fullScreen() {
        return effecttList[efid][6];
    }

}
