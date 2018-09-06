package com.example.mahmoudsalaheldien.codeportfolio;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mahmoudsalaheldien.codeportfolio.SQLite.StoreCodeSQLite.StoreCodeEntry;

import com.example.mahmoudsalaheldien.codeportfolio.SQLite.StoreCodeSQLite;
import com.hsalf.smilerating.SmileRating;

public class InputsActivity extends AppCompatActivity {
    private static StoreCodeSQLite storeCodeSQLite;
    private static SQLiteDatabase sqLiteDatabase;
    private static final String TAG = "smile: ";
    private TextInputLayout titleCode_TextInputLayout_inputAct,
            typeCode_TextInputLayout_actInput;
    private SmileRating ratingCode_SmileRating;
    private EditText inputCode_editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inputs);

        titleCode_TextInputLayout_inputAct = findViewById(R.id.titleCode_TextInputLayout_editAct);
        typeCode_TextInputLayout_actInput = findViewById(R.id.typeCode_TextInputLayout_editAct);
        ratingCode_SmileRating = findViewById(R.id.ratingCode_SmileRating_actInput);
        inputCode_editText = findViewById(R.id.inputCode_editText_actInput);
        inputCode_editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (inputCode_editText.getHint().length() > 0) {
                    inputCode_editText.setHint("");
                    inputCode_editText.setGravity(Gravity.START);
                } else if (inputCode_editText.getText().length() <= 0) {
                    inputCode_editText.setHint("Enter Code here");
                    inputCode_editText.setGravity(Gravity.CENTER);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_input_menu, menu);
        return true;
    }

    public void saveMenu(MenuItem item) {
        String titleCode = null, typeCode = null;
        if (titleCode_TextInputLayout_inputAct.getEditText().getText() != null) {
            titleCode = titleCode_TextInputLayout_inputAct.getEditText().getText().toString().trim();
        }
        if (typeCode_TextInputLayout_actInput.getEditText().getText() != null) {
             typeCode = typeCode_TextInputLayout_actInput.getEditText().getText().toString().trim();
        }
        int rationgCode = ratingCode_SmileRating.getSelectedSmile();
        String inputCode = inputCode_editText.getText().toString();

        storeCodeSQLite = new StoreCodeSQLite(InputsActivity.this);
        // Gets the data repository in write mode
        sqLiteDatabase = storeCodeSQLite.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(StoreCodeEntry.TITLE_CODE, titleCode);
        values.put(StoreCodeEntry.TYPE_CODE, typeCode);
        values.put(StoreCodeEntry.RATING_CODE, rationgCode);
        values.put(StoreCodeEntry.CONTENT_CODE, inputCode);
        // Create a new map of values, where column names are the keys
        if (titleCode != null && typeCode != null && !titleCode.equals("") && !typeCode.equals("") && !inputCode.equals("")) {
            long newRowId = sqLiteDatabase.insert(StoreCodeEntry.TABLE_NAME, null, values);
            finish();
        }else {
            Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
