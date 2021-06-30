package stmik_amik.bandung.si20406_proyek2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText etUsername, etPassowrd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        etUsername = findViewById(R.id.et_username);
        etPassowrd = findViewById(R.id.et_password);

        Button btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goLogin = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(goLogin);
                finish();
            }
        });

        FloatingActionButton fabRegister = findViewById(R.id.fab_register);
        fabRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = etUsername.getText().toString();
                String password = etPassowrd.getText().toString();

                if (username.equals("")) {
                    Toast.makeText(RegisterActivity.this, "Silahkan isi username Anda",
                            Toast.LENGTH_SHORT).show();
                } else if (password.equals("")) {
                    Toast.makeText(RegisterActivity.this, "Silahkan isi password Anda",
                            Toast.LENGTH_SHORT).show();
                } else {

                    mAuth.createUserWithEmailAndPassword(username, password)
                            .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        FirebaseUser user = mAuth.getCurrentUser();

                                        InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                                        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

                                        Toast.makeText(RegisterActivity.this, "Authentication success.",
                                                Toast.LENGTH_SHORT).show();
                                        Intent goLogin = new Intent(RegisterActivity.this, LoginActivity.class);
                                        goLogin.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                        startActivity(goLogin);


                                    } else {
                                        // If sign in fails, display a message to the user.
                                        InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                                        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

                                        Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();

                                    }

                                    // ...
                                }
                            });
                }

            }
        });


    }

    @Override
    public void onBackPressed() {
        Intent goLogin = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(goLogin);
        finish();
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }
}
