package Dao;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;


/**
 * 数据
 *
 * @author Admin
 * @date 2021/12/11
 */
public class Data {
    /**
     * 人物的等级
     */
    protected int level=0;
    /**
     * 等级名称
     */
    protected ArrayList<Level> levelList = new ArrayList<>(Arrays.asList(
            new Level(0,"C语言入门",0,0,200,200,10),
            new Level(1,"Noj100题通关",0.1f,0.05f,100,50,50),
            new Level(2,"面向对象编程入门",0.15f,0.05f,200,100,150),
            new Level(3,"面向对象程序开发",0.25f,0.06f,400,200,400),
            new Level(4,"软件开发入门",0.35f,0.07f,800,400,1000),
            new Level(5,"软件开发测试",0.45f,0.08f,1500,800,2500),
            new Level(6,"初级软件工程师",0.55f,0.09f,2500,1500,5000),
            new Level(7,"软件大师",2,0.1f,4000,2000,10000)

    ));
    /**
     * 存活状态
     */
    protected boolean live = true;//
    /**
     * 死亡状态
     */
    protected boolean death;
    /**
     * 之前血量
     */
    protected float actorHPT = 1;
    /**
     * 现在血量
     */
    protected float actorHPF = 200;
    /**
     * 血量上限
     */
    protected float actorHPLimit = 200;
    /**
     * 当前SP
     */
    protected float actorSP = 200;//
    /**
     * SP上限
     */
    protected float actorSPLimit = 200;//
    /**
     * 指每帧回复血量数目
     * 血条移动的速度
     */
    protected float actorHPResume = (float)0.15;//
    /**
     * hp 恢复
     */
    protected float actorHPRecover = (float)0;
    /**
     * sp 恢复
     */
    protected float actorSPRecover = (float)0;
    /**
     * 攻击力
     */
    protected float atk = 1f;//攻击力
    /**
     * 防御力
     */
    protected float def = 0f;//防御力
    /**
     * 是否能看到hp 功能未完善
     */
    protected boolean checkHP = true;
    /**
     * 检查sp
     */
    protected boolean checkSP = true;
    /**
     * 勇者状态
     * 0hp恢复 1sp恢复 2速度提升 3攻击力提升 4防御力提升 5燃烧 6中毒
     */
    protected int[] status = {0,0,0,0,0,0,0};//
    /**
     * 状态持续的时间
     * 0hp恢复 1sp恢复 2速度提升 3攻击力提升 4防御力提升 5燃烧 6中毒
     */
    protected int[] statusTime = {100,100,1000,1000,1000,500,500};
    //声明角色数据
    /**
     * 角色金钱
     */
    protected int money;
    /**
     * 角色帽子
     */
    protected int hat;
    /**
     * 角色盔甲装备
     */
    protected int equip;
    /**
     * 角色武器
     */
    //生成武器容器
    protected float exp=0;
    protected float[][] hatList = {
            /*0HP      1SP     2ATK     3DEF*/
            {0,         0,    0,           0},
            {40,        0,    0,       0.10f},//草帽
            {80,        0,    0.10f,   0.15f},//耳机
            {40,      100,    0.15f,   0.10f},//学士法帽
            {400,       0,    0.20f,   0.30f},//华丽帽
            {240,     500,    0.30f,   0.20f}//安全帽
    };//设定装备属性
    /**
     * 装备清单
     */
    protected float[][] equipList = {
           /*0HP      1SP      2ATK      3DEF*/
            {0,        0,     0,           0},
            {50,       0,     0,       0.10f},//布衣
            {100,      0,     0.10f,   0.15f},//士兵铠甲
            {50,     100,     0.15f,   0.10f},//学士法袍
            {500,      0,     0.20f,   0.30f},//骑士铠甲
            {300,    500,     0.30f,   0.20f}//光明法袍
    };//设定装备属性
    public String getLevelName(int level)
    {
        return levelList.get(level).levelName;
    }
    //获取金钱
    public int getMoney() {
        return money;
    }
    //角色状态相关
    public boolean isLive() {
        return live;
    }

    public void setLive(boolean live) {
        this.live = live;
    }

    public boolean isDeath() {
        return death;
    }

    public void setDeath(boolean death) {
        this.death = death;
    }

    public void setActorHPT(int aHPT) {
        this.actorHPT = aHPT;
    }

    public void setActorHPF(int aHPF) {
        this.actorHPF = aHPF;
    }

    public void setActorHPLimit(int aHPLimit) {
        this.actorHPLimit = aHPLimit;
    }

    public void setActorSP(int aSP) {
        this.actorSP = aSP;
    }

    public void setActorSPLimit(int aSPLimit) {
        this.actorSPLimit = aSPLimit;
    }

    public float getActorHPT() {
        return actorHPT;
    }

    public float getActorHPF() {
        return actorHPF;
    }

    public float getActorHPLimit() {
        return actorHPLimit+getHPplus();
    }

    public float getActorSP() {
        return actorSP;
    }

    public float getActorSPLimit() {
        return actorSPLimit+getSPplus();
    }

    public float getAtk() {
        return atk+getATKplus();
    }

    public float getDEF() {
        return def+getDEFplus();
    }

    public void adjustStatus(int a, int b){
        status[a] = b;
    }

    public void adjustStatusTime(int a, int b){
        statusTime[a] = b;
    }

    public void setActorHPT(float actorHPT) {
        this.actorHPT = actorHPT;
    }

    public float getExp() {
        return exp;
    }

    public void setExp(float exp) {
        this.exp = exp;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
    //角色装备相关
    public int getHat() {
        return hat;//获取已装备的防具ID
    }

    public void setHat(int hat){
        this.hat = hat;
    }

    public int getEquip() {
        return equip;//获取已装备的防具ID
    }

    public void setEquip(int equip) {
        this.equip = equip;
    }

    public float getHPplus() {
        return equipList[getEquip()][0]+hatList[getHat()][0];
    }

    public float getSPplus() {
        return equipList[getEquip()][1]+hatList[getHat()][1];
    }

    public float getATKplus() {
        return equipList[getEquip()][2]+hatList[getHat()][2];
    }

    public float getDEFplus() {
        return equipList[getEquip()][3]+hatList[getHat()][3];
    }

    public String getHatName() {
        String name = "";
        switch (getHat()) {
            case 0:
                name = "无";
                break;
            case 1:
                name = "草帽";
                break;
            case 2:
                name = "耳机";
                break;
            case 3:
                name = "舒适帽";
                break;
            case 4:
                name = "华丽帽";
                break;
            case 6:
                name = "安全帽";
                break;
        }
        return name;
    }

    public String getEquipName() {
        String name = "";
        switch (getEquip()) {
            case 0:
                name = "无";
                break;
            case 1:
                name = "布衣";
                break;
            case 2:
                name = "运动服";
                break;
            case 3:
                name = "羽绒服";
                break;
            case 4:
                name = "校园披肩";
                break;
            case 5:
                name = "华服";
                break;
        }
        return name;
    }

    public String getHatEffect() {
        String effect = "";
        switch (getHat()) {
            case 0:
                effect = " ";
                break;
            case 1:
                effect = "HP:40 DEF:0.05";
                break;
            case 2:
                effect = "HP:80 DEF:0.1";
                break;
            case 3:
                effect = "HP:40 SP:100 DEF:0.05";
                break;
            case 4:
                effect = "HP:400 DEF:0.25";
                break;
            case 5:
                effect = "HP:240 SP:500 DEF:0.15";
                break;
        }
        return effect;
    }

    public String getEquipEffect() {
        String effect = "";
        switch (getEquip()) {
            case 0:
                effect = " ";
                break;
            case 1:
                effect = "HP:50 DEF:0.05";
                break;
            case 2:
                effect = "HP:100 DEF:0.1";
                break;
            case 3:
                effect = "HP:50 SP:100 DEF:0.05";
                break;
            case 4:
                effect = "HP:500 DEF:0.25";
                break;
            case 5:
                effect = "HP:300 SP:500 DEF:0.15";
                break;
        }
        return effect;
    }

    public String getHatInfo() {
        String info = "";
        switch (getHat()) {
            case 0:
                info = " ";
                break;
            case 1:
                info = "普通的不能再普通的帽子";
                break;
            case 2:
                info = "普通的音乐耳机，可以听歌";
                break;
            case 3:
                info = "好看的帽子，可以让人心神愉悦";
                break;
            case 4:
                info = "华丽奢华的帽子，看起来很贵";
                break;
            case 5:
                info = "安全帽，可以有效地保护人不受学习的伤害";
                break;
        }
        return info;
    }

    public String getEquipInfo() {
        String info = "";
        switch (getEquip()) {
            case 0:
                info = " ";
                break;
            case 1:
                info = "再普通不过的白色衬衫";
                break;
            case 2:
                info = "运动少年的运动服";
                break;
            case 3:
                info = "好看的羽绒服，可以让人温暖起来";
                break;
            case 4:
                info = "校园风披肩，让人不禁回头";
                break;
            case 5:
                info = "华丽的衣服，看起来就想买。";
                break;
        }
        return info;
    }

    public void setActorHPF(float actorHPF) {
        this.actorHPF = actorHPF;
    }

    public void setActorHPLimit(float actorHPLimit) {
        this.actorHPLimit = actorHPLimit;
    }

    public void setActorSP(float actorSP) {
        this.actorSP = actorSP;
    }

    public void setActorSPLimit(float actorSPLimit) {
        this.actorSPLimit = actorSPLimit;
    }

    public void setActorHPResume(float actorHPResume) {
        this.actorHPResume = actorHPResume;
    }

    public void setActorHPRecover(float actorHPRecover) {
        this.actorHPRecover = actorHPRecover;
    }

    public void setActorSPRecover(float actorSPRecover) {
        this.actorSPRecover = actorSPRecover;
    }

    public void setAtk(float atk) {
        this.atk = atk;
    }

    public void setDef(float def) {
        this.def = def;
    }

    public void setCheckHP(boolean checkHP) {
        this.checkHP = checkHP;
    }

    public void setCheckSP(boolean checkSP) {
        this.checkSP = checkSP;
    }

    public void setStatus(int[] status) {
        this.status = status;
    }

    public void setStatusTime(int[] statusTime) {
        this.statusTime = statusTime;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void setHatList(float[][] hatList) {
        this.hatList = hatList;
    }

    public void setEquipList(float[][] equipList) {
        this.equipList = equipList;
    }

    public Image getHatimg() {
        Image img = (new ImageIcon("src/image/Item/hat/hat"+getHat()+".png")).getImage();
        return img;
    }

    public Image getEquipimg() {
        Image img = (new ImageIcon("src/image/Item/equip/equip"+getEquip()+".png")).getImage();
        return img;
    }


}
