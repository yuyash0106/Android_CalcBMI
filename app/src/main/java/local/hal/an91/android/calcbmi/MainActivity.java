package local.hal.an91.android.calcbmi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //計算ボタンのButtonオブジェクトを取得。
        Button btCalc = findViewById(R.id.bt_calc);
        //リスナクラスのインスタンスを生成。
        CalcListener listener = new CalcListener();
        //計算ボタンにリスナを設定。
        btCalc.setOnClickListener(listener);
    }

    private class CalcListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {

            //身長入力欄のEditTextオブジェクトを取得。
            EditText inputHeight =  findViewById(R.id.et_height);
           String strHeight = inputHeight.getText().toString();

            //体重入力欄のEditTextオブジェクトを取得。
            EditText inputWeight =  findViewById(R.id.et_weight);
            String strWeight = inputWeight.getText().toString();

            //メッセージオブジェクトを取得。
            TextView outputBmi = findViewById(R.id.tvOutputBmi);
            TextView output = findViewById(R.id.tvOutput);

            //未入力チェック。
            if (strHeight.equals("") || strWeight.equals("")) {
                output.setText("身長と体重を入力してください。");
            } else if (strHeight.equals("0") || strWeight.equals("0")) {
                output.setText("0以外を入力してください。");
            } else {
                //BMIの値による分岐。
                double height = Double.parseDouble(inputHeight.getText().toString());
                double weight = Double.parseDouble(inputWeight.getText().toString());
                //BMI計算
                double bmi = weight / ((height / 100) * (height / 100));
                BigDecimal bigBmi = new BigDecimal(bmi);
                bigBmi = bigBmi.setScale(1, RoundingMode.HALF_UP);
                //身長をメートルに変換
                double heightM = height / 100;
                //適正体重計算
                double properWeight = (heightM * heightM) * 22;
                BigDecimal bigWeight = new BigDecimal(properWeight);
                bigWeight = bigWeight.setScale(0, RoundingMode.HALF_UP);
                //bigBmiをdoubleに
                double bmiD = bigBmi.doubleValue();
                //BMIの値による分岐。
                if (bmiD >=25) {
                    outputBmi.setText("BMI:" + bigBmi);
                    output.setText("肥満です。体重" + bigWeight + "kgを目指しましょう。");
                } else if (bmiD >= 18.5) {
                    outputBmi.setText("BMI:" + bigBmi);
                    output.setText("ちょうどいいです。理想体重は" + bigWeight + "kgです。現状を維持しましょう。");
                } else if (bmiD < 18.5) {
                    outputBmi.setText("BMI:" + bigBmi);
                    output.setText("やせています。体重" + bigWeight + "kgを目指しましょう。");
                }
            }
        }
    }
}

