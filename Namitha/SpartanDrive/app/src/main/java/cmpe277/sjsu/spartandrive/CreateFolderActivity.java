package cmpe277.sjsu.spartandrive;

/**
 * Created by namithashetty on 11/25/15.
 */

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;

import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveApi;
import com.google.android.gms.drive.DriveFolder;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.MetadataChangeSet;

public class CreateFolderActivity extends ConnectionActivity {
    private static final String TAG = "PickFolderWithOpenerActivity";

    @Override
    public void onConnected(Bundle connectionHint) {
        super.onConnected(connectionHint);
        Log.i(TAG, "Request code" + getIntent().getExtras().getString("folderName"));
        String folderName = getIntent().getExtras().getString("folderName");
        DriveId driveId = Drive.DriveApi.getRootFolder(getGoogleApiClient()).getDriveId();
        DriveFolder folder = driveId.asDriveFolder();
        MetadataChangeSet changeSet = new MetadataChangeSet.Builder()
                .setTitle(folderName).build();
        folder.createFolder(getGoogleApiClient(), changeSet);
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        startActivity(intent);
    }
}
