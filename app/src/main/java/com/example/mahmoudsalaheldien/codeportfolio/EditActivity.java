package com.example.mahmoudsalaheldien.codeportfolio;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mahmoudsalaheldien.codeportfolio.SQLite.StoreCodeSQLite;
import com.example.mahmoudsalaheldien.codeportfolio.SQLite.StoreCodeSQLite.StoreCodeEntry;
import com.hsalf.smilerating.SmileRating;

public class EditActivity extends AppCompatActivity {
    private TextInputLayout titleCode_TextInputLayout_editAct,
            typeCode_TextInputLayout_editAct;
    private ImageView rationImage_ImageView_editAct, copy_imageView_actEdit;
    private EditText inputCode_editText_editAct;
    private Menu menu;
    private SmileRating ratingCode_SmileRating_editAct;
    private int ratingCode;
    private ClipboardManager myClipboard;
    private ClipData myClip;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_edit_menu, menu);
        this.menu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.editMenu_actEdit:
                copy_imageView_actEdit.setVisibility(View.GONE);
                MenuItem saveMenu = menu.findItem(R.id.saveMenu_actEdit);
                titleCode_TextInputLayout_editAct.setEnabled(true);
                titleCode_TextInputLayout_editAct.getEditText().setFocusableInTouchMode(true);
                typeCode_TextInputLayout_editAct.setEnabled(true);
                typeCode_TextInputLayout_editAct.getEditText().setFocusableInTouchMode(true);
                inputCode_editText_editAct.setEnabled(true);
                inputCode_editText_editAct.setFocusableInTouchMode(true);
                item.setVisible(false);
                saveMenu.setVisible(true);
                ratingCode_SmileRating_editAct.setVisibility(View.VISIBLE);
                rationImage_ImageView_editAct.setVisibility(View.GONE);
                ratingCode_SmileRating_editAct.setSelectedSmile(ratingCode);
                return true;
            case R.id.saveMenu_actEdit:
                ContentValues values = new ContentValues();
                String titleCode = titleCode_TextInputLayout_editAct.getEditText().getText().toString().trim();
                values.put(StoreCodeEntry.TITLE_CODE, titleCode);
                String typeCode = typeCode_TextInputLayout_editAct.getEditText().getText().toString().trim();
                values.put(StoreCodeEntry.TYPE_CODE, typeCode);
                ratingCode = ratingCode_SmileRating_editAct.getSelectedSmile();
                values.put(StoreCodeEntry.RATING_CODE, ratingCode);
                String contentCode = inputCode_editText_editAct.getText().toString().trim();
                values.put(StoreCodeEntry.CONTENT_CODE, contentCode);
                if (!titleCode.equals("") && !typeCode.equals("") && !contentCode.equals("")) {
                    copy_imageView_actEdit.setVisibility(View.VISIBLE);
                    MenuItem editMenu = menu.findItem(R.id.editMenu_actEdit);
                    titleCode_TextInputLayout_editAct.setEnabled(false);
                    typeCode_TextInputLayout_editAct.setEnabled(false);
                    inputCode_editText_editAct.setEnabled(false);
                    item.setVisible(false);
                    editMenu.setVisible(true);
                    ratingCode_SmileRating_editAct.setVisibility(View.GONE);
                    rationImage_ImageView_editAct.setVisibility(View.VISIBLE);
// Which row to update, based on the title
                    Intent intent = getIntent();
                    int id = intent.getIntExtra(StoreCodeEntry._ID, -1);
                    String selection = StoreCodeEntry._ID + " = ?";
                    String[] selectionArgs = {Integer.toString(id)};
                    SQLiteDatabase sqLiteDatabase = new StoreCodeSQLite(this).getWritableDatabase();
                    int count = sqLiteDatabase.update(
                            StoreCodeEntry.TABLE_NAME,
                            values,
                            selection,
                            selectionArgs);
                    switch (ratingCode) {
                        case 4:
                            rationImage_ImageView_editAct.setImageResource(R.drawable.five_emoji);
                            break;
                        case 3:
                            rationImage_ImageView_editAct.setImageResource(R.drawable.four_emoji);
                            break;
                        case 2:
                            rationImage_ImageView_editAct.setImageResource(R.drawable.three_emoji);
                            break;
                        case 1:
                            rationImage_ImageView_editAct.setImageResource(R.drawable.two_emoji);
                            break;
                        case 0:
                            rationImage_ImageView_editAct.setImageResource(R.drawable.one_emoji);
                    }
                } else {
                    Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        copy_imageView_actEdit = findViewById(R.id.copy_imageView_actEdit);
        titleCode_TextInputLayout_editAct = findViewById(R.id.titleCode_TextInputLayout_editAct);
        typeCode_TextInputLayout_editAct = findViewById(R.id.typeCode_TextInputLayout_editAct);
        rationImage_ImageView_editAct = findViewById(R.id.rationImage_ImageView_editAct);
        ratingCode_SmileRating_editAct = findViewById(R.id.ratingCode_SmileRating_editAct);
        inputCode_editText_editAct = findViewById(R.id.inputCode_editText_editAct);
        titleCode_TextInputLayout_editAct.getEditText().setTextColor(Color.rgb(30, 30, 30));
        typeCode_TextInputLayout_editAct.getEditText().setTextColor(Color.rgb(30, 30, 30));
        inputCode_editText_editAct.setTextColor(Color.rgb(30, 30, 30));
        Intent intent = getIntent();
        String titleCode = intent.getStringExtra(StoreCodeEntry.TITLE_CODE);
        titleCode_TextInputLayout_editAct.getEditText().setText(titleCode);
        titleCode_TextInputLayout_editAct.setEnabled(false);
        String typeCode = intent.getStringExtra(StoreCodeEntry.TYPE_CODE);
        typeCode_TextInputLayout_editAct.getEditText().setText(typeCode);
        typeCode_TextInputLayout_editAct.setEnabled(false);
        ratingCode = intent.getIntExtra(StoreCodeEntry.RATING_CODE, -1);
        switch (ratingCode) {
            case 4:
                rationImage_ImageView_editAct.setImageResource(R.drawable.five_emoji);
                break;
            case 3:
                rationImage_ImageView_editAct.setImageResource(R.drawable.four_emoji);
                break;
            case 2:
                rationImage_ImageView_editAct.setImageResource(R.drawable.three_emoji);
                break;
            case 1:
                rationImage_ImageView_editAct.setImageResource(R.drawable.two_emoji);
                break;
            case 0:
                rationImage_ImageView_editAct.setImageResource(R.drawable.one_emoji);
        }
        String contentCode = intent.getStringExtra(StoreCodeEntry.CONTENT_CODE);
        inputCode_editText_editAct.setText(contentCode);
        inputCode_editText_editAct.setEnabled(false);
    }

    public void copyCode(View view) {
        myClipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        String text;
        text = inputCode_editText_editAct.getText().toString();
        myClip = ClipData.newPlainText("text", text);
        myClipboard.setPrimaryClip(myClip);
        Toast.makeText(getApplicationContext(), "Code Copied", Toast.LENGTH_SHORT).show();
    }
}
