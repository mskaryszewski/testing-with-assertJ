package com.mskaryszewski.cucumber;

import cucumber.api.java.en.Then;

public class CucumberTest {

    @Then("^A person named (.*) is (.*) years old$")
    public void certificationName(String name, int age) {
        System.out.println("This is my name: " + name + " and I am " + age + " years old.");
    }
}