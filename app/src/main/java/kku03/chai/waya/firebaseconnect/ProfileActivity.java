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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;
    private TextView textView;
    private Button saveButton,logoutButton;

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

        textView.setText(user.getEmail());

        saveButton.setOnClickListener(this);
        logoutButton.setOnClickListener(this);

    }

    private void saveUserInfo(){
        String name = nameEditText.getText().toString().trim();
        String phone = phoneEditText.getText().toString().trim();

        UserInfo userInfo = new UserInfo(name,phone);

        FirebaseUser user = firebaseAuth.getCurrentUser();
        databaseReference.child(user.getUid()).setValue(userInfo);

        Toast.makeText(this,"Saved..",Toast.LENGTH_SHORT).show();

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
    }
}
