package com.example.patternlockviewtest2;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;

import org.jetbrains.annotations.Nullable;

import java.util.List;

import io.paperdb.Paper;

public final class MainActivity extends AppCompatActivity {
    PatternLockView mPatternLockView1;
    PatternLockView mPatternLockView2;
    String first_pattern = "";
    String second_pattern = "";
    String first_pattern_key = "first_pattern_code";
    String second_pattern_key = "second_pattern_code";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Paper.init(this);
       String first_pattern_saved = Paper.book().read(first_pattern_key);
        String second_pattern_saved = Paper.book().read(second_pattern_key);
        if ((first_pattern_saved != null && !first_pattern_saved.equals("null"))
                && (second_pattern_saved != null && !second_pattern_saved.equals("null"))) {
            setContentView(R.layout.pattern_screen);
            mPatternLockView1 = (PatternLockView) findViewById(R.id.pattern_lock_view1);
            mPatternLockView2 = (PatternLockView) findViewById(R.id.pattern_lock_view2);
            mPatternLockView1.addPatternLockListener(new PatternLockViewListener() {
                @Override
                public void onStarted() {

                }

                @Override
                public void onProgress(List<PatternLockView.Dot> progressPattern) {

                }

                @Override
                public void onComplete(List<PatternLockView.Dot> pattern) {
                    first_pattern = PatternLockUtils.patternToString(mPatternLockView1,pattern);
                    if(first_pattern.equals(first_pattern_saved)){
                        Toast.makeText(MainActivity.this,"Pattern is correct ",Toast.LENGTH_SHORT).show();
                        mPatternLockView1.setVisibility(View.GONE);
                        mPatternLockView2.setVisibility(View.VISIBLE);
                    }else{
                        Toast.makeText(MainActivity.this,"Pattern is incorrect! ",Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onCleared() {

                }
            });

            mPatternLockView2.addPatternLockListener(new PatternLockViewListener() {
                @Override
                public void onStarted() {

                }

                @Override
                public void onProgress(List<PatternLockView.Dot> progressPattern) {

                }

                @Override
                public void onComplete(List<PatternLockView.Dot> pattern) {
                    second_pattern = PatternLockUtils.patternToString(mPatternLockView2,pattern);
                    if(second_pattern.equals(second_pattern_saved)){
                        Toast.makeText(MainActivity.this,"Pattern 2 is correct ",Toast.LENGTH_SHORT).show();
                        deletePattern();
                        finish();
                    }else{
                        Toast.makeText(MainActivity.this,"Pattern 2 is incorrect! ",Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onCleared() {

                }
            });
        } else {
            setContentView(R.layout.activity_main);
            mPatternLockView1 = (PatternLockView) findViewById(R.id.pattern_lock_view1);
            mPatternLockView2 = (PatternLockView) findViewById(R.id.pattern_lock_view2);
            Button btnOne = (Button) findViewById(R.id.btnSetPattern1);
            Button btnTwo = (Button) findViewById(R.id.btnSetPattern2);

            btnOne.setVisibility(View.GONE);
            btnTwo.setVisibility(View.GONE);

            mPatternLockView2.setVisibility(View.GONE);

            mPatternLockView1.addPatternLockListener(new PatternLockViewListener() {
                @Override
                public void onStarted() {

                }

                @Override
                public void onProgress(List<PatternLockView.Dot> progressPattern) {

                }

                @Override
                public void onComplete(List<PatternLockView.Dot> pattern) {
                    first_pattern= PatternLockUtils.patternToString(mPatternLockView1,pattern);
                    btnOne.setVisibility(View.VISIBLE);
                }

                @Override
                public void onCleared() {

                }
            });
            btnOne.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Paper.book().write(first_pattern_key,first_pattern);
                    Toast.makeText(MainActivity.this,"Pattern 1 saved",Toast.LENGTH_SHORT).show();
                    mPatternLockView2.setVisibility(View.VISIBLE);
                }
            });

            mPatternLockView2.addPatternLockListener(new PatternLockViewListener() {
                @Override
                public void onStarted() {

                }

                @Override
                public void onProgress(List<PatternLockView.Dot> progressPattern) {

                }

                @Override
                public void onComplete(List<PatternLockView.Dot> pattern) {
                    second_pattern= PatternLockUtils.patternToString(mPatternLockView2,pattern);
                    btnTwo.setVisibility(View.VISIBLE);
                }

                @Override
                public void onCleared() {

                }
            });
            btnTwo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Paper.book().write(second_pattern_key,second_pattern);
                    Toast.makeText(MainActivity.this,"Pattern 2 saved",Toast.LENGTH_SHORT).show();
                    finish();
                }
            });
        }
    }

    private void deletePattern() {
        Paper.book().delete(first_pattern_key);
        Paper.book().delete(second_pattern_key);
        Toast.makeText(MainActivity.this, "Pattern cleared", Toast.LENGTH_SHORT).show();

    }
}
