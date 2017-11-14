package sister.ta.app;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import sister.ta.app.model.User;

public class LoginActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    FirebaseAuth.AuthStateListener authStateListener;
    DatabaseReference databaseReference;

    Button btnLogin;
    EditText txtEmail, txtPassword;
    TextView txtDaftar;

//    @Override
//    protected void onStart() {
//        super.onStart();
//        firebaseAuth.addAuthStateListener(authStateListener);
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        txtDaftar = findViewById(R.id.txtDaftar);

        txtDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(i);
            }
        });

//        authStateListener = new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                FirebaseUser user = firebaseAuth.getCurrentUser();
//                if(user!=null){
//                    databaseReference.child("users").child(user.getUid()).addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(DataSnapshot dataSnapshot) {
//                            try{
//                                User user = dataSnapshot.getValue(User.class);
//                                if(user.getRole().equals("Mahasiswa")){
//                                    Intent intent = new Intent(getApplicationContext(), HomeMahasiswaActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                    startActivity(intent);
//                                    finish();
//                                } else{
//                                    Intent intent = new Intent(getApplicationContext(), HomeDosenActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                    startActivity(intent);
//                                    finish();
//                                }
//                            } catch (Exception ex){
//                                System.out.println("Error checking logged in user : " + ex.toString());
//                            }
//                        }
//
//                        @Override
//                        public void onCancelled(DatabaseError databaseError) {
//
//                        }
//                    });
//                } else{
                    btnLogin.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            LoginUser();
                        }
                    });
//                }
//            }
//        };
    }

    private void LoginUser(){
        String email = txtEmail.getText().toString();
        String password = txtPassword.getText().toString();
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    String userId = task.getResult().getUser().getUid();
                    databaseReference.child("users").child(userId).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            User user = dataSnapshot.getValue(User.class);
                            String name = user.getNama_lengkap();
                            if(user.getRole().equals("Mahasiswa")){
                                Intent intent = new Intent(getApplicationContext(), HomeMahasiswaActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }
        });
    }
}
