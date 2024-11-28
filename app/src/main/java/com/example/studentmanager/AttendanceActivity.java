package com.example.studentmanager;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AttendanceActivity extends AppCompatActivity {
    private EditText edtAttendanceTime;
    private ListView lvAttendanceStudents;
    private Button btnConfirmAttendance;
    private DatabaseHelper dbHelper;
    private List<Student> studentList;
    private List<String> selectedStudents = new ArrayList<>();
    private String attendanceTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        dbHelper = new DatabaseHelper(this);

        edtAttendanceTime = findViewById(R.id.edtAttendanceTime);
        lvAttendanceStudents = findViewById(R.id.lvAttendanceStudents);
        btnConfirmAttendance = findViewById(R.id.btnConfirmAttendance);

        edtAttendanceTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(AttendanceActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                                edtAttendanceTime.setText(String.format("%02d:%02d", selectedHour, selectedMinute));
                                attendanceTime = String.format("%02d:%02d", selectedHour, selectedMinute);
                            }
                        }, hour, minute, true);
                mTimePicker.show();
            }
        });

        studentList = dbHelper.getAllStudents();
        ArrayAdapter<Student> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_multiple_choice,
                studentList
        );
        lvAttendanceStudents.setAdapter(adapter);
        lvAttendanceStudents.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        btnConfirmAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (attendanceTime == null || attendanceTime.isEmpty()) {
                    Toast.makeText(AttendanceActivity.this,
                            "Vui lòng chọn thời gian điểm danh",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                selectedStudents.clear();
                for (int i = 0; i < lvAttendanceStudents.getCount(); i++) {
                    if (lvAttendanceStudents.isItemChecked(i)) {
                        selectedStudents.add(studentList.get(i).getMaSV());
                    }
                }

                if (!selectedStudents.isEmpty()) {
                    String result = "Điểm danh lúc " + attendanceTime +
                            " cho các sinh viên: " + selectedStudents.toString();
                    Toast.makeText(AttendanceActivity.this, result,
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(AttendanceActivity.this,
                            "Chưa chọn sinh viên để điểm danh",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
