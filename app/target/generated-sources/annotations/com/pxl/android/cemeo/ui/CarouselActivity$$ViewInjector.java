// Generated code from Butter Knife. Do not modify!
package com.pxl.android.cemeo.ui;

import android.view.View;
import butterknife.Views.Finder;

public class CarouselActivity$$ViewInjector {
  public static void inject(Finder finder, com.pxl.android.cemeo.ui.CarouselActivity target, Object source) {
    View view;
    view = finder.findById(source, 2131099719);
    target.indicator = (com.viewpagerindicator.TitlePageIndicator) view;
    view = finder.findById(source, 2131099720);
    target.pager = (android.support.v4.view.ViewPager) view;
  }
}
