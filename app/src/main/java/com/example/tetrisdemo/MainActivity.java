package com.example.tetrisdemo;

import android.content.res.Resources;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.support.v7.widget.GridLayout;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private LogicMatrix logicMatrix = new LogicMatrix(17,12);
    final Handler handler = new Handler();

    boolean running = true;
    boolean stop = false;

    public void leftActivy(View view){
        if(!running) {
            stop = true;
            logicMatrix.moveLeft();
            stop = false;
        }
        Log.i("log","Left.");
    }

    public void rightActivity(View view){
        if(!running) {
            stop = true;
            logicMatrix.moveRight();
            stop = false;
        }
        Log.i("log","Right.");
    }

    public void rotateLeft(View view){
        if(!running) {
            stop = true;
            logicMatrix.rotate(0);
            stop = false;
        }
        Log.i("log","Rotate left.");
    }

    public void rotateRight(View view){
        if(!running) {
            stop = true;
            logicMatrix.rotate(1);
            stop = false;
        }
        Log.i("log","Rotate right.");
    }

    private void startGame(){
        final Runnable run = new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(this,500);

                if(!stop) {
                    running = true;
                    boolean finishedGame = false;
                    printMatrix();
                    finishedGame = logicMatrix.gameCycle();

                    if (finishedGame) {
                        Toast message = Toast.makeText(getApplicationContext(),"Fin del juego.", Toast.LENGTH_LONG);
                        message.show();
                        handler.removeCallbacks(this);
                    }
                    Log.i("run", "cycle");
                    running = false;
                }
            }
        };
        run.run();
    }

    private void printMatrix(){
        final GridLayout layout = (GridLayout)findViewById(R.id.screenLayout);
        final int columns = layout.getColumnCount();

        ImageView image;
        for (int x = 0 ; x < 12 ; ++x){
            for(int y = 0; y < 17 ; ++y){
                image = (ImageView) layout.getChildAt(columns * y + x);
                if (logicMatrix.spaceOccupied(x,y)){
                    image.setImageResource(R.drawable.border);
                } else {
                    image.setImageResource(R.drawable.empty);
                }
            }
        }

    }

    private void createBackground(){
        final GridLayout layout = (GridLayout)findViewById(R.id.screenLayout);

        int columns = layout.getColumnCount();
        int rows = layout.getRowCount();
        int quantity = columns*rows;

        for(int i = 0; i < quantity ; ++i){

            ImageView image = new ImageView(this);
            layout.addView(image);
            image.getLayoutParams().width = 50;
            image.getLayoutParams().height = 50;
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createBackground();
        startGame();

    }
}
