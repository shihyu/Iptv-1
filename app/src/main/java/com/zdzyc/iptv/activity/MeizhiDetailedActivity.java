package com.zdzyc.iptv.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.andexert.library.RippleView;
import com.bumptech.glide.Glide;
import com.zdzyc.iptv.R;
import com.zdzyc.iptv.app.StatusBarCompat;
import com.zdzyc.iptv.data.entity.Meizhi;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.senab.photoview.PhotoViewAttacher;

public class MeizhiDetailedActivity extends AppCompatActivity {

    @Bind(R.id.iv_meizhi)
    ImageView ivMeizhi;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meizhi_detailed);
        ButterKnife.bind(this);
        StatusBarCompat.compat(this, R.color.primaryDark);
        Meizhi news = (Meizhi)getIntent().getSerializableExtra("news");

        Glide.with(this).load(news.getUrl()).into(ivMeizhi);

        PhotoViewAttacher mAttacher = new PhotoViewAttacher(ivMeizhi);
        mAttacher.update();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_meizhi_detailed, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
