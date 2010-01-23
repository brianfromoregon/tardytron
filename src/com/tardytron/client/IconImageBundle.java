package com.tardytron.client;

import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.ImageBundle;

public interface IconImageBundle extends ImageBundle {

    @Resource("flag_blue.gif")
    AbstractImagePrototype blueFlag();

    @Resource("flag_red.gif")
    AbstractImagePrototype redFlag();

    @Resource("flag_green.gif")
    AbstractImagePrototype greenFlag();

    @Resource("redx.gif")
    AbstractImagePrototype redX();
}
