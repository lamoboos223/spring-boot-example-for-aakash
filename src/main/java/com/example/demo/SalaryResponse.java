package com.example.demo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class SalaryResponse {

    @JsonProperty("decision_id")
    private String decisionId;
    @JsonProperty("result")
    private ResultClass result;

    public String getDecisionId() {
        return decisionId;
    }

    public void setDecisionId(String decisionId) {
        this.decisionId = decisionId;
    }

    public ResultClass getResult() {
        return result;
    }

    public void setResult(ResultClass result) {
        this.result = result;
    }

    public static class ResultClass {
        @JsonProperty("allow")
        private boolean allow;
        @JsonProperty("subordinates")
        private Map<String, String[]> subordinates;

        public boolean isAllow() {
            return allow;
        }

        public void setAllow(boolean allow) {
            this.allow = allow;
        }

        public Map<String, String[]> getSubordinates() {
            return subordinates;
        }

        public void setSubordinates(Map<String, String[]> subordinates) {
            this.subordinates = subordinates;
        }
    }
}