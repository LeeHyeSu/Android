package ddwu.mobile.lecture.multimedia.report04_01_20160989;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddMemoActivity extends AppCompatActivity {

    private static final int REQUEST_TAKE_PHOTO = 200;

    private String mCurrentPhotoPath;

    ImageView ivPhoto;
    EditText etMemo;

    MemoDBHelper helper;
    private final static String DB_NAME ="memo_db";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_memo);

        helper = new MemoDBHelper(this);

        ivPhoto = (ImageView)findViewById(R.id.ivPhoto);
        etMemo = (EditText)findViewById(R.id.etMemo);

        ivPhoto.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
//                    외부 카메라 호출
                    dispatchTakePictureIntent();
                    setPic();
                    return true;
                }
                return false;
            }
        });

    }

    private void dispatchTakePictureIntent() {
        Intent takePicktureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(takePicktureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            if(photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "ddwu.mobile.lecture.multimedia.report04_01_20160989.fileprovider",
                        photoFile);
                takePicktureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePicktureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    private void setPic() {
        // Get the dimensions of the View
        int targetW = ivPhoto.getWidth();
        int targetH = ivPhoto.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
//        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        ivPhoto.setImageBitmap(bitmap);
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btnSave:
//                DB에 촬영한 사진의 파일 경로 및 메모 저장
                SQLiteDatabase db = helper.getWritableDatabase();

                ContentValues row = new ContentValues();
                row.put(MemoDBHelper.PATH, mCurrentPhotoPath);
                row.put(MemoDBHelper.MEMO, etMemo.getText().toString());

                db.insert(MemoDBHelper.TABLE_NAME, null, row);

                helper.close();
                finish();
                Toast.makeText(this, "Save!", Toast.LENGTH_SHORT).show();
            case R.id.btnCancel:
                File imgFile = new File(mCurrentPhotoPath);
                imgFile.delete();

                finish();
                break;
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            File imgFile = new File(mCurrentPhotoPath);

            if(imgFile.exists()) {
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                ivPhoto.setImageBitmap(myBitmap);
            }
        }
    }



}
