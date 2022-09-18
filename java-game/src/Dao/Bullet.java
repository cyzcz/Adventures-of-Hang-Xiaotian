package Dao;

import javax.swing.*;
import java.awt.*;

public class Bullet {
    protected int bulletID;
    protected int x, y;
    protected int oldX, oldY;
    protected int originX, originY;
    protected int step = 1;
    protected int steps = 0;
    protected int tm = 100;
    protected int bulletDir = 0;
    protected int origin = 0;//0:无限制 1:于怪物 2:于角色
    protected float atk;
    protected boolean death;//控制子弹显示
    protected double angleX,angleY;
    protected Image img;
    protected String path = "src/image/Bullet/arm";
    //定义子弹信息
    /* PS:10寿命差不多飞出3格距离（飞到第三格）    */
    protected int[][] bulletList = {
           /*0atk  1寿命  2速度   3特效  4矩形宽/高  6偏移x 7偏移y 8伤害类型（0：对怪物  1：对角色 2：对全部）*/
            {10,     50,   8,      1,     32,32,     0,     0,    0,     4,    0,     0,          0},//木质短弓0
            {100,    50,   8,      2,     48,48,     0,     0,    0,     5,    1,     0,          3},//寒霜长弓1
            {50,     50,   8,      3,     32,32,     0,     0,    0,     4,    0,     0,          1},//火炎短杖2
            {50,     50,   8,      4,     32,32,     0,     0,    0,     4,    0,     0,          2},//毒妖短杖3
            {200,   100,   3,      5,    130,130,    0,   -50,    0,     8,    1,     1,          1},//黑煌法杖4
            {1000,   78,   0,      6,      0,0,      0,     0,    0,    14,    2,     1,          4},//死神柬5
            {0,      50,   0,      11,   110,110,    0,     0,    1,     4,    0,     1,          0},//史莱姆6
            {10,     17,   0,      11,    55,55,   -12,   -12,    1,     4,    1,     1,          0},//丧尸7
            {15,    250,   3,      11,    32,32,     3,     3,    1,     6,    0,     1,          0},//鬼吸蝠8
            {20,    250,   4,      11,    32,32,     3,     3,    1,     6,    0,     1,          0},//杀人蜂9
            {25,    500,   3,      11,    32,32,     0,     0,    1,     7,    0,     1,          0},//荒漠蝎10
    };                                                                            /*11行走图一致 12异常状态*/
                                                                      /*9帧数  10攻击类型（0：单体 1：穿透 2：全屏）*/
    public void gameMapPosition(int x, int y){
        this.originX = x;
        this.originY = y;
    }


    public int getBulletID() {
        return bulletID;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setBulletDir(int bulletDir) {
        this.bulletDir = bulletDir;
    }

    public int getAtk() {
        return (int)(bulletList[getBulletID() - 1][0]*atk);
    }

    public int getTime() {
        return bulletList[getBulletID() - 1][1];
    }

    public int getSpeed() {
        return bulletList[getBulletID() - 1][2];
    }

    public int getEffect() {
        return bulletList[getBulletID() - 1][3];
    }

    public int getBulletWidth() {
        return bulletList[getBulletID() - 1][4];
    }

    public int getBulletHeight() {
        return bulletList[getBulletID() - 1][5];
    }

    public int getDeviationx() {
        return bulletList[getBulletID() - 1][6];
    }

    public int getDeviationy() {
        return bulletList[getBulletID() - 1][7];
    }

    public int getType() {
        return bulletList[getBulletID() - 1][8];
    }

    public int getFrame() {
        return bulletList[getBulletID() - 1][9];
    }

    public int getPenetrate() {
        return bulletList[getBulletID() - 1][10];
    }

    public int sameImg() {
        return bulletList[getBulletID() - 1][11];
    }

    public int getStatus() {
        return bulletList[getBulletID() - 1][12];
    }

    public boolean isDeath() {
        return death;
    }

    public void setDeath(boolean death) {
        this.death = death;
    }

    public Image getImg() {
        String dir = null;
        String str = null;
        int bulletDirs;
        if (bulletDir > 10){
            bulletDirs = bulletDir/10;
        }else bulletDirs = bulletDir;
        switch (bulletDirs) {
            case 1:
                dir = "u";
                break;
            case 2:
                dir = "d";
                break;
            case 3:
                dir = "l";
                break;
            case 4:
                dir = "r";
                break;
        }
        if (sameImg() == 1) {
            str = path + bulletID + "_" + step + ".png";
        } else {
            str = path + bulletID + "_" + dir + "_" + step + ".png";
        }
        return (new ImageIcon(str)).getImage();
    }

    public int getWidth() {
        return getImg().getWidth(null);
    }

    public int getHeight() {
        return getImg().getHeight(null);
    }

    //获取子弹的范围
    public Rectangle getRect() {
        if (getBulletWidth() == 0) {
            return new Rectangle(0, 0, 1280, 720);
        } else return new Rectangle((originX-oldX)+getDeviationx() + x, (originY-oldY)+getDeviationy() + y, getBulletWidth(), getBulletHeight());
    }

    //子弹的碰撞过程
    public boolean hit(Rectangle w) {
        //如果子弹与矩形的范围重合子弹死亡
        if (!isDeath() && this.getRect().intersects(w)) {
            return true;
        }return false;
    }
}

