package com.filelibrary;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import com.filelibrary.exception.ActivityOrFragmentNullException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.WeakReference;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import cropper.CropImage;

public class Utils extends AppCompatActivity {
    private static final int MY_CAMERA_PERMISSION_CODE = 5916;
    private static final int REQUEST_WRITE_PERMISSIONS = 7623;
    private static final int CAMERA_REQUEST = 9912;
    private static final int CROP_IMAGE_REQUEST = 8212;
    private static final int CHOOSE_FILE_REQUEST_CODE = 2376;
    private static WeakReference<Activity> activityParent;


    public static ActivityBuilder with(Fragment fragment) throws ActivityOrFragmentNullException {

        if (fragment == null) {
            throw new ActivityOrFragmentNullException("Fragment is null");
        }
        activityParent = new WeakReference<>((Activity) fragment.getActivity());
        return new ActivityBuilder();
    }

    public static ActivityBuilder with(Activity activity) throws ActivityOrFragmentNullException {
        activityParent = new WeakReference<>(activity);
        if (activityParent.get() == null) {
            throw new ActivityOrFragmentNullException("Activity is null");
        }
        return new ActivityBuilder();
    }


    public static class ActivityBuilder {
        public Builder getImageFile() {
            return getFile(new String[]{"image/*"});
        }

        public Builder getFile(String[] mimeType) {
            return new Builder(mimeType,false,false);
        }

        public Builder getFile() {
            return new Builder(false,false);
        }

        public Builder getImageFromCamera() {
            return new Builder(true,true);
        }

        public String copyToAppDirectories(String filePath,String directoryName) throws IOException{
            File src=new File(filePath);
            File directory = activityParent.get().getDir(directoryName, Context.MODE_PRIVATE);
            //noinspection ResultOfMethodCallIgnored
            directory.mkdir();
            File des = new File(directory,src.getName());
                InputStream in = new FileInputStream(src);
                try {
                    OutputStream out = new FileOutputStream(des);
                    try {
                        // Transfer bytes from in to out
                        byte[] buf = new byte[1024];
                        int len;
                        while ((len = in.read(buf)) > 0) {
                            out.write(buf, 0, len);
                        }
                    } finally {
                        out.close();
                    }
                } finally {
                    in.close();
                }
                return des.getAbsolutePath();

        }

        public String moveToAppDirectories(String filePath,String directoryName) throws IOException {
            String path = copyToAppDirectories(filePath,directoryName);
            File src = new File(filePath);
            if (src.exists())
                src.delete();

            return path;
        }

    }


    public static class Builder {
        private Callback callback = null;
        private String filePath;
        private String cropFilePath;
        private String fileName;
        private boolean isSetFileName;
        private Uri fileUri = null;
        private boolean crop = false;
        private boolean compress = false;
        private String[] mimeType = null;
        private String directoryName = null;
        private static Builder builder = null;
        private boolean camera;
        private boolean isDeleteFile;

        public Builder(boolean camera,boolean isDeleteFile) {
            builder=this;
            fileName = System.currentTimeMillis() / 1000 + "";
            isSetFileName =false;
            this.camera = camera;
            this.isDeleteFile = isDeleteFile;
        }

        public Builder(String[] a,boolean camera,boolean isDeleteFile) {
            builder=this;
            fileName = System.currentTimeMillis() / 1000 + "";
            mimeType = a;
            isSetFileName =false;
            this.camera = camera;
            this.isDeleteFile = isDeleteFile;
        }

        public static synchronized void notifyPermissionsChange(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            if (activityParent.get() == null || builder == null) {
                return;
            }
            if (requestCode == REQUEST_WRITE_PERMISSIONS) {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                         builder.chooseFile();
                } else {
                    if (builder.callback != null)
                    builder.callback.onFailure("Please give storage permission");
                    // Toast.makeText(this, "Please give storage permission", Toast.LENGTH_LONG).show();
                }
            }
            if (requestCode == MY_CAMERA_PERMISSION_CODE) {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                        builder.capture();
                } else {
                    if (builder.callback != null)
                    builder.callback.onFailure("Please give camera and storage permission");
                    //Toast.makeText(this, "Please give camera permission", Toast.LENGTH_LONG).show();
                }

            }

        }

        public static synchronized void notifyActivityChange(int requestCode, int resultCode, @Nullable Intent data) {
            if (activityParent.get() == null || builder == null) {
                return;
            }
            if (requestCode == CAMERA_REQUEST) {
                try {
                    if (resultCode == Activity.RESULT_OK) {
                        if (builder.crop) {
                            builder.setCrop(builder.fileUri);
                        } else if (builder.compress) {
                            builder.compress(builder.fileUri);

                        } else {
                            if (builder.callback != null)
                            builder.callback.onSuccess(builder.fileUri, builder.filePath);
                        }
                    } else {
                        if (builder.fileUri != null) {
                            File deleteFile = new File(builder.fileUri.getPath());
                            if (deleteFile.exists()) {
                                deleteFile.delete();
                            }
                        }
                        builder.fileUri = null;
                        builder.fileName = "";
                        builder.filePath = "";
                        if (builder.callback != null)
                        builder.callback.onFailure("crop image error");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            if (requestCode == CROP_IMAGE_REQUEST) {
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                if (resultCode == RESULT_OK) {
                    if (builder.isDeleteFile) {
                        File oldFile = new File(builder.filePath);
                        if (oldFile.exists())
                            oldFile.delete();
                    }
                    builder.fileUri = result.getUri();
                    builder.filePath = builder.cropFilePath;
                    if (builder.compress) {
                        builder.isDeleteFile = true;
                        builder.compress(builder.fileUri);
                    } else {
                        if (builder.callback != null)
                            builder.callback.onSuccess(builder.fileUri, builder.filePath);
                    }
                } else {
                    builder.fileUri = null;
                    builder.fileName = "";
                    builder.filePath = "";

                }
            }
            if (requestCode == CHOOSE_FILE_REQUEST_CODE) {
                if (data == null) return;
                builder.fileUri = data.getData();
                builder.filePath = FilePath.getPath(activityParent.get(),builder.fileUri);
                if (!builder.isSetFileName) {
                    builder.fileName = FilePath.getFilename(activityParent.get(), builder.fileUri);
                   int i = builder.fileName.lastIndexOf('.');
                    builder.fileName = builder.fileName.substring(0,i);
                }
                String type = activityParent.get().getContentResolver().getType(builder.fileUri);
                if (type != null && type.startsWith("image/")) {
                    if (builder.crop) {
                        builder.setCrop(builder.fileUri);
                    } else if (builder.compress) {
                        builder.compress(builder.fileUri);

                    } else {
                        if (builder.callback != null)
                            builder.callback.onSuccess(builder.fileUri, builder.filePath);
                    }

                } else {
                    if (builder.callback != null)
                        builder.callback.onSuccess(builder.fileUri, FilePath.getPath(activityParent.get(),builder.fileUri));
                }
            }
        }

        private  void setCrop(Uri uri) {
            String rootPath;
            if (directoryName == null) {
                rootPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                        .getAbsolutePath() + "/Cropped/";
            }else {
                rootPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                        .getAbsolutePath() + "/" + directoryName + "/";
            }
            File fileDirectory = new File(rootPath);
            if (!fileDirectory.exists()) {
                fileDirectory.mkdirs();
            }
            File image = new File(fileDirectory, fileName + ".jpg");
            cropFilePath = image.getAbsolutePath();
            CropImage.activity(uri)
                    .setOutputUri(fileToUri(image))
                    .setRequestCode(CROP_IMAGE_REQUEST)
                    .start(activityParent.get());
        }

        private  void compress(Uri uri) {
            try {
                InputStream is = null;
                is = activityParent.get().getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(is);
                if (isDeleteFile) {
                    File oldFile = new File(filePath);
                    if (oldFile.exists())
                        oldFile.delete();
                }
                saveImageToApp(bitmap);
                if (is != null) {
                    is.close();
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private  void capture() {
            if (activityParent.get() == null)
                return;
            File file = null;
            // Create the File where the photo should go
            try {
                file = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                ex.printStackTrace();
            }


            fileUri = fileToUri(file);

            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
            cameraIntent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

            activityParent.get().startActivityForResult(cameraIntent, CAMERA_REQUEST);


        }

        private static Uri fileToUri(File file) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                return FileProvider.getUriForFile(activityParent.get(), activityParent.get().getPackageName()+".file_library_provider", file);
            } else {
                return Uri.fromFile(file);
            }
        }

        private  File createImageFile() throws IOException {
            // Create an image file name

            File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            File image = null;
            try {
                //noinspection ResultOfMethodCallIgnored
                storageDir.mkdirs();
                image = new File(storageDir, fileName + ".jpg");

                /*image = File.createTempFile(
                        "12345",  *//* prefix *//*
                        ".jpg",         *//* suffix *//*
                        storageDir      *//* directory *//*
                );*/
                Log.e("file nMAE", fileName);
                // Save a file: path for use with ACTION_VIEW intents
                filePath = image.getAbsolutePath();
                Log.e("file nMAE", filePath);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return image;
        }

        private  void saveImageToApp(Bitmap bmp) {
            try {
                try {
                    float width = 1024.0f;
                    int nh = (int) (bmp.getHeight() * (width / bmp.getWidth()));
                    Bitmap scaled = Bitmap.createScaledBitmap(bmp, (int) width, nh, true);
                    writeFile(fileName, scaled);
                    scaled.recycle();
                    bmp.recycle();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private  void writeFile(String filename, Bitmap message) {
            if (activityParent.get() == null) {
                return;
            }
            try {
                String rootPath;
                if (directoryName == null) {
                    rootPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                            .getAbsolutePath() + "/Compress/";
                }else {
                    rootPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                            .getAbsolutePath() + "/" + directoryName + "/";
                }
                File fileDirectory = new File(rootPath);
                if (!fileDirectory.exists()) {
                    fileDirectory.mkdirs();
                }
                filename += ".jpg";
                File fileWithinMyDir = new File(fileDirectory, filename); //Getting a file within the dir.
                FileOutputStream fileOutputStream = new FileOutputStream(fileWithinMyDir);
                message.compress(Bitmap.CompressFormat.JPEG, 80, fileOutputStream);
                filePath = fileWithinMyDir.getAbsolutePath();
                if (callback != null)
                callback.onSuccess(fileToUri(fileWithinMyDir), filePath);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private  void copyFile(File src, File dst) throws IOException {
            InputStream in = new FileInputStream(src);
            try {
                OutputStream out = new FileOutputStream(dst);
                try {
                    // Transfer bytes from in to out
                    byte[] buf = new byte[1024];
                    int len;
                    while ((len = in.read(buf)) > 0) {
                        out.write(buf, 0, len);
                    }
                } finally {
                    out.close();
                }
            } finally {
                in.close();
            }
        }

        private void chooseFile() {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("*/*");
            if (mimeType != null)
                intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeType);
            Intent i = Intent.createChooser(intent, "File");
            activityParent.get().startActivityForResult(i, CHOOSE_FILE_REQUEST_CODE);
        }

        public Builder compressEnable(boolean a) {
            compress = a;
            return this;
        }

        public Builder cropEnable(boolean a) {
            crop = a;
            return this;
        }

        public Builder setDirectoryName(String path){
            directoryName =path;
            return this;
        }

        public Builder getResult(Callback callback1) {
            callback = callback1;
            return this;
        }

        public Builder fileName(String name) {
            fileName = name;
            isSetFileName = true;
            return this;


        }

        public void start() {
            if (activityParent.get() == null) {
                return;
            }
            if (camera == true) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ActivityCompat.checkSelfPermission(activityParent.get(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(activityParent.get(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(activityParent.get(), new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_CAMERA_PERMISSION_CODE);
                    } else {
                        capture();
                    }
                } else {
                    capture();
                }

            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ActivityCompat.checkSelfPermission(activityParent.get(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(activityParent.get(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_PERMISSIONS);
                    } else {
                        chooseFile();

                    }
                } else {
                    chooseFile();
                }
            }
        }

    }


}
