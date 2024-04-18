package adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.civiladvocacy.MainActivity;

import com.example.civiladvocacy.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import Model.Candidates;

public class candidate_data_adapter extends RecyclerView.Adapter<candidate_data_adapter.ViewHolder> {
    List<Candidates> candidate_list;
    MainActivity main_act;
    public candidate_data_adapter(MainActivity main_act, List<Candidates> candidate_list) {
        this.main_act = main_act;
        this.candidate_list = candidate_list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item_candidate, parent, false);
        view.setOnClickListener(main_act);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.position.setText(candidate_list.get(position).getPosition());
        holder.candidate_name.setText(String.format("%s (%s)", candidate_list.get(position).getCandidate_name(), candidate_list.get(position).getPol_party()));
        if(candidate_list.get(position).getDp_link()!=null && !candidate_list.get(position).getDp_link().isEmpty())
        {
            Picasso.get().load(candidate_list.get(position).getDp_link()).error(R.drawable.brokenimage).into(holder.dp_icon);
        }
    }

    @Override
    public int getItemCount() {
        return candidate_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView candidate_name;
        ImageView dp_icon;
        TextView position;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            position = itemView.findViewById(R.id.candidate_position);
            candidate_name = itemView.findViewById(R.id.candidate_name);
            dp_icon = itemView.findViewById(R.id.display_pic);
        }
    }


}
