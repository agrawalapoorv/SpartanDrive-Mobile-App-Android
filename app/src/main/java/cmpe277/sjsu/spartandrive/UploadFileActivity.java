package cmpe277.sjsu.spartandrive;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.google.api.client.http.ByteArrayContent;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.google.api.services.drive.model.ParentReference;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UploadFileActivity  extends ConnectionActivity implements OnClickListener {
    EditText editTextEmail, editTextSubject, editTextMessage;
    Button btnSend, btnAttachment;
    String email, subject, message, attachmentFile;

    Uri URI = null;
    private static final int PICK_FROM_GALLERY = 101;
    int columnIndex;
    StringBuilder sb = new StringBuilder();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_upload_file);
        setContentView(R.layout.activity_email);
        editTextEmail = (EditText) findViewById(R.id.editTextTo);
        editTextSubject = (EditText) findViewById(R.id.editTextSubject);
        editTextMessage = (EditText) findViewById(R.id.editTextMessage);
        btnAttachment = (Button) findViewById(R.id.buttonAttachment);
        btnSend = (Button) findViewById(R.id.buttonSend);
        btnSend.setOnClickListener(this);
        btnAttachment.setOnClickListener(this);
    }


    public void uploadFile() throws IOException {

        FileList files = DriveRestArctivity.mService.files().list().setQ("title = 'MyFiles' and trashed = false").execute();
        String directoryId = "";

        if (files.getItems().size() == 0) {
            File appDir = new File();
            appDir.setTitle("MyFiles");
            appDir.setMimeType("application/vnd.google-apps.folder");
            appDir.setDescription("Sample app folder");

            File directory = DriveRestArctivity.mService.files().insert(appDir).execute();
            directoryId = directory.getId();
        } else {
            directoryId = files.getItems().get(0).getId();
        }

        String randomUUID = UUID.randomUUID().toString();

        File sampleFile = new File();

        sampleFile.setTitle("file" + randomUUID);
        sampleFile.setMimeType("text/plain");

        List<ParentReference> parents = new ArrayList<ParentReference>();
        ParentReference fileParent = new ParentReference();
        fileParent.setId(directoryId);
        parents.add(fileParent);
        sampleFile.setParents(parents);

        File uploadedFile = DriveRestArctivity.mService.files().insert(sampleFile, new ByteArrayContent("text/plain", sb.toString().getBytes())).execute();



    }

    @Override
    public void onClick(View v) {
        if (v == btnAttachment) {
            openGallery();

        }
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_FROM_GALLERY && resultCode == RESULT_OK) {
            /**
             * Get Path
             */
            //File uploadedFile = mService.files().insert(sampleFile, new ByteArrayContent("text/plain", randomUUID.getBytes())).execute();
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,filePathColumn, null, null, null);
            cursor.moveToFirst();
            columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            attachmentFile = cursor.getString(columnIndex);
            BufferedReader br = null;
            try {
                br = new BufferedReader(new FileReader(attachmentFile));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                sb = new StringBuilder();
                String line = null;
                try {
                    line = br.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                while (line != null) {
                    sb.append(line);
                    sb.append(System.lineSeparator());
                    try {
                        line = br.readLine();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                String everything = sb.toString();
            } finally {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            Log.e("Attachment Path:", attachmentFile);
            URI = Uri.parse("file://" + attachmentFile);

            cursor.close();
        }
    }
    public void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.putExtra("return-data", true);
        startActivityForResult(
                Intent.createChooser(intent, "Complete action using"),
                PICK_FROM_GALLERY);

    }
}