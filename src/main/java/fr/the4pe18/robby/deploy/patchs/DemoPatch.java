package fr.the4pe18.robby.deploy.patchs;

import fr.the4pe18.robby.deploy.RobbyPatch;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class DemoPatch extends RobbyPatch {
    public DemoPatch() {
        super("DemoPatch");
    }

    @Override
    protected void patch() {
        step(false, "Etape 1", context -> {
            TimeUnit.SECONDS.sleep(2);
            return null;
        });
        step(false, "Etape 2", context -> {
            TimeUnit.SECONDS.sleep(2);
            return 5;
        });
        step(false, "Etape 3", context -> {
            TimeUnit.SECONDS.sleep(2);
            throw new Exception("hello demo!");
        });
        step(false, "Etape 4", context -> {
            TimeUnit.SECONDS.sleep(2);
            return 5;
        });
        step(true, "Etape 5", context -> {
            Random r = new Random();
            TimeUnit.SECONDS.sleep(2);
            if (r.nextInt(10) > 6) throw new Exception("hello demo!");
            else return 5;
        });
        step(false, "Etape 6", context -> {
            TimeUnit.SECONDS.sleep(2);
            return 5;
        });
    }
}
