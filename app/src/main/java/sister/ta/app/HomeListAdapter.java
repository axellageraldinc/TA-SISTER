package sister.ta.app;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import sister.ta.app.model.User;

/**
 * Created by axellageraldinc on 11/11/17.
 */

public class HomeListAdapter extends RecyclerView.Adapter<HomeListAdapter.ItemViewHolder> {

    Context context;
    List<User> userList = new ArrayList<>();

    public HomeListAdapter(Context context, List<User> userList){
        this.context = context;
        this.userList = userList;
    }

    @Override
    public HomeListAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_list_adapter, parent, false);
        ItemViewHolder itemViewHolder = new ItemViewHolder(v);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(HomeListAdapter.ItemViewHolder holder, int position) {
        holder.txtNamaDosen.setText(userList.get(position).getNama_lengkap());
        String status = userList.get(position).getStatus();
        String posisi = null;
        if(status.equals("no"))
            posisi = "Tidak di kampus";
        else if(status.equals("yes"))
            posisi = "Di kampus";
        else
            posisi = "Di " + status;
        if(posisi.equals("Tidak di kampus"))
            holder.txtStatus.setTextColor(ContextCompat.getColor(context, R.color.colorRed));
        else
            holder.txtStatus.setTextColor(ContextCompat.getColor(context, R.color.colorGreen));
        holder.txtStatus.setText(posisi);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView txtNamaDosen, txtStatus;
        public ItemViewHolder(View itemView) {
            super(itemView);
            txtNamaDosen = itemView.findViewById(R.id.txtNamaDosen);
            txtStatus = itemView.findViewById(R.id.txtStatus);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println("Clicked item : " + txtNamaDosen.getText().toString());
                }
            });
        }
    }
}
