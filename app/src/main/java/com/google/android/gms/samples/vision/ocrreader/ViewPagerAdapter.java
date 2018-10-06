package com.google.android.gms.samples.vision.ocrreader;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private Context context;

    public ViewPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new HomeActivity();
        }
//        else if (position == 1){
//            return new ReportActivity();
//        }
        else if(position == 1)
        {
            return new DetailsActivity();
        }
        else {
            return new MainActivity();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
//        switch (position) {
//            case 0:
//                return context.getString(R.string.tab_home);
//            case 1:
//                return context.getString(R.string.tab_details);
//            case 2:
//                return context.getString(R.string.tab_camera);
//            default:
//                return null;
//        }
        return null;
    }
}
