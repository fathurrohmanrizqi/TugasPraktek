package digitalent.rizqifathurrohman.tugaspraktek.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import digitalent.rizqifathurrohman.tugaspraktek.database.DatabaseHelper;
import digitalent.rizqifathurrohman.tugaspraktek.model.User;

public class UserRepository {
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;
    private Context context;

    public UserRepository(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    public void open() {
        database = dbHelper.getWritableDatabase();
        if (database != null && database.isOpen()) {
            Log.d("Database Connected", "open: Successfully connected to database");
        } else {
            Toast.makeText(context, "Failed to connect to the database", Toast.LENGTH_SHORT).show();
        }
    }

    public void close() {
        if (database != null && database.isOpen()) {
            database.close();
        }
        dbHelper.close();
    }

    public long createUser(String name, String domisili, String kelamin) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_NAME, name);
        values.put(DatabaseHelper.COLUMN_DOMISILI, domisili);
        values.put(DatabaseHelper.COLUMN_KELAMIN, kelamin);
        return database.insert(DatabaseHelper.TABLE_USER, null, values);
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = database.query(DatabaseHelper.TABLE_USER,
                    new String[]{DatabaseHelper.COLUMN_ID, DatabaseHelper.COLUMN_NAME, DatabaseHelper.COLUMN_DOMISILI, DatabaseHelper.COLUMN_KELAMIN, DatabaseHelper.COLUMN_NPM, DatabaseHelper.COLUMN_TTL},
                    null, null, null, null, null);

            if (cursor != null && cursor.getCount() > 0) {
                int idIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_ID);
                int nameIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME);
                int domisiliIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_DOMISILI);
                int kelaminIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_KELAMIN);
                int npmIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_NPM);
                int ttlIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_TTL);

                if (idIndex != -1 && nameIndex != -1 && domisiliIndex != -1 && kelaminIndex != -1) {
                    cursor.moveToFirst();
                    do {
                        long id = cursor.getLong(idIndex);
                        String name = cursor.getString(nameIndex);
                        String domisili = cursor.getString(domisiliIndex);
                        String kelamin = cursor.getString(kelaminIndex);
                        String npm = cursor.getString(kelaminIndex);
                        String ttl = cursor.getString(kelaminIndex);
                        users.add(new User(id, name, domisili, kelamin, npm, ttl));
                    } while (cursor.moveToNext());
                } else {
                    Log.e("UserRepository", "One or more columns are missing in the cursor");
                }
            } else {
                Log.e("UserRepository", "Cursor is null or empty");
            }
        } catch (Exception e) {
            Log.e("UserRepository", "Error while fetching users: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return users;
    }

    public User getUserById(long id) {
        User user = null;
        Cursor cursor = null;
        try {
            cursor = database.query(DatabaseHelper.TABLE_USER,
                    new String[]{DatabaseHelper.COLUMN_ID, DatabaseHelper.COLUMN_NAME, DatabaseHelper.COLUMN_DOMISILI, DatabaseHelper.COLUMN_KELAMIN, DatabaseHelper.COLUMN_NPM, DatabaseHelper.COLUMN_TTL},
                    DatabaseHelper.COLUMN_ID + " = ?",
                    new String[]{String.valueOf(id)},
                    null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                int idIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_ID);
                int nameIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME);
                int domisiliIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_DOMISILI);
                int kelaminIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_KELAMIN);
                int npmIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_NPM);
                int ttlIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_TTL);

                if (idIndex != -1 && nameIndex != -1 && domisiliIndex != -1 && kelaminIndex != -1 && npmIndex != -1 && ttlIndex != -1) {
                    long userId = cursor.getLong(idIndex);
                    String name = cursor.getString(nameIndex);
                    String domisili = cursor.getString(domisiliIndex);
                    String kelamin = cursor.getString(kelaminIndex);
                    String npm = cursor.getString(npmIndex);
                    String ttl = cursor.getString(ttlIndex);
                    user = new User(userId, name, domisili, kelamin, npm, ttl);
                } else {
                    Log.e("UserRepository", "One or more columns are missing in the cursor");
                }
            } else {
                Log.e("UserRepository", "Cursor is null or empty");
            }
        } catch (Exception e) {
            Log.e("UserRepository", "Error while fetching user by ID: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return user;
    }

    public int updateUser(long id, String name, String domisili, String kelamin, String npm, String ttl) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_NAME, name);
        values.put(DatabaseHelper.COLUMN_DOMISILI, domisili);
        values.put(DatabaseHelper.COLUMN_KELAMIN, kelamin);
        values.put(DatabaseHelper.COLUMN_NPM, npm);
        values.put(DatabaseHelper.COLUMN_TTL, ttl);
        return database.update(DatabaseHelper.TABLE_USER, values,
                DatabaseHelper.COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)});
    }

    public void deleteUser(long id) {
        database.delete(DatabaseHelper.TABLE_USER,
                DatabaseHelper.COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)});
    }
}

