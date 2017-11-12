package sister.ta.app;

import android.content.Context;
import android.net.Uri;
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
}
