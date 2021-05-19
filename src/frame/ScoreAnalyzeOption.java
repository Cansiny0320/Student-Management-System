package frame;

import javax.swing.*;

public class ScoreAnalyzeOption extends JDialog {

    /**
     * @param owner 它的父窗口
     * @param title 窗口名
     * @param modal 对话框操作模式，当modal为true时，代表用户必须结束对话框才能回到原来所属的窗口。当modal为 false时，代表对话框与所属窗口可以互相切换，彼此之间在操作上没有顺序性。
     */
    public ScoreAnalyzeOption(JFrame owner, String title, boolean modal) {
        super(owner, title, modal);
    }
}

