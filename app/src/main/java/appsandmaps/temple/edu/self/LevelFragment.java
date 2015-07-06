package appsandmaps.temple.edu.self;


import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ProgressBar;
import android.widget.TextView;
import appsandmaps.temple.edu.self.R;

public class LevelFragment extends Fragment {

    TextView tv;
    ProgressBar pBar;
    int progress = 0;
    
    public LevelFragment() {
        // Required empty public constructor
    }
    public void setLevel(int progress){
        this.progress = progress;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_level, container, false);

        tv = (TextView) v.findViewById(R.id.tvLevel);
        tv.setText("Level \n"+"#");


        pBar = (ProgressBar) v.findViewById(R.id.progressBarLevel);
        pBar.setSecondaryProgress(pBar.getMax());

        //using animation to fill the data points
        ObjectAnimator animator = ObjectAnimator.ofInt(pBar, "progress", progress);
        //setting the time for 500 miliseconds
        animator.setDuration(500);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.start();
        //Adding listner to show the endpoint result
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {


                tv.setText("Level \n"+"#");
            }

            @Override
            public void onAnimationEnd(Animator animation) {

                tv.setText("Level \n"+"5");
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {


                tv.setText("Level \n"+"#");
            }
        });


        return v;
    }
}
