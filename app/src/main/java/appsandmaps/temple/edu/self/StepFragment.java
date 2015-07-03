package appsandmaps.temple.edu.self;




import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;



public class StepFragment extends Fragment {


    TextView tv;
    ProgressBar pBar;
    int pStatus = 0;
    int eStatus = 0;

    private Handler handler = new Handler();

    public StepFragment() {
        // Required empty public constructor
    }

    public void setStepCount(Integer stepCount){
        eStatus = stepCount;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_step, container, false);


        tv = (TextView) v.findViewById(R.id.tvStep);
        pBar = (ProgressBar) v.findViewById(R.id.progressBarStep);

        pBar.setSecondaryProgress(pBar.getMax());

        new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                while (pStatus <= eStatus) {

                    handler.post(new Runnable() {

                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            pBar.setProgress(pStatus);
                            tv.setText("" +pStatus + " " +"\n"+"steps" );
                        }
                    });
                    try {
                        // Sleep for 200 milliseconds.
                        // Just to display the progress slowly
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    pStatus++;
                }

            }
        }).start();

        return v;
    }


}
