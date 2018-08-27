package catchGameFX;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import static javafx.scene.input.KeyCode.DOWN;
import static javafx.scene.input.KeyCode.UP;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;

public class FXMLDocumentController implements Initializable {
    Random rnd = new Random();
    
    private final double MAX = 400;//右端
    private final double MIN = 6;//左端
    private final double STEP = 8;//racketの移動距離
    private final int intrebal = 50;//タイマーの間隔
    private static boolean reverse = false;
    private int random = 1;
    
    private Timer tm;
    private TimerTask tt;
    private  static double c_xpos;
    private  static double c_ypos;
    
    @FXML
    private Label racket;
    @FXML
    private Circle circle;    
    @FXML
    private Label tensu;
    @FXML
    private AnchorPane panel;

    @FXML
    private void handleButtonAction(ActionEvent event) {
        Button btn = (Button)event.getSource();
        String str = btn.getText();
        
        if(str.equals("Start")){
            
            btn.setText("Stop");
            circle.setVisible(true);
            tm = new Timer();
            tt = new MyTimer();
            tm.schedule(tt, 0, intrebal);
            
        }else{
            btn.setText("Start");
            tt.cancel();
            tm.cancel();
        }
        System.out.println(str);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        racketSetup();
    }    
///////////////////////////////////////   
    private void racketSetup(){

        panel.addEventFilter( KeyEvent.KEY_PRESSED , (event) -> {
        double xpos = 0;
        switch(event.getCode()){
            
            case UP:
                break;
                
            case DOWN:
                break;
                
            case RIGHT:
                xpos = racket.getLayoutX();
                if( xpos < MAX){
                    xpos += STEP;
                    racket.setLayoutX( xpos );
                }
                break;
                
            case LEFT:  
                xpos = racket.getLayoutX();
                if( xpos > MIN){
                           xpos -=STEP;   
                        racket.setLayoutX( xpos );
                        }
                break;
            default:
        }
        racket.setText("" + xpos);});
    }
/////////////////////////////////////////////    
class MyTimer extends TimerTask {
     
     private final double SPEED = 8.0;//落下の距離
     private final int KASAN = 100;
     
    MyTimer(){
        c_xpos = circle.getLayoutX();
        c_ypos = circle.getLayoutY();
    }    
    
    @Override     
    public void run(){
    Platform.runLater(() -> {
    
    double circle_ypos = circle.getLayoutY();
    double circle_xpos = circle.getLayoutX();
     
    double racket_ypos = racket.getLayoutY();
    double racket_xpos = racket.getLayoutX();
    
    System.out.println(random);
 
    switch(random){
        case 1: //右側に進む処理
            circle_ypos += SPEED;//Y軸に動かす 
            if(circle_xpos < MAX && circle_xpos > MIN && reverse == false){
            circle_xpos += SPEED;   
        
        }else if (circle_xpos >= MAX || reverse == true) {
            System.out.println("右端");
            reverse = true;//trueになると跳ね返る
            circle_xpos -= SPEED;
        }
        break;
        
        case 2: //左側に進む処理
            circle_ypos += SPEED;//Y軸に動かす  
            if(circle_xpos < MAX && circle_xpos > MIN && reverse == false){
            circle_xpos -= SPEED;      
            
            }else if (circle_xpos <= MIN || reverse == true) {
            System.out.println("左端");
            reverse = true;//trueになると跳ね返る
            circle_xpos += SPEED;
        }
        break;
     }
//  xpos += SPEED;
  
///底までいったらX軸のみランダムに座標を決める
    if (circle_ypos > 520 ) {
        random = rnd.nextInt(2)+1;
        reverse = false;
        circle_ypos = 0;   
        circle_xpos = (double) (Math.random()*MAX)+1;
        System.out.println("=============================");
        System.out.println("リセットしました");
        System.out.println("=============================");
    }

        circle.setLayoutY(circle_ypos);//Y座標をセット
        circle.setLayoutX(circle_xpos);//X座標をセット
        //System.out.println("X = " + racket_xpos);
        //System.out.println("Y = " + racket_ypos);
        System.out.println("X = " + circle_xpos);
        System.out.println("Y = " + circle_ypos);
        System.out.println(reverse);

        if (circle_xpos > racket_xpos && circle_xpos < racket_xpos + 50) {
            if (circle_ypos > racket_ypos) {
                System.out.println("あたり");
                int score = Integer.parseInt(tensu.getText());
                score += KASAN;
                tensu.setText(String.valueOf(score));
                circle_ypos = 0;   
                circle_xpos = (double) (Math.random()*500)+1;
                circle.setLayoutY(circle_ypos);//Y座標をセット
                circle.setLayoutX(circle_xpos);//X座標をセット
                random = rnd.nextInt(2)+1;
                reverse = false;
            }   
        }
    });    
    }
}
}
