package be.niedel.protopulsar.employmentservice;

import be.niedel.protopulsar.recruitmentservice.contract.CandidateSelectedEvent;
import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.Message;
import org.apache.pulsar.client.api.PulsarClientException;

public class EmploymentServiceApplication {

    private static final String URL = "pulsar://localhost:6650";
    private static final String TOPIC = "recruitment";
    private static final int AMOUNT_OF_MESSAGES_TO_CONSUME = 30;

    public static void main(String[] args) {
        try (
                var pulsarClient = new PulsarConsumerClient(URL);
                var pulsarTopicConsumer = pulsarClient.createConsumerFor(TOPIC, CandidateSelectedEvent.class)) {
            startConsumingMessages(pulsarTopicConsumer, AMOUNT_OF_MESSAGES_TO_CONSUME);
            System.out.println("Job's done... closing down EmploymentServiceApplication");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void startConsumingMessages(Consumer<CandidateSelectedEvent> consumer, int amountOfMessagesToConsume) throws PulsarClientException {
        while (amountOfMessagesToConsume > 0) {
            Message<CandidateSelectedEvent> message = consumer.receive();
            CandidateSelectedEvent event = message.getValue();
            try {
                System.out.println("Message received from: " + message.getProducerName());
                System.out.println(event.toString());
                consumer.acknowledge(message);
                amountOfMessagesToConsume -= 1;
            } catch (Exception e) {
                consumer.negativeAcknowledge(message);
            }
        }
    }

}

