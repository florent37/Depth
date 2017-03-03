package com.github.florent37.depth.anim;

/**
 * Created by florentchampigny on 03/03/2017.
 */

public class ViewFinalState {

    private Float finalScale;
    private Float finalTranslationX;
    private Float finalTranslationY;
    private Float finalRotationX;
    private Float finalRotationZ;
    private Float finalElevation;

    public ViewFinalState() {
    }

    public Float getFinalScale() {
        return finalScale;
    }

    public Float getFinalTranslationY() {
        return finalTranslationY;
    }

    public Float getFinalTranslationX() {
        return finalTranslationX;
    }

    public Float getFinalRotationX() {
        return finalRotationX;
    }

    public Float getFinalRotationZ() {
        return finalRotationZ;
    }

    public Float getFinalElevation() {
        return finalElevation;
    }

    public static class Builder{

        private float finalScale;
        private float finalTranslationX;
        private float finalTranslationY;
        private float finalRotationX;
        private float finalRotationZ;
        private float finalElevation;

        public Builder() {
        }

        public Builder setFinalScale(float finalScale) {
            this.finalScale = finalScale;
            return this;
        }

        public Builder setFinalTranslationY(float finalTranslationY) {
            this.finalTranslationY = finalTranslationY;
            return this;
        }

        public Builder setFinalTranslationX(float finalTranslationX) {
            this.finalTranslationX = finalTranslationX;
            return this;
        }

        public Builder setFinalRotationX(float finalRotationX) {
            this.finalRotationX = finalRotationX;
            return this;
        }

        public Builder setFinalRotationZ(float finalRotationZ) {
            this.finalRotationZ = finalRotationZ;
            return this;
        }

        public Builder setFinalElevation(float finalElevation) {
            this.finalElevation = finalElevation;
            return this;
        }

        public ViewFinalState build(){
            final ViewFinalState viewFinalState = new ViewFinalState();
            viewFinalState.finalRotationX = finalRotationX;
            viewFinalState.finalRotationZ = finalRotationZ;
            viewFinalState.finalTranslationY = finalTranslationY;
            viewFinalState.finalTranslationX = finalTranslationX;
            viewFinalState.finalScale = finalScale;
            viewFinalState.finalElevation = finalElevation;
            return viewFinalState;
        }
    }

}
