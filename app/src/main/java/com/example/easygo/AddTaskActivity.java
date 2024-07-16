////package com.example.easygo;
////
////import static android.content.ContentValues.TAG;
////
////import androidx.annotation.NonNull;
////import androidx.appcompat.app.AppCompatActivity;
////
////import android.os.Bundle;
////import android.util.Log;
////import android.view.MenuItem;
////import android.view.View;
////import android.widget.Button;
////import android.widget.EditText;
////import android.widget.Toast;
////
////import com.example.easygo.model.TaskModel;
////import com.google.android.gms.tasks.OnFailureListener;
////import com.google.android.gms.tasks.OnSuccessListener;
////import com.google.firebase.auth.FirebaseAuth;
////import com.google.firebase.firestore.DocumentReference;
////import com.google.firebase.firestore.FirebaseFirestore;
////
////public class AddTaskActivity extends AppCompatActivity {
////
////    EditText etTaskInput;
////    Button saveBtn;
////    FirebaseFirestore db;
////    String TAG="EasyGO";
////    @Override
////    protected void onCreate(Bundle savedInstanceState) {
////        super.onCreate(savedInstanceState);
////        setContentView(R.layout.activity_add_task);
////        getSupportActionBar().setTitle("Add Task");
////        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
////
////        saveBtn=findViewById(R.id.taskSaveBtn);
////        etTaskInput=findViewById(R.id.inputTaskName);
////
////        db = FirebaseFirestore.getInstance();
////
////        saveBtn.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////
////                String taskName=etTaskInput.getText().toString().trim();
////                if(taskName!=null)
////                {
////                    findViewById(R.id.progress).setVisibility(View.VISIBLE);
////                    TaskModel taskModel=new TaskModel("",taskName,"PENDING", FirebaseAuth.getInstance().getUid());
////                    db.collection("tasks").add(taskModel).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
////                                @Override
////                                public void onSuccess(DocumentReference documentReference) {
////                                    Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
////
////                                    findViewById(R.id.successLayout).setVisibility(View.VISIBLE);
////                                    findViewById(R.id.progress).setVisibility(View.GONE);
////                                    findViewById(R.id.addTaskLayout).setVisibility(View.GONE);
////
////
////
////                                }
////                            })
////                            .addOnFailureListener(new OnFailureListener() {
////                                @Override
////                                public void onFailure(@NonNull Exception e) {
////                                    Log.w(TAG, "Error adding document", e);
////                                }
////                            });
////
////                }
////            }
////        });
////
////    }
////
////    @Override
////    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
////        if(item.getItemId()==android.R.id.home)
////        {
////            finish();
////        }
////        return super.onOptionsItemSelected(item);
////    }
////}
//
//package com.example.easygo;
//
//import static android.content.ContentValues.TAG;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.widget.Toolbar;
//
//import android.os.Bundle;
//import android.util.Log;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//
//import com.example.easygo.model.TaskModel;
//import com.google.android.gms.tasks.OnFailureListener;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.firestore.DocumentReference;
//import com.google.firebase.firestore.FirebaseFirestore;
//
//public class AddTaskActivity extends AppCompatActivity {
//
//    EditText etTaskInput;
//    Button saveBtn;
//    FirebaseFirestore db;
//    String TAG = "EasyGO";
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_add_task);
//
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        if (getSupportActionBar() != null) {
//            getSupportActionBar().setTitle("Add Task");
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        }
//
//        saveBtn = findViewById(R.id.taskSaveBtn);
//        etTaskInput = findViewById(R.id.inputTaskName);
//
//        db = FirebaseFirestore.getInstance();
//
//        saveBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String taskName = etTaskInput.getText().toString().trim();
//                if (taskName != null && !taskName.isEmpty()) {
//                    findViewById(R.id.progress).setVisibility(View.VISIBLE);
//                    TaskModel taskModel = new TaskModel("", taskName, "PENDING", FirebaseAuth.getInstance().getUid());
//                    db.collection("tasks").add(taskModel).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                        @Override
//                        public void onSuccess(DocumentReference documentReference) {
//                            Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
//                            findViewById(R.id.successLayout).setVisibility(View.VISIBLE);
//                            findViewById(R.id.progress).setVisibility(View.GONE);
//                            findViewById(R.id.addTaskLayout).setVisibility(View.GONE);
//                        }
//                    }).addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            Log.w(TAG, "Error adding document", e);
//                        }
//                    });
//                } else {
//                    etTaskInput.setError("Task name cannot be empty");
//                }
//            }
//        });
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        if (item.getItemId() == android.R.id.home) {
//            finish();
//        }
//        return super.onOptionsItemSelected(item);
//    }
//}
//
//package com.example.easygo;
//
//import static android.content.ContentValues.TAG;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.widget.Toolbar;
//
//import android.os.Bundle;
//import android.util.Log;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Toast;
//
//import com.example.easygo.model.TaskModel;
//import com.google.android.gms.tasks.OnFailureListener;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.firestore.DocumentReference;
//import com.google.firebase.firestore.FirebaseFirestore;
//
//public class AddTaskActivity extends AppCompatActivity {
//
//    EditText etTaskName, etTaskDate, etTaskTime, etTaskDescription;
//    Button saveBtn;
//    FirebaseFirestore db;
//    String TAG = "EasyGO";
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_add_task);
//
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        if (getSupportActionBar() != null) {
//            getSupportActionBar().setTitle("Add Task");
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        }
//
//        saveBtn = findViewById(R.id.taskSaveBtn);
//        etTaskName = findViewById(R.id.inputTaskName);
//        etTaskDate = findViewById(R.id.inputTaskDate);
//        etTaskTime = findViewById(R.id.inputTaskTime);
//        etTaskDescription = findViewById(R.id.inputTaskDescription);
//
//        db = FirebaseFirestore.getInstance();
//
//        saveBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String taskName = etTaskName.getText().toString().trim();
//                String taskDate = etTaskDate.getText().toString().trim();
//                String taskTime = etTaskTime.getText().toString().trim();
//                String taskDescription = etTaskDescription.getText().toString().trim();
//
//                if (taskName.isEmpty()) {
//                    etTaskName.setError("Task name cannot be empty");
//                    return;
//                }
//
//                if (taskDate.isEmpty()) {
//                    etTaskDate.setError("Task date cannot be empty");
//                    return;
//                }
//
//                if (taskTime.isEmpty()) {
//                    etTaskTime.setError("Task time cannot be empty");
//                    return;
//                }
//
//                if (taskDescription.isEmpty()) {
//                    etTaskDescription.setError("Task description cannot be empty");
//                    return;
//                }
//
//                findViewById(R.id.progress).setVisibility(View.VISIBLE);
//                TaskModel taskModel = new TaskModel("", taskName, "PENDING", FirebaseAuth.getInstance().getUid(), taskDate, taskTime, taskDescription);
//
//                db.collection("tasks").add(taskModel).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                    @Override
//                    public void onSuccess(DocumentReference documentReference) {
//                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
//                        findViewById(R.id.successLayout).setVisibility(View.VISIBLE);
//                        findViewById(R.id.progress).setVisibility(View.GONE);
//                        findViewById(R.id.addTaskLayout).setVisibility(View.GONE);
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.w(TAG, "Error adding document", e);
//                        findViewById(R.id.progress).setVisibility(View.GONE);
//                        Toast.makeText(AddTaskActivity.this, "Error adding task", Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//        });
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        if (item.getItemId() == android.R.id.home) {
//            finish();
//        }
//        return super.onOptionsItemSelected(item);
//    }
//}

package com.example.easygo;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.easygo.model.TaskModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;

public class AddTaskActivity extends AppCompatActivity {

    EditText etTaskName, etTaskDate, etTaskTime, etTaskDescription;
    Button saveBtn;
    FirebaseFirestore db;
    String TAG = "EasyGO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Add Task");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        saveBtn = findViewById(R.id.taskSaveBtn);
        etTaskName = findViewById(R.id.inputTaskName);
        etTaskDate = findViewById(R.id.inputTaskDate);
        etTaskTime = findViewById(R.id.inputTaskTime);
        etTaskDescription = findViewById(R.id.inputTaskDescription);

        db = FirebaseFirestore.getInstance();

        etTaskDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        etTaskTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker();
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String taskName = etTaskName.getText().toString().trim();
                String taskDate = etTaskDate.getText().toString().trim();
                String taskTime = etTaskTime.getText().toString().trim();
                String taskDescription = etTaskDescription.getText().toString().trim();

                if (taskName.isEmpty()) {
                    etTaskName.setError("Task name cannot be empty");
                    return;
                }

                if (taskDate.isEmpty()) {
                    etTaskDate.setError("Task date cannot be empty");
                    return;
                }

                if (taskTime.isEmpty()) {
                    etTaskTime.setError("Task time cannot be empty");
                    return;
                }

                if (taskDescription.isEmpty()) {
                    etTaskDescription.setError("Task description cannot be empty");
                    return;
                }

                findViewById(R.id.progress).setVisibility(View.VISIBLE);
                TaskModel taskModel = new TaskModel("", taskName, "PENDING", FirebaseAuth.getInstance().getUid(), taskDate, taskTime, taskDescription);

                db.collection("tasks").add(taskModel).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                        findViewById(R.id.successLayout).setVisibility(View.VISIBLE);
                        findViewById(R.id.progress).setVisibility(View.GONE);
                        findViewById(R.id.addTaskLayout).setVisibility(View.GONE);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                        findViewById(R.id.progress).setVisibility(View.GONE);
                        Toast.makeText(AddTaskActivity.this, "Error adding task", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void showDatePicker() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year1, month1, dayOfMonth) -> {
                    String date = dayOfMonth + "/" + (month1 + 1) + "/" + year1;
                    etTaskDate.setText(date);
                }, year, month, day);
        datePickerDialog.show();
    }

    private void showTimePicker() {
        final Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                (view, hourOfDay, minute1) -> {
                    String time = hourOfDay + ":" + minute1;
                    etTaskTime.setText(time);
                }, hour, minute, true);
        timePickerDialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}


