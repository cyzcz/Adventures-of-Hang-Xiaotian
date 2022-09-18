package Service;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import Frame.*;
import Dao.*;
import View.*;

/**
 * 数据
 *
 * @author Admin
 * @date 2021/12/11
 */
public class DataController extends Data {

    private ArmController arm;//
    /**
     * 武器缓存
     */
    private ArmController armRepeater = new ArmController(0);
    /**
     * 武器集合
     */
    public ArrayList<ArmController> arms = new ArrayList<>();
    /**
     * 物品
     */
    //生成物品容器
    private ItemController item;//角色物品
    /**
     * 物品缓存
     */
    private ItemController itemRepeater = new ItemController(0, 0, 0);

    /**
     * 物品集合
     */
    public ArrayList<ItemController> items = new ArrayList<>();
    /**
     * 子弹
     */
    //生成子弹容器
    public ArrayList<BulletController> bullets = new ArrayList<>();
    /**
     * 帽子列表
     */
    //构造函数初始化人物道具
    public DataController() {
        //初始装备道具
        money = 500;
        hat = 1;
        equip = 1;
        for (int i = 0; i < 1; i++) {
            arm = new ArmController(i);
            arms.add(arm);//给人物添加武器
        }
        for (int i = 0; i < 3; i++) {
            item = new ItemController(i, 0, 5);
            items.add(item);//给人物添加物品
        }
    }

    //获取金钱
    public int getMoney() {
        return money;
    }
    //增加money

    public void setArms(String ArmsId) {
        String[] arr = ArmsId.split(" ");
        arms.get(0).setId(Integer.parseInt(arr[0]));
//        arms.get(1).setId(Integer.parseInt(arr[1]));
//        arms.get(2).setId(Integer.parseInt(arr[2]));
    }
    public void setItems(String ItemsId) {
        String[] arr = ItemsId.split(" ");
        items.get(0).setId(Integer.parseInt(arr[0]));
        items.get(1).setId(Integer.parseInt(arr[1]));
        items.get(2).setId(Integer.parseInt(arr[2]));
    }

    public void setArm(int i) {
        this.arm.setId(i);
    }

    public void setBullets(ArrayList<BulletController> bullets) {
        this.bullets = bullets;
    }

    public void adjustMoney(int a){
        money += a;
    }
    public void checkExp(){
        if(levelList.get(level).getExpNeed()<=this.exp)
        {
            exp-=levelList.get(level).getExpNeed();
            level=level+1;
            atk+=levelList.get(level).getATKUp();
            def+=levelList.get(level).getDFKUp();
            actorHPF +=levelList.get(level).getHPUp();
            actorHPLimit+=levelList.get(level).getHPUp();
            actorSP+=levelList.get(level).getSPUp();
            actorSPLimit+=levelList.get(level).getSPUp();
            System.out.println("你升级啦");
        }

    }

    public void adjustExp(float a){
        exp += a;
        checkExp();
    }
    //检查角色HP
    public void updateHP() {
        if ((actorHPF+actorHPRecover) < (actorHPLimit+getHPplus())){
            actorHPF += actorHPRecover;
        }else actorHPF = actorHPLimit+getHPplus();
        if (actorHPT < actorHPF) actorHPT += (actorHPLimit+getHPplus())*actorHPResume/100;
        if (actorHPT > actorHPF) actorHPT -= (actorHPLimit+getHPplus())*actorHPResume/100;
        if (actorHPF < 0) actorHPF = 0;
        if (actorHPLimit < 1) actorHPLimit = 1;
        if ((actorHPLimit+getHPplus()) < actorHPF) actorHPF = actorHPLimit+getHPplus();
        if ((actorHPLimit+getHPplus()) < actorHPT) actorHPT = actorHPLimit+getHPplus();
    }

    //检查角色SP
    public void updateSP() {
        if (checkSP) {
            if ((actorSPLimit+getSPplus()) > actorSP) actorSP += actorSPRecover;
        }
        if ((actorSPLimit+getSPplus()) < actorSP) actorSP = actorSPLimit+getSPplus();
        if (actorSPLimit < 1) actorSPLimit = 1;
        if (actorSP < 0) actorSP = 0;
    }


    //更新状态
    public void SatsUpdate(ActorController actor) {
        //hp恢复
        if (status[0] == 1) {
            actorHPRecover = (float) 0.15;
            statusTime[0]--;
            if (statusTime[0] <= 0) {
                actorHPRecover = (float) 0;
                status[0] = 0;
            }
        }
        //sp恢复
        if (status[1] == 1) {
            actorSPRecover = (float) 0.15;
            statusTime[1]--;
            if (statusTime[1] <= 0) {
                actorSPRecover = (float) 0;
                status[1] = 0;
            }
        }
        //速度提升
        if (status[2] == 1) {
            actor.adjustSpeed(2);
            statusTime[2]--;
            if (statusTime[2] <= 0) {
                actor.adjustSpeed(-2);
                status[2] = 0;
            }
        }
        //攻击力提升
        if (status[3] == 1) {
            atk = (float) 1.25*atk;
            statusTime[3]--;
            if (statusTime[3] <= 0) {
                atk = (float) 0.8*atk;
                status[3] = 0;
            }
        }
        //防御力提升
        if (status[4] == 1) {
            def = (float) 0.2*(1-def)+def;
            statusTime[4]--;
            if (statusTime[4] <= 0) {
                def = (float)((def-0.2)*1.25);
                }
                status[4] = 0;
            }
        //燃烧
        if (status[5] == 1) {
            if (actorHPF >= actorHPF * 0.002) {
                actorHPF -= actorHPF * 0.002;
            } else {
                actorHPF = 0;
            }
            statusTime[5]--;
            if (statusTime[5] <= 0) {
                status[5] = 0;
            }
        }
        //中毒
        if (status[6] == 1) {
            if (actorHPF >= (actorHPLimit - actorHPF) * 0.002) {
                actorHPF -= (actorHPLimit - actorHPF) * 0.002;
            } else {
                actorHPF = 0;
            }
            statusTime[6]--;
            if (statusTime[6] <= 0) {
                status[6] = 0;
            }
        }
    }
    //角色HP控制
    public void hpControl(int h) {
        if (h > 0){
            if ((actorHPF+h)>=(actorHPLimit+getHPplus())){
                actorHPF = actorHPLimit+getHPplus();
            }else actorHPF += h;
        }else {
            if ((actorHPF-h)<=0){
                actorHPF = 0;
            }else actorHPF -= h;
        }
    }
    //角色SP控制
    public void spControl(int s) {
        if (s > 0){
            if ((actorSP+s)>=(actorSPLimit+getSPplus())){
                actorSP = actorSPLimit+getSPplus();
            }else actorSP += s;
        }else {
            if ((actorSP+s)<=0){
                actorSP = 0;
            }else actorSP += s;
        }
    }
    //角色受伤
    public void actorHit(BulletController b, ActorController actor) {
        if (bullets.contains(b)){
            return;
        }
        bullets.add(b);
        if (b.getStatus() == 1){
            this.status[5] = 1;
            this.statusTime[0] = 500;
        }
        if (b.getStatus() == 2){
            this.status[6] = 1;
            this.statusTime[1] = 500;
        }
        if (b.getAtk()*(1-getDEF()) > (actorHPF)) {
            actorHPF  = 0;
        } else actorHPF -= b.getAtk()*(1-getDEF());
        actor.addEffect(b.getEffect());
        if (b.getPenetrate() == 0){
            b.setDeath(true);
        }
    }
    //角色武器相关
    public int getArm(int i) {
        return arms.get(i).getId();//获取已装备的首位武器ID
    }
    //获取武器CD
    public int getATKCD(int i) {
        return (int) arms.get(i).getCd();
    }
    //设置武器CD
    public void setATKCD(int i) {
        arms.get(i).setCd(0);
        arms.get(i).setCdLimit(getATKCD(i));
    }
    //刷新武器CD
    public void updateATKCD() {
        for (int i = 0; i < arms.size(); i++) {
            if (arms.get(i).getCdLimit() > arms.get(i).getCd()){
                arms.get(i).adjustCd(1);
            }
        }
    }
    //检查武器CD
    public boolean checkATKCD(int i) {
        if (arms.get(i).getCdLimit() == arms.get(i).getCd()){
            return true;
        }return false;
    }
    //获取子弹ID
    public int getBullet(int i) {
        return arms.get(i).getBulletID();
    }
    //切换武器
    public void changeArm() {
        if (!arms.isEmpty()){
            armRepeater = arms.get(0);
            for (int i = 0; i < arms.size() - 1; i++) {
                arms.set(i, arms.get(i + 1));
            }
            arms.set(arms.size() - 1, armRepeater);
        }
    }
    //获得武器
    public void getArm(ArmController arm) {
        if (arms.size() == 1||arms.size() == 2){
            arms.add(0,arm);
        }else {
            arms.set(0,arm);
        }
    }

    public boolean getUseful(int i) {
        if (items.get(i).getType() == 0) {
            return true;
        }
        return false;
    }
    //道具相关
    public void useItem(int i) {
        if (!items.isEmpty() && getUseful(i)) {
            items.get(i).useEffect(items.get(i).getId(),this);
            items.get(i).adjustNumber(-1);
            if (items.get(i).getNumber() == 0) {
                items.remove(i);
                items.trimToSize();
            }
        }
    }

    //切换物品
    public void changeItem() {
        if (!items.isEmpty()){
            itemRepeater = items.get(0);
            for (int i = 0; i < items.size() - 1; i++) {
                items.set(i, items.get(i + 1));
            }
            items.set(items.size() - 1, itemRepeater);
        }
    }
    //获得物品
    public void getItem(ItemController item, int num) {
        int sum = 0;
        if (!items.isEmpty()){
            for (int j = 0; j < items.size(); j++) {
                if (item.getId() == items.get(j).getId()){
                    items.get(j).adjustNumber(num);
                    sum++;
                }
            }
            if (sum==0){
                items.add(item);
            }
        }else {
            items.add(item);
            items.get(0).adjustNumber(num-1);
        }
    }
    //更新数据
    public void update() {
        updateHP();
        updateSP();
        updateATKCD();
    }

    public Arm getArm() {
        return arm;
    }

    public String getArms() {
        String ArmsId=String.valueOf(arms.get(0).getId());
        return ArmsId;
    }

    public String getItems() {
        String ItemsId=(items.get(0).getId())+" "+items.get(1).getId()+" "+items.get(2).getId();
        return ItemsId;
    }
    public ArrayList<BulletController> getBullets() {
        return bullets;
    }
}
