package lk.uok.mit.helloworld;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class HelloWorldActivity extends AppCompatActivity {

    private Context context=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello_world);
        this.context=getApplicationContext();

        //First get the current Intent by using Activity classe's getIntent() method
        Intent currentIntent=getIntent();
        // then get extras bundle by using getExtras() method of the Intent
        Bundle extras=currentIntent.getExtras();
        // Both above lines could have been written in a single line as below if required
        // Bundle extras = getIntent().getExtras();
        if (extras != null) {
            //The key argument here must match that used in the other activity
            //i.	“CONTACT_NUMBER” – the text in Contact Number input
            String contactNumber = extras.getString("CONTACT_NUMBER");
            //ii.	“MESSAGE” – the text in Message input
            String message = extras.getString("MESSAGE");

            //set the contact number value in to "textViewContactNumber"
            //get the reference to text view in java
            TextView textViewContactNumber=findViewById(R.id.textViewContactNumber);
            //set the text of text view
            textViewContactNumber.setText(contactNumber);

            //set the contact number value in to "textViewMessage"
            //get the reference to text view in java
            TextView textViewMessage=findViewById(R.id.textViewMessage);
            //set the text of text view
            textViewMessage.setText(message);

        }

    }
}
