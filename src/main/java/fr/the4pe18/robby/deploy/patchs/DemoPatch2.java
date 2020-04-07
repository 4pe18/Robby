package fr.the4pe18.robby.deploy.patchs;

import fr.the4pe18.robby.deploy.RobbyPatch;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class DemoPatch2 extends RobbyPatch {
    public DemoPatch2() {
        super("DemoPatch2");
    }

    @Override
    protected void patch() {
        step(false, "Etape 1", context -> {
            TimeUnit.SECONDS.sleep(2);
            return null;
        });
        step(false, "Etape 2", context -> {
            TimeUnit.SECONDS.sleep(2);
            return null;
        });
        step(false, "Etape 3", context -> {
            TimeUnit.SECONDS.sleep(2);
            return null;
        });
        step(false, "Etape 4", context -> {
            TimeUnit.SECONDS.sleep(2);
            return null;
        });
        step(false, "Etape 5", context -> {
            TimeUnit.SECONDS.sleep(2);
            return null;
        });
        step(false, "Etape 6", context -> {
            TimeUnit.SECONDS.sleep(2);
            return null;
        });
        step(false, "Etape 7", context -> {
            TimeUnit.SECONDS.sleep(2);
            return null;
        });
        step(false, "Etape 8", context -> {
            TimeUnit.SECONDS.sleep(2);
            return null;
        });
        step(false, "Etape 9", context -> {
            TimeUnit.SECONDS.sleep(2);
            return null;
        });
        step(false, "Un nom d'étape :o", context -> {
            TimeUnit.SECONDS.sleep(2);
            return null;
        });
        step(false, "OMG cette étape a un nom très très long", context -> {
            TimeUnit.SECONDS.sleep(2);
            return null;
        });
    }
}
