package be.niedel.protopulsar.recruitmentservice;

import be.niedel.protopulsar.employmentservice.contract.CreateEmployerRequest;
import be.niedel.protopulsar.employmentservice.contract.Id;

import java.util.Arrays;

public class RecruitmentServiceApplication {

    public static void main(String[] args) {
        EmploymentClient employmentClient = new EmploymentClient();

        employmentClient.createEmployer(
                CreateEmployerRequest.newBuilder()
                        .setId(Id.newBuilder().setValue("123"))
                        .setName("Jimmy")
                        .build())
                .thenAccept(response -> {
                    System.out.println(response);
                    System.out.println(Arrays.toString(response.toByteArray()));
                }).join();
    }

}

