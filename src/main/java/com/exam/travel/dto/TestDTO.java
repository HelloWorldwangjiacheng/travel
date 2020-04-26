package com.exam.travel.dto;

/**
 * @Author w1586
 * @Date 2020/3/24 0:00
 * @Cersion 1.0
 */
public class TestDTO {

    private String name;
    private String age;

    public static TestDTO createGeneratorName(String name){
        TestDTO testDTO = new TestDTO();
        testDTO.name = name;
        return testDTO;
    }

    @Override
    public String toString() {
        return "TestDTO{" +
                "name='" + name + '\'' +
                ", age='" + age + '\'' +
                '}';
    }
}
