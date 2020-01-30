package ru.otus.homework;

import com.sun.management.GarbageCollectionNotificationInfo;
import ru.otus.homework.gcLoad.Loading;

import javax.management.NotificationEmitter;
import javax.management.NotificationListener;
import javax.management.openmbean.CompositeData;
import java.lang.management.GarbageCollectorMXBean;

import java.util.List;


public class Testing {

    public static void main(String[] args) {
        switchOnMonitoring();
        Loading loading = new Loading(1000000000);
        loading.run();

    }

    private static void switchOnMonitoring() {
        final long[] youngGenCount = {0};
        final long[] oldGenCount = {0};
        final double[] youngGenDuration = {0};
        final double[] oldGenDuration = {0};

        List<GarbageCollectorMXBean> gcbeans = java.lang.management.ManagementFactory.getGarbageCollectorMXBeans();
        for (GarbageCollectorMXBean gcbean : gcbeans) {
            System.out.println("GC name:" + gcbean.getName());
            NotificationEmitter emitter = (NotificationEmitter) gcbean;
            NotificationListener listener = (notification, handback) -> {
                if (notification.getType().equals(GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION)) {
                    GarbageCollectionNotificationInfo info = GarbageCollectionNotificationInfo.from((CompositeData) notification.getUserData());
                    String gcAction = info.getGcAction();
                    long duration = info.getGcInfo().getDuration();
                    double totalDuration = (youngGenDuration[0] + oldGenDuration[0]);
                    double programTime = info.getGcInfo().getEndTime();
                    double efficiency = (programTime - totalDuration) / programTime * 100;
                    if (gcAction.equals("end of minor GC")) {
                        youngGenCount[0]++;
                        youngGenDuration[0] += duration;
                    }
                    if (gcAction.equals("end of major GC")) {
                        oldGenCount[0]++;
                        oldGenDuration[0] += duration;
                    }
                    System.out.println("Time of the last collection: " + programTime / 60000 + " ,Minor collections = " + youngGenCount[0] + " ,duration =  " + youngGenDuration[0] / 60000 + "m," + " Major collections = " + oldGenCount[0] + ", duration = " + oldGenDuration[0] / 60000 + "m, " + " Total GC duration time = " + totalDuration / 60000 + "m" + " ,Total amount of iterations = " + Loading.majorIterationsCount + " ,Efficiency = " + efficiency + "%");
                }
            };
            emitter.addNotificationListener(listener, null, null);
        }
    }
}

