
package com.pxl.android.cemeo.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.github.kevinsawicki.wishlist.SingleTypeAdapter;
import com.github.kevinsawicki.wishlist.Toaster;
import com.github.kevinsawicki.wishlist.ViewUtils;
import com.pxl.android.cemeo.R;
import com.pxl.android.cemeo.R.id;
import com.pxl.android.cemeo.R.layout;
import com.pxl.android.cemeo.authenticator.LogoutService;

import java.util.Collections;
import java.util.List;


public abstract class CreateMeetingActivity<E> extends SherlockActivity implements LoaderCallbacks<E> {

    @Override
    public Loader<E> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<E> loader, E data) {


    }

    @Override
    public void onLoaderReset(Loader<E> loader) {

    }
}
