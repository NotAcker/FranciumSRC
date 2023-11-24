package org.apache.core.g.w;

import org.apache.core.g.c.C;
import org.apache.core.g.c.MBC;
import org.apache.core.m.s.S;
import org.apache.core.g.CG;
import org.apache.core.m.M;

public class MSW extends W {

    private final M module;
    private final MBC moduleButton;

    public MSW(CG parent, double x, double y, M module, MBC moduleButton) {
        super(parent, x, y, 200, 200);
        super.closable = true;
        super.minimizable = false;
        super.setTitle(module.getName());
        this.module = module;
        this.moduleButton = moduleButton;
        y = 40;
        for (S<?> setting : module.getSettings()) {
            C component = setting.makeComponent(this);
            if (component != null) {
                component.setX(20);
                component.setY(y);
                addComponent(component);
                y += component.getLength() + 17.0;
            }
        }
    }

    @Override
    public void onClose() {
        moduleButton.settingWindowClosed();
    }

}
