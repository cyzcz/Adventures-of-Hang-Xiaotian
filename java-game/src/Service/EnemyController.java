package Service;

import Dao.Dialog;
import Dao.Enemy;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import Frame.*;
import Dao.*;
import View.*;
/**
 * The type enemy.
 */
public class EnemyController extends Enemy {
    /**
     * 子弹
     */
    public ArrayList<BulletController> bullets = new ArrayList<>();
    /**
     * 伤害
     */
    public ArrayList<EffectController> effects = new ArrayList<>();
    /**
     * Instantiates a new enemy.
     *
     * @param id   the id
     * @param x    the x
     * @param y    the y
     * @param oldx the oldx
     * @param oldy the oldy
     */
    public EnemyController(int id, int x, int y, int oldx, int oldy) {
        super(id, x, y, oldx, oldy);
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
    public boolean isVisible()
    {
        return visible;
    }
    public void draw(Graphics g, GameFrame gf, int id) {

    }

    private void stepping(){
        steps++;
        if (steps >= Math.abs(10/speed)+3){
            step++;
            steps = 0;
        }
        if (step == 3) {
            step = 1;
        }
    }

    /**
     * 怪物的数据判断
     */
    public void enemyDataCheck(GameFrame gf) {
        if (hp > hpLimit) {
            hpLimit = hp;
        }
        if (hp < 0) {
            hp = 0;
        }
        if (hpLimit < 0) {
            hpLimit = 1;
        }
        if (hp <= 0) {
            if (enemyList[getEnemyID() - 1][1] != 0){
                live = false;
                System.out.println("怪物死了");
                gf.actorData.adjustExp(this.exp);
            }
        }
    }

    /**
     * enemy satus update.
     *怪物状态更新
     * @param gf the gf
     */
//更新状态
    public void enemySatusUpdate(GameFrame gf) {
        //燃烧
        if (status[0] == 1) {
            statusTime[0]--;
            if (statusTime[0]%24 == 0){
                addEffect(7);
            }
            if (statusTime[0]%50 == 0){
                if (hp >= hp * 0.01) {
                    hp -= hp * 0.01;
                } else {
                    hp = 0;
                }
            }
            if (statusTime[0] <= 0) {
                status[0] = 0;
            }
        }
        //中毒
        if (status[1] == 1) {
            statusTime[1]--;
            if (statusTime[1]%24 == 0){
                addEffect(8);
            }
            if (statusTime[1]%50 == 0){
                if (hp >= (hpLimit - hp) * 0.01) {
                    hp -= (hpLimit - hp) * 0.01;
                } else {
                    hp = 0;
                }
            }
            if (statusTime[1] <= 0) {
                status[1] = 0;
            }
        }
        //冰冻
        if (status[2] == 1) {
            statusTime[2]--;
            if (statusTime[2]%24 == 0){
                addEffect(9);
            }
            if (statusTime[2] <= 0) {
                status[2] = 0;
            }
        }
        //致死
        if (status[3] == 1) {
            statusTime[3]--;
            if (statusTime[3]%100 == 0){
                addEffect(10);
            }
            if (statusTime[3]%50 == 0){
                if (hp >= hp * 0.05) {
                    hp -= hp * 0.05;
                } else {
                    hp = 0;
                }
            }
            if (statusTime[3] <= 0) {
                status[3] = 0;
            }
        }
    }

    /**
     * 怪物受伤
     *
     * @param b  the b
     * @param gm the gm
     */
//怪物受伤
    /*灵感
    * 伤害帧：在子弹类的子弹信息列表写上希望产生伤害的帧数，然后在这里进行判断，是否为伤害帧，产生伤害一次。*/
    public void enemyHit(BulletController b, GameMap gm) {
        if (bullets.contains(b)){
            return;
        }
        bullets.add(b);
        if (b.getStatus() == 1){
            this.status[0] = 1;
            this.statusTime[0] = 500;
        }
        if (b.getStatus() == 2){
            this.status[1] = 1;
            this.statusTime[1] = 500;
        }
        if (b.getStatus() == 3){
            this.status[2] = 1;
            this.statusTime[2] = 500;
        }
        if (b.getStatus() == 4){
            this.status[3] = 1;
            this.statusTime[3] = 1000;
        }
        this.hp -= b.getAtk()*(1-getDEF());
        gm.addEffect(b.getX(), b.getY(), b.getEffect());
        if (b.getPenetrate() == 0){
            b.setDeath(true);
        }
    }

    /**
     * 怪物攻击
     *
     * @param gf the gf
     */
//怪物攻击
    public void enemyAtk(GameFrame gf) {
        if (actionATK) {
            //近战
            if (getEnemyID() == 2|| getEnemyID() == 3){
                BulletController bullet = new BulletController(x, y, getBulletId(),atk);
                gf.getGameMap().bullets.add(bullet);
                actionATK = false;
            }
            //固定瞄准角度
            if (getEnemyID() == 4|| getEnemyID() == 5){
                double a = Math.abs((originX-oldX)+x - gf.actor.getX());
                double b = Math.abs((originY-oldY)+y - gf.actor.getY());
                double c = Math.sqrt(a*a + b*b);
                BulletController bullet = new BulletController(a/c,b/c,x, y, getBulletId(),atk);
                if ((originX-oldX)+x > gf.actor.getX()&&(originY-oldY)+y <= gf.actor.getY()){
                    bullet = new BulletController(-a/c,b/c,x, y, getBulletId(),atk);
                }
                if ((originX-oldX)+x >= gf.actor.getX()&&(originY-oldY)+y > gf.actor.getY()){
                    bullet = new BulletController(-a/c,-b/c,x, y, getBulletId(),atk);
                }
                if ((originX-oldX)+x < gf.actor.getX()&&(originY-oldY)+y >= gf.actor.getY()){
                    bullet = new BulletController(a/c,-b/c,x,  y, getBulletId(),atk);
                }
                if ((originX-oldX)+x <= gf.actor.getX()&&(originY-oldY)+y < gf.actor.getY()){
                    bullet = new BulletController(a/c,b/c, x, y, getBulletId(),atk);
                }
                gf.getGameMap().bullets.add(bullet);
                actionATK = false;
            }
            //跟踪子弹
            if (getEnemyID() == 6){
                BulletController bullet = new BulletController(x, y, getBulletId(),atk);
                gf.getGameMap().bullets.add(bullet);
                actionATK = false;
            }
        }
    }

    /**
     * Move.
     *
     * @param gf the gf
     */
//************怪物AI部分***************
    public void move(GameFrame gf) {
        if (action){
            if (fly&&getType() == 0){
                stepping();
            }
            if (status[2] != 1){
                switch (getMoveMode()) {
                    case 0: {
                    }
                    break;
                    //史莱姆
                    case 1: {
                        RandomMove();
                    }
                    break;
                    //丧尸男
                    case 2:
                        //丧尸女
                    case 3: {
                        if (RangeCheck(0, 5)) {
                            closeMove();
                            if (RangeCheck(0, 1)){
                                enemyAtk(gf);
                            }
                        }else RandomMove();
                    }
                    break;
                    //鬼吸蝠
                    case 4: {
                        if (RangeCheck(4, 7)) {
                            closeMove();
                        }
                        if (RangeCheck(0, 4)){
                            enemyAtk(gf);
                        }else RandomMove();
                    }
                    break;
                    //杀人蜂
                    case 5: {
                        if (RangeCheck(4, 6)) {
                            closeMove();
                        }
                        if (RangeCheck(0, 4)){
                            enemyAtk(gf);
                        }else RandomMove();
                    }
                    break;
                    //荒漠蝎
                    case 6: {
                        if (RangeCheck(5, 7)) {
                            closeMove();
                        }
                        if (RangeCheck(0, 5)){
                            enemyAtk(gf);
                        }else RandomMove();
                    }
                    break;
                }
            }
            if (status[3] == 1&&status[2] != 1){
                leaveMove();
            }
            action = false;
        }
    }

    /**
     * Action.
     */
//瞬间执行
    public void action() {
        if (getMoveMode() == 2||getMoveMode() == 3){
            if (RangeCheck(0, 5)) {
                speed = 3;
            }else speed = (int)enemyList[getEnemyID() - 1][11];
        }
        if (getType() == 1) {
            if (RangeCheck(0, 4)) {
                img = (new ImageIcon("src/image/enemy/tip2.png")).getImage();
            }
        }
        if (getType() == 2) {
            if (RangeCheck(0, 4)) {
                img = (new ImageIcon("src/image/enemy/tip3.png")).getImage();
            }
        }
        if (getType() == 3) {
            if (RangeCheck(0, 4)) {
                img = (new ImageIcon("src/image/enemy/tip4.png")).getImage();
            }
        }
    }

    /**
     * Range check boolean.
     *
     * @param a the a
     * @param b the b
     * @return the boolean
     */
//范围检测（a=0时，范围包括中心点a>=0，b>=1）
    public boolean RangeCheck(int a, int b) {
        Rectangle rectB = new Rectangle((originX-oldX)+x-b*32, (originY-oldY)+y-b*32, (2*b+1)*32, (2*b+1)*32);
        Rectangle rectA = new Rectangle((originX-oldX)+x-(a-1)*32, (originY-oldY)+y-(a-1)*32, (2*(a-1)+1)*32, (2*(a-1)+1)*32);
        if (a == 0) {
            if (rectB.intersects(actorRect)) {
                return true;
            }
        } else if (rectB.intersects(actorRect) && !rectA.intersects(actorRect)) {
            return true;
        }
        return false;
    }

    /**
     * Hit boolean.
     *
     * @param point the point
     * @return the boolean
     */
//方向碰撞检测（与墙、水、人）
    public boolean hit(int point) {
        int check = 0;
        for (int i = 0; i < wallList.size(); ++i) {
            Square wall = wallList.get(i);
            wall.getGameMapOrigin(originX,originY);
            if (this.getRect(point).intersects(wall.getRectangle()) || this.getRect(point).intersects(actorRect)) {
                count = 0;
                check++;
            }
        }
        for (int j = 0; j < waterList.size(); ++j) {
            Square water = waterList.get(j);
            if (this.getRect(point).intersects(water.getRectangle()) && !this.fly) {
                count = 0;
                check++;
            }
        }
        if (check > 0) {
            return true;
        }
        return false;
    }

    /**
     * Move u.
     *
     * @param speed the speed
     */
//朝各方向移动
    public void moveU(int speed) {
        if (!hit(1)) {
            this.y -= speed;
            stepping();
            dir = 1;
            if (count >= 1){
                count--;
            }
        }
    }

    /**
     * Move d.
     *
     * @param speed the speed
     */
    public void moveD(int speed) {
        if (!hit(2)) {
            this.y += speed;
            stepping();
            dir = 2;
            if (count >= 1){
                count--;
            }
        }
    }

    /**
     * Move l.
     *
     * @param speed the speed
     */
    public void moveL(int speed) {
        if (!hit(3)) {
            this.x -= speed;
            stepping();
            dir = 3;
            if (count >= 1){
                count--;
            }
        }
    }

    /**
     * Move r.
     *
     * @param speed the speed
     */
    public void moveR(int speed) {
        if (!hit(4)) {
            this.x += speed;
            stepping();
            dir = 4;
            if (count >= 1){
                count--;
            }
        }
    }

    /**
     * Close move.
     */
//靠近角色移动
    public void closeMove() {
        Random random=new Random();
        int i= random.nextInt(7);
        if(i<=3) {
            if (Math.abs((originX - oldX) + x - actorRect.x) >= Math.abs((originY - oldX) + y - actorRect.y)) {
                count = (int) (Math.random() * 20 + 10);
                if ((originX - oldX) + x >= actorRect.x) {
                    point = 3;
                    moveL(speed);
                }
                if ((originX - oldX) + x < actorRect.x) {
                    point = 4;
                    moveR(speed);
                }
            }
            if (Math.abs((originY - oldX) + y - actorRect.y) > Math.abs((originX - oldX) + x - actorRect.x)) {
                count = (int) (Math.random() * 20 + 10);
                if ((originY - oldX) + y >= actorRect.y) {
                    point = 1;
                    moveU(speed);
                }
                if ((originY - oldX) + y < actorRect.y) {
                    point = 2;
                    moveD(speed);
                }
            }
        }
        else{
            RandomMove();
        }
    }

    /**
     * Leave move.
     */
//远离角色移动
    public void leaveMove() {
        if (Math.abs((originX-oldX)+x - actorRect.x) >= Math.abs((originY-oldX)+y - actorRect.y)) {
            count = (int) (Math.random() * 20 + 10);
            if ((originX-oldX)+x >= actorRect.x) {
                point = 4;
                moveR(speed);
            }
            if ((originX-oldX)+x < actorRect.x) {
                point = 3;
                moveL(speed);
            }
        }
        if (Math.abs((originY-oldX)+y - actorRect.y) > Math.abs((originX-oldX)+x - actorRect.x)) {
            count = (int) (Math.random() * 20 + 10);
            if ((originY-oldX)+y >= actorRect.y) {
                point = 2;
                moveD(speed);
            }
            if ((originY-oldX)+y < actorRect.y) {
                point = 1;
                moveU(speed);
            }
        }
    }

    /**
     * Random move.
     */
//随机移动
    public void RandomMove() {
        int s = (int) (Math.random() * 8 + 1);
        if (count != 0) {
            switch (point) {
                case 1: {
                    dir = 1;
                    moveU(getSpeed());
                }
                break;
                case 2: {
                    dir = 2;
                    moveD(getSpeed());
                }
                break;
                case 3: {
                    dir = 3;
                    moveL(getSpeed());
                }
                break;
                case 4: {
                    dir = 4;
                    moveR(getSpeed());
                }
                break;
                case 0: {
                    count--;
                }
                break;
            }
        } else {
            switch (s) {
                case 1: {
                    point = 1;
                    dir = 1;
                    moveU(getSpeed());
                }
                break;
                case 2: {
                    point = 2;
                    dir = 2;
                    moveD(getSpeed());

                }
                break;
                case 3: {
                    point = 3;
                    dir = 3;
                    moveL(getSpeed());
                }
                break;
                case 4: {
                    point = 4;
                    dir = 4;
                    moveR(getSpeed());
                }
                break;
                default: {
                    point = 0;
                }
                break;
            }
            count = (int) (Math.random() * 10 + 5);
        }
    }

    /**
     * Gets effect x.
     *
     * @return the effect x
     */
    public int getEffectX() {
        return (originX-oldX)+x;
    }

    /**
     * Gets effect y.
     *
     * @return the effect y
     */
    public int getEffectY() {
        return (originY-oldY)+y;
    }

    /**
     * Add effect.
     *
     * @param efid the efid
     */
//于怪物上显示特效
    public void addEffect(int efid) {
        effects.add(new EffectController(this, originX, originY, efid));
    }

    /**
     * Draw effect.
     *
     * @param g the g
     */
//于怪物上绘制特效
    public void drawEffect(Graphics g) {
        for (int i = 0; i < effects.size(); i++) {
            effects.get(i).draw(g,originX,originY,this);
        }
    }

    /**
     * Gets rectangle.
     *
     * @return the rectangle
     */
    public Rectangle getRectangle() {
        return new Rectangle((originX-oldX)+x, (originY-oldY)+y, getWidth(), getHeight());
    }

    /**
     * Draw enemy.
     *
     * @param g the g
     */
    public void drawenemy(Graphics g) {
        g.drawImage(img, (originX-oldX)+this.x+getDeviationx(), (originY-oldY)+this.y+getDeviationy(), getImgWidth(), getImgHeight(), null);
        drawEffect(g);
    }

    public void drawInfo(Graphics g) {
        if (actionTimer >= 2) {
            action = true;
            actionTimer = 0;
        } else actionTimer++;
        if (atkCd >= atkCdLimit) {
            actionATK = true;
            atkCd = 0;
        } else atkCd++;
        Font mainfont = new Font("宋体", Font.PLAIN, 12);
        g.setFont(mainfont);
        g.setColor(Color.white);
        String HPstr = getenemyName() + " " + ((int)this.hp) + "/" + ((int)this.hpLimit);
        if (enemyList[getEnemyID() - 1][1] != 0){
            g.drawString(HPstr, (originX-oldX)+this.x + this.getImgWidth() / 2 - (HPstr.length() * 12) / 3, (originY-oldY)+this.y);

        }
    }
}
