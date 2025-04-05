package com.example.syy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Database.db";
    private static final int DATABASE_VERSION = 2;

    private static final String TABLE_USERS = "users";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_ROLE = "role"; // "user" немесе "brand"

    // Brand-specific бағандар
    private static final String COLUMN_WATER_NAME = "water_name";
    private static final String COLUMN_COMPOSITION = "composition";
    private static final String COLUMN_SOURCE = "source";
    private static final String COLUMN_WATER_TYPE = "water_type";
    private static final String COLUMN_PHOTO_PATH = "photo_path";
    private static final String COLUMN_CERTIFICATE_PATH = "certificate_path";
    private static final String COLUMN_APPROVAL_STATUS = "approval_status"; // "pending", "approved", "rejected"

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_USERS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_EMAIL + " TEXT UNIQUE NOT NULL, " +
                COLUMN_PASSWORD + " TEXT NOT NULL, " +
                COLUMN_ROLE + " TEXT NOT NULL, " +
                COLUMN_WATER_NAME + " TEXT, " +
                COLUMN_COMPOSITION + " TEXT, " +
                COLUMN_SOURCE + " TEXT, " +
                COLUMN_WATER_TYPE + " TEXT, " +
                COLUMN_PHOTO_PATH + " TEXT, " +
                COLUMN_CERTIFICATE_PATH + " TEXT, " +
                COLUMN_APPROVAL_STATUS + " TEXT DEFAULT 'pending')";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Жаңа версия шықса, кестені жаңарту үшін
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    // Қарапайым қолданушыны қосу
    public boolean addUser(String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_PASSWORD, password);
        values.put(COLUMN_ROLE, "user");

        long result = db.insert(TABLE_USERS, null, values);
        db.close();
        return result != -1;
    }

    // Бренд қолданушыны қосу
    public boolean addBrand(String email, String password, String waterName, String composition,
                            String source, String waterType, String photoPath, String certificatePath) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_PASSWORD, password);
        values.put(COLUMN_ROLE, "brand");
        values.put(COLUMN_WATER_NAME, waterName);
        values.put(COLUMN_COMPOSITION, composition);
        values.put(COLUMN_SOURCE, source);
        values.put(COLUMN_WATER_TYPE, waterType);
        values.put(COLUMN_PHOTO_PATH, photoPath);
        values.put(COLUMN_CERTIFICATE_PATH, certificatePath);
        values.put(COLUMN_APPROVAL_STATUS, "pending");

        long result = db.insert(TABLE_USERS, null, values);
        db.close();
        return result != -1;
    }

    // Кіру тексерісі: қолданушы бар ма
    public boolean checkUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE email = ? AND password = ?",
                new String[]{email, password});
        boolean exists = cursor.moveToFirst();
        cursor.close();
        db.close();
        return exists;
    }

    // Қолданушының рөлін анықтау (user немесе brand)
    public String getUserRole(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT role FROM " + TABLE_USERS + " WHERE email = ?", new String[]{email});
        String role = null;
        if (cursor.moveToFirst()) {
            role = cursor.getString(0);
        }
        cursor.close();
        db.close();
        return role;
    }

    // Бренд мақұлдау күйін анықтау (pending, approved, rejected)
    public String getBrandStatus(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT approval_status FROM " + TABLE_USERS + " WHERE email = ?", new String[]{email});
        String status = null;
        if (cursor.moveToFirst()) {
            status = cursor.getString(0);
        }
        cursor.close();
        db.close();
        return status;
    }

    // Барлық күтудегі брендтер тізімі (admin)
    public Cursor getAllPendingBrands() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_USERS +
                " WHERE " + COLUMN_ROLE + "='brand' AND " + COLUMN_APPROVAL_STATUS + "='pending'";
        return db.rawQuery(query, null);
    }

    // Админ мақұлдауы немесе қабылдамауы
    public boolean updateApprovalStatus(int brandId, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_APPROVAL_STATUS, status);

        int rows = db.update(TABLE_USERS, values, COLUMN_ID + "=?",
                new String[]{String.valueOf(brandId)});
        db.close();
        return rows > 0;
    }

    // Сертификат және фото жолын алу (қажет болса)
    public Cursor getBrandMediaPaths(int brandId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + COLUMN_PHOTO_PATH + ", " + COLUMN_CERTIFICATE_PATH +
                " FROM " + TABLE_USERS + " WHERE " + COLUMN_ID + "=?";
        return db.rawQuery(query, new String[]{String.valueOf(brandId)});
    }
}
