package com.zdzyc.iptv.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.andexert.library.RippleView;
import com.zdzyc.iptv.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SeachActivity extends AppCompatActivity {


    @Bind(R.id.back_button)
    Button backButton;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.seach_text)
    EditText seachText;
    @Bind(R.id.seach_reset)
    ImageView seachReset;
    @Bind(R.id.cancel_action)
    TextView cancelAction;

    @Bind(R.id.more)
    RippleView rippleView;
    @OnClick(R.id.back_button)
    void back_button() {
        rippleView.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                finish();
            }
        });
    }



    @OnClick(R.id.cancel_action)
    void cancel_action() {
        cancelAction.setVisibility(View.GONE);
    }

    @OnClick(R.id.seach_reset)
    void seach_reset() {
        seachText.setText("");
    }

    @OnClick(R.id.seach_text)
    void seach_text(){
        cancelAction.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seach);
        ButterKnife.bind(this);

        title.setText(R.string.seach_title);

        seachText.addTextChangedListener(textWatcher);
    }

    private TextWatcher textWatcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {
            if (seachText.getText().length() > 0) {
                seachReset.setVisibility(View.VISIBLE);
            } else {
                seachReset.setVisibility(View.GONE);
            }
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

}
