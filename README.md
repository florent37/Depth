# Depth

Add some Depth to your fragments

<a href="https://github.com/florent37/Depth/raw/master/sample-debug.apk">Sample apk</a>

[![gif](https://raw.githubusercontent.com/florent37/Depth/master/media/default.gif)](https://github.com/florent37/Depth)
[![gif](https://raw.githubusercontent.com/florent37/Depth/master/media/revert.gif)](https://github.com/florent37/Depth)

**The blue comes from the activity background color**

In your **activity**
```java
final Depth depth = DepthProvider.getDepth(container);
depth
      .animate()
      .reduce(oldFragment)

      .exit(oldFragment)

      .enter(newFragment)
      .start();
```

In your **fragment**
```java
private Depth depth;

@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    this.depth = DepthProvider.getDepth(container);
    return depth.setupFragment(10f, 10f, inflater.inflate(R.layout.fragment_1, container, false));
}

@Override
public void onViewCreated(final View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    depth.onFragmentReady(this);
}
```

# Customize the animations

```java
depth
     .animate()
     .reduce(oldFragment, new ReduceConfiguration()
             .setRotationZ(0f)
             .setRotationX(30f)
             .setDuration(1000)
     )

     .exit(oldFragment, new ExitConfiguration()
             .setFinalXPercent(0f)
             .setFinalYPercent(-1f)
     )
     .enter(newFragment, new EnterConfiguration()
             .setInitialXPercent(0f)
             .setInitialYPercent(1f)
             .setInitialRotationZ(0f)
             .setInitialRotationX(30f)
     )
     .start();
```

[![gif](https://raw.githubusercontent.com/florent37/Depth/master/media/top.gif)](https://github.com/florent37/Depth)
[![gif](https://raw.githubusercontent.com/florent37/Depth/master/media/left.gif)](https://github.com/florent37/Depth)

# Add multiples DepthLayouts

Don't use `depth.setupFragment(`

But manually create your own layout with `DepthRelativeLayoutContainer`and `DepthRelativeLayout`

```xml
<com.github.florent37.depth.DepthRelativeLayoutContainer xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <com.github.florent37.depth.DepthRelativeLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:layout_below="@+id/status_bar"
        android:background="@android:color/white"
        app:depth_value="2dp"
        app:depth_zIndex="0">

        <LinearLayout
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_centerHorizontal="true"
            android:layout_centerVertical="true"
            android:orientation="vertical">

            <View
                android:id="@+id/next"
                android:layout_width="40dp"
                android:layout_height="40dp"
                android:layout_centerInParent="true"
                android:layout_marginBottom="10dp"
                android:background="@drawable/circle_blue" />

            <View
                android:id="@+id/open_reset"
                android:layout_width="40dp"
                android:layout_height="40dp"
                android:layout_centerInParent="true"
                android:background="@drawable/circle_blue" />

        </LinearLayout>

    </com.github.florent37.depth.DepthRelativeLayout>

    <com.github.florent37.depth.DepthRelativeLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_below="@+id/status_bar"
        android:background="@android:color/white"
        app:depth_value="2dp"
        app:depth_zIndex="1">

        <View
            android:id="@+id/status_bar"
            android:layout_width="match_parent"
            android:layout_height="25dp"
            android:background="@color/colorPrimaryDark" />

        <com.github.florent37.awesomebar.AwesomeBar
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:background="@android:color/white"
            android:layout_below="@+id/status_bar"
            app:bar_animatedIcons="false"
            app:bar_primaryColor="@color/colorPrimary"
            app:bar_primaryDarkColor="@color/colorPrimaryDark" />

    </com.github.florent37.depth.DepthRelativeLayout>

</com.github.florent37.depth.DepthRelativeLayoutContainer>
```

[![gif](https://raw.githubusercontent.com/florent37/Depth/master/media/revert.gif)](https://github.com/florent37/Depth)

#Download

<a href='https://ko-fi.com/A160LCC' target='_blank'><img height='36' style='border:0px;height:36px;' src='https://az743702.vo.msecnd.net/cdn/kofi1.png?v=0' border='0' alt='Buy Me a Coffee at ko-fi.com' /></a>

In your module [![Download](https://api.bintray.com/packages/florent37/maven/Depth/images/download.svg)](https://bintray.com/florent37/maven/Depth/_latestVersion)
```groovy
compile 'com.github.florent37:depth:1.0.0'
```

# Changelog

##1.0.0

Initial import

# Community

Forked from [danielzeller/Depth-LIB-Android-](https://github.com/danielzeller/Depth-LIB-Android-)
Thanks for their amazing work !

# Credits

Author: Florent Champigny

<a href="https://plus.google.com/+florentchampigny">
  <img alt="Follow me on Google+"
       src="https://raw.githubusercontent.com/florent37/DaVinci/master/mobile/src/main/res/drawable-hdpi/gplus.png" />
</a>
<a href="https://twitter.com/florent_champ">
  <img alt="Follow me on Twitter"
       src="https://raw.githubusercontent.com/florent37/DaVinci/master/mobile/src/main/res/drawable-hdpi/twitter.png" />
</a>
<a href="https://fr.linkedin.com/in/florentchampigny">
  <img alt="Follow me on LinkedIn"
       src="https://raw.githubusercontent.com/florent37/DaVinci/master/mobile/src/main/res/drawable-hdpi/linkedin.png" />
</a>

#License

    Copyright 2017 florent37, Inc.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
