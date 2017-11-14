package sister.ta.app;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import sister.ta.app.model.User;


public class DosenFragment extends Fragment {
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;

    View view;
    ToggleButton toggleStatus;
    TextView txtGanti;
    String email, id, jurusan, nama_lengkap, role, share, status;

    GPSTracker gpsTracker;

    public DosenFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        gpsTracker = new GPSTracker(getContext());
        AsyncTask asyncTask = new AsyncTask(gpsTracker, getContext());
        asyncTask.execute();

        view = inflater.inflate(R.layout.fragment_dosen, container, false);
        toggleStatus = view.findViewById(R.id.toggleStatus);
        txtGanti = view.findViewById(R.id.txtGanti);
        try{
            databaseReference.child("users").child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    User user = dataSnapshot.getValue(User.class);

                    email = user.getEmail(); id = user.getId(); jurusan = user.getJurusan();
                    nama_lengkap = user.getNama_lengkap(); role = user.getRole(); share = user.getShare();
                    status = user.getStatus();

                    if(share.equals("no")) {
                        toggleStatus.setChecked(false);
                        txtGanti.setText("Klik tombol diatas untuk share status lokasi anda");
                    }
                    else {
                        toggleStatus.setChecked(true);
                        txtGanti.setText("Klik tombol diatas untuk stop share status lokasi anda");
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        } catch (Exception ex){
            System.out.println(ex.toString());
        }

        toggleStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            String toggleState;
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                    toggleState="yes";
                else
                    toggleState="no";
                User user = new User(id, email, nama_lengkap, status, role, jurusan, toggleState);
                databaseReference.child("users").child(firebaseUser.getUid()).setValue(user);
            }
        });
        return view;
    }

    private void setLocationAddress() {
        if (gpsTracker.getLocation() != null) {
            if (gpsTracker.getLatitude() != 0 && gpsTracker.getLongitude() != 0) {
//                Toast.makeText(getContext(), gpsTracker.getLatitude() + "," + gpsTracker.getLongitude(), Toast.LENGTH_SHORT).show();
                System.out.println(gpsTracker.getLatitude() + "," + gpsTracker.getLongitude());
            } else {
                buildAlertMessageNoGps();
            }
        } else {
            buildAlertMessageNoGps();
        }
    }
    private void buildAlertMessageNoGps() {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
            builder1.setMessage("Location not detected");
            builder1.setCancelable(true);

            builder1.setPositiveButton("Try Again",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            setLocationAddress();
                        }
                    });

            builder1.setNegativeButton(
                    android.R.string.cancel,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alert11 = builder1.create();
            alert11.show();
    }

    class AsyncTask extends android.os.AsyncTask<Void, Void, Boolean>{
        Context context;
        GPSTracker gpsTracker;
        int i=0;

        public AsyncTask(GPSTracker gpsTracker, Context context){
            this.gpsTracker = gpsTracker;
            this.context=context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            gpsTracker = new GPSTracker(getContext());
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            while(i<1){
                try {
                    if(){

                    }
                    setLocationAddress();
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    }
}
