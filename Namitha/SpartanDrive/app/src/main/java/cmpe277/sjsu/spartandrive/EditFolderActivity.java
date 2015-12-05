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

import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveApi;
import com.google.android.gms.drive.DriveFolder;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.DriveResource;
import com.google.android.gms.drive.MetadataChangeSet;
import android.widget.Toast;

/**
 * Created by namithashetty on 11/28/15.
 */
public class EditFolderActivity extends ConnectionActivity {

    private static final String TAG = "ConnectionDriveActivity";
    final Context context = this;
    private Button button;

    @Override
    public void onConnected(Bundle connectionHint) {
        super.onConnected(connectionHint);

        // custom dialog
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.edit_folder);
        dialog.setTitle("Rename a folder");

        // set the custom dialog components - text, image and button
        EditText text = (EditText) dialog.findViewById(R.id.folderName);
        text.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    final String folderName = v.getText().toString();
                    Log.i(TAG, "Request code" + folderName);

                    //Button dialogButton = (Button) dialog.findViewById(R.id.editFolder);
                    //dialogButton.setOnClickListener(new View.OnClickListener() {
                      //  @Override
                        //public void onClick(View view) {
                            String driveId = getIntent().getExtras().getString("driveId");
                            Intent intent = new Intent(EditFolderActivity.this, UpdateFolderActivity.class);
                            Log.i(TAG, "Request code" + folderName);
                            intent.putExtra("folderName", folderName);
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


