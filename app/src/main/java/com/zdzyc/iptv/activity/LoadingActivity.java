package com.zdzyc.iptv.activity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.BounceInterpolator;
import android.widget.RelativeLayout;

import com.zdzyc.iptv.R;
import com.zdzyc.iptv.util.Common;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class LoadingActivity extends AppCompatActivity {

    @Bind(R.id.user_face_rll)
    RelativeLayout userFaceRll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        ButterKnife.bind(this);
        ObjectAnimator moveIn = ObjectAnimator.ofFloat(userFaceRll, "translationY", 0, Common.SCREEN_H /3 );
        AnimatorSet animSet = new AnimatorSet();
        animSet.play(moveIn);
        animSet.setDuration(4000);
        animSet.setInterpolator(new BounceInterpolator());
        animSet.start();
        moveIn.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                startActivity(new Intent(LoadingActivity.this, MainActivity.class));
                finish();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });


    }

//    private void startAnimation() {
//        Point startPoint = new Point(getWidth() / 2, RADIUS);
//        Point endPoint = new Point(getWidth() / 2, getHeight() - RADIUS);
//        ValueAnimator anim = ValueAnimator.ofObject(new PointEvaluator(), startPoint, endPoint);
//        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                currentPoint = (Point) animation.getAnimatedValue();
//                invalidate();
//            }
//        });
//        anim.setInterpolator(new BounceInterpolator());
//        anim.setDuration(3000);
//        anim.start();
//    }

}
