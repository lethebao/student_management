package com.example.studentmanager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddEditStudentActivity extends AppCompatActivity {
    private EditText edtMaSV, edtTenSV, edtMaLop;
    private Button btnSave;
    private DatabaseHelper dbHelper;
    private boolean isEditMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_student);

        dbHelper = new DatabaseHelper(this);

        edtMaSV = findViewById(R.id.edtMaSV);
        edtTenSV = findViewById(R.id.edtTenSV);
        edtMaLop = findViewById(R.id.edtMaLop);
        btnSave = findViewById(R.id.btnSave);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            isEditMode = true;
            edtMaSV.setText(extras.getString("MASV"));
            edtTenSV.setText(extras.getString("TENSV"));
            edtMaLop.setText(extras.getString("MALOP"));

            edtMaSV.setEnabled(false);
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveStudent();
            }
        });
    }

    private void saveStudent() {
        String maSV = edtMaSV.getText().toString().trim();
        String tenSV = edtTenSV.getText().toString().trim();
        String maLop = edtMaLop.getText().toString().trim();

        if (maSV.isEmpty() || tenSV.isEmpty() || maLop.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            if (isEditMode) {
                int result = dbHelper.updateStudent(maSV, tenSV, maLop);
                if (result > 0) {
                    Toast.makeText(this, "Cập nhật thành công",
                            Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(this, "Cập nhật thất bại",
                            Toast.LENGTH_SHORT).show();
                }
            } else {
                long result = dbHelper.addStudent(maSV, tenSV, maLop);
                if (result != -1) {
                    Toast.makeText(this, "Thêm sinh viên thành công",
                            Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(this, "Thêm sinh viên thất bại",
                            Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception e) {
            Toast.makeText(this, "Lỗi: " + e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
    }
}
