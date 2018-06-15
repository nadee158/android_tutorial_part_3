package lk.uok.mit.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import lk.uok.mit.helloworld.HelloWorldActivity;
import lk.uok.mit.helloworld.R;

public class SendMessageActivity extends Activity {

    //declare the button at class level, once its initialized inside onCreate, it can be used in other methods as well
    private Button buttonSend = null;

    //decalae a variable to hold the context at the class level
    private Context context = null;

    //declare a variable at class level to hold the reference to the Contact Number autocomplete field
    private AutoCompleteTextView autoCompleteTextContactNumber = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set the view of the activity
        setContentView(R.layout.activity_send_message);

        //initialize the context
        this.context = getApplicationContext();

        //initialize the autoCompleteTextContactNumber by geting reference
        this.autoCompleteTextContactNumber = findViewById(R.id.autoCompleteTextContactNumber);
        //call the setDataSourceForAutoComplete method we wrote to initialize the data source
        this.setDataSourceForAutoComplete();

        //initialize the class variable before using it
        buttonSend = findViewById(R.id.buttonSend);

        //get the java reference to Message Text box
        EditText editTextMessage = findViewById(R.id.editTextMessage);
        // call the addTextChangedListener() method on editText and pass an annonymous inner class
        editTextMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            //inside the listner, when the user changes textvalidateinput
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //vall validateInputs method and get if the fields are valid or not
                boolean isFieldsValid = validateInputs();
                // if the fields are valid, enable the button
                if (isFieldsValid) {
                    buttonSend.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //initialize the contactNumberText to a null value for now
                String contactNumberText = null;
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

    //a method to set the data source (adapter) to the autoCompleteTextContactNumber
    private void setDataSourceForAutoComplete() {
        //it takes three parameters to create an adapter
        //the context
        Context contextParameter = this.context;
        //the layout resource to define how to display a single contact number
        //here a built in resource of android is used
        int resourceParameter = android.R.layout.select_dialog_item;
        //data list to be displayed
        List<String> objectListParameter = this.getContactNumbers();
        //using above three parameters an Array adapter has been created
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(contextParameter, resourceParameter, objectListParameter);
        //having declared as a class variable, autoCompleteTextContactNumber is visble to this method too
        //use setAdapter method to set the adapter to the view
        this.autoCompleteTextContactNumber.setAdapter(adapter);
        //Specifies the minimum number of characters the user has to type in the edit box before the drop down list is shown.
        this.autoCompleteTextContactNumber.setThreshold(1);
        //set the background color of drop down to a darker color so it can be easily seen
        this.autoCompleteTextContactNumber.setDropDownBackgroundResource(R.color.colorPrimaryDark);
    }

    //a method to get the contact number list of device as String List
    private List<String> getContactNumbers() {
        //i.	Create a new java.util.ArrayList of String
        List<String> contactNumberList = new ArrayList<String>();
        //ii.	Add 10 random numbers to the list
        for (int i = 0; i < 10; i++) {
            Random rnd = new Random();
            //(numbers to look like phone numbers)
            int n = 112280670 + rnd.nextInt(9000000);
            contactNumberList.add('0' + Integer.toString(n));
        }
        //iii.	Return the list
        return contactNumberList;
    }


    // Check whether user has phone contacts manipulation permission or not.
    private boolean hasUserPermission(String permission) {
        boolean ret = true;
        // If android sdk version is bigger than 23 the need to check run time permission.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // return phone read contacts permission grant status.
            int hasPermission = ContextCompat.checkSelfPermission(this.context, permission);
            // If permission is granted then return true.
            if (hasPermission != PackageManager.PERMISSION_GRANTED) {
                ret = false;
            }
        }
        return ret;
    }


    // Request a runtime permission to app user.
    private void requestPermission(String permission) {
        String requestPermissionArray[] = {permission};
        ActivityCompat.requestPermissions(this, requestPermissionArray, 1);
    }

    // After user select Allow or Deny button in request runtime permission dialog
    // , this method will be invoked.
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        int length = grantResults.length;
        if (length > 0) {
            int grantResult = grantResults[0];

            if (grantResult == PackageManager.PERMISSION_GRANTED) {

                Toast.makeText(getApplicationContext(), "You allowed permission, please enter contact again.", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "You denied permission.", Toast.LENGTH_LONG).show();
            }
        }
    }


    //this is to check if both contact number and a message is enetered
    private boolean validateInputs() {
        boolean isValid = true;
        //initialize the contactNumberText to a null value for now
        String contactNumberText = null;

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