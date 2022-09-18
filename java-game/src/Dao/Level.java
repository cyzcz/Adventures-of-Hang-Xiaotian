package Dao;

public class Level {
    int Level;
    String levelName;
    float ATKUp;
    float DFKUp;
    float HPUp;
    float SPUp;
    float expNeed;


    public int getLevel() {
        return Level;
    }

    public String getLevelName() {
        return levelName;
    }

    public float getATKUp() {
        return ATKUp;
    }

    public float getDFKUp() {
        return DFKUp;
    }

    public float getHPUp() {
        return HPUp;
    }

    public float getSPUp() {
        return SPUp;
    }

    public float getExpNeed() {
        return expNeed;
    }

    public Level(int level, String levelName, float ATKUp, float DFKUp, float HPUp, float SPUp, float expNeed) {
        Level = level;
        this.levelName = levelName;
        this.ATKUp = ATKUp;
        this.DFKUp = DFKUp;
        this.HPUp = HPUp;
        this.SPUp = SPUp;
        this.expNeed = expNeed;
    }
}
