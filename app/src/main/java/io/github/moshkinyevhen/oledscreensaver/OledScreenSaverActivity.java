package io.github.moshkinyevhen.oledscreensaver;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.view.WindowManager;

/**
 * Displays one edge-to-edge black surface while keeping the display awake.
 *
 * <p>Inputs: launcher activation and a user tap. Linearization: {@link #onCreate(Bundle)} owns
 * the window flag and publishes the sole content view. Guarantees: every app-rendered pixel is
 * black and one tap finishes the task. Forbidden: background services, wake locks, permissions,
 * networking, analytics, persistent state, or brightness changes. Cleanup: Android clears the
 * window-owned keep-screen-on flag when this Activity finishes. Observe: this Activity and the
 * {@code FLAG_KEEP_SCREEN_ON} bit; device screenshots prove rendered black, not physical panel
 * power consumption.</p>
 */
@SuppressWarnings("deprecation") // API 23–29 require the legacy immersive-system-bar contract.
public final class OledScreenSaverActivity extends Activity {

    private static final int LEGACY_IMMERSIVE_FLAGS =
            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        View blackSurface = new View(this);
        blackSurface.setBackgroundColor(Color.BLACK);
        blackSurface.setContentDescription(getString(R.string.tap_to_close));
        blackSurface.setOnClickListener(ignored -> finishAndRemoveTask());
        setContentView(blackSurface);

        hideSystemBars();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemBars();
        }
    }

    private void hideSystemBars() {
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false);
            WindowInsetsController controller = window.getInsetsController();
            if (controller != null) {
                controller.hide(WindowInsets.Type.systemBars());
                controller.setSystemBarsBehavior(
                        WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE);
            }
            return;
        }

        window.getDecorView().setSystemUiVisibility(LEGACY_IMMERSIVE_FLAGS);
    }
}
