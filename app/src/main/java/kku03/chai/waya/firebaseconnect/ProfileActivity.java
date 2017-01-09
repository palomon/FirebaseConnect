package kku03.chai.waya.firebaseconnect;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;
    private TextView textView;
    private Button saveButton,logoutButton,loadButton;

    private DatabaseReference databaseReference;
    private EditText nameEditText,phoneEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(this,LoginActivity.class));
        }

        databaseReference = FirebaseDatabase.getInstance().getReference();

        nameEditText = (EditText)findViewById(R.id.editTextName);
        phoneEditText =  (EditText)findViewById(R.id.editTextPhone);

        FirebaseUser user = firebaseAuth.getCurrentUser();

        textView = (TextView)findViewById(R.id.textView2);
        saveButton = (Button)findViewById(R.id.buttonSave);
        logoutButton = (Button)findViewById(R.id.buttonLogout);
        loadButton = (Button)findViewById(R.id.buttonLoad);

        textView.setText(user.getEmail());

        saveButton.setOnClickListener(this);
        logoutButton.setOnClickListener(this);
        loadButton.setOnClickListener(this);
    }

    private void saveUserInfo(){
        String name = nameEditText.getText().toString().trim();
        String phone = phoneEditText.getText().toString().trim();

        UserInfo userInfo = new UserInfo(name,phone);

        FirebaseUser user = firebaseAuth.getCurrentUser();
        databaseReference.child("users").child(user.getUid()).setValue(userInfo);

        Toast.makeText(this,"Saved..",Toast.LENGTH_SHORT).show();

    }

    private void loadUserInfo(){
        FirebaseUser user = firebaseAuth.getCurrentUser();
        databaseReference.child("users").child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                UserInfo post = dataSnapshot.getValue(UserInfo.class);
                nameEditText.setText(post.name);
                phoneEditText.setText(post.phone);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v == saveButton){
            saveUserInfo();
        }
        if(v == logoutButton){
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this , LoginActivity.class));
        }
        if(v == loadButton){
            loadUserInfo();
        }
    }
}
