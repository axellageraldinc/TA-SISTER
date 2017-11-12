package sister.ta.app;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import sister.ta.app.model.Jurusan;
import sister.ta.app.model.User;

public class RegisterActivity extends AppCompatActivity {
    Button btnDaftar;
    EditText txtEmail, txtPassword, txtNamaLengkap;
    Spinner spinnerRole, spinnerJurusan;

    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;

    String userId, email, password, namaLengkap, jurusan;
    List<String> listJurusan = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseAuth = FirebaseAuth.getInstance();

        btnDaftar = findViewById(R.id.btnDaftar);
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        txtNamaLengkap = findViewById(R.id.txtNamaLengkap);
        spinnerRole = findViewById(R.id.spinnerRole);
        spinnerJurusan = findViewById(R.id.spinnerJurusan);

        GetListRole();
        GetListJurusan();

//        spinnerJurusan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                jurusan = adapterView.getItemAtPosition(i).toString();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });

        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!txtEmail.getText().toString().isEmpty() &&
                        !txtPassword.getText().toString().isEmpty() &&
                        !txtNamaLengkap.getText().toString().isEmpty()){
                    RegisterUser();
                } else{
                    Toast.makeText(RegisterActivity.this, "Isi semua field yang ada!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void GetListRole(){
        List<String> listRole = new ArrayList<>();
        listRole.add("Mahasiswa");
        listRole.add("Dosen");
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(RegisterActivity.this, android.R.layout.simple_spinner_item, listRole);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spinnerRole.setAdapter(dataAdapter);
    }

    private void GetListJurusan(){
        databaseReference = FirebaseDatabase.getInstance().getReference("list_jurusan");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try{
                    listJurusan.clear();
                    for(DataSnapshot data : dataSnapshot.getChildren()){
                        String jurusan = data.getValue(Jurusan.class).getJurusan();
                        listJurusan.add(jurusan);
                    }
                    // Creating adapter for spinner
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(RegisterActivity.this, android.R.layout.simple_spinner_item, listJurusan);
                    // Drop down layout style - list view with radio button
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    // attaching data adapter to spinner
                    spinnerJurusan.setAdapter(dataAdapter);
                } catch (Exception ex){
                    System.out.println("Error get list jurusan on DataChange : " + ex.toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void RegisterUser(){
        email = txtEmail.getText().toString();
        password = txtPassword.getText().toString();
        namaLengkap = txtNamaLengkap.getText().toString();

        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(RegisterActivity.this, "Berhasil registrasi user!", Toast.LENGTH_SHORT).show();
                    txtEmail.setText("");
                    txtPassword.setText("");
                    txtNamaLengkap.setText("");
                    userId = task.getResult().getUser().getUid();
                    User user;
                    if(spinnerRole.getSelectedItem().toString().equals("Mahasiswa")){
                        user = new User(userId, email, namaLengkap, "no", spinnerRole.getSelectedItem().toString(), spinnerJurusan.getSelectedItem().toString());
                    } else{
                        user = new User(userId, email, namaLengkap, "no", spinnerRole.getSelectedItem().toString(), spinnerJurusan.getSelectedItem().toString(), "no");
                    }
                    System.out.println("Jurusan selected : " + spinnerJurusan.getSelectedItem().toString());
                    databaseReference = FirebaseDatabase.getInstance().getReference("users");
                    databaseReference.child(userId).setValue(user);
                    firebaseAuth.signOut();
                } else{
                    Toast.makeText(RegisterActivity.this, "Gagal registrasi user", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
