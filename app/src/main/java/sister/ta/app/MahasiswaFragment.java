package sister.ta.app;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import sister.ta.app.model.User;

public class MahasiswaFragment extends Fragment {
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;

    View view;
    RecyclerView recyclerDosen;
    HomeListAdapter homeListAdapter;
    List<User> userList = new ArrayList<>();

    String jurusan;

    public MahasiswaFragment(){

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
        try{
            databaseReference.child("users").child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    User user = dataSnapshot.getValue(User.class);
                    jurusan = user.getJurusan();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        } catch (Exception ex){
            System.out.println(ex.toString());
        }

        view = inflater.inflate(R.layout.fragment_mahasiswa, container, false);
        recyclerDosen = view.findViewById(R.id.listViewHome);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        recyclerDosen.setLayoutManager(llm);
        homeListAdapter = new HomeListAdapter(getContext(), userList);
        GetDataDosen();
        return view;
    }

    private void GetDataDosen(){
        databaseReference.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userList.clear();
                for(DataSnapshot data : dataSnapshot.getChildren()) {
                    User user = data.getValue(User.class);
                    if (user.getJurusan().equals(jurusan) &&
                            user.getRole().equals("Dosen") &&
                            user.getShare().equals("yes"))
                        userList.add(user);
                }
                recyclerDosen.setAdapter(homeListAdapter);
                homeListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
