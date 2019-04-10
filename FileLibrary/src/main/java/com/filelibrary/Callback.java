package com.filelibrary;

import android.net.Uri;


public interface Callback {
    public void onSuccess(Uri uri,String filePath);
    public void onFailure(String error);
}
