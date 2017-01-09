package kku03.chai.waya.firebaseconnect;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;
    private TextView textView;
    private Button saveButton,logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(this,LoginActivity.class));
        }

        FirebaseUser user = firebaseAuth.getCurrentUser();

        textView = (TextView)findViewById(R.id.textView2);
        saveButton = (Button)findViewById(R.id.buttonSave);
        logoutButton = (Button)findViewById(R.id.buttonLogout);

        textView.setText(user.getEmail());

        saveButton.setOnClickListener(this);
        logoutButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v == saveButton){

        }
        else if(v == logoutButton){
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this , LoginActivity.class));
        }
    }
}
