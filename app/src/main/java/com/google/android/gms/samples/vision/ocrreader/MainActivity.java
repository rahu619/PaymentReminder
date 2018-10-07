/*
 * Copyright (C) The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.android.gms.samples.vision.ocrreader;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.internal.service.Common;

import java.util.Calendar;

import Db.DbHandler;


public class MainActivity extends Fragment implements View.OnClickListener,ToolbarFragment.OnFragmentInteractionListener {

    // Use a compound button so either checkbox or switch widgets work.
    private CompoundButton autoFocus;
    private CompoundButton useFlash;
   // OnDataListener _onDataListener;
//    private TextView statusMessage;
//    private TextView textValue;

    private static final int RC_OCR_CAPTURE = 9003;
    private static final String TAG = "MainActivity";
    public DetailsActivity detailsActivity;

    public MainActivity() {

    }

//    @Override
//    public void OnAttach(Activity activity){
//        super.onAttach(activity);
//        try {
//            _onDataListener = (OnDataListener) activity;
//        }
//        catch(ClassCastException e){
//
//        }
//
//
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.activity_main, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        autoFocus = getView().findViewById(R.id.auto_focus);
        useFlash = getView().findViewById(R.id.use_flash);
        getView().findViewById(R.id.read_text).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.read_text) {
            // launch Ocr capture activity.
            Intent intent = new Intent(getActivity(), OcrCaptureActivity.class);
            intent.putExtra(OcrCaptureActivity.AutoFocus, autoFocus.isChecked());
            intent.putExtra(OcrCaptureActivity.UseFlash, useFlash.isChecked());

            startActivityForResult(intent, RC_OCR_CAPTURE);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == RC_OCR_CAPTURE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    String text = data.getStringExtra(OcrCaptureActivity.TextBlockObject);
                    Log.d(TAG, "Text read: " + text);

                    TabLayout tabhost = (TabLayout) getActivity().findViewById(R.id.tabLayout);
                    tabhost.getTabAt(2).select();
                    detailsActivity.setBillContent(text);


                } else {

                }
            } else {

            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

//    public interface OnDataListener{
//        public void OnDataReceived(String content);
//    }
}
