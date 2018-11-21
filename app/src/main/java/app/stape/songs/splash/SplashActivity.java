package app.stape.songs.splash;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import app.stape.songs.R;
import app.stape.songs.top.artists.view.TopArtistsActivity;

public class SplashActivity extends AppCompatActivity implements Animator.AnimatorListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        startAnimation();
    }

    private void startAnimation() {
        PropertyValuesHolder alpha = PropertyValuesHolder.ofFloat("alpha", 0f, 1f);
        PropertyValuesHolder x = PropertyValuesHolder.ofFloat("scaleX", 0f, 1f);
        PropertyValuesHolder y = PropertyValuesHolder.ofFloat("scaleY", 0f, 1f);

        ImageView logo = findViewById(R.id.logo);

        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(logo, alpha, x, y);
        animator.setDuration(1000);
        animator.addListener(this);
        animator.start();
    }

    @Override
    public void onAnimationStart(Animator animation) {

    }

    @Override
    public void onAnimationEnd(Animator animation) {
       startActivity(new Intent(this, TopArtistsActivity.class));
       finish();
    }

    @Override
    public void onAnimationCancel(Animator animation) {

    }

    @Override
    public void onAnimationRepeat(Animator animation) {

    }
}
