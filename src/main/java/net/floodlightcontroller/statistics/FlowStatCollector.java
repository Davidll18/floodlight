package net.floodlightcontroller.statistics;

import net.floodlightcontroller.core.IFloodlightProviderService;
import net.floodlightcontroller.threadpool.IThreadPoolService;
import org.restlet.ext.jackson.JacksonRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import java.io.IOException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class FlowStatCollector extends ServerResource {
    protected static Logger log = LoggerFactory.getLogger(FlowStatCollector.class);

    protected IFloodlightProviderService floodlightProvider;
    protected IStatisticsService statisticsService;
    protected IThreadPoolService threadPoolService;
    protected ScheduledExecutorService scheduledExecutor;

    @Override
    protected void doInit() {
        floodlightProvider = (IFloodlightProviderService) getContext().getAttributes().get(IFloodlightProviderService.class);
        statisticsService = (IStatisticsService) getContext().getAttributes().get(IStatisticsService.class);
        threadPoolService = (IThreadPoolService) getContext().getAttributes().get(IThreadPoolService.class);
        scheduledExecutor = threadPoolService.getScheduledExecutor();
    }

    @Get("json")
    public Representation retrieve() {
        scheduledExecutor.scheduleAtFixedRate(new FlowStatsTask(), 0, 10, TimeUnit.SECONDS);
        return new JacksonRepresentation<>(new Status("FlowStatCollector started"));
    }

    private class FlowStatsTask implements Runnable {
        @Override
        public void run() {
            // Aquí debes implementar la lógica para recolectar las estadísticas de Flows
            // Puedes utilizar statisticsService para obtener estadísticas de Flows de switches
            // y procesarlas como desees
            log.info("Recolectando estadísticas de Flows...");
        }
    }
}

