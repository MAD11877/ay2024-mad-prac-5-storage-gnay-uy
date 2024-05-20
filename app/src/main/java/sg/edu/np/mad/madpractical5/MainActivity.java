package sg.edu.np.mad.madpractical5;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        int userId = intent.getIntExtra("id", 0);
//        User user = ListActivity.userList.stream()
//                .filter(u -> u.id == userId)
//                .findFirst()
//                .orElse(new User("John Doe", "MAD Developer", 0, false));

        User user = null;
        for (User u : ListActivity.userList) {
            if (u.id == userId) {
                user = u;
                break;
            }
        }
        if (user == null) {
            user = new User("John Doe", "MAD Developer", 0, false);
        }

        TextView name = findViewById(R.id.textView2);
        TextView desc = findViewById(R.id.textView3);
        Button followBtn = findViewById(R.id.Button1);

        followBtn.setText(user.followed ? "UNFOLLOW" : "FOLLOW");
        name.setText(user.name);
        desc.setText(user.description);

        Random random = new Random();
        DatabaseHandler db = new DatabaseHandler(this);

        User finalUser = user;
        findViewById(R.id.Button1).setOnClickListener(v -> {
            boolean isFollowed = finalUser.followed;
            finalUser.followed = !isFollowed;
            db.updateUser(finalUser); // Update the database

            if (!finalUser.followed) {
                ((Button) v).setText("UNFOLLOW");
                Toast.makeText(this, "Followed", Toast.LENGTH_SHORT).show();
            } else {
                ((Button) v).setText("FOLLOW");
                Toast.makeText(this, "Unfollowed", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.imageView1).setOnClickListener(v -> {
            AlertDialog alertDialog = new AlertDialog.Builder(this)
                    .setTitle("Profile")
                    .setMessage("MADness")
                    .setPositiveButton("CLOSE", (dialog, which) -> {
                        dialog.dismiss();
                    })
                    .setNegativeButton("VIEW", (dialog, which) -> {
                        String text = "MAD " + Math.abs(random.nextInt());
                        name.setText(text);
                    })
                    .create();

            alertDialog.show();
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}