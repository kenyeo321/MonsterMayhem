package sg.com.kaplan.pdma.monstermayhem;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class playingscreen extends AppCompatActivity {

    ImageView[] pics = new ImageView[8];        //Array of ImageViews
    ImageView newGameButton;                    //To Start a new game
    ImageView win, lose;                         //Set win lose image
    Drawable[] images = new Drawable[4];        //Array of Images as Drawable resources
    Drawable[] board;                           //Array of Drawable resources to go into the ImageViews
    Drawable questionMark;                      //Question Mark image for the "top" of each ImageView
    TextView scoreText;                         //TextView for the game score
    TextView highScoreView;                     //TextView of cumulative high score
    RelativeLayout layout;                      //The UI Layout
    boolean firstPick = true;                   //Is it the first pick in a round?
    int score = 0;                              //Score goes down by 200 each wrong guess
    int pick1 = -1, pick2 = -1;                 //While playing, sets value for first and second clicks
    int highScore;                              //The int value of the high score
    int matches = 0;                            //The number of matches so far achieved
    TextView tries;                             //TextView for tries
    int trying = 4;                             //Number of tries

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playingscreen);

        //Populate array with ImageViews from layout
        pics[0] = (ImageView) findViewById(R.id.i0);
        pics[1] = (ImageView) findViewById(R.id.i1);
        pics[2] = (ImageView) findViewById(R.id.i2);
        pics[3] = (ImageView) findViewById(R.id.i3);
        pics[4] = (ImageView) findViewById(R.id.i4);
        pics[5] = (ImageView) findViewById(R.id.i5);
        pics[6] = (ImageView) findViewById(R.id.i6);
        pics[7] = (ImageView) findViewById(R.id.i7);

        scoreText = (TextView) findViewById(R.id.scoreText);
        tries = (TextView) findViewById(R.id.triesText);
        highScoreView = (TextView) findViewById(R.id.highScoreView);
        layout = (RelativeLayout) findViewById(R.id.layout);
        newGameButton = (ImageView) findViewById(R.id.newGameButton);
        win = (ImageView) findViewById(R.id.win);
        lose = (ImageView) findViewById(R.id.lose);
        questionMark = ContextCompat.getDrawable(getApplicationContext(), R.drawable.tileqm);

        images[0] = ContextCompat.getDrawable(getApplicationContext(), R.drawable.monster1);
        images[1] = ContextCompat.getDrawable(getApplicationContext(), R.drawable.monster2);
        images[2] = ContextCompat.getDrawable(getApplicationContext(), R.drawable.monster3);
        images[3] = ContextCompat.getDrawable(getApplicationContext(), R.drawable.monster4);

        setBoard();
        highScore = getHighScore();
        highScoreView.setText("High Score = " + highScore);
        tries.setText("Try Left = " + trying);
    }

    //Sets the images to random positions
    public void setBoard() {
        board = new Drawable[8];
        //Assigns each image to two random positions
        for (int i = 0; i < images.length; i++) {
            int pic1 = (int) (Math.random() * board.length);
            int pic2 = (int) (Math.random() * board.length);
            while (board[pic1] != null) {
                pic1 = (int) (Math.random() * board.length);
            }
            board[pic1] = images[i];
            while (board[pic2] != null) {
                pic2 = (int) (Math.random() * board.length);
            }
            board[pic2] = images[i];
        }

        //Sets the question mark image to each ImageView and makes each clickable
        for (ImageView i : pics) {
            i.setImageDrawable(questionMark);
            i.setClickable(true);
        }
    }

    //If an image is clicked it will "flip" to reveal the image below
    //If the first and second pick match, 100 points are awarded, otherwise 200 are deducted
    public void click(View v) {

        switch (v.getId()) {
            case R.id.i0:
                pics[0].setImageDrawable(board[0]);
                if (firstPick) pick1 = 0;
                else pick2 = 0;
                break;

            case R.id.i1:
                pics[1].setImageDrawable(board[1]);
                if (firstPick) pick1 = 1;
                else pick2 = 1;
                break;

            case R.id.i2:
                pics[2].setImageDrawable(board[2]);
                if (firstPick) pick1 = 2;
                else pick2 = 2;
                break;

            case R.id.i3:
                pics[3].setImageDrawable(board[3]);
                if (firstPick) pick1 = 3;
                else pick2 = 3;
                break;

            case R.id.i4:
                pics[4].setImageDrawable(board[4]);
                if (firstPick) pick1 = 4;
                else pick2 = 4;
                break;

            case R.id.i5:
                pics[5].setImageDrawable(board[5]);
                if (firstPick) pick1 = 5;
                else pick2 = 5;
                break;

            case R.id.i6:
                pics[6].setImageDrawable(board[6]);
                if (firstPick) pick1 = 6;
                else pick2 = 6;
                break;

            case R.id.i7:
                pics[7].setImageDrawable(board[7]);
                if (firstPick) pick1 = 7;
                else pick2 = 7;
                break;
        }

        if (!firstPick) {
            pics[pick2].setClickable(false);

            if (board[pick1] == board[pick2]) {
                matches++;
                score += 400;
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                    }
                }, 500);
            } else {
                if (board[pick1] != board[pick2]) {
                    trying--;
                    if(score >= 200) {
                        score -= 200;
                    } else
                        score = 0;
                }
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pics[pick1].setClickable(true);
                        pics[pick2].setClickable(true);
                        pics[pick1].setImageDrawable(questionMark);
                        pics[pick2].setImageDrawable(questionMark);
                    }
                }, 500);
            }
            tries.setText("Try Left = " + trying);
            scoreText.setText("Score: " + score);
        } else {
            pics[pick1].setClickable(false);
        }

        firstPick = !firstPick;

        if (trying <= 0 || matches == 4) {
            matches = 0;
            if (score > highScore) {
                setHighScore();
            }

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    for (ImageView i : pics) {
                        i.setVisibility(View.GONE);
                    }

                }
            }, 1000);

            endGame(v);
        }
    }

    //Resets the board and variable values for a new game
    public void startGame(View v) {
        setBoard();
        for (ImageView i : pics) {
            i.setVisibility(View.VISIBLE);
        }
        score = 0;
        tries.setText("Try Left = " + trying);
        scoreText.setText("Score: " + score);
        highScoreView.setText("High Score = " + highScore);
        newGameButton.setVisibility(View.GONE);
        lose.setVisibility(View.GONE);
        win.setVisibility(View.GONE);
    }

    //Retrieves the universal high score from test file
    public int getHighScore() {
        int s = 0;
        try {
            FileInputStream f = openFileInput("hiScore.txt");
            InputStreamReader i = new InputStreamReader(f);
            try {
                s = i.read();
                i.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return s;
    }

    //Changes the universal high score in text file
    public void setHighScore() {
        try {
            FileOutputStream f = openFileOutput("hiScore.txt", MODE_WORLD_READABLE);
            OutputStreamWriter o = new OutputStreamWriter(f);
            try {
                o.write(score);
                o.flush();
                o.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        highScore = score;
    }

    //Sequence for the end of a game
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void endGame(View v) {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (trying == 0) {
                    lose.setVisibility(View.VISIBLE);
                    trying = 4;
                } else {
                    win.setVisibility(View.VISIBLE);
                    trying = 4;
                }
            }
        }, 1000);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                for (ImageView i : pics) {
                    newGameButton.setVisibility(View.VISIBLE);
                }
            }
        }, 2000);
    }
}

