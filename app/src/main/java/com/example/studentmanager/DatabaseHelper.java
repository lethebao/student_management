package com.example.studentmanager;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "qlsinhvien.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_SINHVIEN = "TableSinhvien";
    public static final String COLUMN_MASV = "masv";
    public static final String COLUMN_TENSV = "tensv";
    public static final String COLUMN_MALOP = "malop";

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_SINHVIEN + " (" +
                    COLUMN_MASV + " TEXT PRIMARY KEY, " +
                    COLUMN_TENSV + " TEXT, " +
                    COLUMN_MALOP + " TEXT)";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
        insertSampleData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SINHVIEN);
        onCreate(db);
    }

    private void insertSampleData(SQLiteDatabase db) {
        ContentValues values = new ContentValues();

        String[][] sampleStudents = {
                {"SV001", "Nguyễn Văn A", "CNTT01"},
                {"SV002", "Trần Thị B", "CNTT02"},
                {"SV003", "Lê Văn C", "KTPM01"},
                {"SV004", "Phạm Thị D", "KTPM02"},
                {"SV005", "Hoàng Văn E", "CNTT03"}
        };

        for (String[] student : sampleStudents) {
            values.put(COLUMN_MASV, student[0]);
            values.put(COLUMN_TENSV, student[1]);
            values.put(COLUMN_MALOP, student[2]);
            db.insert(TABLE_SINHVIEN, null, values);
            values.clear();
        }
    }

    public long addStudent(String maSV, String tenSV, String maLop) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_MASV, maSV);
        values.put(COLUMN_TENSV, tenSV);
        values.put(COLUMN_MALOP, maLop);
        return db.insert(TABLE_SINHVIEN, null, values);
    }

    public List<Student> getAllStudents() {
        List<Student> studentList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_SINHVIEN;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        int masvIndex = cursor.getColumnIndex(COLUMN_MASV);
        int tensvIndex = cursor.getColumnIndex(COLUMN_TENSV);
        int malopIndex = cursor.getColumnIndex(COLUMN_MALOP);

        if (masvIndex == -1 || tensvIndex == -1 || malopIndex == -1) {
            cursor.close();
            return studentList;
        }

        if (cursor.moveToFirst()) {
            do {
                Student student = new Student(
                        cursor.getString(masvIndex),
                        cursor.getString(tensvIndex),
                        cursor.getString(malopIndex)
                );
                studentList.add(student);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return studentList;
    }

    public int updateStudent(String maSV, String tenSV, String maLop) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TENSV, tenSV);
        values.put(COLUMN_MALOP, maLop);

        return db.update(TABLE_SINHVIEN, values,
                COLUMN_MASV + " = ?", new String[]{maSV});
    }

    public void deleteStudent(String maSV) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_SINHVIEN,
                COLUMN_MASV + " = ?", new String[]{maSV});
    }
}
