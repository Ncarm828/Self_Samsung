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



public class StepFragment extends Fragment {
    TextView tv;
    ProgressBar pBar;
    int progress = 0;
    
    public StepFragment() {
        // Required empty public constructor
    }

    public void setStepCount(int stepCount){
        progress = stepCount;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_step, container, false);

        tv = (TextView) v.findViewById(R.id.tvStep);
        tv.setText(" # " + "\n" + "steps");
        pBar = (ProgressBar) v.findViewById(R.id.progressBarStep);
        pBar.setSecondaryProgress(pBar.getMax());

        //using animation to fill the data points
        ObjectAnimator animator = ObjectAnimator.ofInt(pBar, "progress", progress);
        //setting the time for 750 miliseconds
        animator.setDuration(750);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.start();
        //Adding listner to show the endpoint result
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                tv.setText(" # " + "\n" + "steps");
            }
            @Override
            public void onAnimationEnd(Animator animation) {
                tv.setText("" + progress + " " + "\n" + "steps");
            }
            @Override
            public void onAnimationCancel(Animator animation) {
            }
            @Override
            public void onAnimationRepeat(Animator animation) {
                tv.setText(" # " + "\n" + "steps");
            }
        });
        return v;
    }
}
