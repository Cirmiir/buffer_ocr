package com.kirmiir.ocrbuffer.globalkeylistener;

import com.kirmiir.ocrbuffer.actor.IActor;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

public class GlobalKeyListener implements NativeKeyListener {
    private short hotKeyFlag = 0x00;
    private IActor _action;
    private static final short MASK_1 = 1;
    private static final short MASK_2 = 1 << 1;
    private static final short MASK_3 = 1 << 2;

    public void addAction(IActor action){
        _action = action;
    }

    private boolean areAllPressed(){
        return hotKeyFlag == (MASK_1 | MASK_2 | MASK_3);
    }

    private void check(){
        if (areAllPressed() && _action != null)
            _action.execute();

    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {
        switch (e.getKeyCode()){
            case NativeKeyEvent.VC_ALT_L:
                hotKeyFlag |= MASK_1;
                check();
                break;
            case NativeKeyEvent.VC_CONTROL_L:
                hotKeyFlag |= MASK_2;
                check();
                break;
            case NativeKeyEvent.VC_A:
                hotKeyFlag |= MASK_3;
                check();
                break;
            default:
        }
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent e) {
        switch (e.getKeyCode()){
        case NativeKeyEvent.VC_ALT_L:
            hotKeyFlag &= MASK_1;
            check();
            break;
        case NativeKeyEvent.VC_CONTROL_L:
            hotKeyFlag &= MASK_2;
            check();
            break;
        case NativeKeyEvent.VC_A:
            hotKeyFlag &= MASK_3;
            check();
            break;
        default:
    }
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) {
    }
}
