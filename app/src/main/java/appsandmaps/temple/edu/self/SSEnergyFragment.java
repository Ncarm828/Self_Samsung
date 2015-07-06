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

public class SSEnergyFragment extends Fragment {
    TextView tv;
    ProgressBar pBar;
    int progress =0 ;
    public SSEnergyFragment() {
        // Required empty public constructor
    }
    public void setSSEnergy (int steps) {
        //Getting value from the function call and setting it as a End point
        progress = steps/100;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_ssenergy, container, false);
        //grabbing the textview to display the values
        tv = (TextView) v.findViewById(R.id.tvSSEnergy);

        tv.setText("SS ENERGY  :  " +"#" );

        //getting the progreebar to fill up with data
        pBar = (ProgressBar) v.findViewById(R.id.progressBarSSEnergy);

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
                tv.setText("SS ENERGY  :  " +"#" );
            }
            @Override
            public void onAnimationEnd(Animator animation) {
                tv.setText("SS ENERGY  :  " + progress );
            }
            @Override
            public void onAnimationCancel(Animator animation) {
            }
            @Override
            public void onAnimationRepeat(Animator animation) {
                tv.setText("SS ENERGY  :  " +"#" );
            }
        });
        return v;
    }
}