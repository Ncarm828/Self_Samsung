package appsandmaps.temple.edu.self;




import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import appsandmaps.temple.edu.self.R;

public class GoalFragment extends Fragment {

    TextView tv;
    ProgressBar pBar;
    int progress = 55;

    public GoalFragment() {
        // Required empty public constructor
    }

    //setting goal as the % of dialy step cnt, assuming the avg steps to be 7000
    public void setGoal(int stepCount){
        progress = ((stepCount*100)/7000);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_goal, container, false);
        tv = (TextView) v.findViewById(R.id.tvGoal);

        tv.setText("Goal \n" +"#" + " %" );

        pBar = (ProgressBar) v.findViewById(R.id.progressBarGoal);
        pBar.setSecondaryProgress(pBar.getMax());



        //using animation to fill the data points
        ObjectAnimator animator = ObjectAnimator.ofInt(pBar, "progress", progress);
        //setting the time for 500 miliseconds
        animator.setDuration(500);
        animator.setInterpolator(new LinearInterpolator());
        animator.start();
        //Adding listner to show the endpoint result

        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

                tv.setText("Goal \n" +"#" + " %" );
            }

            @Override
            public void onAnimationEnd(Animator animation) {

                tv.setText("Goal \n" + progress + " %" );
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

                tv.setText("Goal \n" +"#" + " %" );

            }
        });
     return  v;
    }
