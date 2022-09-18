package Service;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;

import Dao.*;
import Frame.*;
import View.*;

public class GameMap {
    public int id = 0;
    public int x;
    public int y;
    public int w;
    public int h;
    //导入游戏画面对象
    private GameFrame gf;
    public GameMap(GameFrame gf){
        this.gf = gf;
    }
    //读取地图数据
    private ArrayList<String> list = new ArrayList<>();
    public int[][] airWall;
    //生成背景
    public Ground bg = new Ground();
    //生成遮盖光影
    public Overlays overlays = new Overlays();
    //生成空气墙容器
    public Square wall;
    public ArrayList<Square> wallList = new ArrayList<>();
    //生成水容器
    public Square water;
    public ArrayList<Square> waterList = new ArrayList<>();
    //生成事件容器
    private int eventNumber;
    LinkedList<EventController> eventList = new LinkedList<>();
    //生成怪物容器
    public EnemyController enemy;
    ArrayList<EnemyController> enemyList = new ArrayList<>();
    //生成子弹容器
    public Bullet bullet;
    ArrayList<BulletController> bullets = new ArrayList<>();
    //生成特效容器
    public EffectController effect;
    ArrayList<EffectController> effects = new ArrayList<>();
    //默认地图路径

    public ArrayList<Square> getWallList() {
        return wallList;
    }

    public ArrayList<Square> getWaterList() {
        return waterList;
    }

    public LinkedList<EventController> getEventList() {
        return eventList;
    }

    private String mapPath = "src/map/air.txt";

    public ArrayList<BulletController> getBullets() {
        return bullets;
    }

    public ArrayList<EffectController> getEffects() {
        return effects;
    }

    public ArrayList<EnemyController> getEnemyList() {
        return enemyList;
    }
    private boolean isGif=false;
    //初始化地图数据
    public void initAirWall() {
        x = 0;
        y = 0;
        w = 0;
        h = 0;
        bg.x = 0;
        bg.y = 0;
        list.clear();
        wallList.clear();
        waterList.clear();
        eventList.clear();
        enemyList.clear();
        bullets.clear();
        effects.clear();
        switch (id){
            case 0:eventNumber = 0;
                 isGif=true;
                break;
            case 1:eventNumber = 3;
                isGif=false;
                break;
            case 2:eventNumber = 6;
                isGif=false;
                break;
            case 3:eventNumber = 2;
                break;
            case 4:eventNumber = 2;
                break;
            case 5:eventNumber = 3;
                break;
            case 6:eventNumber = 3;
                break;
            case 7:eventNumber = 2;
                break;
            case 8:eventNumber = 2;
                break;
            case 9:eventNumber = 6;
                break;
            case 10:eventNumber = 3;
                break;
            case 11:eventNumber = 2;
                break;
        }
    }
    public void initAirWall(String mapdata) {
        x = 0;
        y = 0;
        w = 0;
        h = 0;
        id=Integer.parseInt(mapdata);
        bg.x = 0;
        bg.y = 0;
        list.clear();
        wallList.clear();
        waterList.clear();
        eventList.clear();
        enemyList.clear();
        bullets.clear();
        effects.clear();
        switch (id){
            case 1:eventNumber = 3;
                break;
            case 2:eventNumber = 5;
                break;
            case 3:eventNumber = 2;
                break;
            case 4:eventNumber = 2;
                break;
            case 5:eventNumber = 3;
                break;
            case 6:eventNumber = 3;
                break;
            case 7:eventNumber = 2;
                break;
            case 8:eventNumber = 2;
                break;
            case 9:eventNumber = 6;
                break;
            case 10:eventNumber = 3;
                break;
            case 11:eventNumber = 2;
                break;
        }
    }

    //读取地图数据
    private void readAirWall() throws Exception {
        list.clear();
        mapPath = "src/map/air"+gf.getGameMap().id+".txt";
        FileInputStream fis = new FileInputStream(mapPath);
        InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader br = new BufferedReader(isr);
        //有就读，一回读一行，存储到list字符串容器中
        for (String value = br.readLine(); value != null; value = br.readLine()) {
            list.add(value);
        }
        br.close();
        //确定行高
        int row = list.size();
        int cloum = 0;
        int i;
        String str;
        String[] values;
        //先读取一行，确定宽度
        for (i = 0; i < 1; ++i) {
            str = list.get(i);
            values = str.split(",");//用","作为分隔符
            cloum = values.length;
        }
        //确定宽度后，创建二维数组
        airWall = new int[row][cloum];
        for (i = 0; i < list.size(); ++i) {
            str = list.get(i);
            values = str.split(",");
            for (int j = 0; j < values.length; ++j) {
                airWall[i][j] = Integer.parseInt(values[j]);
            }
        }
        System.out.println("结束");
    }

    //写入地图数据
    public void writeAirWall() throws Exception {
        readAirWall();
        int[][] newAirWall = new int[airWall.length][airWall[0].length];
        mapPath = "src/map/air"+gf.getGameMap().id+".txt";
        File file = new File(mapPath);
        if (file.exists()&&file.isFile()){
            file.delete();
            System.out.println("删除老文件");
        }
        FileWriter out = new FileWriter(file);
        for (int k = 0; k < wallList.size(); k++) {
            if ((wallList.get(k).getX()+wallList.get(k).getOldX())/32<=airWall[0].length && (wallList.get(k).getY()-wallList.get(k).getOldY())/32<=airWall.length){
                newAirWall[(wallList.get(k).getY()-wallList.get(k).getOldY())/32][(wallList.get(k).getX()+wallList.get(k).getOldX())/32] = 1;
            }
        }
        for (int k = 0; k < waterList.size(); k++) {
            if ((waterList.get(k).getX()+waterList.get(k).getOldX())/32<=airWall[0].length && (waterList.get(k).getY()-waterList.get(k).getOldY())/32<=airWall.length){
                newAirWall[(waterList.get(k).getY()-waterList.get(k).getOldY())/32][(waterList.get(k).getX()+waterList.get(k).getOldX())/32] = 2;
            }
        }
        for (int k = 0; k < enemyList.size(); k++) {
            if ((enemyList.get(k).getX()+enemyList.get(k).getOldX())/32<=airWall[0].length && (enemyList.get(k).getY()-enemyList.get(k).getOldY())/32<=airWall.length){
                newAirWall[(enemyList.get(k).getY()-enemyList.get(k).getOldY())/32][(enemyList.get(k).getX()+enemyList.get(k).getOldX())/32] = enemyList.get(k).getEnemyID()+9;
            }
        }

        for (int i = 0; i < newAirWall.length; i++) {
            for (int j = 0; j < newAirWall[i].length; j++) {
                out.write(newAirWall[i][j]+",");
            }
            out.write("\n");
        }
        out.close();
    }

    //创造矩形
    public void createSquare() throws Exception {
        initAirWall();
        readAirWall();
        //生成事件
        for (int k = 0; k < eventNumber; k++) {
            eventList.add(new EventController(id,x,y,k));
        }
        this.w = airWall[0].length*32;
        this.h = airWall.length*32;
        for (int i = 0; i < airWall.length; ++i) {
            for (int j = 0; j < airWall[0].length; ++j) {
                //空气墙（完全障碍）
                if (airWall[i][j] == 1) {
                    wall = new Square(x + j * 32, y + i * 32,x,y, 32, 32);
                    wallList.add(wall);
                }
                //水（低位障碍）
                if (airWall[i][j] == 2) {
                    water = new Square(x + j * 32, y + i * 32,x,y, 32, 32);
                    waterList.add(water);
                }
                //怪物
                if (airWall[i][j] >= 10) {
                    enemy = new EnemyController(airWall[i][j] - 9, x + j * 32, y + i * 32, x, y);
                    enemyList.add(enemy);
                }
            }
        }
    }

    public void addEffect(int x, int y, int efid) {
        effects.add(new EffectController(x, y, gf.getGameMap().x, gf.getGameMap().y, efid));
    }

    //绘制底部地图元素
    public void drawBace(Graphics g) {
        //绘制背景
        bg.draw(id, g,this.isGif,gf.isIfDialog());
        //处理事件
        for (int i = 0; i < eventList.size(); i++) {
            EventController event = eventList.get(i);
            event.getGameMapOrigin(x,y);
            event.getActorRect(gf.actor.getActorRect());
            if (event.isLive()){
                event.draw(event.getX(),event.getY(),g);
                if (eventList.get(i).getType() == 0){
                    //判断角色矩形与事件矩形是否交叉
                    if (gf.actor.getActorRect().intersects(event.getRectangle())&&eventList.get(i).isLive()) {
                        if (event.isAction()){
                            event.eventPlay(gf);
                            event.setAction(false);
                        }
                    }else event.setAction(true);
                }else if (eventList.get(i).getType() == 1){
                    eventList.get(i).eventPlay(gf);
                }
            }
        }
    }
    public void drawGround(Graphics g) {
        //绘制背景
        bg.draw(id, g,this.isGif,gf.isIfDialog());
    }
    //绘制顶部地图元素
    public void drawMedium(Graphics g) {
        //处理怪物
        for (int m = 0; m < enemyList.size(); ++m) {
            EnemyController enemy = enemyList.get(m);
            if (!enemy.isFly()){
                //传参
                enemy.getGameMapOrigin(x,y);
                enemy.enemySatusUpdate(gf);
                enemy.enemyDataCheck(gf);
                enemy.getWallList(wallList, waterList);
                enemy.getActorRect(gf.actor.getActorRect());
                enemy.action();
                enemy.move(gf);
                if (!enemy.isLive()) {
                    enemyList.remove(m);
                    if (enemy.getLoot() == 1){
                        EventController coin = new EventController(0,x,y,0);
                        coin.setLootID(500);
                        coin.setX(x+ enemy.getX()-12);
                        coin.setY(y+ enemy.getY());
                        eventList.add(coin);
                        EventController loot = new EventController(0,x,y,0);
                        loot.setX(x+ enemy.getX()+12);
                        loot.setY(y+ enemy.getY());
                        loot.setLootID(202);
                        eventList.add(loot);
                    }
                    if (enemy.getLoot() == 2){
                        EventController coin = new EventController(0,x,y,0);
                        coin.setLootID(500);
                        coin.setX(x+ enemy.getX()-12);
                        coin.setY(y+ enemy.getY());
                        eventList.add(coin);
                        EventController loot = new EventController(0,x,y,0);
                        loot.setX(x+ enemy.getX()+12);
                        loot.setY(y+ enemy.getY());
                        int lootNum = (int) (Math.random()*100+1);//此处是怪物掉落倍率
                        int lootID;
                        if (lootNum <= 50){//道具
                            lootID = (int) (Math.random()*6+1);
                            loot.setLootID(lootID);
                        }
                        if (lootNum > 50&&lootNum <= 70){//帽子
                            lootID = (int) (Math.random()*5+1);
                            loot.setLootID(lootID+100);
                        }
                        if (lootNum > 70&&lootNum <= 90){//衣服
                            lootID = (int) (Math.random()*5+1);
                            loot.setLootID(lootID+200);
                        }
                        if (lootNum > 90&&lootNum <= 100){//武器
                            lootID = (int) (Math.random()*5+1);
                            loot.setLootID(lootID+300);
                        }
                        eventList.add(loot);
                    }
                }
                //绘制怪物
                enemy.drawenemy(g);
            }
        }
        //处理子弹
        for (int j = 0; j < bullets.size(); j++) {
            BulletController bullet = bullets.get(j);  //获取当前子弹
            if (bullet.getPenetrate() != 2){
                bullet.gameMapPosition(x,y);
                bullet.actorPosition(gf.actor);
                bullet.draw(bullet.getBulletID(), g);  //画子弹
                //与墙碰撞
                for (int m = 0; m < wallList.size(); ++m) {
                    Square wall = wallList.get(m);
                    wall.getGameMapOrigin(x,y);
                    if (bullet.hit(wall.getRectangle())) {
                        if (bullet.getPenetrate() != 2) {
                            bullet.setDeath(true);
                            if (bullets.get(j).isDeath()) {
                                addEffect(bullet.getX(), bullet.getY(), bullet.getEffect());
                                bullets.remove(j);
                            }
                        }
                    }
                }
                //三种伤害类型（0：对怪 1：对人 2：对全部）
                if (bullet.getType() == 0){
                    //与怪碰撞
                    for (int m = 0; m < enemyList.size(); ++m) {
                        EnemyController enemy = enemyList.get(m);
                        if (bullet.hit(enemy.getRectangle())) {
                            if (bullet.getPenetrate() == 0) {
                                bullet.setDeath(true);
                            }
                            enemy.enemyHit(bullet, this);
                            if (bullet.isDeath()) {
                                bullets.remove(j);
                            }
                        }
                    }
                }
                if (bullet.getType() == 1){
                    if (bullet.hit(gf.actor.getActorRect())) {
                        gf.actorData.actorHit(bullet, gf.actor);
                        if (bullet.isDeath()) {
                            bullets.remove(j);
                        }
                    }
                }
                if (bullet.getType() == 2){
                    for (int m = 0; m < enemyList.size(); ++m) {
                        EnemyController enemy = enemyList.get(m);
                        if (bullet.hit(enemy.getRectangle())) {
                            if (bullet.getPenetrate() == 0) {
                                bullet.setDeath(true);
                            }
                            enemy.enemyHit(bullet, this);
                            if (bullet.isDeath()) {
                                bullets.remove(j);
                            }
                        }
                    }
                    if (bullet.hit(gf.actor.getActorRect())) {
                        gf.actorData.actorHit(bullet, gf.actor);
                        if (bullet.isDeath()) {
                            bullets.remove(j);
                        }
                    }
                }
            }
        }
        //绘制怪物信息
        for (int m = 0; m < enemyList.size(); ++m) {
            EnemyController enemy = enemyList.get(m);
            enemy.drawInfo(g);
        }
        //画特效
        for (int i = 0; i < effects.size(); i++) {
            effects.get(i).draw(g,gf.getGameMap());
        }
        //画遮盖光影
        overlays.drawCover(id, x, y, g);
    }
    public void drawTop(Graphics g) {
        //处理怪物
        for (int m = 0; m < enemyList.size(); ++m) {
            EnemyController enemy = enemyList.get(m);
            if (enemy.isFly()) {
                //传参
                enemy.getGameMapOrigin(x,y);
                enemy.enemySatusUpdate(gf);
                enemy.enemyDataCheck(gf);
                enemy.getWallList(wallList, waterList);
                enemy.getActorRect(gf.actor.getActorRect());
                enemy.action();
                enemy.move(gf);
                if (!enemy.isLive()) {
                    enemyList.remove(m);
                    if (enemy.getLoot() == 1){
                        EventController coin = new EventController(0,x,y,0);
                        coin.setLootID(500);
                        coin.setX(x+ enemy.getX()-12);
                        coin.setY(y+ enemy.getY());
                        eventList.add(coin);
                        EventController loot = new EventController(0,x,y,0);
                        loot.setX(x+ enemy.getX()+12);
                        loot.setY(y+ enemy.getY());
                        loot.setLootID(202);
                        eventList.add(loot);
                    }
                    if (enemy.getLoot() == 2){
                        EventController coin = new EventController(0,x,y,0);
                        coin.setLootID(500);
                        coin.setX(x+ enemy.getX()-12);
                        coin.setY(y+ enemy.getY());
                        eventList.add(coin);
                        EventController loot = new EventController(0,x,y,0);
                        loot.setX(x+ enemy.getX()+12);
                        loot.setY(y+ enemy.getY());
                        int lootNum = (int) (Math.random()*100+1);//此处是怪物掉落倍率
                        int lootID;
                        if (lootNum <= 50){//道具
                            lootID = (int) (Math.random()*6+1);
                            loot.setLootID(lootID);
                        }
                        if (lootNum > 50&&lootNum <= 70){//帽子
                            lootID = (int) (Math.random()*5+1);
                            loot.setLootID(lootID+100);
                        }
                        if (lootNum > 70&&lootNum <= 90){//衣服
                            lootID = (int) (Math.random()*5+1);
                            loot.setLootID(lootID+200);
                        }
                        if (lootNum > 90&&lootNum <= 100){//武器
                            lootID = (int) (Math.random()*5+1);
                            loot.setLootID(lootID+300);
                        }
                        eventList.add(loot);
                    }
                }
                //绘制怪物
                enemy.drawenemy(g);
            }
        }
        //处理子弹
        for (int j = 0; j < bullets.size(); j++) {
            BulletController bullet = bullets.get(j);  //获取当前子弹
            if (bullet.getPenetrate() == 2) {
                bullet.gameMapPosition(x,y);
                bullet.actorPosition(gf.actor);
                bullet.draw(bullet.getBulletID(), g);  //画子弹
                //与墙碰撞
                for (int m = 0; m < wallList.size(); ++m) {
                    Square wall = wallList.get(m);
                    wall.getGameMapOrigin(x,y);
                    if (bullet.hit(wall.getRectangle())) {
                        if (bullet.getPenetrate() != 2) {
                            bullet.setDeath(true);
                            if (bullets.get(j).isDeath()) {
                                addEffect(bullet.getX(), bullet.getY(), bullet.getEffect());
                                bullets.remove(j);
                            }
                        }
                    }
                }
                //三种伤害类型（0：对怪 1：对人 2：对全部）
                if (bullet.getType() == 0){
                    //与怪碰撞
                    for (int m = 0; m < enemyList.size(); ++m) {
                        EnemyController enemy = enemyList.get(m);
                        if (bullet.hit(enemy.getRectangle())) {
                            if (bullet.getPenetrate() == 0) {
                                bullet.setDeath(true);
                            }
                            enemy.enemyHit(bullet, this);
                            if (bullet.isDeath()) {
                                bullets.remove(j);
                            }
                        }
                    }
                }
                if (bullet.getType() == 1){
                    if (bullet.hit(gf.actor.getActorRect())) {
                        gf.actorData.actorHit(bullet, gf.actor);
                        if (bullet.isDeath()) {
                            bullets.remove(j);
                        }
                    }
                }
                if (bullet.getType() == 2){
                    for (int m = 0; m < enemyList.size(); ++m) {
                        EnemyController enemy = enemyList.get(m);
                        if (bullet.hit(enemy.getRectangle())) {
                            if (bullet.getPenetrate() == 0) {
                                bullet.setDeath(true);
                            }
                            enemy.enemyHit(bullet, this);
                            if (bullet.isDeath()) {
                                bullets.remove(j);
                            }
                        }
                    }
                    if (bullet.hit(gf.actor.getActorRect())) {
                        gf.actorData.actorHit(bullet, gf.actor);
                        if (bullet.isDeath()) {
                            bullets.remove(j);
                        }
                    }
                }
            }
        }
        //绘制怪物信息
        for (int m = 0; m < enemyList.size(); ++m) {
            EnemyController enemy = enemyList.get(m);
            enemy.drawInfo(g);
        }
        if (gf.isTest()){
            overlays.drawLine(id, x, y, g);
        }

    }
    public String getMapData() {
        String mapData=new String();
        mapData= String.valueOf(getId());
        return mapData;
    }

    public int getId() {
        return id;
    }
}
