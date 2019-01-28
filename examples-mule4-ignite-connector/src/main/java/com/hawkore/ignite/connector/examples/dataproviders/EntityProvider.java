package com.hawkore.ignite.connector.examples.dataproviders;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import com.hawkore.ignite.examples.entities.simple.Entity;
import com.hawkore.ignite.extensions.api.ingestion.IQueueDataProvider;
import com.hawkore.ignite.extensions.api.ingestion.IngestionProgressNotifier;
import com.hawkore.ignite.extensions.api.ingestion.IngestionResult;

/**
 * 
 * Thread-safe Queue Data Provider implementation to use on
 * apache-ignite:queue-ingest-data operation
 *
 * @author Manuel Núñez (manuel.nunez@hawkore.com)
 *
 */
public class EntityProvider implements IQueueDataProvider<Entity, Entity> {

    // thread-safe data supplier
    private final EntitySupplier dataProvider;

    // thread-safe progress notifier
    private final IngestionProgressNotifier progressNotifier;

    /**
     * 
     * @param numberOfEntities
     *            - number of entities to generate
     * @param initialId
     *            - initial id to use
     * @param numberOfIngesters
     *            - number of ingesters that apache-ignite:queue-ingest-data
     *            operation uses (just to sync IngestionResult when ingestion
     *            finished )
     */
    public EntityProvider(int numberOfEntities, int initialId, int numberOfIngesters) {
        // thread-safe data supplier
        dataProvider = new EntitySupplier(numberOfEntities, initialId);

        // thread-safe progress notifier
        progressNotifier = new IngestionProgressNotifier() {

            private IngestionResult result = new IngestionResult();

            private int receivedFinishNotifications = 0;

            @Override
            public synchronized void notify(IngestionResult result) {

                if (result.isFinished()) {
                    this.result.updateGlobal(this.result.getProcessed() + result.getProcessed(),
                        this.result.getSent() + result.getSent(),
                        Math.max(this.result.getElapsedTime(), result.getElapsedTime()));
                    receivedFinishNotifications++;
                    if (receivedFinishNotifications == numberOfIngesters) {
                        this.result.finish();
                    }
                }
            }

            @Override
            public synchronized IngestionResult getIngestionResult() {
                return this.result;
            }
        };
    }

    @Override
    public Supplier<Entity> getDataSupplier() {
        return dataProvider;
    }

    @Override
    public Predicate<Entity> getFilter() {
        return null;
    }

    @Override
    public IngestionProgressNotifier getProgressNotifier() {
        return progressNotifier;
    }

    @Override
    public Function<Entity, Entity> getTransformer() {
        return p -> {
            p.setData(p.getData() + " trasformed ");
            return p;
        };
    }

}