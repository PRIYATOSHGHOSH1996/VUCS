package com.vucs.helper;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.BufferedSink;

public class ProgressRequestBody extends RequestBody {
    private File mFile;
    private String mPath;
    private UploadCallbacks mListener;
    private String content_type;
    private String title;
    private static final int DEFAULT_BUFFER_SIZE = 2048;//10240

    public interface UploadCallbacks {
        void onProgressUpdate(int percentage ,String title);

        void onError();

        void onFinish( String title);
    }

    public ProgressRequestBody(final File file, String content_type, final UploadCallbacks listener, String title) {
        this.content_type = content_type;
        mFile = file;
        mListener = listener;
        this.title = title;
    }


    @Override
    public MediaType contentType() {
        return MediaType.parse(content_type + "/*");
    }

    @Override
    public long contentLength() throws IOException {
        return mFile.length();
    }

    @Override
    public void writeTo(@NonNull BufferedSink sink) throws IOException {
        long fileLength = mFile.length();
        byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
        FileInputStream in = new FileInputStream(mFile);
        long uploaded = 0;

        try {
            int read;
            Handler handler = new Handler(Looper.getMainLooper());
            int num = 0;
            while ((read = in.read(buffer)) != -1) {

                // update progress on UI thread
                int progress = (int) (100 * uploaded / fileLength);
                if( progress > num + 2 ){
                    handler.post(new ProgressUpdater(uploaded, fileLength, title));
                    num = progress;
                }

                uploaded += read;
                sink.write(buffer, 0, read);
            }
            handler.post(new ProgressUpdater(fileLength, fileLength, title));
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            in.close();
        }
    }

    private class ProgressUpdater implements Runnable {
        private long mUploaded;
        private long mTotal;
        private String title;
        public ProgressUpdater(long uploaded, long total ,String title) {
            mUploaded = uploaded;
            mTotal = total;
            this.title = title;
        }

        @Override
        public void run() {
            try {
                mListener.onProgressUpdate((int) (100 * mUploaded / mTotal),title);
                if (mUploaded == mTotal)
                    mListener.onFinish(title);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}