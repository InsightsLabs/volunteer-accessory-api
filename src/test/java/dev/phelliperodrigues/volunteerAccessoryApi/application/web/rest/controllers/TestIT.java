package dev.phelliperodrigues.volunteerAccessoryApi.application.web.rest.controllers;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.RecordComponent;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Slf4j
public abstract class TestIT {
    protected Stream<TestCaseMethod> expandTestCases(Object testCaseRecord) {
        List<TestCaseMethod> tcms = new ArrayList<>();
        for (Method m : testCaseRecord.getClass().getDeclaredMethods()) {
            if (m.isAnnotationPresent(Test.class)) {
                tcms.add(new TestCaseMethod(testCaseRecord, m));
            }
        }
        return tcms.stream();
    }

    private static String renderNameFromRecordComponents(String value, Object record, RecordComponent[] recordComponents) {
        String name = value;
        for (RecordComponent rc : recordComponents) {
            Object result = null;
            try {
                result = rc.getAccessor().invoke(record);
            } catch (Exception e) {
                log.error("Error on renderNameFromRecordComponents", e);
            }
            name = name.replaceAll("\\$\\{" + rc.getName() + "}", String.valueOf(result));
        }
        return name;
    }

    protected record TestCaseMethod(Object testCase, Method method) {
        public void invoke() throws InvocationTargetException, IllegalAccessException {
            method.invoke(testCase);
        }
    }

    protected String generateDisplayName(TestCaseMethod tcm) {
        Method m = tcm.method();
        DisplayName displayName = m.getAnnotation(DisplayName.class);
        String testName = m.getName();
        if (displayName != null) {
            testName = renderNameFromRecordComponents(displayName.value(), tcm.testCase(), tcm.testCase().getClass().getRecordComponents());
        }
        return testName;
    }
}
