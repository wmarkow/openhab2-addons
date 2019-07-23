/**
 * Copyright (c) 2010-2019 Contributors to the openHAB project
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.openhab.binding.astro.internal.handler;

import static org.eclipse.smarthome.core.thing.ThingStatus.ONLINE;
import static org.openhab.binding.astro.internal.AstroBindingConstants.THING_TYPE_SUN;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.eclipse.smarthome.core.scheduler.CronScheduler;
import org.eclipse.smarthome.core.thing.Thing;
import org.eclipse.smarthome.core.thing.ThingTypeUID;
import org.openhab.binding.astro.internal.calc.SunCalc;
import org.openhab.binding.astro.internal.job.DailyJobSun;
import org.openhab.binding.astro.internal.job.DailyJobSunScheduler;
import org.openhab.binding.astro.internal.job.Job;
import org.openhab.binding.astro.internal.job.PositionalJob;
import org.openhab.binding.astro.internal.model.Planet;
import org.openhab.binding.astro.internal.model.Sun;
import org.openhab.binding.astro.internal.util.DateTimeUtils;

/**
 * The SunHandler is responsible for updating calculated sun data.
 *
 * @author Gerhard Riegler - Initial contribution
 * @author Amit Kumar Mondal - Implementation to be compliant with ESH Scheduler
 */
public class SunHandler extends AstroThingHandler {

    public static final Set<ThingTypeUID> SUPPORTED_THING_TYPES = new HashSet<>(Arrays.asList(THING_TYPE_SUN));

    private final String[] positionalChannelIds = new String[] { "position#azimuth", "position#elevation",
            "radiation#direct", "radiation#diffuse", "radiation#total" };
    private final SunCalc sunCalc = new SunCalc();
    private Sun sun;

    /**
     * Constructor
     */
    public SunHandler(Thing thing) {
        super(thing);
    }

    @Override
    public void publishDailyInfo() {
        initializeSun();
        publishPositionalInfo();
    }

    @Override
    public void publishPositionalInfo() {
        initializeSun();
        sunCalc.setPositionalInfo(Calendar.getInstance(), thingConfig.getLatitude(), thingConfig.getLongitude(),
                thingConfig.getAltitude(), sun);
        publishPlanet();
    }

    @Override
    public Planet getPlanet() {
        return sun;
    }

    @Override
    public void dispose() {
        super.dispose();
        sun = null;
    }

    @Override
    protected String[] getPositionalChannelIds() {
        return positionalChannelIds;
    }

    @Override
    protected Job getDailyJob() {
        return new DailyJobSun(thing.getUID().getAsString(), this);
    }

    @Override
    protected void restartJobs() {
        logger.debug("Restarting jobs for thing {}", getThing().getUID());
        monitor.lock();
        try {
            stopJobs();
            if (getThing().getStatus() == ONLINE) {
                String thingUID = getThing().getUID().toString();

                // Daily Job
                Job runnable = getDailyJob();
                // Execute daily startup job immediately
                runnable.run();

                scheduleNextJobRestart((Sun) getPlanet(), thingUID);

                schedulePositionalJob(thingUID);
            }
        } finally {
            monitor.unlock();
        }
    }

    private void initializeSun() {
        sun = sunCalc.getSunInfo(Calendar.getInstance(), thingConfig.getLatitude(), thingConfig.getLongitude(),
                thingConfig.getAltitude());
    }

    private void scheduleNextJobRestart(Sun sun, String thingUID) {
        DailyJobSunScheduler dailyJobSunScheduler = new DailyJobSunScheduler();
        Calendar nextJobsRestart = dailyJobSunScheduler.findNextExecutionTime(Calendar.getInstance(),
                sun.getNoon().getStart(), sun.getMorningNight().getEnd());

        logger.info("Scheduling next jobs restart for thing {} to {}", getThing().getUID(),
                nextJobsRestart.getTime().toString());
        schedule(new Job() {

            @Override
            public void run() {
                restartJobs();
            }

            @Override
            public String getThingUID() {
                return thingUID;
            }
        }, nextJobsRestart);
    }
}
