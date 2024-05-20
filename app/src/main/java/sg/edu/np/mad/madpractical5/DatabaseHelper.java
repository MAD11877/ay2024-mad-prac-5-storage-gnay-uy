package sg.edu.np.mad.madpractical5;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Random;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "userDatabase";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_USERS = "users";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_FOLLOWED = "followed";

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_USERS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME + " TEXT, " +
                    COLUMN_DESCRIPTION + " TEXT, " +
                    COLUMN_FOLLOWED + " INTEGER" + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Random random = new Random();
        db.execSQL(TABLE_CREATE);
        // Insert 20 dummy entries
        for (int i = 1; i <= 20; i++) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_NAME, "User " + random.nextInt());
            values.put(COLUMN_DESCRIPTION, "Description for User HELLO FROM SQLITE");
            values.put(COLUMN_FOLLOWED, i % 2); // Alternating between 0 and 1 for 'followed' status
            db.insert(TABLE_USERS, null, values);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    public ContentValues userToValues(User user) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, user.name);
        values.put(COLUMN_DESCRIPTION, user.description);
        values.put(COLUMN_FOLLOWED, user.followed ? 1 : 0);
        return values;
    }

    public int updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.update(TABLE_USERS, userToValues(user), COLUMN_ID + " = ?", new String[]{String.valueOf(user.id)});
    }

    @SuppressLint("Range")
    public ArrayList<User> getUsers() {
        ArrayList<User> userList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, new String[]{COLUMN_ID, COLUMN_NAME, COLUMN_DESCRIPTION, COLUMN_FOLLOWED}, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                user.name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                user.description = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION));
                user.followed = cursor.getInt(cursor.getColumnIndex(COLUMN_FOLLOWED)) == 1;
                userList.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return userList;
    }
}
