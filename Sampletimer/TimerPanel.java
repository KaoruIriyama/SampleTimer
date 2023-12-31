package Sampletimer;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
//タイマーを表示するパネル
public class TimerPanel extends JPanel implements ActionListener{
    private final Map<Integer, Integer> cycles = 
    new HashMap<Integer, Integer>(Map.of(2, 5, 20, 20, 30, 300, 50, 600)) ;
    //活動時間タイマーにセットするキー(単位は分)とリラックスタイマーにセットする値(単位は秒)のマップ
    //key1とvalue1はテスト用
    private Timer timer;//活動時間タイマー
    private int sec;//活動時間タイマーの残り秒数
    private int maxsec;//活動時間タイマーの最大秒数
    
    private Timer relaxtimer;//リラックスタイマー
    private int relaxsec;//リラックスタイマーの残り秒数
    private int maxrelaxsec;//リラックスタイマーの最大秒数
    
    private JLabel textlabel; //タイマーの種類を表示するラベル
    private JLabel timelabel;//タイマーラベル
    
    private JButton startbutton;//開始ボタン
    private JButton stopbutton;//停止ボタン
    
    TimerPanel(int num){
        //タイマーの背景色を設定
        this.setBackground(Color.LIGHT_GRAY);
        // タイマーの秒数を設定
        sec = num * 60; //cyclesのキー値をコンストラクタで60倍してセットする
        maxsec = sec;
        relaxsec = cycles.get(num);
        maxrelaxsec = cycles.get(num);
        // タイマーの種類を表示するラベルの準備
        textlabel = new JLabel();
        textlabel.setFont(new Font("MS Gothic", 1, 20));
        textlabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        textlabel.setAlignmentY(Component.TOP_ALIGNMENT);
        // タイマーを表示するラベルの準備
        timelabel = new JLabel(showSecond(sec));
        timelabel.setFont(new Font("Arial", 1, 40));
        timelabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        timelabel.setAlignmentY(Component.CENTER_ALIGNMENT);
        // 活動時間用タイマーを準備
        timer = new Timer(1000 , this);
        // timer.addActionListener(this); これを入れると actionPerformed()が重複して実行される！
        timer.setActionCommand("settime");
        // リラックス時間用のタイマーを準備
        relaxtimer = new Timer(1000, this);
        relaxtimer.setActionCommand("relax");
        // 開始ボタンと停止ボタンを収めるパネルの準備
        JPanel buttonpanel = new JPanel(new FlowLayout());
        buttonpanel.setOpaque(false);
        // 開始ボタン
        startbutton = new JButton("開始");
        startbutton.addActionListener(this);
        startbutton.setActionCommand("start");
        // 停止ボタン
        stopbutton = new JButton("停止");
        stopbutton.addActionListener(this);//setActionCommand()はこれの後でないと実行されない
        stopbutton.setActionCommand("stop");
        // ボタン2つをボタンパネルに格納
        buttonpanel.add(startbutton);
        buttonpanel.add(stopbutton);
        buttonpanel.setAlignmentY(Component.BOTTOM_ALIGNMENT);
    
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        // ラベル2つとボタンパネルを格納する(レイアウト用にBoxも格納)
        this.add(Box.createRigidArea(new Dimension(10,10)));
        this.add(textlabel);
        this.add(Box.createRigidArea(new Dimension(10,60)));
        this.add(timelabel);
        this.add(Box.createRigidArea(new Dimension(10,60)));
        this.add(buttonpanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        textlabel.setText("残り時間");
        // textlabel.setText("残り時間"  + " " + cmd);//テスト用
        timelabel.setText(showSecond(sec));
        //while文だとsecが減らなかった。
        if(cmd.equals("stop")){
            //停止ボタンが押されたらタイマーを止める
            timer.stop();
            startbutton.setEnabled(true);
            stopbutton.setEnabled(false);
        }else if(cmd.equals("start")){
            //開始ボタンが押されたらタイマーを開始、あるいは再開する
            timer.start();
            startbutton.setEnabled(false);
            stopbutton.setEnabled(true);
        }
        else if(cmd.equals("settime")){
            if(sec > 0){
                startbutton.setEnabled(false);
                stopbutton.setEnabled(true);
                // 0になるまで活動時間タイマーの残り秒数を減らす
                sec--;
            }else{
                //0になったら活動時間タイマーをリセット
                timer.stop();
                sec = maxsec;
                // リラックスタイマーをスタート
                relaxtimer.start();
            }    
        }else if(cmd.equals("relax")){
            //タイマーの背景色を暗くする
            this.setBackground(Color.DARK_GRAY);
            //ラベルの文字を明るくする
            textlabel.setForeground(Color.LIGHT_GRAY);
            timelabel.setForeground(Color.LIGHT_GRAY);
            textlabel.setText("リラックスタイム");
            // textlabel.setText("リラックスタイム" + cmd);//テスト用
            //リラックスタイマーの秒数をセット
            timelabel.setText(showSecond(relaxsec));
            if(relaxsec > 0){
                // 0になるまでリラックスタイマーの残り秒数を減らす
                relaxsec--;
            }else{
                //0になったらリラックスタイマーをリセット
                relaxtimer.stop();
                relaxsec = maxrelaxsec;
                // 背景色と文字の色を戻す
                textlabel.setForeground(Color.BLACK);
                timelabel.setForeground(Color.BLACK);
                this.setBackground(Color.LIGHT_GRAY);
                //活動時間タイマーをスタート
                textlabel.setText("残り時間");
                // textlabel.setText("残り時間" + cmd);//テスト用
                timer.start();                
            }            
        }
    }
    // タイマーの秒数を"00 : 00"の書式で表示させるメソッド
    String showSecond(int sec){
        final String FORMAT = "%02d : %02d";
        return String.format(FORMAT, sec / 60, sec % 60);
    }
}
