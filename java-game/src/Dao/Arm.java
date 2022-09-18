package Dao;

import javax.swing.*;
import java.awt.*;

public class Arm {
    //基本信息
    protected int id;
    protected int bulletID;
    protected float cd;
    protected float cdLimit;
    protected Image image = null;
    protected String name = null;
    protected String armInfo = null;

    //获取武器ID
    public int getId() {
        return id;
    }
    //设置CD
    public void setCd(int cd){
        this.cd = cd;
    }
    //设置CDLimit
    public void setCdLimit(int cdLimit){
        this.cdLimit = cdLimit;
    }
    //调整CD
    public void adjustCd(int a){
        this.cd += a;
    }
    //获取CD
    public float getCd(){
        return cd;
    }
    //获取CDLimit
    public float getCdLimit() {
        switch (id) {
            case 0:
                cdLimit = 20f;
                break;
            case 1:
                cdLimit = 100f;
                break;
            case 2:
                cdLimit = 100f;
                break;
            case 3:
                cdLimit = 100f;
                break;
            case 4:
                cdLimit = 500f;
                break;
            case 5:
                cdLimit = 2000f;
                break;
        }
        return cdLimit;
    }
    //获取CD
    public int spCost() {
        int spCost = 0;
        switch (id) {
            case 0:
                spCost = 1;
                break;
            case 1:
                spCost = 10;
                break;
            case 2:
                spCost = 2;
                break;
            case 3:
                spCost = 2;
                break;
            case 4:
                spCost = 10;
                break;
            case 5:
                spCost = 20;
                break;
        }
        return spCost;
    }
    //获取子弹ID
    public int getBulletID() {
        switch (id) {
            case 0:
                bulletID = 1;
                break;
            case 1:
                bulletID = 2;
                break;
            case 2:
                bulletID = 3;
                break;
            case 3:
                bulletID = 4;
                break;
            case 4:
                bulletID = 5;
                break;
            case 5:
                bulletID = 6;
                break;
        }
        return bulletID;
    }

    //获取图像
    public Image getImage() {
        image = (new ImageIcon("src/image/Item/arm/arm"+id+".png")).getImage();
        return image;
    }

    //获取名字
    public String getName() {
        switch (id) {
            case 0:
                name = "铅笔";
                break;
            case 1:
                name = "高等数学";
                break;
            case 2:
                name = "单板吉他";
                break;
            case 3:
                name = "大学物理";
                break;
            case 4:
                name = "电子橡皮";
                break;
            case 5:
                name = "校徽";
                break;
        }
        return name;
    }
    //获取效果
    public String getEffect() {
        switch (id) {
            case 0:
                armInfo = "ATK:10 CD:20 spCost:1";
                break;
            case 1:
                armInfo = "ATK:100 CD:250 spCost:10";
                break;
            case 2:
                armInfo = "ATK:50 CD:100 spCost:6";
                break;
            case 3:
                armInfo = "ATK:50 CD:100 spCost:6";
                break;
            case 4:
                armInfo = "ATK:200 CD:500 spCost:10";
                break;
            case 5:
                armInfo = "ATK:1000 CD:2000 spCost:20";
                break;
        }
        return armInfo;
    }
    //获取介绍
    public String getInfo() {
        switch (id) {
            case 0:
                armInfo = "常见的普通铅笔";
                break;
            case 1:
                armInfo = "高等数学教材，看起来很不好学";
                break;
            case 2:
                armInfo = "一个吉他，据说能发出美妙的歌声";
                break;
            case 3:
                armInfo = "一本大学物理教材，上面书写着深奥晦涩的文字";
                break;
            case 4:
                armInfo = "一个可以在电子设备上使用的橡皮擦，可以擦掉人的noj作业";
                break;
            case 5:
                armInfo = "西工大的校徽，有着不可思议的力量";
                break;
        }
        return armInfo;
    }

    public void setId(int id) {
        this.id = id;
    }
}
