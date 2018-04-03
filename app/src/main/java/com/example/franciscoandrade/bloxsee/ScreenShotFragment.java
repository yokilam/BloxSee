package com.example.franciscoandrade.bloxsee;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.franciscoandrade.bloxsee.views.student.CloseScreenshot;
import com.github.chrisbanes.photoview.PhotoView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;


/**
 * A simple {@link Fragment} subclass.
 */
public class ScreenShotFragment extends Fragment {

    View v;
    ImageView exitScreenShoot;

    FirebaseStorage storage= FirebaseStorage.getInstance();
    StorageReference storageRef;

    PhotoView imageContainer;

    CloseScreenshot closeScreenshot;

    public ScreenShotFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.fragment_screen_shot, container, false);

        imageContainer= v.findViewById(R.id.imageContainer);
        exitScreenShoot= v.findViewById(R.id.exitScreenShoot);

        exitScreenShoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeScreenshot.closeScreenshot();
            }
        });

        return v;
    }






    public void show(String url) {
        Log.d("LOAD=", "showImage: "+url);
        download(url);
    }

    private void download(String url) {
        ByteArrayOutputStream baos= new ByteArrayOutputStream();
        byte[]data=baos.toByteArray();
        String path= "images/"+url+".jpg";
        storageRef=storage.getReference(path);
        Log.d("REFERENCE==", "download: "+storageRef);


        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {

                Log.d("DOWNLOAD=", "onSuccess: "+uri.toString());
                // Got the download URL for 'users/me/profile.png'
                Picasso.with(getActivity())
                        .load(uri.toString())
                        .into(imageContainer);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        closeScreenshot=(CloseScreenshot)context;
    }
}
