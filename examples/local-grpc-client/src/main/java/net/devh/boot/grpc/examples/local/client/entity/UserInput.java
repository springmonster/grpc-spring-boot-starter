package net.devh.boot.grpc.examples.local.client.entity;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class UserInput {
    private String name;
    private int age;
    private Map<String, String> address;
    private List<String> hobby;
}
