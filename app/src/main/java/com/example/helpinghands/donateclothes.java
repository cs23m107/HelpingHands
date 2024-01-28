package com.example.helpinghands;

import static com.example.helpinghands.R.id.progressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.squareup.picasso.Picasso;
import android.widget.ProgressBar;
import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class donateclothes extends AppCompatActivity {
    int flag;
    File f;  // global f
    Uri contentUri;  // golbal uri
    String imageFileName;  //got from gallery fun
    public static final int CAMERA_PERM_CODE = 101;
    public static final int CAMERA_REQUEST_CODE = 102;
    ImageView selecteImage;
    Button takePhoto, gallery,submit_button;
    String currentPhotoPath;
     //ProgressBar progressBar;

    EditText clothes_address,clothes_mobile;

    private FirebaseStorage storage;
    private FirebaseFirestore firestore;

    private String PhotoUrl;
    private FirebaseAuth firebaseAuth;

    private String CurrentUserId;
    StorageReference storageReference;
    public static final int REQUEST_IMAGE_CAPTURE = 101;
    public static final int GALLERY_REQUEST_CODE = 105;
    private final int FINE_PERMISSION_CODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donateclothes);

        selecteImage = (ImageView) findViewById(R.id.clothesimage);
        takePhoto = (Button) findViewById(R.id.Camera);
        gallery = (Button) findViewById(R.id.Gallery);
        clothes_address=(EditText) findViewById(R.id.clothesaddress);
        clothes_mobile=(EditText) findViewById(R.id.clothesmobile) ;
        submit_button=(Button) findViewById(R.id.clothessubmit);
         // progressBar=(ProgressBar) findViewById(R.id.progressBar);

        firestore= FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        storage=FirebaseStorage.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
        CurrentUserId=firebaseAuth.getCurrentUser().getUid();



        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                askCameraPermissions();
            }
        });
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(gallery, GALLERY_REQUEST_CODE);
            }
        });
        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* if(flag==0)
                    uploadImageToFirebase(f.getName(), contentUri);
                if(flag==1)
                    uploadImageToFirebase(imageFileName, contentUri);*/
                //uploadcontactdetails();
                db_upload();
            }
        });

    }
    private void db_upload(){
        String address = clothes_address.getText().toString().trim();
        String mobile = clothes_mobile.getText().toString().trim();
        if (TextUtils.isEmpty(address) && TextUtils.isEmpty(mobile)) {
            Toast.makeText(donateclothes.this, "Please fill address & mobile no.", Toast.LENGTH_SHORT).show();
        }
        Map<String,Object> data =new HashMap<>();
        data.put("Address",address);
        data.put("mobile",mobile);
        data.put("Photo",PhotoUrl);
        data.put("Userid",CurrentUserId);
        firestore.collection("clothesinfo").add(data);
    }

    private void uploadcontactdetails() {
        String address = clothes_address.getText().toString().trim();
        String mobile = clothes_mobile.getText().toString().trim();
        if (TextUtils.isEmpty(address) && TextUtils.isEmpty(mobile)) {
            Toast.makeText(donateclothes.this, "Please fill address & mobile no.", Toast.LENGTH_SHORT).show();
        } else {
            // FirebaseDatabase database= FirebaseDatabase.getInstance();
            DocumentReference documentReference = firestore.collection("clothesinfo").document(CurrentUserId);
            bookuserdetails bookuserdetails = new bookuserdetails(address, mobile,PhotoUrl,CurrentUserId);
        /*Map<String, Object> user=new HashMap<>();
        user.put("address",address);
        user.put("mobil",mobile);
        user.put("photo",PhotoUrl);*/

            documentReference.set(bookuserdetails, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        //  DocId= documentReference.getId();
                        //  bookuserdetails.setDocID(DocId);
                        documentReference.set(bookuserdetails,SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(donateclothes.this,"Uploaded Successfully",Toast.LENGTH_SHORT).show();
                                }
                            }

                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(donateclothes.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }

            });

        }
    }
    private void askCameraPermissions () {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERM_CODE);
        } else {
            //openCamera();
            dispatchTakePictureIntent();
        }

    }


    @Override
    public void onRequestPermissionsResult ( int requestCode, @NonNull String[] permissions,
                                             @NonNull int[] grantResults){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERM_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //  openCamera();
                dispatchTakePictureIntent();

            } else {
                Toast.makeText(this, "Camera Persmission Required", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void openCamera () {
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(i, 102);
    }

    @Override
    protected void onActivityResult ( int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == CAMERA_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                f = new File(currentPhotoPath);    //   Here f to global variable
                selecteImage.setImageURI(Uri.fromFile(f));
                Log.d("tag", "Absolute Url of Image is " + Uri.fromFile(f));
                Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                contentUri = Uri.fromFile(f);       //   Here contentUri to global variable
                mediaScanIntent.setData(contentUri);
                this.sendBroadcast(mediaScanIntent);
                flag=0;
                //  progressBar.setVisibility(View.VISIBLE);
                uploadImageToFirebase(f.getName(), contentUri);
                // progressBar.setVisibility(View.GONE);
            }
        }


        if (requestCode == GALLERY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                contentUri = data.getData();
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                imageFileName = "JPEG_" + timeStamp + "." + getFileExt(contentUri);
                Log.d("tag", "onActivityResult:Gallery Image Uri" + imageFileName);
                selecteImage.setImageURI(contentUri);
                flag=1;
                //progressBar.setVisibility(View.VISIBLE);
                uploadImageToFirebase(imageFileName, contentUri);
                // progressBar.setVisibility(View.GONE);
            }
        }
    }


    private File createImageFile () throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        //  File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for  use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void dispatchTakePictureIntent () {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                //    Uri photoURI = FileProvider.getUriForFile(this,
                //          "com.example.andriod.fileprovider",
                //        photoFile);
                Uri photoURI = FileProvider.getUriForFile(this, "com.example.helpinghands.andriod.fileprovider", photoFile);

                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE);
            }
        }
    }

    private String getFileExt (Uri contentUri){
        ContentResolver c = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(c.getType(contentUri));
    }

    private void uploadImageToFirebase (String name, Uri contentUri){
       // progressBar.setVisibility(View.VISIBLE);
        StorageReference image = storageReference.child("images/" + name);
        image.putFile(contentUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                image.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {

                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(selecteImage);
                        PhotoUrl=uri.toString();

                        Log.d("tag","OnSucess: Uploaded Image URl is "+ uri.toString());
                    }

                });
                Toast.makeText(donateclothes.this, "Image is Uploaded", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(donateclothes.this, "Upload Failed", Toast.LENGTH_SHORT).show();
            }
        });
      //  progressBar.setVisibility(View.GONE);

    }
}