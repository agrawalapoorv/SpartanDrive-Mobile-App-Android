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
 * Created by namithashetty on 12/5/15.
 */
public class SearchPopUpActivity extends ConnectionActivity{
    private static final String TAG = "ConnectionDriveActivity";
    final Context context = this;
    private Button button;

    @Override
    public void onConnected(Bundle connectionHint) {
        //super.onConnected(connectionHint);
        Log.d("Searchpop Menu","Inside the Onconnect method  of SearchPop");
        // custom dialog
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.search_pop_up);
        dialog.setTitle("Search for folders and files");

        // set the custom dialog components - text, image and button
        EditText text = (EditText) dialog.findViewById(R.id.searchItem);
        text.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    final String searchTerm = v.getText().toString();
                    Log.i(TAG, "Request code" + searchTerm);

                    //Button dialogButton = (Button) dialog.findViewById(R.id.searchTerm);
                    //dialogButton.setOnClickListener(new View.OnClickListener() {
                       // @Override
                       // public void onClick(View view) {
                            Intent intent = new Intent(SearchPopUpActivity.this, SearchFileBasedOnTitleActivity.class);
                            Log.i(TAG, "Request code" + searchTerm);
                            intent.putExtra("SearchTerm", searchTerm);
                            startActivity(intent);
                        //}
                   // });
                    //Toast.makeText(CustomFolderNameActivity.this, "Folder name is:" + folderName, Toast.LENGTH_SHORT).show();
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
