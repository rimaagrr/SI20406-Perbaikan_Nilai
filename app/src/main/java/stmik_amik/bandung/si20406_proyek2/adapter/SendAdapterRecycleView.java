package stmik_amik.bandung.si20406_proyek2.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import stmik_amik.bandung.si20406_proyek2.CreateActivity;
import stmik_amik.bandung.si20406_proyek2.R;
import stmik_amik.bandung.si20406_proyek2.model.DataM;

public class SendAdapterRecycleView extends RecyclerView.Adapter<SendAdapterRecycleView.MyViewHolder> {

    private List<DataM> listItem;
    private Activity mActivity;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout linearLayout;
        public TextView list_name, list_npm;

        public MyViewHolder(View view) {
            super(view);
            linearLayout = view.findViewById(R.id.data_list);
            list_name = view.findViewById(R.id.list_name);
            list_npm = view.findViewById(R.id.list_npm);
        }
    }

    public SendAdapterRecycleView(List<DataM> listItem, Activity activity) {
        this.listItem = listItem;
        this.mActivity = activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final DataM dataM = listItem.get(position);

        holder.list_name.setText(dataM.getName());
        holder.list_npm.setText(dataM.getNpm());

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent goDetail = new Intent(mActivity, CreateActivity.class);
                goDetail.putExtra("id", dataM.getKey());
                goDetail.putExtra("name", dataM.getName());
                goDetail.putExtra("npm", dataM.getNpm());

                mActivity.startActivity(goDetail);

            }
        });

    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }


}
