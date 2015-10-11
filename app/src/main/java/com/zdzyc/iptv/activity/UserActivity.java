package com.zdzyc.iptv.activity;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zdzyc.iptv.R;
import com.zdzyc.iptv.util.Common;
import com.zdzyc.iptv.widget.PullToZoomScrollViewEx;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserActivity extends AppCompatActivity {

    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.listview)
    PullToZoomScrollViewEx listview;

    @OnClick(R.id.back_button)
    void back_button() {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        ButterKnife.bind(this);
        title.setText("个人资料");

        View zoomView = LayoutInflater.from(this).inflate(R.layout.user_info_head, null, false);
        View contentView = LayoutInflater.from(this).inflate(R.layout.user_item_list, null, false);
        listview.setZoomView(zoomView);
        listview.setHeaderViewSize(Common.SCREEN_W,150);
        listview.setScrollContentView(contentView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user, menu);
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
