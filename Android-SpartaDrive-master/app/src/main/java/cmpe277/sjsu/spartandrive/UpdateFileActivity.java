package cmpe277.sjsu.spartandrive;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.drive.DriveFolder;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.MetadataChangeSet;

/**
 * Created by namithashetty on 12/13/15.
 */
public class UpdateFileActivity extends ConnectionActivity {

    @Override
    public void onConnected(Bundle connectionHint) {
        // super.onConnected(connectionHint);
        //Drive.DriveApi.fetchDriveId(getGoogleApiClient(), "0B8m6vocGpnl3QXQtaTFQOVZjUDQ")
        //.setResultCallback(idCallback);
        String folderName = getIntent().getExtras().getString("fileName");
        String driveId = getIntent().getExtras().getString("driveId");

        //DriveId driveId = Drive.DriveApi.getRootFolder(getGoogleApiClient()).getDriveId();
        DriveId drive = DriveId.decodeFromString(driveId);
        DriveFile file = drive.asDriveFile();
        MetadataChangeSet changeSet = new MetadataChangeSet.Builder()
                .setTitle(folderName).build();
        file.updateMetadata(getGoogleApiClient(), changeSet);
        //Drive.DriveApi.getRootFolder(getGoogleApiClient()).listChildren(getGoogleApiClient())
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        startActivity(intent);
        //.setResultCallback(metadataResult);
    }
}
