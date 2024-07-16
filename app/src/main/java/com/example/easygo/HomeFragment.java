//package com.example.easygo;
//
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.SearchView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.easygo.model.TaskModel;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.firestore.FirebaseFirestore;
//import com.google.firebase.firestore.QueryDocumentSnapshot;
//import com.google.firebase.firestore.QuerySnapshot;
//import com.squareup.picasso.Picasso;
//
//import java.util.ArrayList;
//
//import de.hdodenhof.circleimageview.CircleImageView;
//import io.github.inflationx.viewpump.ViewPumpContextWrapper;
//
//public class HomeFragment extends Fragment {
//
//    private RecyclerView taskRv;
//    private ArrayList<TaskModel> dataList = new ArrayList<>();
//    private TaskListAdapter taskListAdapter;
//    private FirebaseFirestore db;
//    private static final String TAG = "Homepage query docs";
//    private TextView userNameTv;
//    private CircleImageView userImageIv;
//    private SearchView searchView;
//    private FirebaseAuth auth;
//    private FirebaseUser user;
//
//
//    @Override
//    public void onAttach(@NonNull Context context) {
//        super.onAttach(ViewPumpContextWrapper.wrap(context));
//    }
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_home, container, false);
//
//        db = FirebaseFirestore.getInstance();
//        taskRv = view.findViewById(R.id.taskListRv);
//        userNameTv = view.findViewById(R.id.userNameTv);
////        userImageIv = view.findViewById(R.id.userProfileIv);
//        searchView = view.findViewById(R.id.searchview);
//
//        auth = FirebaseAuth.getInstance();
//        user = auth.getCurrentUser();
//
//        if (user == null) {
//            Intent intent = new Intent(getActivity(), LoginActivity.class);
//            startActivity(intent);
//            getActivity().finish();
//        } else {
//            userNameTv.setText(user.getEmail());
//        }
//
////        userImageIv.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                Intent intent = new Intent(getActivity(), SettingsActivity.class);
////                startActivity(intent);
////            }
////        });
////
////        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
////            userNameTv.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
////            Picasso.get().load(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl()).into(userImageIv);
////        }
//
//        taskListAdapter = new TaskListAdapter(dataList);
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
//        taskRv.setLayoutManager(layoutManager);
//        taskRv.setAdapter(taskListAdapter);
//
//        view.findViewById(R.id.addTaskFAB).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                try {
//                    Log.d(TAG, "FAB clicked");
//                    Intent intent = new Intent(getActivity(), AddTaskActivity.class);
//                    startActivity(intent);
//                } catch (Exception e) {
//                    Log.e(TAG, "Error starting AddTaskActivity", e);
//                }
//            }
//        });
//
//        fetchTasks();
//
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String s) {
//                Log.d("search", " " + s);
//                taskListAdapter.clearAllItems();
//                db.collection("tasks")
//                        .orderBy("taskName")
//                        .startAt(s)
//                        .endAt(s + '\uf8ff')
//                        .get()
//                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                            @Override
//                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                                if (task.isSuccessful()) {
//                                    for (QueryDocumentSnapshot document : task.getResult()) {
//                                        Log.d(TAG, document.getId() + " => " + document.getData());
//
//                                        TaskModel taskModel = document.toObject(TaskModel.class);
//                                        taskModel.setTaskId(document.getId());
//
//                                        dataList.add(taskModel);
//                                        taskListAdapter.notifyDataSetChanged();
//                                    }
//                                } else {
//                                    Log.d(TAG, "Error getting documents: ", task.getException());
//                                }
//                            }
//                        });
//
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String s) {
//                return false;
//            }
//        });
//
//        return view;
//    }
//
//    private void fetchTasks() {
//        db.collection("tasks")
//                .whereEqualTo("userId", FirebaseAuth.getInstance().getUid())
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                Log.d(TAG, document.getId() + " => " + document.getData());
//
//                                TaskModel taskModel = document.toObject(TaskModel.class);
//                                taskModel.setTaskId(document.getId());
//
//                                dataList.add(taskModel);
//                                taskListAdapter.notifyDataSetChanged();
//                            }
//                        } else {
//                            Log.d(TAG, "Error getting documents: ", task.getException());
//                        }
//                    }
//                });
//    }
//}
//
//package com.example.easygo;
//
//import android.Manifest;
//import android.app.AlarmManager;
//import android.app.PendingIntent;
//import android.content.Context;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.os.Build;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.SearchView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.core.app.ActivityCompat;
//import androidx.core.content.ContextCompat;
//import androidx.fragment.app.Fragment;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.easygo.model.TaskModel;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.firestore.FirebaseFirestore;
//import com.google.firebase.firestore.QueryDocumentSnapshot;
//import com.google.firebase.firestore.QuerySnapshot;
//
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Locale;
//
//import de.hdodenhof.circleimageview.CircleImageView;
//import io.github.inflationx.viewpump.ViewPumpContextWrapper;
//
//public class HomeFragment extends Fragment {
//
//    private RecyclerView taskRv;
//    private ArrayList<TaskModel> dataList = new ArrayList<>();
//    private TaskListAdapter taskListAdapter;
//    private FirebaseFirestore db;
//    private static final String TAG = "Homepage query docs";
//    private TextView userNameTv;
//    private CircleImageView userImageIv;
//    private SearchView searchView;
//    private FirebaseAuth auth;
//    private FirebaseUser user;
//    private AlarmManager alarmManager;
//
//    private static final int REQUEST_CODE_POST_NOTIFICATIONS = 1;
//
//    @Override
//    public void onAttach(@NonNull Context context) {
//        super.onAttach(ViewPumpContextWrapper.wrap(context));
//    }
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_home, container, false);
//
//        db = FirebaseFirestore.getInstance();
//        taskRv = view.findViewById(R.id.taskListRv);
//        userNameTv = view.findViewById(R.id.userNameTv);
//        searchView = view.findViewById(R.id.searchview);
//        auth = FirebaseAuth.getInstance();
//        user = auth.getCurrentUser();
//        alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
//
//        if (user == null) {
//            Intent intent = new Intent(getActivity(), LoginActivity.class);
//            startActivity(intent);
//            getActivity().finish();
//        } else {
//            userNameTv.setText(user.getEmail());
//        }
//
//        taskListAdapter = new TaskListAdapter(dataList);
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
//        taskRv.setLayoutManager(layoutManager);
//        taskRv.setAdapter(taskListAdapter);
//
//        view.findViewById(R.id.addTaskFAB).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                try {
//                    Log.d(TAG, "FAB clicked");
//                    Intent intent = new Intent(getActivity(), AddTaskActivity.class);
//                    startActivity(intent);
//                } catch (Exception e) {
//                    Log.e(TAG, "Error starting AddTaskActivity", e);
//                }
//            }
//        });
//
//        fetchTasks();
//
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String s) {
//                Log.d("search", " " + s);
//                taskListAdapter.clearAllItems();
//                db.collection("tasks")
//                        .orderBy("taskName")
//                        .startAt(s)
//                        .endAt(s + '\uf8ff')
//                        .get()
//                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                            @Override
//                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                                if (task.isSuccessful()) {
//                                    for (QueryDocumentSnapshot document : task.getResult()) {
//                                        Log.d(TAG, document.getId() + " => " + document.getData());
//
//                                        TaskModel taskModel = document.toObject(TaskModel.class);
//                                        taskModel.setTaskId(document.getId());
//
//                                        dataList.add(taskModel);
//                                        taskListAdapter.notifyDataSetChanged();
//                                    }
//                                } else {
//                                    Log.d(TAG, "Error getting documents: ", task.getException());
//                                }
//                            }
//                        });
//
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String s) {
//                return false;
//            }
//        });
//
//        checkAndRequestNotificationPermission();
//
//        return view;
//    }
//
//    private void fetchTasks() {
//        db.collection("tasks")
//                .whereEqualTo("userId", FirebaseAuth.getInstance().getUid())
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                Log.d(TAG, document.getId() + " => " + document.getData());
//
//                                TaskModel taskModel = document.toObject(TaskModel.class);
//                                taskModel.setTaskId(document.getId());
//
//                                dataList.add(taskModel);
//                                setTaskAlarm(taskModel);
//                                taskListAdapter.notifyDataSetChanged();
//                            }
//                        } else {
//                            Log.d(TAG, "Error getting documents: ", task.getException());
//                        }
//                    }
//                });
//    }
//
//    private void setTaskAlarm(TaskModel taskModel) {
//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
//        try {
//            Calendar calendar = Calendar.getInstance();
//            calendar.setTime(dateFormat.parse(taskModel.getTaskDate() + " " + taskModel.getTaskTime()));
//
//            Intent intent = new Intent(getContext(), TaskAlarmReceiver.class);
//            intent.putExtra("taskId", taskModel.getTaskId());
//            PendingIntent pendingIntent = PendingIntent.getBroadcast(
//                    getContext(),
//                    taskModel.getTaskId().hashCode(),
//                    intent,
//                    PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_MUTABLE // Adding the mutability flag
//            );
//
//            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
//            Log.d(TAG, "Alarm set for task: " + taskModel.getTaskName() + " at " + calendar.getTime());
//        } catch (ParseException e) {
//            Log.e(TAG, "Error parsing date/time for task alarm", e);
//        }
//    }
//
//
//    private void checkAndRequestNotificationPermission() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//            if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
//                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.POST_NOTIFICATIONS}, REQUEST_CODE_POST_NOTIFICATIONS);
//            }
//        }
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == REQUEST_CODE_POST_NOTIFICATIONS) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                Log.d(TAG, "Notification permission granted.");
//            } else {
//                Log.d(TAG, "Notification permission denied.");
//            }
//        }
//    }
//}

package com.example.easygo;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easygo.model.TaskModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class HomeFragment extends Fragment {

    private RecyclerView taskRv;
    private ArrayList<TaskModel> dataList = new ArrayList<>();
    private TaskListAdapter taskListAdapter;
    private FirebaseFirestore db;
    private static final String TAG = "Homepage query docs";
    private TextView userNameTv;
    private CircleImageView userImageIv;
    private SearchView searchView;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private AlarmManager alarmManager;

    private static final int REQUEST_CODE_POST_NOTIFICATIONS = 1;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(ViewPumpContextWrapper.wrap(context));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        db = FirebaseFirestore.getInstance();
        taskRv = view.findViewById(R.id.taskListRv);
        userNameTv = view.findViewById(R.id.userNameTv);
        searchView = view.findViewById(R.id.searchview);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);

        if (user == null) {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
            getActivity().finish();
        } else {
            userNameTv.setText(user.getEmail());
        }

        taskListAdapter = new TaskListAdapter(dataList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        taskRv.setLayoutManager(layoutManager);
        taskRv.setAdapter(taskListAdapter);

        view.findViewById(R.id.refresh).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataList.clear();
                fetchTasks();
            }
        });


        view.findViewById(R.id.addTaskFAB).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Log.d(TAG, "FAB clicked");
                    Intent intent = new Intent(getActivity(), AddTaskActivity.class);
                    startActivity(intent);
                } catch (Exception e) {
                    Log.e(TAG, "Error starting AddTaskActivity", e);
                }
            }
        });

        fetchTasks();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Log.d("search", " " + s);
                taskListAdapter.clearAllItems();
                db.collection("tasks")
                        .orderBy("taskName")
                        .startAt(s)
                        .endAt(s + '\uf8ff')
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Log.d(TAG, document.getId() + " => " + document.getData());

                                        TaskModel taskModel = document.toObject(TaskModel.class);
                                        taskModel.setTaskId(document.getId());

                                        dataList.add(taskModel);
                                        taskListAdapter.notifyDataSetChanged();
                                    }
                                } else {
                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                }
                            }
                        });

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        checkAndRequestNotificationPermission();

        return view;
    }

    private void fetchTasks() {
        db.collection("tasks")
                .whereEqualTo("userId", FirebaseAuth.getInstance().getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());

                                TaskModel taskModel = document.toObject(TaskModel.class);
                                taskModel.setTaskId(document.getId());

                                if ("pending".equalsIgnoreCase(taskModel.getTaskStatus())) {
                                    setTaskAlarm(taskModel);
                                }

                                dataList.add(taskModel);
                                taskListAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    private void setTaskAlarm(TaskModel taskModel) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dateFormat.parse(taskModel.getTaskDate() + " " + taskModel.getTaskTime()));

            Intent intent = new Intent(getContext(), TaskAlarmReceiver.class);
            intent.putExtra("taskId", taskModel.getTaskId());
            intent.putExtra("taskName", taskModel.getTaskName());
            PendingIntent pendingIntent = PendingIntent.getBroadcast(
                    getContext(),
                    taskModel.getTaskId().hashCode(),
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_MUTABLE // Adding the mutability flag
            );

            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis() + 500, pendingIntent);
            Log.d(TAG, "Alarm set for task: " + taskModel.getTaskName() + " at " + calendar.getTime());
        } catch (ParseException e) {
            Log.e(TAG, "Error parsing date/time for task alarm", e);
        }
    }


    private void checkAndRequestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.POST_NOTIFICATIONS}, REQUEST_CODE_POST_NOTIFICATIONS);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_POST_NOTIFICATIONS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "Notification permission granted.");
            } else {
                Log.d(TAG, "Notification permission denied.");
            }
        }
    }
}


