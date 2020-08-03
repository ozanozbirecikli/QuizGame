package com.example.ozan.quizgame;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;


import java.util.Random;

public class Quiz extends AppCompatActivity {

    private CountDownTimer timer;
    private long timeLeft = 10000;
    int seconds;
    AlertDialog.Builder builder;
    private int point = 0;
    private Random random = new Random();
    int nextQ;
    private TextView question, A, B, C, score, time;
    private String[] questionlist = {" ", "Whats the color of the sky?", "Are gila monsters venomous?",
            "Are blue-ringed octopuses venomous?", "What is the national games of Anguilla?", "What is the national sport of United States?",
            "What is the national sport of Bermuda?", "What is the capital city of Belarus?",
            "What is the capital city of Iceland?", "What is the capital city of Slovakia?",
            "Where is Tomato Festival celebrated?", "Where is Maine Lobster Festival celebrated?",
            "Where is Ivrea Orange Festival celebrated?", "How many Apollo missions landed men on the moon?",
            "In which country will the 2018 Winter Olympics be held?", "Freddie Mercury died in which year?",
            "Where was Che Guevara killed?", "Who was the first mailman in Philadelphia?",
            "Which fast food chain has the most restaurants in America?",
            "Which of these cities is farthest north?", "Which album has sold most copies worldwide?"
    };


    private String[] answers = {" ", "Blue", "White", "Yellow", "Maybe", "No", "Yes", "Maybe", "No", "Yes"
            , "Yacht racing", "Football", "Archery", "Basketball", "American Football", "Baseball", "Swimmming", "Cricket", "Football",
            "Minsk", "Tirana", "Accra", "Vientiane", "Reykjavik", "Malé", "Ljubljana", "Manila", "Bratislava",
            "Mexico", "Portugal", "Spain", "USA", "Canada", "Australia", "Spain", "Italy", "Japan", "Two", "Five", "Six"
            , "Canada", "South Korea", "Germany", "1999", "1991", "1995", "Bolivia", "Cuba", "Jamaica", "Benjamin Franklin",
            "Neil Mail", "Nik Kershaw", "Subway", "McDonald's", "KFC", "Vancouver, Canada", "Bucharest, Romania", "Dublin, Ireland",
            "Thriller - Michael Jackson", "Saturday Night Fever - Bee Gees", "The Wall - Pink Floyd"
    };

    private int[] check = {0, 1, 6, 9, 10, 15, 17, 19, 23, 27, 30, 31, 35, 39, 41, 44, 46, 49, 52, 57, 58};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        question = findViewById(R.id.textQuestion);
        A = findViewById(R.id.A);
        B = findViewById(R.id.B);
        C = findViewById(R.id.C);
        score = findViewById(R.id.score);
        time = findViewById(R.id.time);

        builder = new AlertDialog.Builder(Quiz.this);


        setAnswerQuestions();

    }

    private void setAnswerQuestions() {
        nextQ = random.nextInt(questionlist.length - 2);
        int nextA = (nextQ + 1) * 3;

        question.setText(questionlist[nextQ + 1]);
        A.setText(answers[nextA]);
        B.setText(answers[nextA - 1]);
        C.setText(answers[nextA - 2]);

        timeLeft = 10000;


        timer = new CountDownTimer(timeLeft, 1000) {
            @Override
            public void onTick(long l) {
                timeLeft = l;
                seconds = (int) timeLeft % 600000 / 1000;
                time.setText("" + seconds);
            }

            @Override
            public void onFinish() {

            }
        }.start();

    }

    private void answerChecker(int i, String answergiven) {
        String answer = answers[check[i]];
        if (seconds == 0) {
            builder.setTitle("Time is Up!");
            builder.setTitle("Sorry! Your time is up");
            builder.setCancelable(false);

            builder.setNegativeButton("Back to Menu", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    onBackPressed();
                }
            });
            builder.setPositiveButton("Restart", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    timer.cancel();
                    point = 0;
                    score.setText("" + point);
                    setAnswerQuestions();

                }
            });
            builder.show();
        } else if (answer.equals(answergiven)) {
            point += 1;
            score.setText("" + point);
            timer.cancel();
            setAnswerQuestions();

        } else {
            builder.setTitle("Game Over");
            builder.setMessage("Sorry wrong answer!");
            builder.setCancelable(false);
            builder.setNegativeButton("Back to Menu", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    onBackPressed();

                }
            });
            builder.setPositiveButton("Restart", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    timer.cancel();
                    point = 0;
                    score.setText("" + point);
                    setAnswerQuestions();

                }
            });
            builder.show();
        }
    }

    public void onClick(View view) {

        TextView clicked = (TextView) view;
        String answer = clicked.getText().toString();
        answerChecker(nextQ + 1, answer);
    }
}



