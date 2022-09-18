package Service;

import Service.ActorController;
import Service.GameMap;

import javax.swing.*;
import java.awt.*;
import Frame.*;
import Dao.*;
import View.*;
public class EffectController extends Effect{
    //通常
    public EffectController(int x, int y, int oldX, int oldY, int id) {
        this.x = x;
        this.y = y;
        this.efid = id;
        this.oldX = oldX;
        this.oldY = oldY;
    }

    //于怪物
    public EffectController(EnemyController enemy, int oldX, int oldY, int id) {
        this.x = enemy.getX();
        this.y = enemy.getY();
        this.efid = id;
        this.oldX = oldX;
        this.oldY = oldY;
    }

    //于角色
    public EffectController(ActorController actorController, int id) {
        this.x = actorController.getX();
        this.y = actorController.getY();
        this.efid = id;
    }

    //显示于怪物
    public void draw(Graphics g, int x, int y, EnemyController enemy) {
        this.originX = x;
        this.originY = y;
        if (!live) return;
        if (steps == getTime()) {
            live = false;  //爆炸死亡
            steps = 0;  //步骤时间归0
            enemy.effects.remove(this);  //集合中删除
            return;
        }
        steps++;
        if (steps % 6 == 0) {
            step++;
        }
        if (step == getFrame()) {
            step = 1;
        }
        g.drawImage(getImage(), enemy.getEffectX() - getWidth() / 2 + getDeviationx(), enemy.getEffectY() - getHeight() / 2 + getDeviationy(),getWidth(),getHeight(), null);
    }

    //显示于角色
    public void draw(Graphics g, ActorController actorController) {
        if (!live) return;
        if (steps == getTime()) {
            live = false;  //爆炸死亡
            steps = 0;  //步骤时间归0
            actorController.getEffects().remove(this);  //集合中删除
            return;
        }
        steps++;
        if (steps % 6 == 0) {
            step++;
        }
        if (step == getFrame()) {
            step = 1;
        }
        g.drawImage(getImage(), actorController.getX() - getWidth() / 2 + getDeviationx(), actorController.getY() - getHeight() / 2 + getDeviationy(),getWidth(),getHeight(), null);
    }

    //显示于地图
    public void draw(Graphics g, GameMap gm) {
        this.originX = gm.x;
        this.originY = gm.y;
        if (!live) return;
        if (steps == getTime()) {
            live = false;  //爆炸死亡
            steps = 0;  //步骤时间归0
            gm.effects.remove(this);  //集合中删除
            return;
        }
        steps++;
        if (steps % 6 == 0) {
            step++;
        }
        if (step == getFrame()) {
            step = 1;
        }
        if (fullScreen() != 1) {
            g.drawImage(getImage(), (originX-oldX)+x - getWidth() / 2 + getDeviationx(), (originY-oldY)+y - getHeight() / 2 + getDeviationy(),getWidth(),getHeight(), null);
        } else g.drawImage(getImage(), 1280 / 2 - getWidth() / 2, 720 / 2 - getHeight() / 2, null);
    }
}
