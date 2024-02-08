package com.github.khangzxrr.service.dto.requestProgressDto;

import com.github.khangzxrr.domain.enumeration.RequestProgressType;

public class RequestStepGuideDTO {

    private RequestProgressType currentStep;

    private RequestProgressType[] steps;

    public RequestStepGuideDTO(RequestProgressType currentStep, RequestProgressType[] steps) {
        this.currentStep = currentStep;
        this.steps = steps;
    }

    public RequestProgressType getCurrentState() {
        return currentStep;
    }

    public void setCurrentState(RequestProgressType currentStep) {
        this.currentStep = currentStep;
    }

    public RequestProgressType getCurrentStep() {
        return currentStep;
    }

    public void setCurrentStep(RequestProgressType currentStep) {
        this.currentStep = currentStep;
    }

    public RequestProgressType[] getSteps() {
        return steps;
    }

    public void setSteps(RequestProgressType[] steps) {
        this.steps = steps;
    }
}
