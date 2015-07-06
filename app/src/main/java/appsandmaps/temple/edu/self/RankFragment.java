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

public class RankFragment extends Fragment {
    TextView tv;
    ProgressBar pBar;
    int progress = 0;
    public RankFragment() {
        // Required empty public constructor
    }
    public void setRank(int progress){
        this.progress=progress;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_rank, container, false);
        tv = (TextView) v.findViewById(R.id.tvRank);
        tv.setText("Rank \n"+"#" );
        pBar = (ProgressBar) v.findViewById(R.id.progressBarRank);
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
                tv.setText("Rank \n"+"#" );
            }
            @Override
            public void onAnimationEnd(Animator animation) {
                tv.setText("Rank \n"+"7" );
            }
            @Override
            public void onAnimationCancel(Animator animation) {
            }
            @Override
            public void onAnimationRepeat(Animator animation) {
                tv.setText("Rank \n"+"#" );
            }
        });
        return  v;
    }   
}
