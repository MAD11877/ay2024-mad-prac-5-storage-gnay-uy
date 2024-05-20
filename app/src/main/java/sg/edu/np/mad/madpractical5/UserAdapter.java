package sg.edu.np.mad.madpractical5;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserViewHolder> {

    private ArrayList<User> userList;

    public UserAdapter(ArrayList<User> userList, Context context) {
        this.userList = userList;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_activity_list, parent, false);
        return new UserViewHolder(item);
    }

    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {

        User currentUser = userList.get(position);
        holder.nameTextView.setText(currentUser.name);
        holder.descriptionTextView.setText(currentUser.description);
        holder.imageView.setOnClickListener(v -> {
            AlertDialog alertDialog = new AlertDialog.Builder(v.getContext())
                    .setTitle("Profile")
                    .setMessage(currentUser.name)
                    .setPositiveButton("CLOSE", (dialog, which) -> {
                        dialog.dismiss();
                    })
                    .setNegativeButton("VIEW", (dialog, which) -> {
                        Intent intent = new Intent(v.getContext(), MainActivity.class);
                        intent.putExtra("id", currentUser.id);
                        v.getContext().startActivity(intent);
                    })
                    .create();

            alertDialog.show();

            System.out.println("Image clicked");
        });

        if (currentUser.name.substring(currentUser.name.length() - 1).equals("7")) {
            int width = holder.squareImageView.getWidth();
            ViewGroup.LayoutParams params = holder.squareImageView.getLayoutParams();
            params.height = width;
            holder.squareImageView.setLayoutParams(params);

            holder.squareImageView.setVisibility(View.VISIBLE);

        }
    }

    @Override
    public int getItemCount() {
        System.out.println(userList.size());
        return userList.size();
    }
}