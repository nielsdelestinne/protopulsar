package be.niedel.protopulsar.recruitmentservice;

import be.niedel.protopulsar.recruitmentservice.contract.CandidateSelectedEvent;
import be.niedel.protopulsar.recruitmentservice.contract.CandidateSelectedEvent.Name;
import org.apache.pulsar.client.api.Producer;

public class RecruitmentServiceApplication {

    private static final String URL = "pulsar://localhost:6650";
    private static final String TOPIC = "recruitment";

    public static void main(String[] args) {

        try (
                var pulsarClient = new PulsarProducerClient(URL);
                var pulsarTopicProducer = pulsarClient.createProducerFor(TOPIC, CandidateSelectedEvent.class)) {
            pulsarTopicProducer.send(createCandidateSelectedEvent("Jim", "Jimmens"));
            pulsarTopicProducer.send(createCandidateSelectedEvent("Sarah", "Sarajevo"));
            pulsarTopicProducer.send(createCandidateSelectedEvent("John", "Johnson"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static CandidateSelectedEvent createCandidateSelectedEvent(String firstName, String lastName) {
        return CandidateSelectedEvent.newBuilder()
                .setName(Name.newBuilder()
                        .setFirstName(firstName)
                        .setLastName(lastName)
                        .build())
                .build();
    }

}

