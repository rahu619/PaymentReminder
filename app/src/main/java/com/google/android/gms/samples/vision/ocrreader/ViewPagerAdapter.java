package com.google.android.gms.samples.vision.ocrreader;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private Context context;

    private DetailsActivity detailsActivity;
    private HomeActivity homeActivity;
    private ReportActivity reportActivity;
    private MainActivity mainActivity;

    public ViewPagerAdapter(Context context, FragmentManager fm, HomeActivity homeActivity, MainActivity mainActivity, ReportActivity reportActivity, DetailsActivity detailsActivity) {
        super(fm);
        this.context = context;
        this.detailsActivity = detailsActivity;
        this.homeActivity = homeActivity;
        this.reportActivity = reportActivity;
        this.mainActivity = mainActivity;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return homeActivity;
        }
        else if (position == 1){
            return reportActivity;
        }
        else if(position == 2)
        {
            return detailsActivity;
        }
        else {
            return mainActivity;
        }
    }

    @Override
    public int getCount() {
        return 4;
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
