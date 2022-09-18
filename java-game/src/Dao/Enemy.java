package Dao;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * The type enemy.
 */
public class Enemy {
    /**
     * 怪物id
     */
    protected int enemyID;
    /**
     * x,y坐标
     */
    protected int x, y;
    /**
     * oldx，oldy
     */
    protected int oldX, oldY;
    /**
     * originx
     */
    protected int originX, originY;
    /**
     * dx
     */
    protected int dx, dy;
    /**
     * 宽度与高度
     */
    protected int width, height;
    /**
     * 朝向
     */
    protected int dir = 2;
    /**
     * 攻击间隔
     */
    protected int atkCd = 0;
    /**
     * 攻击间隔限制
     */
    protected int atkCdLimit = 0;
    /**
     *
     */
    protected int step = 1;
    /**
     *
     */
    protected int steps = 0;
    /**
     * 行动计时器
     */
    protected int actionTimer = 0;
    /**
     * 子弹id
     */
    protected int bulletId;
    /**
     * 战利品
     */
    protected int loot;
    /**
     * 速度
     */
    protected int speed;
    /**
     * 血量，血上限
     */
    protected float hp, hpLimit;
    /**
     * 攻击力，防御力
     */
    protected float atk, def;
    /**
     * 飞翔
     */
    protected boolean fly;
    /**
     * 存活状态
     */
    protected boolean live = true;
    /**
     * 事件
     */
    protected boolean action;
    /**
     * 事件攻击力
     */
    protected boolean actionATK;
    /**
     *
     */
    public int count = 5;
    /**
     *
     */
    public int point = 0;
    /**
     * 图片
     */
    protected Image img;
    /**
     * 怪物矩形
     */
    protected Rectangle actorRect = null;

    public int[] status = {  0,  0,  0,  0,  0};//0燃烧 1中毒 2冰冻 3恐惧 4没想好
    /**
     * 状态的时间
     */
    public int[] statusTime = {  500,  500,  500,  500,  500};
    /**
     * 墙列表
     *///生成空气墙容器
    protected ArrayList<Square> wallList = new ArrayList<>();
    /**
     *
     *///生成水容器容器
    protected ArrayList<Square> waterList = new ArrayList<>();
    protected float exp;
    /**
     * 怪物列表
     *///定义怪物信息
    protected boolean visible=true;
    protected float[][] enemyList = {
            /*0id 1hp    2子弹  3掉落物 4矩形宽 5矩形高  6偏移x 7偏移y 8移动方式  9飞行(0不会 1会) 10攻击CD 11速度 12/13攻防*/
            {0,   100,    7,     1,     32,     32,     0,     0,    1,        0,              200,     1,    1,0,10},//简单的知识
            {0,   250,    8,     2,     32,     32,     0,     0,     2,        0,             200,     2,    1,0,150},//学霸猫
            {0,   250,   8,     2,     32,     32,     0,     0,     3,        0,             200,     2,    1,0,150},//学霸狗
            {0,   1000,   9,     2,     32,     32,     0,     0,     4,        0,             300,     3,    1,0,1000},//汽车
            {0,   1500,  10,     2,     32,     32,     0,     0,     5,        1,             300,     3,    1,0,2000},//战斗机
            {0,   5000,  11,     2,     32,     32,     0,     0,     5,        0,             500,     2,    1,0,10000},//未定义
            {1,   0,      0,     0,     32,     32,   -96,   -160,    0,        1,               0,     0,    1,0,0},//路牌1
            {2,   0,      0,     0,     32,     32,   -96,   -160,    0,        1,               0,     0,    1,0,0},//未定义
            {3,   0,      0,     0,     32,     32,   -96,   -160,    0,        1,               0,     0,    1,0,0},//未定义
            {4,   0,      0,     0,     32,     32,   -96,   -160,    0,        1,               0,     0,    1,0,0},//npc1
            {4,   0,      0,     0,     32,     32,   -96,   -160,    0,        1,               0,     0,    1,0,0},//宝箱1
    };
    public void setVisible(boolean visible) {
        this.visible = visible;
    }
    public boolean isVisible()
    {
        return visible;
    }
    /**
     * Instantiates a new enemy.
     *
     * @param id   the id
     * @param x    the x
     * @param y    the y
     * @param oldx the oldx
     * @param oldy the oldy
     */
    public Enemy(int id, int x, int y, int oldx, int oldy) {
        this.enemyID = id;
        this.x = x;
        this.y = y;
        this.oldX = oldx;
        this.oldY = oldy;
        this.hp = enemyList[getEnemyID() - 1][1];
        this.hpLimit = enemyList[getEnemyID() - 1][1];
        this.bulletId = (int)enemyList[getEnemyID() - 1][2];
        this.loot = (int)enemyList[getEnemyID() - 1][3];
        this.width = (int)enemyList[getEnemyID() - 1][4];
        this.height = (int)enemyList[getEnemyID() - 1][5];
        this.dx = (int)enemyList[getEnemyID() - 1][6];
        this.dy = (int)enemyList[getEnemyID() - 1][7];
        this.atkCdLimit = (int)enemyList[getEnemyID() - 1][10];
        this.speed = (int)enemyList[getEnemyID() - 1][11];
        this.atk = enemyList[getEnemyID() - 1][12];
        this.def = enemyList[getEnemyID() - 1][13];
        this.exp=enemyList[getEnemyID() - 1][14];
        if (enemyList[getEnemyID() - 1][9] == 1) {
            this.fly = true;
        } else this.fly = false;
    }

    /**
     * Gets x.
     *
     * @return the x
     */
    public int getX() {
        return x;
    }

    /**
     * Sets x.
     *
     * @param x the x
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Gets y.
     *
     * @return the y
     */
    public int getY() {
        return y;
    }

    /**
     * Sets y.
     *
     * @param y the y
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Is live boolean.
     *
     * @return the boolean
     */
    public boolean isLive() {
        return live;
    }

    /**
     * Is fly boolean.
     *
     * @return the boolean
     */
    public boolean isFly() {
        return fly;
    }

    /**
     * Sets live.
     *
     * @param live the live
     */
    public void setLive(boolean live) {
        this.live = live;
    }

    /**
     * Sets enemy hp.
     *
     * @param hp the hp
     */
    public void setenemyHP(float hp) {
        this.hp = hp;
    }

    /**
     * Gets enemy id.
     *
     * @return the enemy id
     */
    public int getEnemyID() {
        return enemyID;
    }

    /**
     * Gets enemy hp.
     *
     * @return the enemy hp
     */
    public float getenemyHP() {
        return hp;
    }

    /**
     * Gets bullet id.
     *
     * @return the bullet id
     */
    public int getBulletId() {
        return bulletId;
    }

    /**
     * Gets loot.
     *
     * @return the loot
     */
    public int getLoot() {
        return loot;
    }

    /**
     * Gets width.
     *
     * @return the width
     */
    public int getWidth() {
        return width;
    }

    /**
     * Gets height.
     *
     * @return the height
     */
    public int getHeight() {
        return height;
    }

    /**
     * Gets img width.
     *
     * @return the img width
     */
    public int getImgWidth() {
        return getenemyimg().getWidth(null);
    }

    /**
     * Gets img height.
     *
     * @return the img height
     */
    public int getImgHeight() {
        return getenemyimg().getHeight(null);
    }

    /**
     * Gets deviationx.
     *
     * @return the deviationx
     */
    public int getDeviationx() {
        return dx;
    }

    /**
     * Gets deviationy.
     *
     * @return the deviationy
     */
    public int getDeviationy() {
        return dy;
    }

    /**
     * Gets move mode.
     *
     * @return the move mode
     */
    public int getMoveMode() {
        return (int)enemyList[getEnemyID() - 1][8];
    }

    /**
     * Gets speed.
     *
     * @return the speed
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * Gets atk.
     *
     * @return the atk
     */
    public float getATK() {
        return atk;
    }

    /**
     * Gets def.
     *
     * @return the def
     */
    public float getDEF() {
        return def;
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public int getType() {
        return (int)enemyList[getEnemyID() - 1][0];
    }

    public int getOldX() {
        return oldX;
    }

    public int getOldY() {
        return oldY;
    }

    public int getOriginX() {
        return originX;
    }

    public int getOriginY() {
        return originY;
    }

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }

    public String getDirStr(){
        if (dir == 1){
            return "u";
        }else if (dir == 2){
            return "d";
        }else if (dir == 3){
            return "l";
        }else if (dir == 4){
            return "r";
        }return "d";
    }

    /**
     * Gets enemy name.
     *
     * @return the enemy name
     */
    public String getenemyName() {
        switch (getEnemyID()) {
            case 1:
                return "简单的知识";
            case 2:
                return "学霸猫";
            case 3:
                return "学霸狗";
            case 4:
                return "汽车";
            case 5:
                return "战斗机";
        }
        return "";
    }

    /**
     * 获取怪物图片
     *
     */
    public Image getenemyimg() {
        if (getType() == 0){
            if (getEnemyID() != 1){
                img = (new ImageIcon("src/image/Enemy/enemy"+ enemyID +"_"+getDirStr()+"_"+step+".png")).getImage();
            }else img = (new ImageIcon("src/image/Enemy/enemy1_1.png")).getImage();
        }else {
            if (getType() == 1||getType() == 2||getType() == 3){
                img = (new ImageIcon("src/image/Enemy/tip1.png")).getImage();
            }
        }
        return img;
    }

    /**
     * Sets enemyimg.
     *
     * @param img the img
     */
    public void setenemyimg(Image img) {
        this.img = img;
    }

    /**
     * Reset enemyimg.
     */
    public void resetenemyimg() {
        this.img = getenemyimg();
    }

    public Rectangle getRect(int point) {
        switch (point) {
            case 1: {
                return new Rectangle((originX-oldX)+x, (originY-oldY)+y - getSpeed(), getWidth(), getHeight());
            }
            case 2: {
                return new Rectangle((originX-oldX)+x, (originY-oldY)+y + getSpeed(), getWidth(), getHeight());
            }
            case 3: {
                return new Rectangle((originX-oldX)+x - getSpeed(), (originY-oldY)+y, getWidth(), getHeight());
            }
            case 4: {
                return new Rectangle((originX-oldX)+x + getSpeed(), (originY-oldY)+y, getWidth(), getHeight());
            }
        }
        return new Rectangle((originX-oldX)+x, (originY-oldY)+y, getWidth(), getHeight());
    }

    /**
     * Gets wall list.
     *
     * @param wallList  the wall list
     * @param waterList the water list
     */
    public void getWallList(ArrayList<Square> wallList, ArrayList<Square> waterList) {
        this.wallList = wallList;
        this.waterList = waterList;
    }

    /**
     * Gets actor rect.
     *
     * @param rect the rect
     */
    public void getActorRect(Rectangle rect) {
        actorRect = rect;
    }

    /**
     * Gets game map origin.
     *
     * @param x the x
     * @param y the y
     */
    public void getGameMapOrigin(int x, int y) {
        originX = x;
        originY = y;
    }

}
