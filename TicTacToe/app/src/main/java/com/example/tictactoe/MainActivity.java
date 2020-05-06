package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import androidx.gridlayout.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    //player1(cross)=1, player2(zero)=0
    int activePlayer=0;
    int gametag[]={2,2,2,2,2,2,2,2,2};
    int[][] win={{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};
    boolean gameactive=true;

    public void tictac(View view){
        ImageView imageView=(ImageView)view;
        Log.i("tag",imageView.getTag().toString());
        int g = Integer.parseInt(imageView.getTag().toString());
        if(gametag[g]==2 && gameactive) {

            gametag[g] = activePlayer;
            imageView.setTranslationY(-1000);

            if (activePlayer == 0) {
                imageView.setImageResource(R.drawable.zero);
                Toast.makeText(this, "Player1", Toast.LENGTH_SHORT).show();
                activePlayer = 1;
            } else {
                imageView.setImageResource(R.drawable.xxx);
                Toast.makeText(this, "Player2", Toast.LENGTH_SHORT).show();
                activePlayer = 0;
            }
            imageView.animate().translationYBy(1000).setDuration(300);


            for (int[] win : win) {
                if (gametag[win[0]] == gametag[win[1]] && gametag[win[1]] == gametag[win[2]] && gametag[win[0]] != 2) {
                    gameactive=false;
                    String winner;
                    if (gametag[win[0]] == 0) {
                        winner="player 1";
                        Toast.makeText(this, winner+" won", Toast.LENGTH_SHORT).show();
                    } else {
                        winner="player 2";
                        Toast.makeText(this, winner+" won", Toast.LENGTH_SHORT).show();
                    }
                    Button playagain=(Button)findViewById(R.id.playbutton);
                    playagain.setVisibility(View.VISIBLE);
                    TextView wintext=(TextView)findViewById(R.id.winnertextView);
                    wintext.setText(winner+" has won");
                    wintext.setVisibility(View.VISIBLE);


                }

            }

        }
    }

    public void playagain(View view){
        Button playagain=(Button)findViewById(R.id.playbutton);
        playagain.setVisibility(View.INVISIBLE);
        TextView wintext=(TextView)findViewById(R.id.winnertextView);
        wintext.setVisibility(View.INVISIBLE);
         activePlayer=0;
         gameactive=true;
        for(int i=0; i<gametag.length;i++){
            gametag[i]=2;
        }
        GridLayout gl=(GridLayout)findViewById(R.id.gridlayout);
        for(int j=0; j<gl.getChildCount();j++){
            ImageView imageView= (ImageView)gl.getChildAt(j);
            imageView.setImageDrawable(null);
        }
    }

}
