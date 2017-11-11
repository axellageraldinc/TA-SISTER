package sister.ta.app;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by axellageraldinc on 11/11/17.
 */

public class HomeListAdapter extends RecyclerView.Adapter<HomeListAdapter.ItemViewHolder> {
    @Override
    public HomeListAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(HomeListAdapter.ItemViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        public ItemViewHolder(View itemView) {
            super(itemView);
        }
    }
}
