(2)取消导航栏的点击效果（类似水波纹的效果）

这里写图片描述

我们只需在BottomNavigationView的布局文件中添加一个属性：

app:itemBackground="@null"


(3)取消导航栏的每项点击文字和图片放大的效果

这里写图片描述

我们需要在values中的demens.xml中设置：

<?xml version="1.0" encoding="utf-8"?>
<resources>
    <!--BottomNavigationView 的选中没有选中的字体大小-->
    <dimen name="design_bottom_navigation_active_text_size">10dp</dimen>
    <dimen name="design_bottom_navigation_text_size">10dp</dimen>

    <!--BottomNavigationView 只放图标时的设置-->
    <!--<dimen name="design_bottom_navigation_active_text_size">0dp</dimen>-->
    <!--<dimen name="design_bottom_navigation_text_size">0dp</dimen>-->
    <!--<dimen name="design_bottom_navigation_margin">16dp</dimen>-->
</resources>
