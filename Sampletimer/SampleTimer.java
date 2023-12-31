package Sampletimer;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
//JFrameを継承したメインクラス
public class SampleTimer extends JFrame{
    // private final Integer[] cycles = {2, 20, 30, 50};//テスト用
    private final Integer[] cycles = {20, 30, 50};//スタートダイアログの選択肢になるタイマー周期(単位は分)
    
    public static void main(String[] args){
        SampleTimer spt = new SampleTimer();
        spt.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        spt.setBounds(10, 10, 300, 300);
        spt.setTitle("サンプルタイマー");
        spt.setVisible(true);
    }
    SampleTimer(){
        //タイマー周期を選択するダイアログの準備
        int num = JOptionPane.showOptionDialog(
         this,
         "タイマー周期を選択してください(分)", 
         "サンプルタイマー", 
         JOptionPane.YES_NO_OPTION,
         JOptionPane.QUESTION_MESSAGE,
         null, 
        cycles, //ここの引数にはオブジェクトの配列しか許されない
        cycles[0]);
        //選択された値を渡してタイマーパネルの準備
        TimerPanel timerPanel = new TimerPanel(cycles[num]);
        this.add(timerPanel);
    }
}
    
