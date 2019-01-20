package com.hawkore.ignite.connector.examples.dataproviders;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import javax.cache.expiry.ExpiryPolicy;

import com.hawkore.ignite.examples.entities.pois.Poi;
import com.hawkore.ignite.examples.entities.pois.PoiKey;
import com.hawkore.ignite.extensions.api.ingestion.ICacheDataProvider;
import com.hawkore.ignite.extensions.api.ingestion.IngestionProgressNotifier;
import com.hawkore.ignite.extensions.api.ingestion.IngestionResult;

public class PoisProvider implements ICacheDataProvider<PoiKey, Poi, Poi>{

        // thread-safe pois supplier
        final PoisSupplier poisSupplier;
        
        // thread-safe progress notifier
        final IngestionProgressNotifier progressNotifier ;

        
    	public PoisProvider(int numberOfPois, String countryCode, int initKey, int numberOfIngesters) {
    		
    		poisSupplier = new PoisSupplier(numberOfPois, countryCode, initKey);
    		
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
        public Supplier<Poi> getDataSupplier() {
            return poisSupplier;
        }

        @Override
        public Function<Poi, ExpiryPolicy> getExpiryPolicier() {
            return null;
        }

        @Override
        public Predicate<Poi> getFilter() {
            // just do not filter input
            return null;
        }

        @Override
        public Function<Poi, PoiKey> getKeyResolver() {
            // extract key from poi to store into "pois" cache
            return Poi::getKey;
        }

        @Override
        public IngestionProgressNotifier getProgressNotifier() {
            return progressNotifier;
        }

        @Override
        public Function<Poi, Poi> getTransformer() {
            // just return poi without transformation
            return p -> p;
        }
}
