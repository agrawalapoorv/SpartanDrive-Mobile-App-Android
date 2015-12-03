package cmpe277.sjsu.spartandrive;

/**
 * Created by namithashetty on 12/1/15.
 */

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.content.Context;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.view.LayoutInflater;
import android.view.View.OnClickListener;
import com.google.android.gms.drive.Drive;
import android.view.View;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class CustomFolderNameActivity extends ConnectionActivity{
    private static final String TAG = "ConnectionDriveActivity";
    final Context context = this;
    private Button button;

    @Override
    public void onConnected(Bundle connectionHint) {
        super.onConnected(connectionHint);

        // custom dialog
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.folder_name);
        dialog.setTitle("Create a folder");

        // set the custom dialog components - text, image and button
        EditText text = (EditText) dialog.findViewById(R.id.folderName);
        text.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    final String folderName = v.getText().toString();
                    Log.i(TAG, "Request code" + folderName);

                    Button dialogButton = (Button) dialog.findViewById(R.id.createFolder);
                    dialogButton.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(CustomFolderNameActivity.this, CreateFolderActivity.class);
                            Log.i(TAG, "Request code" + folderName);
                            intent.putExtra("folderName", folderName);
                            startActivity(intent);
                        }
                    });
                    //Toast.makeText(CustomFolderNameActivity.this, "Folder name is:" + folderName, Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });

        Button cancelButton = (Button) dialog.findViewById(R.id.cancel_action);
        // if button is clicked, close the custom dialog
        cancelButton.setOnClickListener(new OnClickListener() {
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