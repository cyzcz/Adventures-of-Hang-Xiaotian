package Dao;

import java.util.ArrayList;
import java.util.Arrays;

public class Dialog {
    private static String[][][] dialogs=
            {
                    //人物1的对话 每一个元素代表一个窗口
                    {
                        {"根据人脸识别，你就是航小天同学。现在邀请你完成测试","1.你知道这是什么尊吗？","A.金尊 B.银尊 C.何尊 D.四羊方尊"},
                        {"很遗憾，回答错误，正确答案是C"},
                        {"恭喜你，回答正确。"},
                        {"C"}
                    },
                    {
                            {"接着闯关吧！同学","西北工业大学于_____首批获准试办国家示范性软件学院？","A.2001年6月 B. 2002年6月 C.2001年12月 D. 2002年12月"},
                            {"很遗憾，回答错误，正确答案是2001年12月~"},
                            {"不愧是聪明的你呢，答对了！"},
                            {"C"}
                    },
                    {
                            {"最后一道题了，加油！","西北工业大学于_____成为国家“985工程”重点建设高校?","A. 2001年6月 B. 2001年4月 C.2002年6月 D. 2002年4月"},
                            {"很遗憾，回答错误，正确答案是2001年4月~，下次一定要答对哦"},
                            {"你真是太聪明了！连这都知道!\n不愧是软件学院的学生呢！"},
                            {"B"}
                    }
            };
    public static String[] getDialog(int id,int number)
    {
        return dialogs[id][number];
    }
    public static String getResult(int id)
    {
        return dialogs[id][dialogs[id].length-1][0];
    }
    public static String getRFalse(int id)
    {
        return dialogs[id][dialogs[id].length-3][0];
    }
    public static String getTrue(int id)
    {
        return dialogs[id][dialogs[id].length-2][0];
    }
    public static int getWindows(int id)
    {
        return dialogs.length;
    }
    public static void main(String[] args)
    {
        System.out.println( getWindows(0));
    }
}
