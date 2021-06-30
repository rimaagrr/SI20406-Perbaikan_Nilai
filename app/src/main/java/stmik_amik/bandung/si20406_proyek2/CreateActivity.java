package stmik_amik.bandung.si20406_proyek2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import stmik_amik.bandung.si20406_proyek2.model.DataM;

public class CreateActivity extends AppCompatActivity {

    private static final String TAG = "SI20406-Proyek2";
    private DatabaseReference database;

    private Toolbar myToolbar;

    private EditText etNpm, etName;
    private ProgressBar loading;
    private Button btn_cancel, btn_save;
    private TextView back;

    private AlertDialog alertDialog;

    private String sPid, sPname, sPnpm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        // Write a message to the database
        database = FirebaseDatabase.getInstance("https://si20406-proyek2-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();

        sPid = getIntent().getStringExtra("id");
        sPnpm = getIntent().getStringExtra("npm");
        sPname = getIntent().getStringExtra("name");

        etNpm = findViewById(R.id.npm);
        etName = findViewById(R.id.name);
        btn_save = findViewById(R.id.save);
        btn_cancel = findViewById(R.id.cancel);

        back = findViewById(R.id.back);

        loading = findViewById(R.id.simpleProgressBar);


        etNpm.setText(sPnpm);
        etName.setText(sPname);

        if (sPid.equals("")){
            btn_save.setText("Save");
            btn_cancel.setVisibility(View.GONE);
            myToolbar.setTitle("Add Data");
        } else {
            btn_save.setText("Edit");
            btn_cancel.setText("Delete");
            myToolbar.setTitle("Edit/Delete Data");
        }

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Snpm = etNpm.getText().toString();
                String Sname = etName.getText().toString();

                if (btn_save.getText().equals("Save")){

                    //PERINTAH SAVE
                    if(Snpm.equals("")){
                        etNpm.setError("Silahkan masukan NPM");
                        etNpm.requestFocus();
                    } else if(Sname.equals("")){
                        etName.setError("Silahkan masukan Nama");
                        etName.requestFocus();
                    } else {
                        loading.setVisibility(View.VISIBLE);

                        submitUser(new DataM(
                                Snpm.toLowerCase(),
                                Sname.toLowerCase()));
                        finish();
                    }
                } else {

                    //PERINTAH EDIT
                    if(Snpm.equals("")){
                        etNpm.setError("Silahkan masukan NPM");
                        etNpm.requestFocus();
                    } else if(Sname.equals("")){
                        etName.setError("Silahkan masukan Nama");
                        etName.requestFocus();
                    } else {
                        loading.setVisibility(View.VISIBLE);

                        editUser(new DataM(
                                Snpm.toLowerCase(),
                                Sname.toLowerCase()),
                                sPid);
                        finish();
                    }

                }

            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (btn_cancel.getText().equals("Cancel")) {
                    //tutup page
                    finish();
                } else {
                    // delete
                    showDialog();

                }

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void submitUser(DataM dataM){
        database.child("Data").push().setValue(dataM).addOnSuccessListener(CreateActivity.this, (OnSuccessListener) (aVoid) -> {

            loading.setVisibility(View.INVISIBLE);

            etName.setText("");
            etNpm.setText("");

            Toast.makeText(this, "Data saved successfully", Toast.LENGTH_SHORT).show();

        });
    }

    private void editUser(DataM dataM, String id) {
        database.child("Data")
                .child(id)
                .setValue(dataM)
                .addOnSuccessListener(this, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        loading.setVisibility(View.INVISIBLE);

                        etName.setText("");
                        etNpm.setText("");

                        Toast.makeText(CreateActivity.this,
                                "Data edited successfully",
                                Toast.LENGTH_SHORT).show();
                    }

                });
    }

    private void showDialog(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set title dialog
        alertDialogBuilder.setTitle("Delete this data?");

        // set pesan dari dialog
        alertDialogBuilder
//                .setMessage("Klik Ya untuk keluar!")
//                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        database.child("Data").child(sPid).removeValue().addOnSuccessListener((OnSuccessListener) (aVoid) ->{

                            Toast.makeText(CreateActivity.this, "Data has been deleted ", Toast.LENGTH_SHORT).show();
                            finish();
                        });
                    }
                })
                .setNegativeButton("No",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // jika tombol ini diklik, akan menutup dialog
                        // dan tidak terjadi apa2
                        dialog.cancel();
                    }
                });

        // membuat alert dialog dari builder
        AlertDialog alertDialog = alertDialogBuilder.create();

        // menampilkan alert dialog
        alertDialog.show();
    }
}