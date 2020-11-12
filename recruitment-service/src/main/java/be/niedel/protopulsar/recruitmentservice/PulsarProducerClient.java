package be.niedel.protopulsar.recruitmentservice;

import be.niedel.protopulsar.recruitmentservice.contract.CandidateSelectedEvent;
import com.google.protobuf.GeneratedMessageV3;
import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.api.Schema;

import java.io.Closeable;
import java.io.IOException;

import static org.apache.pulsar.client.api.PulsarClient.builder;

public class PulsarProducerClient implements Closeable {

    private final PulsarClient pulsarClient;

    public PulsarProducerClient(String pulsarConnectionUrl) throws PulsarClientException {
        pulsarClient = createClient(pulsarConnectionUrl);
    }

    private PulsarClient createClient(String pulsarConnectionUrl) throws PulsarClientException {
        return builder()
                .serviceUrl(pulsarConnectionUrl)
                .build();
    }

    public <T extends GeneratedMessageV3> Producer<T> createProducerFor(String topic, Class<T> clazz) throws PulsarClientException {
        return pulsarClient
                .newProducer(Schema.PROTOBUF(clazz))
                .topic(topic)
                .create();
    }

    @Override
    public void close() throws IOException {
        pulsarClient.close();
    }
}
