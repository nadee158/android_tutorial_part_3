package lk.uok.mit.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import lk.uok.mit.helloworld.HelloWorldActivity;
import lk.uok.mit.helloworld.R;

public class SendMessageActivity extends Activity {

    //declare the button at class level, once its initialized inside onCreate, it can be used in other methods as well
    private Button buttonSend=null;

    //decalae a variable to hold the context at the class level
    private Context context=null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set the view of the activity
        setContentView(R.layout.activity_send_message);

        //initialize the context
        this.context=getApplicationContext();

        EditText editTextContactNumber = findViewById(R.id.editTextContactNumber);
        editTextContactNumber.setHint(R.string.hint_edit_text_contact_number_new);

        //initialize the class variable before using it
        buttonSend = findViewById(R.id.buttonSend);

        editTextContactNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //vall validateInputs method and get if the fields are valid or not
                boolean isFieldsValid = validateInputs();
                // if the fields are valid, enable the button
                if(isFieldsValid){
                    buttonSend.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        //get the java reference to Message Text box
        EditText editTextMessage = findViewById(R.id.editTextMessage);
        // call the addTextChangedListener() method on editText and pass an annonymous inner class
        editTextMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            //inside the listner, when the user changes textvalidateinput
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //vall validateInputs method and get if the fields are valid or not
                boolean isFieldsValid = validateInputs();
                // if the fields are valid, enable the button
                if(isFieldsValid){
                    buttonSend.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get the java reference to Contact Number Text box
                EditText editTextContactNumber = findViewById(R.id.editTextContactNumber);
                //get the current text from Contact Number text box
                String contactNumberText = editTextContactNumber.getText().toString();

                //get the java reference to Message Text box
                EditText editTextMessage = findViewById(R.id.editTextMessage);
                //get the current text from Message text box
                String messageText = editTextMessage.getText().toString();

                //create a new Intent by passing the context of the current Activity
                // and the class of the Next Activity we need to start as parameters
                Intent intent = new Intent(context, HelloWorldActivity.class);
                //add the data to be passed to the next Activity using putExtra method of the intent
                intent.putExtra("CONTACT_NUMBER", contactNumberText);
                intent.putExtra("MESSAGE", messageText);
                //using inherited startActivity method of the Activity class, start the HelloWorldActivity
                startActivity(intent);
            }
        });


    }

    //this is to check if both contact number and a message is enetered
    private boolean validateInputs() {
        boolean isValid = true;
        //get the java reference to Contact Number Text box
        EditText editTextContactNumber = findViewById(R.id.editTextContactNumber);
        //get the current text from Contact Number text box
        String contactNumberText = editTextContactNumber.getText().toString();

        //if the text of contact number edittext is an empty value, set the valid status to false
        if (contactNumberText == null || contactNumberText.trim().isEmpty()) {
            isValid = false;
        }

        //get the java reference to Message Text box
        EditText editTextMessage = findViewById(R.id.editTextMessage);
        //get the current text from Message text box
        String messageText = editTextMessage.getText().toString();

        //if the text of Message edittext is an empty value, set the valid status to false
        if (messageText == null || messageText.trim().isEmpty()) {
            isValid = false;
        }

        return isValid;
    }
}