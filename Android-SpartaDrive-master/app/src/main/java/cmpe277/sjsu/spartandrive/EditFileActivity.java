package cmpe277.sjsu.spartandrive;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by namithashetty on 12/13/15.
 */
public class EditFileActivity extends ConnectionActivity {

    private static final String TAG = "ConnectionDriveActivity";
    final Context context = this;

    @Override
    public void onConnected(Bundle connectionHint) {
        // super.onConnected(connectionHint);

        // custom dialog
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.edit_file);
        dialog.setTitle("Rename a file");

        // set the custom dialog components - text, image and button
        EditText text = (EditText) dialog.findViewById(R.id.fileName);
        text.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    final String fileName = v.getText().toString();
                    Log.i(TAG, "Request code" + fileName);

                    //Button dialogButton = (Button) dialog.findViewById(R.id.editFolder);
                    //dialogButton.setOnClickListener(new View.OnClickListener() {
                    //  @Override
                    //public void onClick(View view) {
                    String driveId = getIntent().getExtras().getString("driveId");
                    Intent intent = new Intent(EditFileActivity.this, UpdateFileActivity.class);
                    Log.i(TAG, "Request code" + fileName);
                    intent.putExtra("fileName", fileName);
                    intent.putExtra("driveId",driveId);
                    startActivity(intent);
                    // }
                    // });
                    //Toast.makeText(EditFolderActivity.this, "Folder title updated successfully", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });

        Button cancelButton = (Button) dialog.findViewById(R.id.cancel_action);
        // if button is clicked, close the custom dialog
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        dialog.show();
    }
}


