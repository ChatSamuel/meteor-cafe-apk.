/*
 * Meteor Cafe dedicated launcher.
 * The J2ME-Loader runtime remains under its original Apache-2.0 license.
 * The bundled JAR preserves the original game except for one documented
 * +8-pixel placement adjustment to the four color buttons. The pristine JAR
 * is preserved in the project-level original/ folder.
 */
package ru.playsoftware.j2meloader;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.content.SharedPreferences;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import com.android.dx.command.dexer.Main;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

import javax.microedition.shell.MicroActivity;

import ru.playsoftware.j2meloader.config.Config;
import ru.playsoftware.j2meloader.config.ProfileModel;
import ru.playsoftware.j2meloader.config.ProfilesManager;
import ru.playsoftware.j2meloader.util.Constants;
import ru.woesss.j2me.jar.Descriptor;

/**
 * First-run bootstrap for the bundled Meteor Cafe MIDlet.
 * It converts the bundled game JAR to the DEX format required by Android's runtime,
 * stores the bundled JAR for resource loading, writes the MIDlet descriptor and
 * creates a 240x320 touch-oriented profile. The original JAR is preserved separately.
 */
public final class MeteorCafeLauncherActivity extends AppCompatActivity {
    private static final String GAME_ID = "meteor-cafe-1-18-0";
    private static final String ASSET_JAR = "games/meteor_cafe.jar";
    private static final String EXPECTED_SHA256 =
            "81b4bc734c18406bcfe250846157dd0b84ab6cb29712f76c1a636a850fce1d3a";

    private TextView statusText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        showPreparingScreen();
        initializeFirstLaunchPreferences();

        new Thread(() -> {
            try {
                File appDir = prepareGameFiles();
                runOnUiThread(() -> launchGame(appDir));
            } catch (Throwable error) {
                error.printStackTrace();
                runOnUiThread(() -> showFailure(error));
            }
        }, "MeteorCafeBootstrap").start();
    }

    private void initializeFirstLaunchPreferences() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        boolean changed = false;
        if (!preferences.contains(Constants.PREF_TOOLBAR)) {
            editor.putBoolean(Constants.PREF_TOOLBAR, false);
            changed = true;
        }
        if (!preferences.contains(Constants.PREF_STATUSBAR)) {
            editor.putBoolean(Constants.PREF_STATUSBAR, false);
            changed = true;
        }
        if (!preferences.contains(Constants.PREF_KEEP_SCREEN)) {
            editor.putBoolean(Constants.PREF_KEEP_SCREEN, true);
            changed = true;
        }
        if (!preferences.contains(Constants.PREF_VIBRATION)) {
            editor.putBoolean(Constants.PREF_VIBRATION, true);
            changed = true;
        }
        if (changed) editor.apply();
    }

    private void showPreparingScreen() {
        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setGravity(Gravity.CENTER);
        root.setPadding(32, 32, 32, 32);
        root.setBackgroundColor(0xFF11151B);

        TextView title = new TextView(this);
        title.setText("METEOR CAFÉ");
        title.setTextColor(0xFFF2F0E9);
        title.setTextSize(24);
        title.setGravity(Gravity.CENTER);
        root.addView(title, new LinearLayout.LayoutParams(-1, -2));

        statusText = new TextView(this);
        statusText.setText("Preparando o jogo pela primeira vez…");
        statusText.setTextColor(0xFFB9C0C9);
        statusText.setTextSize(14);
        statusText.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams statusParams = new LinearLayout.LayoutParams(-1, -2);
        statusParams.topMargin = 20;
        root.addView(statusText, statusParams);

        ProgressBar progress = new ProgressBar(this);
        LinearLayout.LayoutParams progressParams = new LinearLayout.LayoutParams(-2, -2);
        progressParams.topMargin = 24;
        root.addView(progress, progressParams);
        setContentView(root);
    }

    private File prepareGameFiles() throws Exception {
        File appDir = new File(Config.getAppDir(), GAME_ID);
        File configDir = new File(Config.getConfigsDir(), GAME_ID);
        File dataDir = new File(Config.getDataDir(), GAME_ID);
        ensureDirectory(appDir);
        ensureDirectory(configDir);
        ensureDirectory(dataDir);

        File dexFile = new File(appDir, Config.MIDLET_DEX_FILE);
        File descriptorFile = new File(appDir, Config.MIDLET_MANIFEST_FILE);
        File resourceJar = new File(appDir, Config.MIDLET_RES_FILE);
        boolean installed = dexFile.isFile() && dexFile.length() > 0
                && descriptorFile.isFile() && resourceJar.isFile()
                && sha256(resourceJar).equals(EXPECTED_SHA256);

        if (!installed) {
            copyBundledJar(resourceJar);
            convertJarToDex(resourceJar, dexFile);
            writeDescriptor(resourceJar, descriptorFile);
        }

        // This dedicated app owns its profile. Re-apply it on every launch so
        // settings from an earlier test cannot leave the old top-aligned layout.
        ProfileModel profile = ProfilesManager.loadConfig(configDir);
        if (profile == null) {
            profile = new ProfileModel(configDir);
        }
        profile.screenWidth = 240;
        profile.screenHeight = 320; // Keep the game's native MIDP canvas for touch compatibility.
        profile.orientation = 2; // portrait
        profile.screenBackgroundColor = 0xFF243746; // themed fallback behind the artwork
        profile.screenScaleToFit = true;
        profile.screenKeepAspectRatio = true;
        profile.screenScaleType = 1; // fit; never stretch the original sprites
        profile.screenScaleRatio = 100;
        profile.screenGravity = 2; // center the native 240x320 view
        profile.screenFilter = false; // preserve crisp pixel-art edges
        profile.forceFullscreen = true;
        profile.touchInput = true;
        profile.showKeyboard = false;
        profile.graphicsMode = android.os.Build.VERSION.SDK_INT >= 26 ? 3 : 0;
        profile.hwAcceleration = android.os.Build.VERSION.SDK_INT >= 26;
        profile.parallelRedrawScreen = false;
        if (!ProfilesManager.saveConfig(profile)) {
            throw new IOException("Não foi possível salvar a configuração de tela do Meteor Café.");
        }
        return appDir;
    }

    private void copyBundledJar(File destination) throws Exception {
        File temp = new File(destination.getParentFile(), "res.jar.tmp");
        try (InputStream input = getAssets().open(ASSET_JAR);
             FileOutputStream output = new FileOutputStream(temp)) {
            byte[] buffer = new byte[16 * 1024];
            int count;
            while ((count = input.read(buffer)) != -1) output.write(buffer, 0, count);
        }
        if (!sha256(temp).equals(EXPECTED_SHA256)) {
            // Do not proceed with a corrupted or unexpectedly changed game archive.
            //noinspection ResultOfMethodCallIgnored
            temp.delete();
            throw new IOException("A cópia interna do JAR Meteor Café não passou na verificação SHA-256.");
        }
        if (destination.exists() && !destination.delete()) {
            throw new IOException("Não foi possível substituir o arquivo de recursos do jogo.");
        }
        if (!temp.renameTo(destination)) {
            throw new IOException("Não foi possível finalizar a cópia interna do jogo.");
        }
    }

    private void convertJarToDex(File jar, File dex) throws Exception {
        // Same conversion flags used by the upstream J2ME-Loader installer.
        Main.main(new String[]{
                "--no-optimize",
                "--core-library",
                "--output=" + dex.getAbsolutePath(),
                jar.getAbsolutePath()
        });
        if (!dex.isFile() || dex.length() == 0) {
            throw new IOException("O conversor não produziu o arquivo DEX do Meteor Café.");
        }
    }

    private void writeDescriptor(File jar, File descriptorFile) throws Exception {
        try (JarFile archive = new JarFile(jar)) {
            Manifest manifest = archive.getManifest();
            if (manifest == null) throw new IOException("O JAR não contém META-INF/MANIFEST.MF.");
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            manifest.write(output);
            Descriptor descriptor = new Descriptor(
                    output.toString(StandardCharsets.UTF_8), false);
            descriptor.writeTo(descriptorFile);
        }
    }

    private void launchGame(File appDir) {
        Intent intent = new Intent(this, MicroActivity.class);
        intent.setData(Uri.parse(appDir.getAbsolutePath()));
        intent.putExtra(Constants.KEY_MIDLET_NAME, "MeteorCafe");
        startActivity(intent);
        finish();
    }

    private void showFailure(Throwable error) {
        new AlertDialog.Builder(this)
                .setTitle("Não foi possível iniciar o Meteor Café")
                .setMessage(error.getMessage() == null ? error.toString() : error.getMessage())
                .setCancelable(false)
                .setPositiveButton("Tentar novamente", (dialog, which) -> recreate())
                .setNegativeButton("Fechar", (dialog, which) -> finish())
                .show();
    }

    private static void ensureDirectory(File directory) throws IOException {
        if (!directory.isDirectory() && !directory.mkdirs()) {
            throw new IOException("Não foi possível criar a pasta: " + directory.getAbsolutePath());
        }
    }

    private static String sha256(File file) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        try (InputStream input = new java.io.FileInputStream(file)) {
            byte[] buffer = new byte[32 * 1024];
            int count;
            while ((count = input.read(buffer)) != -1) digest.update(buffer, 0, count);
        }
        StringBuilder hex = new StringBuilder(64);
        for (byte value : digest.digest()) hex.append(String.format("%02x", value & 0xff));
        return hex.toString();
    }
}
