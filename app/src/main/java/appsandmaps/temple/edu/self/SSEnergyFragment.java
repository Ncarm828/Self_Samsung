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

public class SSEnergyFragment extends Fragment {
    TextView tv;
    ProgressBar pBar;
    int pStatus = 0;
    int SSEnegry =0 ;
    private Handler handler = new Handler();
    public SSEnergyFragment() {
        // Required empty public constructor
    }
    public void setStepCount (Integer steps) {
        //Getting value from the function call and setting it as a End point
        SSEnegry = steps/100;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_ssenergy, container, false);
        tv = (TextView) v.findViewById(R.id.tvSSEnergy);
//        pBar = (myProgressBar) findViewById(R.id.progressBar1);

        pBar = (ProgressBar) v.findViewById(R.id.progressBarSSEnergy);

        new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                while (pStatus <= SSEnegry) {

                    handler.post(new Runnable() {

                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            pBar.setProgress(pStatus);
                            tv.setText("SS ENERGY  :  " +pStatus );
                        }
                    });
                    try {
                        // Sleep for 200 milliseconds.
                        // Just to display the progress slowly
                        Thread.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    pStatus ++;
                }
            }
        }).start();
        return v;
    }

}