package ar.edu.utn.frba.dds.notification;

import com.microsoft.azure.functions.*;
import com.microsoft.azure.functions.annotation.*;
import java.time.LocalDateTime;

/**
 * Azure Functions with Timer trigger.
 */
public class TimerTriggerJava {
    /**
     * This function will be invoked periodically according to the specified schedule.
     */
    @FunctionName("TimerTriggerJava")
    public void run(
        @TimerTrigger(name = "timerInfo", schedule = "0 */5 * * * *") String timerInfo,
        final ExecutionContext context
    ) {
        context.getLogger().info("Java Timer trigger function executed at: " + LocalDateTime.now());
        MainNotificaciones.main(new String[] {});
    }
}
