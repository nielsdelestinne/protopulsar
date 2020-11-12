package be.niedel.protopulsar.employmentservice;

import be.niedel.protopulsar.recruitmentservice.contract.CandidateSelectedEvent;
import com.google.protobuf.GeneratedMessageV3;
import org.apache.pulsar.client.api.*;

import java.io.Closeable;
import java.io.IOException;
import java.util.UUID;

import static org.apache.pulsar.client.api.PulsarClient.builder;

public class PulsarConsumerClient implements Closeable {

    private final PulsarClient pulsarClient;

    public PulsarConsumerClient(String pulsarConnectionUrl) throws PulsarClientException {
        pulsarClient = createClient(pulsarConnectionUrl);
    }

    private PulsarClient createClient(String pulsarConnectionUrl) throws PulsarClientException {
        return builder()
                .serviceUrl(pulsarConnectionUrl)
                .build();
    }

    public <T extends GeneratedMessageV3> Consumer<T> createConsumerFor(String topic, Class<T> clazz) throws PulsarClientException {
        return pulsarClient
                .newConsumer(Schema.PROTOBUF(clazz))
                .topic(topic)
                .subscriptionName("Subscription: " + UUID.randomUUID().toString())
                .subscribe();
    }

    @Override
    public void close() throws IOException {
        pulsarClient.close();
    }
}
