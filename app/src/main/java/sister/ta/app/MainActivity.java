package sister.ta.app;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import sister.ta.app.model.User;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    FirebaseAuth.AuthStateListener authStateListener;
    int status=0;

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuthh) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
            if(user!=null){
                databaseReference.child("users").child(user.getUid()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        try{
                            User user = dataSnapshot.getValue(User.class);
                            System.out.println(user.getRole());
                            if(user.getRole().equals("Mahasiswa")){
                                System.out.println("Status doInBackground : 1");
                                status=1;
                                    Intent intent = new Intent(getApplicationContext(), HomeMahasiswaActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                    finish();
                            } else{
                                System.out.println("Status doInBackground : 2");
                                status=2;
                                    Intent intent = new Intent(getApplicationContext(), HomeDosenActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                    finish();
                            }
                        } catch (Exception ex){
                            System.out.println("Error checking logged in user : " + ex.toString());
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            } else{
                System.out.println("Belum login");
                System.out.println("Status doInBackground : 0");
                status=0;
                    Intent i = new Intent(getApplicationContext(), LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    finish();
            }
//                AsyncTask asyncTask = new AsyncTask(firebaseAuth, databaseReference);
//                asyncTask.execute();
            }
        };
    }

//    class AsyncTask extends android.os.AsyncTask<Void, Void, Void> {
//        FirebaseAuth firebaseAuth;
//        DatabaseReference databaseReference;
//        FirebaseAuth.AuthStateListener authStateListener;
//
//        public AsyncTask(FirebaseAuth firebaseAuth, DatabaseReference databaseReference) {
//            this.firebaseAuth = firebaseAuth;
//            this.databaseReference = databaseReference;
//        }
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//        }
//
//        @Override
//        protected Void doInBackground(Void... voids) {
//            FirebaseUser user = firebaseAuth.getCurrentUser();
//            if(user!=null){
//                databaseReference.child("users").child(user.getUid()).addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        try{
//                            User user = dataSnapshot.getValue(User.class);
//                            System.out.println(user.getRole());
//                            if(user.getRole().equals("Mahasiswa")){
//                                System.out.println("Status doInBackground : 1");
//                                status=1;
////                                    Intent intent = new Intent(getApplicationContext(), HomeMahasiswaActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
////                                    startActivity(intent);
////                                    finish();
//                            } else{
//                                System.out.println("Status doInBackground : 2");
//                                status=2;
////                                    Intent intent = new Intent(getApplicationContext(), HomeDosenActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
////                                    startActivity(intent);
////                                    finish();
//                            }
//                        } catch (Exception ex){
//                            System.out.println("Error checking logged in user : " + ex.toString());
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//
//                    }
//                });
//            } else{
//                System.out.println("Belum login");
//                System.out.println("Status doInBackground : 0");
//                status=0;
////                    Intent i = new Intent(getApplicationContext(), LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
////                    startActivity(i);
////                    finish();
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void result) {
//            super.onPostExecute(result);
//            System.out.println("Status : " + status);
//            if(status==0){
//                Intent i = new Intent(getApplicationContext(), LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(i);
//                finish();
//            } else if(status==1){
//                Intent intent = new Intent(getApplicationContext(), HomeMahasiswaActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent);
//                finish();
//            } else if(status==2){
//                Intent intent = new Intent(getApplicationContext(), HomeDosenActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent);
//                finish();
//            }
//        }
//    }
}
