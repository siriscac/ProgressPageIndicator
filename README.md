[ ![Download](https://api.bintray.com/packages/siriscac/maven/ProgressPageIndicator/images/download.svg) ](https://bintray.com/siriscac/maven/ProgressPageIndicator/_latestVersion)
# ProgressPageIndicator
A native library for Android that provides a filling Page Indicator for viewpagers

![Sample Screenshot](https://raw.githubusercontent.com/siriscac/ProgressPageIndicator/master/screens/screen.png)

*For a working implementation, Have a look at the Sample Project - app module*

1. Include the library as local library project or get it from jCenter(). 

  Add this to your build.gradle
  ```groovy
   repositories {
     maven {
         url "http://dl.bintray.com/siriscac/maven"
     }
   }
   ```
   
   and in your dependancies,
   ```groovy
    compile 'com.siriscac:progresspageindicator:1.0.0'
   ```

2. Include the ProgressPageIndicator widget in your layout.

  ```xml
    <com.cepheuen.progresspageindicator.ProgressPageIndicator
        android:id="@+id/pageIndicator"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        app:dotGap="2dp"
        app:fillColor="#cccccc"
        app:radius="8dp" />
  ```
    
3. Refer the widget from the layout and set the viewpager to it by using setViewPager().
   
  ```java
   pagerIndicator = (ProgressPageIndicator) findViewById(R.id.dotsL);
   pagerIndicator.setViewPager(viewPager);
  ```
   
#Customization

There are five attributes which are applicable to `ProgressPageIndicator`.

  - `dotGap` Space between the indicators
  - `strokeColor` Color of the Stroke
  - `fillColor` Color of the Fill
  - `radius` Radius of the indicator
  - `strokeRadius` Radius of the stroke
  

  - You can also set these attributes from your java code by calling `setStrokeColor(strokeColor)`, `setFillColor(fillColor)`, `setRadius(Radius)`, `setStrokeRadiys(strokeRadius)`, `setDotGap(dotGap)` respectively.
  
#Compatibility
  
  * Android Honeycomb 3.0+
  
#Changelog

### Version: 1.0

  * Initial Build
  
#Author

  * Muthuramakrishnan - <siriscac@gmail.com>
  
#License

    Copyright 2014 Muthuramakrishnan

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
