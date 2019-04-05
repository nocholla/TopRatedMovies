package com.nocholla.top.rated.movies.fragments;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.nocholla.top.rated.movies.R;
import com.nocholla.top.rated.movies.features.about_us.AboutUsActivity;
import com.nocholla.top.rated.movies.features.help.HelpActivity;
import com.nocholla.top.rated.movies.util.Constants;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class BottomSheetFragment extends BottomSheetDialogFragment {

    private LinearLayout bottomSheetAboutUs;
    private LinearLayout bottomSheetHelp;
    private LinearLayout bottomSheetShare;
    private LinearLayout bottomSheetContactUs;

    public BottomSheetFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_bottom_sheet_dialog, container, false);

        // About Us
        bottomSheetAboutUs = view.findViewById(R.id.bottom_sheet_about_us);
        bottomSheetAboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AboutUsActivity.class));
                //getActivity().finish();
            }
        });

        // Help
        bottomSheetHelp = view.findViewById(R.id.bottom_sheet_help);
        bottomSheetHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), HelpActivity.class));
                //getActivity().finish();
            }
        });

        // Share
        bottomSheetShare = view.findViewById(R.id.bottom_sheet_share);
        bottomSheetShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  Begin Share Business
                List<Intent> targetShareIntents = new ArrayList<Intent>();
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");

                PackageManager pm = getActivity().getPackageManager();
                List<ResolveInfo> resInfos = pm.queryIntentActivities(shareIntent, 0);
                if (!resInfos.isEmpty()) {
                    for (ResolveInfo resInfo : resInfos) {

                        String packageName = resInfo.activityInfo.packageName;

                        if (packageName.contains("com.twitter.android")
                                || packageName.contains("com.facebook.katana")
                                || packageName.contains("com.whatsapp")
                                || packageName.contains("com.google.android.apps.plus")
                                || packageName.contains("com.google.android.talk")
                                || packageName.contains("com.slack")
                                || packageName.contains("com.google.android.gm")
                                || packageName.contains("com.facebook.orca")
                                || packageName.contains("com.yahoo.mobile")
                                || packageName.contains("com.skype.raider")
                                || packageName.contains("com.android.mms")
                                || packageName.contains("com.linkedin.android")
                                || packageName.contains("com.google.android.apps.messaging")) {

                            // Begin Share Saved Image
                            Intent intent = new Intent();
                            intent.setComponent(new ComponentName(packageName, resInfo.activityInfo.name));
                            intent.putExtra("AppName", resInfo.loadLabel(pm).toString());
                            intent.setAction(Intent.ACTION_SEND);
                            //intent.setType("image/*");
                            intent.setType("text/plain");
                            intent.putExtra(Intent.EXTRA_SUBJECT, "Check out TMDB App!");
                            intent.putExtra(Intent.EXTRA_TEXT, "Check out our top rated movies!"
                                    + " \n "
                                    + " \n "
                                    + "The Movie Database (TMDb) is a community built movie and TV database. Every piece of data has been added by our amazing community dating back to 2008. TMDb\\'s strong international focus and breadth of data is largely unmatched and something we\\'re incredibly proud of. Put simply, we live and breathe community and that\\'s precisely what makes us different."
                                    + " \n "
                                    + " \n "
                                    + "Download and share the TMDB Movie App!");

                            intent.setPackage(packageName);
                            targetShareIntents.add(intent);
                            // End Share
                        }
                    }
                    if (!targetShareIntents.isEmpty()) {
                        Collections.sort(targetShareIntents, new Comparator<Intent>() {
                            @Override
                            public int compare(Intent o1, Intent o2) {
                                return o1.getStringExtra("AppName").compareTo(o2.getStringExtra("AppName"));
                            }
                        });
                        Intent chooserIntent = Intent.createChooser(targetShareIntents.remove(0), "Share Via");
                        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, targetShareIntents.toArray(new Parcelable[]{}));
                        getActivity().startActivity(chooserIntent);
                    } else {
                        Toast.makeText(getActivity(), getString(R.string.toast_no_app_to_share), Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        // Contact Us
        bottomSheetContactUs = (LinearLayout) view.findViewById(R.id.bottom_sheet_contact_us);
        bottomSheetContactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String mailTo = "mailto:" + Constants.TMDB_EMAIL;
                    Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                    emailIntent.setData(Uri.parse(mailTo));
                    startActivity(emailIntent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(getActivity(), getString(R.string.toast_no_application_can_handle_this_request_email),  Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });

        return view;

    }

}

