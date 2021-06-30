package stmik_amik.bandung.si20406_proyek2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import stmik_amik.bandung.si20406_proyek2.adapter.SendAdapterRecycleView;
import stmik_amik.bandung.si20406_proyek2.model.DataM;

public class HomeActivity extends AppCompatActivity {

    private DatabaseReference database;

    private ArrayList<DataM> daftarItem;
    private SendAdapterRecycleView sendAdapterRecycleView;

    private RecyclerView rv_list;
    private ProgressBar loading;

    private FloatingActionButton fab_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        database = FirebaseDatabase.getInstance("https://si20406-proyek2-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();

        rv_list = findViewById(R.id.list_item);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rv_list.setLayoutManager(mLayoutManager);
        rv_list.setItemAnimator(new DefaultItemAnimator());

        loading = findViewById(R.id.simpleProgressBar);
        loading.setVisibility(View.VISIBLE);

        database.child("Data").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                daftarItem = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    /**
                     * Mapping data pada DataSnapshot ke dalam object Wisata
                     * Dan juga menyimpan primary key pada object Wisata
                     * untuk keperluan Edit dan Delete data
                     */
                    DataM dataM = dataSnapshot.getValue(DataM.class);
                    dataM.setKey(dataSnapshot.getKey());

                    /**
                     * Menambahkan object Wisata yang sudah dimapping
                     * ke dalam ArrayList
                     */
                    daftarItem.add(dataM);
                }

                /**
                 * Inisialisasi adapter dan data hotel dalam bentuk ArrayList
                 * dan mengeset Adapter ke dalam RecyclerView
                 */
                sendAdapterRecycleView = new SendAdapterRecycleView(daftarItem, HomeActivity.this);
                rv_list.setAdapter(sendAdapterRecycleView);

                loading.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                /**
                 * Kode ini akan dipanggil ketika ada error dan
                 * pengambilan data gagal dan memprint error nya
                 * ke LogCat
                 */
                System.out.println(error.getDetails()+" "+error.getMessage());
                loading.setVisibility(View.INVISIBLE);
            }
        });

        fab_add = findViewById(R.id.fab_add);
        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, CreateActivity.class)
                        .putExtra("id", "")
                        .putExtra("name", "")
                        .putExtra("npm", ""));
            }
        });
    }
}