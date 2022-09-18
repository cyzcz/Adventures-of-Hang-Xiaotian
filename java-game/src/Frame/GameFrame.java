package Frame;

import Dao.Dialog;
import Service.ActorController;
import Service.DataController;

import Service.EnemyController;
import Service.GameMap;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;

import Frame.*;
import Dao.*;
import View.*;
import View.Menu;

public class GameFrame extends JFrame {
    private int gifId=1;
    private int mapChange=1;
    private boolean ifGif=false;
    private boolean answer=false;
    private int step=0;
    static final int WIDTH = 1280;
    static final int HEIGHT = 720;
    boolean test = false;
    private boolean win=false;
    boolean show=false;

    //黑场过渡
    private int blacky1 = -1080;
    private int blacky2 = HEIGHT;
    //切换地图变量
    public int changing = 0;
    private int toId,toX,toY;
    //生成角色数据
    public DataController actorData = new DataController();
    //生成角色
    public ActorController actor = new ActorController(this);
    //生成地图
    private GameMap gameMap = new GameMap(this);
    //生成菜单
    private Menu menu = new Menu(actorData);
    //生成UI
    private UI ui = new UI(this);
    //游戏窗口
    private boolean reload=false;
    private String key=null;
    private boolean ifDialog=false;

    public DataController getActorData() {
        return actorData;
    }

    public ActorController getActor() {
        return actor;
    }

    public void setTest(boolean test) {
        this.test = test;
    }

    public boolean isIfDialog() {
        return ifDialog;
    }

    public boolean isWin() {
        return win;
    }

    public void setWin(boolean win) {
        this.win = win;
    }
    public boolean isAnswer() {
        return answer;
    }

    public void setAnswer(boolean answer) {
        this.answer = answer;
    }

    public boolean isReload() {
        return reload;
    }

    public void setReload(boolean reload) {
        this.reload = reload;
    }

    public boolean isIfGif() {
        return ifGif;
    }

    public void setIfGif(boolean ifGif) {
        this.ifGif = ifGif;
    }

    public void setIfDialog(boolean ifDialog) {
        this.ifDialog = ifDialog;
    }

    public String getKey() {
        return key;
    }

    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public GameMap getGameMap() {
        return gameMap;
    }

    public Menu getMenu() {
        return menu;
    }

    public UI getUi() {
        return ui;
    }

    public boolean isTest() {
        return test;
    }

    public int getGifId() {
        return gifId;
    }

    public void setGifId(int gifId) {
        this.gifId = gifId;
    }
    public GameFrame(boolean isReload) throws Exception {
        //游戏分辨率
        this.setSize(WIDTH, HEIGHT);
//        this.setSize(1920, 1080);
        //游戏名称
        this.setTitle("航小天历险记");
        //更改窗口图标
        ImageIcon icon = new ImageIcon("src/image/Item/arm/arm5.png");
        this.setReload(isReload);
        this.setIconImage(icon.getImage());
        //无边框模式
//        this.setUndecorated(true);
        //居中显示窗口
        this.setLocationRelativeTo(null);
        //设置窗口不可变
        this.setResizable(false);
        //设置窗口关闭
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //设置窗口可见
        this.setVisible(true);
        //创造空气墙
        if(isReload()) {
            ArrayList<String> Tempter = this.readTxt("save.dat");
            //System.out.println(Tempter);
            actorData.setEquip(Integer.parseInt(Tempter.get(0)));
            actorData.setMoney(Integer.parseInt(Tempter.get(1)));
            actorData.setHat(Integer.parseInt(Tempter.get(2)));
            actorData.setActorHPF(Integer.parseInt(Tempter.get(3)));
            actorData.setActorHPLimit(Integer.parseInt(Tempter.get(4)));
            actorData.setActorSP(Integer.parseInt(Tempter.get(5)));
            actorData.setActorSPLimit(Integer.parseInt(Tempter.get(6)));
            actorData.setArm(Integer.parseInt(Tempter.get(7)));
            actorData.setArms(Tempter.get(8));
            actorData.setItems(Tempter.get(9));
            gameMap.initAirWall(Tempter.get(10));
            this.toX=Integer.parseInt(Tempter.get(11));
            this.toY=Integer.parseInt(Tempter.get(12));
            gameMap.createSquare();
            mapMove();
        }
        else{//角色线程开始
            gameMap.createSquare();
            this.ChangeMap(0,20*32,30*32,3);
        }
        actor.start();
        //窗口线程
        Runnable gameThread = () -> {
            while (true) {
                this.repaint();
                try {
                   // System.out.println( "ATK:"+actorData.getAtk());
                    Thread.sleep(15);
                    //对话检测
                    for(EnemyController enemy:gameMap.getEnemyList())
                    {
                        if(enemy.isVisible())
                        {
                            repaint();
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        Thread mainlined = new Thread(gameThread);
        mainlined.start();
    }

    //地图卷动  卷动到极限返回true
    public void gameMapScroll(int x, int y){
        if (x>0){
            if (gameMap.x+x<0){
                gameMap.x += x;
                gameMap.bg.x += x;
            }else {
                gameMap.x = 0;
                gameMap.bg.x = 0;
            }
        }else {
            if (gameMap.x+x > -(gameMap.airWall[0].length * 32 - WIDTH)) {
                gameMap.x += x;
                gameMap.bg.x += x;
            }else {
                gameMap.x = -(gameMap.airWall[0].length * 32 - WIDTH);
                gameMap.bg.x = -(gameMap.airWall[0].length * 32 - WIDTH);
            }
        }
        if (y>0){
            if (gameMap.y+y<0){
                gameMap.y += y;
                gameMap.bg.y += y;
            }else {
                gameMap.y = 0;
                gameMap.bg.y = 0;
            }
        }else {
            if (gameMap.y+y >= -(gameMap.airWall.length * 32 - HEIGHT)) {
                gameMap.y += y;
                gameMap.bg.y += y;
            }else {
                gameMap.y = -(gameMap.airWall.length * 32 - HEIGHT);
                gameMap.bg.y = -(gameMap.airWall.length * 32 - HEIGHT);
            }
        }
    }

    public boolean checkGameMapScroll(int x, int y){
        if (x>0){
            if (gameMap.x+x<0){
                gameMap.x += x;
                gameMap.bg.x += x;
            }else {
                gameMap.x = 0;
                gameMap.bg.x = 0;
            }
        }else {
            if (gameMap.x+x >= -(gameMap.airWall[0].length * 32 - WIDTH)) {
                gameMap.x += x;
                gameMap.bg.x += x;
            }else {
                gameMap.x = -(gameMap.airWall[0].length * 32 - WIDTH);
                gameMap.bg.x = -(gameMap.airWall[0].length * 32 - WIDTH);
                return true;
            }
        }
        if (y>0){
            if (gameMap.y+y<0){
                gameMap.y += y;
                gameMap.bg.y += y;
            }else {
                gameMap.y = 0;
                gameMap.bg.y = 0;
            }
        }else {
            if (gameMap.y+y >= -(gameMap.airWall.length * 32 - HEIGHT)) {
                gameMap.y += y;
                gameMap.bg.y += y;
            }else {
                gameMap.y = -(gameMap.airWall.length * 32 - HEIGHT);
                gameMap.bg.y = -(gameMap.airWall.length * 32 - HEIGHT);
                return true;
            }
        }return false;
    }

    //切换地图
    public void ChangeMap(int id,int x, int y,int chang){
        toId = id;
        toX = x;
        toY = y;
        changing=chang;
        actor.actorStop();
        actor.setActorLined(false);
        actorData.bullets.clear();//清理子弹容器
    }

    //刷新处理
    public void paint(Graphics g) {
        //双缓存
        if(Record.isSave())
        {
            Record.setSave(false);
            this.save("save.dat");
            JOptionPane.showMessageDialog(this, "恭喜你保存成功", "保存成功",JOptionPane.WARNING_MESSAGE);
        }
        BufferedImage bufferedImage = (BufferedImage) this.createImage(this.getSize().width, this.getSize().height);
        Graphics gp = bufferedImage.getGraphics();
        //角色死亡判定
        if ((int)actorData.getActorHPT() == 0){
            actorData.setLive(false);//角色不存活
            actorData.setDeath(true);//角色死亡
            actor.actorStop();//角色立定
            actor.setActorLined(false);//角色固定
            changing = 1;//进入场景改变阶段一
        }
        //角色数据刷新
        if (actorData.isLive()){
            actorData.update();
            actorData.SatsUpdate(actor);
        }
        //绘制底部地图（空气墙，事件，怪物）
            if (isIfDialog()) {
                gameMap.drawGround(gp);
            }
            //绘制角色
            if (!isIfDialog()) {
                gameMap.drawBace(gp);
                if (actorData.isLive()) {
                    gp.drawImage(actor.getImg(), actor.getX(), actor.getY(), actor.getWidth(), actor.getHeight(), this);
                } else
                    gp.drawImage((new ImageIcon("src/image/Characters/death.png")).getImage(), actor.getX() - 25, actor.getY() - 12, this);
                actor.drawEffect(gp);
                //绘制顶部地图（子弹，特效）
                gameMap.drawMedium(gp);
                gameMap.drawTop(gp);
                //test/////////////////////////////////////////////
                if (test) {
                    for (int i = 0; i < gameMap.getWallList().size(); i++) {
                        gameMap.getWallList().get(i).getGameMapOrigin(gameMap.x, gameMap.y);
                        gameMap.getWallList().get(i).drawX(gp);
                    }
                    for (int i = 0; i < gameMap.getWaterList().size(); i++) {
                        gameMap.getWaterList().get(i).getGameMapOrigin(gameMap.x, gameMap.y);
                        gameMap.getWaterList().get(i).drawY(gp);
                    }
                    //System.out.println((gameMap.x)+"/"+(gameMap.y));
                    System.out.println("真实位置：" + (gameMap.x + actor.getX()) + "/" + (actor.getY() - gameMap.y));
                }
//        //绘制角色
//        if (actorData.isLive()){
//            gp.drawImage(actor.getImg(), actor.getX(), actor.getY(), actor.getWidth(), actor.getHeight(), this);
//        }else gp.drawImage((new ImageIcon("src/image/Characters/death.png")).getImage(), actor.getX()-25, actor.getY()-12, this);
                //画UI
                if (!test && ui.isVisible()) {
                    ui.draw(gp);
                }
                //画菜单
                if (menu.isVisible()) {
                    menu.draw(gp);
                }
            }
            //场景切换阶段一（落幕 ）
            if (changing == 1) {
                gp.drawImage((new ImageIcon("src/image/UI/black.png")).getImage(), 0, blacky1, this);
                gp.drawImage((new ImageIcon("src/image/UI/black.png")).getImage(), 0, blacky2, this);
                if (!(blacky1 >= 200 && blacky2 <= -(1080 - HEIGHT + 200))) {
                    blacky1 += 15;
                    blacky2 -= 15;
                } else if (actorData.isLive()) {//判断是 切换地图 还是 死亡界面
                    gameMap.id = toId;
                    try {
                        gameMap.createSquare();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //X方向
                    if (toX > WIDTH / 2 - 180 && toX < WIDTH / 2 + 180) {//是否在安全区域
                        actor.setX(toX);
                    } else if (toX < WIDTH / 2 - 180) {//地图无需卷动且人物在安全区内
                        actor.setX(toX);
                    } else if (toX > WIDTH / 2 + 180) {//地图需要卷动且人物不在安全区内
                        if (checkGameMapScroll(-(toX - (WIDTH / 2 + 180)), 0)) {//先将地图卷动，如果地图卷动到极限
                            gameMapScroll(-(toX - (WIDTH / 2 + 180)), 0);
                            if (toX >= gameMap.airWall[0].length * 32) {//地图无法卷动且人物仍然在画面以下
                                actor.setX(WIDTH - 32);
                            } else {
                                actor.setX(toX - (gameMap.airWall[0].length * 32 - WIDTH));
                            }
                        } else {//先将地图卷动，如果地图没有卷动到极限
                            actor.setX(WIDTH / 2 + 180);
                        }
                    }
                    //Y方向
                    if (toY > HEIGHT / 2 - 180 && toY < HEIGHT / 2 + 180) {//是否在安全区域
                        actor.setY(toY);
                    } else if (toY < HEIGHT / 2 - 180) {//地图无需卷动且人物在安全区内
                        actor.setY(toY);
                    } else if (toY > HEIGHT / 2 + 180) {//地图需要卷动且人物不在安全区内
                        if (checkGameMapScroll(0, -(toY - (HEIGHT / 2 + 180)))) {//先将地图卷动，判断地图卷动到极限
                            gameMapScroll(0, -(toY - (HEIGHT / 2 + 180)));
                            if (toY >= gameMap.airWall.length * 32) {//地图无法卷动且人物仍然在画面以下
                                actor.setY(HEIGHT - 32);
                            } else {
                                actor.setY(toY - (gameMap.airWall.length * 32 - HEIGHT));
                            }
                        } else {//先将地图卷动，如果地图没有卷动到极限
                            actor.setY(HEIGHT / 2 + 180);
                        }
                    }
                    changing = 2;
                } else if (actorData.isDeath()) {
                    gp.drawImage((new ImageIcon("src/image/UI/death_txt.png")).getImage(), 0, 0, this);
                }
            }
            //场景切换阶段二（启幕 释放角色）
            if (changing == 2) {
                gp.drawImage((new ImageIcon("src/image/UI/black.png")).getImage(), 0, blacky1, this);
                gp.drawImage((new ImageIcon("src/image/UI/black.png")).getImage(), 0, blacky2, this);
                if (!(blacky1 >= HEIGHT && blacky2 <= -1080)) {
                    blacky1 += 15;
                    blacky2 -= 15;
                } else {
                    blacky1 = -1080;
                    blacky2 = HEIGHT;
                    changing = 0;
                    if (actorData.isDeath()) {//复活 播放特效
                        actorData.setDeath(false);
                        actor.addEffect(12);
                    }
                    actor.setActorLined(true);
                }
                //此处重置gif中的东西
                this.setSize(WIDTH, HEIGHT);
                ui.setVisible(true);
            }
            if (changing == 3 && !ifGif) {
                ui.setVisible(false);
                this.setSize(600, 600);
                ifGif = true;
            }
            if (changing == 3) {
                gp.drawImage((new ImageIcon("src/image/Gif/" + gifId + ".gif")).getImage(), 0, blacky1, this);
                Font nameFont = new Font("微软雅黑", Font.PLAIN, 28);
                g.setFont(nameFont);

                step++;
            }
            if (changing == 3) {
                if (step == 720) {
                    step = 0;
                    ifGif = false;
                    this.ChangeMap(mapChange, 15 * 32, 57 * 32, 1);

                }
            }
            g.drawImage(bufferedImage, 3, 26, null);

            if (isIfDialog()) {
                if (isAnswer()) {
                    Image image = new ImageIcon("src/image/dialog.png").getImage();
                    Font nameFont = new Font("微软雅黑", Font.PLAIN, 28);
                    g.drawImage(image, 100, 463, null);
                    g.setFont(nameFont);
                    g.setColor(Color.BLACK);
                    if (key.equals(Dialog.getResult(gameMap.id - 1))) {
                        g.drawString(Dialog.getTrue(gameMap.id - 1), 110, 498);
                        actorData.hpControl(9999);
                        actorData.spControl(9999);
                        actor.addEffect(12);
                        actorData.adjustExp(500);
                        actorData.adjustMoney(100);
                    } else {
                        g.drawString(Dialog.getRFalse(gameMap.id - 1), 110, 498);
                    }
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    key = null;
                    setIfDialog(false);
                    setAnswer(false);
                }
                if (!isAnswer()) {
                    actor.setState();
                    Image image = new ImageIcon("src/image/dialog.png").getImage();
                    Font nameFont = new Font("微软雅黑", Font.PLAIN, 28);
                    g.drawImage(image, 100, 463, null);
                    g.setFont(nameFont);
                    g.setColor(Color.BLACK);
                    String[] dialogs = Dialog.getDialog(gameMap.id - 1, 0);
                    for (int j = 0; j < dialogs.length; j++) {
                        g.drawString(dialogs[j], 110, 498 + j * 60);
                    }
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (key != null) {
                        setAnswer(true);
                    }
                }
            }
        if(isWin())
        {
            this.setWin(false);
            this.dispose();
            EndFrame ef=new EndFrame();
            ef.setVisible(true);
        }
    }

    private static BufferedReader stdIn =
            new BufferedReader(new InputStreamReader(System.in));
    private static PrintWriter stdOut =
            new PrintWriter(System.out, true);
    private static PrintWriter stdErr =
            new PrintWriter(System.err, true);
    private String ActorInformation;



    public String ActorRemark() {
        ActorInformation = new String();

        ActorInformation += getActorData().getEquip() + "\n"
                +getActorData().getMoney()+"\n"
                +getActorData().getHat()+"\n"
                +(int)getActorData().getActorHPF()+"\n"
                +(int)(getActorData().getActorHPLimit()-getActorData().getHPplus())+"\n"
                +(int)getActorData().getActorSP()+"\n"
                +(int) (getActorData().getActorSPLimit()-getActorData().getSPplus())+"\n"
                +getActorData().getArm().getId()+"\n"
                +getActorData().getArms()+"\n"
                +getActorData().getItems()+"\n"
                +gameMap.getMapData()+"\n"+this.toX+"\n"+this.toY;
        return ActorInformation;
    }

    public void save(String fileName) {

        if (fileName.equals("")) {
            stdErr.println("请输入文件名！");
        }
        if (ActorRemark() == null) {
            stdErr.println("没有数据！保存失败");
        }
        if (ActorRemark().equals("")) {
            stdErr.println("输入信息为空！保存失败");
        } else {
            try {
                FileWriter fileWriter = new FileWriter(fileName);
                fileWriter.write(ActorRemark());
                fileWriter.flush();
                fileWriter.close();
                System.out.println("save to file" + fileName + " successfully");
            } catch (IOException e) {
                System.out.println("failed to save");
            }
        }

    }

    public ArrayList<String> readTxt(String fileName) {
        File file = new File(fileName);

        BufferedReader reader = null;
        ArrayList<String> tempStr = new ArrayList<>();

        String line;
        try {
            reader = new BufferedReader(new FileReader(file));


            while ((line=reader.readLine()) != null) {
                tempStr.add(line);
            }
            reader.close();
//            for (String i : tempStr) {
//                System.out.println(i);
//            }


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return tempStr;
    }
    public void mapMove()
    {

        //X方向
        if (toX>WIDTH/2-180&&toX<WIDTH/2+180){//是否在安全区域
            actor.setX(toX);
        }else if (toX<WIDTH/2-180){//地图无需卷动且人物在安全区内
            actor.setX(toX);
        }else if (toX>WIDTH/2+180){//地图需要卷动且人物不在安全区内
            if (checkGameMapScroll(-(toX-(WIDTH/2+180)),0)){//先将地图卷动，如果地图卷动到极限
                gameMapScroll(-(toX-(WIDTH/2+180)),0);
                if (toX >= gameMap.airWall[0].length*32){//地图无法卷动且人物仍然在画面以下
                    actor.setX(WIDTH-32);
                }else {
                    actor.setX(toX-(gameMap.airWall[0].length*32-WIDTH));
                }
            }else {//先将地图卷动，如果地图没有卷动到极限
                actor.setX(WIDTH/2+180);
            }
        }
        //Y方向
        if (toY>HEIGHT/2-180&&toY<HEIGHT/2+180){//是否在安全区域
            actor.setY(toY);
        }else if (toY<HEIGHT/2-180){//地图无需卷动且人物在安全区内
            actor.setY(toY);
        }else if (toY>HEIGHT/2+180){//地图需要卷动且人物不在安全区内
            if (checkGameMapScroll(0,-(toY-(HEIGHT/2+180)))){//先将地图卷动，判断地图卷动到极限
                gameMapScroll(0,-(toY-(HEIGHT/2+180)));
                if (toY >= gameMap.airWall.length*32){//地图无法卷动且人物仍然在画面以下
                    actor.setY(HEIGHT-32);
                }else {
                    actor.setY(toY-(gameMap.airWall.length*32-HEIGHT));
                }
            }else {//先将地图卷动，如果地图没有卷动到极限
                actor.setY(HEIGHT/2+180);
            }
        }

    }
}
