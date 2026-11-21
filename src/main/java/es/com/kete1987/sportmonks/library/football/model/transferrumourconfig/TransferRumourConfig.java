package es.com.kete1987.sportmonks.library.football.model.transferrumourconfig;

import es.com.kete1987.sportmonks.library.common.util.ModelCollections;

import java.util.List;

/**
 * Catalogue of {@code transfer-rumours/config}: the ids the transfer rumours refer to, grouped
 * into fee bands, transfer types and rumour statuses. Sportmonks recommends fetching it once and
 * caching it.
 */
public class TransferRumourConfig {
    private List<TransferRumourConfigEntry> fee;
    private List<TransferRumourConfigEntry> transfers;
    private List<TransferRumourConfigEntry> statuses;

    public List<TransferRumourConfigEntry> getFee() {
        return ModelCollections.unmodifiable(fee);
    }

    public List<TransferRumourConfigEntry> getTransfers() {
        return ModelCollections.unmodifiable(transfers);
    }

    public List<TransferRumourConfigEntry> getStatuses() {
        return ModelCollections.unmodifiable(statuses);
    }
}
