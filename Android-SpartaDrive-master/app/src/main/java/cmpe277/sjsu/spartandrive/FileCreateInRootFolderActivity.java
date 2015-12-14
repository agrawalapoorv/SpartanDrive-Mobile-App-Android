package cmpe277.sjsu.spartandrive;

import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;

import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveApi;
import com.google.android.gms.drive.DriveContents;
import com.google.android.gms.drive.DriveFolder;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.MetadataChangeSet;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

/**
 * Created by namithashetty on 12/13/15.
 */
public class FileCreateInRootFolderActivity extends ConnectionActivity {
    private GridView mResultsListView;
    private ViewAdapter mResultsAdapter;
    private GridView mListViewSamples;
    private static final String TAG = "ConnectionDriveActivity";
    private String driveId;

    @Override
    public void onConnected(Bundle connectionHint) {
        setContentView(R.layout.activity_main);
        mResultsListView = (GridView) findViewById(R.id.listItems);
        mResultsAdapter = new ViewAdapter(this);
        mResultsListView.setAdapter(mResultsAdapter);
        // super.onConnected(connectionHint);
        Log.i(TAG, "Request code" + getIntent().getExtras().getString("fileName"));
            /*String fileName = getIntent().getExtras().getString("fileName");
            DriveId driveId = Drive.DriveApi.getRootFolder(getGoogleApiClient()).getDriveId();
            DriveFolder folder = driveId.asDriveFolder();
            MetadataChangeSet changeSet = new MetadataChangeSet.Builder()
                    .setTitle(fileName).build();
            folder.createFile(getGoogleApiClient(), changeSet, driveContents);
            Intent intent = new Intent(getBaseContext(), MainActivity.class);
            startActivity(intent);*/
        Drive.DriveApi.newDriveContents(getGoogleApiClient())
                .setResultCallback(driveContentsCallback);
    }

    final private ResultCallback<DriveApi.DriveContentsResult> driveContentsCallback = new
            ResultCallback<DriveApi.DriveContentsResult>() {
                @Override
                public void onResult(DriveApi.DriveContentsResult result) {
                    if (!result.getStatus().isSuccess()) {
                        showMessage("Error while trying to create new file contents");
                        return;
                    }
                    final DriveContents driveContents = result.getDriveContents();

                    // Perform I/O off the UI thread.
                    new Thread() {
                        @Override
                        public void run() {
                            // write content to DriveContents
                            OutputStream outputStream = driveContents.getOutputStream();
                            Writer writer = new OutputStreamWriter(outputStream);
                            try {
                                writer.write("Please edit this file after create");
                                writer.close();
                            } catch (IOException e) {
                                Log.e(TAG, e.getMessage());
                            }
                            DriveId driveId;
                            String fileName = getIntent().getExtras().getString("fileName");
                            //String folderName = getIntent().getExtras().getString("folderName");
                            //String driveString = getIntent().getExtras().getString("driveId");

                            //DriveFolder folder = driveId1.asDriveFolder();
                            MetadataChangeSet changeSet = new MetadataChangeSet.Builder()
                                    .setTitle(fileName).build();
                            Drive.DriveApi.getRootFolder(getGoogleApiClient())
                                    .createFile(getGoogleApiClient(), changeSet, driveContents)
                                    .setResultCallback(fileCallback);

                        }
                    }.start();
                }
            };

    final private ResultCallback<DriveFolder.DriveFileResult> fileCallback = new
            ResultCallback<DriveFolder.DriveFileResult>() {
                @Override
                public void onResult(DriveFolder.DriveFileResult result) {
                    if (!result.getStatus().isSuccess()) {
                        showMessage("Error while trying to create the file");
                        return;
                    }
                    showMessage("File created successfully!! Go back to the root folder to view the file");
                }
            };


    /*private static final String TAG = "CreateFileWithCreatorActivity";

    protected static final int REQUEST_CODE_CREATOR = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_file);*/
       /* Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/

       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
   /* }



    @Override
    public void onConnected(Bundle connectionHint) {
       // super.onConnected(connectionHint);
        Log.d("CreateFileActivity","Inside the onConnect method of CreateFileActivity");
       Drive.DriveApi.newDriveContents(mGoogleApiClient).setResultCallback(driveContentsCallback);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("CreateFileActivity","Inside the onActivityResult method of CreateFileActivity");
        switch (requestCode) {
            case REQUEST_CODE_CREATOR:
                if (resultCode == RESULT_OK) {
                    DriveId driveId = (DriveId) data.getParcelableExtra(
                            OpenFileActivityBuilder.EXTRA_RESPONSE_DRIVE_ID);
                    // showMessage("File created with ID: " + driveId);
                }
                finish();
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }

    final ResultCallback<DriveApi.DriveContentsResult> driveContentsCallback =
            new ResultCallback<DriveApi.DriveContentsResult>() {
                @Override
                public void onResult(DriveApi.DriveContentsResult result) {
                    MetadataChangeSet metadataChangeSet = new MetadataChangeSet.Builder()
                            .setMimeType("text/html").build();
                    IntentSender intentSender = Drive.DriveApi
                            .newCreateFileActivityBuilder()
                            .setInitialMetadata(metadataChangeSet)
                            .setInitialDriveContents(result.getDriveContents())
                            .build(mGoogleApiClient);
                    try {
                        startIntentSenderForResult(
                                intentSender, REQUEST_CODE_CREATOR, null, 0, 0, 0);
                    } catch (IntentSender.SendIntentException e) {
                        // Log.w(TAG, "Unable to send intent", e);
                    }
                }
            };*/


}
