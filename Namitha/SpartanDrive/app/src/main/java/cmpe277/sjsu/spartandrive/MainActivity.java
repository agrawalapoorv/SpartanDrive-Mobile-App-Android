package cmpe277.sjsu.spartandrive;

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
import android.widget.ListView;

import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.PendingResults;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveApi;
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


public class MainActivity extends ConnectionActivity  {

    private ViewAdapter mViewAdapter;
    private static final int REQUEST_CODE_OPENER = 1;
    private static final String TAG = "ConnectionDriveActivity";

    private ListView mResultsListView;
    private ViewAdapter mResultsAdapter;
    private ListView mListViewSamples;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_main);
        mResultsListView = (ListView) findViewById(R.id.listItems);
        mResultsAdapter = new ViewAdapter(this);
        mResultsListView.setAdapter(mResultsAdapter);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(),  CreateFolderInFolderActivity.class);
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
        //final PendingResult sActivities = Drive.DriveApi.newDriveContents(getGoogleApiClient());
        mListViewSamples = (ListView) findViewById(R.id.listItems);
        mListViewSamples.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int i, long arg3) {
                Drive.DriveApi.fetchDriveId(getGoogleApiClient(), "0B8m6vocGpnl3TVh6bnJ0eUxJYlU")
                        .setResultCallback(idCallback);
            }
        });
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        super.onConnected(connectionHint);
        Log.i(TAG, "Root ID" + getGoogleApiClient());
        Drive.DriveApi.fetchDriveId(getGoogleApiClient(), "0B8m6vocGpnl3VGVzelctREUxYlk")
                .setResultCallback(idCallback);
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
                    showMessage("Successfully listed files.");
                }
            };
}






