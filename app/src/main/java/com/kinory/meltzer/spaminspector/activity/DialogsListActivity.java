package com.kinory.meltzer.spaminspector.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.kinory.meltzer.spaminspector.R;
import com.kinory.meltzer.spaminspector.model.Dialog;
import com.kinory.meltzer.spaminspector.model.User;
import com.stfalcon.chatkit.commons.ImageLoader;
import com.stfalcon.chatkit.commons.models.IDialog;
import com.stfalcon.chatkit.dialogs.DialogsList;
import com.stfalcon.chatkit.dialogs.DialogsListAdapter;

public class DialogsListActivity extends AppCompatActivity {

    private DialogsListAdapter<Dialog> dialogsListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialogs_list);

        DialogsList dialogsList = (DialogsList) findViewById(R.id.dialogsList);
        dialogsListAdapter = new DialogsListAdapter<>(null);
        dialogsList.setAdapter(dialogsListAdapter);

        // Adds a dummy dialog
        dialogsListAdapter.addItem(new Dialog("1", new User("1", "Gilad Kinory", "Kinory")));

        // Start a DialogActivity when a dialog is clicked
        dialogsListAdapter.setOnDialogClickListener(dialog -> {
            Intent intent = new Intent(this, DialogActivity.class);
            intent.putExtra("dialog", dialog);
            startActivity(intent);
        });

    }
}
