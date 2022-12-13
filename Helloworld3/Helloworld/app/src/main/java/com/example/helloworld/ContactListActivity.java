package com.example.helloworld;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helloworld.adapters.ContactAdapter;
import com.example.helloworld.adapters.MovieAdapter;
import com.example.helloworld.objects.Contact;
import com.example.helloworld.objects.Movie;
import com.example.helloworld.sqlite.DatabaseHandler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class ContactListActivity extends AppCompatActivity {
    private ArrayList<Contact> arrayList;
    private ContactAdapter contactAdapter;
    private RecyclerView recyclerView;
    private DatabaseHandler db;
    private FloatingActionButton btnAdd;
    //dang ky resultLauncher for Add button
    private ActivityResultLauncher<Intent> launcherforAdd;
    private ActivityResultLauncher<Intent> launcherforEdit;
    //position for edit data
    private int iPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_contact);
        //change tittle actionbar
        getSupportActionBar().setTitle(getString(R.string.listcontacttitle));
        db = new DatabaseHandler(this);
        initResultLauncher();
        getAllContacts();
        //su dung recyclerview de hien thi du lieu
        //do du lieu len adapter
        contactAdapter = new ContactAdapter(this, arrayList, new ContactAdapter.ClickListeners() {

            @Override
            public void onItemClick(int position, View v) {
                //gui du lieu sang AddContactActivity de edit du lieu
                iPosition = position;
                Contact c = arrayList.get(position);
                //tao intent va gui sang
                Intent i = new Intent(ContactListActivity.this, AddContactActivity.class);
                i.putExtra("contact", c);
                i.putExtra("flag", "1");//danh dau flag la chuc nang sua
                launcherforEdit.launch(i);
            }

            @Override
            public void onItemLongClick(int position, View v) {
                //code here
                //String name = arrayList.get(position).getName();
                recyclerView = findViewById(R.id.rcListContacts);
                AlertDialog.Builder alertDialog = new  AlertDialog.Builder(ContactListActivity.this);
                alertDialog.setTitle("Bạn có chắc muốn xóa");
                alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
                //alertDialog.setMessage(selectedValue);
                alertDialog.setNegativeButton("Xóa", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        int id = arrayList.get(position).getID();
                        String name = arrayList.get(position).getName();
                        String phone = arrayList.get(position).getPhoneNumber();
                        db.deleteContact(id);
                        Contact x= new Contact(id, name, phone);
                        arrayList.remove(x);
                        contactAdapter.notifyDataSetChanged();
//                        recyclerView = findViewById(R.id.rcListContacts);
//                        recyclerView.setAdapter(contactAdapter);
//                        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
//                        recyclerView.setLayoutManager(new LinearLayoutManager(ContactListActivity.this));

                    } });
                alertDialog.setPositiveButton("Thoát", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //alertDialog.dismiss();
                    } });
                alertDialog.show();
                //
                //Toast.makeText(getBaseContext(), name, Toast.LENGTH_SHORT).show();
                btnAdd.setVisibility(View.VISIBLE);
            }
        });
        //set du lieu cho recyclerview
        recyclerView = findViewById(R.id.rcListContacts);
        recyclerView.setAdapter(contactAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //
        btnAdd = findViewById(R.id.floatingActionButton);
        btnAdd.setOnClickListener(v -> {
            //open add contact activity
            Intent intent = new Intent(ContactListActivity.this, AddContactActivity.class);
            intent.putExtra("flag", "0");//danh dau flag la chuc nang them moi
            launcherforAdd.launch(intent);
        });
    }

    private void getAllContacts() {
        try {
            arrayList = new ArrayList<>();
            arrayList = db.getAllContacts();
        } catch (Exception ex) {
            Log.e("getAllContact", ex.getMessage());
        }
    }

    private void initResultLauncher() {
        try {
            launcherforAdd = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result != null && result.getResultCode() == RESULT_OK) {
                    //lay ve contact tai day va cap nhat len giao dien
                    Contact c = (Contact) result.getData().getSerializableExtra("contact");
                    //cap nhat tren giao
                    arrayList.add(c);
                    contactAdapter.notifyDataSetChanged();
                }
            });
            launcherforEdit = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),result -> {
               //edit data
                if (result != null && result.getResultCode() == RESULT_OK) {
                    //lay ve contact tai day va cap nhat len giao dien
                    Contact c = (Contact) result.getData().getSerializableExtra("contact");
                    //cap nhat tren giao
                    arrayList.set(iPosition, c);
                    contactAdapter.notifyDataSetChanged();
                }
            });
        } catch (Exception ex) {
            Log.e("initResultLauncher", ex.getMessage());
        }
    }
}