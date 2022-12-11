package com.example.todolistapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.Date;

public class HomeActivity extends AppCompatActivity {
private Toolbar toolbar;
private RecyclerView recyclerView;
private FloatingActionButton floatingActionButton;
private String id;
private FirebaseAuth mAuth;

DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://todolist-e5a6e-default-rtdb.firebaseio.com");
private String key="";
private String task;
private String description;
private ProgressDialog loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_home);
        initViews();

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNote();
            }
        });

    }

    private void addNote() {
        AlertDialog.Builder myDialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View myView = inflater.inflate(R.layout.input_file,null);
        myDialog.setView(myView);

        final AlertDialog dialog = myDialog.create();
        dialog.setCancelable(false);

        final EditText task = myView.findViewById(R.id.et_noteInputFile);
        final EditText description = myView.findViewById(R.id.et_Description);
        Button save = myView.findViewById(R.id.saveBtn);
        Button cancel = myView.findViewById(R.id.cancelBtn);


        //TODO CANCEL BUTTON
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });




        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mTask = task.getText().toString().trim();
                String mDiscription = description.getText().toString().trim();
            //    String id= "0505252055";
                String date = DateFormat.getDateInstance().format(new Date());

                if(TextUtils.isEmpty(mTask)){
                    task.setError("Task Required");
                    return;
                }
                if(TextUtils.isEmpty(mDiscription)){
                    task.setError("Description Required");
                    return;
                }

                else{
                    databaseReference.child("task").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            Model m = new Model(mTask,mDiscription,id,date);
                            databaseReference.child("task").child(id).child(mTask).setValue(m);


                                Toast.makeText(HomeActivity.this,"Task has been inserted successfully",Toast.LENGTH_SHORT).show();
                                loader.dismiss();

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }


             dialog.dismiss();

            }
        });
        dialog.show();





    }

    private void initViews() {

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Todo List");

        mAuth = FirebaseAuth.getInstance();


        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        loader = new ProgressDialog(this);


        floatingActionButton = findViewById(R.id.fab);

    }


    @Override
    protected void onStart() {
        super.onStart();
        //:TODO BUNDEL GIVE THE PHONE NUMBER THE NEXT ACTIVITIS

        Bundle bundle = getIntent().getExtras();
        id = bundle.getString("id","PhoneTxet");



      //  String id = "0505252055";


        FirebaseRecyclerOptions<Model> options = new FirebaseRecyclerOptions.Builder<Model>()
                .setQuery(databaseReference.child("task").child(id), Model.class)
                .build();

        FirebaseRecyclerAdapter<Model,myViewHolder> adapter = new FirebaseRecyclerAdapter<Model, myViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull myViewHolder holder, @SuppressLint("RecyclerView") final int position, @NonNull Model model) {
                Log.d("TAG", "onBindViewHolder: "+model.getDate());
                    holder.setDate(model.getDate());
                    holder.setDescription(model.getDescription());
                    holder.setTask(model.getTask());


                    holder.mView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            key = getRef(position).getKey();
                            task = model.getTask();
                            description = model.getDescription();
                            updateTask();
                        }
                    });







            }

            @NonNull
            @Override
            public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.retrieved_layout,parent,false);
                return new myViewHolder(view);
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }








    public static class myViewHolder extends RecyclerView.ViewHolder {
        View mView;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
        }



        public void setTask(String task){
            TextView taskTextView = mView.findViewById(R.id.taskTv);
            taskTextView.setText(task);
        }

        public void setDescription(String description){
            TextView descriptionTextView = mView.findViewById(R.id.descriptionTv);
            descriptionTextView.setText(description);
        }
        public void setDate(String date){
            TextView dateTextView = mView.findViewById(R.id.dateTv);
            dateTextView.setText(date);
        }

    }

private void updateTask (){

        AlertDialog.Builder myDialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View view =inflater.inflate(R.layout.update_data,null);
        myDialog.setView(view);
        AlertDialog dialog = myDialog.create();
        EditText mtask = view.findViewById(R.id.et_updateTask);
        EditText mDesc = view.findViewById(R.id.et_updateDescription);
        mtask.setText(task);
        mtask.setSelection(task.length());
        mDesc.setText(description);
        mDesc.setSelection(description.length());

        Button delBtn = view.findViewById(R.id.btnDeleteUpdate);
        Button updateBtn = view.findViewById(R.id.btnSaveUpdate);
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                task= mtask.getText().toString().trim();
                description = mDesc.getText().toString().trim();
                String data = DateFormat.getDateInstance().format(new Date());
                Model m = new Model(task,description,id,data);
                databaseReference.child("task").child(id).child(task).setValue(m).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(HomeActivity.this,"Data has been update successfully",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            String error = task.getException().toString();
                            Toast.makeText(HomeActivity.this,"Update failed"+error,Toast.LENGTH_SHORT).show();

                        }
                    }
                });
                dialog.dismiss();

            }
        });

        delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child("task").child(id).child(task).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(HomeActivity.this,"Task deleted successfully",Toast.LENGTH_SHORT).show();

                        }
                        else{
                            String error = task.getException().toString();
                            Toast.makeText(HomeActivity.this,"Failed to delete task "+error,Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                dialog.dismiss();

                }
        });





    dialog.show();


}


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
       switch (item.getItemId()){
           case R.id.logout:
               mAuth.signOut();
               Intent intent = new Intent(HomeActivity.this,loginActivity.class);
               intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
       }
        return super.onOptionsItemSelected(item);
    }
}