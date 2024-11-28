package com.example.studentmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private DatabaseHelper dbHelper;
    private ListView lvStudents;
    private List<Student> studentList;
    private ArrayAdapter<Student> adapter;
    private Button btnAddStudent, btnAttendance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);
        lvStudents = findViewById(R.id.lvStudents);
        btnAddStudent = findViewById(R.id.btnAddStudent);
        btnAttendance = findViewById(R.id.btnAttendance);

        loadStudentList();

        btnAddStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddEditStudentActivity.class);
                startActivity(intent);
            }
        });

        btnAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AttendanceActivity.class);
                startActivity(intent);
            }
        });

        lvStudents.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Student selectedStudent = studentList.get(position);
                Intent intent = new Intent(MainActivity.this, AddEditStudentActivity.class);
                intent.putExtra("MASV", selectedStudent.getMaSV());
                intent.putExtra("TENSV", selectedStudent.getTenSV());
                intent.putExtra("MALOP", selectedStudent.getMaLop());
                startActivity(intent);
            }
        });

        lvStudents.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Student selectedStudent = studentList.get(position);
                dbHelper.deleteStudent(selectedStudent.getMaSV());
                loadStudentList();
                Toast.makeText(MainActivity.this,
                        "Đã xóa sinh viên: " + selectedStudent.getTenSV(),
                        Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    private void loadStudentList() {
        studentList = dbHelper.getAllStudents();
        adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                studentList
        );
        lvStudents.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadStudentList();
    }
}