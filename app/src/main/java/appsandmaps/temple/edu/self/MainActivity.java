package appsandmaps.temple.edu.self;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;


import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.samsung.android.sdk.SsdkUnsupportedException;

import com.samsung.android.sdk.remotesensor.SrsRemoteSensorManager;
import com.samsung.android.sdk.remotesensor.SrsRemoteSensorManager.EventListener;
import com.samsung.android.sdk.remotesensor.SrsRemoteSensorEvent;
import com.samsung.android.sdk.remotesensor.SrsRemoteSensor;

import com.samsung.android.sdk.remotesensor.Srs;


import java.net.URL;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import android.content.pm.PackageInfo;


public class MainActivity extends Activity implements EventListener{


    static SrsRemoteSensorManager mServiceManager = null;
    List<SrsRemoteSensor> pedoSensorList;
    Srs remoteSensor = null;
    SrsRemoteSensor pedometerSensor = null;
    static String Steps = "0";


    private static final String GEAR_PACKAGE_NAME = "com.samsung.accessory";
    private static final String GEAR_FIT_PACKAGE_NAME = "com.samsung.android.wms";
    private static final String REMOTESENSOR_PACKAGE_NAME = "com.samsung.android.sdk.remotesensor";
    private boolean mBroadcastState = false;


    private final static String TAG = "CustomContentProvider";
    AlarmReceiver alarm = new AlarmReceiver();
    TextView textViews;
    //Button button = (Button) findViewById(R.id.button3);

    Float StepHolder = Float.valueOf(0);

    ImageView image1;
    ImageView image2;
    ImageView image3;
    ImageView image4;
    ImageView image5;
    ImageView image6;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {

            remoteSensor = new Srs();

            //checks the order of the permission and if everything is okay creates a connection with remote sensor
            if (checkPermission()) {
                initializeeSRS();
            }

            //used to display information and progress bar
            getStepInformation();

            //sets the alarm and passes the context of main activity
            alarm.setAlarm(this);

            //the manager class is passed in the remoteSensor,then you are able to control the the sensor
            mServiceManager = new SrsRemoteSensorManager(remoteSensor);

            new HurryUpandWait().execute();


            PlaceholderFragment fragment = new PlaceholderFragment();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.container1, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        image1 = (ImageView)findViewById(R.id.imageButton1);
        image2 = (ImageView)findViewById(R.id.imageButton7);
        image3 = (ImageView)findViewById(R.id.imageButton3);
        image4 = (ImageView)findViewById(R.id.imageButton4);
        image5 = (ImageView)findViewById(R.id.imageButton5);
        image6 = (ImageView)findViewById(R.id.imageButton6);

        mServiceManager.registerListener(this, pedometerSensor,
                SrsRemoteSensorManager.SENSOR_DELAY_NORMAL, 0);

        image1.setOnClickListener(new View.OnClickListener() {


            public void onClick(View arg0) {

                PlaceholderFragment fragment = new PlaceholderFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container1, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });

        image2.setOnClickListener(new View.OnClickListener() {


            public void onClick(View arg0) {

                Toast.makeText(MainActivity.this,"NULL",Toast.LENGTH_SHORT).show();

            }
        });

        image3.setOnClickListener(new View.OnClickListener() {


            public void onClick(View arg0) {

                Toast.makeText(MainActivity.this,"NULL",Toast.LENGTH_SHORT).show();

            }
        });

        image4.setOnClickListener(new View.OnClickListener() {


            public void onClick(View arg0) {

                StepsFragment fragment = new StepsFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container1, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });

        image5.setOnClickListener(new View.OnClickListener() {


            public void onClick(View arg0) {

                AppFragment fragment = new AppFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container1, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });

        image6.setOnClickListener(new View.OnClickListener() {


            public void onClick(View arg0) {

                Toast.makeText(MainActivity.this,"NULL",Toast.LENGTH_SHORT).show();

            }
        });


    }


    public void getPedometerSensorInfo() {
        pedoSensorList =
                mServiceManager.getSensorList(SrsRemoteSensor.TYPE_PEDOMETER);

        if (pedoSensorList != null) {
            SrsRemoteSensor sensor;
            sensor = pedoSensorList.get(0);
          //  makeToast(sensor.toString());

        } else {
            makeToast("Sensor is NULL Please Reload Page....");
        }
    }

    public void getPedometerEvent() {
        if (pedoSensorList != null) {
            pedometerSensor = pedoSensorList.get(0);
            mServiceManager.registerListener(this, pedometerSensor, SrsRemoteSensorManager.SENSOR_DELAY_NORMAL, 0);
        } else {
            makeToast("Sensor is NULL Please Reload Page....");
        }
    }

    public void stopPedometerEvent() {
        SrsRemoteSensor sensor;
        sensor = pedoSensorList.get(0);
        mServiceManager.unregisterListener(this, sensor);
    }


    @Override
    protected void onPause() {
        super.onPause();
        mServiceManager.unregisterListener(this, pedometerSensor);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterBroadcastReceiver();

    }

    @Override
    public void onAccuracyChanged(SrsRemoteSensor srsRemoteSensor, int i) {

    }

    @Override
    public void onSensorValueChanged(final SrsRemoteSensorEvent srsRemoteSensorEvent) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (srsRemoteSensorEvent.sensor.getType() == SrsRemoteSensor.TYPE_PEDOMETER) {
//                        pedoValueText.setText("Step Count : (" +
//                                Float.toString(srsRemoteSensorEvent.values[0]) + ")");
                    //  makeToast(Float.toString(srsRemoteSensorEvent.values[0]));

                    //May need to delete
                    Steps = Float.toString(srsRemoteSensorEvent.values[0]);

                    updateInformation("1");
                   // textViews = (TextView) findViewById(R.id.steps);
                   // textViews.setText(Steps);


                }
            }
        });
    }

    @Override
    public void onSensorDisabled(SrsRemoteSensor srsRemoteSensor) {

    }

    void updateInformation(String str_id) {
        try {
            int id = Integer.parseInt(str_id);
            ContentValues values = new ContentValues();
            makeToast(Steps);
            values.put(ContractClass.FitNessTable.STEPS, Steps);
            //  values.put(ContractClass.FitNessTable.EXPERIENCE, content.getText().toString());
            getContentResolver().update(ContractClass.CONTENT_URI, values,
                    ContractClass.FitNessTable.ID + " = " + id, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean initializeeSRS() {
        boolean srsInitState = false;

        try {
            /**
             * initialize() initialize Remote Sensor package. This needs to be called first.
             * If the device does not support Remote Sensor, SsdkUnsupportedException is thrown.
             */
            remoteSensor.initialize(this.getApplicationContext());

            srsInitState = true;

        } catch (SsdkUnsupportedException e) {
            srsInitState = false;

            switch (e.getType()) {
                case SsdkUnsupportedException.LIBRARY_NOT_INSTALLED:
                    registerBroadcastReceiver();

                    try {
                        if ((remoteSensor.isFeatureEnabled(Srs.TYPE_GEAR_MANAGER) == false) && (remoteSensor.isFeatureEnabled(Srs.TYPE_GEAR_FIT_MANAGER) == false)) {
                            Toast.makeText(this, "Install Gear Manager or Gear Fit Manager package", Toast.LENGTH_SHORT).show();
                            invokeInstallOption(R.string.manager_msg_str, null);
                            break;
                        }

                        if (remoteSensor.isFeatureEnabled(Srs.TYPE_REMOTE_SENSOR_SERVICE) == false) {
                            Toast.makeText(this, "Install Remote Sensor Service package", Toast.LENGTH_SHORT).show();
                            invokeInstallOption(R.string.rss_msg_str, null);
                        }

                    } catch (RuntimeException eRun) {
                        Toast.makeText(this, "RuntimeException = " + eRun.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    break;

                case SsdkUnsupportedException.LIBRARY_UPDATE_IS_REQUIRED:
                    Toast.makeText(this, "Package update is required", Toast.LENGTH_SHORT).show();
                    break;

                default:
                    Toast.makeText(this, "SsdkUnsupportedException = " + e.getType(), Toast.LENGTH_SHORT).show();
                    break;
            }
        } catch (IllegalArgumentException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        } catch (SecurityException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();

            invokeInstallOption(-1, e.getMessage());
        }

        return srsInitState;
    }

    private void registerBroadcastReceiver() {
        mBroadcastState = true;

        IntentFilter filter = new IntentFilter();

        filter.addAction(Intent.ACTION_PACKAGE_ADDED);
        filter.addDataScheme("package");

        this.registerReceiver(btReceiver, filter);
    }

    private void unregisterBroadcastReceiver() {

        if ((btReceiver != null) && (mBroadcastState)) {
            this.unregisterReceiver(btReceiver);
        }

        mBroadcastState = false;
    }

    BroadcastReceiver btReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent == null) {
                return;
            }

            String action = intent.getAction();

            if (action == null) {
                return;
            }

            if (action.equals(Intent.ACTION_PACKAGE_ADDED)) {
                String appName = intent.getDataString();

                if (appName != null) {
                    if (appName.toLowerCase(Locale.ENGLISH).contains(GEAR_FIT_PACKAGE_NAME.toLowerCase(Locale.ENGLISH))) {
                        invokeInstallOption(R.string.rss_msg_str, null);
                    } else if (appName.toLowerCase(Locale.ENGLISH).contains(REMOTESENSOR_PACKAGE_NAME.toLowerCase(Locale.ENGLISH))) {
                        if (checkPermission()) {
                            initializeeSRS();
                        }
                    }
                }
            }
        }
    };

    private void invokeInstallOption(final int msgID, String msg) {

        DialogInterface.OnClickListener msgClick = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int selButton) {
                switch (selButton) {
                    case DialogInterface.BUTTON_POSITIVE:

                        Intent intent = null;

                        if ((msgID == R.string.rss_msg_str) || (msgID == R.string.rss_permission_msg_str)) {
                            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("samsungapps://ProductDetail/" + "com.samsung.android.sdk.remotesensor"));
                        } else if (msgID == R.string.manager_msg_str) {
                            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("samsungapps://ProductDetail/" + "com.samsung.android.wms"));
                        } else if (msgID == R.string.permission_msg_str) {
                            Uri packageURI = Uri.parse("package:" + getPackageName());

                            intent = new Intent(Intent.ACTION_DELETE, packageURI);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        } else if (msgID == -1) {
                            finish();
                        }

                        if (intent != null) {
                            try {
                                startActivity(intent);
                            } catch (ActivityNotFoundException eRun) {
                            }
                        }

                        break;

                    default:
                        break;
                }
            }
        };

        AlertDialog.Builder message = new AlertDialog.Builder(this, AlertDialog.THEME_HOLO_DARK);

        if (msgID != -1) {
            message.setMessage(msgID);
        }

        if (msg != null) {
            message.setMessage(msg);
        }

        message.setPositiveButton(R.string.ok_str, msgClick);
        message.setCancelable(false);

        message.show();

    }

    private boolean checkPermission() {
        PackageManager packageManager = this.getPackageManager();
        boolean mIsSapInstalled = false;
        boolean mIsWingtipInstalled = false;
        boolean mIsSapPermissionGranted = false;
        boolean mIsWingtipPermissionGranted = false;

        if (packageManager == null) {
            return false;
        }

		/* If the Remote Sensor service is not installed, return */
        if ((checkPackage(packageManager, REMOTESENSOR_PACKAGE_NAME) == false) ||
                ((checkPackage(packageManager, GEAR_PACKAGE_NAME) == false) &&
                        (checkPackage(packageManager, GEAR_FIT_PACKAGE_NAME) == false))) {
            return true;
        }

		/* If the Remote Sensor service is not having permission to access Gear Manger, launch the Samsung App Store to download Remote Sensor Service*/
        if (checkPackage(packageManager, GEAR_PACKAGE_NAME) == true) {
            mIsSapInstalled = true;

            if (packageManager.checkPermission(
                    "com.samsung.accessory.permission.ACCESSORY_FRAMEWORK",
                    "com.samsung.android.sdk.remotesensor") == PackageManager.PERMISSION_GRANTED) {

                mIsSapPermissionGranted = true;
            }
        }

		/* If the Remote Sensor service is not having permission to access Gear Fit Manger, launch the Samsung App Store to download Remote Sensor Service*/
        if (checkPackage(packageManager, GEAR_FIT_PACKAGE_NAME) == true) {
            mIsWingtipInstalled = true;

            if (packageManager.checkPermission(
                    "com.samsung.android.sdk.permission.SESSION_MANAGER_SERVICE",
                    "com.samsung.android.sdk.remotesensor") == PackageManager.PERMISSION_GRANTED) {

                mIsWingtipPermissionGranted = true;
            }
        }

        if (((mIsWingtipInstalled == true) && (mIsWingtipPermissionGranted == false))
                || ((mIsSapInstalled == true) && (mIsSapPermissionGranted == false))) {

            invokeInstallOption(R.string.rss_permission_msg_str, null);

            return false;
        }

		/* If the Remote Sensor application is not having permission to access Remote Sensor Service, launch the Samsung App Store to download Remote Sensor Application*/
        if (packageManager.checkPermission(
                "com.samsung.android.sdk.permission.REMOTE_SENSOR_SERVICE",
                "appsandmaps.temple.edu.self") != PackageManager.PERMISSION_GRANTED) {

            invokeInstallOption(R.string.permission_msg_str, null);

            return false;
        }

        return true;
    }


    private boolean checkPackage(PackageManager packageManager, String szPackageName) {
        PackageInfo packageInfo = null;
        boolean bReturn = false;

        try {
            packageInfo = packageManager.getPackageInfo(szPackageName, 0);

            if (packageInfo != null) {
                return true;
            }

            bReturn = false;

        } catch (PackageManager.NameNotFoundException e1) {
            bReturn = false;
        }

        return bReturn;
    }


    //thread for testing purposes not for real project, waits 10 seconds ad then fire
    //off function for device to run
    private class HurryUpandWait extends AsyncTask<Void, Void, Void> {


        @Override
        protected Void doInBackground(Void... params) {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            publishProgress();
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            getPedometerSensorInfo();
            getPedometerEvent();
        }
    }





    public class AppFragment extends Fragment  {
        public String data;

        public AppFragment() {
        }



        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_app, container, false);

            ImageButton image = (ImageButton)rootView.findViewById(R.id.imageView7);

            image.setOnClickListener(new View.OnClickListener() {


                public void onClick(View arg0) {
                    Intent i = new Intent(MainActivity.this, Flow.class);
                    startActivity(i);
                }
            });

            return rootView;
        }
        @Override
        public void onResume() {
            super.onResume();
            ImageButton button = (ImageButton)findViewById(R.id.imageButton5);
            button.setImageResource(R.mipmap.ic_dashboard_white_24dp);
        }

        @Override
        public void onPause() {
            super.onPause();
            ImageButton button = (ImageButton)findViewById(R.id.imageButton5);
            button.setImageResource(R.mipmap.ic_dashboard_black_24dp);
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            TextView view = (TextView)findViewById(R.id.textView);
            view.setText("Apps");

            ImageButton button = (ImageButton)findViewById(R.id.imageButton5);
            button.setImageResource(R.mipmap.ic_dashboard_white_24dp);
        }

        @Override
        public void onDetach() {
            super.onDetach();
        }
    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);


            GoalFragment fragment = new GoalFragment();
            fragment.setGoal(Math.round(Float.parseFloat(Steps)));
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.fragment_goal, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

            LevelFragment fragment2 = new LevelFragment();
            fragment2.setLevel(20);
            FragmentManager fragmentManager2 = getFragmentManager();
            FragmentTransaction fragmentTransaction2 = fragmentManager2.beginTransaction();
            fragmentTransaction2.add(R.id.fragment_level, fragment2);
            fragmentTransaction2.addToBackStack(null);
            fragmentTransaction2.commit();

            RankFragment fragment1 = new RankFragment();
            fragment1.setRank(35);
            FragmentManager fragmentManager1 = getFragmentManager();
            FragmentTransaction fragmentTransaction1 = fragmentManager1.beginTransaction();
            fragmentTransaction1.add(R.id.fragment_rank, fragment1);
            fragmentTransaction1.addToBackStack(null);
            fragmentTransaction1.commit();

            SSEnergyFragment fragment4 = new SSEnergyFragment();
            //Actual Step Count
            //fragment4.setStepCount(1265);
            fragment4.setSSEnergy(Math.round(Float.parseFloat(Steps)));
            FragmentManager fragmentManager4 = getFragmentManager();
            FragmentTransaction fragmentTransaction4 = fragmentManager4.beginTransaction();
            fragmentTransaction4.add(R.id.fragment_SSEnergy, fragment4);
            fragmentTransaction4.addToBackStack(null);
            fragmentTransaction4.commit();

            return rootView;
        }

        @Override
        public void onResume() {
            super.onResume();
            ImageButton button = (ImageButton)findViewById(R.id.imageButton1);
            button.setImageResource(R.mipmap.ic_home_white_24dp);
        }

        @Override
        public void onPause() {
            super.onPause();
            ImageButton button = (ImageButton)findViewById(R.id.imageButton1);
            button.setImageResource(R.mipmap.ic_home_black_24dp);
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            TextView view = (TextView)findViewById(R.id.textView);
            view.setText("Jeff");

            ImageButton button = (ImageButton)findViewById(R.id.imageButton1);
            button.setImageResource(R.mipmap.ic_home_white_24dp);
        }

        @Override
        public void onDetach() {
            super.onDetach();

        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public class StepsFragment extends Fragment {

        public StepsFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.stepfragment, container, false);

            getPedometerSensorInfo();
            getPedometerEvent();
            getStepInformation();
            updateInformation("1");
            stopPedometerEvent();


            StepFragment fragment = new StepFragment();
            fragment.setStepCount(Math.round(Float.parseFloat(Steps)));
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.fragment_Step1, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

            return rootView;
        }

        @Override
        public void onResume() {
            super.onResume();
            ImageButton button = (ImageButton)findViewById(R.id.imageButton4);
            button.setImageResource(R.mipmap.ic_data_usage_white_24dp);

        }

        @Override
        public void onPause() {
            super.onPause();
            ImageButton button = (ImageButton)findViewById(R.id.imageButton4);
            button.setImageResource(R.mipmap.ic_data_usage_black_24dp);
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            TextView view = (TextView)findViewById(R.id.textView);
            view.setText("Steps");

            ImageButton button = (ImageButton)findViewById(R.id.imageButton4);
            button.setImageResource(R.mipmap.ic_data_usage_white_24dp);
        }

        @Override
        public void onDetach() {
            super.onDetach();

        }
    }

    int getStepInformation() {
        Cursor cur = getContentResolver().query(ContractClass.CONTENT_URI,
                null, null, null, null);

        if (cur.getCount() > 0) {
            Log.i(TAG, "Showing values.....");
            while (cur.moveToNext()) {
                String Id = cur.getString(cur.getColumnIndex(ContractClass.FitNessTable.ID));
                String title = cur.getString(cur.getColumnIndex(ContractClass.FitNessTable.STEPS));
                String Steps = cur.getString(cur.getColumnIndex(ContractClass.FitNessTable.EXPERIENCE));

            //    System.out.println("Id = " + Id + ", Steps : " + title + ", other :" + Steps);
            //    textViews = (TextView) findViewById(R.id.steps);
            //    textViews.setText(title);

                cur.close();
                makeToast(title);

                return Integer.parseInt(title);
            }


        } else {
            return 0;
        }

        return 0;
    }



    private void makeToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }

}
