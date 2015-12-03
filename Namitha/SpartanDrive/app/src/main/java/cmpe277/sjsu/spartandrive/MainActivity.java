package cmpe277.sjsu.spartandrive;

import android.app.ActionBar;
import android.content.BroadcastReceiver;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.PendingResults;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveApi;
import com.google.android.gms.drive.DriveContents;
import com.google.android.gms.drive.DriveFolder;
import com.google.android.gms.drive.DriveId;
import android.widget.Button;
import com.google.android.gms.drive.query.Query;
import com.google.android.gms.drive.query.Filters;
import com.google.android.gms.drive.query.SearchableField;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.PopupMenu;
import android.widget.TextView;
import java.util.Objects;

import com.google.android.gms.common.data.AbstractDataBuffer;
import com.google.android.gms.drive.DriveApi.MetadataBufferResult;
import com.google.android.gms.drive.Metadata;
import com.google.android.gms.drive.MetadataBuffer;

public class MainActivity extends ConnectionActivity  {

    private ViewAdapter mViewAdapter;
    private static final int REQUEST_CODE_OPENER = 1;
    private static final String TAG = "ConnectionDriveActivity";

    private GridView mResultsListView;
    private ViewAdapter mResultsAdapter;
    private GridView mListViewSamples;
    private String driveId;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_main);
        mResultsListView = (GridView) findViewById(R.id.listItems);
        mResultsAdapter = new ViewAdapter(this);
        mResultsListView.setAdapter(mResultsAdapter);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(),  CustomFolderNameActivity.class);
                startActivity(intent);
            }
        });


        //Code to handle onClick for CreateFolder button
       /* final Button button = (Button) findViewById(R.id.fab);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Intent intent = new Intent(getBaseContext(),  CreateFolderInFolderActivity.class);
                startActivity(intent);
            }
        });
*/
        mListViewSamples = (GridView) findViewById(R.id.listItems);
        mListViewSamples.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i(TAG, "Selected item:" + mResultsAdapter.getItem(position).getDriveId());
                driveId = mResultsAdapter.getItem(position).getDriveId().encodeToString();
                //String item = arg0.getItemAtPosition(i).toString();
                // Log.i(TAG, "DriveID:" +Drive.DriveApi.fetchDriveId(getGoogleApiClient(), "i"));
                //       .setResultCallback(idCallback);
                //Intent intent = new Intent(getBaseContext(), listContentsActivity.class);
                //startActivity(intent);
                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(MainActivity.this, mListViewSamples);
                //Inflating the Popup using xml file
                popup.getMenuInflater()
                        .inflate(R.menu.menu_main, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        Log.i(TAG, "res" + item.getTitle());
                        if (item.getTitle().equals("View Content")) {
                            Intent intent = new Intent(getBaseContext(), listContentsActivity.class);
                            intent.putExtra("driveId",driveId);
                            startActivity(intent);
                        }
                        if (item.getTitle().equals("Edit")) {
                            Intent intent = new Intent(getBaseContext(), EditFolderActivity.class);
                            intent.putExtra("driveId",driveId);
                            startActivity(intent);
                        }
                        if (item.getTitle().equals("Delete")) {
                            Intent intent = new Intent(getBaseContext(), DeleteFolderActivity.class);
                            intent.putExtra("driveId",driveId);
                            startActivity(intent);
                        }
                        return true;
                    }
                });

                popup.show(); //showing popup menu
            }
        });
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        super.onConnected(connectionHint);
        Drive.DriveApi.getRootFolder(getGoogleApiClient()).listChildren(getGoogleApiClient()).setResultCallback(metadataResult);
        //Log.i(TAG,"Drive" +Drive.DriveApi.getFolder(getGoogleApiClient(),getDriveId());
       // Drive.DriveApi.fetchDriveId(getGoogleApiClient(), "0B8m6vocGpnl3VGVzelctREUxYlk")
         //       .setResultCallback(idCallback);
    }

    final private ResultCallback<DriveApi.DriveIdResult> idCallback = new ResultCallback<DriveApi.DriveIdResult>() {
        @Override
        public void onResult(DriveApi.DriveIdResult result) {
            if (!result.getStatus().isSuccess()) {
                showMessage("Cannot find DriveId. Are you authorized to view this file?");
                return;
            }
            DriveId driveId = result.getDriveId();
            DriveFolder folder = driveId.asDriveFolder();
            folder.listChildren(getGoogleApiClient())
                    .setResultCallback(metadataResult);
        }
    };

    final private ResultCallback<DriveApi.MetadataBufferResult> metadataResult = new
            ResultCallback<DriveApi.MetadataBufferResult>() {
                @Override
                public void onResult(DriveApi.MetadataBufferResult result) {
                    if (!result.getStatus().isSuccess()) {
                        showMessage("Problem while retrieving files");
                        return;
                    }
                    mResultsAdapter.clear();
                    mResultsAdapter.append(result.getMetadataBuffer());
                    //showMessage("Successfully listed files.");
                }
            };
}






