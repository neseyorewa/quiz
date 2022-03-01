package quiz.example.kisokusei;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.e.kisokusei1.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

        private TextView mondaibangou, question;
        private Button answer1, answer2, answer3, answer4;

        private String rightAnswer;
        private int rightAnswerCount = 0;
        private int quizCount = 1;
    static final private int QUIZ_COUNT = 5;


    ArrayList<ArrayList<String>> quizArray = new ArrayList<>();

        String[][] quizData = {
                // {"問題", "正解", "選択肢１", "選択肢２", "選択肢３"}
                {"1,3,5,X,9", "7", "2", "11", "8"},
                {"2,3,X,8,12,17,23", "5", "9", "4", "6"},
                {"2,4,8,16,X,64", "32","18", "52", "44"},
                {"1,3,2,4,3,X", "5", "8", "10", "15"},
                {"3,4,6,9,13,X", "25","33", "27", "18"},
                {"3,2,4,3,6,5,10,X", "9","20", "14", "18"},
                {"5,3,6,4,8,6,X", "12", "4", "8", "21"},
                {"1,2,4,7,11,X", "16", "13", "21", "30"},
                {"2,6,4,12,10,30,X", "28", "45", "63", "24"},
                {"10,8,16,14,28,X", "26", "36", "23", "48"},
        };

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            mondaibangou = findViewById(R.id.mondaibangou);
            question = findViewById(R.id.question);
            answer1 = findViewById(R.id.answer1);
            answer2 = findViewById(R.id.answer2);
            answer3 = findViewById(R.id.answer3);
            answer4 = findViewById(R.id.answer4);

            // ここから追加
            // quizDataからクイズ出題用のquizArrayを作成する
            for (int i = 0; i < quizData.length; i++) {

                // 新しいArrayListを準備
                ArrayList<String> tmpArray = new ArrayList<>();

                // クイズデータを追加
                tmpArray.add(quizData[i][0]);  // 問題
                tmpArray.add(quizData[i][1]);  // 正解
                tmpArray.add(quizData[i][2]);  // 選択肢１
                tmpArray.add(quizData[i][3]);  // 選択肢２
                tmpArray.add(quizData[i][4]);  // 選択肢３

                // tmpArrayをquizArrayに追加する
                quizArray.add(tmpArray);
            }


            showNextQuiz();

        }

            public void showNextQuiz () {
                // クイズカウントラベルを更新
                mondaibangou.setText("Q" + quizCount);

                // ランダムな数字を取得
                Random random = new Random();
                int randomNum = random.nextInt(quizArray.size());

                // randomNumを使って、quizArrayからクイズを一つ取り出す
                ArrayList<String> quiz = quizArray.get(randomNum);

                // 問題文（都道府県名）を表示
                question.setText(quiz.get(0));

                // 正解をrightAnswerにセット
                rightAnswer = quiz.get(1);

                // クイズ配列から問題文（都道府県名）を削除
                quiz.remove(0);

                // 正解と選択肢３つをシャッフル
                Collections.shuffle(quiz);

                // 回答ボタンに正解と選択肢３つを表示
                answer1.setText(quiz.get(0));
                answer2.setText(quiz.get(1));
                answer3.setText(quiz.get(2));
                answer4.setText(quiz.get(3));

                // このクイズをquizArrayから削除
                quizArray.remove(randomNum);
            }

    public void checkAnswer(View view) {

        // どの回答ボタンが押されたか
        Button answerBtn = findViewById(view.getId());
        String btnText = answerBtn.getText().toString();

        String alertTitle;
        if (btnText.equals(rightAnswer)) {
            alertTitle = "正解!";
            rightAnswerCount++;
        } else {
            alertTitle = "不正解...";
        }

        // ダイアログを作成
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(alertTitle);
        builder.setMessage("答え : " + rightAnswer);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (quizCount == QUIZ_COUNT) {
                    // 結果画面へ移動
                    Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
                    intent.putExtra("RIGHT_ANSWER_COUNT", rightAnswerCount);
                    startActivity(intent);


                }
                else{
                    quizCount++;
                    showNextQuiz();
                    }
            }
        }).setCancelable(false);
        builder.show();
    }

}
