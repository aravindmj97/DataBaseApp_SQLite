package com.iamaravind.databaseapp;

import android.database.Cursor;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DataBaseHelper db = new DataBaseHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText id = (EditText)findViewById(R.id.id);
        final EditText name = (EditText)findViewById(R.id.name);
        final EditText college = (EditText)findViewById(R.id.college);
        Button savebtn = (Button)findViewById(R.id.savebt);
        Button viewbtn = (Button)findViewById(R.id.viewbt);
        Button deletebtn = (Button)findViewById(R.id.deletebt);
        Button updatebtn = (Button)findViewById(R.id.updatebt);

        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean res = db.insertDb(name.getText().toString(), college.getText().toString());
                if (res == true)
                    Toast.makeText(MainActivity.this,"Data Inserted Successfully", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(MainActivity.this,"Data Not Inserted", Toast.LENGTH_LONG).show();
            }
     });
        viewbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Cursor result = db.getdata();
                if (result.getCount() == 0) {
                    showMessage("Error!!!", "Data Base is Empty!!");
                    return;
                }
                StringBuffer stringBuffer = new StringBuffer();
                while (result.moveToNext()){
                    stringBuffer.append("ID = "+ result.getString(0)+"\n");
                    stringBuffer.append("Name = "+ result.getString(1)+"\n");
                    stringBuffer.append("College = "+ result.getString(2)+"\n");
                }
                showMessage("Data From DB", stringBuffer.toString());
            }
        });
        deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               Integer delres =  db.deletefrom(id.getText().toString());
                if (delres > 0)
                    Toast.makeText(MainActivity.this, "Successfully Deleted", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(MainActivity.this, "Error While Deleting", Toast.LENGTH_LONG).show();
            }
        });
        updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean updateres = db.updateto(id.getText().toString(), name.getText().toString(), college.getText().toString());
                if (updateres == true)
                    Toast.makeText(MainActivity.this, "Data Successfully Updated", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(MainActivity.this, "Error While Updating Data", Toast.LENGTH_LONG).show();
            }
        });
    }
    public  void showMessage(String title, String message)
    {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}
